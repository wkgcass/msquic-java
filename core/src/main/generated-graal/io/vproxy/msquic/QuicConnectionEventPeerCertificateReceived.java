package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicConnectionEventPeerCertificateReceived extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("Certificate"),
        ValueLayout.JAVA_INT.withName("DeferredErrorFlags"),
        ValueLayout.JAVA_INT.withName("DeferredStatus"),
        ValueLayout.ADDRESS.withName("Chain")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW CertificateVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Certificate")
        )
    );

    public MemorySegment getCertificate() {
        var SEG = CertificateVH.getMemorySegment(MEMORY);
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

    private static final VarHandleW DeferredErrorFlagsVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("DeferredErrorFlags")
        )
    );

    public int getDeferredErrorFlags() {
        return DeferredErrorFlagsVH.getInt(MEMORY);
    }

    public void setDeferredErrorFlags(int DeferredErrorFlags) {
        DeferredErrorFlagsVH.set(MEMORY, DeferredErrorFlags);
    }

    private static final VarHandleW DeferredStatusVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("DeferredStatus")
        )
    );

    public int getDeferredStatus() {
        return DeferredStatusVH.getInt(MEMORY);
    }

    public void setDeferredStatus(int DeferredStatus) {
        DeferredStatusVH.set(MEMORY, DeferredStatus);
    }

    private static final VarHandleW ChainVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Chain")
        )
    );

    public MemorySegment getChain() {
        var SEG = ChainVH.getMemorySegment(MEMORY);
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
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventPeerCertificateReceived{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Certificate => ");
            SB.append(PanamaUtils.memorySegmentToString(getCertificate()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("DeferredErrorFlags => ");
            SB.append(getDeferredErrorFlags());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("DeferredStatus => ");
            SB.append(getDeferredStatus());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Chain => ");
            SB.append(PanamaUtils.memorySegmentToString(getChain()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventPeerCertificateReceived> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventPeerCertificateReceived.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventPeerCertificateReceived.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventPeerCertificateReceived.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventPeerCertificateReceived ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventPeerCertificateReceived.Array";
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
        protected String toStringTypeName() {
            return "QuicConnectionEventPeerCertificateReceived.Func";
        }

        @Override
        protected QuicConnectionEventPeerCertificateReceived construct(MemorySegment seg) {
            return new QuicConnectionEventPeerCertificateReceived(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:fc673bef9162c0dec64a7adb8f28002de7d505dd20e49e29eb4ef25238eb21d4
