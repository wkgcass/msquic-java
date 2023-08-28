package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicBuffer {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT_UNALIGNED.withName("Length"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS_UNALIGNED.withName("Buffer")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle LengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Length")
    );

    public int getLength() {
        return (int) LengthVH.get(MEMORY);
    }

    public void setLength(int Length) {
        LengthVH.set(MEMORY, Length);
    }

    private static final VarHandle BufferVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Buffer")
    );

    public MemorySegment getBuffer() {
        var SEG = (MemorySegment) BufferVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setBuffer(MemorySegment Buffer) {
        if (Buffer == null) {
            BufferVH.set(MEMORY, MemorySegment.NULL);
        } else {
            BufferVH.set(MEMORY, Buffer);
        }
    }

    public QuicBuffer(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicBuffer(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicBuffer> {
        public Array(MemorySegment buf) {
            super(buf, QuicBuffer.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicBuffer.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicBuffer construct(MemorySegment seg) {
            return new QuicBuffer(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicBuffer value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicBuffer> {
        private Func(io.vproxy.pni.CallSite<QuicBuffer> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicBuffer> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicBuffer construct(MemorySegment seg) {
            return new QuicBuffer(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:68dfba6e7f54b75b9eee8c40b717fbea6f296f9b9a99b723491feefc8312ffd0
