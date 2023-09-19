package io.vproxy.msquic.sample;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.QuicStreamEvent;
import io.vproxy.msquic.wrap.Stream;

import static io.vproxy.msquic.MsQuicConsts.*;

public class SampleStream extends Stream {
    private final CommandLine cli;

    public SampleStream(CommandLine cli, Options opts) {
        super(opts);
        this.cli = cli;
    }

    private int receiveMethodType = 0; // 0: directly receive, 1: delay and receive

    @Override
    protected boolean requireEventLogging() {
        return true;
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    @Override
    public int callback(QuicStreamEvent event) {
        super.callback(event);
        return switch (event.getType()) {
            case QUIC_STREAM_EVENT_START_COMPLETE -> {
                var data = event.getUnion().getSTART_COMPLETE();
                if (data.getStatus() == 0) {
                    cli.registerStream(this);
                }
                yield 0;
            }
            case QUIC_STREAM_EVENT_RECEIVE -> {
                var data = event.getUnion().getRECEIVE();
                var len = data.getTotalBufferLength();
                if (len == 0 || (data.getFlags() & QUIC_RECEIVE_FLAG_FIN) != 0) {
                    Logger.alert("received FIN from remote");
                    yield 0;
                }
                if ((receiveMethodType++) % 2 == 0) {
                    yield 0;
                }
                new Thread(() -> {
                    Logger.warn(LogType.ALERT, "delay for a few seconds before calling StreamReceiveComplete");
                    try {
                        Thread.sleep(2_000);
                    } catch (InterruptedException ignore) {
                    }
                    Logger.warn(LogType.ALERT, "calling StreamReceiveComplete with len " + len);
                    streamQ.receiveComplete(len);
                }).start();
                data.setTotalBufferLength(0);
                yield QUIC_STATUS_PENDING;
            }
            case QUIC_STREAM_EVENT_SEND_COMPLETE -> 0;
            case QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN -> 0;
            case QUIC_STREAM_EVENT_PEER_SEND_ABORTED -> 0;
            case QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED -> 0;
            case QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE -> 0;
            case QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE -> {
                cli.removeStream(this);
                yield 0;
            }
            case QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE -> 0;
            default -> QUIC_STATUS_NOT_SUPPORTED;
        };
    }
}
