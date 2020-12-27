package msquic.nativevalues;

import msquic.internal.NativeValues;

public class ConnectionShutdownFlags {
    private ConnectionShutdownFlags() {
    }

    public static final int NONE = NativeValues.get().QUIC_CONNECTION_SHUTDOWN_FLAG_NONE();
    public static final int SILENT = NativeValues.get().QUIC_CONNECTION_SHUTDOWN_FLAG_SILENT();
}
