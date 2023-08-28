package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStream {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("Api"),
        ValueLayout.ADDRESS_UNALIGNED.withName("Stream")
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

    private static final VarHandle StreamVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Stream")
    );

    public MemorySegment getStream() {
        var SEG = (MemorySegment) StreamVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setStream(MemorySegment Stream) {
        if (Stream == null) {
            StreamVH.set(MEMORY, MemorySegment.NULL);
        } else {
            StreamVH.set(MEMORY, Stream);
        }
    }

    public QuicStream(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicStream(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicStream_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle startMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicStream_start", MemorySegment.class /* self */, int.class /* Flags */);

    public int start(int Flags) {
        int RESULT;
        try {
            RESULT = (int) startMH.invokeExact(MEMORY, Flags);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle shutdownMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicStream_shutdown", MemorySegment.class /* self */, int.class /* Flags */, long.class /* ErrorCode */);

    public int shutdown(int Flags, long ErrorCode) {
        int RESULT;
        try {
            RESULT = (int) shutdownMH.invokeExact(MEMORY, Flags, ErrorCode);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle sendMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicStream_send", MemorySegment.class /* self */, io.vproxy.msquic.QuicBuffer.LAYOUT.getClass() /* Buffers */, int.class /* BufferCount */, int.class /* Flags */, MemorySegment.class /* ClientSendContext */);

    public int send(io.vproxy.msquic.QuicBuffer Buffers, int BufferCount, int Flags, MemorySegment ClientSendContext) {
        int RESULT;
        try {
            RESULT = (int) sendMH.invokeExact(MEMORY, (MemorySegment) (Buffers == null ? MemorySegment.NULL : Buffers.MEMORY), BufferCount, Flags, (MemorySegment) (ClientSendContext == null ? MemorySegment.NULL : ClientSendContext));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle receiveCompleteMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_QuicStream_receiveComplete", MemorySegment.class /* self */, long.class /* BufferLength */);

    public void receiveComplete(long BufferLength) {
        try {
            receiveCompleteMH.invokeExact(MEMORY, BufferLength);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle receiveSetEnabledMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_QuicStream_receiveSetEnabled", MemorySegment.class /* self */, boolean.class /* IsEnabled */);

    public int receiveSetEnabled(boolean IsEnabled) {
        int RESULT;
        try {
            RESULT = (int) receiveSetEnabledMH.invokeExact(MEMORY, IsEnabled);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    public static class Array extends RefArray<QuicStream> {
        public Array(MemorySegment buf) {
            super(buf, QuicStream.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStream.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicStream construct(MemorySegment seg) {
            return new QuicStream(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStream value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStream> {
        private Func(io.vproxy.pni.CallSite<QuicStream> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStream> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicStream construct(MemorySegment seg) {
            return new QuicStream(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:4d0bb1b630e50ebcf353c112eb7024692c323c2cb91668a98f28975280b609b8
