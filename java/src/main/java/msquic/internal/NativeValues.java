package msquic.internal;

public class NativeValues {
    private static NativeValues instance;

    private NativeValues() {
    }

    static {
        try {
            Class.forName(NativeLibraryLoader.class.getName());
        } catch (ClassNotFoundException ignore) {
        }
    }

    public static NativeValues get() {
        if (instance == null) {
            synchronized (NativeValues.class) {
                if (instance == null) {
                    instance = new NativeValues();
                }
            }
        }
        return instance;
    }

    public native int QUIC_ADDRESS_FAMILY_UNSPEC();

    public native int QUIC_ADDRESS_FAMILY_INET();

    public native int QUIC_ADDRESS_FAMILY_INET6();

    public native int QUIC_CONNECTION_EVENT_CONNECTED();

    public native int QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT();

    public native int QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER();

    public native int QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE();

    public native int QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED();

    public native int QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED();

    public native int QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED();

    public native int QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE();

    public native int QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS();

    public native int QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED();

    public native int QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED();

    public native int QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED();

    public native int QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED();

    public native int QUIC_CONNECTION_EVENT_RESUMED();

    public native int QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED();

    public native int QUIC_EXECUTION_PROFILE_LOW_LATENCY();

    public native int QUIC_EXECUTION_PROFILE_TYPE_MAX_THROUGHPUT();

    public native int QUIC_EXECUTION_PROFILE_TYPE_SCAVENGER();

    public native int QUIC_EXECUTION_PROFILE_TYPE_REAL_TIME();

    public native int QUIC_LISTENER_EVENT_NEW_CONNECTION();

    public native int QUIC_SEND_FLAG_NONE();

    public native int QUIC_SEND_FLAG_ALLOW_0_RTT();

    public native int QUIC_SEND_FLAG_START();

    public native int QUIC_SEND_FLAG_FIN();

    public native int QUIC_SEND_FLAG_DGRAM_PRIORITY();

    public native int QUIC_SEND_RESUMPTION_FLAG_NONE();

    public native int QUIC_SEND_RESUMPTION_FLAG_FINAL();

    public native int QUIC_SERVER_NO_RESUME();

    public native int QUIC_SERVER_RESUME_ONLY();

    public native int QUIC_SERVER_RESUME_AND_ZERORTT();

    public native int QUIC_STREAM_EVENT_START_COMPLETE();

    public native int QUIC_STREAM_EVENT_RECEIVE();

    public native int QUIC_STREAM_EVENT_SEND_COMPLETE();

    public native int QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN();

    public native int QUIC_STREAM_EVENT_PEER_SEND_ABORTED();

    public native int QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED();

    public native int QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE();

    public native int QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE();

    public native int QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE();

    public native int QUIC_STREAM_SHUTDOWN_FLAG_NONE();

    public native int QUIC_STREAM_SHUTDOWN_FLAG_GRACEFUL();

    public native int QUIC_STREAM_SHUTDOWN_FLAG_ABORT_SEND();

    public native int QUIC_STREAM_SHUTDOWN_FLAG_ABORT_RECEIVE();

    public native int QUIC_STREAM_SHUTDOWN_FLAG_ABORT();

    public native int QUIC_STREAM_SHUTDOWN_FLAG_IMMEDIATE();

    public native int QUIC_STATUS_ABORTED();

    public native int QUIC_STATUS_ADDRESS_IN_USE();

    public native int QUIC_STATUS_ALPN_NEG_FAILURE();

    public native int QUIC_STATUS_BUFFER_TOO_SMALL();

    public native int QUIC_STATUS_CONNECTION_IDLE();

    public native int QUIC_STATUS_CONNECTION_REFUSED();

    public native int QUIC_STATUS_CONNECTION_TIMEOUT();

    public native int QUIC_STATUS_CONTINUE();

    public native int QUIC_STATUS_DNS_RESOLUTION_ERROR();

    public native int QUIC_STATUS_EPOLL_ERROR();

    public native int QUIC_STATUS_HANDSHAKE_FAILURE();

    public native int QUIC_STATUS_INTERNAL_ERROR();

    public native int QUIC_STATUS_INVALID_PARAMETER();

    public native int QUIC_STATUS_INVALID_STATE();

    public native int QUIC_STATUS_NOT_FOUND();

    public native int QUIC_STATUS_NOT_SUPPORTED();

    public native int QUIC_STATUS_OUT_OF_MEMORY();

    public native int QUIC_STATUS_PENDING();

    public native int QUIC_STATUS_PERMISSION_DENIED();

    public native int QUIC_STATUS_PROTOCOL_ERROR();

    public native int QUIC_STATUS_SOCKET_ERROR();

    public native int QUIC_STATUS_SUCCESS();

    public native int QUIC_STATUS_TLS_ERROR();

    public native int QUIC_STATUS_UNREACHABLE();

    public native int QUIC_STATUS_USER_CANCELED();

    public native int QUIC_STATUS_VER_NEG_ERROR();

    public native int QUIC_STREAM_OPEN_FLAG_NONE();

    public native int QUIC_STREAM_OPEN_FLAG_UNIDIRECTIONAL();

    public native int QUIC_STREAM_OPEN_FLAG_0_RTT();

    public native int QUIC_STREAM_START_FLAG_NONE();

    public native int QUIC_STREAM_START_FLAG_FAIL_BLOCKED();

    public native int QUIC_STREAM_START_FLAG_IMMEDIATE();

    public native int QUIC_STREAM_START_FLAG_ASYNC();
}
