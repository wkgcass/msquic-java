package msquic.nativevalues;

import msquic.internal.NativeValues;

import java.util.HashMap;
import java.util.Map;

public enum AddressFamily {
    UNSPEC(NativeValues.get().QUIC_ADDRESS_FAMILY_UNSPEC()),
    INET(NativeValues.get().QUIC_ADDRESS_FAMILY_INET()),
    INET6(NativeValues.get().QUIC_ADDRESS_FAMILY_INET6()),
    ;
    public final int intValue;
    private static final Map<Integer, AddressFamily> valuesMap;

    static {
        valuesMap = new HashMap<>(values().length);
        for (var t : values()) {
            valuesMap.put(t.intValue, t);
        }
    }

    AddressFamily(int intValue) {
        this.intValue = intValue;
    }

    public static AddressFamily valueOf(int v) {
        var ret = valuesMap.get(v);
        if (ret == null) {
            throw new RuntimeException("unknown value " + v + " for AddressFamily");
        }
        return ret;
    }
}
