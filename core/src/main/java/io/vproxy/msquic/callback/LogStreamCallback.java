package io.vproxy.msquic.callback;

import io.vproxy.base.util.ByteArray;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Stream;

import java.lang.foreign.ValueLayout;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public class LogStreamCallback implements StreamCallback {
    @Override
    public int startComplete(Stream stream, QuicStreamEventStartComplete data) {
        Logger.alert(STR."QUIC_STREAM_EVENT_START_COMPLETE: \{stream}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int receive(Stream stream, QuicStreamEventReceive data) {
        Logger.alert(STR."QUIC_STREAM_EVENT_RECEIVE: \{stream}");
        Logger.alert(STR."\{data}");
        int count = data.getBufferCount();
        var bufMem = data.getBuffers().MEMORY;
        bufMem = bufMem.reinterpret(QuicBuffer.LAYOUT.byteSize() * count);
        var bufs = new QuicBuffer.Array(bufMem);
        for (int i = 0; i < count; ++i) {
            var buf = bufs.get(i);
            var seg = buf.getBuffer().reinterpret(buf.getLength());
            var bytes = seg.toArray(ValueLayout.JAVA_BYTE);
            Logger.alert(STR."Buffer[\{i}]\n\{ByteArray.from(bytes).hexDump()}");
        }
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int sendComplete(Stream stream, QuicStreamEventSendComplete data) {
        Logger.alert(STR."QUIC_STREAM_EVENT_SEND_COMPLETE: \{stream}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerSendShutdown(Stream stream) {
        Logger.alert(STR."QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN: \{stream}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerSendAborted(Stream stream, QuicStreamEventPeerSendAborted data) {
        Logger.alert(STR."QUIC_STREAM_EVENT_PEER_SEND_ABORTED: \{stream}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerReceiveAborted(Stream stream, QuicStreamEventPeerReceiveAborted data) {
        Logger.alert(STR."QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED: \{stream}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int sendShutdownComplete(Stream stream, QuicStreamEventSendShutdownComplete data) {
        Logger.alert(STR."QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE: \{stream}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int shutdownComplete(Stream stream, QuicStreamEventShutdownComplete data) {
        Logger.alert(STR."QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE: \{stream}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int idealSendBufferSize(Stream stream, QuicStreamEventIdealSendBufferSize data) {
        Logger.alert(STR."QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE: \{stream}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerAccepted(Stream stream) {
        Logger.alert(STR."QUIC_STREAM_EVENT_PEER_ACCEPTED: \{stream}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int unknown(Stream stream, QuicStreamEvent event) {
        Logger.alert(STR."QUIC_STREAM_EVENT: UNKNOWN \{event.getType()}: \{stream}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public void closed(Stream stream) {
        Logger.alert(STR."QUIC_STREAM: CLOSED: \{stream}");
    }
}
