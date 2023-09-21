package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventSendShutdownComplete extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("Graceful")
    );
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle GracefulVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Graceful")
    );

    public boolean getGraceful() {
        return (boolean) GracefulVH.get(MEMORY);
    }

    public void setGraceful(boolean Graceful) {
        GracefulVH.set(MEMORY, Graceful);
    }

    public QuicStreamEventSendShutdownComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
    }

    public QuicStreamEventSendShutdownComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicStreamEventSendShutdownComplete{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Graceful => ");
            SB.append(getGraceful());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStreamEventSendShutdownComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventSendShutdownComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEventSendShutdownComplete.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEventSendShutdownComplete.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEventSendShutdownComplete ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventSendShutdownComplete.Array";
        }

        @Override
        protected QuicStreamEventSendShutdownComplete construct(MemorySegment seg) {
            return new QuicStreamEventSendShutdownComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventSendShutdownComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventSendShutdownComplete> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventSendShutdownComplete> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventSendShutdownComplete> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventSendShutdownComplete> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventSendShutdownComplete> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventSendShutdownComplete.Func";
        }

        @Override
        protected QuicStreamEventSendShutdownComplete construct(MemorySegment seg) {
            return new QuicStreamEventSendShutdownComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.15
// sha256:6dfb71f997b1a425b750c52e7a492041105e899efbd5cb3d9e0c9c66d05a1de0
