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

public class QuicConnectionEventConnectionShutdownComplete extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("Field01")
    );
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle Field01VH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Field01")
    );

    public byte getField01() {
        return (byte) Field01VH.get(MEMORY);
    }

    public void setField01(byte Field01) {
        Field01VH.set(MEMORY, Field01);
    }

    public boolean isHandshakeCompleted() {
        var N = getField01();
        return ((N >> 0) & 0b1) == 1;
    }

    public void setHandshakeCompleted(boolean HandshakeCompleted) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 0);
        var NN = (byte) (HandshakeCompleted ? 1 : 0);
        NN = (byte) (NN << 0);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public boolean isPeerAcknowledgedShutdown() {
        var N = getField01();
        return ((N >> 1) & 0b1) == 1;
    }

    public void setPeerAcknowledgedShutdown(boolean PeerAcknowledgedShutdown) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 1);
        var NN = (byte) (PeerAcknowledgedShutdown ? 1 : 0);
        NN = (byte) (NN << 1);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public boolean isAppCloseInProgress() {
        var N = getField01();
        return ((N >> 2) & 0b1) == 1;
    }

    public void setAppCloseInProgress(boolean AppCloseInProgress) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 2);
        var NN = (byte) (AppCloseInProgress ? 1 : 0);
        NN = (byte) (NN << 2);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public QuicConnectionEventConnectionShutdownComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
    }

    public QuicConnectionEventConnectionShutdownComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventConnectionShutdownComplete{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Field01 => ");
            SB.append(getField01());
            SB.append(" {\n");
            SB.append(" ".repeat(INDENT + 8)).append("HandshakeCompleted:1 => ").append(isHandshakeCompleted());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("PeerAcknowledgedShutdown:1 => ").append(isPeerAcknowledgedShutdown());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("AppCloseInProgress:1 => ").append(isAppCloseInProgress());
            SB.append("\n");
            SB.append(" ".repeat(INDENT + 4)).append("}");
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventConnectionShutdownComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventConnectionShutdownComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventConnectionShutdownComplete.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventConnectionShutdownComplete.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventConnectionShutdownComplete ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventConnectionShutdownComplete.Array";
        }

        @Override
        protected QuicConnectionEventConnectionShutdownComplete construct(MemorySegment seg) {
            return new QuicConnectionEventConnectionShutdownComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventConnectionShutdownComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventConnectionShutdownComplete> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventConnectionShutdownComplete> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventConnectionShutdownComplete> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventConnectionShutdownComplete> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventConnectionShutdownComplete> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventConnectionShutdownComplete.Func";
        }

        @Override
        protected QuicConnectionEventConnectionShutdownComplete construct(MemorySegment seg) {
            return new QuicConnectionEventConnectionShutdownComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.18
// sha256:1bd0aa32d86bbe05620f3a6c801dcea4f1beac91a071d4bf8312a27391694c84
