package msquic.nativevalues;

import msquic.internal.NativeValues;

import java.util.HashMap;
import java.util.Map;

public enum ConnectionEventType {
    CONNECTED(NativeValues.get().QUIC_CONNECTION_EVENT_CONNECTED()),
    SHUTDOWN_INITIATED_BY_TRANSPORT(NativeValues.get().QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT()),
    SHUTDOWN_INITIATED_BY_PEER(NativeValues.get().QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER()),
    SHUTDOWN_COMPLETE(NativeValues.get().QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE()),
    LOCAL_ADDRESS_CHANGED(NativeValues.get().QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED()),
    PEER_ADDRESS_CHANGED(NativeValues.get().QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED()),
    PEER_STREAM_STARTED(NativeValues.get().QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED()),
    STREAMS_AVAILABLE(NativeValues.get().QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE()),
    PEER_NEEDS_STREAMS(NativeValues.get().QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS()),
    IDEAL_PROCESSOR_CHANGED(NativeValues.get().QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED()),
    DATAGRAM_STATE_CHANGED(NativeValues.get().QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED()),
    DATAGRAM_RECEIVED(NativeValues.get().QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED()),
    DATAGRAM_SEND_STAT_CHANGED(NativeValues.get().QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED()),
    RESUMED(NativeValues.get().QUIC_CONNECTION_EVENT_RESUMED()),
    RESUMPTION_TICKED_RECEIVED(NativeValues.get().QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED()),
    ;
    public final int intValue;
    private static final Map<Integer, ConnectionEventType> valuesMap;

    static {
        valuesMap = new HashMap<>(values().length);
        for (var t : values()) {
            valuesMap.put(t.intValue, t);
        }
    }

    ConnectionEventType(int intValue) {
        this.intValue = intValue;
    }

    public static ConnectionEventType valueOf(int v) {
        var ret = valuesMap.get(v);
        if (ret == null) {
            throw new RuntimeException("unknown value " + v + " for ConnectionEventType");
        }
        return ret;
    }
}
