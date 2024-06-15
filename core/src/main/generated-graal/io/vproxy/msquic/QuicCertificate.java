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

public class QuicCertificate extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.unionLayout(
        ValueLayout.ADDRESS.withName("CertificateFile")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW CertificateFileVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("CertificateFile")
        )
    );

    public io.vproxy.msquic.QuicCertificateFile getCertificateFile() {
        var SEG = CertificateFileVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return new io.vproxy.msquic.QuicCertificateFile(SEG);
    }

    public void setCertificateFile(io.vproxy.msquic.QuicCertificateFile CertificateFile) {
        if (CertificateFile == null) {
            CertificateFileVH.set(MEMORY, MemorySegment.NULL);
        } else {
            CertificateFileVH.set(MEMORY, CertificateFile.MEMORY);
        }
    }

    public QuicCertificate(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += 8;
        OFFSET = 0;
    }

    public QuicCertificate(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        CORRUPTED_MEMORY = true;
        SB.append("QuicCertificate(\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("CertificateFile => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getCertificateFile(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append(")@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicCertificate> {
        public Array(MemorySegment buf) {
            super(buf, QuicCertificate.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicCertificate.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicCertificate.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicCertificate ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicCertificate.Array";
        }

        @Override
        protected QuicCertificate construct(MemorySegment seg) {
            return new QuicCertificate(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicCertificate value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicCertificate> {
        private Func(io.vproxy.pni.CallSite<QuicCertificate> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicCertificate> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicCertificate> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicCertificate> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicCertificate.Func";
        }

        @Override
        protected QuicCertificate construct(MemorySegment seg) {
            return new QuicCertificate(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:ea0eb134dd432fe599e058f18ef97450cbc3c00f37ca985da6f84554689e1b0d
