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

public class QuicConnectionEventIdealProcessorChanged extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT.withName("IdealProcessor"),
        ValueLayout.JAVA_SHORT.withName("PartitionIndex")
    ).withByteAlignment(2);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW IdealProcessorVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("IdealProcessor")
        )
    );

    public short getIdealProcessor() {
        return IdealProcessorVH.getShort(MEMORY);
    }

    public void setIdealProcessor(short IdealProcessor) {
        IdealProcessorVH.set(MEMORY, IdealProcessor);
    }

    private static final VarHandleW PartitionIndexVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("PartitionIndex")
        )
    );

    public short getPartitionIndex() {
        return PartitionIndexVH.getShort(MEMORY);
    }

    public void setPartitionIndex(short PartitionIndex) {
        PartitionIndexVH.set(MEMORY, PartitionIndex);
    }

    public QuicConnectionEventIdealProcessorChanged(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
    }

    public QuicConnectionEventIdealProcessorChanged(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventIdealProcessorChanged{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IdealProcessor => ");
            SB.append(getIdealProcessor());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PartitionIndex => ");
            SB.append(getPartitionIndex());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventIdealProcessorChanged> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventIdealProcessorChanged.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventIdealProcessorChanged.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventIdealProcessorChanged.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventIdealProcessorChanged ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventIdealProcessorChanged.Array";
        }

        @Override
        protected QuicConnectionEventIdealProcessorChanged construct(MemorySegment seg) {
            return new QuicConnectionEventIdealProcessorChanged(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventIdealProcessorChanged value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventIdealProcessorChanged> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventIdealProcessorChanged> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventIdealProcessorChanged> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventIdealProcessorChanged> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventIdealProcessorChanged> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventIdealProcessorChanged.Func";
        }

        @Override
        protected QuicConnectionEventIdealProcessorChanged construct(MemorySegment seg) {
            return new QuicConnectionEventIdealProcessorChanged(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:c407476b959e39f17a32da0c826abd4300c7bb7363edc60341a451bbd331660b
