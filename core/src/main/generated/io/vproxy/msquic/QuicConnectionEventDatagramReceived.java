package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventDatagramReceived extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        io.vproxy.msquic.QuicBuffer.LAYOUT.withName("Buffer"),
        ValueLayout.JAVA_INT.withName("Flags"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private final io.vproxy.msquic.QuicBuffer Buffer;

    public io.vproxy.msquic.QuicBuffer getBuffer() {
        return this.Buffer;
    }

    private static final VarHandleW FlagsVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Flags")
        )
    );

    public int getFlags() {
        return FlagsVH.getInt(MEMORY);
    }

    public void setFlags(int Flags) {
        FlagsVH.set(MEMORY, Flags);
    }

    public QuicConnectionEventDatagramReceived(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        this.Buffer = new io.vproxy.msquic.QuicBuffer(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicBuffer.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicBuffer.LAYOUT.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
    }

    public QuicConnectionEventDatagramReceived(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventDatagramReceived{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Buffer => ");
            PanamaUtils.nativeObjectToString(getBuffer(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Flags => ");
            SB.append(getFlags());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventDatagramReceived> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventDatagramReceived.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventDatagramReceived.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventDatagramReceived.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventDatagramReceived ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventDatagramReceived.Array";
        }

        @Override
        protected QuicConnectionEventDatagramReceived construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramReceived(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventDatagramReceived value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventDatagramReceived> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramReceived> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramReceived> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramReceived> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramReceived> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventDatagramReceived.Func";
        }

        @Override
        protected QuicConnectionEventDatagramReceived construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramReceived(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:43d8ea89bf9e0a3a9ea94b633e042cf937d5ab521150f31a64a8be0480c6e118
