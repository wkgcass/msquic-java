package msquic.internal;

import java.util.Collection;

public class Utils {
    private Utils() {
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
