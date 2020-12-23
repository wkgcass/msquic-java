package msquic;

public interface StreamCallback {
    void callback(StreamEvent event) throws MsQuicException;
}
