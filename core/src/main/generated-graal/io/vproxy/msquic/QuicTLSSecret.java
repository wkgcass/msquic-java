package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicTLSSecret extends AbstractNativeObject implements NativeObject {
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

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

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
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicTLSSecret{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SecretLength => ");
            SB.append(getSecretLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IsSet => ");
            PanamaUtils.nativeObjectToString(getIsSet(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ClientRandom => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else SB.append(PanamaUtils.memorySegmentToString(getClientRandom()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ClientEarlyTrafficSecret => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else SB.append(PanamaUtils.memorySegmentToString(getClientEarlyTrafficSecret()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ClientHandshakeTrafficSecret => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else SB.append(PanamaUtils.memorySegmentToString(getClientHandshakeTrafficSecret()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ServerHandshakeTrafficSecret => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else SB.append(PanamaUtils.memorySegmentToString(getServerHandshakeTrafficSecret()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ClientTrafficSecret0 => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else SB.append(PanamaUtils.memorySegmentToString(getClientTrafficSecret0()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ServerTrafficSecret0 => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else SB.append(PanamaUtils.memorySegmentToString(getServerTrafficSecret0()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicTLSSecret> {
        public Array(MemorySegment buf) {
            super(buf, QuicTLSSecret.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicTLSSecret.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicTLSSecret.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicTLSSecret ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicTLSSecret.Array";
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

        private Func(io.vproxy.pni.CallSite<QuicTLSSecret> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicTLSSecret> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicTLSSecret> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicTLSSecret.Func";
        }

        @Override
        protected QuicTLSSecret construct(MemorySegment seg) {
            return new QuicTLSSecret(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:4a0b9e90b619935fe7bdc37fc2c8353b9d5dbfc19cbc73aa2a4017f05e65f5e2
