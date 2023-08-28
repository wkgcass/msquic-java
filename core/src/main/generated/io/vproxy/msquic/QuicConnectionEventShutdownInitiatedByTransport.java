package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventShutdownInitiatedByTransport {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT_UNALIGNED.withName("Status"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_LONG_UNALIGNED.withName("ErrorCode")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle StatusVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Status")
    );

    public int getStatus() {
        return (int) StatusVH.get(MEMORY);
    }

    public void setStatus(int Status) {
        StatusVH.set(MEMORY, Status);
    }

    private static final VarHandle ErrorCodeVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ErrorCode")
    );

    public long getErrorCode() {
        return (long) ErrorCodeVH.get(MEMORY);
    }

    public void setErrorCode(long ErrorCode) {
        ErrorCodeVH.set(MEMORY, ErrorCode);
    }

    public QuicConnectionEventShutdownInitiatedByTransport(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicConnectionEventShutdownInitiatedByTransport(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventShutdownInitiatedByTransport> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventShutdownInitiatedByTransport.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventShutdownInitiatedByTransport.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventShutdownInitiatedByTransport construct(MemorySegment seg) {
            return new QuicConnectionEventShutdownInitiatedByTransport(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventShutdownInitiatedByTransport value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventShutdownInitiatedByTransport> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByTransport> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByTransport> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventShutdownInitiatedByTransport construct(MemorySegment seg) {
            return new QuicConnectionEventShutdownInitiatedByTransport(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:482c2261e11e3decd76e759e0bd040d5b22239cf394dd5a679ddb274fd2c3011
