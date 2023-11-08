package io.vproxy.msquic.callback;

import io.vproxy.base.util.ByteArray;
import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Connection;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIString;

import java.io.File;
import java.io.IOException;
import java.lang.foreign.ValueLayout;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public class LogConnectionCallback implements ConnectionCallback {
    protected Path getSSLKeyLogFilePath() {
        var v = System.getenv("SSLKEYLOGFILE");
        if (v == null)
            return null;
        if (!new File(v).isFile())
            return null;
        return Path.of(v);
    }

    @Override
    public int connected(Connection conn, QuicConnectionEventConnected data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_CONNECTED: \{conn}");
        Logger.alert(STR."\{data}");

        var SSLKEYLOGFILE = getSSLKeyLogFilePath();
        if (SSLKEYLOGFILE != null) {
            if (conn.getQuicTLSSecret() != null) {
                var s = conn.getQuicTLSSecret();
                var bytes = s.MEMORY.toArray(ValueLayout.JAVA_BYTE);
                Logger.alert(STR."QuicTLSSecret\n\{ByteArray.from(bytes).hexDump()}");
                try {
                    Files.writeString(SSLKEYLOGFILE,
                        MsQuicUtils.convertQuicTlsSecretToSSLKEYLOGFILE(s),
                        StandardOpenOption.APPEND);
                    Logger.alert(STR."SSLKEYLOGFILE: \{SSLKEYLOGFILE}");
                } catch (IOException e) {
                    Logger.error(LogType.FILE_ERROR, STR."failed to write SSLKEYLOGFILE: \{SSLKEYLOGFILE}", e);
                }
            } else {
                Logger.alert("QuicTLSSecret: null");
            }
        }
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int shutdownInitiatedByTransport(Connection conn, QuicConnectionEventShutdownInitiatedByTransport data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int shutdownInitiatedByPeer(Connection conn, QuicConnectionEventShutdownInitiatedByPeer data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int shutdownComplete(Connection conn, QuicConnectionEventConnectionShutdownComplete data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public int localAddressChanged(Connection conn, QuicConnectionEventLocalAddressChanged data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED: \{conn}");
        try (var tmpAlloc = Allocator.ofConfined()) {
            var addr = data.getAddress();
            var str = new PNIString(tmpAlloc.allocate(64));
            addr.toString(str);
            Logger.alert(STR."local address changed: \{addr}");
        }
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public int peerAddressChanged(Connection conn, QuicConnectionEventPeerAddressChanged data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED: \{conn}");
        try (var tmpAlloc = Allocator.ofConfined()) {
            var addr = data.getAddress();
            var str = new PNIString(tmpAlloc.allocate(64));
            addr.toString(str);
            Logger.alert(STR."peer address changed: \{addr}");
        }
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerStreamStarted(Connection conn, QuicConnectionEventPeerStreamStarted data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int streamsAvailable(Connection conn, QuicConnectionEventStreamsAvailable data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerNeedsStreams(Connection conn, QuicConnectionEventPeerNeedsStreams data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int idealProcessorChanged(Connection conn, QuicConnectionEventIdealProcessorChanged data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int datagramStateChanged(Connection conn, QuicConnectionEventDatagramStateChanged data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int datagramReceived(Connection conn, QuicConnectionEventDatagramReceived data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED: \{conn}");
        Logger.alert(STR."\{data}");

        var buffer = data.getBuffer();
        var seg = buffer.getBuffer().reinterpret(buffer.getLength());
        var bytes = seg.toArray(ValueLayout.JAVA_BYTE);
        Logger.alert(STR."Buffer\n\{ByteArray.from(bytes).hexDump()}");

        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int datagramSendStateChanged(Connection conn, QuicConnectionEventDatagramSendStateChanged data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int resumed(Connection conn, QuicConnectionEventResumed data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_RESUMED: \{conn}");
        Logger.alert(STR."\{data}");

        int len = data.getResumptionStateLength() & 0xffff;
        if (len == 0) {
            Logger.alert("ResumptionState: null");
        } else {
            var state = data.getResumptionState().reinterpret(len);
            var bytes = state.toArray(ValueLayout.JAVA_BYTE);
            Logger.alert(STR."ResumptionState\n\{ByteArray.from(bytes).hexDump()}");
        }
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int resumptionTicketReceived(Connection conn, QuicConnectionEventResumptionTicketReceived data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED: \{conn}");
        Logger.alert(STR."\{data}");

        var seg = data.getResumptionTicket().reinterpret(data.getResumptionTicketLength() & 0xffff);
        var bytes = seg.toArray(ValueLayout.JAVA_BYTE);
        Logger.alert(STR."ResumptionTicket\n\{ByteArray.from(bytes).hexDump()}");

        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int peerCertificateReceived(Connection conn, QuicConnectionEventPeerCertificateReceived data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_PEER_CERTIFICATE_RECEIVED: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int reliableResetNegotiated(Connection conn, QuicConnectionEventReliableResetNegotiated data) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT_RELIABLE_RESET_NEGOTIATED: \{conn}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int unknown(Connection conn, QuicConnectionEvent event) {
        Logger.alert(STR."QUIC_CONNECTION_EVENT: UNKNOWN \{event.getType()}: \{conn}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public void closed(Connection conn) {
        Logger.alert(STR."QUIC_CONNECTION: CLOSED: \{conn}");
    }
}
