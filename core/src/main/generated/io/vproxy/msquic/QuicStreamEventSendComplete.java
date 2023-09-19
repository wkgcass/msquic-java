package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventSendComplete {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("Canceled"),
        MemoryLayout.sequenceLayout(7L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS_UNALIGNED.withName("ClientContext")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle CanceledVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Canceled")
    );

    public boolean getCanceled() {
        return (boolean) CanceledVH.get(MEMORY);
    }

    public void setCanceled(boolean Canceled) {
        CanceledVH.set(MEMORY, Canceled);
    }

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

    public QuicStreamEventSendComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
        OFFSET += 7; /* padding */
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicStreamEventSendComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEventSendComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventSendComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEventSendComplete.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicStreamEventSendComplete construct(MemorySegment seg) {
            return new QuicStreamEventSendComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventSendComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventSendComplete> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventSendComplete> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventSendComplete> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventSendComplete> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventSendComplete> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicStreamEventSendComplete construct(MemorySegment seg) {
            return new QuicStreamEventSendComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:f72d50fc7e518412912ab6b75e0b147dc33afc5c2299ef1f33ae6151444e12f6
