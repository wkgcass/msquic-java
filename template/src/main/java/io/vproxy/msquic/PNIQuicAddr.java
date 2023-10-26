package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

@Struct(skip = true)
@Include("msquic.h")
@Name("QUIC_ADDR")
@PointerOnly
@Sizeof("QUIC_ADDR")
public abstract class PNIQuicAddr {
    @Impl(
        // language="c"
        c = """
            return QuicAddrGetFamily(self);
            """
    )
    @Style(Styles.critical)
    abstract int getFamily();

    @Impl(
        // language="c"
        c = """
            QuicAddrSetFamily(self, family);
            """
    )
    @Style(Styles.critical)
    abstract void setFamily(int family);

    @Impl(
        // language="c"
        c = """
            return QuicAddrGetPort(self);
            """
    )
    @Style(Styles.critical)
    abstract int getPort();

    @Impl(
        // language="c"
        c = """
            QuicAddrSetPort(self, port);
            """
    )
    @Style(Styles.critical)
    abstract void setPort(int port);

    @Impl(
        // language="c"
        c = """
            QuicAddrToString(self, (QUIC_ADDR_STR*) str);
            """
    )
    @Style(Styles.critical)
    abstract void toString(String str);
}
