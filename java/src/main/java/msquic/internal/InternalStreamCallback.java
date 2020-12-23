package msquic.internal;

import msquic.MsQuic;
import msquic.MsQuicException;
import msquic.StreamCallback;
import msquic.StreamEvent;
import msquic.nativevalues.Status;
import msquic.nativevalues.StreamEventType;

public class InternalStreamCallback {
    private final MsQuic msquic;
    private final StreamCallback cb;

    public InternalStreamCallback(MsQuic msquic, StreamCallback cb) {
        this.msquic = msquic;
        this.cb = cb;
    }

    @SuppressWarnings("unused")
    @UsedByJNI
    public int callback(int type) {
        try {
            cb.callback(new StreamEvent(StreamEventType.valueOf(type)));
        } catch (MsQuicException e) {
            return e.status.intValue;
        } catch (Throwable t) {
            System.out.println("InternalStreamCallback failed with unexpected Exception");
            t.printStackTrace();
            return Status.INTERNAL_ERROR.intValue;
        }
        return Status.SUCCESS.intValue;
    }
}
