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

public class QuicConnectionEventDatagramSendStateChanged extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ClientContext"),
        ValueLayout.JAVA_INT.withName("State"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
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

    private static final VarHandleW StateVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("State")
        )
    );

    public int getState() {
        return StateVH.getInt(MEMORY);
    }

    public void setState(int State) {
        StateVH.set(MEMORY, State);
    }

    public QuicConnectionEventDatagramSendStateChanged(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
    }

    public QuicConnectionEventDatagramSendStateChanged(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventDatagramSendStateChanged{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ClientContext => ");
            SB.append(PanamaUtils.memorySegmentToString(getClientContext()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("State => ");
            SB.append(getState());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventDatagramSendStateChanged> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventDatagramSendStateChanged.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventDatagramSendStateChanged.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventDatagramSendStateChanged.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventDatagramSendStateChanged ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventDatagramSendStateChanged.Array";
        }

        @Override
        protected QuicConnectionEventDatagramSendStateChanged construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramSendStateChanged(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventDatagramSendStateChanged value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventDatagramSendStateChanged> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramSendStateChanged> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramSendStateChanged> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramSendStateChanged> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramSendStateChanged> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventDatagramSendStateChanged.Func";
        }

        @Override
        protected QuicConnectionEventDatagramSendStateChanged construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramSendStateChanged(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:9e7c060858f9c65344ee5abac3c15e116f8314ffb62d9e52bcee11b46fbba76d
