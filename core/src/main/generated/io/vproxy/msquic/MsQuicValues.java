package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class MsQuicValues {
    private MsQuicValues() {
    }

    private static final MsQuicValues INSTANCE = new MsQuicValues();

    public static MsQuicValues get() {
        return INSTANCE;
    }

    private static final MethodHandle QuicStatusStringMH = PanamaUtils.lookupPNICriticalFunction(false, String.class, "JavaCritical_io_vproxy_msquic_MsQuicValues_QuicStatusString", int.class /* status */);

    public PNIString QuicStatusString(int status) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) QuicStatusStringMH.invokeExact(status);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT.address() == 0 ? null : new PNIString(RESULT);
    }

    private static final MethodHandle sizeofQuicAddrMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_MsQuicValues_sizeofQuicAddr");

    public int sizeofQuicAddr() {
        int RESULT;
        try {
            RESULT = (int) sizeofQuicAddrMH.invokeExact();
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle QUIC_STATUS_NOT_SUPPORTEDMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_STATUS_NOT_SUPPORTED");

    public int QUIC_STATUS_NOT_SUPPORTED() {
        int RESULT;
        try {
            RESULT = (int) QUIC_STATUS_NOT_SUPPORTEDMH.invokeExact();
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle QUIC_ADDRESS_FAMILY_UNSPECMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_ADDRESS_FAMILY_UNSPEC");

    public int QUIC_ADDRESS_FAMILY_UNSPEC() {
        int RESULT;
        try {
            RESULT = (int) QUIC_ADDRESS_FAMILY_UNSPECMH.invokeExact();
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle QUIC_ADDRESS_FAMILY_INETMH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_ADDRESS_FAMILY_INET");

    public int QUIC_ADDRESS_FAMILY_INET() {
        int RESULT;
        try {
            RESULT = (int) QUIC_ADDRESS_FAMILY_INETMH.invokeExact();
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle QUIC_ADDRESS_FAMILY_INET6MH = PanamaUtils.lookupPNICriticalFunction(false, int.class, "JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_ADDRESS_FAMILY_INET6");

    public int QUIC_ADDRESS_FAMILY_INET6() {
        int RESULT;
        try {
            RESULT = (int) QUIC_ADDRESS_FAMILY_INET6MH.invokeExact();
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:00b2c6b2f9431a9dc82cc278cba8a3207abcc4f5d559155b329f10341c4bd3da
