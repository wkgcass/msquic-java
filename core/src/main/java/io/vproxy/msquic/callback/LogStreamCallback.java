package io.vproxy.msquic.callback;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.base.util.bytearray.MemorySegmentByteArray;
import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Stream;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public class LogStreamCallback implements StreamCallback {
    public static final boolean DEFAULT_VALUE_FOR_WITH_DATA = false;

    private final boolean withData;

    public LogStreamCallback() {
        this(DEFAULT_VALUE_FOR_WITH_DATA);
    }

    public LogStreamCallback(boolean withData) {
        this.withData = withData;
    }

    @Override
    public int startComplete(Stream stream, QuicStreamEventStartComplete data) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_START_COMPLETE: " + stream);
        Logger.trace(LogType.ALERT, "" + data);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int receive(Stream stream, QuicStreamEventReceive data) {
        if (!withData) {
            return QUIC_STATUS_NOT_SUPPORTED;
        }

        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_RECEIVE: " + stream);
        Logger.trace(LogType.ALERT, "" + data);
        int count = data.getBufferCount();
        var bufMem = data.getBuffers().MEMORY;
        bufMem = bufMem.reinterpret(QuicBuffer.LAYOUT.byteSize() * count);
        var bufs = new QuicBuffer.Array(bufMem);
        for (int i = 0; i < count; ++i) {
            var buf = bufs.get(i);
            var seg = buf.getBuffer().reinterpret(buf.getLength());
            Logger.trace(LogType.ALERT, "Buffer[" + i + "]\n" + new MemorySegmentByteArray(seg).hexDump());
        }
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int sendComplete(Stream stream, QuicStreamEventSendComplete data) {
        if (!withData) {
            return QUIC_STATUS_NOT_SUPPORTED;
        }

        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_SEND_COMPLETE: " + stream);
        Logger.trace(LogType.ALERT, "" + data);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerSendShutdown(Stream stream) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN: " + stream);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerSendAborted(Stream stream, QuicStreamEventPeerSendAborted data) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_PEER_SEND_ABORTED: " + stream);
        Logger.trace(LogType.ALERT, "" + data);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerReceiveAborted(Stream stream, QuicStreamEventPeerReceiveAborted data) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED: " + stream);
        Logger.trace(LogType.ALERT, "" + data);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int sendShutdownComplete(Stream stream, QuicStreamEventSendShutdownComplete data) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE: " + stream);
        Logger.trace(LogType.ALERT, "" + data);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int shutdownComplete(Stream stream, QuicStreamEventShutdownComplete data) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE: " + stream);
        Logger.trace(LogType.ALERT, "" + data);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int idealSendBufferSize(Stream stream, QuicStreamEventIdealSendBufferSize data) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE: " + stream);
        Logger.trace(LogType.ALERT, "" + data);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerAccepted(Stream stream) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT_PEER_ACCEPTED: " + stream);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int unknown(Stream stream, QuicStreamEvent event) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM_EVENT: UNKNOWN " + event.getType() + ": " + stream);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public void closed(Stream stream) {
        Logger.trace(LogType.ALERT, "QUIC_STREAM: CLOSED: " + stream);
    }
}
