package msquic.nativevalues;

import msquic.internal.NativeValues;

public class SendFlags {
    private SendFlags() {
    }

    public static final int NONE = NativeValues.get().QUIC_SEND_FLAG_NONE();
    public static final int ALLOW_0_RTT = NativeValues.get().QUIC_SEND_FLAG_ALLOW_0_RTT();
    public static final int START = NativeValues.get().QUIC_SEND_FLAG_START();
    public static final int FIN = NativeValues.get().QUIC_SEND_FLAG_FIN();
    public static final int DGRAM_PRIORITY = NativeValues.get().QUIC_SEND_FLAG_DGRAM_PRIORITY();
}
