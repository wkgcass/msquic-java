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
    @Critical
    abstract int getFamily();

    @Impl(
        // language="c"
        c = """
            QuicAddrSetFamily(self, family);
            """
    )
    @Critical
    abstract void setFamily(int family);

    @Impl(
        // language="c"
        c = """
            return QuicAddrGetPort(self);
            """
    )
    @Critical
    abstract int getPort();

    @Impl(
        // language="c"
        c = """
            QuicAddrSetPort(self, port);
            """
    )
    @Critical
    abstract void setPort(int port);

    @Impl(
        // language="c"
        c = """
            QuicAddrToString(self, (QUIC_ADDR_STR*) str);
            """
    )
    @Critical
    abstract void toString(String str);
}
