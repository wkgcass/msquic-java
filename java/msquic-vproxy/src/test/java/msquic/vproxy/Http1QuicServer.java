package msquic.vproxy;

import msquic.*;
import msquic.nativevalues.ExecutionProfile;
import msquic.nativevalues.ServerResumptionLevel;
import vfd.IPPort;
import vproxybase.connection.NetEventLoop;
import vproxybase.connection.ServerSock;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.util.Logger;
import vproxybase.util.VProxyThread;
import vserver.impl.Http1ServerImpl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class Http1QuicServer {
    public static void main(String[] args) throws Exception {
        Path crt = Files.createTempFile("sample-", ".crt");
        Path key = Files.createTempFile("sample-", ".key");
        crt.toFile().deleteOnExit();
        key.toFile().deleteOnExit();
        Files.write(crt, Common.crtContent.getBytes(StandardCharsets.UTF_8));
        Files.write(key, Common.keyContent.getBytes(StandardCharsets.UTF_8));

        SelectorEventLoop sLoop = SelectorEventLoop.open();
        NetEventLoop loop = new NetEventLoop(sLoop);
        sLoop.loop(r -> new VProxyThread(r, "http1-quic-server"));

        MsQuic msquic = MsQuic.open();
        Registration reg = msquic.openRegistration(new RegistrationConfig()
            .setAppName("msquic vproxy http1 sample")
            .setProfile(ExecutionProfile.LOW_LATENCY));
        Configuration conf = reg.openConfiguration(Set.of("hq-28"), new Settings()
            .setIdleTimeoutMs(60_000)
            .setPeerBidiStreamCount(128)
            .setServerResumptionLevel(ServerResumptionLevel.RESUME_AND_ZERORTT)
        );
        conf.loadCredential(crt.toAbsolutePath().toString(), key.toAbsolutePath().toString());

        SimpleMsQuicServerSocketFD serverSocketFD = new SimpleMsQuicServerSocketFD(sLoop, reg, conf, Set.of("hq-28"));
        ServerSock sock = ServerSock.wrap(serverSocketFD, new IPPort("127.0.0.1", 40443), new ServerSock.BindOptions());

        Http1ServerImpl app = new Http1ServerImpl(loop);
        app.get("/", rctx -> rctx.response().end("Hello World\r\n"));
        app.post("/echo", rctx -> rctx.response().end(rctx.body()));
        app.listen(sock);

        Logger.alert("Server started");
    }
}
