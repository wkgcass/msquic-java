package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventDatagramSendStateChanged {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("ClientContext"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("State"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    );
    public final MemorySegment MEMORY;

    private static final VarHandle ClientContextVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ClientContext")
    );

    public MemorySegment getClientContext() {
        var SEG = (MemorySegment) ClientContextVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setClientContext(MemorySegment ClientContext) {
        if (ClientContext == null) {
            ClientContextVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ClientContextVH.set(MEMORY, ClientContext);
        }
    }

    private static final VarHandle StateVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("State")
    );

    public int getState() {
        return (int) StateVH.get(MEMORY);
    }

    public void setState(int State) {
        StateVH.set(MEMORY, State);
    }

    public QuicConnectionEventDatagramSendStateChanged(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
    }

    public QuicConnectionEventDatagramSendStateChanged(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventDatagramSendStateChanged> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventDatagramSendStateChanged.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventDatagramSendStateChanged.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventDatagramSendStateChanged construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramSendStateChanged(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventDatagramSendStateChanged value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventDatagramSendStateChanged> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramSendStateChanged> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramSendStateChanged> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramSendStateChanged> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramSendStateChanged> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventDatagramSendStateChanged construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramSendStateChanged(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:1b32f41c8f55d2b1bbf36aee419e762f50851a79b5d5f461916d254c61874567
