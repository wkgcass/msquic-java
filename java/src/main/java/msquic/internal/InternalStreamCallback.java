package msquic.internal;

import msquic.MsQuic;
import msquic.MsQuicException;
import msquic.StreamCallback;
import msquic.StreamEvent;
import msquic.nativevalues.Status;
import msquic.nativevalues.StreamEventType;

public class InternalStreamCallback {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final MsQuic msquic;
    private final StreamCallback cb;

    public InternalStreamCallback(MsQuic msquic, StreamCallback cb) {
        this.msquic = msquic;
        this.cb = cb;
    }

    @SuppressWarnings("unused")
    @UsedByJNI
    public int callback(int type,
                        //
                        long RECEIVE_totalBufferLengthPtr,
                        long RECEIVE_absoluteOffset,
                        long RECEIVE_totalBufferLength,
                        long[] RECEIVE_bufferPtrs,
                        int RECEIVE_receiveFlags
                        //
    ) {
        try {
            cb.callback(new StreamEvent(StreamEventType.valueOf(type),
                RECEIVE_totalBufferLengthPtr, RECEIVE_absoluteOffset, RECEIVE_totalBufferLength, RECEIVE_bufferPtrs, RECEIVE_receiveFlags
            ));
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
