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

public class QuicConnectionEventShutdownInitiatedByPeer extends AbstractNativeObject implements NativeObject {
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

    public QuicConnectionEventShutdownInitiatedByPeer(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicConnectionEventShutdownInitiatedByPeer(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventShutdownInitiatedByPeer{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ErrorCode => ");
            SB.append(getErrorCode());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventShutdownInitiatedByPeer> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventShutdownInitiatedByPeer.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventShutdownInitiatedByPeer.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventShutdownInitiatedByPeer.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByPeer ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventShutdownInitiatedByPeer.Array";
        }

        @Override
        protected QuicConnectionEventShutdownInitiatedByPeer construct(MemorySegment seg) {
            return new QuicConnectionEventShutdownInitiatedByPeer(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventShutdownInitiatedByPeer value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventShutdownInitiatedByPeer> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByPeer> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByPeer> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByPeer> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByPeer> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventShutdownInitiatedByPeer.Func";
        }

        @Override
        protected QuicConnectionEventShutdownInitiatedByPeer construct(MemorySegment seg) {
            return new QuicConnectionEventShutdownInitiatedByPeer(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:cfaee368642b25c18465c12abfc4c049ec5c354c72ab14d9f3b22ec92492d273
