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

public class MsQuicMod {
    private MsQuicMod() {
    }

    private static final MsQuicMod INSTANCE = new MsQuicMod();

    public static MsQuicMod get() {
        return INSTANCE;
    }

    private static final MethodHandle openExtraMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), io.vproxy.msquic.QuicExtraApiTable.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_MsQuicMod_openExtra", int.class /* Version */, MemorySegment.class /* returnStatus */);

    public io.vproxy.msquic.QuicExtraApiTable openExtra(int Version, IntArray returnStatus) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openExtraMH.invokeExact(Version, (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicExtraApiTable(RESULT);
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
// metadata.generator-version: pni 21.0.0.20
// sha256:6111e3e38c2eb86ad9c1bd975ac7790d0f62002e5233696265a3bc1c9aa4d387
