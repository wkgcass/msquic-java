package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventPeerSendAborted extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("ErrorCode")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW ErrorCodeVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ErrorCode")
        )
    );

    public long getErrorCode() {
        return ErrorCodeVH.getLong(MEMORY);
    }

    public void setErrorCode(long ErrorCode) {
        ErrorCodeVH.set(MEMORY, ErrorCode);
    }

    public QuicStreamEventPeerSendAborted(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicStreamEventPeerSendAborted(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicStreamEventPeerSendAborted{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ErrorCode => ");
            SB.append(getErrorCode());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStreamEventPeerSendAborted> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventPeerSendAborted.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEventPeerSendAborted.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEventPeerSendAborted.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEventPeerSendAborted ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventPeerSendAborted.Array";
        }

        @Override
        protected QuicStreamEventPeerSendAborted construct(MemorySegment seg) {
            return new QuicStreamEventPeerSendAborted(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventPeerSendAborted value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventPeerSendAborted> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventPeerSendAborted> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventPeerSendAborted> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventPeerSendAborted> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventPeerSendAborted> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventPeerSendAborted.Func";
        }

        @Override
        protected QuicStreamEventPeerSendAborted construct(MemorySegment seg) {
            return new QuicStreamEventPeerSendAborted(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:7d8477d9283b0a56366892d0e504cbb1a039cc21be7f0f1322536c930f6774ce
