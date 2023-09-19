package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicAddr {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(

    );
    public final MemorySegment MEMORY;

    public QuicAddr(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
    }

    public QuicAddr(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    private static final MethodHandle getFamilyMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicAddr_getFamily", MemorySegment.class /* self */);

    public int getFamily() {
        int RESULT;
        try {
            RESULT = (int) getFamilyMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle setFamilyMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicAddr_setFamily", MemorySegment.class /* self */, int.class /* family */);

    public void setFamily(int family) {
        try {
            setFamilyMH.invokeExact(MEMORY, family);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle getPortMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicAddr_getPort", MemorySegment.class /* self */);

    public int getPort() {
        int RESULT;
        try {
            RESULT = (int) getPortMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle setPortMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicAddr_setPort", MemorySegment.class /* self */, int.class /* port */);

    public void setPort(int port) {
        try {
            setPortMH.invokeExact(MEMORY, port);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle toStringMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicAddr_toString", MemorySegment.class /* self */, String.class /* str */);

    public void toString(PNIString str) {
        try {
            toStringMH.invokeExact(MEMORY, (MemorySegment) (str == null ? MemorySegment.NULL : str.MEMORY));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    public static class Array extends RefArray<QuicAddr> {
        public Array(MemorySegment buf) {
            super(buf, QuicAddr.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicAddr.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicAddr construct(MemorySegment seg) {
            return new QuicAddr(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicAddr value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicAddr> {
        private Func(io.vproxy.pni.CallSite<QuicAddr> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicAddr> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicAddr> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicAddr> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicAddr construct(MemorySegment seg) {
            return new QuicAddr(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:9b096e4f74913f24556e52faba9799a6bbe64bf4ec0b402dd0fa8f45b1551966
