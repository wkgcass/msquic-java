package msquic.nativevalues;

import msquic.internal.NativeValues;

public class StreamShutdownFlags {
    private StreamShutdownFlags() {
    }

    public static final int NONE = NativeValues.get().QUIC_STREAM_SHUTDOWN_FLAG_NONE();
    public static final int GRACEFUL = NativeValues.get().QUIC_STREAM_SHUTDOWN_FLAG_GRACEFUL();
    public static final int ABORT_SEND = NativeValues.get().QUIC_STREAM_SHUTDOWN_FLAG_ABORT_SEND();
    public static final int ABORT_RECEIVE = NativeValues.get().QUIC_STREAM_SHUTDOWN_FLAG_ABORT_RECEIVE();
    public static final int ABORT = NativeValues.get().QUIC_STREAM_SHUTDOWN_FLAG_ABORT();
    public static final int IMMEDIATE = NativeValues.get().QUIC_STREAM_SHUTDOWN_FLAG_IMMEDIATE();
}
