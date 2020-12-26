package msquic.nativevalues;

import msquic.internal.NativeValues;

import java.util.HashMap;
import java.util.Map;

public enum StreamEventType {
    START_COMPLETE(NativeValues.get().QUIC_STREAM_EVENT_START_COMPLETE()),
    RECEIVE(NativeValues.get().QUIC_STREAM_EVENT_RECEIVE()),
    SEND_COMPLETE(NativeValues.get().QUIC_STREAM_EVENT_SEND_COMPLETE()),
    PEER_SEND_SHUTDOWN(NativeValues.get().QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN()),
    PEER_SEND_ABORTED(NativeValues.get().QUIC_STREAM_EVENT_PEER_SEND_ABORTED()),
    PEER_RECEIVE_ABORTED(NativeValues.get().QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED()),
    SEND_SHUTDOWN_COMPLETE(NativeValues.get().QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE()),
    SHUTDOWN_COMPLETE(NativeValues.get().QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE()),
    IDEAL_SEND_BUFFER_SIZE(NativeValues.get().QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE()),
    ;
    public final int intValue;
    private static final Map<Integer, StreamEventType> valuesMap;

    static {
        valuesMap = new HashMap<>(values().length);
        for (var t : values()) {
            valuesMap.put(t.intValue, t);
        }
    }

    StreamEventType(int intValue) {
        this.intValue = intValue;
    }

    public static StreamEventType valueOf(int v) {
        var ret = valuesMap.get(v);
        if (ret == null) {
            throw new RuntimeException("unknown value " + v + " for StreamEventType");
        }
        return ret;
    }
}
