package msquic.nativevalues;

import msquic.internal.NativeValues;

public class StreamOpenFlags {
    private StreamOpenFlags() {
    }

    public static final int NONE = NativeValues.get().QUIC_STREAM_OPEN_FLAG_NONE();
    public static final int UNIDIRECTIONAL = NativeValues.get().QUIC_STREAM_OPEN_FLAG_UNIDIRECTIONAL();
    public static final int ZERO_RTT = NativeValues.get().QUIC_STREAM_OPEN_FLAG_0_RTT();
}
