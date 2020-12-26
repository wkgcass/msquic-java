package msquic;

import msquic.internal.UsedByJNI;

import java.nio.ByteBuffer;

public interface MemoryAllocator<T> {
    @SuppressWarnings("unused")
    @UsedByJNI
    T allocate(int size);

    @SuppressWarnings("unused")
    @UsedByJNI
    ByteBuffer getMemory(T mem);

    @SuppressWarnings("unused")
    @UsedByJNI
    void release(T mem);
}
