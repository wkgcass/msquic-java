package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicListenerEvent {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT_UNALIGNED.withName("Type"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        io.vproxy.msquic.QuicListenerEventUnion.LAYOUT.withName("Union")
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

    private final io.vproxy.msquic.QuicListenerEventUnion Union;

    public io.vproxy.msquic.QuicListenerEventUnion getUnion() {
        return this.Union;
    }

    public QuicListenerEvent(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        this.Union = new io.vproxy.msquic.QuicListenerEventUnion(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicListenerEventUnion.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicListenerEventUnion.LAYOUT.byteSize();
    }

    public QuicListenerEvent(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicListenerEvent> {
        public Array(MemorySegment buf) {
            super(buf, QuicListenerEvent.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicListenerEvent.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicListenerEvent construct(MemorySegment seg) {
            return new QuicListenerEvent(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicListenerEvent value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicListenerEvent> {
        private Func(io.vproxy.pni.CallSite<QuicListenerEvent> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicListenerEvent> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEvent> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEvent> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicListenerEvent construct(MemorySegment seg) {
            return new QuicListenerEvent(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:c6dfcc234303e0821ed9107f4372554f5cbea43edea9f2ac55ea5b0e3c187be4
