package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicApiTable extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("Api")
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

    public QuicApiTable(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicApiTable(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicApiTable_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle openRegistrationMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), io.vproxy.msquic.QuicRegistration.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_QuicApiTable_openRegistration", MemorySegment.class /* self */, io.vproxy.msquic.QuicRegistrationConfig.LAYOUT.getClass() /* Config */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicRegistration openRegistration(io.vproxy.msquic.QuicRegistrationConfig Config, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openRegistrationMH.invokeExact(MEMORY, (MemorySegment) (Config == null ? MemorySegment.NULL : Config.MEMORY), (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicRegistration.LAYOUT));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicRegistration(RESULT);
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicApiTable{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Api => ");
            SB.append(PanamaUtils.memorySegmentToString(getApi()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicApiTable> {
        public Array(MemorySegment buf) {
            super(buf, QuicApiTable.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicApiTable.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicApiTable.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicApiTable ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicApiTable.Array";
        }

        @Override
        protected QuicApiTable construct(MemorySegment seg) {
            return new QuicApiTable(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicApiTable value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicApiTable> {
        private Func(io.vproxy.pni.CallSite<QuicApiTable> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicApiTable> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicApiTable> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicApiTable> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicApiTable.Func";
        }

        @Override
        protected QuicApiTable construct(MemorySegment seg) {
            return new QuicApiTable(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.18
// sha256:8512b66a9088118fc0a8f28ef67f1838fd6ffa54a0baaff08e57f72b2c2454cc
