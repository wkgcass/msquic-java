package io.vproxy.msquic.sample;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.QuicStreamEventReceive;
import io.vproxy.msquic.QuicStreamEventShutdownComplete;
import io.vproxy.msquic.QuicStreamEventStartComplete;
import io.vproxy.msquic.callback.StreamCallback;
import io.vproxy.msquic.wrap.Stream;

import static io.vproxy.msquic.MsQuicConsts.QUIC_RECEIVE_FLAG_FIN;
import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_PENDING;

public class SampleStreamCallback implements StreamCallback {
    private final CommandLine cli;

    public SampleStreamCallback(CommandLine cli) {
        this.cli = cli;
    }

    private int receiveMethodType = 0; // 0: directly receive, 1: delay and receive

    @Override
    public int startComplete(Stream stream, QuicStreamEventStartComplete data) {
        if (data.getStatus() == 0) {
            cli.registerStream(stream);
        }
        return 0;
    }

    @Override
    public int receive(Stream stream, QuicStreamEventReceive data) {
        var len = data.getTotalBufferLength();
        if (len == 0 || (data.getFlags() & QUIC_RECEIVE_FLAG_FIN) != 0) {
            Logger.alert("received FIN from remote");
            return 0;
        }
        if ((receiveMethodType++) % 2 == 0) {
            return 0;
        }
        new Thread(() -> {
            Logger.warn(LogType.ALERT, "delay for a few seconds before calling StreamReceiveComplete");
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException ignore) {
            }
            Logger.warn(LogType.ALERT, STR."calling StreamReceiveComplete with len \{len}");
            stream.streamQ.receiveComplete(len);
        }).start();
        data.setTotalBufferLength(0);
        return QUIC_STATUS_PENDING;
    }

    @Override
    public int shutdownComplete(Stream stream, QuicStreamEventShutdownComplete data) {
        cli.removeStream(stream);
        return 0;
    }
}
