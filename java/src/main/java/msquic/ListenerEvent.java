package msquic;

import msquic.nativevalues.ListenerEventType;

public class ListenerEvent {
    public final ListenerEventType type;
    public final NEW_CONNECTION_DATA NEW_CONNECTION;

    public ListenerEvent(ListenerEventType type, Connection newConnection) {
        this.type = type;
        if (type == ListenerEventType.NEW_CONNECTION) {
            this.NEW_CONNECTION = new NEW_CONNECTION_DATA(newConnection);
        } else {
            this.NEW_CONNECTION = null;
        }
    }

    public static class NEW_CONNECTION_DATA {
        public final Connection connection;

        public NEW_CONNECTION_DATA(Connection connection) {
            this.connection = connection;
        }
    }
}
