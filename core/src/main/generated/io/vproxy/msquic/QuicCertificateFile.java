package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicCertificateFile extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("PrivateKeyFile"),
        ValueLayout.ADDRESS.withName("CertificateFile")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

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
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicCertificateFile{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PrivateKeyFile => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getPrivateKeyFile(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("CertificateFile => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getCertificateFile(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicCertificateFile> {
        public Array(MemorySegment buf) {
            super(buf, QuicCertificateFile.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicCertificateFile.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicCertificateFile.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicCertificateFile ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicCertificateFile.Array";
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

        private Func(io.vproxy.pni.CallSite<QuicCertificateFile> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicCertificateFile> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicCertificateFile> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicCertificateFile.Func";
        }

        @Override
        protected QuicCertificateFile construct(MemorySegment seg) {
            return new QuicCertificateFile(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.15
// sha256:f160caafb0a65c76c4fb641c7f156592a0301ce7bb8f58d7ec2016a30163712f
