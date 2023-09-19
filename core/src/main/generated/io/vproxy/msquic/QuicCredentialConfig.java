package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicCredentialConfig {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT_UNALIGNED.withName("Type"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("Flags"),
        io.vproxy.msquic.QuicCertificate.LAYOUT.withName("Certificate"),
        ValueLayout.ADDRESS_UNALIGNED.withName("Principle"),
        ValueLayout.ADDRESS_UNALIGNED.withName("Reserved"),
        ValueLayout.ADDRESS_UNALIGNED.withName("AsyncHandler"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("AllowedCipherSuites"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS_UNALIGNED.withName("CaCertificateFile")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle TypeVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Type")
    );

    public int getType() {
        return (int) TypeVH.get(MEMORY);
    }

    public void setType(int Type) {
        TypeVH.set(MEMORY, Type);
    }

    private static final VarHandle FlagsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Flags")
    );

    public int getFlags() {
        return (int) FlagsVH.get(MEMORY);
    }

    public void setFlags(int Flags) {
        FlagsVH.set(MEMORY, Flags);
    }

    private final io.vproxy.msquic.QuicCertificate Certificate;

    public io.vproxy.msquic.QuicCertificate getCertificate() {
        return this.Certificate;
    }

    private static final VarHandle PrincipleVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Principle")
    );

    public PNIString getPrinciple() {
        var SEG = (MemorySegment) PrincipleVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return new PNIString(SEG);
    }

    public void setPrinciple(String Principle, Allocator ALLOCATOR) {
        this.setPrinciple(new PNIString(ALLOCATOR, Principle));
    }

    public void setPrinciple(PNIString Principle) {
        if (Principle == null) {
            PrincipleVH.set(MEMORY, MemorySegment.NULL);
        } else {
            PrincipleVH.set(MEMORY, Principle.MEMORY);
        }
    }

    private static final VarHandle ReservedVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Reserved")
    );

    public MemorySegment getReserved() {
        var SEG = (MemorySegment) ReservedVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setReserved(MemorySegment Reserved) {
        if (Reserved == null) {
            ReservedVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ReservedVH.set(MEMORY, Reserved);
        }
    }

    private static final VarHandle AsyncHandlerVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("AsyncHandler")
    );

    public MemorySegment getAsyncHandler() {
        var SEG = (MemorySegment) AsyncHandlerVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setAsyncHandler(MemorySegment AsyncHandler) {
        if (AsyncHandler == null) {
            AsyncHandlerVH.set(MEMORY, MemorySegment.NULL);
        } else {
            AsyncHandlerVH.set(MEMORY, AsyncHandler);
        }
    }

    private static final VarHandle AllowedCipherSuitesVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("AllowedCipherSuites")
    );

    public int getAllowedCipherSuites() {
        return (int) AllowedCipherSuitesVH.get(MEMORY);
    }

    public void setAllowedCipherSuites(int AllowedCipherSuites) {
        AllowedCipherSuitesVH.set(MEMORY, AllowedCipherSuites);
    }

    private static final VarHandle CaCertificateFileVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("CaCertificateFile")
    );

    public PNIString getCaCertificateFile() {
        var SEG = (MemorySegment) CaCertificateFileVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return new PNIString(SEG);
    }

    public void setCaCertificateFile(String CaCertificateFile, Allocator ALLOCATOR) {
        this.setCaCertificateFile(new PNIString(ALLOCATOR, CaCertificateFile));
    }

    public void setCaCertificateFile(PNIString CaCertificateFile) {
        if (CaCertificateFile == null) {
            CaCertificateFileVH.set(MEMORY, MemorySegment.NULL);
        } else {
            CaCertificateFileVH.set(MEMORY, CaCertificateFile.MEMORY);
        }
    }

    public QuicCredentialConfig(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        this.Certificate = new io.vproxy.msquic.QuicCertificate(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicCertificate.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicCertificate.LAYOUT.byteSize();
        OFFSET += 8;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += 8;
    }

    public QuicCredentialConfig(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicCredentialConfig> {
        public Array(MemorySegment buf) {
            super(buf, QuicCredentialConfig.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicCredentialConfig.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicCredentialConfig construct(MemorySegment seg) {
            return new QuicCredentialConfig(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicCredentialConfig value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicCredentialConfig> {
        private Func(io.vproxy.pni.CallSite<QuicCredentialConfig> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicCredentialConfig> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicCredentialConfig> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicCredentialConfig> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicCredentialConfig construct(MemorySegment seg) {
            return new QuicCredentialConfig(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:c8be820aef3a22e1a41470a25c26759923cfbeadc5e1291e484048321401321f
