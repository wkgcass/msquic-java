package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicCertificateFile {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("PrivateKeyFile"),
        ValueLayout.ADDRESS_UNALIGNED.withName("CertificateFile")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle PrivateKeyFileVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("PrivateKeyFile")
    );

    public PNIString getPrivateKeyFile() {
        var SEG = (MemorySegment) PrivateKeyFileVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return new PNIString(SEG);
    }

    public void setPrivateKeyFile(String PrivateKeyFile, Allocator ALLOCATOR) {
        this.setPrivateKeyFile(new PNIString(ALLOCATOR, PrivateKeyFile));
    }

    public void setPrivateKeyFile(PNIString PrivateKeyFile) {
        if (PrivateKeyFile == null) {
            PrivateKeyFileVH.set(MEMORY, MemorySegment.NULL);
        } else {
            PrivateKeyFileVH.set(MEMORY, PrivateKeyFile.MEMORY);
        }
    }

    private static final VarHandle CertificateFileVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("CertificateFile")
    );

    public PNIString getCertificateFile() {
        var SEG = (MemorySegment) CertificateFileVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return new PNIString(SEG);
    }

    public void setCertificateFile(String CertificateFile, Allocator ALLOCATOR) {
        this.setCertificateFile(new PNIString(ALLOCATOR, CertificateFile));
    }

    public void setCertificateFile(PNIString CertificateFile) {
        if (CertificateFile == null) {
            CertificateFileVH.set(MEMORY, MemorySegment.NULL);
        } else {
            CertificateFileVH.set(MEMORY, CertificateFile.MEMORY);
        }
    }

    public QuicCertificateFile(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += 8;
        OFFSET += 8;
    }

    public QuicCertificateFile(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicCertificateFile> {
        public Array(MemorySegment buf) {
            super(buf, QuicCertificateFile.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicCertificateFile.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicCertificateFile construct(MemorySegment seg) {
            return new QuicCertificateFile(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicCertificateFile value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicCertificateFile> {
        private Func(io.vproxy.pni.CallSite<QuicCertificateFile> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicCertificateFile> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicCertificateFile construct(MemorySegment seg) {
            return new QuicCertificateFile(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:d587caee0f0d731654f19b9ec12b4c1340ae0c4a301b9c01e781eafaadd6c180
