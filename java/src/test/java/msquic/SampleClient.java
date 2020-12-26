package msquic;

import msquic.nativevalues.*;

import java.nio.ByteBuffer;
import java.util.Set;

public class SampleClient {
    private static Registration reg = null;
    private static Configuration conf = null;
    private static Connection conn = null;
    private static Stream stream = null;

    public static void main(String[] args) throws Exception {
        // { // this is optional
        //     MsQuic.setMemoryAllocator(new Utils.DirectByteBufferMemoryAllocator());
        // }
        MsQuic msquic = MsQuic.open();
        try {
            reg = msquic.openRegistration(new RegistrationConfig()
                .setAppName("quicsample")
                .setProfile(ExecutionProfile.LOW_LATENCY));
            conf = reg.openConfiguration(Set.of("sample"), new Settings()
                .setIdleTimeoutMs(1000));
            conf.initAsClient(true);
            conn = reg.openConnection(event -> {
                switch (event.type) {
                    case CONNECTED:
                        System.out.println("[conn][" + conn + "] Connected: " +
                            "local=" + conn.getLocalAddress() + ", " +
                            "remote=" + conn.getRemoteAddress());
                        System.out.println("[conn][" + conn + "] " +
                            "Negotiated alpn: " + event.CONNECTED.negotiatedAlpn + ", " +
                            "Session resumed: " + event.CONNECTED.sessionResumed);
                        try {
                            clientSend();
                        } catch (MsQuicException e) {
                            conn.close();
                            throw e;
                        }
                        break;
                    case SHUTDOWN_INITIATED_BY_TRANSPORT:
                        System.out.println("[conn][" + conn + "] Shut down by transport");
                        break;
                    case SHUTDOWN_INITIATED_BY_PEER:
                        System.out.println("[conn][" + conn + "] Shut down by peer");
                        break;
                    case SHUTDOWN_COMPLETE:
                        System.out.println("[conn][" + conn + "] All done");
                        if (!event.SHUTDOWN_COMPLETE.appCloseInProgress) {
                            conn.close();
                        }
                        break;
                    default:
                        break;
                }
            });
            System.out.println("[conn][" + conn + "] Connecting...");
            try {
                conn.start(conf, AddressFamily.UNSPEC, "127.0.0.1", 4567);
            } catch (MsQuicException e) {
                conn.close();
                throw e;
            }
        } finally {
            if (conf != null) {
                conf.close();
            }
            if (reg != null) {
                reg.close();
            }
            msquic.close();
            // { // this is optional
            //     MsQuic.UNSAFE_release();
            // }
        }
    }

    private static void clientSend() throws MsQuicException {
        try {
            stream = conn.openStream(StreamOpenFlags.NONE, event -> {
                switch (event.type) {
                    case START_COMPLETE:
                        System.out.println("[strm][" + stream + "] Stream started: " + stream.getId());
                        break;
                    case SEND_COMPLETE:
                        System.out.println("[strm][" + stream + "] Data sent");
                        Utils.U.invokeCleaner(stream.pollWBuf());
                        break;
                    case RECEIVE:
                        System.out.println("[strm][" + stream + "] Data received");
                        int len = (int) event.RECEIVE.getTotalBufferLength();
                        System.out.println("[strm][" + stream + "] Received bytes: " + len);

                        ByteBuffer rcvBuf = ByteBuffer.allocateDirect(len);
                        for (QuicBuffer qbuf : event.RECEIVE.buffers) {
                            qbuf.read(rcvBuf);
                        }
                        rcvBuf.flip();
                        byte[] rcvByteArray = new byte[len];
                        rcvBuf.get(rcvByteArray);
                        Utils.U.invokeCleaner(rcvBuf);
                        System.out.println("[strm][" + stream + "] Received data: " + new String(rcvByteArray));

                        break;
                    case PEER_SEND_ABORTED:
                        System.out.println("[strm][" + stream + "] Peer aborted");
                        break;
                    case PEER_SEND_SHUTDOWN:
                        System.out.println("[strm][" + stream + "] Peer shut down");
                        break;
                    case SHUTDOWN_COMPLETE:
                        System.out.println("[strm][" + stream + "] All done");
                        stream.close();
                        break;
                    default:
                        break;
                }
            });
        } catch (MsQuicException e) {
            System.out.println("StreamOpen failed, " + stream + "!");
            throw e;
        }
        System.out.println("[strm][" + stream + "] Starting...");
        try {
            stream.start(StreamStartFlags.NONE);
        } catch (MsQuicException e) {
            System.out.println("StreamStart failed, " + stream + "!");
            stream.close();
            throw e;
        }
        String sndStr = Utils.randomStr(100);
        System.out.println("[strm][" + stream + "] Sending data: " + sndStr);
        ByteBuffer buf = ByteBuffer.allocateDirect(100);
        byte[] bytes = sndStr.getBytes();
        buf.put(bytes);
        buf.flip();
        try {
            stream.send(SendFlags.FIN, buf);
        } catch (MsQuicException e) {
            System.out.println("StreamSend failed: " + e.status);
            Utils.U.invokeCleaner(buf);
        }
    }
}
