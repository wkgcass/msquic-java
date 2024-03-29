package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

@Downcall
@Include("msquic.h")
public interface PNIMsQuic {
    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api;
            QUIC_STATUS res = MsQuicOpenVersion(Version, (const void**) &api);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->Api = api;
                return return_;
            }
            return NULL;
            """
    )
    @Style(Styles.critical)
    PNIQuicApiTable open(@Unsigned int Version, @Raw int[] returnStatus);

    @Impl(
        // language="c"
        c = """
            return QuicAddrFromString(addr, port, result);
            """
    )
    @Style(Styles.critical)
    boolean buildQuicAddr(String addr, int port, PNIQuicAddr result);
}
