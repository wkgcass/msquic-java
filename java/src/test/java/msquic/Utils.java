package msquic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    private Utils() {
    }

    static final Unsafe U;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            U = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static class DirectByteBufferMemoryAllocator implements MemoryAllocator<ByteBuffer> {
        @Override
        public ByteBuffer allocate(int size) {
            return ByteBuffer.allocateDirect(size);
        }

        @Override
        public ByteBuffer getMemory(ByteBuffer mem) {
            return mem;
        }

        @Override
        public void release(ByteBuffer mem) {
            U.invokeCleaner(mem);
        }
    }

    private static final byte[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".getBytes();

    static String randomStr(int len) {
        byte[] buf = new byte[len];
        for (int i = 0; i < len; ++i) {
            byte b = chars[ThreadLocalRandom.current().nextInt(chars.length)];
            buf[i] = b;
        }
        return new String(buf);
    }
}
