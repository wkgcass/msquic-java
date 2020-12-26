package msquic.nativevalues;

import msquic.internal.NativeValues;

public class ReceiveFlags {
    private ReceiveFlags() {
    }

    public static final int NONE = NativeValues.get().QUIC_RECEIVE_FLAG_NONE();
    public static final int ZERO_RTT = NativeValues.get().QUIC_RECEIVE_FLAG_0_RTT();
    public static final int FIN = NativeValues.get().QUIC_RECEIVE_FLAG_FIN();
}
