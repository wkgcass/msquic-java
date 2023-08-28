package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventSendShutdownComplete {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("Graceful")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle GracefulVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Graceful")
    );

    public boolean getGraceful() {
        return (boolean) GracefulVH.get(MEMORY);
    }

    public void setGraceful(boolean Graceful) {
        GracefulVH.set(MEMORY, Graceful);
    }

    public QuicStreamEventSendShutdownComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
    }

    public QuicStreamEventSendShutdownComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEventSendShutdownComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventSendShutdownComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEventSendShutdownComplete.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicStreamEventSendShutdownComplete construct(MemorySegment seg) {
            return new QuicStreamEventSendShutdownComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventSendShutdownComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventSendShutdownComplete> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventSendShutdownComplete> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventSendShutdownComplete> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicStreamEventSendShutdownComplete construct(MemorySegment seg) {
            return new QuicStreamEventSendShutdownComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:e52021c1e4ec6ccd9ff18c61c4d36ad6526dc700df28a6b8434cef7748e01c9c
