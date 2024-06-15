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

public class QuicStreamEvent extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("Type"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        io.vproxy.msquic.QuicStreamEventUnion.LAYOUT.withName("Union")
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

    private final io.vproxy.msquic.QuicStreamEventUnion Union;

    public io.vproxy.msquic.QuicStreamEventUnion getUnion() {
        return this.Union;
    }

    public QuicStreamEvent(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        this.Union = new io.vproxy.msquic.QuicStreamEventUnion(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventUnion.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventUnion.LAYOUT.byteSize();
    }

    public QuicStreamEvent(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicStreamEvent{\n");
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

    public static class Array extends RefArray<QuicStreamEvent> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEvent.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEvent.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEvent.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEvent ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEvent.Array";
        }

        @Override
        protected QuicStreamEvent construct(MemorySegment seg) {
            return new QuicStreamEvent(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEvent value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEvent> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEvent> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEvent> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEvent> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEvent> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEvent.Func";
        }

        @Override
        protected QuicStreamEvent construct(MemorySegment seg) {
            return new QuicStreamEvent(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:40fdf09bfbbee34c31355173c6a53bc9e55bacb1efedad5860f100d89fbd2518
