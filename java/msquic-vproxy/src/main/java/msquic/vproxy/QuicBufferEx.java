package msquic.vproxy;

import msquic.QuicBuffer;

import java.nio.ByteBuffer;

public class QuicBufferEx {
    private final QuicBuffer buffer;
    private int off = 0;

    public QuicBufferEx(QuicBuffer buffer) {
        this.buffer = buffer;
    }

    public int used() {
        return buffer.length() - off;
    }

    public void read(ByteBuffer buf) {
        int n = buffer.read(buf, off);
        off += n;
    }
}
