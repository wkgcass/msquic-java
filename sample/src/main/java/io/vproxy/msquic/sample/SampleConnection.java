package io.vproxy.msquic.sample;

import io.vproxy.msquic.MsQuicUpcall;
import io.vproxy.msquic.QuicConnectionEvent;
import io.vproxy.msquic.QuicStream;
import io.vproxy.msquic.wrap.Connection;
import io.vproxy.msquic.wrap.Stream;
import io.vproxy.pni.Allocator;

import java.nio.file.Path;

import static io.vproxy.msquic.MsQuicConsts.*;

class SampleConnection extends Connection {
    private final CommandLine cli;

    public SampleConnection(CommandLine cli, Options opts) {
        super(opts);
        this.cli = cli;
    }

    @Override
    protected boolean requireEventLogging() {
        return true;
    }

    @Override
    protected Path getSSLKeyLogFilePath() {
        return cli.quicTlsSecretLogFilePath;
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    @Override
    public int callback(QuicConnectionEvent event) {
        super.callback(event);
        return switch (event.getType()) {
            case QUIC_CONNECTION_EVENT_CONNECTED -> {
                cli.registerConnection(this);
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT -> 0;
            case QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER -> 0;
            case QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE -> {
                cli.removeConnection(this);
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED -> 0;
            case QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED -> 0;
            case QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED -> {
                var data = event.getUnion().getPEER_STREAM_STARTED();
                var streamHQUIC = data.getStream();
                var allocator = Allocator.ofUnsafe();
                var stream_ = new QuicStream(allocator);
                stream_.setApi(opts.apiTableQ.getApi());
                stream_.setStream(streamHQUIC);
                var stream = new SampleStream(cli, new Stream.Options(this, allocator, stream_));
                opts.apiTableQ.setCallbackHandler(streamHQUIC, MsQuicUpcall.streamCallback, stream.ref.MEMORY);
                cli.registerStream(stream);
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE -> 0;
            case QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS -> 0;
            case QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED -> 0;
            case QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED -> 0;
            case QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED -> 0;
            case QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED -> 0;
            case QUIC_CONNECTION_EVENT_RESUMED -> 0;
            case QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED -> {
                var data = event.getUnion().getRESUMPTION_TICKET_RECEIVED();
                {
                    var seg = data.getResumptionTicket().reinterpret(data.getResumptionTicketLength() & 0xffff);
                    cli.registerResumptionTicket(getRemoteAddress().formatToIPPortString(), seg);
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_PEER_CERTIFICATE_RECEIVED -> 0;
            case QUIC_CONNECTION_EVENT_RELIABLE_RESET_NEGOTIATED -> 0;
            default -> QUIC_STATUS_NOT_SUPPORTED;
        };
    }
}
