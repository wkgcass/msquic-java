package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicListenerEventUnion {
    public static final MemoryLayout LAYOUT = MemoryLayout.unionLayout(
        io.vproxy.msquic.QuicListenerEventNewConnection.LAYOUT.withName("NEW_CONNECTION"),
        io.vproxy.msquic.QuicListenerEventStopComplete.LAYOUT.withName("STOP_COMPLETE")
    );
    public final MemorySegment MEMORY;

    private final io.vproxy.msquic.QuicListenerEventNewConnection NEW_CONNECTION;

    public io.vproxy.msquic.QuicListenerEventNewConnection getNEW_CONNECTION() {
        return this.NEW_CONNECTION;
    }

    private final io.vproxy.msquic.QuicListenerEventStopComplete STOP_COMPLETE;

    public io.vproxy.msquic.QuicListenerEventStopComplete getSTOP_COMPLETE() {
        return this.STOP_COMPLETE;
    }

    public QuicListenerEventUnion(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        this.NEW_CONNECTION = new io.vproxy.msquic.QuicListenerEventNewConnection(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicListenerEventNewConnection.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicListenerEventNewConnection.LAYOUT.byteSize();
        OFFSET = 0;
        this.STOP_COMPLETE = new io.vproxy.msquic.QuicListenerEventStopComplete(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicListenerEventStopComplete.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicListenerEventStopComplete.LAYOUT.byteSize();
        OFFSET = 0;
    }

    public QuicListenerEventUnion(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicListenerEventUnion> {
        public Array(MemorySegment buf) {
            super(buf, QuicListenerEventUnion.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicListenerEventUnion.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicListenerEventUnion construct(MemorySegment seg) {
            return new QuicListenerEventUnion(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicListenerEventUnion value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicListenerEventUnion> {
        private Func(io.vproxy.pni.CallSite<QuicListenerEventUnion> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventUnion> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicListenerEventUnion construct(MemorySegment seg) {
            return new QuicListenerEventUnion(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:166153fb4cc97b185a0979fd03f2564aa52a711ec7b8cb8209a5b458971b059c
