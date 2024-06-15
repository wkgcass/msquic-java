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

public class QuicListenerEventNewConnection extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("Info"),
        ValueLayout.ADDRESS.withName("Connection")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW InfoVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Info")
        )
    );

    public io.vproxy.msquic.QuicNewConnectionInfo getInfo() {
        var SEG = InfoVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return new io.vproxy.msquic.QuicNewConnectionInfo(SEG);
    }

    public void setInfo(io.vproxy.msquic.QuicNewConnectionInfo Info) {
        if (Info == null) {
            InfoVH.set(MEMORY, MemorySegment.NULL);
        } else {
            InfoVH.set(MEMORY, Info.MEMORY);
        }
    }

    private static final VarHandleW ConnectionVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("Connection")
        )
    );

    public MemorySegment getConnection() {
        var SEG = ConnectionVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setConnection(MemorySegment Connection) {
        if (Connection == null) {
            ConnectionVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ConnectionVH.set(MEMORY, Connection);
        }
    }

    public QuicListenerEventNewConnection(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += 8;
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicListenerEventNewConnection(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicListenerEventNewConnection{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Info => ");
            if (CORRUPTED_MEMORY) SB.append("<?>");
            else PanamaUtils.nativeObjectToString(getInfo(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Connection => ");
            SB.append(PanamaUtils.memorySegmentToString(getConnection()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicListenerEventNewConnection> {
        public Array(MemorySegment buf) {
            super(buf, QuicListenerEventNewConnection.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicListenerEventNewConnection.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicListenerEventNewConnection.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicListenerEventNewConnection ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListenerEventNewConnection.Array";
        }

        @Override
        protected QuicListenerEventNewConnection construct(MemorySegment seg) {
            return new QuicListenerEventNewConnection(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicListenerEventNewConnection value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicListenerEventNewConnection> {
        private Func(io.vproxy.pni.CallSite<QuicListenerEventNewConnection> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicListenerEventNewConnection> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventNewConnection> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicListenerEventNewConnection> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicListenerEventNewConnection.Func";
        }

        @Override
        protected QuicListenerEventNewConnection construct(MemorySegment seg) {
            return new QuicListenerEventNewConnection(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:3340a8a1a1119114928e806346b19d30073d9a513dcc601580e0ff5c48948021
