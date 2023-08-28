package io.vproxy.msquic.sample;

import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Connection;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;
import io.vproxy.pni.PNIString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.*;

class SampleConnection extends Connection {
    private final CommandLine cli;

    public SampleConnection(CommandLine cli, QuicApiTable table, QuicRegistration reg, Allocator allocator, Function<PNIRef<Connection>, QuicConnection> connectionSupplier) {
        super(table, reg, allocator, connectionSupplier);
        this.cli = cli;
    }

    @Override
    public int callback(QuicConnectionEvent event) {
        super.callback(event);
        return switch (event.getType()) {
            case QUIC_CONNECTION_EVENT_CONNECTED -> {
                System.out.println("QUIC_CONNECTION_EVENT_CONNECTED");
                cli.registerConnection(this);
                var data = event.getUnion().getCONNECTED();
                {
                    var negotiatedAlpn = data.getNegotiatedAlpn().reinterpret(data.getNegotiatedAlpnLength());
                    System.out.println("NegotiatedAlpn");
                    Utils.hexDump(negotiatedAlpn);
                }
                {
                    System.out.println("SessionResumed: " + data.getSessionResumed());
                }
                if (getQuicTLSSecret() != null) {
                    var s = getQuicTLSSecret();
                    System.out.println("QuicTLSSecret");
                    Utils.hexDump(s.MEMORY);
                    try {
                        Files.writeString(cli.quicTlsSecretLogFilePath,
                            MsQuicUtils.convertQuicTlsSecretToSSLKEYLOGFILE(s),
                            StandardOpenOption.APPEND);
                        System.out.println("SSLKEYLOGFILE: " + cli.quicTlsSecretLogFilePath);
                    } catch (IOException ignore) {
                    }
                } else {
                    System.out.println("QuicTLSSecret: null");
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT -> {
                System.out.println("QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT");
                var data = event.getUnion().getSHUTDOWN_INITIATED_BY_TRANSPORT();
                {
                    System.out.println("Status: " + data.getStatus());
                    System.out.println("ErrorCode: " + data.getErrorCode());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER -> {
                System.out.println("QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER");
                var data = event.getUnion().getSHUTDOWN_INITIATED_BY_PEER();
                {
                    System.out.println("ErrorCode: " + data.getErrorCode());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE -> {
                System.out.println("QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE");
                cli.removeConnection(this);
                var data = event.getUnion().getSHUTDOWN_COMPLETE();
                {
                    System.out.println("HandshakeCompleted: " + data.getHandshakeCompleted());
                    System.out.println("PeerAcknowledgedShutdown: " + data.getPeerAcknowledgedShutdown());
                    System.out.println("AppCloseInProgress: " + data.getAppCloseInProgress());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED -> {
                System.out.println("QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED");
                var data = event.getUnion().getLOCAL_ADDRESS_CHANGED();
                try (var tmpAlloc = Allocator.ofConfined()) {
                    var addr = data.getAddress();
                    var str = new PNIString(tmpAlloc.allocate(64));
                    addr.toString(str);
                    System.out.println("local address changed: " + addr);
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED -> {
                System.out.println("QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED");
                var data = event.getUnion().getPEER_ADDRESS_CHANGED();
                try (var tmpAlloc = Allocator.ofConfined()) {
                    var addr = data.getAddress();
                    var str = new PNIString(tmpAlloc.allocate(64));
                    addr.toString(str);
                    System.out.println("peer address changed: " + addr);
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED -> {
                System.out.println("QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED");
                var data = event.getUnion().getPEER_STREAM_STARTED();
                {
                    System.out.println("Flags: " + data.getFlags());
                }
                var streamHQUIC = data.getStream();
                var allocator = Allocator.ofUnsafe();
                var stream_ = new QuicStream(allocator);
                stream_.setApi(apiTable.getApi());
                stream_.setStream(streamHQUIC);
                var stream = new SampleStream(cli, apiTable, registration, connection, allocator, ref -> {
                    apiTable.setCallbackHandler(streamHQUIC, MsQuicUpcall.streamCallback, ref.MEMORY);
                    return stream_;
                });
                cli.registerStream(stream);
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE -> {
                System.out.println("QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE");
                var data = event.getUnion().getSTREAMS_AVAILABLE();
                {
                    System.out.println("BidirectionalCount: " + data.getBidirectionalCount());
                    System.out.println("UnidirectionalCount: " + data.getUnidirectionalCount());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS -> {
                System.out.println("QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS");
                var data = event.getUnion().getPEER_NEEDS_STREAMS();
                {
                    System.out.println("Bidirectional: " + data.getBidirectional());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED -> {
                System.out.println("QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED");
                var data = event.getUnion().getIDEAL_PROCESSOR_CHANGED();
                {
                    System.out.println("IdealProcessor: " + data.getIdealProcessor());
                    System.out.println("PartitionIndex: " + data.getPartitionIndex());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED -> {
                System.out.println("QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED");
                var data = event.getUnion().getDATAGRAM_STATE_CHANGED();
                {
                    System.out.println("SendEnabled: " + data.getSendEnabled());
                    System.out.println("MaxSendLength: " + data.getMaxSendLength());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED -> {
                System.out.println("QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED");
                var data = event.getUnion().getDATAGRAM_RECEIVED();
                {
                    var buffer = data.getBuffer();
                    var seg = buffer.getBuffer().reinterpret(buffer.getLength());
                    System.out.println("Buffer");
                    Utils.hexDump(seg);
                }
                {
                    System.out.println("Flags: " + data.getFlags());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED -> {
                System.out.println("QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED");
                var data = event.getUnion().getDATAGRAM_SEND_STATE_CHANGED();
                {
                    System.out.println("ClientContext: " + data.getClientContext());
                    System.out.println("State: " + data.getState());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_RESUMED -> {
                System.out.println("QUIC_CONNECTION_EVENT_RESUMED");
                var data = event.getUnion().getRESUMED();
                {
                    int len = data.getResumptionStateLength() & 0xffff;
                    if (len == 0) {
                        System.out.println("ResumptionState: null");
                    } else {
                        var state = data.getResumptionState().reinterpret(len);
                        System.out.println("ResumptionState");
                        Utils.hexDump(state);
                    }
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED -> {
                System.out.println("QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED");
                var data = event.getUnion().getRESUMPTION_TICKET_RECEIVED();
                {
                    var seg = data.getResumptionTicket().reinterpret(data.getResumptionTicketLength() & 0xffff);
                    System.out.println("ResumptionTicket");
                    Utils.hexDump(seg);
                    cli.registerResumptionTicket(getRemoteAddress().formatToIPPortString(), seg);
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_PEER_CERTIFICATE_RECEIVED -> {
                System.out.println("QUIC_CONNECTION_EVENT_PEER_CERTIFICATE_RECEIVED");
                var data = event.getUnion().getPEER_CERTIFICATE_RECEIVED();
                {
                    System.out.println("Certificate: " + data.getCertificate());
                    System.out.println("DeferredErrorFlags: " + data.getDeferredErrorFlags());
                    System.out.println("DeferredStatus: " + data.getDeferredStatus());
                    System.out.println("Chain: " + data.getChain());
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_RELIABLE_RESET_NEGOTIATED -> {
                System.out.println("QUIC_CONNECTION_EVENT_RELIABLE_RESET_NEGOTIATED");
                var data = event.getUnion().getRELIABLE_RESET_NEGOTIATED();
                {
                    System.out.println("IsNegotiated: " + data.getIsNegotiated());
                }
                yield 0;
            }
            default -> QUIC_STATUS_NOT_SUPPORTED;
        };
    }
}
