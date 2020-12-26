package msquic;

import msquic.internal.Native;

import java.nio.ByteBuffer;

public class QuicBuffer {
    private final long ptr;
    private int length = -1;

    QuicBuffer(long ptr) {
        this.ptr = ptr;
    }

    public int length() {
        if (length == -1) {
            length = Native.get().QuicBufferLength(ptr);
        }
        return length;
    }

    public int read(ByteBuffer dst) {
        return read(dst, 0);
    }

    public int read(ByteBuffer dst, long srcOffset) {
        int dstOff = dst.position();
        int maxLen = dst.limit() - dst.position();

        int readLen = Native.get().QuicBufferRead(ptr, srcOffset, dst, dstOff, maxLen);
        dst.position(dstOff + readLen);

        return readLen;
    }

    @Override
    public String toString() {
        return "QuicBuffer(0x" + Long.toHexString(ptr) + "/" + length + ')';
    }
}
