package msquic;

import msquic.internal.Native;

import java.util.Objects;

public class MsQuic {
    private static boolean initiated = false;
    private static MemoryAllocator<?> allocator = null;
    public final long msquic;
    private boolean isOpen = true;

    private MsQuic(long msquic) {
        this.msquic = msquic;
    }

    public static void setMemoryAllocator(MemoryAllocator<?> allocatorLocal) {
        Objects.requireNonNull(allocatorLocal);
        if (initiated) {
            throw new IllegalStateException("MsQuic is already initiated");
        }
        if (allocator == null) {
            synchronized (MsQuic.class) {
                if (allocator == null) {
                    allocator = allocatorLocal;
                    return;
                }
            }
        }
        throw new IllegalStateException("memory allocator already set: " + allocator);
    }

    public static void UNSAFE_release() {
        if (!initiated) { // no need to release if it's not initiated
            return;
        }
        synchronized (MsQuic.class) {
            Native.get().MsQuicJavaRelease();
            allocator = null;
            initiated = false;
        }
    }

    public static MsQuic open() throws MsQuicException {
        if (!initiated) {
            synchronized (MsQuic.class) {
                if (!initiated) {
                    initiated = true;
                    Native.get().MsQuicJavaInit(allocator);
                }
            }
        }
        long msquic = Native.get().MsQuicOpen();
        return new MsQuic(msquic);
    }

    public void close() {
        if (!isOpen) {
            return;
        }
        isOpen = false;
        Native.get().MsQuicClose(msquic);
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

    public Registration openRegistration(RegistrationConfig config) throws MsQuicException {
        long reg = Native.get().RegistrationOpen(msquic, config.appName, config.profile.intValue);
        return new Registration(this, reg);
    }

    @Override
    public String toString() {
        return "MsQuic(0x" + Long.toHexString(msquic) + "/" + (isOpen ? "open" : "closed") + ')';
    }
}
