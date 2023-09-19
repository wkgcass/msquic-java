package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventResumed {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT_UNALIGNED.withName("ResumptionStateLength"),
        MemoryLayout.sequenceLayout(6L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS_UNALIGNED.withName("ResumptionState")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle ResumptionStateLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ResumptionStateLength")
    );

    public short getResumptionStateLength() {
        return (short) ResumptionStateLengthVH.get(MEMORY);
    }

    public void setResumptionStateLength(short ResumptionStateLength) {
        ResumptionStateLengthVH.set(MEMORY, ResumptionStateLength);
    }

    private static final VarHandle ResumptionStateVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ResumptionState")
    );

    public MemorySegment getResumptionState() {
        var SEG = (MemorySegment) ResumptionStateVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setResumptionState(MemorySegment ResumptionState) {
        if (ResumptionState == null) {
            ResumptionStateVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ResumptionStateVH.set(MEMORY, ResumptionState);
        }
    }

    public QuicConnectionEventResumed(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += 6; /* padding */
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicConnectionEventResumed(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventResumed> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventResumed.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventResumed.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventResumed construct(MemorySegment seg) {
            return new QuicConnectionEventResumed(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventResumed value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventResumed> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventResumed> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventResumed> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventResumed> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventResumed> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventResumed construct(MemorySegment seg) {
            return new QuicConnectionEventResumed(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:6aad48a7b7faa0a2df055d1d2d75615ccf6cbf27546c59d82165dcd6b44203c3
