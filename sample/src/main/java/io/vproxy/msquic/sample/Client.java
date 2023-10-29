package io.vproxy.msquic.sample;

import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.*;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIString;
import io.vproxy.pni.graal.GraalUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.vproxy.msquic.MsQuicConsts.*;

public class Client {
    private static final String HELP_STR = """
        Usage: host={...} port={...}
            host             initial connection connect to host
            port             initial connection connect to port
            alpn             alpn to connect to peer, default "proto-x"
            insecure         skip cert validation: true or false, default true
            ca               specify ca cert file, default no ca
          ENV:
            SSLKEYLOGFILE    ssl key log file
        """;
    private static ApiTable api;
    private static Registration reg;
    private static Configuration conf;

    public static void main(String[] args) {
        System.loadLibrary("msquic");
        System.loadLibrary("msquic-java");
        MsQuicUpcall.setImpl(MsQuicUpcallImpl.get());
        MsQuicModUpcall.setImpl(MsQuicModUpcallImpl.get());
        MsQuicMod.get().MsQuicSetEventLoopThreadDispatcher(MsQuicModUpcall.dispatch);
        GraalUtils.setThread();

        String host = null;
        String portStr = null;
        String alpn = "proto-x";
        String insecureStr = "true";
        String caCertFile = null;
        for (var arg : args) {
            if (arg.startsWith("host=")) {
                host = arg.substring("host=".length()).trim();
            } else if (arg.startsWith("port=")) {
                portStr = arg.substring("port=".length()).trim();
            } else if (arg.startsWith("alpn=")) {
                alpn = arg.substring("alpn=".length()).trim();
            } else if (arg.startsWith("insecure=")) {
                insecureStr = arg.substring("insecure=".length()).trim();
            } else if (arg.startsWith("ca=")) {
                caCertFile = arg.substring("ca=".length()).trim();
            }
        }
        if (host == null) {
            System.out.println("missing host");
            System.out.println(HELP_STR);
            return;
        }
        if (portStr == null) {
            System.out.println("missing port");
            System.out.println(HELP_STR);
            return;
        }
        if (!insecureStr.equals("true") && !insecureStr.equals("false")) {
            System.out.println("invalid insecure: must be true or false");
            System.out.println(HELP_STR);
            return;
        }
        int port = Integer.parseInt(portStr);
        boolean insecure = insecureStr.equals("true");

        System.out.println("Start quic client ...");

        System.out.println("MsQuicOpenVersion begin ...");
        api = ApiTables.V2;
        System.out.println("MsQuicOpenVersion done");

        System.out.println("Init Registration begin ...");
        {
            var regAllocator = Allocator.ofUnsafe();
            var config = new QuicRegistrationConfig(regAllocator);
            config.setAppName("sample:client", regAllocator);
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
                settings.getIsSet().setIdleTimeoutMs(true);
                settings.setIdleTimeoutMs(60 * 60_000); // 1 hour
                settings.getIsSet().setCongestionControlAlgorithm(true);
                settings.setCongestionControlAlgorithm((short) QUIC_CONGESTION_CONTROL_ALGORITHM_BBR);
                settings.getIsSet().setPeerBidiStreamCount(true);
                settings.setPeerBidiStreamCount((short) 128);
            }
            var buffers = new QuicBuffer.Array(confAllocator, 1);
            buffers.get(0).setBuffer(new PNIString(confAllocator, alpn).MEMORY);
            buffers.get(0).setLength(alpn.length());
            var conf_ = reg.opts.registrationQ.openConfiguration(buffers, 1, settings, null, null, confAllocator);
            if (conf_ == null) {
                throw new RuntimeException("ConfigurationOpen failed");
            }
            conf = new Configuration(new Configuration.Options(reg, conf_, confAllocator));
            var cred = new QuicCredentialConfig(confAllocator);
            cred.setType(QUIC_CREDENTIAL_TYPE_NONE);
            int flags = QUIC_CREDENTIAL_FLAG_CLIENT;
            if (insecure) {
                flags |= QUIC_CREDENTIAL_FLAG_NO_CERTIFICATE_VALIDATION;
            }
            cred.setFlags(flags);
            cred.setAllowedCipherSuites(
                QUIC_ALLOWED_CIPHER_SUITE_AES_128_GCM_SHA256 |
                QUIC_ALLOWED_CIPHER_SUITE_AES_256_GCM_SHA384 |
                QUIC_ALLOWED_CIPHER_SUITE_CHACHA20_POLY1305_SHA256);
            if (caCertFile != null && !insecure) {
                cred.setCaCertificateFile(caCertFile, confAllocator);
            }
            var err = conf.opts.configurationQ.loadCredential(cred);
            if (err != 0) {
                throw new RuntimeException("ConfigurationLoadCredential failed");
            }
        }
        System.out.println("Init Configuration done");

        Path path;
        if (System.getenv("SSLKEYLOGFILE") != null) {
            path = Path.of(System.getenv("SSLKEYLOGFILE"));
            if (!path.toFile().exists()) {
                try {
                    Files.createFile(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                    return;
                }
            }
        } else {
            try {
                path = Files.createTempFile("quic-tls-secret", ".log");
                path.toFile().deleteOnExit();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
                return;
            }
        }
        System.out.println("SSLKEYLOGFILE for current process: " + path);
        var cli = new CommandLine(true, reg, conf, path);

        System.out.println("Init Connection begin ...");
        {
            var connAllocator = Allocator.ofUnsafe();
            var conn = new SampleConnection(cli, new Connection.Options(reg, connAllocator, ref ->
                reg.opts.registrationQ.openConnection(MsQuicUpcall.connectionCallback, ref.MEMORY, null, connAllocator)));
            if (conn.connectionQ == null) {
                conn.close();
                throw new RuntimeException("ConnectionOpen failed");
            }
            var err = conn.enableTlsSecretDebug();
            if (err != 0) {
                System.out.println("failed to set QuicTlsSecret for debugging");
            }
            err = conn.connectionQ.start(conf.opts.configurationQ, QUIC_ADDRESS_FAMILY_INET, new PNIString(connAllocator, host), port);
            if (err != 0) {
                conn.close();
                throw new RuntimeException("ConnectionStart failed");
            }
        }
        System.out.println("Init Connection done");

        cli.run();
    }
}
