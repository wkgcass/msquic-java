package msquic;

import msquic.nativevalues.ConnectionEventType;

public class ConnectionEvent {
    public final ConnectionEventType type;
    public final Stream peerStreamStarted;

    public ConnectionEvent(ConnectionEventType type, Stream peerStreamStarted) {
        this.type = type;
        this.peerStreamStarted = peerStreamStarted;
    }
}
