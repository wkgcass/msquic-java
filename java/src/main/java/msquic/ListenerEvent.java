package msquic;

import msquic.nativevalues.ListenerEventType;

public class ListenerEvent {
    public final ListenerEventType type;
    public final NEW_CONNECTION_DATA NEW_CONNECTION;

    public ListenerEvent(ListenerEventType type,
                         //
                         Connection NEW_CONNECTION_connection,
                         String NEW_CONNECTION_negotiatedAlpn,
                         String NEW_CONNECTION_serverName
                         //
    ) {
        this.type = type;
        if (type == ListenerEventType.NEW_CONNECTION) {
            this.NEW_CONNECTION = new NEW_CONNECTION_DATA(
                NEW_CONNECTION_connection,
                new NEW_CONNECTION_DATA.NewConnectionInfo(
                    NEW_CONNECTION_negotiatedAlpn,
                    NEW_CONNECTION_serverName
                ));
        } else {
            this.NEW_CONNECTION = null;
        }
    }

    public static class NEW_CONNECTION_DATA {
        public final Connection connection;
        public final NewConnectionInfo info;

        public NEW_CONNECTION_DATA(Connection connection, NewConnectionInfo info) {
            this.connection = connection;
            this.info = info;
        }

        public static class NewConnectionInfo {
            public final String negotiatedAlpn;
            public final String serverName;

            public NewConnectionInfo(String negotiatedAlpn,
                                     String serverName) {
                this.negotiatedAlpn = negotiatedAlpn;
                this.serverName = serverName;
            }

            @Override
            public String toString() {
                return "NewConnectionInfo{" +
                    "negotiatedAlpn=" + negotiatedAlpn +
                    ", serverName='" + serverName + '\'' +
                    '}';
            }
        }
    }
}
