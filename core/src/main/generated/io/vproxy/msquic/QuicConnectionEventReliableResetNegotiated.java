package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventReliableResetNegotiated {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("IsNegotiated")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle IsNegotiatedVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("IsNegotiated")
    );

    public boolean getIsNegotiated() {
        return (boolean) IsNegotiatedVH.get(MEMORY);
    }

    public void setIsNegotiated(boolean IsNegotiated) {
        IsNegotiatedVH.set(MEMORY, IsNegotiated);
    }

    public QuicConnectionEventReliableResetNegotiated(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
    }

    public QuicConnectionEventReliableResetNegotiated(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventReliableResetNegotiated> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventReliableResetNegotiated.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventReliableResetNegotiated.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventReliableResetNegotiated construct(MemorySegment seg) {
            return new QuicConnectionEventReliableResetNegotiated(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventReliableResetNegotiated value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventReliableResetNegotiated> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventReliableResetNegotiated> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventReliableResetNegotiated> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventReliableResetNegotiated construct(MemorySegment seg) {
            return new QuicConnectionEventReliableResetNegotiated(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:25ac88960b8a94604b511c019756e93ed85dcd41463e2155e115fb43f3e62d51
