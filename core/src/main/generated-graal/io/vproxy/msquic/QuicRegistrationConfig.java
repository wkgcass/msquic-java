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

public class QuicRegistrationConfig extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("AppName"),
        ValueLayout.JAVA_INT.withName("ExecutionProfile"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW AppNameVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("AppName")
        )
    );

    public PNIString getAppName() {
        var SEG = AppNameVH.getMemorySegment(MEMORY);
        if (SEG.address() == 0) return null;
        return new PNIString(SEG);
    }

    public void setAppName(String AppName, Allocator ALLOCATOR) {
        this.setAppName(new PNIString(ALLOCATOR, AppName));
    }

    public void setAppName(PNIString AppName) {
        if (AppName == null) {
            AppNameVH.set(MEMORY, MemorySegment.NULL);
        } else {
            AppNameVH.set(MEMORY, AppName.MEMORY);
        }
    }

    private static final VarHandleW ExecutionProfileVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("ExecutionProfile")
        )
    );

    public int getExecutionProfile() {
        return ExecutionProfileVH.getInt(MEMORY);
    }

    public void setExecutionProfile(int ExecutionProfile) {
        ExecutionProfileVH.set(MEMORY, ExecutionProfile);
    }

    public QuicRegistrationConfig(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += 8;
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
    }

    public QuicRegistrationConfig(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
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
    }

    public static class Array extends RefArray<QuicRegistrationConfig> {
        public Array(MemorySegment buf) {
            super(buf, QuicRegistrationConfig.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicRegistrationConfig.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicRegistrationConfig.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicRegistrationConfig ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicRegistrationConfig.Array";
        }

        @Override
        protected QuicRegistrationConfig construct(MemorySegment seg) {
            return new QuicRegistrationConfig(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicRegistrationConfig value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicRegistrationConfig> {
        private Func(io.vproxy.pni.CallSite<QuicRegistrationConfig> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicRegistrationConfig> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicRegistrationConfig> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicRegistrationConfig> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicRegistrationConfig.Func";
        }

        @Override
        protected QuicRegistrationConfig construct(MemorySegment seg) {
            return new QuicRegistrationConfig(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:5a44a35beebb9213052867355725de8321b6e5420227512f41a5506b683c5f0e
