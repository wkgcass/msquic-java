package msquic;

import msquic.internal.InternalListenerCallback;
import msquic.internal.Native;
import msquic.internal.Utils;

import java.util.Collection;

public class Registration {
    private final MsQuic msquic;
    public final long reg;
    private boolean isOpen = true;

    Registration(MsQuic msquic, long reg) {
        this.msquic = msquic;
        this.reg = reg;
    }

    public void close() {
        if (!isOpen) {
            return;
        }
        isOpen = false;
        Native.get().RegistrationClose(msquic.msquic, reg);
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

    public Configuration openConfiguration(Collection<String> alpn, Settings settings) throws MsQuicException {
        String[] alpnArr = Utils.collectionToArrayList(alpn);
        long conf = Native.get().ConfigurationOpen(msquic.msquic, reg, alpnArr,
            settings.idleTimeoutMs == null ? 0 : settings.idleTimeoutMs, settings.idleTimeoutMs != null,
            settings.serverResumptionLevel == null ? 0 : settings.serverResumptionLevel.intValue, settings.serverResumptionLevel == null,
            settings.peerBidiStreamCount == null ? 0 : settings.peerBidiStreamCount, settings.peerBidiStreamCount != null);
        return new Configuration(msquic, conf);
    }

    public Listener openListener(ListenerCallback cb) throws MsQuicException {
        var internalCB = new InternalListenerCallback(msquic, cb);
        long lsn = Native.get().ListenerOpen(msquic.msquic, reg, internalCB);
        return new Listener(msquic, lsn);
    }

    @Override
    public String toString() {
        return "Registration(0x" + Long.toHexString(reg) + "/" + (isOpen ? "open" : "closed") + ')';
    }
}
