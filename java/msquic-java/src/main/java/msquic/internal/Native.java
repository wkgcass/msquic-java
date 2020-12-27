package msquic.internal;

import msquic.MemoryAllocator;
import msquic.MsQuicException;

import java.nio.ByteBuffer;

public class Native {
    private static Native instance;

    private Native() {
    }

    static {
        try {
            Class.forName(NativeLibraryLoader.class.getName());
        } catch (ClassNotFoundException ignore) {
        }
    }

    public static Native get() {
        if (instance == null) {
            synchronized (Native.class) {
                if (instance == null) {
                    instance = new Native();
                }
            }
        }
        return instance;
    }

    public native int QuicBufferLength(long bufPtr);

    public native int QuicBufferRead(long bufPtr, int srcOff, ByteBuffer dst, int dstOff, int maxReadLen);

    public native void MsQuicJavaInit(MemoryAllocator<?> allocator);

    public native void MsQuicJavaRelease();

    public native long MsQuicOpen() throws MsQuicException;

    public native void MsQuicClose(long msquic);

    public native long RegistrationOpen(long msquic, String appName, int profile) throws MsQuicException;

    public native void RegistrationClose(long msquic, long reg);

    public native void RegistrationShutdown(long msquic, long reg, int connectionShutdownFlags, long errorCode);

    public native long ConfigurationOpen(long msquic, long reg, String[] alpn,
                                         int idleTimeoutMs, boolean isSetIdleTimeoutMs,
                                         int serverResumeptionLevel, boolean isSetServerResumeptionLevel,
                                         int peerBidiStreamCount, boolean isSetPeerBidiStreamCount)
        throws MsQuicException;

    public native void ConfigurationClose(long msquic, long conf);

    public native void ConfigurationLoadCredential(long msquic, long conf, String certFilePath, String keyFilePath) throws MsQuicException;

    public native void ConfigurationLoadAsClient(long msquic, long conf, boolean noCertValidation) throws MsQuicException;

    public native long ListenerOpen(long msquic, long reg, InternalListenerCallback cb) throws MsQuicException;

    public native void ListenerClose(long msquic, long lsn);

    public native void ListenerStart(long msquic, long lsn, String[] alpn, int family, boolean loopback, int port) throws MsQuicException;

    public native long ConnectionOpen(long msquic, long reg, InternalConnectionCallback cb) throws MsQuicException;

    public native void ConnectionClose(long msquic, long conn);

    public native void ConnectionStart(long msquic, long conn, long conf, int family, String address, int port) throws MsQuicException;

    public native void ConnectionShutdown(long msquic, long conn, int flags, long errorCode);

    public native void ConnectionSetCallbackHandler(long msquic, long conn, InternalConnectionCallback cb);

    public native void ConnectionSetConfiguration(long msquic, long conn, long conf) throws MsQuicException;

    public native void ConnectionSendResumptionTicket(long msquic, long conn, int sendResumptionFlags) throws MsQuicException;

    public native String GetConnectionLocalAddress(long msquic, long conn) throws MsQuicException;

    public native String GetConnectionRemoteAddress(long msquic, long conn) throws MsQuicException;

    public native long StreamOpen(InternalConnectionCallback connCB, long msquic, long conn, int streamOpenFlags, InternalStreamCallback cb) throws MsQuicException;

    public native void StreamClose(long msquic, long stream);

    public native void StreamStart(long msquic, long stream, int streamStartFlags) throws MsQuicException;

    public native void StreamSetCallbackHandler(long msquic, long stream, InternalStreamCallback cb);

    public native void StreamShutdown(long msquic, long stream, int shutdownFlags) throws MsQuicException;

    public native void StreamSend(long msquic, long stream, int sendFlags, ByteBuffer directByteBuffer, int off, int lim) throws MsQuicException;

    public native void StreamReceiveComplete(long msquic, long stream, long consumedLen);

    public native void StreamReceiveSetEnabled(long msquic, long stream, boolean enabled);

    public native void StreamReceiveSetTotalLength(long ptr, long len);

    public native long GetStreamId(long msquic, long stream) throws MsQuicException;
}
