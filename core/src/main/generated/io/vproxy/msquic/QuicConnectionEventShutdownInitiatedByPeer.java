package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventShutdownInitiatedByPeer {
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

    public QuicConnectionEventShutdownInitiatedByPeer(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicConnectionEventShutdownInitiatedByPeer(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventShutdownInitiatedByPeer> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventShutdownInitiatedByPeer.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventShutdownInitiatedByPeer.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventShutdownInitiatedByPeer construct(MemorySegment seg) {
            return new QuicConnectionEventShutdownInitiatedByPeer(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventShutdownInitiatedByPeer value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventShutdownInitiatedByPeer> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByPeer> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByPeer> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventShutdownInitiatedByPeer construct(MemorySegment seg) {
            return new QuicConnectionEventShutdownInitiatedByPeer(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:8012937b395c03f637be89e1d002f2e41ff05011900d01c439945d9df44d9f3e
