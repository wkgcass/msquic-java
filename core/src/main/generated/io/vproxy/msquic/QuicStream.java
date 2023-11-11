package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStream extends io.vproxy.msquic.QuicObjectBase implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        io.vproxy.msquic.QuicObjectBase.LAYOUT

    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    public QuicStream(MemorySegment MEMORY) {
        super(MEMORY);
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += io.vproxy.msquic.QuicObjectBase.LAYOUT.byteSize();
    }

    public QuicStream(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicStream_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle startMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicStream_start", MemorySegment.class /* self */, int.class /* Flags */);

    public int start(int Flags) {
        int RESULT;
        try {
            RESULT = (int) startMH.invokeExact(MEMORY, Flags);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle shutdownMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicStream_shutdown", MemorySegment.class /* self */, int.class /* Flags */, long.class /* ErrorCode */);

    public int shutdown(int Flags, long ErrorCode) {
        int RESULT;
        try {
            RESULT = (int) shutdownMH.invokeExact(MEMORY, Flags, ErrorCode);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle sendMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicStream_send", MemorySegment.class /* self */, io.vproxy.msquic.QuicBuffer.LAYOUT.getClass() /* Buffers */, int.class /* BufferCount */, int.class /* Flags */, MemorySegment.class /* ClientSendContext */);

    public int send(io.vproxy.msquic.QuicBuffer Buffers, int BufferCount, int Flags, MemorySegment ClientSendContext) {
        int RESULT;
        try {
            RESULT = (int) sendMH.invokeExact(MEMORY, (MemorySegment) (Buffers == null ? MemorySegment.NULL : Buffers.MEMORY), BufferCount, Flags, (MemorySegment) (ClientSendContext == null ? MemorySegment.NULL : ClientSendContext));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle receiveCompleteMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicStream_receiveComplete", MemorySegment.class /* self */, long.class /* BufferLength */);

    public void receiveComplete(long BufferLength) {
        try {
            receiveCompleteMH.invokeExact(MEMORY, BufferLength);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle receiveSetEnabledMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicStream_receiveSetEnabled", MemorySegment.class /* self */, boolean.class /* IsEnabled */);

    public int receiveSetEnabled(boolean IsEnabled) {
        int RESULT;
        try {
            RESULT = (int) receiveSetEnabledMH.invokeExact(MEMORY, IsEnabled);
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
        SB.append("QuicStream{\n");
        SB.append(" ".repeat(INDENT + 4)).append("SUPER => ");
        {
            INDENT += 4;
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
            INDENT -= 4;
            SB.append("\n");

        }
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStream> {
        public Array(MemorySegment buf) {
            super(buf, QuicStream.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStream.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStream.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStream ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStream.Array";
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

        private Func(io.vproxy.pni.CallSite<QuicStream> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStream> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStream> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStream.Func";
        }

        @Override
        protected QuicStream construct(MemorySegment seg) {
            return new QuicStream(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.18
// sha256:63d9f2f30bb65e83d5c25dde8c252c48f1e1130662b8d9d8ff7383054e7a2344
