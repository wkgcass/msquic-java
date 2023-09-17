package io.vproxy.msquic;

import io.vproxy.pni.annotation.Critical;
import io.vproxy.pni.annotation.Function;
import io.vproxy.pni.annotation.Impl;
import io.vproxy.pni.annotation.Include;

@Function
@Include("msquic.h")
public interface PNIMsQuicValues {
    @Critical
    String QuicStatusString(int status);

    @Impl(
        // language="c"
        c = """
            return sizeof(QUIC_ADDR);
            """
    )
    @Critical
    int sizeofQuicAddr();

    @Impl(
        // language="c"
        c = """
            return QUIC_STATUS_NOT_SUPPORTED;
            """
    )
    @Critical
    int QUIC_STATUS_NOT_SUPPORTED();

    @Impl(
        // language="c"
        c = """
            return QUIC_STATUS_PENDING;
            """
    )
    @Critical
    int QUIC_STATUS_PENDING();

    @Impl(
        // language="c"
        c = """
            return QUIC_ADDRESS_FAMILY_UNSPEC;
            """
    )
    @Critical
    int QUIC_ADDRESS_FAMILY_UNSPEC();

    @Impl(
        // language="c"
        c = """
            return QUIC_ADDRESS_FAMILY_INET;
            """
    )
    @Critical
    int QUIC_ADDRESS_FAMILY_INET();

    @Impl(
        // language="c"
        c = """
            return QUIC_ADDRESS_FAMILY_INET6;
            """
    )
    @Critical
    int QUIC_ADDRESS_FAMILY_INET6();
}
