package msquic.internal;

import java.nio.ByteBuffer;
import java.util.Collection;

public class Utils {
    private Utils() {
    }

    public static final ByteBuffer EMPTY_BUFFER;

    static {
        EMPTY_BUFFER = ByteBuffer.allocateDirect(1);
        EMPTY_BUFFER.position(0).limit(0);
    }

    public static String[] collectionToArrayList(Collection<String> alpn) {
        int count = alpn.size();
        String[] arr = new String[count];
        int idx = 0;
        for (String s : alpn) {
            arr[idx++] = s;
        }
        return arr;
    }
}
