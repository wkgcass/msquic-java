package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventSendComplete extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("Canceled"),
        MemoryLayout.sequenceLayout(7L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS.withName("ClientContext")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW CanceledVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Canceled")
        )
    );

    public boolean isCanceled() {
        return CanceledVH.getBool(MEMORY);
    }

    public void setCanceled(boolean Canceled) {
        CanceledVH.set(MEMORY, Canceled);
    }

    private static final VarHandleW ClientContextVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ClientContext")
        )
    );

    public MemorySegment getClientContext() {
        var SEG = ClientContextVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setClientContext(MemorySegment ClientContext) {
        if (ClientContext == null) {
            ClientContextVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ClientContextVH.set(MEMORY, ClientContext);
        }
    }

    public QuicStreamEventSendComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
        OFFSET += 7; /* padding */
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicStreamEventSendComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicStreamEventSendComplete{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Canceled => ");
            SB.append(isCanceled());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ClientContext => ");
            SB.append(PanamaUtils.memorySegmentToString(getClientContext()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStreamEventSendComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventSendComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEventSendComplete.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEventSendComplete.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEventSendComplete ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventSendComplete.Array";
        }

        @Override
        protected QuicStreamEventSendComplete construct(MemorySegment seg) {
            return new QuicStreamEventSendComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventSendComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventSendComplete> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventSendComplete> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventSendComplete> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventSendComplete> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventSendComplete> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventSendComplete.Func";
        }

        @Override
        protected QuicStreamEventSendComplete construct(MemorySegment seg) {
            return new QuicStreamEventSendComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:923257bbc76f76351f9e19a5c83c834e8e64b8f74689e6c12e7e6e840a5dfdaa
