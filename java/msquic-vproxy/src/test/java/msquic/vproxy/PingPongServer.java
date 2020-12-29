package msquic.vproxy;

import msquic.*;
import msquic.nativevalues.ExecutionProfile;
import msquic.nativevalues.ServerResumptionLevel;
import vfd.IPPort;
import vfd.SocketFD;
import vproxybase.connection.*;
import vproxybase.connection.Connection;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.util.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class PingPongServer {
    public static void main(String[] args) throws Exception {
        Path crt = Files.createTempFile("sample-", ".crt");
        Path key = Files.createTempFile("sample-", ".key");
        crt.toFile().deleteOnExit();
        key.toFile().deleteOnExit();
        Files.write(crt, Common.crtContent.getBytes(StandardCharsets.UTF_8));
        Files.write(key, Common.keyContent.getBytes(StandardCharsets.UTF_8));

        SelectorEventLoop sLoop = SelectorEventLoop.open();
        sLoop.loop(r -> new VProxyThread(r, "ping-pong-server"));
        NetEventLoop loop = new NetEventLoop(sLoop);

        MsQuic msquic = MsQuic.open();
        Registration reg = msquic.openRegistration(new RegistrationConfig()
            .setAppName("msquic vproxy sample")
            .setProfile(ExecutionProfile.LOW_LATENCY));
        Configuration conf = reg.openConfiguration(Set.of("vproxy sample"), new Settings()
            .setIdleTimeoutMs(60_000)
            .setPeerBidiStreamCount(128)
            .setServerResumptionLevel(ServerResumptionLevel.RESUME_AND_ZERORTT)
        );
        conf.loadCredential(crt.toAbsolutePath().toString(), key.toAbsolutePath().toString());

        MsQuicListenerFD listenerFD = new MsQuicListenerFD(reg, conf, Set.of("vproxy sample"));
        ServerSock sock = ServerSock.wrap(listenerFD, new IPPort("127.0.0.1", 5678), new ServerSock.BindOptions());
        loop.addServer(sock, null, new ServerHandler() {
            @Override
            public void acceptFail(ServerHandlerContext ctx, IOException err) {
                Logger.error(LogType.ALERT, "accept failed: " + ctx.server, err);
            }

            @Override
            public Tuple<RingBuffer, RingBuffer> getIOBuffers(SocketFD channel) {
                return new Tuple<>(RingBuffer.allocate(2), RingBuffer.EMPTY_BUFFER);
            }

            @Override
            public void removed(ServerHandlerContext ctx) {
                Logger.error(LogType.IMPROPER_USE, ctx.server + " removed from loop");
                ctx.server.close();
            }

            @Override
            public void connection(ServerHandlerContext ctx, Connection connection) {
                Logger.alert("new connection from " + ctx.server + ": " + connection);
                var cb = new Callback<ServerSock, IOException>() {
                    @Override
                    protected void onFailed(IOException err) {
                        Logger.error(LogType.ALERT, "handling failed", err);
                    }

                    @Override
                    protected void onSucceeded(ServerSock connServerSock) {
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
                                        new PingPongHandler("server-accepted"));
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
                            connection.close();
                        }

                        Logger.alert("send message from server to client");
                        MsQuicConnectionFD connFD = (MsQuicConnectionFD) connServerSock.channel;
                        MsQuicStreamFD streamFD;
                        try {
                            streamFD = new MsQuicStreamFD(connFD);
                        } catch (MsQuicException e) {
                            Logger.error(LogType.ALERT, "building stream from " + connFD + " failed");
                            return;
                        }
                        ConnectableConnection streamConn;
                        try {
                            streamConn = ConnectableConnection.wrap(streamFD, connection.remote, new ConnectionOpts(),
                                RingBuffer.allocate(1024), RingBuffer.allocate(1024));
                        } catch (IOException e) {
                            Logger.error(LogType.CONN_ERROR, "wrap " + streamFD + " into ConectableConnection failed", e);
                            streamFD.close();
                            return;
                        }
                        try {
                            loop.addConnectableConnection(streamConn, null, new PingPongHandler("server-connected"));
                        } catch (IOException e) {
                            Logger.error(LogType.EVENT_LOOP_ADD_FAIL, "adding " + streamConn + " to loop failed", e);
                            streamConn.close();
                        }
                    }
                };
                try {
                    loop.addConnection(connection, null, new MsQuicConnTransHandler(cb));
                } catch (IOException e) {
                    Logger.error(LogType.EVENT_LOOP_ADD_FAIL, "adding conn " + connection + " into loop failed", e);
                    connection.close();
                }
            }
        });
        Logger.alert("Server started");
    }
}
