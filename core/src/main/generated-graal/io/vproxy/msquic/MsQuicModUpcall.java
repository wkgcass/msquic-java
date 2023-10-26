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

public class MsQuicModUpcall {
    private static final Arena ARENA = Arena.ofShared();

    public static MemorySegment dispatch;
    public static final CEntryPointLiteral<CFunctionPointer> dispatchCEPL = GraalUtils.defineCFunctionByName(new PNILinkOptions(), io.vproxy.msquic.MsQuicModUpcall.class, "dispatch");

    @CEntryPoint
    public static int dispatch(IsolateThread THREAD, VoidPointer ConfigPTR, VoidPointer EventQPTR, VoidPointer ThreadPTR, VoidPointer ContextPTR) {
        if (IMPL == null) {
            System.out.println("io.vproxy.msquic.MsQuicModUpcall#dispatch");
            System.exit(1);
        }
        var Config = MemorySegment.ofAddress(ConfigPTR.rawValue());
        var EventQ = MemorySegment.ofAddress(EventQPTR.rawValue());
        var Thread = MemorySegment.ofAddress(ThreadPTR.rawValue());
        var Context = MemorySegment.ofAddress(ContextPTR.rawValue());
        var RESULT = IMPL.dispatch(
            (Config.address() == 0 ? null : new io.vproxy.msquic.CXPLAT_THREAD_CONFIG(Config)),
            (EventQ.address() == 0 ? null : EventQ),
            (Thread.address() == 0 ? null : Thread),
            (Context.address() == 0 ? null : Context)
        );
        return RESULT;
    }

    private static void setNativeImpl() {
        dispatch = MemorySegment.ofAddress(dispatchCEPL.getFunctionPointer().rawValue());

        var initMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions().setCritical(true), void.class, "JavaCritical_io_vproxy_msquic_MsQuicModUpcall_INIT", MemorySegment.class);
        try {
            initMH.invoke(dispatch);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        dispatch = PanamaUtils.lookupFunctionPointer(new PNILookupOptions(), "JavaCritical_io_vproxy_msquic_MsQuicModUpcall_dispatch").orElseThrow(() -> new NullPointerException("JavaCritical_io_vproxy_msquic_MsQuicModUpcall_dispatch"));
    }

    private static Interface IMPL = null;

    public static void setImpl(Interface impl) {
        java.util.Objects.requireNonNull(impl);
        IMPL = impl;
        setNativeImpl();
    }

    public interface Interface {
        int dispatch(io.vproxy.msquic.CXPLAT_THREAD_CONFIG Config, MemorySegment EventQ, MemorySegment Thread, MemorySegment Context);
    }
}
// metadata.generator-version: pni 21.0.0.17
// sha256:fe3c0c963c03e483183cd9aa0f84dc0ecf572b1e678da1190cf3a6a7c7e5a90e
