package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventPeerStreamStarted {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("Stream"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("Flags"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    );
    public final MemorySegment MEMORY;

    private static final VarHandle StreamVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Stream")
    );

    public MemorySegment getStream() {
        var SEG = (MemorySegment) StreamVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setStream(MemorySegment Stream) {
        if (Stream == null) {
            StreamVH.set(MEMORY, MemorySegment.NULL);
        } else {
            StreamVH.set(MEMORY, Stream);
        }
    }

    private static final VarHandle FlagsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Flags")
    );

    public int getFlags() {
        return (int) FlagsVH.get(MEMORY);
    }

    public void setFlags(int Flags) {
        FlagsVH.set(MEMORY, Flags);
    }

    public QuicConnectionEventPeerStreamStarted(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
    }

    public QuicConnectionEventPeerStreamStarted(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventPeerStreamStarted> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventPeerStreamStarted.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventPeerStreamStarted.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventPeerStreamStarted construct(MemorySegment seg) {
            return new QuicConnectionEventPeerStreamStarted(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventPeerStreamStarted value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventPeerStreamStarted> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerStreamStarted> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerStreamStarted> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventPeerStreamStarted construct(MemorySegment seg) {
            return new QuicConnectionEventPeerStreamStarted(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:6e85b27b1a683c8b2d436ff054936504d5ee4286116d2407ede1e2339c993edf
