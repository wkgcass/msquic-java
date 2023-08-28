package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEvent {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT_UNALIGNED.withName("Type"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        io.vproxy.msquic.QuicConnectionEventUnion.LAYOUT.withName("Union")
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

    private final io.vproxy.msquic.QuicConnectionEventUnion Union;

    public io.vproxy.msquic.QuicConnectionEventUnion getUnion() {
        return this.Union;
    }

    public QuicConnectionEvent(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        this.Union = new io.vproxy.msquic.QuicConnectionEventUnion(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventUnion.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventUnion.LAYOUT.byteSize();
    }

    public QuicConnectionEvent(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEvent> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEvent.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEvent.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEvent construct(MemorySegment seg) {
            return new QuicConnectionEvent(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEvent value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEvent> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEvent> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEvent> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEvent construct(MemorySegment seg) {
            return new QuicConnectionEvent(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:8a26902a050ef74d2abfb1dc975b104f3abb9020b1243a3e1526624b4b469b7a
