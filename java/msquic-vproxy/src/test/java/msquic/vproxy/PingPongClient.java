package msquic.vproxy;

import msquic.*;
import msquic.nativevalues.ExecutionProfile;
import vfd.IPPort;
import vfd.SocketFD;
import vproxybase.connection.*;
import vproxybase.connection.Connection;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.util.*;

import java.io.IOException;
import java.util.Set;

public class PingPongClient {
    public static void main(String[] args) throws Exception {
        SelectorEventLoop sLoop = SelectorEventLoop.open();
        sLoop.loop(r -> new VProxyThread(r, "ping-pong-client"));
        NetEventLoop loop = new NetEventLoop(sLoop);

        MsQuic msquic = MsQuic.open();
        Registration reg = msquic.openRegistration(new RegistrationConfig()
            .setAppName("msquic vproxy sample")
            .setProfile(ExecutionProfile.LOW_LATENCY));
        Configuration conf = reg.openConfiguration(Set.of("vproxy sample"), new Settings()
            .setIdleTimeoutMs(60_000)
            .setPeerBidiStreamCount(128)
        );
        conf.initAsClient(true);

        MsQuicConnectionFD connFD = new MsQuicConnectionFD(reg, conf);
        var remote = new IPPort("127.0.0.1", 5678);
        ConnectableConnection initialConnection = ConnectableConnection.wrap(connFD,
            remote,
            new ConnectionOpts(),
            RingBuffer.allocate(2),
            RingBuffer.EMPTY_BUFFER);
        loop.addConnectableConnection(initialConnection, null, new MsQuicConnTransHandler(new Callback<>() {
            @Override
            protected void onFailed(IOException err) {
                Logger.error(LogType.ALERT, "handling failed", err);
            }

            @Override
            protected void onSucceeded(ServerSock connServerSock) {
                // launch a server for the connection on client side
                var handler = new ServerHandler() {
                    @Override
                    public void acceptFail(ServerHandlerContext ctx, IOException err) {
                        Logger.error(LogType.ALERT, "accept failed: " + ctx.server + ", shutting down", err);
                        ctx.server.close();
                    }

                    @Override
                    public void connection(ServerHandlerContext ctx, Connection connection) {
                        try {
                            loop.addConnection(connection, null,
                                new PingPongHandler("client-accepted"));
                        } catch (IOException e) {
                            Logger.error(LogType.EVENT_LOOP_ADD_FAIL, "add " + connection + " to loop failed", e);
                            connection.close();
                        }
                    }

                    @Override
                    public Tuple<RingBuffer, RingBuffer> getIOBuffers(SocketFD channel) {
                        return new Tuple<>(
                            RingBuffer.allocate(1024),
                            RingBuffer.allocate(1024)
                        );
                    }

                    @Override
                    public void removed(ServerHandlerContext ctx) {
                        Logger.error(LogType.IMPROPER_USE, ctx.server + " removed from loop");
                        ctx.server.close();
                    }
                };
                try {
                    loop.addServer(connServerSock, null, handler);
                } catch (IOException e) {
                    Logger.error(LogType.EVENT_LOOP_ADD_FAIL, "add " + connServerSock + " to loop failed", e);
                    connServerSock.close();
                    return;
                }

                Logger.alert("send message from client to server");
                MsQuicStreamFD streamFD;
                try {
                    streamFD = new MsQuicStreamFD(connFD);
                } catch (MsQuicException e) {
                    Logger.error(LogType.ALERT, "building stream from " + connFD + " failed");
                    return;
                }
                ConnectableConnection streamConn;
                try {
                    streamConn = ConnectableConnection.wrap(streamFD, remote, new ConnectionOpts(),
                        RingBuffer.allocate(1024), RingBuffer.allocate(1024));
                } catch (IOException e) {
                    Logger.error(LogType.CONN_ERROR, "wrap " + streamFD + " into ConectableConnection failed", e);
                    streamFD.close();
                    return;
                }
                try {
                    loop.addConnectableConnection(streamConn, null, new PingPongHandler("client-connected"));
                } catch (IOException e) {
                    Logger.error(LogType.EVENT_LOOP_ADD_FAIL, "adding " + streamConn + " to loop failed", e);
                    streamConn.close();
                }
            }
        }));
    }
}
