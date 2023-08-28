package io.vproxy.msquic.sample;

import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Stream;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;

import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.*;

public class SampleStream extends Stream {
    private final CommandLine cli;

    public SampleStream(CommandLine cli, QuicApiTable table, QuicRegistration reg, QuicConnection conn, Allocator allocator, Function<PNIRef<Stream>, QuicStream> streamSupplier) {
        super(table, reg, conn, allocator, streamSupplier);
        this.cli = cli;
    }

    @Override
    public int callback(QuicStreamEvent event) {
        super.callback(event);
        return switch (event.getType()) {
            case QUIC_STREAM_EVENT_START_COMPLETE -> {
                System.out.println("QUIC_STREAM_EVENT_START_COMPLETE");
                var data = event.getUnion().getSTART_COMPLETE();
                if (data.getID() != -1) {
                    cli.registerStream(this);
                }
                {
                    System.out.println("Status: " + data.getStatus());
                    System.out.println("ID: " + data.getID());
                    System.out.println("PeerAccepted: " + data.getPeerAccepted());
                }
                yield 0;
            }
            case QUIC_STREAM_EVENT_RECEIVE -> {
                System.out.println("QUIC_STREAM_EVENT_RECEIVE");
                var data = event.getUnion().getRECEIVE();
                {
                    System.out.println("AbsoluteOffset: " + data.getAbsoluteOffset());
                    System.out.println("TotalBufferLength: " + data.getTotalBufferLength());
                    System.out.println("Flags: " + data.getFlags());
                }
                {
                    int count = data.getBufferCount();
                    var bufMem = data.getBuffers().MEMORY;
                    bufMem = bufMem.reinterpret(QuicBuffer.LAYOUT.byteSize() * count);
                    var bufs = new QuicBuffer.Array(bufMem);
                    for (int i = 0; i < count; ++i) {
                        var buf = bufs.get(i);
                        var seg = buf.getBuffer().reinterpret(buf.getLength());
                        System.out.println("Buffer[" + i + "]");
                        Utils.hexDump(seg);
                    }
                }
                yield 0;
            }
            case QUIC_STREAM_EVENT_SEND_COMPLETE -> {
                System.out.println("QUIC_STREAM_EVENT_SEND_COMPLETE");
                var data = event.getUnion().getSEND_COMPLETE();
                {
                    System.out.println("Canceled: " + data.getCanceled());
                    System.out.println("ClientContext: " + data.getClientContext());
                }
                yield 0;
            }
            case QUIC_STREAM_EVENT_PEER_SEND_ABORTED -> {
                System.out.println("QUIC_STREAM_EVENT_PEER_SEND_ABORTED");
                var data = event.getUnion().getPEER_SEND_ABORTED();
                {
                    System.out.println("ErrorCode: " + data.getErrorCode());
                }
                yield 0;
            }
            case QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED -> {
                System.out.println("QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED");
                var data = event.getUnion().getPEER_RECEIVE_ABORTED();
                {
                    System.out.println("ErrorCode: " + data.getErrorCode());
                }
                yield 0;
            }
            case QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE -> {
                System.out.println("QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE");
                var data = event.getUnion().getSEND_SHUTDOWN_COMPLETE();
                {
                    System.out.println("Graceful: " + data.getGraceful());
                }
                yield 0;
            }
            case QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE -> {
                System.out.println("QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE");
                cli.removeStream(this);
                var data = event.getUnion().getSHUTDOWN_COMPLETE();
                {
                    System.out.println("ConnectionShutdown: " + data.getConnectionShutdown());
                    System.out.println("AppCloseInProgress: " + data.getAppCloseInProgress());
                    System.out.println("ConnectionShutdownByApp: " + data.getConnectionShutdownByApp());
                    System.out.println("ConnectionClosedRemotely: " + data.getConnectionClosedRemotely());
                    System.out.println("ConnectionErrorCode: " + data.getConnectionErrorCode());
                    System.out.println("ConnectionCloseStatus: " + data.getConnectionCloseStatus());
                }
                yield 0;
            }
            case QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE -> {
                System.out.println("QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE");
                var data = event.getUnion().getIDEAL_SEND_BUFFER_SIZE();
                {
                    System.out.println("ByteCount: " + data.getByteCount());
                }
                // TODO
                yield 0;
            }
            default -> QUIC_STATUS_NOT_SUPPORTED;
        };
    }
}
