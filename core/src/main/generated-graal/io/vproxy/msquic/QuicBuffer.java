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

public class QuicBuffer extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("Length"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS.withName("Buffer")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW LengthVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Length")
        )
    );

    public int getLength() {
        return LengthVH.getInt(MEMORY);
    }

    public void setLength(int Length) {
        LengthVH.set(MEMORY, Length);
    }

    private static final VarHandleW BufferVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Buffer")
        )
    );

    public MemorySegment getBuffer() {
        var SEG = BufferVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setBuffer(MemorySegment Buffer) {
        if (Buffer == null) {
            BufferVH.set(MEMORY, MemorySegment.NULL);
        } else {
            BufferVH.set(MEMORY, Buffer);
        }
    }

    public QuicBuffer(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicBuffer(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicBuffer{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Length => ");
            SB.append(getLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Buffer => ");
            SB.append(PanamaUtils.memorySegmentToString(getBuffer()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicBuffer> {
        public Array(MemorySegment buf) {
            super(buf, QuicBuffer.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicBuffer.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicBuffer.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicBuffer ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicBuffer.Array";
        }

        @Override
        protected QuicBuffer construct(MemorySegment seg) {
            return new QuicBuffer(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicBuffer value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicBuffer> {
        private Func(io.vproxy.pni.CallSite<QuicBuffer> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicBuffer> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicBuffer> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicBuffer> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicBuffer.Func";
        }

        @Override
        protected QuicBuffer construct(MemorySegment seg) {
            return new QuicBuffer(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:d4c7e5cf49575951be7185c4f702002846485b7e67c22f605abf78af3b154a5b
