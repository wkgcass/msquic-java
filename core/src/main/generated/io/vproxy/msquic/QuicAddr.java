package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicAddr extends AbstractNativeObject implements NativeObject {
    private static final MethodHandle __getLayoutByteSizeMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions().setCritical(true), long.class, "JavaCritical_io_vproxy_msquic_QuicAddr___getLayoutByteSize");

    private static long __getLayoutByteSize() {
        long RESULT;
        try {
            RESULT = (long) __getLayoutByteSizeMH.invokeExact();
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    public static final MemoryLayout LAYOUT = PanamaUtils.padLayout(__getLayoutByteSize(), MemoryLayout::structLayout

    );
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    public QuicAddr(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
    }

    public QuicAddr(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle getFamilyMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicAddr_getFamily", MemorySegment.class /* self */);

    public int getFamily() {
        int RESULT;
        try {
            RESULT = (int) getFamilyMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle setFamilyMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicAddr_setFamily", MemorySegment.class /* self */, int.class /* family */);

    public void setFamily(int family) {
        try {
            setFamilyMH.invokeExact(MEMORY, family);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle getPortMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicAddr_getPort", MemorySegment.class /* self */);

    public int getPort() {
        int RESULT;
        try {
            RESULT = (int) getPortMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle setPortMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicAddr_setPort", MemorySegment.class /* self */, int.class /* port */);

    public void setPort(int port) {
        try {
            setPortMH.invokeExact(MEMORY, port);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle toStringMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicAddr_toString", MemorySegment.class /* self */, String.class /* str */);

    public void toString(PNIString str) {
        try {
            toStringMH.invokeExact(MEMORY, (MemorySegment) (str == null ? MemorySegment.NULL : str.MEMORY));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicAddr{\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicAddr> {
        public Array(MemorySegment buf) {
            super(buf, QuicAddr.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicAddr.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicAddr.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicAddr ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicAddr.Array";
        }

        @Override
        protected QuicAddr construct(MemorySegment seg) {
            return new QuicAddr(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicAddr value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicAddr> {
        private Func(io.vproxy.pni.CallSite<QuicAddr> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicAddr> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicAddr> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicAddr> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicAddr.Func";
        }

        @Override
        protected QuicAddr construct(MemorySegment seg) {
            return new QuicAddr(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.17
// sha256:3797726da6c562ad00ab892f3ee644abb7a0770a5184f3e4c8c01a25461da2f5
