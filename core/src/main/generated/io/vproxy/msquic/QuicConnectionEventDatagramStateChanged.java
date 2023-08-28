package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventDatagramStateChanged {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("SendEnabled"),
        MemoryLayout.sequenceLayout(1L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_SHORT_UNALIGNED.withName("MaxSendLength")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle SendEnabledVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("SendEnabled")
    );

    public boolean getSendEnabled() {
        return (boolean) SendEnabledVH.get(MEMORY);
    }

    public void setSendEnabled(boolean SendEnabled) {
        SendEnabledVH.set(MEMORY, SendEnabled);
    }

    private static final VarHandle MaxSendLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MaxSendLength")
    );

    public short getMaxSendLength() {
        return (short) MaxSendLengthVH.get(MEMORY);
    }

    public void setMaxSendLength(short MaxSendLength) {
        MaxSendLengthVH.set(MEMORY, MaxSendLength);
    }

    public QuicConnectionEventDatagramStateChanged(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
        OFFSET += 1; /* padding */
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
    }

    public QuicConnectionEventDatagramStateChanged(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventDatagramStateChanged> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventDatagramStateChanged.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventDatagramStateChanged.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventDatagramStateChanged construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramStateChanged(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventDatagramStateChanged value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventDatagramStateChanged> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramStateChanged> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramStateChanged> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventDatagramStateChanged construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramStateChanged(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:e32b5d444624d6005b3f5c39395147adc7dc2d79db6bc1f74cc9a15f5cbff23c
