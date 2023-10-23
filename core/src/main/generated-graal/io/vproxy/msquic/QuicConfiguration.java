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

public class QuicConfiguration extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("Api"),
        ValueLayout.ADDRESS.withName("Conf")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle ApiVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Api")
    );

    public MemorySegment getApi() {
        var SEG = (MemorySegment) ApiVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setApi(MemorySegment Api) {
        if (Api == null) {
            ApiVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ApiVH.set(MEMORY, Api);
        }
    }

    private static final VarHandle ConfVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Conf")
    );

    public MemorySegment getConf() {
        var SEG = (MemorySegment) ConfVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setConf(MemorySegment Conf) {
        if (Conf == null) {
            ConfVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ConfVH.set(MEMORY, Conf);
        }
    }

    public QuicConfiguration(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicConfiguration(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicConfiguration_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle loadCredentialMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicConfiguration_loadCredential", MemorySegment.class /* self */, io.vproxy.msquic.QuicCredentialConfig.LAYOUT.getClass() /* CredConfig */);

    public int loadCredential(io.vproxy.msquic.QuicCredentialConfig CredConfig) {
        int RESULT;
        try {
            RESULT = (int) loadCredentialMH.invokeExact(MEMORY, (MemorySegment) (CredConfig == null ? MemorySegment.NULL : CredConfig.MEMORY));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConfiguration{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Api => ");
            SB.append(PanamaUtils.memorySegmentToString(getApi()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Conf => ");
            SB.append(PanamaUtils.memorySegmentToString(getConf()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConfiguration> {
        public Array(MemorySegment buf) {
            super(buf, QuicConfiguration.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConfiguration.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConfiguration.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConfiguration ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConfiguration.Array";
        }

        @Override
        protected QuicConfiguration construct(MemorySegment seg) {
            return new QuicConfiguration(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConfiguration value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConfiguration> {
        private Func(io.vproxy.pni.CallSite<QuicConfiguration> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConfiguration> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConfiguration> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConfiguration> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConfiguration.Func";
        }

        @Override
        protected QuicConfiguration construct(MemorySegment seg) {
            return new QuicConfiguration(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:7c64014b8eb20f2630d56971e3949111057f007dda95c90e0ae6f74c5ce714e6
