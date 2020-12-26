package msquic.nativevalues;

import msquic.internal.NativeValues;

public class SendResumptionFlags {
    private SendResumptionFlags() {
    }

    public static final int NONE = NativeValues.get().QUIC_SEND_RESUMPTION_FLAG_NONE();
    public static final int FINAL = NativeValues.get().QUIC_SEND_RESUMPTION_FLAG_FINAL();
}
