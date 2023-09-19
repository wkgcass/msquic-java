package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventPeerCertificateReceived {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("Certificate"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("DeferredErrorFlags"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("DeferredStatus"),
        ValueLayout.ADDRESS_UNALIGNED.withName("Chain")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle CertificateVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Certificate")
    );

    public MemorySegment getCertificate() {
        var SEG = (MemorySegment) CertificateVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setCertificate(MemorySegment Certificate) {
        if (Certificate == null) {
            CertificateVH.set(MEMORY, MemorySegment.NULL);
        } else {
            CertificateVH.set(MEMORY, Certificate);
        }
    }

    private static final VarHandle DeferredErrorFlagsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("DeferredErrorFlags")
    );

    public int getDeferredErrorFlags() {
        return (int) DeferredErrorFlagsVH.get(MEMORY);
    }

    public void setDeferredErrorFlags(int DeferredErrorFlags) {
        DeferredErrorFlagsVH.set(MEMORY, DeferredErrorFlags);
    }

    private static final VarHandle DeferredStatusVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("DeferredStatus")
    );

    public int getDeferredStatus() {
        return (int) DeferredStatusVH.get(MEMORY);
    }

    public void setDeferredStatus(int DeferredStatus) {
        DeferredStatusVH.set(MEMORY, DeferredStatus);
    }

    private static final VarHandle ChainVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Chain")
    );

    public MemorySegment getChain() {
        var SEG = (MemorySegment) ChainVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setChain(MemorySegment Chain) {
        if (Chain == null) {
            ChainVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ChainVH.set(MEMORY, Chain);
        }
    }

    public QuicConnectionEventPeerCertificateReceived(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicConnectionEventPeerCertificateReceived(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventPeerCertificateReceived> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventPeerCertificateReceived.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventPeerCertificateReceived.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventPeerCertificateReceived construct(MemorySegment seg) {
            return new QuicConnectionEventPeerCertificateReceived(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventPeerCertificateReceived value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventPeerCertificateReceived> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerCertificateReceived> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerCertificateReceived> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerCertificateReceived> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerCertificateReceived> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventPeerCertificateReceived construct(MemorySegment seg) {
            return new QuicConnectionEventPeerCertificateReceived(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:1990cb2b4fb9dad3a2fa5346578eb164432217bd645434f5e88092c1db71a4f5
