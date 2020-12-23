package msquic;

import msquic.nativevalues.ListenerEventType;

public class ListenerEvent {
    public final ListenerEventType type;
    public final Connection newConnection;

    public ListenerEvent(ListenerEventType type, Connection newConnection) {
        this.type = type;
        this.newConnection = newConnection;
    }
}
