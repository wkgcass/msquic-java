package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

@Downcall
@Include("msquic.h")
public interface PNIMsQuicValues {
    @Style(Styles.critical)
    String QuicStatusString(int status);

    @Impl(
        // language="c"
        c = """
            return QUIC_STATUS_NOT_SUPPORTED;
            """
    )
    @Style(Styles.critical)
    int QUIC_STATUS_NOT_SUPPORTED();

    @Impl(
        // language="c"
        c = """
            return QUIC_STATUS_PENDING;
            """
    )
    @Style(Styles.critical)
    int QUIC_STATUS_PENDING();

    @Impl(
        // language="c"
        c = """
            return QUIC_ADDRESS_FAMILY_UNSPEC;
            """
    )
    @Style(Styles.critical)
    int QUIC_ADDRESS_FAMILY_UNSPEC();

    @Impl(
        // language="c"
        c = """
            return QUIC_ADDRESS_FAMILY_INET;
            """
    )
    @Style(Styles.critical)
    int QUIC_ADDRESS_FAMILY_INET();

    @Impl(
        // language="c"
        c = """
            return QUIC_ADDRESS_FAMILY_INET6;
            """
    )
    @Style(Styles.critical)
    int QUIC_ADDRESS_FAMILY_INET6();
}
