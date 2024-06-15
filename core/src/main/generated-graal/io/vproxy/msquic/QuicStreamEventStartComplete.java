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

public class QuicStreamEventStartComplete extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("Status"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_LONG.withName("ID"),
        ValueLayout.JAVA_BYTE.withName("Field01"),
        MemoryLayout.sequenceLayout(7L, ValueLayout.JAVA_BYTE) /* padding */
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW StatusVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Status")
        )
    );

    public int getStatus() {
        return StatusVH.getInt(MEMORY);
    }

    public void setStatus(int Status) {
        StatusVH.set(MEMORY, Status);
    }

    private static final VarHandleW IDVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ID")
        )
    );

    public long getID() {
        return IDVH.getLong(MEMORY);
    }

    public void setID(long ID) {
        IDVH.set(MEMORY, ID);
    }

    private static final VarHandleW Field01VH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Field01")
        )
    );

    public byte getField01() {
        return Field01VH.getByte(MEMORY);
    }

    public void setField01(byte Field01) {
        Field01VH.set(MEMORY, Field01);
    }

    public boolean isPeerAccepted() {
        var N = getField01();
        return ((N >> 0) & 0b1) == 1;
    }

    public void setPeerAccepted(boolean PeerAccepted) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 0);
        var NN = (byte) (PeerAccepted ? 1 : 0);
        NN = (byte) (NN << 0);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public QuicStreamEventStartComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
        OFFSET += 7; /* padding */
    }

    public QuicStreamEventStartComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicStreamEventStartComplete{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Status => ");
            SB.append(getStatus());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ID => ");
            SB.append(getID());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Field01 => ");
            SB.append(getField01());
            SB.append(" {\n");
            SB.append(" ".repeat(INDENT + 8)).append("PeerAccepted:1 => ").append(isPeerAccepted());
            SB.append("\n");
            SB.append(" ".repeat(INDENT + 4)).append("}");
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStreamEventStartComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventStartComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEventStartComplete.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEventStartComplete.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEventStartComplete ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventStartComplete.Array";
        }

        @Override
        protected QuicStreamEventStartComplete construct(MemorySegment seg) {
            return new QuicStreamEventStartComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventStartComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventStartComplete> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventStartComplete> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventStartComplete> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventStartComplete> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventStartComplete> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventStartComplete.Func";
        }

        @Override
        protected QuicStreamEventStartComplete construct(MemorySegment seg) {
            return new QuicStreamEventStartComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:80849cee7384dfcd0348602dc2884d17bd6a5145343058ae92720326290699a6
