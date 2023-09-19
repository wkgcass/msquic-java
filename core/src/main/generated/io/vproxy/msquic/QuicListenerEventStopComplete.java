package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicListenerEventStopComplete {
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

    public QuicListenerEventStopComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
    }

    public QuicListenerEventStopComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicListenerEventStopComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicListenerEventStopComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicListenerEventStopComplete.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicListenerEventStopComplete construct(MemorySegment seg) {
            return new QuicListenerEventStopComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicListenerEventStopComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicListenerEventStopComplete> {
        private Func(io.vproxy.pni.CallSite<QuicListenerEventStopComplete> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicListenerEventStopComplete> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventStopComplete> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventStopComplete> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicListenerEventStopComplete construct(MemorySegment seg) {
            return new QuicListenerEventStopComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:4c7e959b125efcb067ce7e8d10b393c010d0b1c328bd64c0dd94bf3b6ec3d404
