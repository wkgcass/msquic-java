package msquic;

@FunctionalInterface
public interface ListenerCallback {
    void callback(ListenerEvent event) throws MsQuicException;
}
