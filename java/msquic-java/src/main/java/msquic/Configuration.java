package msquic;

import msquic.internal.Native;

public class Configuration {
    private final MsQuic msquic;
    public final long conf;
    private boolean isOpen = true;

    Configuration(MsQuic msquic, long conf) {
        this.msquic = msquic;
        this.conf = conf;
    }

    public void close() {
        if (!isOpen) {
            return;
        }
        isOpen = false;
        Native.get().ConfigurationClose(msquic.msquic, conf);
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

    public void loadCredential(String certFilePath, String keyFilePath) throws MsQuicException {
        Native.get().ConfigurationLoadCredential(msquic.msquic, conf, certFilePath, keyFilePath);
    }

    public void initAsClient(boolean noCertValidation) throws MsQuicException {
        Native.get().ConfigurationLoadAsClient(msquic.msquic, conf, noCertValidation);
    }

    @Override
    public String toString() {
        return "Configuration(0x" + Long.toHexString(conf) + "/" + (isOpen ? "open" : "closed") + ')';
    }
}
