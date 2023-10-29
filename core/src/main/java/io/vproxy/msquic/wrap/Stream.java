package io.vproxy.msquic.wrap;

import io.vproxy.base.util.Logger;
import io.vproxy.msquic.*;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;
import io.vproxy.pni.array.IntArray;
import io.vproxy.pni.array.LongArray;

import java.lang.foreign.MemorySegment;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.*;

public abstract class Stream {
    public final PNIRef<Stream> ref;
    public final Options opts;
    public final QuicStream streamQ;

    protected final Set<SendContext> pendingSendContexts = new HashSet<>();

    private long id;

    public Stream(Options opts) {
        this.ref = PNIRef.of(this);
        this.opts = opts;
        if (opts.streamQ != null) {
            this.streamQ = opts.streamQ;
        } else {
            this.streamQ = opts.streamSupplier.apply(ref);
        }
        opts.streamSupplier = null; // gc

        if (opts.streamQ != null) {
            this.canCallClose = false;
        }

        getIdFromStream(streamQ);
    }

    public static class OptionsBase extends Connection.OptionsBase {
        public final Connection connection;
        protected final QuicStream streamQ;

        public OptionsBase(Connection connection) {
            super(connection.opts);
            this.connection = connection;
            this.streamQ = null;
        }

        public OptionsBase(Connection connection, QuicStream streamQ) {
            super(connection.opts);
            this.connection = connection;
            this.streamQ = streamQ;
        }
    }

    public static class Options extends OptionsBase {
        private final Allocator allocator;
        private Function<PNIRef<Stream>, QuicStream> streamSupplier;

        public Options(Connection connection, Allocator allocator, QuicStream streamQ) {
            super(connection, streamQ);
            this.allocator = allocator;
            this.streamSupplier = null;
        }

        public Options(Connection connection, Allocator allocator, Function<PNIRef<Stream>, QuicStream> streamSupplier) {
            super(connection);
            this.allocator = allocator;
            this.streamSupplier = streamSupplier;
        }
    }

    private void getIdFromStream(QuicStream stream) {
        if (stream != null) {
            try (var tmpAllocator = Allocator.ofConfined()) {
                var idPtr = new LongArray(tmpAllocator, 1);
                var lenPtr = new IntArray(tmpAllocator, 1);
                opts.apiTable.opts.apiTableQ.getParam(stream.getStream(), QUIC_PARAM_STREAM_ID, lenPtr, idPtr.MEMORY);
                id = idPtr.get(0);
            }
        }
    }

    public Set<SendContext> getPendingSendContexts() {
        return pendingSendContexts;
    }

    private volatile boolean closed = false;
    private volatile boolean streamIsClosed = false;
    private volatile boolean streamIsShutdown = false;
    private volatile boolean canCallClose = true;

    public boolean isClosed() {
        return closed;
    }

    public void close() {
        if (closed) {
            return;
        }
        synchronized (this) {
            if (closed) {
                return;
            }
            closed = true;
        }

        closeStream();
        opts.allocator.close();
        ref.close();
        for (var ctx : pendingSendContexts) {
            finish(ctx, false);
        }
        close0();
    }

    private void finish(SendContext ctx, boolean succeeded) {
        pendingSendContexts.remove(ctx);
        ctx.onSendComplete.onSendComplete(ctx, succeeded);
    }

    protected void close0() {
    }

    /**
     * Call `close` if allowed.
     * If `close` is not allowed to be called, then call `shutdown` instead.
     * If `close` is called, `shutdown` will not be called.
     * But if `shutdown` is called, `close` still can be called by re-invoking this method.
     * `close` and `shutdown` would each only be called once.
     */
    public void closeStream() {
        if (streamIsClosed) {
            return;
        }
        synchronized (this) {
            if (streamIsClosed) {
                return;
            }
            if (canCallClose) {
                streamIsClosed = true;
            }
        }
        if (canCallClose) {
            if (streamQ != null) {
                streamQ.close();
            }
            return;
        }
        if (streamIsShutdown) {
            return;
        }
        synchronized (this) {
            if (streamIsShutdown) {
                return;
            }
            streamIsShutdown = true;
        }
        streamQ.shutdown(QUIC_STREAM_SHUTDOWN_FLAG_ABORT_RECEIVE |
                         QUIC_STREAM_SHUTDOWN_FLAG_ABORT_SEND, 0);
    }

    public int send(Allocator allocator, MemorySegment... dataArray) {
        return send(0, allocator, dataArray);
    }

    public int send(int flags, Allocator allocator, MemorySegment... dataArray) {
        return send(flags, new SendContext(allocator, (ctx, ok) -> ctx.allocator.close()), dataArray);
    }

    public int send(int flags, SendContext ctx, MemorySegment... dataArray) {
        if (dataArray.length == 0)
            return 0;
        var qbufArray = new QuicBuffer.Array(ctx.allocator, dataArray.length);
        for (int i = 0; i < dataArray.length; ++i) {
            var qbuf = qbufArray.get(i);
            var data = dataArray[i];
            qbuf.setLength((int) data.byteSize());
            qbuf.setBuffer(data);
        }
        int res = streamQ.send(qbufArray.get(0) /*address*/, dataArray.length, flags, PNIRef.of(ctx).MEMORY);
        if (res == 0) {
            pendingSendContexts.add(ctx);
            return 0;
        }
        // failed
        finish(ctx, false);
        return res;
    }

    // need to override
    public int callback(QuicStreamEvent event) {
        if (requireEventLogging()) {
            logEvent(event);
        }

        return switch (event.getType()) {
            case QUIC_STREAM_EVENT_START_COMPLETE -> {
                var data = event.getUnion().getSTART_COMPLETE();
                if (data.getStatus() != 0) {
                    closeStream();
                    yield 0;
                }
                canCallClose = false;
                id = data.getID();
                yield 0;
            }
            case QUIC_STREAM_EVENT_SEND_COMPLETE -> {
                var data = event.getUnion().getSEND_COMPLETE();
                var context = data.getClientContext();
                if (context != null) {
                    var ctxRef = PNIRef.<SendContext>of(context);
                    var ctx = ctxRef.getRef();
                    ctxRef.close();
                    finish(ctx, !data.isCanceled());
                }
                yield 0;
            }
            case QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE -> {
                canCallClose = true;
                close();
                yield 0;
            }
            default -> QUIC_STATUS_NOT_SUPPORTED;
        };
    }

    protected boolean requireEventLogging() {
        return Logger.debugOn();
    }

    protected void logEvent(QuicStreamEvent event) {
        Logger.alert("---------- " + Thread.currentThread() + " ----------");
        switch (event.getType()) {
            case QUIC_STREAM_EVENT_START_COMPLETE -> {
                Logger.alert("QUIC_STREAM_EVENT_START_COMPLETE");
                var data = event.getUnion().getSTART_COMPLETE();
                {
                    Logger.alert("Status: " + data.getStatus());
                    Logger.alert("ID: " + data.getID());
                    Logger.alert("PeerAccepted: " + data.isPeerAccepted());
                }
            }
            case QUIC_STREAM_EVENT_RECEIVE -> {
                Logger.alert("QUIC_STREAM_EVENT_RECEIVE");
                var data = event.getUnion().getRECEIVE();
                {
                    Logger.alert("AbsoluteOffset: " + data.getAbsoluteOffset());
                    Logger.alert("TotalBufferLength: " + data.getTotalBufferLength());
                    Logger.alert("Flags: " + data.getFlags());
                }
                {
                    int count = data.getBufferCount();
                    var bufMem = data.getBuffers().MEMORY;
                    bufMem = bufMem.reinterpret(QuicBuffer.LAYOUT.byteSize() * count);
                    var bufs = new QuicBuffer.Array(bufMem);
                    for (int i = 0; i < count; ++i) {
                        var buf = bufs.get(i);
                        var seg = buf.getBuffer().reinterpret(buf.getLength());
                        Logger.alert("Buffer[" + i + "]");
                        Utils.hexDump(seg);
                    }
                }
            }
            case QUIC_STREAM_EVENT_SEND_COMPLETE -> {
                Logger.alert("QUIC_STREAM_EVENT_SEND_COMPLETE");
                var data = event.getUnion().getSEND_COMPLETE();
                {
                    Logger.alert("Canceled: " + data.isCanceled());
                    Logger.alert("ClientContext: " + data.getClientContext());
                }
            }
            case QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN -> {
                Logger.alert("QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN");
                var data = event.getUnion().getPEER_SEND_ABORTED();
                {
                    Logger.alert("ErrorCode: " + data.getErrorCode());
                }
            }
            case QUIC_STREAM_EVENT_PEER_SEND_ABORTED -> {
                Logger.alert("QUIC_STREAM_EVENT_PEER_SEND_ABORTED");
                var data = event.getUnion().getPEER_SEND_ABORTED();
                {
                    Logger.alert("ErrorCode: " + data.getErrorCode());
                }
            }
            case QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED -> {
                Logger.alert("QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED");
                var data = event.getUnion().getPEER_RECEIVE_ABORTED();
                {
                    Logger.alert("ErrorCode: " + data.getErrorCode());
                }
            }
            case QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE -> {
                Logger.alert("QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE");
                var data = event.getUnion().getSEND_SHUTDOWN_COMPLETE();
                {
                    Logger.alert("Graceful: " + data.isGraceful());
                }
            }
            case QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE -> {
                Logger.alert("QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE");
                var data = event.getUnion().getSHUTDOWN_COMPLETE();
                {
                    Logger.alert("ConnectionShutdown: " + data.isConnectionShutdown());
                    Logger.alert("AppCloseInProgress: " + data.isAppCloseInProgress());
                    Logger.alert("ConnectionShutdownByApp: " + data.isConnectionShutdownByApp());
                    Logger.alert("ConnectionClosedRemotely: " + data.isConnectionClosedRemotely());
                    Logger.alert("ConnectionErrorCode: " + data.getConnectionErrorCode());
                    Logger.alert("ConnectionCloseStatus: " + data.getConnectionCloseStatus());
                }
            }
            case QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE -> {
                Logger.alert("QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE");
                var data = event.getUnion().getIDEAL_SEND_BUFFER_SIZE();
                {
                    Logger.alert("ByteCount: " + data.getByteCount());
                }
            }
            default -> Logger.alert("UNKNOWN STREAM EVENT: " + event.getType());
        }
    }

    public String toString() {
        return "Stream[id=" + id
               + " conn=" + opts.connection
               + "]@" + Long.toString(streamQ.getStream().address(), 16)
               + (isClosed() ? "[closed]" : "[open]");
    }
}
