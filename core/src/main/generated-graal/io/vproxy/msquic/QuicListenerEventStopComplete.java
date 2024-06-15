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

public class QuicListenerEventStopComplete extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("Field01")
    );
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
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

    public boolean isAppCloseInProgress() {
        var N = getField01();
        return ((N >> 0) & 0b1) == 1;
    }

    public void setAppCloseInProgress(boolean AppCloseInProgress) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 0);
        var NN = (byte) (AppCloseInProgress ? 1 : 0);
        NN = (byte) (NN << 0);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public QuicListenerEventStopComplete(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
    }

    public QuicListenerEventStopComplete(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicListenerEventStopComplete{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Field01 => ");
            SB.append(getField01());
            SB.append(" {\n");
            SB.append(" ".repeat(INDENT + 8)).append("AppCloseInProgress:1 => ").append(isAppCloseInProgress());
            SB.append("\n");
            SB.append(" ".repeat(INDENT + 4)).append("}");
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicListenerEventStopComplete> {
        public Array(MemorySegment buf) {
            super(buf, QuicListenerEventStopComplete.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicListenerEventStopComplete.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicListenerEventStopComplete.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicListenerEventStopComplete ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListenerEventStopComplete.Array";
        }

        @Override
        protected QuicListenerEventStopComplete construct(MemorySegment seg) {
            return new QuicListenerEventStopComplete(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicListenerEventStopComplete value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicListenerEventStopComplete> {
        private Func(io.vproxy.pni.CallSite<QuicListenerEventStopComplete> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicListenerEventStopComplete> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventStopComplete> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventStopComplete> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListenerEventStopComplete.Func";
        }

        @Override
        protected QuicListenerEventStopComplete construct(MemorySegment seg) {
            return new QuicListenerEventStopComplete(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:b4aa7f0494d070b87dcf5fc2d02900f09968bd67e571601f4d18691da3ae87dc
