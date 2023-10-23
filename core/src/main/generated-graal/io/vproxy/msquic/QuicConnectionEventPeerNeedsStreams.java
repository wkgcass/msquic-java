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

public class QuicConnectionEventPeerNeedsStreams extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("Bidirectional")
    );
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle BidirectionalVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Bidirectional")
    );

    public boolean getBidirectional() {
        return (boolean) BidirectionalVH.get(MEMORY);
    }

    public void setBidirectional(boolean Bidirectional) {
        BidirectionalVH.set(MEMORY, Bidirectional);
    }

    public QuicConnectionEventPeerNeedsStreams(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
    }

    public QuicConnectionEventPeerNeedsStreams(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventPeerNeedsStreams{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Bidirectional => ");
            SB.append(getBidirectional());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventPeerNeedsStreams> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventPeerNeedsStreams.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventPeerNeedsStreams.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventPeerNeedsStreams.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventPeerNeedsStreams ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventPeerNeedsStreams.Array";
        }

        @Override
        protected QuicConnectionEventPeerNeedsStreams construct(MemorySegment seg) {
            return new QuicConnectionEventPeerNeedsStreams(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventPeerNeedsStreams value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventPeerNeedsStreams> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerNeedsStreams> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerNeedsStreams> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerNeedsStreams> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerNeedsStreams> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventPeerNeedsStreams.Func";
        }

        @Override
        protected QuicConnectionEventPeerNeedsStreams construct(MemorySegment seg) {
            return new QuicConnectionEventPeerNeedsStreams(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:7698263e66782545f64372301654f09de30c704bdcc4847a80011e024af2f4e0
