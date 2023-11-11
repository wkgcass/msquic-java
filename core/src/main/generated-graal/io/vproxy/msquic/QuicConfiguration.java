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

public class QuicConfiguration extends io.vproxy.msquic.QuicObjectBase implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        io.vproxy.msquic.QuicObjectBase.LAYOUT

    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    public QuicConfiguration(MemorySegment MEMORY) {
        super(MEMORY);
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += io.vproxy.msquic.QuicObjectBase.LAYOUT.byteSize();
    }

    public QuicConfiguration(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicConfiguration_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle loadCredentialMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicConfiguration_loadCredential", MemorySegment.class /* self */, io.vproxy.msquic.QuicCredentialConfig.LAYOUT.getClass() /* CredConfig */);

    public int loadCredential(io.vproxy.msquic.QuicCredentialConfig CredConfig) {
        int RESULT;
        try {
            RESULT = (int) loadCredentialMH.invokeExact(MEMORY, (MemorySegment) (CredConfig == null ? MemorySegment.NULL : CredConfig.MEMORY));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConfiguration{\n");
        SB.append(" ".repeat(INDENT + 4)).append("SUPER => ");
        {
            INDENT += 4;
            SB.append("QuicObjectBase{\n");
            {
                SB.append(" ".repeat(INDENT + 4)).append("Api => ");
                SB.append(PanamaUtils.memorySegmentToString(getApi()));
            }
            SB.append(",\n");
            {
                SB.append(" ".repeat(INDENT + 4)).append("Handle => ");
                SB.append(PanamaUtils.memorySegmentToString(getHandle()));
            }
            SB.append("\n");
            SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
            INDENT -= 4;
            SB.append("\n");

        }
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConfiguration> {
        public Array(MemorySegment buf) {
            super(buf, QuicConfiguration.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConfiguration.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConfiguration.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConfiguration ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConfiguration.Array";
        }

        @Override
        protected QuicConfiguration construct(MemorySegment seg) {
            return new QuicConfiguration(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConfiguration value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConfiguration> {
        private Func(io.vproxy.pni.CallSite<QuicConfiguration> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConfiguration> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConfiguration> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConfiguration> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConfiguration.Func";
        }

        @Override
        protected QuicConfiguration construct(MemorySegment seg) {
            return new QuicConfiguration(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.18
// sha256:2cd4a79ab9deb11c80fbb979de36d2315d3f4d81c76ab04ed6b678c92d619c83
