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

public class QuicStreamEventShutdownComplete extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("ConnectionShutdown"),
        ValueLayout.JAVA_BYTE.withName("Field01"),
        MemoryLayout.sequenceLayout(6L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_LONG.withName("ConnectionErrorCode"),
        ValueLayout.JAVA_INT.withName("ConnectionCloseStatus"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW ConnectionShutdownVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ConnectionShutdown")
        )
    );

    public boolean isConnectionShutdown() {
        return ConnectionShutdownVH.getBool(MEMORY);
    }

    public void setConnectionShutdown(boolean ConnectionShutdown) {
        ConnectionShutdownVH.set(MEMORY, ConnectionShutdown);
    }

    private static final VarHandleW Field01VH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Field01")
        )
    );

    public byte getField01() {
        return Field01VH.getByte(MEMORY);
    }

    public void setField01(byte Field01) {
        Field01VH.set(MEMORY, Field01);
    }

    public boolean isAppCloseInProgress() {
        var N = getField01();
        return ((N >> 0) & 0b1) == 1;
    }

    public void setAppCloseInProgress(boolean AppCloseInProgress) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 0);
        var NN = (byte) (AppCloseInProgress ? 1 : 0);
        NN = (byte) (NN << 0);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public boolean isConnectionShutdownByApp() {
        var N = getField01();
        return ((N >> 1) & 0b1) == 1;
    }

    public void setConnectionShutdownByApp(boolean ConnectionShutdownByApp) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 1);
        var NN = (byte) (ConnectionShutdownByApp ? 1 : 0);
        NN = (byte) (NN << 1);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public boolean isConnectionClosedRemotely() {
        var N = getField01();
        return ((N >> 2) & 0b1) == 1;
    }

    public void setConnectionClosedRemotely(boolean ConnectionClosedRemotely) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 2);
        var NN = (byte) (ConnectionClosedRemotely ? 1 : 0);
        NN = (byte) (NN << 2);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    private static final VarHandleW ConnectionErrorCodeVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ConnectionErrorCode")
        )
    );

    public long getConnectionErrorCode() {
        return ConnectionErrorCodeVH.getLong(MEMORY);
    }

    public void setConnectionErrorCode(long ConnectionErrorCode) {
        ConnectionErrorCodeVH.set(MEMORY, ConnectionErrorCode);
    }

    private static final VarHandleW ConnectionCloseStatusVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ConnectionCloseStatus")
        )
    );

    public int getConnectionCloseStatus() {
        return ConnectionCloseStatusVH.getInt(MEMORY);
    }

    public void setConnectionCloseStatus(int ConnectionCloseStatus) {
        ConnectionCloseStatusVH.set(MEMORY, ConnectionCloseStatus);
    }

    public QuicStreamEventShutdownComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
        OFFSET += 6; /* padding */
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
    }

    public QuicStreamEventShutdownComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicStreamEventShutdownComplete{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ConnectionShutdown => ");
            SB.append(isConnectionShutdown());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Field01 => ");
            SB.append(getField01());
            SB.append(" {\n");
            SB.append(" ".repeat(INDENT + 8)).append("AppCloseInProgress:1 => ").append(isAppCloseInProgress());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ConnectionShutdownByApp:1 => ").append(isConnectionShutdownByApp());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ConnectionClosedRemotely:1 => ").append(isConnectionClosedRemotely());
            SB.append("\n");
            SB.append(" ".repeat(INDENT + 4)).append("}");
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ConnectionErrorCode => ");
            SB.append(getConnectionErrorCode());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ConnectionCloseStatus => ");
            SB.append(getConnectionCloseStatus());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStreamEventShutdownComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventShutdownComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEventShutdownComplete.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEventShutdownComplete.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEventShutdownComplete ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventShutdownComplete.Array";
        }

        @Override
        protected QuicStreamEventShutdownComplete construct(MemorySegment seg) {
            return new QuicStreamEventShutdownComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventShutdownComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventShutdownComplete> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventShutdownComplete> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventShutdownComplete> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventShutdownComplete> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventShutdownComplete> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventShutdownComplete.Func";
        }

        @Override
        protected QuicStreamEventShutdownComplete construct(MemorySegment seg) {
            return new QuicStreamEventShutdownComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:aff4163982614504f807046a7d20b5cd983da276937f10003a986639a5f486e4
