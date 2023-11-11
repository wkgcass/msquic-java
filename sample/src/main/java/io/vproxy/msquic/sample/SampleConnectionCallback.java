package io.vproxy.msquic.sample;

import io.vproxy.msquic.*;
import io.vproxy.msquic.callback.ConnectionCallback;
import io.vproxy.msquic.callback.LogStreamCallback;
import io.vproxy.msquic.callback.StreamCallbackList;
import io.vproxy.msquic.wrap.Connection;
import io.vproxy.msquic.wrap.Stream;
import io.vproxy.pni.Allocator;

class SampleConnectionCallback implements ConnectionCallback {
    private final CommandLine cli;

    public SampleConnectionCallback(CommandLine cli) {
        this.cli = cli;
    }

    @Override
    public int connected(Connection conn, QuicConnectionEventConnected data) {
        cli.registerConnection(conn);
        return 0;
    }

    @Override
    public int shutdownComplete(Connection conn, QuicConnectionEventConnectionShutdownComplete data) {
        cli.removeConnection(conn);
        return 0;
    }

    @Override
    public int peerStreamStarted(Connection conn, QuicConnectionEventPeerStreamStarted data) {
        var streamHQUIC = data.getStream();
        var allocator = Allocator.ofUnsafe();
        var stream_ = new QuicStream(allocator);
        {
            stream_.setApi(conn.opts.apiTableQ.getApi());
            stream_.setHandle(streamHQUIC);
        }
        var stream = new Stream(new Stream.Options(conn, allocator,
            new StreamCallbackList()
                .add(new LogStreamCallback())
                .add(new SampleStreamCallback(cli)),
            stream_));
        stream_.setCallbackHandler(MsQuicUpcall.streamCallback, stream.ref.MEMORY);
        cli.registerStream(stream);
        return 0;
    }

    @Override
    public int resumptionTicketReceived(Connection conn, QuicConnectionEventResumptionTicketReceived data) {
        var seg = data.getResumptionTicket().reinterpret(data.getResumptionTicketLength() & 0xffff);
        cli.registerResumptionTicket(conn.getRemoteAddress().formatToIPPortString(), seg);
        return 0;
    }
}
