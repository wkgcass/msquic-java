package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicNewConnectionInfo {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT_UNALIGNED.withName("QuicVersion"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS_UNALIGNED.withName("LocalAddress"),
        ValueLayout.ADDRESS_UNALIGNED.withName("RemoteAddress"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("CryptoBufferLength"),
        ValueLayout.JAVA_SHORT_UNALIGNED.withName("ClientAlpnListLength"),
        ValueLayout.JAVA_SHORT_UNALIGNED.withName("ServerNameLength"),
        ValueLayout.JAVA_BYTE.withName("NegotiatedAlpnLength"),
        MemoryLayout.sequenceLayout(7L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS_UNALIGNED.withName("CryptoBuffer"),
        ValueLayout.ADDRESS_UNALIGNED.withName("ClientAlpnList"),
        ValueLayout.ADDRESS_UNALIGNED.withName("NegotiatedAlpn"),
        ValueLayout.ADDRESS_UNALIGNED.withName("ServerName")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle QuicVersionVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("QuicVersion")
    );

    public int getQuicVersion() {
        return (int) QuicVersionVH.get(MEMORY);
    }

    public void setQuicVersion(int QuicVersion) {
        QuicVersionVH.set(MEMORY, QuicVersion);
    }

    private static final VarHandle LocalAddressVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("LocalAddress")
    );

    public io.vproxy.msquic.QuicAddr getLocalAddress() {
        var SEG = (MemorySegment) LocalAddressVH.get(MEMORY);
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

    private static final VarHandle RemoteAddressVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("RemoteAddress")
    );

    public io.vproxy.msquic.QuicAddr getRemoteAddress() {
        var SEG = (MemorySegment) RemoteAddressVH.get(MEMORY);
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

    private static final VarHandle CryptoBufferLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("CryptoBufferLength")
    );

    public int getCryptoBufferLength() {
        return (int) CryptoBufferLengthVH.get(MEMORY);
    }

    public void setCryptoBufferLength(int CryptoBufferLength) {
        CryptoBufferLengthVH.set(MEMORY, CryptoBufferLength);
    }

    private static final VarHandle ClientAlpnListLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ClientAlpnListLength")
    );

    public short getClientAlpnListLength() {
        return (short) ClientAlpnListLengthVH.get(MEMORY);
    }

    public void setClientAlpnListLength(short ClientAlpnListLength) {
        ClientAlpnListLengthVH.set(MEMORY, ClientAlpnListLength);
    }

    private static final VarHandle ServerNameLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ServerNameLength")
    );

    public short getServerNameLength() {
        return (short) ServerNameLengthVH.get(MEMORY);
    }

    public void setServerNameLength(short ServerNameLength) {
        ServerNameLengthVH.set(MEMORY, ServerNameLength);
    }

    private static final VarHandle NegotiatedAlpnLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("NegotiatedAlpnLength")
    );

    public byte getNegotiatedAlpnLength() {
        return (byte) NegotiatedAlpnLengthVH.get(MEMORY);
    }

    public void setNegotiatedAlpnLength(byte NegotiatedAlpnLength) {
        NegotiatedAlpnLengthVH.set(MEMORY, NegotiatedAlpnLength);
    }

    private static final VarHandle CryptoBufferVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("CryptoBuffer")
    );

    public MemorySegment getCryptoBuffer() {
        var SEG = (MemorySegment) CryptoBufferVH.get(MEMORY);
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

    private static final VarHandle ClientAlpnListVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ClientAlpnList")
    );

    public MemorySegment getClientAlpnList() {
        var SEG = (MemorySegment) ClientAlpnListVH.get(MEMORY);
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

    private static final VarHandle NegotiatedAlpnVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("NegotiatedAlpn")
    );

    public MemorySegment getNegotiatedAlpn() {
        var SEG = (MemorySegment) NegotiatedAlpnVH.get(MEMORY);
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

    private static final VarHandle ServerNameVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ServerName")
    );

    public PNIString getServerName() {
        var SEG = (MemorySegment) ServerNameVH.get(MEMORY);
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
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicNewConnectionInfo> {
        public Array(MemorySegment buf) {
            super(buf, QuicNewConnectionInfo.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicNewConnectionInfo.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
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

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicNewConnectionInfo> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicNewConnectionInfo construct(MemorySegment seg) {
            return new QuicNewConnectionInfo(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:ddc6cfe03efe5fc0c458a5d1615536ec690ad6161e7f2cf4d7687bac9d7d620f
