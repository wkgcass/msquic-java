package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventResumptionTicketReceived {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT_UNALIGNED.withName("ResumptionTicketLength"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS_UNALIGNED.withName("ResumptionTicket")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle ResumptionTicketLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ResumptionTicketLength")
    );

    public int getResumptionTicketLength() {
        return (int) ResumptionTicketLengthVH.get(MEMORY);
    }

    public void setResumptionTicketLength(int ResumptionTicketLength) {
        ResumptionTicketLengthVH.set(MEMORY, ResumptionTicketLength);
    }

    private static final VarHandle ResumptionTicketVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ResumptionTicket")
    );

    public MemorySegment getResumptionTicket() {
        var SEG = (MemorySegment) ResumptionTicketVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setResumptionTicket(MemorySegment ResumptionTicket) {
        if (ResumptionTicket == null) {
            ResumptionTicketVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ResumptionTicketVH.set(MEMORY, ResumptionTicket);
        }
    }

    public QuicConnectionEventResumptionTicketReceived(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicConnectionEventResumptionTicketReceived(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventResumptionTicketReceived> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventResumptionTicketReceived.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventResumptionTicketReceived.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventResumptionTicketReceived construct(MemorySegment seg) {
            return new QuicConnectionEventResumptionTicketReceived(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventResumptionTicketReceived value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventResumptionTicketReceived> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventResumptionTicketReceived> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventResumptionTicketReceived> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventResumptionTicketReceived> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventResumptionTicketReceived> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventResumptionTicketReceived construct(MemorySegment seg) {
            return new QuicConnectionEventResumptionTicketReceived(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:7d2a67350a48b4ee044b4f12f092e6b5f7eff07032f054efe5b974964f1a6375
