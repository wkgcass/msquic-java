package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicTLSSecretIsSet {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("IsSet")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle IsSetVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("IsSet")
    );

    public byte getIsSet() {
        return (byte) IsSetVH.get(MEMORY);
    }

    public void setIsSet(byte IsSet) {
        IsSetVH.set(MEMORY, IsSet);
    }

    public byte getClientRandom() {
        var N = getIsSet();
        return (byte) ((N >> 0) & 0b1);
    }

    public void setClientRandom(byte ClientRandom) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 0);
        ClientRandom = (byte) (ClientRandom & 0b1);
        ClientRandom = (byte) (ClientRandom << 0);
        N = (byte) ((N & ~MASK) | (ClientRandom & MASK));
        setIsSet(N);
    }

    public byte getClientEarlyTrafficSecret() {
        var N = getIsSet();
        return (byte) ((N >> 1) & 0b1);
    }

    public void setClientEarlyTrafficSecret(byte ClientEarlyTrafficSecret) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 1);
        ClientEarlyTrafficSecret = (byte) (ClientEarlyTrafficSecret & 0b1);
        ClientEarlyTrafficSecret = (byte) (ClientEarlyTrafficSecret << 1);
        N = (byte) ((N & ~MASK) | (ClientEarlyTrafficSecret & MASK));
        setIsSet(N);
    }

    public byte getClientHandshakeTrafficSecret() {
        var N = getIsSet();
        return (byte) ((N >> 2) & 0b1);
    }

    public void setClientHandshakeTrafficSecret(byte ClientHandshakeTrafficSecret) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 2);
        ClientHandshakeTrafficSecret = (byte) (ClientHandshakeTrafficSecret & 0b1);
        ClientHandshakeTrafficSecret = (byte) (ClientHandshakeTrafficSecret << 2);
        N = (byte) ((N & ~MASK) | (ClientHandshakeTrafficSecret & MASK));
        setIsSet(N);
    }

    public byte getServerHandshakeTrafficSecret() {
        var N = getIsSet();
        return (byte) ((N >> 3) & 0b1);
    }

    public void setServerHandshakeTrafficSecret(byte ServerHandshakeTrafficSecret) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 3);
        ServerHandshakeTrafficSecret = (byte) (ServerHandshakeTrafficSecret & 0b1);
        ServerHandshakeTrafficSecret = (byte) (ServerHandshakeTrafficSecret << 3);
        N = (byte) ((N & ~MASK) | (ServerHandshakeTrafficSecret & MASK));
        setIsSet(N);
    }

    public byte getClientTrafficSecret0() {
        var N = getIsSet();
        return (byte) ((N >> 4) & 0b1);
    }

    public void setClientTrafficSecret0(byte ClientTrafficSecret0) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 4);
        ClientTrafficSecret0 = (byte) (ClientTrafficSecret0 & 0b1);
        ClientTrafficSecret0 = (byte) (ClientTrafficSecret0 << 4);
        N = (byte) ((N & ~MASK) | (ClientTrafficSecret0 & MASK));
        setIsSet(N);
    }

    public byte getServerTrafficSecret0() {
        var N = getIsSet();
        return (byte) ((N >> 5) & 0b1);
    }

    public void setServerTrafficSecret0(byte ServerTrafficSecret0) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 5);
        ServerTrafficSecret0 = (byte) (ServerTrafficSecret0 & 0b1);
        ServerTrafficSecret0 = (byte) (ServerTrafficSecret0 << 5);
        N = (byte) ((N & ~MASK) | (ServerTrafficSecret0 & MASK));
        setIsSet(N);
    }

    public QuicTLSSecretIsSet(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
    }

    public QuicTLSSecretIsSet(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicTLSSecretIsSet> {
        public Array(MemorySegment buf) {
            super(buf, QuicTLSSecretIsSet.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicTLSSecretIsSet.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicTLSSecretIsSet construct(MemorySegment seg) {
            return new QuicTLSSecretIsSet(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicTLSSecretIsSet value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicTLSSecretIsSet> {
        private Func(io.vproxy.pni.CallSite<QuicTLSSecretIsSet> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicTLSSecretIsSet> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicTLSSecretIsSet construct(MemorySegment seg) {
            return new QuicTLSSecretIsSet(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:1198bdbaa08ac00215f673b94102f1b36caeac6ab92b5468a759755615a0cb3a
