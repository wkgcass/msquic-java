package io.vproxy.msquic.sample;

import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.ApiTable;
import io.vproxy.msquic.wrap.Configuration;
import io.vproxy.msquic.wrap.Listener;
import io.vproxy.msquic.wrap.Registration;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;
import io.vproxy.pni.PNIString;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;

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
        {
            var apiTableAllocator = Allocator.ofUnsafe();
            var apiTable = MsQuic.get().open(QUIC_API_VERSION_2, null, apiTableAllocator);
            if (apiTable == null) {
                throw new RuntimeException("MsQuicOpenVersion failed");
            }
            api = new ApiTable(apiTable, apiTableAllocator);
        }
        System.out.println("MsQuicOpenVersion done");

        System.out.println("Init Registration begin ...");
        {
            var regAllocator = Allocator.ofUnsafe();
            var config = new QuicRegistrationConfig(regAllocator);
            config.setAppName("sample:server", regAllocator);
            config.setExecutionProfile(QUIC_EXECUTION_PROFILE_LOW_LATENCY);
            var reg_ = api.apiTable.openRegistration(config, null, regAllocator);
            if (reg_ == null) {
                throw new RuntimeException("RegistrationOpen failed");
            }
            reg = new Registration(api.apiTable, reg_, regAllocator);
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
            var conf_ = reg.registration.openConfiguration(alpnBuffers, 2, settings, null, null, confAllocator);
            if (conf_ == null) {
                throw new RuntimeException("ConfigurationOpen failed");
            }
            conf = new Configuration(api.apiTable, reg.registration, conf_, confAllocator);
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
            int err = conf.configuration.loadCredential(cred);
            if (err != 0) {
                conf.close();
                throw new RuntimeException("ConfigurationLoadCredential failed");
            }
        }
        System.out.println("Init Configuration done");

        var cli = new CommandLine(false, conf, null);

        System.out.println("Init Listener begin ...");
        {
            var listenerAllocator = Allocator.ofUnsafe();
            lsn = new ServerListener(cli, api.apiTable, reg.registration, listenerAllocator, ref ->
                reg.registration.openListener(MsQuicUpcall.listenerCallback, ref.MEMORY, null, listenerAllocator));
            if (lsn.listener == null) {
                lsn.close();
                throw new RuntimeException("failed creating listener");
            }
            var alpnBuffers = new QuicBuffer.Array(listenerAllocator, 2);
            alpnBuffers.get(0).setBuffer(new PNIString(listenerAllocator, "proto-x").MEMORY);
            alpnBuffers.get(0).setLength(7);
            alpnBuffers.get(1).setBuffer(new PNIString(listenerAllocator, "proto-y").MEMORY);
            alpnBuffers.get(1).setLength(7);
            var quicAddr = new QuicAddr(listenerAllocator.allocate(sizeofQuicAddr));
            MsQuic.get().buildQuicAddr(new PNIString(listenerAllocator, "0.0.0.0"), port, quicAddr);
            var err = lsn.listener.start(alpnBuffers, 2, quicAddr);
            if (err != 0) {
                throw new RuntimeException("ListenerStart failed");
            }
        }
        System.out.println("Init Listener done");

        cli.run();
    }

    private static class ServerListener extends Listener {
        private final CommandLine cli;

        public ServerListener(CommandLine cli, QuicApiTable table, QuicRegistration reg, Allocator allocator, Function<PNIRef<Listener>, QuicListener> listenerSupplier) {
            super(table, reg, allocator, listenerSupplier);
            this.cli = cli;
        }

        @Override
        public int callback(QuicListenerEvent event) {
            return switch (event.getType()) {
                case QUIC_LISTENER_EVENT_NEW_CONNECTION -> {
                    System.out.println("QUIC_LISTENER_EVENT_NEW_CONNECTION");
                    var data = event.getUnion().getNEW_CONNECTION();
                    var info = data.getInfo();
                    {
                        System.out.println("QuicVersion: " + info.getQuicVersion());
                    }
                    try (var allocator = Allocator.ofConfined()) {
                        var addr = new PNIString(allocator.allocate(64));
                        info.getLocalAddress().toString(addr);
                        System.out.println("LocalAddress: " + addr);
                    }
                    try (var allocator = Allocator.ofConfined()) {
                        var addr = new PNIString(allocator.allocate(64));
                        info.getRemoteAddress().toString(addr);
                        System.out.println("RemoteAddress: " + addr);
                    }
                    {
                        var buffer = info.getCryptoBuffer().reinterpret(info.getCryptoBufferLength());
                        System.out.println("CryptoBuffer:");
                        Utils.hexDump(buffer);
                    }
                    {
                        var alpn = info.getClientAlpnList().reinterpret(info.getClientAlpnListLength());
                        System.out.println("ClientAlpnList:");
                        Utils.hexDump(alpn);
                    }
                    {
                        var serverName = info.getServerName().MEMORY.reinterpret(info.getServerNameLength());
                        byte[] bytes = new byte[(int) serverName.byteSize()];
                        MemorySegment.ofArray(bytes).copyFrom(serverName);
                        System.out.println("ServerName: " + new String(bytes));
                    }
                    {
                        var negotiatedAlpn = info.getNegotiatedAlpn().reinterpret(info.getNegotiatedAlpnLength());
                        System.out.println("NegotiatedAlpn:");
                        Utils.hexDump(negotiatedAlpn);
                    }
                    var connHQUIC = data.getConnection();
                    var connectionAllocator = Allocator.ofUnsafe();
                    var conn_ = new QuicConnection(connectionAllocator);
                    {
                        conn_.setApi(apiTable.getApi());
                        conn_.setConn(connHQUIC);
                    }
                    var conn = new SampleConnection(cli, apiTable, registration, connectionAllocator, ref -> {
                        apiTable.setCallbackHandler(connHQUIC, MsQuicUpcall.connectionCallback, ref.MEMORY);
                        return conn_;
                    });
                    var err = conn_.setConfiguration(conf.configuration);
                    if (err != 0) {
                        System.out.println("set configuration to connection failed: " + err);
                        conn.close();
                        yield err;
                    }
                    yield 0;
                }
                case QUIC_LISTENER_EVENT_STOP_COMPLETE -> {
                    System.out.println("QUIC_LISTENER_EVENT_STOP_COMPLETE");
                    {
                        var appCloseInProgress = event.getUnion().getSTOP_COMPLETE().getAppCloseInProgress();
                        System.out.println("AppCloseInProgress: " + appCloseInProgress);
                    }
                    yield 0;
                }
                default -> QUIC_STATUS_NOT_SUPPORTED;
            };
        }
    }
}
