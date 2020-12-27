package msquic.vproxy;

import vproxybase.util.Logger;
import vproxybase.util.direct.DirectByteBuffer;
import vproxybase.util.direct.DirectMemoryUtils;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class DirectBuffer {
    private final DirectByteBuffer buf;
    private final int cap;
    private int ePos = 0;
    private int sPos = 0;
    private boolean ePosIsAfterSPos = true;

    private final LinkedList<Slice> slices = new LinkedList<>();

    public DirectBuffer(int size) {
        this.buf = DirectMemoryUtils.allocateDirectBuffer(size);
        this.cap = buf.capacity();
    }

    public int free() {
        if (ePosIsAfterSPos) {
            return ePos - sPos;
        } else {
            return cap - sPos + ePos;
        }
    }

    public Slice allocate(int requestLength) {
        assert Logger.lowLevelDebug("before allocating sPos=" + sPos
            + ", ePos=" + ePos + ", ePosIsAfterSPos=" + ePosIsAfterSPos);
        int actualLen;
        if (ePosIsAfterSPos) {
            actualLen = cap - ePos;
        } else {
            actualLen = sPos - ePos;
        }
        if (actualLen > requestLength) {
            actualLen = requestLength;
        }
        if (actualLen == 0) { // cannot allocate
            assert Logger.lowLevelDebug("cannot allocate");
            return null;
        }
        Slice s = new Slice(ePos, ePos + actualLen);
        ePos += actualLen;
        if (ePos == cap) {
            if (ePosIsAfterSPos) {
                ePos = 0;
                ePosIsAfterSPos = false;
            }
        }
        assert Logger.lowLevelDebug("after allocating sPos=" + sPos
            + ", ePos=" + ePos + ", ePosIsAfterSPos=" + ePosIsAfterSPos);
        slices.add(s);
        return s;
    }

    public void release() {
        assert Logger.lowLevelDebug("before releasing sPos=" + sPos
            + ", ePos=" + ePos + ", ePosIsAfterSPos=" + ePosIsAfterSPos);
        var slice = slices.pollFirst();
        assert slice != null;
        sPos = slice.lim;
        if (ePosIsAfterSPos) {
            if (sPos == ePos) {
                // reset all
                sPos = 0;
                ePos = 0;
            }
        } else {
            if (sPos == cap) {
                // reset e/s state
                sPos = 0;
                ePosIsAfterSPos = true;
            }
        }
        assert Logger.lowLevelDebug("after releasing sPos=" + sPos
            + ", ePos=" + ePos + ", ePosIsAfterSPos=" + ePosIsAfterSPos);
    }

    public void clean() {
        buf.clean();
    }

    public class Slice {
        private final int off;
        private final int lim;
        public final int len;

        public Slice(int off, int lim) {
            this.off = off;
            this.lim = lim;
            this.len = lim - off;
        }

        public ByteBuffer get() {
            return buf.limit(lim).position(off).realBuffer();
        }
    }
}
