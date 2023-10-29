package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicTLSSecretIsSet extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("IsSet")
    );
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle IsSetVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("IsSet")
    );

    public byte getIsSet() {
        return (byte) IsSetVH.get(MEMORY);
    }

    public void setIsSet(byte IsSet) {
        IsSetVH.set(MEMORY, IsSet);
    }

    public boolean isClientRandom() {
        var N = getIsSet();
        return ((N >> 0) & 0b1) == 1;
    }

    public void setClientRandom(boolean ClientRandom) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 0);
        var NN = (byte) (ClientRandom ? 1 : 0);
        NN = (byte) (NN << 0);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setIsSet(N);
    }

    public boolean isClientEarlyTrafficSecret() {
        var N = getIsSet();
        return ((N >> 1) & 0b1) == 1;
    }

    public void setClientEarlyTrafficSecret(boolean ClientEarlyTrafficSecret) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 1);
        var NN = (byte) (ClientEarlyTrafficSecret ? 1 : 0);
        NN = (byte) (NN << 1);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setIsSet(N);
    }

    public boolean isClientHandshakeTrafficSecret() {
        var N = getIsSet();
        return ((N >> 2) & 0b1) == 1;
    }

    public void setClientHandshakeTrafficSecret(boolean ClientHandshakeTrafficSecret) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 2);
        var NN = (byte) (ClientHandshakeTrafficSecret ? 1 : 0);
        NN = (byte) (NN << 2);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setIsSet(N);
    }

    public boolean isServerHandshakeTrafficSecret() {
        var N = getIsSet();
        return ((N >> 3) & 0b1) == 1;
    }

    public void setServerHandshakeTrafficSecret(boolean ServerHandshakeTrafficSecret) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 3);
        var NN = (byte) (ServerHandshakeTrafficSecret ? 1 : 0);
        NN = (byte) (NN << 3);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setIsSet(N);
    }

    public boolean isClientTrafficSecret0() {
        var N = getIsSet();
        return ((N >> 4) & 0b1) == 1;
    }

    public void setClientTrafficSecret0(boolean ClientTrafficSecret0) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 4);
        var NN = (byte) (ClientTrafficSecret0 ? 1 : 0);
        NN = (byte) (NN << 4);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setIsSet(N);
    }

    public boolean isServerTrafficSecret0() {
        var N = getIsSet();
        return ((N >> 5) & 0b1) == 1;
    }

    public void setServerTrafficSecret0(boolean ServerTrafficSecret0) {
        var N = getIsSet();
        byte MASK = (byte) (0b1 << 5);
        var NN = (byte) (ServerTrafficSecret0 ? 1 : 0);
        NN = (byte) (NN << 5);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setIsSet(N);
    }

    public QuicTLSSecretIsSet(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
    }

    public QuicTLSSecretIsSet(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicTLSSecretIsSet{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IsSet => ");
            SB.append(getIsSet());
            SB.append(" {\n");
            SB.append(" ".repeat(INDENT + 8)).append("ClientRandom:1 => ").append(isClientRandom());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ClientEarlyTrafficSecret:1 => ").append(isClientEarlyTrafficSecret());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ClientHandshakeTrafficSecret:1 => ").append(isClientHandshakeTrafficSecret());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ServerHandshakeTrafficSecret:1 => ").append(isServerHandshakeTrafficSecret());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ClientTrafficSecret0:1 => ").append(isClientTrafficSecret0());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ServerTrafficSecret0:1 => ").append(isServerTrafficSecret0());
            SB.append("\n");
            SB.append(" ".repeat(INDENT + 4)).append("}");
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicTLSSecretIsSet> {
        public Array(MemorySegment buf) {
            super(buf, QuicTLSSecretIsSet.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicTLSSecretIsSet.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicTLSSecretIsSet.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicTLSSecretIsSet ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicTLSSecretIsSet.Array";
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

        private Func(io.vproxy.pni.CallSite<QuicTLSSecretIsSet> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicTLSSecretIsSet> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicTLSSecretIsSet> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicTLSSecretIsSet.Func";
        }

        @Override
        protected QuicTLSSecretIsSet construct(MemorySegment seg) {
            return new QuicTLSSecretIsSet(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.18
// sha256:0499222ecdac47e047c7a1517c519e338b055eebde3841a628195b3204460305
