package msquic;

import msquic.internal.Native;
import msquic.nativevalues.StreamEventType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StreamEvent {
    public final StreamEventType type;
    public final RECEIVE_DATA RECEIVE;

    public StreamEvent(StreamEventType type,
                       //
                       long RECEIVE_totalBufferLengthPtr,
                       long RECEIVE_absoluteOffset,
                       long RECEIVE_totalBufferLength,
                       long[] RECEIVE_bufferPtrs,
                       int RECEIVE_receiveFlags
                       //
    ) {
        this.type = type;
        if (type == StreamEventType.RECEIVE) {
            RECEIVE = new RECEIVE_DATA(RECEIVE_totalBufferLengthPtr, RECEIVE_absoluteOffset, RECEIVE_totalBufferLength, RECEIVE_bufferPtrs, RECEIVE_receiveFlags);
        } else {
            RECEIVE = null;
        }
    }

    public static class RECEIVE_DATA {
        private final long totalBufferLengthPtr;
        public final long absoluteOffset;
        private long totalBufferLength;
        public final List<QuicBuffer> buffers;
        public final int receiveFlags;

        public RECEIVE_DATA(long totalBufferLengthPtr, long absoluteOffset, long totalBufferLength, long[] bufferPtrs, int receiveFlags) {
            this.totalBufferLengthPtr = totalBufferLengthPtr;
            this.absoluteOffset = absoluteOffset;
            this.totalBufferLength = totalBufferLength;
            var bufs = new ArrayList<QuicBuffer>(bufferPtrs.length);
            for (long p : bufferPtrs) {
                bufs.add(new QuicBuffer(p));
            }
            this.buffers = Collections.unmodifiableList(bufs);
            this.receiveFlags = receiveFlags;
        }

        public long getTotalBufferLength() {
            return totalBufferLength;
        }

        public void setTotalBufferLength(long len) {
            Native.get().StreamReceiveSetTotalLength(totalBufferLengthPtr, len);
            this.totalBufferLength = len;
        }
    }
}
