package io.vproxy.msquic.sample;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.*;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIString;

import static io.vproxy.msquic.MsQuicConsts.*;

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
        MsQuicUpcall.setImpl(MsQuicUpcallImpl.get());

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
                throw new RuntimeException("RegistrationOpen failed");
            }
            reg = new Registration(new Registration.Options(api, reg_, regAllocator));
        }
        System.out.println("Init Registration done");

        System.out.println("Init Configuration begin ...");
        {
            var confAllocator = Allocator.ofUnsafe();
            var settings = new QuicSettings(confAllocator);
            {
                settings.getIsSet().setIdleTimeoutMs(1);
                settings.setIdleTimeoutMs(60 * 60_000); // 1 hour
                settings.getIsSet().setCongestionControlAlgorithm(1);
                settings.setCongestionControlAlgorithm((short) QUIC_CONGESTION_CONTROL_ALGORITHM_BBR);
                settings.getIsSet().setServerResumptionLevel(1);
                settings.setServerResumptionLevel((byte) QUIC_SERVER_RESUME_AND_ZERORTT);
                settings.getIsSet().setPeerBidiStreamCount(1);
                settings.setPeerBidiStreamCount((short) 128);
            }
            var alpnBuffers = new QuicBuffer.Array(confAllocator, 2);
            {
                alpnBuffers.get(0).setBuffer(new PNIString(confAllocator, "proto-x").MEMORY);
                alpnBuffers.get(0).setLength(7);
                alpnBuffers.get(1).setBuffer(new PNIString(confAllocator, "proto-y").MEMORY);
                alpnBuffers.get(1).setLength(7);
            }
            var conf_ = reg.opts.registrationQ.openConfiguration(alpnBuffers, 2, settings, null, null, confAllocator);
            if (conf_ == null) {
                throw new RuntimeException("ConfigurationOpen failed");
            }
            conf = new Configuration(new Configuration.Options(reg, conf_, confAllocator));
            var cred = new QuicCredentialConfig(confAllocator);
            {
                cred.setType(QUIC_CREDENTIAL_TYPE_CERTIFICATE_FILE);
                cred.setFlags(QUIC_CREDENTIAL_FLAG_NONE);
                var cf = new QuicCertificateFile(confAllocator);
                cf.setCertificateFile(cert, confAllocator);
                cf.setPrivateKeyFile(key, confAllocator);
                cred.getCertificate().setCertificateFile(cf);
                cred.setAllowedCipherSuites(QUIC_ALLOWED_CIPHER_SUITE_AES_256_GCM_SHA384);
            }
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
            lsn = new ServerListener(cli, new Listener.Options(reg, listenerAllocator, ref ->
                reg.opts.registrationQ.openListener(MsQuicUpcall.listenerCallback, ref.MEMORY, null, listenerAllocator)));
            if (lsn.listenerQ == null) {
                lsn.close();
                throw new RuntimeException("failed creating listener");
            }
            var alpnBuffers = new QuicBuffer.Array(listenerAllocator, 2);
            alpnBuffers.get(0).setBuffer(new PNIString(listenerAllocator, "proto-x").MEMORY);
            alpnBuffers.get(0).setLength(7);
            alpnBuffers.get(1).setBuffer(new PNIString(listenerAllocator, "proto-y").MEMORY);
            alpnBuffers.get(1).setLength(7);
            var quicAddr = new QuicAddr(listenerAllocator);
            MsQuic.get().buildQuicAddr(new PNIString(listenerAllocator, "0.0.0.0"), port, quicAddr);
            var err = lsn.listenerQ.start(alpnBuffers, 2, quicAddr);
            if (err != 0) {
                throw new RuntimeException("ListenerStart failed");
            }
        }
        System.out.println("Init Listener done");

        cli.run();
    }

    private static class ServerListener extends Listener {
        private final CommandLine cli;

        public ServerListener(CommandLine cli, Listener.Options opts) {
            super(opts);
            this.cli = cli;
        }

        @Override
        protected boolean requireEventLogging() {
            return true;
        }

        @Override
        public int callback(QuicListenerEvent event) {
            return switch (event.getType()) {
                case QUIC_LISTENER_EVENT_NEW_CONNECTION -> {
                    var data = event.getUnion().getNEW_CONNECTION();
                    var connHQUIC = data.getConnection();
                    var connectionAllocator = Allocator.ofUnsafe();
                    var conn_ = new QuicConnection(connectionAllocator);
                    {
                        conn_.setApi(opts.apiTableQ.getApi());
                        conn_.setConn(connHQUIC);
                    }
                    var conn = new SampleConnection(cli, new Connection.Options(this, connectionAllocator, conn_));
                    opts.apiTableQ.setCallbackHandler(connHQUIC, MsQuicUpcall.connectionCallback, conn.ref.MEMORY);
                    var err = conn_.setConfiguration(conf.opts.configurationQ);
                    if (err != 0) {
                        Logger.error(LogType.ALERT, "set configuration to connection failed: " + err);
                        conn.close();
                        yield err;
                    }
                    yield 0;
                }
                case QUIC_LISTENER_EVENT_STOP_COMPLETE -> 0;
                default -> QUIC_STATUS_NOT_SUPPORTED;
            };
        }
    }
}
