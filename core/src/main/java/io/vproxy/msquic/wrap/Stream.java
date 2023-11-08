package io.vproxy.msquic.wrap;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.QuicBuffer;
import io.vproxy.msquic.QuicStream;
import io.vproxy.msquic.QuicStreamEvent;
import io.vproxy.msquic.callback.StreamCallback;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;
import io.vproxy.pni.array.IntArray;
import io.vproxy.pni.array.LongArray;

import java.lang.foreign.MemorySegment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.*;

public class Stream {
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
            // opts.streamQ != null means it's accepted
            this.canCallClose = false;
            getIdFromStream(streamQ);
        }
    }

    public int start(int flags) {
        int res = streamQ.start(flags);
        if (res == 0) {
            canCallClose = false;
        }
        return res;
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
        public final StreamCallback callback;

        public Options(Connection connection, Allocator allocator, StreamCallback callback, QuicStream streamQ) {
            super(connection, streamQ);
            this.allocator = allocator;
            this.callback = callback;
            this.streamSupplier = null;
        }

        public Options(Connection connection, Allocator allocator, StreamCallback callback, Function<PNIRef<Stream>, QuicStream> streamSupplier) {
            super(connection);
            this.allocator = allocator;
            this.callback = callback;
            this.streamSupplier = streamSupplier;
        }
    }

    private void getIdFromStream(QuicStream stream) {
        if (stream != null) {
            try (var tmpAllocator = Allocator.ofConfined()) {
                var idPtr = new LongArray(tmpAllocator, 1);
                var lenPtr = new IntArray(tmpAllocator, 1);
                lenPtr.set(0, 8);
                opts.apiTable.opts.apiTableQ.getParam(stream.getStream(), QUIC_PARAM_STREAM_ID, lenPtr, idPtr.MEMORY);
                id = idPtr.get(0);
            }
        }
    }

    public Set<SendContext> getPendingSendContexts() {
        return pendingSendContexts;
    }

    private volatile boolean closed = false;
    private volatile boolean streamCloseIsCalled = false;
    private volatile boolean streamShutdownIsCalled = false;
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
            if (canCallClose) {
                closed = true;
            }
        }

        shutdown();
        if (!canCallClose) {
            return;
        }
        opts.allocator.close();
        ref.close();
        if (!pendingSendContexts.isEmpty()) {
            for (var ctx : new ArrayList<>(pendingSendContexts)) {
                finish(ctx, false);
            }
        }
        opts.callback.closed(this);
    }

    private void finish(SendContext ctx, boolean succeeded) {
        pendingSendContexts.remove(ctx);
        ctx.onSendComplete.onSendComplete(ctx, succeeded);
    }

    /**
     * Call `close` if allowed.
     * If `close` is not allowed to be called, then call `shutdown` instead.
     * If `close` is called, `shutdown` will not be called.
     * But if `shutdown` is called, `close` still can be called by re-invoking this method.
     * `close` and `shutdown` would each only be called once.
     */
    protected void shutdown() {
        if (streamCloseIsCalled) {
            return;
        }
        synchronized (this) {
            if (streamCloseIsCalled) {
                return;
            }
            if (canCallClose) {
                streamCloseIsCalled = true;
            }
        }
        if (canCallClose) {
            if (streamQ != null) {
                streamQ.close();
            }
            return;
        }
        if (streamShutdownIsCalled) {
            return;
        }
        synchronized (this) {
            if (streamShutdownIsCalled) {
                return;
            }
            streamShutdownIsCalled = true;
        }
        if (streamQ == null) {
            return;
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
        if (dataArray.length == 0 && flags == QUIC_SEND_FLAG_NONE)
            return 0;
        QuicBuffer.Array qbufArray = null;
        if (dataArray.length > 0) {
            qbufArray = new QuicBuffer.Array(ctx.allocator, dataArray.length);
            for (int i = 0; i < dataArray.length; ++i) {
                var qbuf = qbufArray.get(i);
                var data = dataArray[i];
                qbuf.setLength((int) data.byteSize());
                qbuf.setBuffer(data);
            }
        }
        QuicBuffer qBufAddr = qbufArray == null ? null : qbufArray.get(0);
        int res = streamQ.send(qBufAddr, dataArray.length, flags, PNIRef.of(ctx).MEMORY);
        if (res == 0) {
            pendingSendContexts.add(ctx);
            return 0;
        }
        // failed
        finish(ctx, false);
        return res;
    }

    public int sendFin() {
        return streamQ.send(null, 0, QUIC_SEND_FLAG_FIN, null);
    }

    // need to override
    public int callback(QuicStreamEvent event) {
        return switch (event.getType()) {
            case QUIC_STREAM_EVENT_START_COMPLETE -> {
                var data = event.getUnion().getSTART_COMPLETE();
                if (data.getStatus() != 0) {
                    Logger.error(LogType.CONN_ERROR, STR."StreamStart failed, status: \{data.getStatus()}, stream: \{this}");
                    canCallClose = true;
                    shutdown();
                    yield 0;
                }
                id = data.getID();

                int status = opts.callback.startComplete(this, data);
                if (status == QUIC_STATUS_NOT_SUPPORTED) {
                    status = 0;
                }
                yield status;
            }
            case QUIC_STREAM_EVENT_RECEIVE -> {
                var data = event.getUnion().getRECEIVE();
                yield opts.callback.receive(this, data);
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
                var status = opts.callback.sendComplete(this, data);
                if (status == QUIC_STATUS_NOT_SUPPORTED) {
                    status = 0;
                }
                yield status;
            }
            case QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN -> opts.callback.peerSendShutdown(this);
            case QUIC_STREAM_EVENT_PEER_SEND_ABORTED -> {
                var data = event.getUnion().getPEER_SEND_ABORTED();
                yield opts.callback.peerSendAborted(this, data);
            }
            case QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED -> {
                var data = event.getUnion().getPEER_RECEIVE_ABORTED();
                yield opts.callback.peerReceiveAborted(this, data);
            }
            case QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE -> {
                var data = event.getUnion().getSEND_SHUTDOWN_COMPLETE();
                yield opts.callback.sendShutdownComplete(this, data);
            }
            case QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE -> {
                var data = event.getUnion().getSHUTDOWN_COMPLETE();
                var status = opts.callback.shutdownComplete(this, data);
                if (status == QUIC_STATUS_NOT_SUPPORTED) {
                    status = 0;
                }
                canCallClose = true;
                close();
                yield status;
            }
            case QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE -> {
                var data = event.getUnion().getIDEAL_SEND_BUFFER_SIZE();
                yield opts.callback.idealSendBufferSize(this, data);
            }
            case QUIC_STREAM_EVENT_PEER_ACCEPTED -> opts.callback.peerAccepted(this);
            default -> opts.callback.unknown(this, event);
        };
    }

    @SuppressWarnings("StringTemplateMigration")
    public String toString() {
        return "Stream[id=" + id
               + " conn=" + opts.connection
               + "]@" + Long.toString(streamQ.getStream().address(), 16)
               + (isClosed() ? "[closed]" : "[open]");
    }
}
