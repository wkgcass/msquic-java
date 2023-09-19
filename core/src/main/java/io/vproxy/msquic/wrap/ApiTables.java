package io.vproxy.msquic.wrap;

import io.vproxy.msquic.MsQuic;
import io.vproxy.msquic.MsQuicConsts;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.array.IntArray;

public class ApiTables {
    private static final Allocator allocator = Allocator.ofUnsafe();

    private ApiTables() {
    }

    public static final ApiTable V2;

    static {
        try (var tmpAlloc = Allocator.ofConfined()) {
            var ret = new IntArray(tmpAlloc, 1);
            var v2 = MsQuic.get().open(MsQuicConsts.QUIC_API_VERSION_2, ret, allocator);
            if (v2 == null) {
                throw new RuntimeException("failed to init V2 api: " + ret.get(0));
            }
            V2 = new ApiTable(new ApiTable.Options(v2, null));
        }
    }
}
