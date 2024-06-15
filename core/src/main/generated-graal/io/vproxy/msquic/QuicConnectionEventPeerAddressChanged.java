package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicConnectionEventPeerAddressChanged extends AbstractNativeObject implements NativeObject {
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

    public QuicConnectionEventPeerAddressChanged(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += 8;
    }

    public QuicConnectionEventPeerAddressChanged(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventPeerAddressChanged{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Address => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getAddress(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventPeerAddressChanged> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventPeerAddressChanged.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventPeerAddressChanged.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventPeerAddressChanged.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventPeerAddressChanged ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventPeerAddressChanged.Array";
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
        protected String toStringTypeName() {
            return "QuicConnectionEventPeerAddressChanged.Func";
        }

        @Override
        protected QuicConnectionEventPeerAddressChanged construct(MemorySegment seg) {
            return new QuicConnectionEventPeerAddressChanged(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:a18a1bdd1577c739dc962e76fe0734e3824b51bb1fc0d5ca354b53e0bb715949
