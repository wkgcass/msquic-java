package msquic.nativevalues;

import msquic.internal.NativeValues;

public class StreamStartFlags {
    private StreamStartFlags() {
    }

    public static final int NONE = NativeValues.get().QUIC_STREAM_START_FLAG_NONE();
    public static final int FAIL_BLOCKED = NativeValues.get().QUIC_STREAM_START_FLAG_FAIL_BLOCKED();
    public static final int IMMEDIATE = NativeValues.get().QUIC_STREAM_START_FLAG_IMMEDIATE();
    public static final int ASYNC = NativeValues.get().QUIC_STREAM_START_FLAG_ASYNC();
}
