package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicNewConnectionInfo extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("QuicVersion"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS.withName("LocalAddress"),
        ValueLayout.ADDRESS.withName("RemoteAddress"),
        ValueLayout.JAVA_INT.withName("CryptoBufferLength"),
        ValueLayout.JAVA_SHORT.withName("ClientAlpnListLength"),
        ValueLayout.JAVA_SHORT.withName("ServerNameLength"),
        ValueLayout.JAVA_BYTE.withName("NegotiatedAlpnLength"),
        MemoryLayout.sequenceLayout(7L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS.withName("CryptoBuffer"),
        ValueLayout.ADDRESS.withName("ClientAlpnList"),
        ValueLayout.ADDRESS.withName("NegotiatedAlpn"),
        ValueLayout.ADDRESS.withName("ServerName")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW QuicVersionVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("QuicVersion")
        )
    );

    public int getQuicVersion() {
        return QuicVersionVH.getInt(MEMORY);
    }

    public void setQuicVersion(int QuicVersion) {
        QuicVersionVH.set(MEMORY, QuicVersion);
    }

    private static final VarHandleW LocalAddressVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("LocalAddress")
        )
    );

    public io.vproxy.msquic.QuicAddr getLocalAddress() {
        var SEG = LocalAddressVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return new io.vproxy.msquic.QuicAddr(SEG);
    }

    public void setLocalAddress(io.vproxy.msquic.QuicAddr LocalAddress) {
        if (LocalAddress == null) {
            LocalAddressVH.set(MEMORY, MemorySegment.NULL);
        } else {
            LocalAddressVH.set(MEMORY, LocalAddress.MEMORY);
        }
    }

    private static final VarHandleW RemoteAddressVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("RemoteAddress")
        )
    );

    public io.vproxy.msquic.QuicAddr getRemoteAddress() {
        var SEG = RemoteAddressVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return new io.vproxy.msquic.QuicAddr(SEG);
    }

    public void setRemoteAddress(io.vproxy.msquic.QuicAddr RemoteAddress) {
        if (RemoteAddress == null) {
            RemoteAddressVH.set(MEMORY, MemorySegment.NULL);
        } else {
            RemoteAddressVH.set(MEMORY, RemoteAddress.MEMORY);
        }
    }

    private static final VarHandleW CryptoBufferLengthVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("CryptoBufferLength")
        )
    );

    public int getCryptoBufferLength() {
        return CryptoBufferLengthVH.getInt(MEMORY);
    }

    public void setCryptoBufferLength(int CryptoBufferLength) {
        CryptoBufferLengthVH.set(MEMORY, CryptoBufferLength);
    }

    private static final VarHandleW ClientAlpnListLengthVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ClientAlpnListLength")
        )
    );

    public short getClientAlpnListLength() {
        return ClientAlpnListLengthVH.getShort(MEMORY);
    }

    public void setClientAlpnListLength(short ClientAlpnListLength) {
        ClientAlpnListLengthVH.set(MEMORY, ClientAlpnListLength);
    }

    private static final VarHandleW ServerNameLengthVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ServerNameLength")
        )
    );

    public short getServerNameLength() {
        return ServerNameLengthVH.getShort(MEMORY);
    }

    public void setServerNameLength(short ServerNameLength) {
        ServerNameLengthVH.set(MEMORY, ServerNameLength);
    }

    private static final VarHandleW NegotiatedAlpnLengthVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("NegotiatedAlpnLength")
        )
    );

    public byte getNegotiatedAlpnLength() {
        return NegotiatedAlpnLengthVH.getByte(MEMORY);
    }

    public void setNegotiatedAlpnLength(byte NegotiatedAlpnLength) {
        NegotiatedAlpnLengthVH.set(MEMORY, NegotiatedAlpnLength);
    }

    private static final VarHandleW CryptoBufferVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("CryptoBuffer")
        )
    );

    public MemorySegment getCryptoBuffer() {
        var SEG = CryptoBufferVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setCryptoBuffer(MemorySegment CryptoBuffer) {
        if (CryptoBuffer == null) {
            CryptoBufferVH.set(MEMORY, MemorySegment.NULL);
        } else {
            CryptoBufferVH.set(MEMORY, CryptoBuffer);
        }
    }

    private static final VarHandleW ClientAlpnListVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ClientAlpnList")
        )
    );

    public MemorySegment getClientAlpnList() {
        var SEG = ClientAlpnListVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setClientAlpnList(MemorySegment ClientAlpnList) {
        if (ClientAlpnList == null) {
            ClientAlpnListVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ClientAlpnListVH.set(MEMORY, ClientAlpnList);
        }
    }

    private static final VarHandleW NegotiatedAlpnVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("NegotiatedAlpn")
        )
    );

    public MemorySegment getNegotiatedAlpn() {
        var SEG = NegotiatedAlpnVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setNegotiatedAlpn(MemorySegment NegotiatedAlpn) {
        if (NegotiatedAlpn == null) {
            NegotiatedAlpnVH.set(MEMORY, MemorySegment.NULL);
        } else {
            NegotiatedAlpnVH.set(MEMORY, NegotiatedAlpn);
        }
    }

    private static final VarHandleW ServerNameVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ServerName")
        )
    );

    public PNIString getServerName() {
        var SEG = ServerNameVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return new PNIString(SEG);
    }

    public void setServerName(String ServerName, Allocator ALLOCATOR) {
        this.setServerName(new PNIString(ALLOCATOR, ServerName));
    }

    public void setServerName(PNIString ServerName) {
        if (ServerName == null) {
            ServerNameVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ServerNameVH.set(MEMORY, ServerName.MEMORY);
        }
    }

    public QuicNewConnectionInfo(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += 8;
        OFFSET += 8;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
        OFFSET += 7; /* padding */
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += 8;
    }

    public QuicNewConnectionInfo(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicNewConnectionInfo{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("QuicVersion => ");
            SB.append(getQuicVersion());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("LocalAddress => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getLocalAddress(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("RemoteAddress => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getRemoteAddress(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("CryptoBufferLength => ");
            SB.append(getCryptoBufferLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ClientAlpnListLength => ");
            SB.append(getClientAlpnListLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ServerNameLength => ");
            SB.append(getServerNameLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("NegotiatedAlpnLength => ");
            SB.append(getNegotiatedAlpnLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("CryptoBuffer => ");
            SB.append(PanamaUtils.memorySegmentToString(getCryptoBuffer()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ClientAlpnList => ");
            SB.append(PanamaUtils.memorySegmentToString(getClientAlpnList()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("NegotiatedAlpn => ");
            SB.append(PanamaUtils.memorySegmentToString(getNegotiatedAlpn()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ServerName => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getServerName(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicNewConnectionInfo> {
        public Array(MemorySegment buf) {
            super(buf, QuicNewConnectionInfo.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicNewConnectionInfo.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicNewConnectionInfo.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicNewConnectionInfo ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicNewConnectionInfo.Array";
        }

        @Override
        protected QuicNewConnectionInfo construct(MemorySegment seg) {
            return new QuicNewConnectionInfo(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicNewConnectionInfo value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicNewConnectionInfo> {
        private Func(io.vproxy.pni.CallSite<QuicNewConnectionInfo> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicNewConnectionInfo> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicNewConnectionInfo> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicNewConnectionInfo> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicNewConnectionInfo.Func";
        }

        @Override
        protected QuicNewConnectionInfo construct(MemorySegment seg) {
            return new QuicNewConnectionInfo(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:a68b47d310a6de28deab9920442351989287c3a0605d7d9a21ef78ff54f66d1f
