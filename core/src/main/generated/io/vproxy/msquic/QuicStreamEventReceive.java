package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventReceive {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG_UNALIGNED.withName("AbsoluteOffset"),
        ValueLayout.JAVA_LONG_UNALIGNED.withName("TotalBufferLength"),
        ValueLayout.ADDRESS_UNALIGNED.withName("Buffers"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("BufferCount"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("Flags")
    );
    public final MemorySegment MEMORY;

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
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEventReceive> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventReceive.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEventReceive.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
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
        protected QuicStreamEventReceive construct(MemorySegment seg) {
            return new QuicStreamEventReceive(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:24ae55512ca4fce56889c6ae8360ae59489cd68dbac862970fd8b649ba7c9c83
