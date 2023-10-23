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

public class MsQuicMod {
    private MsQuicMod() {
    }

    private static final MsQuicMod INSTANCE = new MsQuicMod();

    public static MsQuicMod get() {
        return INSTANCE;
    }

    private static final MethodHandle MsQuicSetEventLoopThreadDispatcherMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_MsQuicMod_MsQuicSetEventLoopThreadDispatcher", MemorySegment.class /* dispatcher */);

    public int MsQuicSetEventLoopThreadDispatcher(MemorySegment dispatcher) {
        int RESULT;
        try {
            RESULT = (int) MsQuicSetEventLoopThreadDispatcherMH.invokeExact((MemorySegment) (dispatcher == null ? MemorySegment.NULL : dispatcher));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle CxPlatGetCurThreadMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_MsQuicMod_CxPlatGetCurThread", MemorySegment.class /* Thread */);

    public int CxPlatGetCurThread(MemorySegment Thread) {
        int RESULT;
        try {
            RESULT = (int) CxPlatGetCurThreadMH.invokeExact((MemorySegment) (Thread == null ? MemorySegment.NULL : Thread));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle INVOKE_LPTHREAD_START_ROUTINEMH = PanamaUtils.lookupPNICriticalFunction(false, void.class, "JavaCritical_io_vproxy_msquic_MsQuicMod_INVOKE_LPTHREAD_START_ROUTINE", MemorySegment.class /* Callback */, MemorySegment.class /* Context */);

    public void INVOKE_LPTHREAD_START_ROUTINE(MemorySegment Callback, MemorySegment Context) {
        try {
            INVOKE_LPTHREAD_START_ROUTINEMH.invokeExact((MemorySegment) (Callback == null ? MemorySegment.NULL : Callback), (MemorySegment) (Context == null ? MemorySegment.NULL : Context));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:676b8e081f4a4396887387219e28479e9edc59d74ec00a542e97fba452d2c8f8
