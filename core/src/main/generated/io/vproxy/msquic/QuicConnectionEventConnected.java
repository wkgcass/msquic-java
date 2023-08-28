package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventConnected {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("SessionResumed"),
        ValueLayout.JAVA_BYTE.withName("NegotiatedAlpnLength"),
        MemoryLayout.sequenceLayout(6L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS_UNALIGNED.withName("NegotiatedAlpn")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle SessionResumedVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("SessionResumed")
    );

    public boolean getSessionResumed() {
        return (boolean) SessionResumedVH.get(MEMORY);
    }

    public void setSessionResumed(boolean SessionResumed) {
        SessionResumedVH.set(MEMORY, SessionResumed);
    }

    private static final VarHandle NegotiatedAlpnLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("NegotiatedAlpnLength")
    );

    public byte getNegotiatedAlpnLength() {
        return (byte) NegotiatedAlpnLengthVH.get(MEMORY);
    }

    public void setNegotiatedAlpnLength(byte NegotiatedAlpnLength) {
        NegotiatedAlpnLengthVH.set(MEMORY, NegotiatedAlpnLength);
    }

    private static final VarHandle NegotiatedAlpnVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("NegotiatedAlpn")
    );

    public MemorySegment getNegotiatedAlpn() {
        var SEG = (MemorySegment) NegotiatedAlpnVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setNegotiatedAlpn(MemorySegment NegotiatedAlpn) {
        if (NegotiatedAlpn == null) {
            NegotiatedAlpnVH.set(MEMORY, MemorySegment.NULL);
        } else {
            NegotiatedAlpnVH.set(MEMORY, NegotiatedAlpn);
        }
    }

    public QuicConnectionEventConnected(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
        OFFSET += 6; /* padding */
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicConnectionEventConnected(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicConnectionEventConnected> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventConnected.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicConnectionEventConnected.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicConnectionEventConnected construct(MemorySegment seg) {
            return new QuicConnectionEventConnected(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventConnected value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventConnected> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventConnected> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventConnected> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicConnectionEventConnected construct(MemorySegment seg) {
            return new QuicConnectionEventConnected(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:f1dbddcc013404d6d2e64e18755847c54f7dadc47ccc1aeff3565d7b70ad5e3d
