package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class MsQuicMod {
    private MsQuicMod() {
    }

    private static final MsQuicMod INSTANCE = new MsQuicMod();

    public static MsQuicMod get() {
        return INSTANCE;
    }

    private static final MethodHandle MsQuicSetThreadCountLimitMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "MsQuicSetThreadCountLimit", int.class /* limit */);

    public void MsQuicSetThreadCountLimit(int limit) {
        try {
            MsQuicSetThreadCountLimitMH.invokeExact(limit);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle MsQuicSetEventLoopThreadDispatcherMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "MsQuicSetEventLoopThreadDispatcher", MemorySegment.class /* dispatcher */);

    public int MsQuicSetEventLoopThreadDispatcher(MemorySegment dispatcher) {
        int RESULT;
        try {
            RESULT = (int) MsQuicSetEventLoopThreadDispatcherMH.invokeExact((MemorySegment) (dispatcher == null ? MemorySegment.NULL : dispatcher));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle CxPlatGetCurThreadMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "CxPlatGetCurThread", MemorySegment.class /* Thread */);

    public int CxPlatGetCurThread(MemorySegment Thread) {
        int RESULT;
        try {
            RESULT = (int) CxPlatGetCurThreadMH.invokeExact((MemorySegment) (Thread == null ? MemorySegment.NULL : Thread));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle INVOKE_LPTHREAD_START_ROUTINEMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_MsQuicMod_INVOKE_LPTHREAD_START_ROUTINE", MemorySegment.class /* Callback */, MemorySegment.class /* Context */);

    public void INVOKE_LPTHREAD_START_ROUTINE(MemorySegment Callback, MemorySegment Context) {
        try {
            INVOKE_LPTHREAD_START_ROUTINEMH.invokeExact((MemorySegment) (Callback == null ? MemorySegment.NULL : Callback), (MemorySegment) (Context == null ? MemorySegment.NULL : Context));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle MsQuicSetIsWorkerMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "MsQuicSetIsWorker", boolean.class /* isWorker */);

    public void MsQuicSetIsWorker(boolean isWorker) {
        try {
            MsQuicSetIsWorkerMH.invokeExact(isWorker);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }
}
// metadata.generator-version: pni 21.0.0.18
// sha256:353eee6ad804c1c6c1989b22c3a03a351aa58848c897217ac38fbac7f26176a1
