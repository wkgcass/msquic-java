package io.vproxy.msquic.callback;

import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Connection;

import java.util.ArrayList;
import java.util.List;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public class ConnectionCallbackList implements ConnectionCallback {
    private final List<ConnectionCallback> callbacks = new ArrayList<>(4);

    public static ConnectionCallbackList withLog(ConnectionCallback cb) {
        return withLog(cb, LogConnectionCallback.DEFAULT_VALUE_FOR_WITH_DETAIL);
    }

    public static ConnectionCallbackList withLog(ConnectionCallback cb, boolean withDetail) {
        if (cb instanceof ConnectionCallbackList cbls) {
            cbls.callbacks.addFirst(new LogConnectionCallback(withDetail));
            return cbls;
        } else {
            return new ConnectionCallbackList()
                .add(new LogConnectionCallback(withDetail))
                .add(cb);
        }
    }

    public static ConnectionCallback withLogIf(boolean checkRes, ConnectionCallback cb) {
        return withLogIf(checkRes, cb, LogConnectionCallback.DEFAULT_VALUE_FOR_WITH_DETAIL);
    }

    public static ConnectionCallback withLogIf(boolean checkRes, ConnectionCallback cb, boolean withDetail) {
        if (checkRes) {
            return withLog(cb, withDetail);
        }
        return cb;
    }

    public ConnectionCallbackList add(ConnectionCallback cb) {
        callbacks.add(cb);
        return this;
    }

    private boolean invalidState(int state) {
        return state != 0 && state != QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int connected(Connection conn, QuicConnectionEventConnected data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.connected(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int shutdownInitiatedByTransport(Connection conn, QuicConnectionEventShutdownInitiatedByTransport data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.shutdownInitiatedByTransport(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int shutdownInitiatedByPeer(Connection conn, QuicConnectionEventShutdownInitiatedByPeer data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.shutdownInitiatedByPeer(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int shutdownComplete(Connection conn, QuicConnectionEventConnectionShutdownComplete data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.shutdownComplete(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int localAddressChanged(Connection conn, QuicConnectionEventLocalAddressChanged data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.localAddressChanged(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int peerAddressChanged(Connection conn, QuicConnectionEventPeerAddressChanged data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.peerAddressChanged(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int peerStreamStarted(Connection conn, QuicConnectionEventPeerStreamStarted data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.peerStreamStarted(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int streamsAvailable(Connection conn, QuicConnectionEventStreamsAvailable data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.streamsAvailable(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int peerNeedsStreams(Connection conn, QuicConnectionEventPeerNeedsStreams data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.peerNeedsStreams(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int idealProcessorChanged(Connection conn, QuicConnectionEventIdealProcessorChanged data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.idealProcessorChanged(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int datagramStateChanged(Connection conn, QuicConnectionEventDatagramStateChanged data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.datagramStateChanged(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int datagramReceived(Connection conn, QuicConnectionEventDatagramReceived data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.datagramReceived(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int datagramSendStateChanged(Connection conn, QuicConnectionEventDatagramSendStateChanged data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.datagramSendStateChanged(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int resumed(Connection conn, QuicConnectionEventResumed data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.resumed(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int resumptionTicketReceived(Connection conn, QuicConnectionEventResumptionTicketReceived data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.resumptionTicketReceived(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int peerCertificateReceived(Connection conn, QuicConnectionEventPeerCertificateReceived data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.peerCertificateReceived(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int reliableResetNegotiated(Connection conn, QuicConnectionEventReliableResetNegotiated data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.reliableResetNegotiated(conn, data);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int unknown(Connection conn, QuicConnectionEvent event) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.unknown(conn, event);
            if (cb.remove(conn)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public void closed(Connection conn) {
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            cb.closed(conn);
            if (cb.remove(conn)) {
                ite.remove();
            }
        }
    }
}
