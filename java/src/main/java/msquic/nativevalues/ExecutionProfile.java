package msquic.nativevalues;

import msquic.internal.NativeValues;

import java.util.HashMap;
import java.util.Map;

public enum ExecutionProfile {
    LOW_LATENCY(NativeValues.get().QUIC_EXECUTION_PROFILE_LOW_LATENCY()),
    MAX_THROUGHPUT(NativeValues.get().QUIC_EXECUTION_PROFILE_TYPE_MAX_THROUGHPUT()),
    SCAVENGER(NativeValues.get().QUIC_EXECUTION_PROFILE_TYPE_SCAVENGER()),
    REAL_TIME(NativeValues.get().QUIC_EXECUTION_PROFILE_TYPE_REAL_TIME()),
    ;
    public final int intValue;
    private static final Map<Integer, ExecutionProfile> valuesMap;

    static {
        valuesMap = new HashMap<>(values().length);
        for (var t : values()) {
            valuesMap.put(t.intValue, t);
        }
    }

    ExecutionProfile(int intValue) {
        this.intValue = intValue;
    }

    public static ExecutionProfile valueOf(int v) {
        var ret = valuesMap.get(v);
        if (ret == null) {
            throw new RuntimeException("unknown value " + v + " for ExecutionProfile");
        }
        return ret;
    }
}
