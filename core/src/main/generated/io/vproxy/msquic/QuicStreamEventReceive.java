package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventReceive extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("AbsoluteOffset"),
        ValueLayout.JAVA_LONG.withName("TotalBufferLength"),
        ValueLayout.ADDRESS.withName("Buffers"),
        ValueLayout.JAVA_INT.withName("BufferCount"),
        ValueLayout.JAVA_INT.withName("Flags")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle AbsoluteOffsetVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("AbsoluteOffset")
    );

    public long getAbsoluteOffset() {
        return (long) AbsoluteOffsetVH.get(MEMORY);
    }

    public void setAbsoluteOffset(long AbsoluteOffset) {
        AbsoluteOffsetVH.set(MEMORY, AbsoluteOffset);
    }

    private static final VarHandle TotalBufferLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("TotalBufferLength")
    );

    public long getTotalBufferLength() {
        return (long) TotalBufferLengthVH.get(MEMORY);
    }

    public void setTotalBufferLength(long TotalBufferLength) {
        TotalBufferLengthVH.set(MEMORY, TotalBufferLength);
    }

    private static final VarHandle BuffersVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Buffers")
    );

    public io.vproxy.msquic.QuicBuffer getBuffers() {
        var SEG = (MemorySegment) BuffersVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return new io.vproxy.msquic.QuicBuffer(SEG);
    }

    public void setBuffers(io.vproxy.msquic.QuicBuffer Buffers) {
        if (Buffers == null) {
            BuffersVH.set(MEMORY, MemorySegment.NULL);
        } else {
            BuffersVH.set(MEMORY, Buffers.MEMORY);
        }
    }

    private static final VarHandle BufferCountVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("BufferCount")
    );

    public int getBufferCount() {
        return (int) BufferCountVH.get(MEMORY);
    }

    public void setBufferCount(int BufferCount) {
        BufferCountVH.set(MEMORY, BufferCount);
    }

    private static final VarHandle FlagsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Flags")
    );

    public int getFlags() {
        return (int) FlagsVH.get(MEMORY);
    }

    public void setFlags(int Flags) {
        FlagsVH.set(MEMORY, Flags);
    }

    public QuicStreamEventReceive(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
        OFFSET += 8;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
    }

    public QuicStreamEventReceive(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicStreamEventReceive{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("AbsoluteOffset => ");
            SB.append(getAbsoluteOffset());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("TotalBufferLength => ");
            SB.append(getTotalBufferLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Buffers => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getBuffers(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("BufferCount => ");
            SB.append(getBufferCount());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Flags => ");
            SB.append(getFlags());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStreamEventReceive> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventReceive.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEventReceive.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEventReceive.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEventReceive ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventReceive.Array";
        }

        @Override
        protected QuicStreamEventReceive construct(MemorySegment seg) {
            return new QuicStreamEventReceive(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventReceive value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventReceive> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventReceive> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventReceive> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventReceive> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventReceive> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventReceive.Func";
        }

        @Override
        protected QuicStreamEventReceive construct(MemorySegment seg) {
            return new QuicStreamEventReceive(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.15
// sha256:e16a97349aa2a474bd287a75927db8558a3a473a92c95f82e78effe1596176a6
