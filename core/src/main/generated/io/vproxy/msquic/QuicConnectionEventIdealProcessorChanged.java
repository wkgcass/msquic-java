package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventIdealProcessorChanged {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT_UNALIGNED.withName("IdealProcessor"),
        ValueLayout.JAVA_SHORT_UNALIGNED.withName("PartitionIndex")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle IdealProcessorVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("IdealProcessor")
    );

    public short getIdealProcessor() {
        return (short) IdealProcessorVH.get(MEMORY);
    }

    public void setIdealProcessor(short IdealProcessor) {
        IdealProcessorVH.set(MEMORY, IdealProcessor);
    }

    private static final VarHandle PartitionIndexVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("PartitionIndex")
    );

    public short getPartitionIndex() {
        return (short) PartitionIndexVH.get(MEMORY);
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
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventIdealProcessorChanged> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventIdealProcessorChanged.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventIdealProcessorChanged.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
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
        protected QuicConnectionEventIdealProcessorChanged construct(MemorySegment seg) {
            return new QuicConnectionEventIdealProcessorChanged(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:9c68bd89ada6689ea5e7b1d4535f0a6e8f7aa08c1e8155339d140470b55cd1be
