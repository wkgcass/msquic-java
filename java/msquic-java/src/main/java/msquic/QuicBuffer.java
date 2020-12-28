package msquic;

import msquic.internal.Native;

import java.nio.ByteBuffer;

public class QuicBuffer {
    private final long ptr;
    private int length = -1;

    private int off = 0;

    QuicBuffer(long ptr) {
        this.ptr = ptr;
    }

    public int length() {
        if (length == -1) {
            length = Native.get().QuicBufferLength(ptr);
        }
        return length;
    }

    public int used() {
        return length() - off;
    }

    public int read(ByteBuffer dst) {
        int n = read(dst, off);
        off += n;
        return n;
    }

    public int read(ByteBuffer dst, int srcOffset) {
        int dstOff = dst.position();
        int maxLen = dst.limit() - dst.position();

        int readLen = Native.get().QuicBufferRead(ptr, srcOffset, dst, dstOff, maxLen);
        dst.position(dstOff + readLen);

        return readLen;
    }

    @Override
    public String toString() {
        return "QuicBuffer(0x" + Long.toHexString(ptr) + "/" + length() + ')';
    }
}
