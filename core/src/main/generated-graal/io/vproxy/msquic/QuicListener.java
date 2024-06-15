package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicListener extends io.vproxy.msquic.QuicObjectBase implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        io.vproxy.msquic.QuicObjectBase.LAYOUT

    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    public QuicListener(MemorySegment MEMORY) {
        super(MEMORY);
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += io.vproxy.msquic.QuicObjectBase.LAYOUT.byteSize();
    }

    public QuicListener(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicListener_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle startMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicListener_start", MemorySegment.class /* self */, MemorySegment.class /* AlpnBuffers */, int.class /* AlpnBufferCount */, io.vproxy.msquic.QuicAddr.LAYOUT.getClass() /* Addr */);

    public int start(io.vproxy.msquic.QuicBuffer.Array AlpnBuffers, int AlpnBufferCount, io.vproxy.msquic.QuicAddr Addr) {
        int RESULT;
        try {
            RESULT = (int) startMH.invokeExact(MEMORY, (MemorySegment) (AlpnBuffers == null ? MemorySegment.NULL : AlpnBuffers.MEMORY), AlpnBufferCount, (MemorySegment) (Addr == null ? MemorySegment.NULL : Addr.MEMORY));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle stopMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicListener_stop", MemorySegment.class /* self */);

    public void stop() {
        try {
            stopMH.invokeExact(MEMORY);
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
        SB.append("QuicListener{\n");
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

    public static class Array extends RefArray<QuicListener> {
        public Array(MemorySegment buf) {
            super(buf, QuicListener.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicListener.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicListener.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicListener ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListener.Array";
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

        private Func(io.vproxy.pni.CallSite<QuicListener> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListener> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListener> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListener.Func";
        }

        @Override
        protected QuicListener construct(MemorySegment seg) {
            return new QuicListener(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:24a00c44cffa2833c337af497dbe3165c2e57f8a5f31a021162635cac88afee2
