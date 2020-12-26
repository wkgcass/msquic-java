package msquic;

import msquic.internal.InternalConnectionCallback;
import msquic.internal.InternalStreamCallback;
import msquic.internal.Native;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class Stream {
    private final MsQuic msquic;
    public final long real;
    public final long wrapper;
    private boolean isOpen = true;
    private final InternalConnectionCallback connectionCallback;

    Stream(MsQuic msquic, long real, long wrapper, InternalConnectionCallback connectionCallback) {
        this.msquic = msquic;
        this.real = real;
        this.wrapper = wrapper;
        this.connectionCallback = connectionCallback;
    }

    public static void initClass() {
        InternalConnectionCallback.streamConstructor = Stream::new;
    }

    public void close() {
        if (!isOpen) {
            return;
        }
        isOpen = false;
        Native.get().StreamClose(msquic.msquic, wrapper);
        connectionCallback.removeStream(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    public void start(int flags) throws MsQuicException {
        Native.get().StreamStart(msquic.msquic, wrapper, flags);
    }

    public void shutdown(int flags) throws MsQuicException {
        Native.get().StreamShutdown(msquic.msquic, wrapper, flags);
    }

    public void setCallbackHandler(StreamCallback cb) {
        var streamCB = new InternalStreamCallback(msquic, cb);
        Native.get().StreamSetCallbackHandler(msquic.msquic, wrapper, streamCB);
    }

    private final LinkedList<ByteBuffer> wBufs = new LinkedList<>();

    public void send(int flags, ByteBuffer buf) throws MsQuicException {
        int off = buf.position();
        int len = buf.limit() - buf.position();
        Native.get().StreamSend(msquic.msquic, wrapper, flags, buf, off, len);
        buf.position(buf.limit());
        wBufs.add(buf);
    }

    public ByteBuffer pollWBuf() {
        return wBufs.pollFirst();
    }

    public void receiveComplete(long consumedLength) {
        Native.get().StreamReceiveComplete(msquic.msquic, wrapper, consumedLength);
    }

    public void setReceiveEnabled(boolean enabled) {
        Native.get().StreamReceiveSetEnabled(msquic.msquic, wrapper, enabled);
    }

    public long getId() throws MsQuicException {
        return Native.get().GetStreamId(msquic.msquic, wrapper);
    }

    @Override
    public String toString() {
        return "Stream(0x" + Long.toHexString(real) + "/0x" + Long.toHexString(wrapper) + "/" + (isOpen ? "open" : "closed") + ')';
    }
}
