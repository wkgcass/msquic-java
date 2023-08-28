package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicRegistration {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("Api"),
        ValueLayout.ADDRESS_UNALIGNED.withName("Reg")
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

    private static final VarHandle RegVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Reg")
    );

    public MemorySegment getReg() {
        var SEG = (MemorySegment) RegVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setReg(MemorySegment Reg) {
        if (Reg == null) {
            RegVH.set(MEMORY, MemorySegment.NULL);
        } else {
            RegVH.set(MEMORY, Reg);
        }
    }

    public QuicRegistration(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicRegistration(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicRegistration_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle shutdownMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicRegistration_shutdown", MemorySegment.class /* self */, int.class /* Flags */, long.class /* ErrorCode */);

    public void shutdown(int Flags, long ErrorCode) {
        try {
            shutdownMH.invokeExact(MEMORY, Flags, ErrorCode);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle openConfigurationMH = PanamaUtils.lookupPNICriticalFunction(false, io.vproxy.msquic.QuicConfiguration.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_QuicRegistration_openConfiguration", MemorySegment.class /* self */, MemorySegment.class /* AlpnBuffers */, int.class /* AlpnBufferCount */, io.vproxy.msquic.QuicSettings.LAYOUT.getClass() /* Settings */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicConfiguration openConfiguration(io.vproxy.msquic.QuicBuffer.Array AlpnBuffers, int AlpnBufferCount, io.vproxy.msquic.QuicSettings Settings, MemorySegment Context, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openConfigurationMH.invokeExact(MEMORY, (MemorySegment) (AlpnBuffers == null ? MemorySegment.NULL : AlpnBuffers.MEMORY), AlpnBufferCount, (MemorySegment) (Settings == null ? MemorySegment.NULL : Settings.MEMORY), (MemorySegment) (Context == null ? MemorySegment.NULL : Context), (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicConfiguration.LAYOUT.byteSize()));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicConfiguration(RESULT);
    }

    private static final MethodHandle openListenerMH = PanamaUtils.lookupPNICriticalFunction(false, io.vproxy.msquic.QuicListener.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_QuicRegistration_openListener", MemorySegment.class /* self */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicListener openListener(MemorySegment Handler, MemorySegment Context, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openListenerMH.invokeExact(MEMORY, (MemorySegment) (Handler == null ? MemorySegment.NULL : Handler), (MemorySegment) (Context == null ? MemorySegment.NULL : Context), (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicListener.LAYOUT.byteSize()));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicListener(RESULT);
    }

    private static final MethodHandle openConnectionMH = PanamaUtils.lookupPNICriticalFunction(false, io.vproxy.msquic.QuicConnection.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_QuicRegistration_openConnection", MemorySegment.class /* self */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicConnection openConnection(MemorySegment Handler, MemorySegment Context, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openConnectionMH.invokeExact(MEMORY, (MemorySegment) (Handler == null ? MemorySegment.NULL : Handler), (MemorySegment) (Context == null ? MemorySegment.NULL : Context), (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicConnection.LAYOUT.byteSize()));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicConnection(RESULT);
    }

    public static class Array extends RefArray<QuicRegistration> {
        public Array(MemorySegment buf) {
            super(buf, QuicRegistration.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicRegistration.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicRegistration construct(MemorySegment seg) {
            return new QuicRegistration(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicRegistration value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicRegistration> {
        private Func(io.vproxy.pni.CallSite<QuicRegistration> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicRegistration> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicRegistration construct(MemorySegment seg) {
            return new QuicRegistration(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:9593618eb64996128277b73099d706850d8fc57e2c5b6ce4ad22b9543fa6d0c9
