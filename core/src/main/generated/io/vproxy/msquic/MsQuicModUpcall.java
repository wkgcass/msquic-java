package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class MsQuicModUpcall {
    private static final Arena ARENA = Arena.ofShared();

    public static final MemorySegment dispatch;

    private static int dispatch(MemorySegment Config, MemorySegment EventQ, MemorySegment Thread, MemorySegment Context) {
        if (IMPL == null) {
            System.out.println("io.vproxy.msquic.MsQuicModUpcall#dispatch");
            System.exit(1);
        }
        var RESULT = IMPL.dispatch(
            (Config.address() == 0 ? null : new io.vproxy.msquic.CXPLAT_THREAD_CONFIG(Config)),
            (EventQ.address() == 0 ? null : EventQ),
            (Thread.address() == 0 ? null : Thread),
            (Context.address() == 0 ? null : Context)
        );
        return RESULT;
    }

    static {
        MethodHandle dispatchMH;
        try {
            dispatchMH = MethodHandles.lookup().findStatic(io.vproxy.msquic.MsQuicModUpcall.class, "dispatch", MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class, MemorySegment.class, MemorySegment.class));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        dispatch = PanamaUtils.defineCFunction(new PNILinkOptions(), ARENA, dispatchMH, int.class, MemorySegment.class, MemorySegment.class, MemorySegment.class, MemorySegment.class);

        var initMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions().setCritical(true), void.class, "JavaCritical_io_vproxy_msquic_MsQuicModUpcall_INIT", MemorySegment.class);
        try {
            initMH.invoke(dispatch);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static Interface IMPL = null;

    public static void setImpl(Interface impl) {
        java.util.Objects.requireNonNull(impl);
        IMPL = impl;
    }

    public interface Interface {
        int dispatch(io.vproxy.msquic.CXPLAT_THREAD_CONFIG Config, MemorySegment EventQ, MemorySegment Thread, MemorySegment Context);
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:d90f1b3f77fb4caa39ce2779e160fa73c6964078af88a2dc22235484ec9d2c02
