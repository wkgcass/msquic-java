package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicTLSSecret {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("SecretLength"),
        io.vproxy.msquic.QuicTLSSecretIsSet.LAYOUT.withName("IsSet"),
        MemoryLayout.sequenceLayout(32L, ValueLayout.JAVA_BYTE).withName("ClientRandom"),
        MemoryLayout.sequenceLayout(64L, ValueLayout.JAVA_BYTE).withName("ClientEarlyTrafficSecret"),
        MemoryLayout.sequenceLayout(64L, ValueLayout.JAVA_BYTE).withName("ClientHandshakeTrafficSecret"),
        MemoryLayout.sequenceLayout(64L, ValueLayout.JAVA_BYTE).withName("ServerHandshakeTrafficSecret"),
        MemoryLayout.sequenceLayout(64L, ValueLayout.JAVA_BYTE).withName("ClientTrafficSecret0"),
        MemoryLayout.sequenceLayout(64L, ValueLayout.JAVA_BYTE).withName("ServerTrafficSecret0")
    );
    public final MemorySegment MEMORY;

    private static final VarHandle SecretLengthVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("SecretLength")
    );

    public byte getSecretLength() {
        return (byte) SecretLengthVH.get(MEMORY);
    }

    public void setSecretLength(byte SecretLength) {
        SecretLengthVH.set(MEMORY, SecretLength);
    }

    private final io.vproxy.msquic.QuicTLSSecretIsSet IsSet;

    public io.vproxy.msquic.QuicTLSSecretIsSet getIsSet() {
        return this.IsSet;
    }

    private final MemorySegment ClientRandom;

    public MemorySegment getClientRandom() {
        return this.ClientRandom;
    }

    private final MemorySegment ClientEarlyTrafficSecret;

    public MemorySegment getClientEarlyTrafficSecret() {
        return this.ClientEarlyTrafficSecret;
    }

    private final MemorySegment ClientHandshakeTrafficSecret;

    public MemorySegment getClientHandshakeTrafficSecret() {
        return this.ClientHandshakeTrafficSecret;
    }

    private final MemorySegment ServerHandshakeTrafficSecret;

    public MemorySegment getServerHandshakeTrafficSecret() {
        return this.ServerHandshakeTrafficSecret;
    }

    private final MemorySegment ClientTrafficSecret0;

    public MemorySegment getClientTrafficSecret0() {
        return this.ClientTrafficSecret0;
    }

    private final MemorySegment ServerTrafficSecret0;

    public MemorySegment getServerTrafficSecret0() {
        return this.ServerTrafficSecret0;
    }

    public QuicTLSSecret(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
        this.IsSet = new io.vproxy.msquic.QuicTLSSecretIsSet(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicTLSSecretIsSet.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicTLSSecretIsSet.LAYOUT.byteSize();
        this.ClientRandom = MEMORY.asSlice(OFFSET, 32);
        OFFSET += 32;
        this.ClientEarlyTrafficSecret = MEMORY.asSlice(OFFSET, 64);
        OFFSET += 64;
        this.ClientHandshakeTrafficSecret = MEMORY.asSlice(OFFSET, 64);
        OFFSET += 64;
        this.ServerHandshakeTrafficSecret = MEMORY.asSlice(OFFSET, 64);
        OFFSET += 64;
        this.ClientTrafficSecret0 = MEMORY.asSlice(OFFSET, 64);
        OFFSET += 64;
        this.ServerTrafficSecret0 = MEMORY.asSlice(OFFSET, 64);
        OFFSET += 64;
    }

    public QuicTLSSecret(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicTLSSecret> {
        public Array(MemorySegment buf) {
            super(buf, QuicTLSSecret.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicTLSSecret.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
        }

        @Override
        protected QuicTLSSecret construct(MemorySegment seg) {
            return new QuicTLSSecret(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicTLSSecret value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicTLSSecret> {
        private Func(io.vproxy.pni.CallSite<QuicTLSSecret> func) {
            super(func);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicTLSSecret> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicTLSSecret construct(MemorySegment seg) {
            return new QuicTLSSecret(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:ace0e3a35d9e47d7dbb99e1d7594062d737db0a088d97605558ed2320827f203
