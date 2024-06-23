package io.vproxy.msquic.sample;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.*;
import io.vproxy.msquic.callback.ConnectionCallbackList;
import io.vproxy.msquic.callback.ListenerCallback;
import io.vproxy.msquic.callback.ListenerCallbackList;
import io.vproxy.msquic.callback.LogListenerCallback;
import io.vproxy.msquic.wrap.*;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.graal.GraalUtils;
import io.vproxy.vfd.IPPort;

import java.util.List;

import static io.vproxy.msquic.MsQuicConsts.QUIC_EXECUTION_PROFILE_LOW_LATENCY;

public class Server {
    private static final String HELP_STR = """
        Usage: cert={...} key={...}
            cert             path to cert file
            key              path to key file
            port             listen port, default 443
        """;
    private static ApiTable api;
    private static Registration reg;
    private static Configuration conf;
    private static Listener lsn;

    public static void main(String[] args) {
        System.loadLibrary("msquic");
        System.loadLibrary("msquic-java");
        MsQuicMod.get().MsQuicSetThreadCountLimit(2);
        MsQuicUpcall.setImpl(MsQuicUpcallImpl.get());
        MsQuicModUpcall.setImpl(MsQuicModUpcallImpl.get());
        MsQuicMod.get().MsQuicSetEventLoopThreadDispatcher(MsQuicModUpcall.dispatch);
        GraalUtils.setThread();

        String cert = null;
        String key = null;
        String portStr = "443";
        for (var arg : args) {
            if (arg.startsWith("cert=")) {
                cert = arg.substring("cert=".length()).trim();
            } else if (arg.startsWith("key=")) {
                key = arg.substring("key=".length()).trim();
            } else if (arg.startsWith("port=")) {
                portStr = arg.substring("port=".length()).trim();
            }
        }
        if (cert == null) {
            System.out.println("missing cert");
            System.out.println(HELP_STR);
            return;
        }
        if (key == null) {
            System.out.println("missing key");
            System.out.println(HELP_STR);
            return;
        }
        int port = Integer.parseInt(portStr);

        System.out.println("Start quic server ...");

        System.out.println("MsQuicOpenVersion begin ...");
        api = ApiTables.V2;
        System.out.println("MsQuicOpenVersion done");

        System.out.println("Init Registration begin ...");
        {
            var regAllocator = Allocator.ofUnsafe();
            var config = new QuicRegistrationConfig(regAllocator);
            config.setAppName("sample:server", regAllocator);
            config.setExecutionProfile(QUIC_EXECUTION_PROFILE_LOW_LATENCY);

            var reg_ = api.opts.apiTableQ.openRegistration(config, null, regAllocator);
            if (reg_ == null) {
                reg.close();
                throw new RuntimeException("RegistrationOpen failed");
            }
            reg = new Registration(new Registration.Options(api, reg_, regAllocator));
        }
        System.out.println("Init Registration done");

        System.out.println("Init Configuration begin ...");
        {
            var confAllocator = Allocator.ofUnsafe();
            var settings = MsQuicUtils.newSettings(60 * 60_000, confAllocator);
            var alpnBuffers = MsQuicUtils.newAlpnBuffers(List.of("sample", "proto-x", "proto-y"), confAllocator);

            var conf_ = reg.opts.registrationQ.openConfiguration(
                alpnBuffers, 2, settings, null, null, confAllocator);

            if (conf_ == null) {
                confAllocator.close();
                throw new RuntimeException("ConfigurationOpen failed");
            }
            conf = new Configuration(new Configuration.Options(reg, conf_, confAllocator));
            var cred = MsQuicUtils.newServerCredential(cert, key, confAllocator);
            int err = conf.opts.configurationQ.loadCredential(cred);
            if (err != 0) {
                conf.close();
                throw new RuntimeException("ConfigurationLoadCredential failed");
            }
        }
        System.out.println("Init Configuration done");

        var cli = new CommandLine(false, reg, conf, null);

        System.out.println("Init Listener begin ...");
        {
            var listenerAllocator = Allocator.ofUnsafe();
            lsn = new Listener(new Listener.Options(reg, listenerAllocator,
                new ListenerCallbackList()
                    .add(new LogListenerCallback(true))
                    .add(new ServerListenerCallback(cli)),
                ref -> reg.opts.registrationQ.openListener(
                    MsQuicUpcall.listenerCallback, ref.MEMORY, null, listenerAllocator)));
            if (lsn.listenerQ == null) {
                lsn.close();
                throw new RuntimeException("failed creating listener");
            }
            var err = lsn.start(new IPPort("0.0.0.0", port), "sample", "proto-x", "proto-y");
            if (err != 0) {
                lsn.close();
                throw new RuntimeException("ListenerStart failed");
            }
        }
        System.out.println("Init Listener done");

        cli.run();
    }

    private record ServerListenerCallback(CommandLine cli) implements ListenerCallback {
        @Override
        public int newConnection(Listener listener, QuicListenerEventNewConnection data) {
            var connHQUIC = data.getConnection();
            var connectionAllocator = Allocator.ofUnsafe();
            var conn_ = new QuicConnection(connectionAllocator);
            {
                conn_.setApi(listener.opts.apiTableQ.getApi());
                conn_.setHandle(connHQUIC);
            }
            var conn = new Connection(new Connection.Options(listener, connectionAllocator,
                new ConnectionCallbackList()
                    .add(new SampleLogConnectionCallback(cli))
                    .add(new SampleConnectionCallback(cli)),
                conn_));
            conn.setConnectionInfo(data);
            conn_.setCallbackHandler(MsQuicUpcall.connectionCallback, conn.ref.MEMORY);
            var err = conn_.setConfiguration(conf.opts.configurationQ);
            if (err != 0) {
                Logger.error(LogType.ALERT, "set configuration to connection failed: " + err);
                conn.close();
                return err;
            }
            return 0;
        }
    }
}
