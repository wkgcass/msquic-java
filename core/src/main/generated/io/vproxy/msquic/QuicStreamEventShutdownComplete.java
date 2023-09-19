package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventShutdownComplete {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("ConnectionShutdown"),
        ValueLayout.JAVA_BYTE.withName("Field01"),
        MemoryLayout.sequenceLayout(6L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_LONG_UNALIGNED.withName("ConnectionErrorCode"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("ConnectionCloseStatus"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    );
    public final MemorySegment MEMORY;

    private static final VarHandle ConnectionShutdownVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ConnectionShutdown")
    );

    public boolean getConnectionShutdown() {
        return (boolean) ConnectionShutdownVH.get(MEMORY);
    }

    public void setConnectionShutdown(boolean ConnectionShutdown) {
        ConnectionShutdownVH.set(MEMORY, ConnectionShutdown);
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

    public byte getAppCloseInProgress() {
        var N = getField01();
        return (byte) ((N >> 0) & 0b1);
    }

    public void setAppCloseInProgress(byte AppCloseInProgress) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 0);
        AppCloseInProgress = (byte) (AppCloseInProgress & 0b1);
        AppCloseInProgress = (byte) (AppCloseInProgress << 0);
        N = (byte) ((N & ~MASK) | (AppCloseInProgress & MASK));
        setField01(N);
    }

    public byte getConnectionShutdownByApp() {
        var N = getField01();
        return (byte) ((N >> 1) & 0b1);
    }

    public void setConnectionShutdownByApp(byte ConnectionShutdownByApp) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 1);
        ConnectionShutdownByApp = (byte) (ConnectionShutdownByApp & 0b1);
        ConnectionShutdownByApp = (byte) (ConnectionShutdownByApp << 1);
        N = (byte) ((N & ~MASK) | (ConnectionShutdownByApp & MASK));
        setField01(N);
    }

    public byte getConnectionClosedRemotely() {
        var N = getField01();
        return (byte) ((N >> 2) & 0b1);
    }

    public void setConnectionClosedRemotely(byte ConnectionClosedRemotely) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 2);
        ConnectionClosedRemotely = (byte) (ConnectionClosedRemotely & 0b1);
        ConnectionClosedRemotely = (byte) (ConnectionClosedRemotely << 2);
        N = (byte) ((N & ~MASK) | (ConnectionClosedRemotely & MASK));
        setField01(N);
    }

    private static final VarHandle ConnectionErrorCodeVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ConnectionErrorCode")
    );

    public long getConnectionErrorCode() {
        return (long) ConnectionErrorCodeVH.get(MEMORY);
    }

    public void setConnectionErrorCode(long ConnectionErrorCode) {
        ConnectionErrorCodeVH.set(MEMORY, ConnectionErrorCode);
    }

    private static final VarHandle ConnectionCloseStatusVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ConnectionCloseStatus")
    );

    public int getConnectionCloseStatus() {
        return (int) ConnectionCloseStatusVH.get(MEMORY);
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
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEventShutdownComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventShutdownComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEventShutdownComplete.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
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
        protected QuicStreamEventShutdownComplete construct(MemorySegment seg) {
            return new QuicStreamEventShutdownComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:b1676ec1869974e6b7e0c97bd71e777f458ba90a6b0640bfa84a45d675649d1d
