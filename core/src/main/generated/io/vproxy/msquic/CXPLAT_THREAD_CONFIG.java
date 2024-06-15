package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class CXPLAT_THREAD_CONFIG extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT.withName("Flags"),
        ValueLayout.JAVA_SHORT.withName("IdealProcessor"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS.withName("Name"),
        ValueLayout.ADDRESS.withName("Callback"),
        ValueLayout.ADDRESS.withName("Context")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW FlagsVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Flags")
        )
    );

    public short getFlags() {
        return FlagsVH.getShort(MEMORY);
    }

    public void setFlags(short Flags) {
        FlagsVH.set(MEMORY, Flags);
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

    private static final VarHandleW NameVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Name")
        )
    );

    public PNIString getName() {
        var SEG = NameVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return new PNIString(SEG);
    }

    public void setName(String Name, Allocator ALLOCATOR) {
        this.setName(new PNIString(ALLOCATOR, Name));
    }

    public void setName(PNIString Name) {
        if (Name == null) {
            NameVH.set(MEMORY, MemorySegment.NULL);
        } else {
            NameVH.set(MEMORY, Name.MEMORY);
        }
    }

    private static final VarHandleW CallbackVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Callback")
        )
    );

    public MemorySegment getCallback() {
        var SEG = CallbackVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setCallback(MemorySegment Callback) {
        if (Callback == null) {
            CallbackVH.set(MEMORY, MemorySegment.NULL);
        } else {
            CallbackVH.set(MEMORY, Callback);
        }
    }

    private static final VarHandleW ContextVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Context")
        )
    );

    public MemorySegment getContext() {
        var SEG = ContextVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setContext(MemorySegment Context) {
        if (Context == null) {
            ContextVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ContextVH.set(MEMORY, Context);
        }
    }

    public CXPLAT_THREAD_CONFIG(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += 8;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public CXPLAT_THREAD_CONFIG(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("CXPLAT_THREAD_CONFIG{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Flags => ");
            SB.append(getFlags());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IdealProcessor => ");
            SB.append(getIdealProcessor());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Name => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getName(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Callback => ");
            SB.append(PanamaUtils.memorySegmentToString(getCallback()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Context => ");
            SB.append(PanamaUtils.memorySegmentToString(getContext()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<CXPLAT_THREAD_CONFIG> {
        public Array(MemorySegment buf) {
            super(buf, CXPLAT_THREAD_CONFIG.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, CXPLAT_THREAD_CONFIG.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, CXPLAT_THREAD_CONFIG.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.CXPLAT_THREAD_CONFIG ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "CXPLAT_THREAD_CONFIG.Array";
        }

        @Override
        protected CXPLAT_THREAD_CONFIG construct(MemorySegment seg) {
            return new CXPLAT_THREAD_CONFIG(seg);
        }

        @Override
        protected MemorySegment getSegment(CXPLAT_THREAD_CONFIG value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<CXPLAT_THREAD_CONFIG> {
        private Func(io.vproxy.pni.CallSite<CXPLAT_THREAD_CONFIG> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<CXPLAT_THREAD_CONFIG> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<CXPLAT_THREAD_CONFIG> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<CXPLAT_THREAD_CONFIG> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "CXPLAT_THREAD_CONFIG.Func";
        }

        @Override
        protected CXPLAT_THREAD_CONFIG construct(MemorySegment seg) {
            return new CXPLAT_THREAD_CONFIG(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:3122d5cd4208bd1f9387d98c2888d89201f63bb8edfb8f2618ba67c4d837c388
