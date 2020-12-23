package msquic.nativevalues;

import msquic.internal.NativeValues;

import java.util.HashMap;
import java.util.Map;

public enum ServerResumptionLevel {
    NO_RESUME(NativeValues.get().QUIC_SERVER_NO_RESUME()),
    RESUME_ONLY(NativeValues.get().QUIC_SERVER_RESUME_ONLY()),
    RESUME_AND_ZERORTT(NativeValues.get().QUIC_SERVER_RESUME_AND_ZERORTT()),
    ;
    public final int intValue;
    private static final Map<Integer, ServerResumptionLevel> valuesMap;

    static {
        valuesMap = new HashMap<>(values().length);
        for (var t : values()) {
            valuesMap.put(t.intValue, t);
        }
    }

    ServerResumptionLevel(int intValue) {
        this.intValue = intValue;
    }

    public static ServerResumptionLevel valueOf(int v) {
        var ret = valuesMap.get(v);
        if (ret == null) {
            throw new RuntimeException("unknown value " + v + " for ServerResumptionLevel");
        }
        return ret;
    }
}
