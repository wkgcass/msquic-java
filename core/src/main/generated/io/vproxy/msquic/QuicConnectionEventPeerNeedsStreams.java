package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventPeerNeedsStreams {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("Bidirectional")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle BidirectionalVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Bidirectional")
    );

    public boolean getBidirectional() {
        return (boolean) BidirectionalVH.get(MEMORY);
    }

    public void setBidirectional(boolean Bidirectional) {
        BidirectionalVH.set(MEMORY, Bidirectional);
    }

    public QuicConnectionEventPeerNeedsStreams(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
    }

    public QuicConnectionEventPeerNeedsStreams(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventPeerNeedsStreams> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventPeerNeedsStreams.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventPeerNeedsStreams.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventPeerNeedsStreams construct(MemorySegment seg) {
            return new QuicConnectionEventPeerNeedsStreams(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventPeerNeedsStreams value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventPeerNeedsStreams> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerNeedsStreams> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerNeedsStreams> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerNeedsStreams> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerNeedsStreams> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventPeerNeedsStreams construct(MemorySegment seg) {
            return new QuicConnectionEventPeerNeedsStreams(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:28220e2f4adcae18b42fa681bb6dee9754f925b23461f15407971999dac99fa4
