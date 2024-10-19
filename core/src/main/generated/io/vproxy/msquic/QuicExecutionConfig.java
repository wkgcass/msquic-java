package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicExecutionConfig extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("flags"),
        ValueLayout.JAVA_INT.withName("pollingIdleTimeoutUs"),
        ValueLayout.JAVA_INT.withName("processorCount"),
        MemoryLayout.sequenceLayout(1L, ValueLayout.JAVA_SHORT).withName("processorList"),
        MemoryLayout.sequenceLayout(2L, ValueLayout.JAVA_BYTE) /* padding */
    ).withByteAlignment(4);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW flagsVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("flags")
        )
    );

    public int getFlags() {
        return flagsVH.getInt(MEMORY);
    }

    public void setFlags(int flags) {
        flagsVH.set(MEMORY, flags);
    }

    private static final VarHandleW pollingIdleTimeoutUsVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("pollingIdleTimeoutUs")
        )
    );

    public int getPollingIdleTimeoutUs() {
        return pollingIdleTimeoutUsVH.getInt(MEMORY);
    }

    public void setPollingIdleTimeoutUs(int pollingIdleTimeoutUs) {
        pollingIdleTimeoutUsVH.set(MEMORY, pollingIdleTimeoutUs);
    }

    private static final VarHandleW processorCountVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("processorCount")
        )
    );

    public int getProcessorCount() {
        return processorCountVH.getInt(MEMORY);
    }

    public void setProcessorCount(int processorCount) {
        processorCountVH.set(MEMORY, processorCount);
    }

    private final ShortArray processorList;

    public ShortArray getProcessorList() {
        return this.processorList;
    }

    public QuicExecutionConfig(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        this.processorList = new ShortArray(MEMORY.asSlice(OFFSET, 1 * ValueLayout.JAVA_SHORT_UNALIGNED.byteSize()));
        OFFSET += 1 * ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += 2; /* padding */
    }

    public QuicExecutionConfig(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicExecutionConfig{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("flags => ");
            SB.append(getFlags());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("pollingIdleTimeoutUs => ");
            SB.append(getPollingIdleTimeoutUs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("processorCount => ");
            SB.append(getProcessorCount());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("processorList => ");
            PanamaUtils.nativeObjectToString(getProcessorList(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicExecutionConfig> {
        public Array(MemorySegment buf) {
            super(buf, QuicExecutionConfig.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicExecutionConfig.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicExecutionConfig.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicExecutionConfig ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicExecutionConfig.Array";
        }

        @Override
        protected QuicExecutionConfig construct(MemorySegment seg) {
            return new QuicExecutionConfig(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicExecutionConfig value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicExecutionConfig> {
        private Func(io.vproxy.pni.CallSite<QuicExecutionConfig> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicExecutionConfig> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicExecutionConfig> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicExecutionConfig> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicExecutionConfig.Func";
        }

        @Override
        protected QuicExecutionConfig construct(MemorySegment seg) {
            return new QuicExecutionConfig(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:c9b3a675bd8c8055f9f842847501a77bd6a5a0fbac23c5a7103d7a31a158add8
