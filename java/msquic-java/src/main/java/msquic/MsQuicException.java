package msquic;

import msquic.nativevalues.Status;

import java.io.IOException;

public class MsQuicException extends IOException {
    public static final MsQuicException PENDING = new MsQuicException(Status.PENDING);

    public final Status status;
    public final int errCode;

    public MsQuicException(Status status) {
        super(status.name());
        this.status = status;
        this.errCode = status.intValue;
    }

    public MsQuicException(int errCode) {
        super(Status.tryToGetByValue(errCode) == null ? "unknown status: " + errCode : Status.valueOf(errCode).name());
        Status status = Status.tryToGetByValue(errCode);
        if (status == null) {
            this.status = Status.UNKNOWN;
        } else {
            this.status = Status.INTERNAL_ERROR;
        }
        this.errCode = errCode;
    }

    @Override
    public String toString() {
        return "MsQuicException(" + status + "/" + errCode + ')';
    }
}
