package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicApiTable {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("Api")
    );
    public final MemorySegment MEMORY;

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
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicApiTable_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle setContextMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicApiTable_setContext", MemorySegment.class /* self */, MemorySegment.class /* Handle */, MemorySegment.class /* Context */);

    public void setContext(MemorySegment Handle, MemorySegment Context) {
        try {
            setContextMH.invokeExact(MEMORY, (MemorySegment) (Handle == null ? MemorySegment.NULL : Handle), (MemorySegment) (Context == null ? MemorySegment.NULL : Context));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle getContextMH = PanamaUtils.lookupPNICriticalFunction(false, MemorySegment.class, "JavaCritical_io_vproxy_msquic_QuicApiTable_getContext", MemorySegment.class /* self */, MemorySegment.class /* Handle */);

    public MemorySegment getContext(MemorySegment Handle) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) getContextMH.invokeExact(MEMORY, (MemorySegment) (Handle == null ? MemorySegment.NULL : Handle));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT;
    }

    private static final MethodHandle setCallbackHandlerMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicApiTable_setCallbackHandler", MemorySegment.class /* self */, MemorySegment.class /* Handle */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */);

    public void setCallbackHandler(MemorySegment Handle, MemorySegment Handler, MemorySegment Context) {
        try {
            setCallbackHandlerMH.invokeExact(MEMORY, (MemorySegment) (Handle == null ? MemorySegment.NULL : Handle), (MemorySegment) (Handler == null ? MemorySegment.NULL : Handler), (MemorySegment) (Context == null ? MemorySegment.NULL : Context));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle setParamMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicApiTable_setParam", MemorySegment.class /* self */, MemorySegment.class /* Handle */, int.class /* Param */, int.class /* BufferLength */, MemorySegment.class /* Buffer */);

    public int setParam(MemorySegment Handle, int Param, int BufferLength, MemorySegment Buffer) {
        int RESULT;
        try {
            RESULT = (int) setParamMH.invokeExact(MEMORY, (MemorySegment) (Handle == null ? MemorySegment.NULL : Handle), Param, BufferLength, (MemorySegment) (Buffer == null ? MemorySegment.NULL : Buffer));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle getParamMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicApiTable_getParam", MemorySegment.class /* self */, MemorySegment.class /* Handle */, int.class /* Param */, MemorySegment.class /* BufferLength */, MemorySegment.class /* Buffer */);

    public int getParam(MemorySegment Handle, int Param, IntArray BufferLength, MemorySegment Buffer) {
        int RESULT;
        try {
            RESULT = (int) getParamMH.invokeExact(MEMORY, (MemorySegment) (Handle == null ? MemorySegment.NULL : Handle), Param, (MemorySegment) (BufferLength == null ? MemorySegment.NULL : BufferLength.MEMORY), (MemorySegment) (Buffer == null ? MemorySegment.NULL : Buffer));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle openRegistrationMH = PanamaUtils.lookupPNICriticalFunction(false, io.vproxy.msquic.QuicRegistration.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_QuicApiTable_openRegistration", MemorySegment.class /* self */, io.vproxy.msquic.QuicRegistrationConfig.LAYOUT.getClass() /* Config */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicRegistration openRegistration(io.vproxy.msquic.QuicRegistrationConfig Config, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openRegistrationMH.invokeExact(MEMORY, (MemorySegment) (Config == null ? MemorySegment.NULL : Config.MEMORY), (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicRegistration.LAYOUT.byteSize()));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicRegistration(RESULT);
    }

    public static class Array extends RefArray<QuicApiTable> {
        public Array(MemorySegment buf) {
            super(buf, QuicApiTable.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicApiTable.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
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
        protected QuicApiTable construct(MemorySegment seg) {
            return new QuicApiTable(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:537353da9c68e8a42978ce76b5490794583c5487224187842f46fc15057e9ffd
