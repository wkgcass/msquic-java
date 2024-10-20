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

public class QuicConnectionEventConnected extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BOOLEAN.withName("SessionResumed"),
        ValueLayout.JAVA_BYTE.withName("NegotiatedAlpnLength"),
        MemoryLayout.sequenceLayout(6L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.ADDRESS.withName("NegotiatedAlpn")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW SessionResumedVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("SessionResumed")
        )
    );

    public boolean isSessionResumed() {
        return SessionResumedVH.getBool(MEMORY);
    }

    public void setSessionResumed(boolean SessionResumed) {
        SessionResumedVH.set(MEMORY, SessionResumed);
    }

    private static final VarHandleW NegotiatedAlpnLengthVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("NegotiatedAlpnLength")
        )
    );

    public byte getNegotiatedAlpnLength() {
        return NegotiatedAlpnLengthVH.getByte(MEMORY);
    }

    public void setNegotiatedAlpnLength(byte NegotiatedAlpnLength) {
        NegotiatedAlpnLengthVH.set(MEMORY, NegotiatedAlpnLength);
    }

    private static final VarHandleW NegotiatedAlpnVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("NegotiatedAlpn")
        )
    );

    public MemorySegment getNegotiatedAlpn() {
        var SEG = NegotiatedAlpnVH.getMemorySegment(MEMORY);
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
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventConnected{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SessionResumed => ");
            SB.append(isSessionResumed());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("NegotiatedAlpnLength => ");
            SB.append(getNegotiatedAlpnLength());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("NegotiatedAlpn => ");
            SB.append(PanamaUtils.memorySegmentToString(getNegotiatedAlpn()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventConnected> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventConnected.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventConnected.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventConnected.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventConnected ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventConnected.Array";
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

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventConnected> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventConnected> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventConnected> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventConnected.Func";
        }

        @Override
        protected QuicConnectionEventConnected construct(MemorySegment seg) {
            return new QuicConnectionEventConnected(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:e6c859db10432a81fa0c91d8d93d72cf4e532117d03cf991449f2bf35d3290c3
