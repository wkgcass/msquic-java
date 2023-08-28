package io.vproxy.msquic;

public class MsQuicConsts {
    private MsQuicConsts() {
    }

    public static final int sizeofQuicAddr = MsQuicValues.get().sizeofQuicAddr();

    public static final int QUIC_API_VERSION_2 = 2;

    public static final int QUIC_STATUS_NOT_SUPPORTED = MsQuicValues.get().QUIC_STATUS_NOT_SUPPORTED();

    public static final int QUIC_EXECUTION_PROFILE_LOW_LATENCY = 0;
    public static final int QUIC_EXECUTION_PROFILE_TYPE_MAX_THROUGHPUT = 1;
    public static final int QUIC_EXECUTION_PROFILE_TYPE_SCAVENGER = 2;
    public static final int QUIC_EXECUTION_PROFILE_TYPE_REAL_TIME = 3;

    public static final int QUIC_CREDENTIAL_TYPE_NONE = 0;
    public static final int QUIC_CREDENTIAL_TYPE_CERTIFICATE_HASH = 1;
    public static final int QUIC_CREDENTIAL_TYPE_CERTIFICATE_HASH_STORE = 2;
    public static final int QUIC_CREDENTIAL_TYPE_CERTIFICATE_CONTEXT = 3;
    public static final int QUIC_CREDENTIAL_TYPE_CERTIFICATE_FILE = 4;
    public static final int QUIC_CREDENTIAL_TYPE_CERTIFICATE_FILE_PROTECTED = 5;
    public static final int QUIC_CREDENTIAL_TYPE_CERTIFICATE_PKCS12 = 6;

    public static final int QUIC_CREDENTIAL_FLAG_NONE = 0;
    public static final int QUIC_CREDENTIAL_FLAG_CLIENT = 0x00000001;
    public static final int QUIC_CREDENTIAL_FLAG_LOAD_ASYNCHRONOUS = 0x00000002;
    public static final int QUIC_CREDENTIAL_FLAG_NO_CERTIFICATE_VALIDATION = 0x00000004;
    public static final int QUIC_CREDENTIAL_FLAG_ENABLE_OCSP = 0x00000008;
    public static final int QUIC_CREDENTIAL_FLAG_INDICATE_CERTIFICATE_RECEIVED = 0x00000010;
    public static final int QUIC_CREDENTIAL_FLAG_DEFER_CERTIFICATE_VALIDATION = 0x00000020;
    public static final int QUIC_CREDENTIAL_FLAG_REQUIRE_CLIENT_AUTHENTICATION = 0x00000040;
    public static final int QUIC_CREDENTIAL_FLAG_USE_TLS_BUILTIN_CERTIFICATE_VALIDATION = 0x00000080;
    public static final int QUIC_CREDENTIAL_FLAG_REVOCATION_CHECK_END_CERT = 0x00000100;
    public static final int QUIC_CREDENTIAL_FLAG_REVOCATION_CHECK_CHAIN = 0x00000200;
    public static final int QUIC_CREDENTIAL_FLAG_REVOCATION_CHECK_CHAIN_EXCLUDE_ROOT = 0x00000400;
    public static final int QUIC_CREDENTIAL_FLAG_IGNORE_NO_REVOCATION_CHECK = 0x00000800;
    public static final int QUIC_CREDENTIAL_FLAG_IGNORE_REVOCATION_OFFLINE = 0x00001000;
    public static final int QUIC_CREDENTIAL_FLAG_SET_ALLOWED_CIPHER_SUITES = 0x00002000;
    public static final int QUIC_CREDENTIAL_FLAG_USE_PORTABLE_CERTIFICATES = 0x00004000;
    public static final int QUIC_CREDENTIAL_FLAG_USE_SUPPLIED_CREDENTIALS = 0x00008000;
    public static final int QUIC_CREDENTIAL_FLAG_USE_SYSTEM_MAPPER = 0x00010000;
    public static final int QUIC_CREDENTIAL_FLAG_CACHE_ONLY_URL_RETRIEVAL = 0x00020000;
    public static final int QUIC_CREDENTIAL_FLAG_REVOCATION_CHECK_CACHE_ONLY = 0x00040000;
    public static final int QUIC_CREDENTIAL_FLAG_INPROC_PEER_CERTIFICATE = 0x00080000;
    public static final int QUIC_CREDENTIAL_FLAG_SET_CA_CERTIFICATE_FILE = 0x00100000;

    public static final int QUIC_ALLOWED_CIPHER_SUITE_NONE = 0;
    public static final int QUIC_ALLOWED_CIPHER_SUITE_AES_128_GCM_SHA256 = 0x1;
    public static final int QUIC_ALLOWED_CIPHER_SUITE_AES_256_GCM_SHA384 = 0x2;
    public static final int QUIC_ALLOWED_CIPHER_SUITE_CHACHA20_POLY1305_SHA256 = 0x4;

    public static final int QUIC_LISTENER_EVENT_NEW_CONNECTION = 0;
    public static final int QUIC_LISTENER_EVENT_STOP_COMPLETE = 1;

    public static final int QUIC_CONNECTION_EVENT_CONNECTED = 0;
    public static final int QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT = 1;
    public static final int QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER = 2;
    public static final int QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE = 3;
    public static final int QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED = 4;
    public static final int QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED = 5;
    public static final int QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED = 6;
    public static final int QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE = 7;
    public static final int QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS = 8;
    public static final int QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED = 9;
    public static final int QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED = 10;
    public static final int QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED = 11;
    public static final int QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED = 12;
    public static final int QUIC_CONNECTION_EVENT_RESUMED = 13;
    public static final int QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED = 14;
    public static final int QUIC_CONNECTION_EVENT_PEER_CERTIFICATE_RECEIVED = 15;
    public static final int QUIC_CONNECTION_EVENT_RELIABLE_RESET_NEGOTIATED = 16;

    public static final int QUIC_STREAM_OPEN_FLAG_NONE = 0;
    public static final int QUIC_STREAM_OPEN_FLAG_UNIDIRECTIONAL = 0x0001;
    public static final int QUIC_STREAM_OPEN_FLAG_0_RTT = 0x0002;
    public static final int QUIC_STREAM_OPEN_FLAG_DELAY_ID_FC_UPDATES = 0x0004;

    public static final int QUIC_RECEIVE_FLAG_NONE = 0;
    public static final int QUIC_RECEIVE_FLAG_0_RTT = 0x0001;
    public static final int QUIC_RECEIVE_FLAG_FIN = 0x0002;

    public static final int QUIC_DATAGRAM_SEND_UNKNOWN = 0;
    public static final int QUIC_DATAGRAM_SEND_SENT = 1;
    public static final int QUIC_DATAGRAM_SEND_LOST_SUSPECT = 2;
    public static final int QUIC_DATAGRAM_SEND_LOST_DISCARDED = 3;
    public static final int QUIC_DATAGRAM_SEND_ACKNOWLEDGED = 4;
    public static final int QUIC_DATAGRAM_SEND_ACKNOWLEDGED_SPURIOUS = 5;
    public static final int QUIC_DATAGRAM_SEND_CANCELED = 6;

    public static final int QUIC_CONNECTION_SHUTDOWN_FLAG_NONE = 0;
    public static final int QUIC_CONNECTION_SHUTDOWN_FLAG_SILENT = 0x0001;

    public static final int QUIC_ADDRESS_FAMILY_UNSPEC = MsQuicValues.get().QUIC_ADDRESS_FAMILY_UNSPEC();
    public static final int QUIC_ADDRESS_FAMILY_INET = MsQuicValues.get().QUIC_ADDRESS_FAMILY_INET();
    public static final int QUIC_ADDRESS_FAMILY_INET6 = MsQuicValues.get().QUIC_ADDRESS_FAMILY_INET6();

    public static final int QUIC_SEND_RESUMPTION_FLAG_NONE = 0;
    public static final int QUIC_SEND_RESUMPTION_FLAG_FINAL = 0x0001;

    public static final int QUIC_STREAM_EVENT_START_COMPLETE = 0;
    public static final int QUIC_STREAM_EVENT_RECEIVE = 1;
    public static final int QUIC_STREAM_EVENT_SEND_COMPLETE = 2;
    public static final int QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN = 3;
    public static final int QUIC_STREAM_EVENT_PEER_SEND_ABORTED = 4;
    public static final int QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED = 5;
    public static final int QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE = 6;
    public static final int QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE = 7;
    public static final int QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE = 8;
    public static final int QUIC_STREAM_EVENT_PEER_ACCEPTED = 9;

    public static final int QUIC_STREAM_START_FLAG_NONE = 0;
    public static final int QUIC_STREAM_START_FLAG_IMMEDIATE = 0x0001;
    public static final int QUIC_STREAM_START_FLAG_FAIL_BLOCKED = 0x0002;
    public static final int QUIC_STREAM_START_FLAG_SHUTDOWN_ON_FAIL = 0x0004;
    public static final int QUIC_STREAM_START_FLAG_INDICATE_PEER_ACCEPT = 0x0008;

    public static final int QUIC_STREAM_SHUTDOWN_FLAG_NONE = 0;
    public static final int QUIC_STREAM_SHUTDOWN_FLAG_GRACEFUL = 0x0001;
    public static final int QUIC_STREAM_SHUTDOWN_FLAG_ABORT_SEND = 0x0002;
    public static final int QUIC_STREAM_SHUTDOWN_FLAG_ABORT_RECEIVE = 0x0004;
    public static final int QUIC_STREAM_SHUTDOWN_FLAG_ABORT = 0x0006;
    public static final int QUIC_STREAM_SHUTDOWN_FLAG_IMMEDIATE = 0x0008;
    public static final int QUIC_STREAM_SHUTDOWN_FLAG_INLINE = 0x0010;

    public static final int QUIC_SEND_FLAG_NONE = 0;
    public static final int QUIC_SEND_FLAG_ALLOW_0_RTT = 0x0001;
    public static final int QUIC_SEND_FLAG_START = 0x0002;
    public static final int QUIC_SEND_FLAG_FIN = 0x0004;
    public static final int QUIC_SEND_FLAG_DGRAM_PRIORITY = 0x0008;
    public static final int QUIC_SEND_FLAG_DELAY_SEND = 0x0010;

    public static final int QUIC_TLS_ALERT_CODE_SUCCESS = 0xFFFF;
    public static final int QUIC_TLS_ALERT_CODE_UNEXPECTED_MESSAGE = 10;
    public static final int QUIC_TLS_ALERT_CODE_BAD_CERTIFICATE = 42;
    public static final int QUIC_TLS_ALERT_CODE_UNSUPPORTED_CERTIFICATE = 43;
    public static final int QUIC_TLS_ALERT_CODE_CERTIFICATE_REVOKED = 44;
    public static final int QUIC_TLS_ALERT_CODE_CERTIFICATE_EXPIRED = 45;
    public static final int QUIC_TLS_ALERT_CODE_CERTIFICATE_UNKNOWN = 46;
    public static final int QUIC_TLS_ALERT_CODE_ILLEGAL_PARAMETER = 47;
    public static final int QUIC_TLS_ALERT_CODE_UNKNOWN_CA = 48;
    public static final int QUIC_TLS_ALERT_CODE_ACCESS_DENIED = 49;
    public static final int QUIC_TLS_ALERT_CODE_INSUFFICIENT_SECURITY = 71;
    public static final int QUIC_TLS_ALERT_CODE_INTERNAL_ERROR = 80;
    public static final int QUIC_TLS_ALERT_CODE_USER_CANCELED = 90;
    public static final int QUIC_TLS_ALERT_CODE_CERTIFICATE_REQUIRED = 116;
    public static final int QUIC_TLS_ALERT_CODE_MAX = 255;

    public static final int QUIC_CONGESTION_CONTROL_ALGORITHM_CUBIC = 0;
    public static final int QUIC_CONGESTION_CONTROL_ALGORITHM_BBR = 1;
    public static final int QUIC_CONGESTION_CONTROL_ALGORITHM_MAX = 2;

    public static final int QUIC_SERVER_NO_RESUME = 0;
    public static final int QUIC_SERVER_RESUME_ONLY = 1;
    public static final int QUIC_SERVER_RESUME_AND_ZERORTT = 2;

    public static final int QUIC_PARAM_CONN_LOCAL_ADDRESS = 0x05000001;
    public static final int QUIC_PARAM_CONN_REMOTE_ADDRESS = 0x05000002;
    public static final int QUIC_PARAM_CONN_RESUMPTION_TICKET = 0x05000010;
    public static final int QUIC_PARAM_CONN_TLS_SECRETS = 0x05000013;
    public static final int QUIC_PARAM_STREAM_ID = 0x08000000;
}
