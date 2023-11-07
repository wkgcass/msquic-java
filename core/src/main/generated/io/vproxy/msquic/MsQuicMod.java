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
}
// metadata.generator-version: pni 21.0.0.18
// sha256:93936cd472eadda6ea18b5a7bea793225da77790a5bb5ea6488c1526ec05d74c
