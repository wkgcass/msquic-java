package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventDatagramReceived {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        io.vproxy.msquic.QuicBuffer.LAYOUT.withName("Buffer"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("Flags"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    );
    public final MemorySegment MEMORY;

    private final io.vproxy.msquic.QuicBuffer Buffer;

    public io.vproxy.msquic.QuicBuffer getBuffer() {
        return this.Buffer;
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

    public QuicConnectionEventDatagramReceived(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        this.Buffer = new io.vproxy.msquic.QuicBuffer(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicBuffer.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicBuffer.LAYOUT.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
    }

    public QuicConnectionEventDatagramReceived(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventDatagramReceived> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventDatagramReceived.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventDatagramReceived.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventDatagramReceived construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramReceived(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventDatagramReceived value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventDatagramReceived> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramReceived> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramReceived> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventDatagramReceived construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramReceived(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:f1b8de699d80061186a49f8e2210883d5f9eef370db2caa00cc5547a7f658e85
