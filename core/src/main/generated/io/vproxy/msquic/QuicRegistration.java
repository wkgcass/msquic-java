package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicRegistration extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("Api"),
        ValueLayout.ADDRESS.withName("Reg")
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
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicRegistration_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle shutdownMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicRegistration_shutdown", MemorySegment.class /* self */, int.class /* Flags */, long.class /* ErrorCode */);

    public void shutdown(int Flags, long ErrorCode) {
        try {
            shutdownMH.invokeExact(MEMORY, Flags, ErrorCode);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle openConfigurationMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), io.vproxy.msquic.QuicConfiguration.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_QuicRegistration_openConfiguration", MemorySegment.class /* self */, MemorySegment.class /* AlpnBuffers */, int.class /* AlpnBufferCount */, io.vproxy.msquic.QuicSettings.LAYOUT.getClass() /* Settings */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicConfiguration openConfiguration(io.vproxy.msquic.QuicBuffer.Array AlpnBuffers, int AlpnBufferCount, io.vproxy.msquic.QuicSettings Settings, MemorySegment Context, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openConfigurationMH.invokeExact(MEMORY, (MemorySegment) (AlpnBuffers == null ? MemorySegment.NULL : AlpnBuffers.MEMORY), AlpnBufferCount, (MemorySegment) (Settings == null ? MemorySegment.NULL : Settings.MEMORY), (MemorySegment) (Context == null ? MemorySegment.NULL : Context), (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicConfiguration.LAYOUT));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicConfiguration(RESULT);
    }

    private static final MethodHandle openListenerMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), io.vproxy.msquic.QuicListener.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_QuicRegistration_openListener", MemorySegment.class /* self */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicListener openListener(MemorySegment Handler, MemorySegment Context, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openListenerMH.invokeExact(MEMORY, (MemorySegment) (Handler == null ? MemorySegment.NULL : Handler), (MemorySegment) (Context == null ? MemorySegment.NULL : Context), (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicListener.LAYOUT));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicListener(RESULT);
    }

    private static final MethodHandle openConnectionMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), io.vproxy.msquic.QuicConnection.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_QuicRegistration_openConnection", MemorySegment.class /* self */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicConnection openConnection(MemorySegment Handler, MemorySegment Context, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openConnectionMH.invokeExact(MEMORY, (MemorySegment) (Handler == null ? MemorySegment.NULL : Handler), (MemorySegment) (Context == null ? MemorySegment.NULL : Context), (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicConnection.LAYOUT));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicConnection(RESULT);
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicRegistration{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Api => ");
            SB.append(PanamaUtils.memorySegmentToString(getApi()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Reg => ");
            SB.append(PanamaUtils.memorySegmentToString(getReg()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicRegistration> {
        public Array(MemorySegment buf) {
            super(buf, QuicRegistration.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicRegistration.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicRegistration.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicRegistration ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicRegistration.Array";
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

        private Func(io.vproxy.pni.CallSite<QuicRegistration> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicRegistration> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicRegistration> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicRegistration.Func";
        }

        @Override
        protected QuicRegistration construct(MemorySegment seg) {
            return new QuicRegistration(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.17
// sha256:7b4aed06d99369bbea4a9031a40c598ffdd34b053846dcb0648543221df6c679
