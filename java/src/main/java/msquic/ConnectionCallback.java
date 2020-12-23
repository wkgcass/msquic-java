package msquic;

@FunctionalInterface
public interface ConnectionCallback {
    void callback(ConnectionEvent event) throws MsQuicException;
}
