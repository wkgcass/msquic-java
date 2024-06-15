package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventIdealSendBufferSize extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("ByteCount")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW ByteCountVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ByteCount")
        )
    );

    public long getByteCount() {
        return ByteCountVH.getLong(MEMORY);
    }

    public void setByteCount(long ByteCount) {
        ByteCountVH.set(MEMORY, ByteCount);
    }

    public QuicStreamEventIdealSendBufferSize(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicStreamEventIdealSendBufferSize(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicStreamEventIdealSendBufferSize{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ByteCount => ");
            SB.append(getByteCount());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStreamEventIdealSendBufferSize> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventIdealSendBufferSize.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEventIdealSendBufferSize.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEventIdealSendBufferSize.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEventIdealSendBufferSize ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventIdealSendBufferSize.Array";
        }

        @Override
        protected QuicStreamEventIdealSendBufferSize construct(MemorySegment seg) {
            return new QuicStreamEventIdealSendBufferSize(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventIdealSendBufferSize value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventIdealSendBufferSize> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventIdealSendBufferSize> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventIdealSendBufferSize> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventIdealSendBufferSize> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventIdealSendBufferSize> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventIdealSendBufferSize.Func";
        }

        @Override
        protected QuicStreamEventIdealSendBufferSize construct(MemorySegment seg) {
            return new QuicStreamEventIdealSendBufferSize(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:c5dbf158914f705b22f3e71df7f9cf64d44a82d54157467953d50de715b4a964
