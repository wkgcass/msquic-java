package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventStreamsAvailable {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT_UNALIGNED.withName("BidirectionalCount"),
        ValueLayout.JAVA_SHORT_UNALIGNED.withName("UnidirectionalCount")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle BidirectionalCountVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("BidirectionalCount")
    );

    public short getBidirectionalCount() {
        return (short) BidirectionalCountVH.get(MEMORY);
    }

    public void setBidirectionalCount(short BidirectionalCount) {
        BidirectionalCountVH.set(MEMORY, BidirectionalCount);
    }

    private static final VarHandle UnidirectionalCountVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("UnidirectionalCount")
    );

    public short getUnidirectionalCount() {
        return (short) UnidirectionalCountVH.get(MEMORY);
    }

    public void setUnidirectionalCount(short UnidirectionalCount) {
        UnidirectionalCountVH.set(MEMORY, UnidirectionalCount);
    }

    public QuicConnectionEventStreamsAvailable(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
    }

    public QuicConnectionEventStreamsAvailable(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventStreamsAvailable> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventStreamsAvailable.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventStreamsAvailable.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventStreamsAvailable construct(MemorySegment seg) {
            return new QuicConnectionEventStreamsAvailable(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventStreamsAvailable value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventStreamsAvailable> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventStreamsAvailable> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventStreamsAvailable> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventStreamsAvailable construct(MemorySegment seg) {
            return new QuicConnectionEventStreamsAvailable(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:acc5c047f5d2a85882237d0878ba24a57627baf62956e3b1f37e914b47ef2681
