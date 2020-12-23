package msquic.nativevalues;

import msquic.internal.NativeValues;

import java.util.HashMap;
import java.util.Map;

public enum ListenerEventType {
    NEW_CONNECTION(NativeValues.get().QUIC_LISTENER_EVENT_NEW_CONNECTION()),
    ;
    public final int intValue;
    private static final Map<Integer, ListenerEventType> valuesMap;

    static {
        valuesMap = new HashMap<>(values().length);
        for (var t : values()) {
            valuesMap.put(t.intValue, t);
        }
    }

    ListenerEventType(int intValue) {
        this.intValue = intValue;
    }

    public static ListenerEventType valueOf(int v) {
        var ret = valuesMap.get(v);
        if (ret == null) {
            throw new RuntimeException("unknown value " + v + " for ListenerEventType");
        }
        return ret;
    }
}
