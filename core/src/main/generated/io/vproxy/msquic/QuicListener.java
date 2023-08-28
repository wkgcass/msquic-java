package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicListener {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("Api"),
        ValueLayout.ADDRESS_UNALIGNED.withName("Lsn")
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

    private static final VarHandle LsnVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Lsn")
    );

    public MemorySegment getLsn() {
        var SEG = (MemorySegment) LsnVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setLsn(MemorySegment Lsn) {
        if (Lsn == null) {
            LsnVH.set(MEMORY, MemorySegment.NULL);
        } else {
            LsnVH.set(MEMORY, Lsn);
        }
    }

    public QuicListener(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicListener(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicListener_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle startMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicListener_start", MemorySegment.class /* self */, MemorySegment.class /* AlpnBuffers */, int.class /* AlpnBufferCount */, io.vproxy.msquic.QuicAddr.LAYOUT.getClass() /* Addr */);

    public int start(io.vproxy.msquic.QuicBuffer.Array AlpnBuffers, int AlpnBufferCount, io.vproxy.msquic.QuicAddr Addr) {
        int RESULT;
        try {
            RESULT = (int) startMH.invokeExact(MEMORY, (MemorySegment) (AlpnBuffers == null ? MemorySegment.NULL : AlpnBuffers.MEMORY), AlpnBufferCount, (MemorySegment) (Addr == null ? MemorySegment.NULL : Addr.MEMORY));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle stopMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicListener_stop", MemorySegment.class /* self */);

    public void stop() {
        try {
            stopMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    public static class Array extends RefArray<QuicListener> {
        public Array(MemorySegment buf) {
            super(buf, QuicListener.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicListener.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicListener construct(MemorySegment seg) {
            return new QuicListener(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicListener value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicListener> {
        private Func(io.vproxy.pni.CallSite<QuicListener> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListener> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicListener construct(MemorySegment seg) {
            return new QuicListener(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:79aede7a26733fd5dc94e97d36d1d38745c175ed802da2b33b62f011a8193021
