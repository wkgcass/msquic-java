package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicCredentialConfig extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("Type"),
        ValueLayout.JAVA_INT.withName("Flags"),
        io.vproxy.msquic.QuicCertificate.LAYOUT.withName("Certificate"),
        ValueLayout.ADDRESS.withName("Principle"),
        ValueLayout.ADDRESS.withName("Reserved"),
        ValueLayout.ADDRESS.withName("AsyncHandler"),
        ValueLayout.JAVA_INT.withName("AllowedCipherSuites"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS.withName("CaCertificateFile")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

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
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicCredentialConfig{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Type => ");
            SB.append(getType());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Flags => ");
            SB.append(getFlags());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Certificate => ");
            PanamaUtils.nativeObjectToString(getCertificate(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Principle => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getPrinciple(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Reserved => ");
            SB.append(PanamaUtils.memorySegmentToString(getReserved()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("AsyncHandler => ");
            SB.append(PanamaUtils.memorySegmentToString(getAsyncHandler()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("AllowedCipherSuites => ");
            SB.append(getAllowedCipherSuites());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("CaCertificateFile => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getCaCertificateFile(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicCredentialConfig> {
        public Array(MemorySegment buf) {
            super(buf, QuicCredentialConfig.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicCredentialConfig.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicCredentialConfig.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicCredentialConfig ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicCredentialConfig.Array";
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
        protected String toStringTypeName() {
            return "QuicCredentialConfig.Func";
        }

        @Override
        protected QuicCredentialConfig construct(MemorySegment seg) {
            return new QuicCredentialConfig(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:41f88a5cbe6eb3df9ab6e29b09fc67017aa8958950a4aac5e19a3796bb19c9f6
