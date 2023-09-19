package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventPeerAddressChanged {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("Address")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle AddressVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Address")
    );

    public io.vproxy.msquic.QuicAddr getAddress() {
        var SEG = (MemorySegment) AddressVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return new io.vproxy.msquic.QuicAddr(SEG);
    }

    public void setAddress(io.vproxy.msquic.QuicAddr Address) {
        if (Address == null) {
            AddressVH.set(MEMORY, MemorySegment.NULL);
        } else {
            AddressVH.set(MEMORY, Address.MEMORY);
        }
    }

    public QuicConnectionEventPeerAddressChanged(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += 8;
    }

    public QuicConnectionEventPeerAddressChanged(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventPeerAddressChanged> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventPeerAddressChanged.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventPeerAddressChanged.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventPeerAddressChanged construct(MemorySegment seg) {
            return new QuicConnectionEventPeerAddressChanged(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventPeerAddressChanged value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventPeerAddressChanged> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerAddressChanged> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerAddressChanged> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerAddressChanged> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerAddressChanged> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventPeerAddressChanged construct(MemorySegment seg) {
            return new QuicConnectionEventPeerAddressChanged(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:1ca005010b4def96e914de4f7518fdacbafde2d72e4e29c95766d7334f27693e
