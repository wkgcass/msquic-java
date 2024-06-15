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

public class QuicConnectionEventPeerStreamStarted extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("Stream"),
        ValueLayout.JAVA_INT.withName("Flags"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW StreamVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Stream")
        )
    );

    public MemorySegment getStream() {
        var SEG = StreamVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setStream(MemorySegment Stream) {
        if (Stream == null) {
            StreamVH.set(MEMORY, MemorySegment.NULL);
        } else {
            StreamVH.set(MEMORY, Stream);
        }
    }

    private static final VarHandleW FlagsVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Flags")
        )
    );

    public int getFlags() {
        return FlagsVH.getInt(MEMORY);
    }

    public void setFlags(int Flags) {
        FlagsVH.set(MEMORY, Flags);
    }

    public QuicConnectionEventPeerStreamStarted(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
    }

    public QuicConnectionEventPeerStreamStarted(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventPeerStreamStarted{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Stream => ");
            SB.append(PanamaUtils.memorySegmentToString(getStream()));
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Flags => ");
            SB.append(getFlags());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventPeerStreamStarted> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventPeerStreamStarted.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventPeerStreamStarted.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventPeerStreamStarted.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventPeerStreamStarted ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventPeerStreamStarted.Array";
        }

        @Override
        protected QuicConnectionEventPeerStreamStarted construct(MemorySegment seg) {
            return new QuicConnectionEventPeerStreamStarted(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventPeerStreamStarted value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventPeerStreamStarted> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerStreamStarted> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventPeerStreamStarted> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerStreamStarted> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventPeerStreamStarted> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventPeerStreamStarted.Func";
        }

        @Override
        protected QuicConnectionEventPeerStreamStarted construct(MemorySegment seg) {
            return new QuicConnectionEventPeerStreamStarted(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:a31cc6dc88a31177b8eee946ccc27bc8f4eedb05f3bb10efce1d2cbef046cf63
