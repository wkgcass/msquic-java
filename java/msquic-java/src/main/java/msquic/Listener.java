package msquic;

import msquic.internal.Native;
import msquic.internal.Utils;

import java.util.Collection;

public class Listener {
    private final MsQuic msquic;
    public final long wrapper;
    private boolean isOpen = true;

    Listener(MsQuic msquic, long wrapper) {
        this.msquic = msquic;
        this.wrapper = wrapper;
    }

    public void close() {
        if (!isOpen) {
            return;
        }
        isOpen = false;
        Native.get().ListenerClose(msquic.msquic, wrapper);
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

    public void start(Collection<String> alpn, Addr addr) throws MsQuicException {
        String[] alpnArr = Utils.collectionToArrayList(alpn);
        Native.get().ListenerStart(msquic.msquic, wrapper, alpnArr, addr.family.intValue, addr.loopback, addr.port);
    }

    @Override
    public String toString() {
        return "Listener(0x" + Long.toHexString(wrapper) + "/" + (isOpen ? "open" : "closed") + ')';
    }
}
