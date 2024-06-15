package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventStreamsAvailable extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT.withName("BidirectionalCount"),
        ValueLayout.JAVA_SHORT.withName("UnidirectionalCount")
    ).withByteAlignment(2);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW BidirectionalCountVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("BidirectionalCount")
        )
    );

    public short getBidirectionalCount() {
        return BidirectionalCountVH.getShort(MEMORY);
    }

    public void setBidirectionalCount(short BidirectionalCount) {
        BidirectionalCountVH.set(MEMORY, BidirectionalCount);
    }

    private static final VarHandleW UnidirectionalCountVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("UnidirectionalCount")
        )
    );

    public short getUnidirectionalCount() {
        return UnidirectionalCountVH.getShort(MEMORY);
    }

    public void setUnidirectionalCount(short UnidirectionalCount) {
        UnidirectionalCountVH.set(MEMORY, UnidirectionalCount);
    }

    public QuicConnectionEventStreamsAvailable(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
    }

    public QuicConnectionEventStreamsAvailable(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventStreamsAvailable{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("BidirectionalCount => ");
            SB.append(getBidirectionalCount());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("UnidirectionalCount => ");
            SB.append(getUnidirectionalCount());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventStreamsAvailable> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventStreamsAvailable.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventStreamsAvailable.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventStreamsAvailable.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventStreamsAvailable ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventStreamsAvailable.Array";
        }

        @Override
        protected QuicConnectionEventStreamsAvailable construct(MemorySegment seg) {
            return new QuicConnectionEventStreamsAvailable(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventStreamsAvailable value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventStreamsAvailable> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventStreamsAvailable> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventStreamsAvailable> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventStreamsAvailable> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventStreamsAvailable> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventStreamsAvailable.Func";
        }

        @Override
        protected QuicConnectionEventStreamsAvailable construct(MemorySegment seg) {
            return new QuicConnectionEventStreamsAvailable(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:85fee2b1f3f519fbe2b32518b018254ff925dd615cb6624da83308eebee8256f
