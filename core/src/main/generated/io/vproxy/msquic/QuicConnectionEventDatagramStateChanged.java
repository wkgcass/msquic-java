package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventDatagramStateChanged extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("SendEnabled"),
        MemoryLayout.sequenceLayout(1L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_SHORT.withName("MaxSendLength")
    ).withByteAlignment(2);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle SendEnabledVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("SendEnabled")
    );

    public boolean getSendEnabled() {
        return (boolean) SendEnabledVH.get(MEMORY);
    }

    public void setSendEnabled(boolean SendEnabled) {
        SendEnabledVH.set(MEMORY, SendEnabled);
    }

    private static final VarHandle MaxSendLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MaxSendLength")
    );

    public short getMaxSendLength() {
        return (short) MaxSendLengthVH.get(MEMORY);
    }

    public void setMaxSendLength(short MaxSendLength) {
        MaxSendLengthVH.set(MEMORY, MaxSendLength);
    }

    public QuicConnectionEventDatagramStateChanged(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
        OFFSET += 1; /* padding */
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
    }

    public QuicConnectionEventDatagramStateChanged(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventDatagramStateChanged{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SendEnabled => ");
            SB.append(getSendEnabled());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MaxSendLength => ");
            SB.append(getMaxSendLength());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventDatagramStateChanged> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventDatagramStateChanged.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventDatagramStateChanged.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventDatagramStateChanged.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventDatagramStateChanged ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventDatagramStateChanged.Array";
        }

        @Override
        protected QuicConnectionEventDatagramStateChanged construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramStateChanged(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventDatagramStateChanged value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventDatagramStateChanged> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramStateChanged> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventDatagramStateChanged> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramStateChanged> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventDatagramStateChanged> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventDatagramStateChanged.Func";
        }

        @Override
        protected QuicConnectionEventDatagramStateChanged construct(MemorySegment seg) {
            return new QuicConnectionEventDatagramStateChanged(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.15
// sha256:c14b7861dde28e71714d0fc21a5fabef9cec61341d427a8703143d0305d38da2
