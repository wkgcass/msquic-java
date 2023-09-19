package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventStartComplete {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT_UNALIGNED.withName("Status"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_LONG_UNALIGNED.withName("ID"),
        ValueLayout.JAVA_BYTE.withName("Field01"),
        MemoryLayout.sequenceLayout(7L, ValueLayout.JAVA_BYTE) /* padding */
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

    private static final VarHandle IDVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ID")
    );

    public long getID() {
        return (long) IDVH.get(MEMORY);
    }

    public void setID(long ID) {
        IDVH.set(MEMORY, ID);
    }

    private static final VarHandle Field01VH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Field01")
    );

    public byte getField01() {
        return (byte) Field01VH.get(MEMORY);
    }

    public void setField01(byte Field01) {
        Field01VH.set(MEMORY, Field01);
    }

    public byte getPeerAccepted() {
        var N = getField01();
        return (byte) ((N >> 0) & 0b1);
    }

    public void setPeerAccepted(byte PeerAccepted) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 0);
        PeerAccepted = (byte) (PeerAccepted & 0b1);
        PeerAccepted = (byte) (PeerAccepted << 0);
        N = (byte) ((N & ~MASK) | (PeerAccepted & MASK));
        setField01(N);
    }

    public QuicStreamEventStartComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
        OFFSET += 7; /* padding */
    }

    public QuicStreamEventStartComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEventStartComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventStartComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEventStartComplete.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicStreamEventStartComplete construct(MemorySegment seg) {
            return new QuicStreamEventStartComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventStartComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventStartComplete> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventStartComplete> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventStartComplete> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventStartComplete> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventStartComplete> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicStreamEventStartComplete construct(MemorySegment seg) {
            return new QuicStreamEventStartComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:0e8db7d2c66f65f9ca9b2f9384f7a76d867beed74ad145cf3e09d1f6452e2728
