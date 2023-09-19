package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventIdealSendBufferSize {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG_UNALIGNED.withName("ByteCount")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle ByteCountVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ByteCount")
    );

    public long getByteCount() {
        return (long) ByteCountVH.get(MEMORY);
    }

    public void setByteCount(long ByteCount) {
        ByteCountVH.set(MEMORY, ByteCount);
    }

    public QuicStreamEventIdealSendBufferSize(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicStreamEventIdealSendBufferSize(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEventIdealSendBufferSize> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventIdealSendBufferSize.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEventIdealSendBufferSize.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicStreamEventIdealSendBufferSize construct(MemorySegment seg) {
            return new QuicStreamEventIdealSendBufferSize(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventIdealSendBufferSize value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventIdealSendBufferSize> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventIdealSendBufferSize> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventIdealSendBufferSize> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventIdealSendBufferSize> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventIdealSendBufferSize> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicStreamEventIdealSendBufferSize construct(MemorySegment seg) {
            return new QuicStreamEventIdealSendBufferSize(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:08305e4649d43d5a4d10adacf5dbd4ed43d1dbec1556dbc57138d1d7c945a5bf
