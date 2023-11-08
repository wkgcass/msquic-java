package io.vproxy.msquic.callback;

import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Stream;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public interface StreamCallback {
    default boolean remove(Stream stream) {
        return false;
    }

    default int startComplete(Stream stream, QuicStreamEventStartComplete data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int receive(Stream stream, QuicStreamEventReceive data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int sendComplete(Stream stream, QuicStreamEventSendComplete data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int peerSendShutdown(Stream stream) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int peerSendAborted(Stream stream, QuicStreamEventPeerSendAborted data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int peerReceiveAborted(Stream stream, QuicStreamEventPeerReceiveAborted data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int sendShutdownComplete(Stream stream, QuicStreamEventSendShutdownComplete data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int shutdownComplete(Stream stream, QuicStreamEventShutdownComplete data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int idealSendBufferSize(Stream stream, QuicStreamEventIdealSendBufferSize data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int peerAccepted(Stream stream) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int unknown(Stream stream, QuicStreamEvent event) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default void closed(Stream stream) {
    }
}
