package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicConnectionEventResumptionTicketReceived extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("ResumptionTicketLength"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS.withName("ResumptionTicket")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

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
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventResumptionTicketReceived{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ResumptionTicketLength => ");
            SB.append(getResumptionTicketLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ResumptionTicket => ");
            SB.append(PanamaUtils.memorySegmentToString(getResumptionTicket()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventResumptionTicketReceived> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventResumptionTicketReceived.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventResumptionTicketReceived.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventResumptionTicketReceived.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventResumptionTicketReceived ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventResumptionTicketReceived.Array";
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
        protected String toStringTypeName() {
            return "QuicConnectionEventResumptionTicketReceived.Func";
        }

        @Override
        protected QuicConnectionEventResumptionTicketReceived construct(MemorySegment seg) {
            return new QuicConnectionEventResumptionTicketReceived(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:c7cd0ee5874c3c5e12ac4d1cd9ead6c25cdb6be7dee715ce6b0ace5fca6e7193
