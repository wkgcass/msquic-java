package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

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
// metadata.generator-version: pni 21.0.0.15
// sha256:f1c84a8ddf5717ca1a410b882c44146b2be473ea81016487a42b93055a0e3d76
