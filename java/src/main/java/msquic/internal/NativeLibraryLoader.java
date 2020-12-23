package msquic.internal;

public class NativeLibraryLoader {
    private NativeLibraryLoader() {
    }

    static {
        System.loadLibrary("msquic");
        System.loadLibrary("msquicjava");
    }
}
