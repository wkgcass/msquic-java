package io.vproxy.msquic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class MsQuicUtils {
    private MsQuicUtils() {
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

    public static String convertToHexString(MemorySegment seg, int len) {
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
