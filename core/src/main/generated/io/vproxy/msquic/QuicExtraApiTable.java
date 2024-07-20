package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicExtraApiTable extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(

    );
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    public QuicExtraApiTable(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
    }

    public QuicExtraApiTable(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle ThreadCountLimitSetMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicExtraApiTable_ThreadCountLimitSet", MemorySegment.class /* self */, int.class /* limit */);

    public void ThreadCountLimitSet(int limit) {
        try {
            ThreadCountLimitSetMH.invokeExact(MEMORY, limit);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle EventLoopThreadDispatcherSetMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicExtraApiTable_EventLoopThreadDispatcherSet", MemorySegment.class /* self */, MemorySegment.class /* dispatcher */);

    public int EventLoopThreadDispatcherSet(MemorySegment dispatcher) {
        int RESULT;
        try {
            RESULT = (int) EventLoopThreadDispatcherSetMH.invokeExact(MEMORY, (MemorySegment) (dispatcher == null ? MemorySegment.NULL : dispatcher));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle ThreadGetCurMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicExtraApiTable_ThreadGetCur", MemorySegment.class /* self */, MemorySegment.class /* Thread */);

    public int ThreadGetCur(MemorySegment Thread) {
        int RESULT;
        try {
            RESULT = (int) ThreadGetCurMH.invokeExact(MEMORY, (MemorySegment) (Thread == null ? MemorySegment.NULL : Thread));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle ThreadSetIsWorkerMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicExtraApiTable_ThreadSetIsWorker", MemorySegment.class /* self */, boolean.class /* isWorker */);

    public void ThreadSetIsWorker(boolean isWorker) {
        try {
            ThreadSetIsWorkerMH.invokeExact(MEMORY, isWorker);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicExtraApiTable{\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicExtraApiTable> {
        public Array(MemorySegment buf) {
            super(buf, QuicExtraApiTable.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicExtraApiTable.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicExtraApiTable.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicExtraApiTable ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicExtraApiTable.Array";
        }

        @Override
        protected QuicExtraApiTable construct(MemorySegment seg) {
            return new QuicExtraApiTable(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicExtraApiTable value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicExtraApiTable> {
        private Func(io.vproxy.pni.CallSite<QuicExtraApiTable> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicExtraApiTable> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicExtraApiTable> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicExtraApiTable> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicExtraApiTable.Func";
        }

        @Override
        protected QuicExtraApiTable construct(MemorySegment seg) {
            return new QuicExtraApiTable(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:6964cc9175d07393c27d7bd182340b14b9096f0d87f0a87c8468c68c7ee78cd5
