package io.vproxy.msquic.callback;

import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Connection;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public interface ConnectionCallback {
    default boolean remove(Connection conn) {
        return false;
    }

    default int connected(Connection conn, QuicConnectionEventConnected data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int shutdownInitiatedByTransport(Connection conn, QuicConnectionEventShutdownInitiatedByTransport data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int shutdownInitiatedByPeer(Connection conn, QuicConnectionEventShutdownInitiatedByPeer data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int shutdownComplete(Connection conn, QuicConnectionEventConnectionShutdownComplete data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int localAddressChanged(Connection conn, QuicConnectionEventLocalAddressChanged data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int peerAddressChanged(Connection conn, QuicConnectionEventPeerAddressChanged data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int peerStreamStarted(Connection conn, QuicConnectionEventPeerStreamStarted data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int streamsAvailable(Connection conn, QuicConnectionEventStreamsAvailable data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int peerNeedsStreams(Connection conn, QuicConnectionEventPeerNeedsStreams data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int idealProcessorChanged(Connection conn, QuicConnectionEventIdealProcessorChanged data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int datagramStateChanged(Connection conn, QuicConnectionEventDatagramStateChanged data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int datagramReceived(Connection conn, QuicConnectionEventDatagramReceived data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int datagramSendStateChanged(Connection conn, QuicConnectionEventDatagramSendStateChanged data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int resumed(Connection conn, QuicConnectionEventResumed data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int resumptionTicketReceived(Connection conn, QuicConnectionEventResumptionTicketReceived data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int peerCertificateReceived(Connection conn, QuicConnectionEventPeerCertificateReceived data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int reliableResetNegotiated(Connection conn, QuicConnectionEventReliableResetNegotiated data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int unknown(Connection conn, QuicConnectionEvent event) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default void closed(Connection conn) {
    }
}
