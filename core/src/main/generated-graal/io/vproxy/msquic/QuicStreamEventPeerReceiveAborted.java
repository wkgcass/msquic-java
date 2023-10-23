package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicStreamEventPeerReceiveAborted extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("ErrorCode")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle ErrorCodeVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ErrorCode")
    );

    public long getErrorCode() {
        return (long) ErrorCodeVH.get(MEMORY);
    }

    public void setErrorCode(long ErrorCode) {
        ErrorCodeVH.set(MEMORY, ErrorCode);
    }

    public QuicStreamEventPeerReceiveAborted(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicStreamEventPeerReceiveAborted(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicStreamEventPeerReceiveAborted{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ErrorCode => ");
            SB.append(getErrorCode());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStreamEventPeerReceiveAborted> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventPeerReceiveAborted.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEventPeerReceiveAborted.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEventPeerReceiveAborted.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEventPeerReceiveAborted ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventPeerReceiveAborted.Array";
        }

        @Override
        protected QuicStreamEventPeerReceiveAborted construct(MemorySegment seg) {
            return new QuicStreamEventPeerReceiveAborted(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventPeerReceiveAborted value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventPeerReceiveAborted> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventPeerReceiveAborted> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventPeerReceiveAborted> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventPeerReceiveAborted> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventPeerReceiveAborted> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventPeerReceiveAborted.Func";
        }

        @Override
        protected QuicStreamEventPeerReceiveAborted construct(MemorySegment seg) {
            return new QuicStreamEventPeerReceiveAborted(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:dee6f2da30eaa1462e70cb4eaa7c7cef60b3c030b7cc6de857c22137facedddf
