package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEvent extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("Type"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        io.vproxy.msquic.QuicConnectionEventUnion.LAYOUT.withName("Union")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW TypeVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Type")
        )
    );

    public int getType() {
        return TypeVH.getInt(MEMORY);
    }

    public void setType(int Type) {
        TypeVH.set(MEMORY, Type);
    }

    private final io.vproxy.msquic.QuicConnectionEventUnion Union;

    public io.vproxy.msquic.QuicConnectionEventUnion getUnion() {
        return this.Union;
    }

    public QuicConnectionEvent(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        this.Union = new io.vproxy.msquic.QuicConnectionEventUnion(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventUnion.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventUnion.LAYOUT.byteSize();
    }

    public QuicConnectionEvent(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEvent{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Type => ");
            SB.append(getType());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Union => ");
            PanamaUtils.nativeObjectToString(getUnion(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEvent> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEvent.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEvent.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEvent.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEvent ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEvent.Array";
        }

        @Override
        protected QuicConnectionEvent construct(MemorySegment seg) {
            return new QuicConnectionEvent(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEvent value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEvent> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEvent> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEvent> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEvent> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEvent> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEvent.Func";
        }

        @Override
        protected QuicConnectionEvent construct(MemorySegment seg) {
            return new QuicConnectionEvent(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:0c95508d1152a1d5c9621de369761c4dcba38864e5e4ecf45cba531d230db637
