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

public class QuicListenerEventUnion extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.unionLayout(
        io.vproxy.msquic.QuicListenerEventNewConnection.LAYOUT.withName("NEW_CONNECTION"),
        io.vproxy.msquic.QuicListenerEventStopComplete.LAYOUT.withName("STOP_COMPLETE")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private final io.vproxy.msquic.QuicListenerEventNewConnection NEW_CONNECTION;

    public io.vproxy.msquic.QuicListenerEventNewConnection getNEW_CONNECTION() {
        return this.NEW_CONNECTION;
    }

    private final io.vproxy.msquic.QuicListenerEventStopComplete STOP_COMPLETE;

    public io.vproxy.msquic.QuicListenerEventStopComplete getSTOP_COMPLETE() {
        return this.STOP_COMPLETE;
    }

    public QuicListenerEventUnion(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        this.NEW_CONNECTION = new io.vproxy.msquic.QuicListenerEventNewConnection(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicListenerEventNewConnection.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicListenerEventNewConnection.LAYOUT.byteSize();
        OFFSET = 0;
        this.STOP_COMPLETE = new io.vproxy.msquic.QuicListenerEventStopComplete(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicListenerEventStopComplete.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicListenerEventStopComplete.LAYOUT.byteSize();
        OFFSET = 0;
    }

    public QuicListenerEventUnion(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        CORRUPTED_MEMORY = true;
        SB.append("QuicListenerEventUnion(\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("NEW_CONNECTION => ");
            PanamaUtils.nativeObjectToString(getNEW_CONNECTION(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("STOP_COMPLETE => ");
            PanamaUtils.nativeObjectToString(getSTOP_COMPLETE(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append(")@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicListenerEventUnion> {
        public Array(MemorySegment buf) {
            super(buf, QuicListenerEventUnion.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicListenerEventUnion.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicListenerEventUnion.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicListenerEventUnion ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListenerEventUnion.Array";
        }

        @Override
        protected QuicListenerEventUnion construct(MemorySegment seg) {
            return new QuicListenerEventUnion(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicListenerEventUnion value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicListenerEventUnion> {
        private Func(io.vproxy.pni.CallSite<QuicListenerEventUnion> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicListenerEventUnion> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventUnion> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventUnion> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListenerEventUnion.Func";
        }

        @Override
        protected QuicListenerEventUnion construct(MemorySegment seg) {
            return new QuicListenerEventUnion(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:67f0711f2a9cba14875a66d060bcdf75b930dfd180f0045f379032b0e14c7588
