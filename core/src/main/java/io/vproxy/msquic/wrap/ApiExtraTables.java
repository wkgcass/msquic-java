package io.vproxy.msquic.wrap;

import io.vproxy.msquic.MsQuicConsts;
import io.vproxy.msquic.MsQuicMod;
import io.vproxy.msquic.QuicExtraApiTable;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.array.IntArray;

public class ApiExtraTables {
    private ApiExtraTables() {
    }

    public static final QuicExtraApiTable V2EXTRA;

    static {
        try (var tmpAlloc = Allocator.ofConfined()) {
            var ret = new IntArray(tmpAlloc, 1);
            var v2extra = MsQuicMod.get().openExtra(MsQuicConsts.QUIC_API_VERSION_2, ret);
            if (v2extra == null) {
                throw new RuntimeException("failed to init V2extra: " + ret.get(0));
            }
            V2EXTRA = v2extra;
        }
    }
}
