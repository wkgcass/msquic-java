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
    public final QuicApiTable apiTable;
    public final QuicRegistration registration;
    public final QuicConnection connection;
    private final Allocator allocator;
    public final QuicStream stream;

    protected final Set<Allocator> pendingAllocators = new HashSet<>();

    private long id;

    public Stream(QuicApiTable apiTable, QuicRegistration registration, QuicConnection connection, Allocator allocator,
                  Function<PNIRef<Stream>, QuicStream> streamSupplier) {
        this.ref = PNIRef.of(this);
        this.apiTable = apiTable;
        this.registration = registration;
        this.connection = connection;
        this.allocator = allocator;
        this.stream = streamSupplier.apply(ref);

        getIdFromStream(stream);
    }

    public Stream(QuicApiTable apiTable, QuicRegistration registration, QuicConnection connection, Allocator allocator,
                  QuicStream stream) {
        this.ref = PNIRef.of(this);
        this.apiTable = apiTable;
        this.registration = registration;
        this.connection = connection;
        this.allocator = allocator;
        this.stream = stream;

        this.canCallClose = false;

        getIdFromStream(stream);
    }

    private void getIdFromStream(QuicStream stream) {
        if (stream != null) {
            try (var tmpAllocator = Allocator.ofConfined()) {
                var idPtr = new LongArray(tmpAllocator, 1);
                var lenPtr = new IntArray(tmpAllocator, 1);
                apiTable.getParam(stream.getStream(), QUIC_PARAM_STREAM_ID, lenPtr, idPtr.MEMORY);
                id = idPtr.get(0);
            }
        }
    }

    public Set<Allocator> getPendingAllocators() {
        return pendingAllocators;
    }

    private volatile boolean closed = false;
    private volatile boolean streamIsClosed = false;
    private volatile boolean streamIsShutdown = false;
    private volatile boolean canCallClose = true;

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
        allocator.close();
        ref.close();
        for (var alloc : pendingAllocators) {
            alloc.close();
        }
        close0();
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
            if (stream != null) {
                stream.close();
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
        stream.shutdown(QUIC_STREAM_SHUTDOWN_FLAG_ABORT_RECEIVE |
                        QUIC_STREAM_SHUTDOWN_FLAG_ABORT_SEND, 0);
    }

    public int send(Allocator allocator, MemorySegment... dataArray) {
        return send(0, allocator, dataArray);
    }

    public int send(int flags, Allocator allocator, MemorySegment... dataArray) {
        if (dataArray.length == 0)
            return 0;
        var qbufArray = new QuicBuffer.Array(allocator, dataArray.length);
        for (int i = 0; i < dataArray.length; ++i) {
            var qbuf = qbufArray.get(i);
            var data = dataArray[i];
            qbuf.setLength((int) data.byteSize());
            qbuf.setBuffer(data);
        }
        int res = stream.send(qbufArray.get(0) /*address*/, dataArray.length, flags, PNIRef.of(allocator).MEMORY);
        if (res == 0) {
            pendingAllocators.add(allocator);
            return 0;
        }
        // failed
        allocator.close();
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
                    var allocatorRef = PNIRef.<Allocator>of(context);
                    var allocator = allocatorRef.getRef();
                    allocator.close();
                    pendingAllocators.remove(allocator);
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
                    Logger.alert("PeerAccepted: " + data.getPeerAccepted());
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
                    Logger.alert("Canceled: " + data.getCanceled());
                    Logger.alert("ClientContext: " + data.getClientContext());
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
                    Logger.alert("Graceful: " + data.getGraceful());
                }
            }
            case QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE -> {
                Logger.alert("QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE");
                var data = event.getUnion().getSHUTDOWN_COMPLETE();
                {
                    Logger.alert("ConnectionShutdown: " + data.getConnectionShutdown());
                    Logger.alert("AppCloseInProgress: " + data.getAppCloseInProgress());
                    Logger.alert("ConnectionShutdownByApp: " + data.getConnectionShutdownByApp());
                    Logger.alert("ConnectionClosedRemotely: " + data.getConnectionClosedRemotely());
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
        }
    }

    public String toString() {
        return "Stream[id=" + id
               + " conn=" + Long.toString(connection.getConn().address(), 16)
               + "]@" + Long.toString(stream.getStream().address(), 16);
    }
}
