package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventPeerReceiveAborted {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG_UNALIGNED.withName("ErrorCode")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle ErrorCodeVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ErrorCode")
    );

    public long getErrorCode() {
        return (long) ErrorCodeVH.get(MEMORY);
    }

    public void setErrorCode(long ErrorCode) {
        ErrorCodeVH.set(MEMORY, ErrorCode);
    }

    public QuicStreamEventPeerReceiveAborted(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicStreamEventPeerReceiveAborted(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEventPeerReceiveAborted> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventPeerReceiveAborted.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEventPeerReceiveAborted.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicStreamEventPeerReceiveAborted construct(MemorySegment seg) {
            return new QuicStreamEventPeerReceiveAborted(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventPeerReceiveAborted value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventPeerReceiveAborted> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventPeerReceiveAborted> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventPeerReceiveAborted> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicStreamEventPeerReceiveAborted construct(MemorySegment seg) {
            return new QuicStreamEventPeerReceiveAborted(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:9f4b760c94b0cced15eaeebdc7b3bd05685470db731ab6782922e53a4bf94332
