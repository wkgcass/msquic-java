package msquic;

import msquic.internal.InternalConnectionCallback;
import msquic.internal.InternalListenerCallback;
import msquic.internal.Native;

public class Connection {
    private final MsQuic msquic;
    public final long real;
    public final long wrapper;
    private boolean isOpen = true;
    private final InternalListenerCallback listenerCallback;

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
        listenerCallback.removeConnection(this);
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

    public void setCallbackHandler(ConnectionCallback cb) {
        var connCB = new InternalConnectionCallback(msquic, cb);
        Native.get().ConnectionSetCallbackHandler(msquic.msquic, wrapper, connCB);
    }

    public void setConfiguration(Configuration conf) throws MsQuicException {
        Native.get().ConnectionSetConfiguration(msquic.msquic, wrapper, conf.conf);
    }

    public void sendResumptionTicket(int flags) throws MsQuicException {
        Native.get().ConnectionSendResumptionTicket(msquic.msquic, wrapper, flags);
    }

    @Override
    public String toString() {
        return "Connection(0x" + Long.toHexString(real) + "/0x" + Long.toHexString(wrapper) + "/" + (isOpen ? "open" : "closed") + ')';
    }
}
