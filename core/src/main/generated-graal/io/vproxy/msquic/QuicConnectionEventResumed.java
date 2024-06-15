package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicConnectionEventResumed extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT.withName("ResumptionStateLength"),
        MemoryLayout.sequenceLayout(6L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS.withName("ResumptionState")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW ResumptionStateLengthVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ResumptionStateLength")
        )
    );

    public short getResumptionStateLength() {
        return ResumptionStateLengthVH.getShort(MEMORY);
    }

    public void setResumptionStateLength(short ResumptionStateLength) {
        ResumptionStateLengthVH.set(MEMORY, ResumptionStateLength);
    }

    private static final VarHandleW ResumptionStateVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ResumptionState")
        )
    );

    public MemorySegment getResumptionState() {
        var SEG = ResumptionStateVH.getMemorySegment(MEMORY);
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
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventResumed{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ResumptionStateLength => ");
            SB.append(getResumptionStateLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ResumptionState => ");
            SB.append(PanamaUtils.memorySegmentToString(getResumptionState()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventResumed> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventResumed.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventResumed.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventResumed.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventResumed ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventResumed.Array";
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
        protected String toStringTypeName() {
            return "QuicConnectionEventResumed.Func";
        }

        @Override
        protected QuicConnectionEventResumed construct(MemorySegment seg) {
            return new QuicConnectionEventResumed(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:755f5e1752278c7e81c4613825ec35ef839c5e2a55efcae01794a415b1c5ba6e
