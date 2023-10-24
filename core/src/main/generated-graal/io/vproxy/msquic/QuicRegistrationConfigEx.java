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

public class QuicRegistrationConfigEx extends io.vproxy.msquic.QuicRegistrationConfig implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        io.vproxy.msquic.QuicRegistrationConfig.LAYOUT,
        ValueLayout.ADDRESS.withName("Context")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle ContextVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Context")
    );

    public MemorySegment getContext() {
        var SEG = (MemorySegment) ContextVH.get(MEMORY);
        if (SEG.address() == 0) return null;
        return SEG;
    }

    public void setContext(MemorySegment Context) {
        if (Context == null) {
            ContextVH.set(MEMORY, MemorySegment.NULL);
        } else {
            ContextVH.set(MEMORY, Context);
        }
    }

    public QuicRegistrationConfigEx(MemorySegment MEMORY) {
        super(MEMORY);
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += io.vproxy.msquic.QuicRegistrationConfig.LAYOUT.byteSize();
        OFFSET += ValueLayout.ADDRESS_UNALIGNED.byteSize();
    }

    public QuicRegistrationConfigEx(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicRegistrationConfigEx{\n");
        SB.append(" ".repeat(INDENT + 4)).append("SUPER => ");
        {
            INDENT += 4;
            SB.append("QuicRegistrationConfig{\n");
            {
                SB.append(" ".repeat(INDENT + 4)).append("AppName => ");
                if (CORRUPTED_MEMORY) SB.append("<?>");
                else PanamaUtils.nativeObjectToString(getAppName(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
            }
            SB.append(",\n");
            {
                SB.append(" ".repeat(INDENT + 4)).append("ExecutionProfile => ");
                SB.append(getExecutionProfile());
            }
            SB.append("\n");
            SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
            INDENT -= 4;
            SB.append(",\n");
        }
        {
            SB.append(" ".repeat(INDENT + 4)).append("Context => ");
            SB.append(PanamaUtils.memorySegmentToString(getContext()));
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicRegistrationConfigEx> {
        public Array(MemorySegment buf) {
            super(buf, QuicRegistrationConfigEx.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicRegistrationConfigEx.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicRegistrationConfigEx.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicRegistrationConfigEx ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicRegistrationConfigEx.Array";
        }

        @Override
        protected QuicRegistrationConfigEx construct(MemorySegment seg) {
            return new QuicRegistrationConfigEx(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicRegistrationConfigEx value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicRegistrationConfigEx> {
        private Func(io.vproxy.pni.CallSite<QuicRegistrationConfigEx> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicRegistrationConfigEx> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicRegistrationConfigEx> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicRegistrationConfigEx> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicRegistrationConfigEx.Func";
        }

        @Override
        protected QuicRegistrationConfigEx construct(MemorySegment seg) {
            return new QuicRegistrationConfigEx(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:539d1091b2e03f8f36af70323efdaa672621ddc901d0e08e7acf829a6122cc74
