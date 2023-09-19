package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicRegistrationConfig {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS_UNALIGNED.withName("AppName"),
        ValueLayout.JAVA_INT_UNALIGNED.withName("ExecutionProfile"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */
    );
    public final MemorySegment MEMORY;

    private static final VarHandle AppNameVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("AppName")
    );

    public PNIString getAppName() {
        var SEG = (MemorySegment) AppNameVH.get(MEMORY);
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

    private static final VarHandle ExecutionProfileVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ExecutionProfile")
    );

    public int getExecutionProfile() {
        return (int) ExecutionProfileVH.get(MEMORY);
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
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicRegistrationConfig> {
        public Array(MemorySegment buf) {
            super(buf, QuicRegistrationConfig.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicRegistrationConfig.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
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
        protected QuicRegistrationConfig construct(MemorySegment seg) {
            return new QuicRegistrationConfig(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.11
// sha256:abbc99cd80bbd3970887fa73bfc8fd36135b5c1935e7abeb14ab834e2aa69a78
