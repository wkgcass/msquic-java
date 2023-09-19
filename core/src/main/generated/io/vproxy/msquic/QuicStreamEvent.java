package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEvent {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT_UNALIGNED.withName("Type"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        io.vproxy.msquic.QuicStreamEventUnion.LAYOUT.withName("Union")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle TypeVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Type")
    );

    public int getType() {
        return (int) TypeVH.get(MEMORY);
    }

    public void setType(int Type) {
        TypeVH.set(MEMORY, Type);
    }

    private final io.vproxy.msquic.QuicStreamEventUnion Union;

    public io.vproxy.msquic.QuicStreamEventUnion getUnion() {
        return this.Union;
    }

    public QuicStreamEvent(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        this.Union = new io.vproxy.msquic.QuicStreamEventUnion(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventUnion.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventUnion.LAYOUT.byteSize();
    }

    public QuicStreamEvent(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEvent> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEvent.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEvent.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicStreamEvent construct(MemorySegment seg) {
            return new QuicStreamEvent(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEvent value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEvent> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEvent> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEvent> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEvent> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEvent> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicStreamEvent construct(MemorySegment seg) {
            return new QuicStreamEvent(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:2c64dbbd1bb08d7e3d875e46ad50b1a8208f8e3bf8a88986d39a1171c28612ef
