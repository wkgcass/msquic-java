package io.vproxy.msquic.sample;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class Utils {
    private Utils() {
    }

    public static void hexDump(MemorySegment seg) {
        hexDump(seg, 16);
    }

    public static void hexDump(MemorySegment seg, int bytesPerLine) {
        var sb = new StringBuilder();
        for (int i = 0, size = (int) seg.byteSize(); i < size; i += bytesPerLine) {
            sb.delete(0, sb.length());
            int j = 0;
            for (; j < bytesPerLine && i + j < size; ++j) {
                int n = seg.get(ValueLayout.JAVA_BYTE, i + j) & 0xff;
                var s = Integer.toString(n, 16);
                if (s.length() == 1) {
                    s = "0" + s;
                }
                System.out.print(s);
                System.out.print(" ");
                if (n >= 33 && n <= 126) {
                    sb.append((char) n);
                } else {
                    sb.append(".");
                }
            }
            System.out.print(" ".repeat(7));
            if (bytesPerLine > j) {
                System.out.print(" ".repeat((bytesPerLine - j) * 3));
            }
            System.out.println(sb);
        }
    }
}
