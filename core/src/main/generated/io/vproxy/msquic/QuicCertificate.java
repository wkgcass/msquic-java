package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicCertificate {
    public static final MemoryLayout LAYOUT = MemoryLayout.unionLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("CertificateFile")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle CertificateFileVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("CertificateFile")
    );

    public io.vproxy.msquic.QuicCertificateFile getCertificateFile() {
        var SEG = (MemorySegment) CertificateFileVH.get(MEMORY);
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
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicCertificate> {
        public Array(MemorySegment buf) {
            super(buf, QuicCertificate.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicCertificate.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
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

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicCertificate> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicCertificate construct(MemorySegment seg) {
            return new QuicCertificate(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:f9f1b44d014da78d638a3b634128f2ea104e71d7cd0a12c6e18ac0b73cf37b75
