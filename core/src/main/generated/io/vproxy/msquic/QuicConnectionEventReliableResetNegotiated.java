package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventReliableResetNegotiated extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("IsNegotiated")
    );
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW IsNegotiatedVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("IsNegotiated")
        )
    );

    public boolean isIsNegotiated() {
        return IsNegotiatedVH.getBool(MEMORY);
    }

    public void setIsNegotiated(boolean IsNegotiated) {
        IsNegotiatedVH.set(MEMORY, IsNegotiated);
    }

    public QuicConnectionEventReliableResetNegotiated(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BOOLEAN.byteSize();
    }

    public QuicConnectionEventReliableResetNegotiated(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventReliableResetNegotiated{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IsNegotiated => ");
            SB.append(isIsNegotiated());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventReliableResetNegotiated> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventReliableResetNegotiated.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventReliableResetNegotiated.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventReliableResetNegotiated.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventReliableResetNegotiated ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventReliableResetNegotiated.Array";
        }

        @Override
        protected QuicConnectionEventReliableResetNegotiated construct(MemorySegment seg) {
            return new QuicConnectionEventReliableResetNegotiated(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventReliableResetNegotiated value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventReliableResetNegotiated> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventReliableResetNegotiated> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventReliableResetNegotiated> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventReliableResetNegotiated> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventReliableResetNegotiated> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventReliableResetNegotiated.Func";
        }

        @Override
        protected QuicConnectionEventReliableResetNegotiated construct(MemorySegment seg) {
            return new QuicConnectionEventReliableResetNegotiated(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:b45cee3617683e5b5a104a950571d9c800a3a72a385887e14304d0606c532e96
