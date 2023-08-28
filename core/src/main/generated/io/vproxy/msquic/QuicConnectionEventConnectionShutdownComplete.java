package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventConnectionShutdownComplete {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("Field01")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle Field01VH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Field01")
    );

    public byte getField01() {
        return (byte) Field01VH.get(MEMORY);
    }

    public void setField01(byte Field01) {
        Field01VH.set(MEMORY, Field01);
    }

    public byte getHandshakeCompleted() {
        var N = getField01();
        return (byte) ((N >> 0) & 0b1);
    }

    public void setHandshakeCompleted(byte HandshakeCompleted) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 0);
        HandshakeCompleted = (byte) (HandshakeCompleted & 0b1);
        HandshakeCompleted = (byte) (HandshakeCompleted << 0);
        N = (byte) ((N & ~MASK) | (HandshakeCompleted & MASK));
        setField01(N);
    }

    public byte getPeerAcknowledgedShutdown() {
        var N = getField01();
        return (byte) ((N >> 1) & 0b1);
    }

    public void setPeerAcknowledgedShutdown(byte PeerAcknowledgedShutdown) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 1);
        PeerAcknowledgedShutdown = (byte) (PeerAcknowledgedShutdown & 0b1);
        PeerAcknowledgedShutdown = (byte) (PeerAcknowledgedShutdown << 1);
        N = (byte) ((N & ~MASK) | (PeerAcknowledgedShutdown & MASK));
        setField01(N);
    }

    public byte getAppCloseInProgress() {
        var N = getField01();
        return (byte) ((N >> 2) & 0b1);
    }

    public void setAppCloseInProgress(byte AppCloseInProgress) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 2);
        AppCloseInProgress = (byte) (AppCloseInProgress & 0b1);
        AppCloseInProgress = (byte) (AppCloseInProgress << 2);
        N = (byte) ((N & ~MASK) | (AppCloseInProgress & MASK));
        setField01(N);
    }

    public QuicConnectionEventConnectionShutdownComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
    }

    public QuicConnectionEventConnectionShutdownComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventConnectionShutdownComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventConnectionShutdownComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventConnectionShutdownComplete.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
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

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventConnectionShutdownComplete> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventConnectionShutdownComplete construct(MemorySegment seg) {
            return new QuicConnectionEventConnectionShutdownComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:b957d354733ad754b01675376b0976bcbe9a20cb5bce7634e003ddcbe5327ac3
