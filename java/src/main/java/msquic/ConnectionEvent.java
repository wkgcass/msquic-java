package msquic;

import msquic.nativevalues.ConnectionEventType;

public class ConnectionEvent {
    public final ConnectionEventType type;
    public final PEER_STREAM_STARTED_DATA PEER_STREAM_STARTED;
    public final SHUTDOWN_COMPLETE_DATA SHUTDOWN_COMPLETE;

    public ConnectionEvent(ConnectionEventType type,
                           Stream PEER_STREAM_STARTED_stream,
                           boolean SHUTDOWN_COMPLETE_appCloseInProgress) {
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
}
