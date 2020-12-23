package msquic.nativevalues;

import msquic.internal.NativeValues;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    SUCCESS(NativeValues.get().QUIC_STATUS_SUCCESS()),
    PENDING(NativeValues.get().QUIC_STATUS_PENDING()),
    CONTINUE(NativeValues.get().QUIC_STATUS_CONTINUE()),
    OUT_OF_MEMORY(NativeValues.get().QUIC_STATUS_OUT_OF_MEMORY()),
    INVALID_PARAMETER(NativeValues.get().QUIC_STATUS_INVALID_PARAMETER()),
    INVALID_STATE(NativeValues.get().QUIC_STATUS_INVALID_STATE()),
    NOT_SUPPORTED(NativeValues.get().QUIC_STATUS_NOT_SUPPORTED()),
    NOT_FOUND(NativeValues.get().QUIC_STATUS_NOT_FOUND()),
    BUFFER_TOO_SMALL(NativeValues.get().QUIC_STATUS_BUFFER_TOO_SMALL()),
    HANDSHAKE_FAILURE(NativeValues.get().QUIC_STATUS_HANDSHAKE_FAILURE()),
    ABORTED(NativeValues.get().QUIC_STATUS_ABORTED()),
    ADDRESS_IN_USE(NativeValues.get().QUIC_STATUS_ADDRESS_IN_USE()),
    CONNECTION_TIMEOUT(NativeValues.get().QUIC_STATUS_CONNECTION_TIMEOUT()),
    CONNECTION_IDLE(NativeValues.get().QUIC_STATUS_CONNECTION_IDLE()),
    INTERNAL_ERROR(NativeValues.get().QUIC_STATUS_INTERNAL_ERROR()),
    CONNECTION_REFUSED(NativeValues.get().QUIC_STATUS_CONNECTION_REFUSED()),
    PROTOCOL_ERROR(NativeValues.get().QUIC_STATUS_PROTOCOL_ERROR()),
    VER_NEG_ERROR(NativeValues.get().QUIC_STATUS_VER_NEG_ERROR()),
    UNREACHABLE(NativeValues.get().QUIC_STATUS_UNREACHABLE()),
    PERMISSION_DENIED(NativeValues.get().QUIC_STATUS_PERMISSION_DENIED()),
    EPOLL_ERROR(NativeValues.get().QUIC_STATUS_EPOLL_ERROR()),
    DNS_RESOLUTION_ERROR(NativeValues.get().QUIC_STATUS_DNS_RESOLUTION_ERROR()),
    SOCKET_ERROR(NativeValues.get().QUIC_STATUS_SOCKET_ERROR()),
    TLS_ERROR(NativeValues.get().QUIC_STATUS_TLS_ERROR()),
    USER_CANCELED(NativeValues.get().QUIC_STATUS_USER_CANCELED()),
    ALPN_NEG_FAILURE(NativeValues.get().QUIC_STATUS_ALPN_NEG_FAILURE()),
    UNKNOWN(INTERNAL_ERROR.intValue),
    ;
    public final int intValue;
    private static final Map<Integer, Status> valuesMap;

    static {
        valuesMap = new HashMap<>(values().length);
        for (var t : values()) {
            valuesMap.put(t.intValue, t);
        }
        valuesMap.put(INTERNAL_ERROR.intValue, INTERNAL_ERROR); // ensure using INTERNAL_ERROR not UNKNOWN
    }

    Status(int intValue) {
        this.intValue = intValue;
    }

    public static Status tryToGetByValue(int v) {
        return valuesMap.get(v);
    }

    public static Status valueOf(int v) {
        var ret = tryToGetByValue(v);
        if (ret == null) {
            throw new RuntimeException("unknown value " + v + " for Status");
        }
        return ret;
    }
}
