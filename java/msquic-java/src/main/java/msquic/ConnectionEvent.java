package msquic;

import msquic.nativevalues.ConnectionEventType;

public class ConnectionEvent {
    public final ConnectionEventType type;
    public final PEER_STREAM_STARTED_DATA PEER_STREAM_STARTED;
    public final SHUTDOWN_COMPLETE_DATA SHUTDOWN_COMPLETE;
    public final LOCAL_ADDRESS_CHANGED_DATA LOCAL_ADDRESS_CHANGED;
    public final PEER_ADDRESS_CHANGED_DATA PEER_ADDRESS_CHANGED;
    public final CONNECTED_DATA CONNECTED;

    public ConnectionEvent(ConnectionEventType type,
                           //
                           Stream PEER_STREAM_STARTED_stream,
                           //
                           boolean SHUTDOWN_COMPLETE_appCloseInProgress,
                           //
                           String LOCAL_ADDRESS_CHANGED_address,
                           //
                           String PEER_ADDRESS_CHANGED_address,
                           //
                           boolean CONNECTED_sessionResumed,
                           String CONNECTED_negotiatedAlpn
                           //
    ) {
        this.type = type;
        if (type == ConnectionEventType.PEER_STREAM_STARTED) {
            PEER_STREAM_STARTED = new PEER_STREAM_STARTED_DATA(PEER_STREAM_STARTED_stream);
        } else {
            PEER_STREAM_STARTED = null;
        }
        if (type == ConnectionEventType.SHUTDOWN_COMPLETE) {
            SHUTDOWN_COMPLETE = new SHUTDOWN_COMPLETE_DATA(SHUTDOWN_COMPLETE_appCloseInProgress);
        } else {
            SHUTDOWN_COMPLETE = null;
        }
        if (type == ConnectionEventType.LOCAL_ADDRESS_CHANGED) {
            LOCAL_ADDRESS_CHANGED = new LOCAL_ADDRESS_CHANGED_DATA(LOCAL_ADDRESS_CHANGED_address);
        } else {
            LOCAL_ADDRESS_CHANGED = null;
        }
        if (type == ConnectionEventType.PEER_ADDRESS_CHANGED) {
            PEER_ADDRESS_CHANGED = new PEER_ADDRESS_CHANGED_DATA(PEER_ADDRESS_CHANGED_address);
        } else {
            PEER_ADDRESS_CHANGED = null;
        }
        if (type == ConnectionEventType.CONNECTED) {
            CONNECTED = new CONNECTED_DATA(CONNECTED_sessionResumed, CONNECTED_negotiatedAlpn);
        } else {
            CONNECTED = null;
        }
    }

    public static class PEER_STREAM_STARTED_DATA {
        public final Stream stream;

        public PEER_STREAM_STARTED_DATA(Stream stream) {
            this.stream = stream;
        }
    }

    public static class SHUTDOWN_COMPLETE_DATA {
        public final boolean appCloseInProgress;

        public SHUTDOWN_COMPLETE_DATA(boolean appCloseInProgress) {
            this.appCloseInProgress = appCloseInProgress;
        }
    }

    public static class LOCAL_ADDRESS_CHANGED_DATA {
        public final String address;

        public LOCAL_ADDRESS_CHANGED_DATA(String address) {
            this.address = address;
        }
    }

    public static class PEER_ADDRESS_CHANGED_DATA {
        public final String address;

        public PEER_ADDRESS_CHANGED_DATA(String address) {
            this.address = address;
        }
    }

    public static class CONNECTED_DATA {
        public final boolean sessionResumed;
        public final String negotiatedAlpn;

        public CONNECTED_DATA(boolean sessionResumed, String negotiatedAlpn) {
            this.sessionResumed = sessionResumed;
            this.negotiatedAlpn = negotiatedAlpn;
        }
    }
}
