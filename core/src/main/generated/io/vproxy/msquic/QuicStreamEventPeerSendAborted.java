package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventPeerSendAborted {
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

    public QuicStreamEventPeerSendAborted(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicStreamEventPeerSendAborted(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEventPeerSendAborted> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventPeerSendAborted.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEventPeerSendAborted.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicStreamEventPeerSendAborted construct(MemorySegment seg) {
            return new QuicStreamEventPeerSendAborted(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventPeerSendAborted value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventPeerSendAborted> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventPeerSendAborted> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventPeerSendAborted> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicStreamEventPeerSendAborted construct(MemorySegment seg) {
            return new QuicStreamEventPeerSendAborted(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:c3f9d105d9fa9323ade828fc36d7491d41e6feff1838b01c475a90dc69879cb1
