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

public class MsQuic {
    private MsQuic() {
    }

    private static final MsQuic INSTANCE = new MsQuic();

    public static MsQuic get() {
        return INSTANCE;
    }

    private static final MethodHandle openMH = PanamaUtils.lookupPNICriticalFunction(false, io.vproxy.msquic.QuicApiTable.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_MsQuic_open", int.class /* Version */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicApiTable open(int Version, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openMH.invokeExact(Version, (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicApiTable.LAYOUT));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicApiTable(RESULT);
    }

    private static final MethodHandle buildQuicAddrMH = PanamaUtils.lookupPNICriticalFunction(false, boolean.class, "JavaCritical_io_vproxy_msquic_MsQuic_buildQuicAddr", String.class /* addr */, int.class /* port */, io.vproxy.msquic.QuicAddr.LAYOUT.getClass() /* result */);

    public boolean buildQuicAddr(PNIString addr, int port, io.vproxy.msquic.QuicAddr result) {
        boolean RESULT;
        try {
            RESULT = (boolean) buildQuicAddrMH.invokeExact((MemorySegment) (addr == null ? MemorySegment.NULL : addr.MEMORY), port, (MemorySegment) (result == null ? MemorySegment.NULL : result.MEMORY));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:62bc1dfce38c1f856c2b07d41a9612b3250a8a541bf30e5c03e9bf97ad6f1edf
