package io.vproxy.msquic;

import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIString;
import io.vproxy.vfd.IPPort;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;

import static io.vproxy.msquic.MsQuicConsts.*;
import static io.vproxy.msquic.MsQuicConsts.QUIC_ALLOWED_CIPHER_SUITE_CHACHA20_POLY1305_SHA256;

public class MsQuicUtils {
    private MsQuicUtils() {
    }

    public static QuicSettings newSettings(int timeoutMillis, Allocator allocator) {
        var settings = new QuicSettings(allocator);
        {
            settings.getIsSet().setIdleTimeoutMs(true);
            settings.setIdleTimeoutMs(timeoutMillis);

            settings.getIsSet().setCongestionControlAlgorithm(true);
            settings.setCongestionControlAlgorithm((short) QUIC_CONGESTION_CONTROL_ALGORITHM_BBR);

            settings.getIsSet().setServerResumptionLevel(true);
            settings.setServerResumptionLevel((byte) QUIC_SERVER_RESUME_AND_ZERORTT);

            settings.getIsSet().setPeerBidiStreamCount(true);
            settings.setPeerBidiStreamCount(Short.MAX_VALUE);

            settings.getIsSet().setPeerUnidiStreamCount(true);
            settings.setPeerUnidiStreamCount(Short.MAX_VALUE);
        }
        return settings;
    }

    public static QuicBuffer.Array newAlpnBuffers(List<String> alpn, Allocator allocator) {
        var alpnBuffers = new QuicBuffer.Array(allocator, alpn.size());
        for (int i = 0; i < alpn.size(); i++) {
            var a = alpn.get(i);
            var str = new PNIString(allocator, a);
            alpnBuffers.get(i).setBuffer(str.MEMORY);
            alpnBuffers.get(i).setLength((int) (str.MEMORY.byteSize() - 1));
        }
        return alpnBuffers;
    }

    public static QuicCredentialConfig newServerCredential(String certFile, String keyFile, Allocator allocator) {
        var cred = new QuicCredentialConfig(allocator);
        {
            cred.setType(QUIC_CREDENTIAL_TYPE_CERTIFICATE_FILE);
            cred.setFlags(QUIC_CREDENTIAL_FLAG_NONE);
            var cf = new QuicCertificateFile(allocator);
            cf.setCertificateFile(certFile, allocator);
            cf.setPrivateKeyFile(keyFile, allocator);
            cred.getCertificate().setCertificateFile(cf);
            setAllowedCipherSuites(cred);
        }
        return cred;
    }

    public static QuicCredentialConfig newClientCredential(boolean noCAValidation, Allocator allocator) {
        return newClientCredential(noCAValidation, null, allocator);
    }

    public static QuicCredentialConfig newClientCredential(boolean noCAValidation, String caFile, Allocator allocator) {
        var cred = new QuicCredentialConfig(allocator);
        {
            cred.setType(QUIC_CREDENTIAL_TYPE_NONE);
            int flags = QUIC_CREDENTIAL_FLAG_CLIENT;
            if (noCAValidation) {
                flags |= QUIC_CREDENTIAL_FLAG_NO_CERTIFICATE_VALIDATION;
            }
            if (caFile != null) {
                flags |= QUIC_CREDENTIAL_FLAG_SET_CA_CERTIFICATE_FILE;
                flags |= QUIC_CREDENTIAL_FLAG_USE_TLS_BUILTIN_CERTIFICATE_VALIDATION;
                cred.setCaCertificateFile(caFile, allocator);
            }
            cred.setFlags(flags);
            setAllowedCipherSuites(cred);
        }
        return cred;
    }

    private static void setAllowedCipherSuites(QuicCredentialConfig cred) {
        cred.setAllowedCipherSuites(
            QUIC_ALLOWED_CIPHER_SUITE_AES_128_GCM_SHA256 |
            QUIC_ALLOWED_CIPHER_SUITE_AES_256_GCM_SHA384 |
            QUIC_ALLOWED_CIPHER_SUITE_CHACHA20_POLY1305_SHA256
        );
    }

    @SuppressWarnings("resource")
    private static final ThreadLocal<PNIString> quicAddrToIPPortHelper = ThreadLocal.withInitial(
        () -> new PNIString(Allocator.ofAuto().allocate(64))
    );

    public static IPPort convertQuicAddrToIPPort(QuicAddr addr) {
        var str = quicAddrToIPPortHelper.get();
        addr.toString(str);
        return new IPPort(str.toString());
    }

    public static QuicAddr convertIPPortToQuicAddr(IPPort ipport, Allocator allocator) {
        var quicAddr = new QuicAddr(allocator);
        MsQuic.get().buildQuicAddr(new PNIString(allocator, ipport.getAddress().formatToIPString()), ipport.getPort(), quicAddr);
        return quicAddr;
    }

    public static String convertQuicTlsSecretToSSLKEYLOGFILE(QuicTLSSecret s) {
        var sb = new StringBuilder();
        int len = s.getSecretLength() & 0xff;
        String clientRandom = convertToHexString(s.getClientRandom(), (int) s.getClientRandom().byteSize());
        if (s.getIsSet().isClientEarlyTrafficSecret()) {
            sb.append("CLIENT_EARLY_TRAFFIC_SECRET ").append(clientRandom).append(" ")
                .append(convertToHexString(s.getClientEarlyTrafficSecret(), len)).append("\n");
        }
        if (s.getIsSet().isClientHandshakeTrafficSecret()) {
            sb.append("CLIENT_HANDSHAKE_TRAFFIC_SECRET ").append(clientRandom).append(" ")
                .append(convertToHexString(s.getClientHandshakeTrafficSecret(), len)).append("\n");
        }
        if (s.getIsSet().isServerHandshakeTrafficSecret()) {
            sb.append("SERVER_HANDSHAKE_TRAFFIC_SECRET ").append(clientRandom).append(" ")
                .append(convertToHexString(s.getServerHandshakeTrafficSecret(), len)).append("\n");
        }
        if (s.getIsSet().isClientTrafficSecret0()) {
            sb.append("CLIENT_TRAFFIC_SECRET_0 ").append(clientRandom).append(" ")
                .append(convertToHexString(s.getClientTrafficSecret0(), len)).append("\n");
        }
        if (s.getIsSet().isServerTrafficSecret0()) {
            sb.append("SERVER_TRAFFIC_SECRET_0 ").append(clientRandom).append(" ")
                .append(convertToHexString(s.getServerTrafficSecret0(), len)).append("\n");
        }
        return sb.toString();
    }

    private static String convertToHexString(MemorySegment seg, int len) {
        var sb = new StringBuilder();
        for (int i = 0, size = (int) seg.byteSize(); i < size && i < len; ++i) {
            int n = seg.get(ValueLayout.JAVA_BYTE, i) & 0xff;
            var s = Integer.toString(n, 16);
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
