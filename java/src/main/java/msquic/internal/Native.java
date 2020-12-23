package msquic.internal;

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

    public native void MsQuicJavaInit();

    public native long MsQuicOpen() throws MsQuicException;

    public native void MsQuicClose(long msquic);

    public native long RegistrationOpen(long msquic, String appName, int profile) throws MsQuicException;

    public native void RegistrationClose(long msquic, long reg);

    public native long ConfigurationOpen(long msquic, long reg, String[] alpn,
                                         int idleTimeoutMs, boolean isSetIdleTimeoutMs,
                                         int serverResumeptionLevel, boolean isSetServerResumeptionLevel,
                                         int peerBidiStreamCount, boolean isSetPeerBidiStreamCount)
        throws MsQuicException;

    public native void ConfigurationClose(long msquic, long conf);

    public native void ConfigurationLoadCredential(long msquic, long conf, String certFilePath, String keyFilePath) throws MsQuicException;

    public native long ListenerOpen(long msquic, long reg, InternalListenerCallback cb) throws MsQuicException;

    public native void ListenerClose(long msquic, long lsn);

    public native void ListenerStart(long msquic, long lsn, String[] alpn, int family, boolean loopback, int port) throws MsQuicException;

    public native void ConnectionClose(long msquic, long conn);

    public native void ConnectionSetCallbackHandler(long msquic, long conn, InternalConnectionCallback cb);

    public native void ConnectionSetConfiguration(long msquic, long conn, long conf) throws MsQuicException;

    public native void ConnectionSendResumptionTicket(long msquic, long conn, int sendResumptionFlags) throws MsQuicException;

    public native void StreamClose(long msquic, long stream);

    public native void StreamSetCallbackHandler(long msquic, long stream, InternalStreamCallback cb);

    public native void StreamShutdown(long msquic, long stream, int shutdownFlags) throws MsQuicException;

    public native void StreamSend(long msquic, long stream, int sendFlags, ByteBuffer directByteBuffer, int off, int lim) throws MsQuicException;
}
