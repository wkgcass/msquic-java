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
        Files.write(crt, crtContent.getBytes(StandardCharsets.UTF_8));
        Files.write(key, keyContent.getBytes(StandardCharsets.UTF_8));

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

    private static final String crtContent = "-----BEGIN CERTIFICATE-----\n" +
        "MIIDqzCCApOgAwIBAgIJAIvTzI2C9khpMA0GCSqGSIb3DQEBCwUAMGsxCzAJBgNV\n" +
        "BAYTAkNOMREwDwYDVQQIDAhaaGVqaWFuZzERMA8GA1UEBwwISGFuZ3pob3UxDTAL\n" +
        "BgNVBAoMBEhvbWUxDTALBgNVBAsMBEhvbWUxGDAWBgNVBAMMD3drZ2Nhc3MgQ0Eg\n" +
        "cm9vdDAeFw0xOTEyMjcwNDM4MDhaFw0yMDEyMjYwNDM4MDhaMF4xCzAJBgNVBAYT\n" +
        "AlVTMQswCQYDVQQIDAJOWTELMAkGA1UEBwwCTlkxDzANBgNVBAoMBkdvb2dsZTEP\n" +
        "MA0GA1UECwwGR29vZ2xlMRMwEQYDVQQDDApnb29nbGUuY29tMIIBIjANBgkqhkiG\n" +
        "9w0BAQEFAAOCAQ8AMIIBCgKCAQEAslkiuiaeiU+9C6pCZZOrwNul89W3os2spvV8\n" +
        "ywtng/oDjx+dH/taHjWjXbh+0muqoy+I8VCwovs/HBmXj8Dx7fmVi3MOXQZHmFV6\n" +
        "v0S4EVFD3Q3iuOHc/gCVq9xa/+xzOdhYU6zGQ/7rOeLmjIDty+yjbJiUILnK88bp\n" +
        "LYJBbC0GLtU+H/c58QddRwmmakkMxRRzC+qfyBRFGUcP7za8Cubgl2sVlc+3QS4A\n" +
        "es2QUV3z+tqCfNjujurgyCCDOHm1+4be/ebzD77iWuOQPveFvzVI4u8qnmu7Rgqg\n" +
        "jXhwO2dnTqkf0KOfSEBnwhIGvqY05ifAiK/M8/buQOP/G21ZJwIDAQABo18wXTAJ\n" +
        "BgNVHRMEAjAAMAsGA1UdDwQEAwIF4DBDBgNVHREEPDA6ggwqLmdvb2dsZS5jb22C\n" +
        "Cmdvb2dsZS5jb22CDyouZ29vZ2xlLmNvbS5oa4INZ29vZ2xlLmNvbS5oazANBgkq\n" +
        "hkiG9w0BAQsFAAOCAQEAdNPlqIGcnsn4Ggyia5KsPI2/RDVI0DBWi3IyWE/Wl+Xu\n" +
        "dW6PJzpleyftoYNYCyt7bot5Y8yTFi4C1ClHz54bGTQ5ec6d/lrBIFNQLmrOOa2q\n" +
        "lwc0XtTPfDz42Z2PLwZW19YUahkslrPNJVe5qrrfzd1TO9BqxEBA7nWvUFmksYcn\n" +
        "ZD/6pgAXW2zzG7WHnEOpysXhw41GEN5z7eJPNgEA6lw+9i81p0hUbt6ReL2ywcMc\n" +
        "j5B2jshR+aUAMgXuJYfIz0MLwo22lm3wKQf8LGKNpP84mCS+wrj1Z6FTFay2aM/4\n" +
        "EBwtLx5zUHQCFtqt5BncsxG60sXX9VjHwYzqCHy22g==\n" +
        "-----END CERTIFICATE-----\n";
    private static final String keyContent = "-----BEGIN PRIVATE KEY-----\n" +
        "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCyWSK6Jp6JT70L\n" +
        "qkJlk6vA26Xz1beizaym9XzLC2eD+gOPH50f+1oeNaNduH7Sa6qjL4jxULCi+z8c\n" +
        "GZePwPHt+ZWLcw5dBkeYVXq/RLgRUUPdDeK44dz+AJWr3Fr/7HM52FhTrMZD/us5\n" +
        "4uaMgO3L7KNsmJQgucrzxuktgkFsLQYu1T4f9znxB11HCaZqSQzFFHML6p/IFEUZ\n" +
        "Rw/vNrwK5uCXaxWVz7dBLgB6zZBRXfP62oJ82O6O6uDIIIM4ebX7ht795vMPvuJa\n" +
        "45A+94W/NUji7yqea7tGCqCNeHA7Z2dOqR/Qo59IQGfCEga+pjTmJ8CIr8zz9u5A\n" +
        "4/8bbVknAgMBAAECggEBAIM2QO5ja0/qclMauC6zLjF9Z+K04Z3NY7CR+3YGtenL\n" +
        "DsNFpvvYmLyRCdfx3JxCyg+08TNZAhtmbU/nJDKG6XcDoJov0+lsrU/N07jUffd/\n" +
        "qkX/6UXMJiJZm8QNIoYXF87+9DzbaCKucbDs1mGYmVrmhnVm69QH3ODs/rCUnD1Q\n" +
        "yDK8JmjoH8GhaCBfvBkUvFCBDWUZalOfchWAn1eQMxv4D8OYRXR14bh5Cv/SJAuF\n" +
        "lKsYvhYI5jBLRNOS7cpf8c6c7YsIcpDf0Ing4fGBHJotbGgvuuP9sZeTQm/xeO7x\n" +
        "4hsz52+2ctJaU0ZSuLK6bqZXxUMdYHM5vCBFLgoHmQECgYEA2vpgaR4j70bIycbq\n" +
        "NesbTruCqwFi3pM3J/3Osi/h6Klz6xHY5XGvc3N2k3551B39Hg1D45jZoInWYJlS\n" +
        "i6u+lG86YSVzOrfHui7/rRpkwQhxw9uEi0IF3B86UnEi0+++cpPlpM95HXDRVTRH\n" +
        "jxEcuoFcXYUj94lW5WqVFjrdqxECgYEA0IBAieIwWh3mLN7CTzEPz5gsfQoG4xNB\n" +
        "lJtvQC71n7VSRouv9TPTAvPCiS9QGq292yYjFLlmU/XH+aBiqqQP49a4/pbLqD0F\n" +
        "+S88+ws93rEEz+Ivm4+T7ZC4zjMqXaRVfGlu+48LqBC0vrQoLHeedCVNTN5omj6x\n" +
        "n3HYwHHXELcCgYEAj1deTPEh7LuVLCA9qFXiZkNwYahio/gSHueRqiqV4sspyjLA\n" +
        "nFEy3Iw0jpA4B5Yp3sYoLpAbxW71Gf7DfhJKirfUq0rshv9Oip3BV/rzATkZ32+O\n" +
        "7+mkFFeMwfK1La6+KBqQNLZrPc5f+TpjrU8yUxPi9oT06lDIxRxjw019VUECgYEA\n" +
        "nkg+tm4X9tGr43RYXnYIYrhLTcFG6Su9JWu6USdVICEujI+OzL7C+gLDNBHO5fHv\n" +
        "p2aUSy9UF3kGjuLLBG/4ACcJ2Xvwr49j8X+C87HdDimkVYyIR7f/vOGY6jC9gMk4\n" +
        "fHIt5pr5ZmUIUZ3Cwb8tc06+GBTfo/jkLv7mZXIGqpECgYBKoSOliG6k+9lJjjkA\n" +
        "LgNAaPMM63VSSomibMoup607gbxvvo6DHNGSsvmRt7qWldEzLhBXSkzbYqvO8GJJ\n" +
        "yXjgioRbuEqcjo/bamQHg3D5U3eDdelETapWsp8jq8K+L5I3rLxwZwgFaqT3d3Yh\n" +
        "KszwcglypMXpemRptTLSi7cNPw==\n" +
        "-----END PRIVATE KEY-----\n";
}
