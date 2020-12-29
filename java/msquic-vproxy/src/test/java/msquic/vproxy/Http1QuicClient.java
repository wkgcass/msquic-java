package msquic.vproxy;

import msquic.*;
import msquic.nativevalues.ExecutionProfile;
import vclient.*;
import vfd.IPPort;
import vlibbase.ConnRef;
import vlibbase.ConnRefPool;
import vlibbase.impl.SimpleConnRef;
import vproxybase.connection.ConnectableConnection;
import vproxybase.connection.ConnectionOpts;
import vproxybase.connection.NetEventLoop;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Http1QuicClient {
    private static SelectorEventLoop sLoop;
    private static Registration reg;
    private static Configuration conf;

    public static void main(String[] args) throws Exception {
        sLoop = SelectorEventLoop.open();
        NetEventLoop loop = new NetEventLoop(sLoop);
        sLoop.loop(r -> new VProxyThread(r, "http1-quic-client"));

        MsQuic msquic = MsQuic.open();
        reg = msquic.openRegistration(new RegistrationConfig()
            .setAppName("msquic vproxy http1 sample")
            .setProfile(ExecutionProfile.LOW_LATENCY));
        conf = reg.openConfiguration(Set.of("hq-28"), new Settings()
            .setIdleTimeoutMs(60_000)
            .setPeerBidiStreamCount(128)
        );
        conf.initAsClient(true);

        IPPort remote = new IPPort("127.0.0.1", 40443);
        HttpClient client = HttpClient.to(remote, new HttpClient.Options()
            .setPoolOptions(new ConnRefPool.Options().setMaxCount(0))
            .setClientContext(new ClientContext(loop)));

        List<HttpClientConn> httpConnections = new ArrayList<>(64);
        List<BlockCallback<HttpResponse, IOException>> callbacks = new ArrayList<>(64);
        for (int i = 0; i < 64; ++i) {
            var conn = createConnection(remote, client);
            httpConnections.add(conn);
            callbacks.add(new BlockCallback<>());
        }

        for (int i = 0; i < 64; ++i) {
            var conn = httpConnections.get(i);
            var cb = callbacks.get(i);

            if (i % 2 == 0) {
                conn.get("/").send(cb::finish);
            } else {
                conn.post("/echo").send("data " + i, cb::finish);
            }
        }

        for (int i = 0; i < 64; ++i) {
            HttpResponse resp = callbacks.get(i).block();
            Logger.alert("response " + i + ": " + resp.status() + " / " + resp.bodyAsString().trim());
        }

        // release

        SimpleMsQuicConnectionManager.connect(sLoop, remote, reg, conf).close();

        conf.close();
        reg.close();
        msquic.close();

        sLoop.close();
    }

    private static HttpClientConn createConnection(IPPort remote, HttpClient client) throws IOException {
        SimpleMsQuicSocketFD socketFD = new SimpleMsQuicSocketFD(sLoop, reg, conf);
        ConnectableConnection conn = ConnectableConnection.wrap(
            socketFD,
            remote,
            new ConnectionOpts(),
            RingBuffer.allocate(1024),
            RingBuffer.allocate(1024));
        ConnRef ref = new SimpleConnRef(conn);
        return ref.transferTo(client);
    }
}
