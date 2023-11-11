package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicObjectBase extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("Api"),
        ValueLayout.ADDRESS.withName("Handle")
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

    private static final VarHandle HandleVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Handle")
    );

    public MemorySegment getHandle() {
        var SEG = (MemorySegment) HandleVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setHandle(MemorySegment Handle) {
        if (Handle == null) {
            HandleVH.set(MEMORY, MemorySegment.NULL);
        } else {
            HandleVH.set(MEMORY, Handle);
        }
    }

    public QuicObjectBase(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicObjectBase(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle setContextMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicObjectBase_setContext", MemorySegment.class /* self */, MemorySegment.class /* Context */);

    public void setContext(MemorySegment Context) {
        try {
            setContextMH.invokeExact(MEMORY, (MemorySegment) (Context == null ? MemorySegment.NULL : Context));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle getContextMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), MemorySegment.class, "JavaCritical_io_vproxy_msquic_QuicObjectBase_getContext", MemorySegment.class /* self */);

    public MemorySegment getContext() {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) getContextMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT;
    }

    private static final MethodHandle setCallbackHandlerMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicObjectBase_setCallbackHandler", MemorySegment.class /* self */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */);

    public void setCallbackHandler(MemorySegment Handler, MemorySegment Context) {
        try {
            setCallbackHandlerMH.invokeExact(MEMORY, (MemorySegment) (Handler == null ? MemorySegment.NULL : Handler), (MemorySegment) (Context == null ? MemorySegment.NULL : Context));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle setParamMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicObjectBase_setParam", MemorySegment.class /* self */, int.class /* Param */, int.class /* BufferLength */, MemorySegment.class /* Buffer */);

    public int setParam(int Param, int BufferLength, MemorySegment Buffer) {
        int RESULT;
        try {
            RESULT = (int) setParamMH.invokeExact(MEMORY, Param, BufferLength, (MemorySegment) (Buffer == null ? MemorySegment.NULL : Buffer));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle getParamMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicObjectBase_getParam", MemorySegment.class /* self */, int.class /* Param */, MemorySegment.class /* BufferLength */, MemorySegment.class /* Buffer */);

    public int getParam(int Param, IntArray BufferLength, MemorySegment Buffer) {
        int RESULT;
        try {
            RESULT = (int) getParamMH.invokeExact(MEMORY, Param, (MemorySegment) (BufferLength == null ? MemorySegment.NULL : BufferLength.MEMORY), (MemorySegment) (Buffer == null ? MemorySegment.NULL : Buffer));
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
        SB.append("QuicObjectBase{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Api => ");
            SB.append(PanamaUtils.memorySegmentToString(getApi()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Handle => ");
            SB.append(PanamaUtils.memorySegmentToString(getHandle()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicObjectBase> {
        public Array(MemorySegment buf) {
            super(buf, QuicObjectBase.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicObjectBase.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicObjectBase.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicObjectBase ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicObjectBase.Array";
        }

        @Override
        protected QuicObjectBase construct(MemorySegment seg) {
            return new QuicObjectBase(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicObjectBase value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicObjectBase> {
        private Func(io.vproxy.pni.CallSite<QuicObjectBase> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicObjectBase> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicObjectBase> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicObjectBase> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicObjectBase.Func";
        }

        @Override
        protected QuicObjectBase construct(MemorySegment seg) {
            return new QuicObjectBase(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.18
// sha256:1ca9f5fcd26a7dd053f6fc7a6fe11a55ecc98d7d1214625ffb6c67126cb0bc54
