package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventShutdownInitiatedByTransport extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("Status"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_LONG.withName("ErrorCode")
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

    private static final VarHandleW ErrorCodeVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ErrorCode")
        )
    );

    public long getErrorCode() {
        return ErrorCodeVH.getLong(MEMORY);
    }

    public void setErrorCode(long ErrorCode) {
        ErrorCodeVH.set(MEMORY, ErrorCode);
    }

    public QuicConnectionEventShutdownInitiatedByTransport(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicConnectionEventShutdownInitiatedByTransport(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnectionEventShutdownInitiatedByTransport{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Status => ");
            SB.append(getStatus());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ErrorCode => ");
            SB.append(getErrorCode());
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventShutdownInitiatedByTransport> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventShutdownInitiatedByTransport.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventShutdownInitiatedByTransport.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventShutdownInitiatedByTransport.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByTransport ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventShutdownInitiatedByTransport.Array";
        }

        @Override
        protected QuicConnectionEventShutdownInitiatedByTransport construct(MemorySegment seg) {
            return new QuicConnectionEventShutdownInitiatedByTransport(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventShutdownInitiatedByTransport value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventShutdownInitiatedByTransport> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByTransport> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByTransport> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByTransport> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventShutdownInitiatedByTransport> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventShutdownInitiatedByTransport.Func";
        }

        @Override
        protected QuicConnectionEventShutdownInitiatedByTransport construct(MemorySegment seg) {
            return new QuicConnectionEventShutdownInitiatedByTransport(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:86ceb5ada086f48ce834d26ebc98d9e06ed36b0cf6d4511bc0891507151f7e1c
