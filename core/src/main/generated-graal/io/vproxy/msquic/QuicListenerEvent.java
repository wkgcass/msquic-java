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

public class QuicListenerEvent extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("Type"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        io.vproxy.msquic.QuicListenerEventUnion.LAYOUT.withName("Union")
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

    private final io.vproxy.msquic.QuicListenerEventUnion Union;

    public io.vproxy.msquic.QuicListenerEventUnion getUnion() {
        return this.Union;
    }

    public QuicListenerEvent(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        this.Union = new io.vproxy.msquic.QuicListenerEventUnion(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicListenerEventUnion.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicListenerEventUnion.LAYOUT.byteSize();
    }

    public QuicListenerEvent(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicListenerEvent{\n");
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

    public static class Array extends RefArray<QuicListenerEvent> {
        public Array(MemorySegment buf) {
            super(buf, QuicListenerEvent.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicListenerEvent.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicListenerEvent.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicListenerEvent ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListenerEvent.Array";
        }

        @Override
        protected QuicListenerEvent construct(MemorySegment seg) {
            return new QuicListenerEvent(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicListenerEvent value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicListenerEvent> {
        private Func(io.vproxy.pni.CallSite<QuicListenerEvent> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicListenerEvent> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEvent> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEvent> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListenerEvent.Func";
        }

        @Override
        protected QuicListenerEvent construct(MemorySegment seg) {
            return new QuicListenerEvent(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:35e22ff22970891480517b81089fcb8bcb10be1f9aeaef56d82e47185245c79a
