package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicListenerEventNewConnection {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("Info"),
        ValueLayout.ADDRESS_UNALIGNED.withName("Connection")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle InfoVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Info")
    );

    public io.vproxy.msquic.QuicNewConnectionInfo getInfo() {
        var SEG = (MemorySegment) InfoVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return new io.vproxy.msquic.QuicNewConnectionInfo(SEG);
    }

    public void setInfo(io.vproxy.msquic.QuicNewConnectionInfo Info) {
        if (Info == null) {
            InfoVH.set(MEMORY, MemorySegment.NULL);
        } else {
            InfoVH.set(MEMORY, Info.MEMORY);
        }
    }

    private static final VarHandle ConnectionVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Connection")
    );

    public MemorySegment getConnection() {
        var SEG = (MemorySegment) ConnectionVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setConnection(MemorySegment Connection) {
        if (Connection == null) {
            ConnectionVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ConnectionVH.set(MEMORY, Connection);
        }
    }

    public QuicListenerEventNewConnection(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += 8;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicListenerEventNewConnection(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicListenerEventNewConnection> {
        public Array(MemorySegment buf) {
            super(buf, QuicListenerEventNewConnection.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicListenerEventNewConnection.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicListenerEventNewConnection construct(MemorySegment seg) {
            return new QuicListenerEventNewConnection(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicListenerEventNewConnection value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicListenerEventNewConnection> {
        private Func(io.vproxy.pni.CallSite<QuicListenerEventNewConnection> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicListenerEventNewConnection> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventNewConnection> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventNewConnection> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicListenerEventNewConnection construct(MemorySegment seg) {
            return new QuicListenerEventNewConnection(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:f4d7b760e031fd4aad969319ccb89235b649effa42a82529d216b6b8c708ba6d
