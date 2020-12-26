package msquic;

import msquic.internal.InternalConnectionCallback;
import msquic.internal.InternalListenerCallback;
import msquic.internal.InternalStreamCallback;
import msquic.internal.Native;
import msquic.nativevalues.AddressFamily;

public class Connection {
    private final MsQuic msquic;
    public final long real;
    public final long wrapper;
    private boolean isOpen = true;
    private final InternalListenerCallback listenerCallback;
    private InternalConnectionCallback connectionCallback;

    Connection(MsQuic msquic, long real, long wrapper, InternalListenerCallback listenerCallback) {
        this.msquic = msquic;
        this.real = real;
        this.wrapper = wrapper;
        this.listenerCallback = listenerCallback;
    }

    public static void initClass() {
        InternalListenerCallback.constructConnection = Connection::new;
    }

    public void close() {
        if (!isOpen) {
            return;
        }
        isOpen = false;
        Native.get().ConnectionClose(msquic.msquic, wrapper);
        if (listenerCallback != null) {
            listenerCallback.removeConnection(this);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    public void start(Configuration conf, AddressFamily family, String targetHost, int targetPort) throws MsQuicException {
        Native.get().ConnectionStart(msquic.msquic, wrapper, conf.conf, family.intValue, targetHost, targetPort);
    }

    public void shutdown(int flags, long errorCode) {
        Native.get().ConnectionShutdown(msquic.msquic, wrapper, flags, errorCode);
    }

    public void setCallbackHandler(ConnectionCallback cb) {
        if (connectionCallback != null) {
            throw new IllegalStateException("callback already set");
        }
        setCallbackHandler0(new InternalConnectionCallback(msquic, cb));
        Native.get().ConnectionSetCallbackHandler(msquic.msquic, wrapper, connectionCallback);
    }

    void setCallbackHandler0(InternalConnectionCallback cb) {
        this.connectionCallback = cb;
    }

    public void setConfiguration(Configuration conf) throws MsQuicException {
        Native.get().ConnectionSetConfiguration(msquic.msquic, wrapper, conf.conf);
    }

    public void sendResumptionTicket(int flags) throws MsQuicException {
        Native.get().ConnectionSendResumptionTicket(msquic.msquic, wrapper, flags);
    }

    public Stream openStream(int flags, StreamCallback cb) throws MsQuicException {
        if (connectionCallback == null) {
            throw new IllegalStateException("callback not set");
        }
        var internalCB = new InternalStreamCallback(msquic, cb);
        long stream = Native.get().StreamOpen(connectionCallback, msquic.msquic, wrapper, flags, internalCB);
        Stream s = connectionCallback.getStreamByWrapperPtr(stream);
        if (s == null) {
            // should not happen
            throw new IllegalStateException("getting stream of " + stream + " failed");
        }
        return s;
    }

    public String getLocalAddress() throws MsQuicException {
        return Native.get().GetConnectionLocalAddress(msquic.msquic, wrapper);
    }

    public String getRemoteAddress() throws MsQuicException {
        return Native.get().GetConnectionRemoteAddress(msquic.msquic, wrapper);
    }

    @Override
    public String toString() {
        return "Connection("
            + (real == -1 ? "-" : "0x" + Long.toHexString(real))
            + "/0x" + Long.toHexString(wrapper) + "/" + (isOpen ? "open" : "closed") + ')';
    }
}
