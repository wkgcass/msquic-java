package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventLocalAddressChanged extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("Address")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW AddressVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Address")
        )
    );

    public io.vproxy.msquic.QuicAddr getAddress() {
        var SEG = AddressVH.getMemorySegment(MEMORY);
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

    public QuicConnectionEventLocalAddressChanged(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += 8;
    }

    public QuicConnectionEventLocalAddressChanged(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventLocalAddressChanged{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Address => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getAddress(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventLocalAddressChanged> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventLocalAddressChanged.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventLocalAddressChanged.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventLocalAddressChanged.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventLocalAddressChanged ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventLocalAddressChanged.Array";
        }

        @Override
        protected QuicConnectionEventLocalAddressChanged construct(MemorySegment seg) {
            return new QuicConnectionEventLocalAddressChanged(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventLocalAddressChanged value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventLocalAddressChanged> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventLocalAddressChanged> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventLocalAddressChanged> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventLocalAddressChanged> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventLocalAddressChanged> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventLocalAddressChanged.Func";
        }

        @Override
        protected QuicConnectionEventLocalAddressChanged construct(MemorySegment seg) {
            return new QuicConnectionEventLocalAddressChanged(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:d4eaaee22a87b82ba27bd8fbff2c18ef69ac9f586833442189540b60ddab0a17
