#include "io_vproxy_msquic_CXPLAT_THREAD_CONFIG.h"
#include "io_vproxy_msquic_MsQuic.h"
#include "io_vproxy_msquic_MsQuic.impl.h"
#include "io_vproxy_msquic_MsQuicMod.h"
#include "io_vproxy_msquic_MsQuicMod.impl.h"
#include "io_vproxy_msquic_MsQuicModUpcall.h"
#include "io_vproxy_msquic_MsQuicUpcall.h"
#include "io_vproxy_msquic_MsQuicValues.h"
#include "io_vproxy_msquic_MsQuicValues.impl.h"
#include "io_vproxy_msquic_QuicAddr.h"
#include "io_vproxy_msquic_QuicAddr.impl.h"
#include "io_vproxy_msquic_QuicApiTable.h"
#include "io_vproxy_msquic_QuicApiTable.impl.h"
#include "io_vproxy_msquic_QuicBuffer.h"
#include "io_vproxy_msquic_QuicCertificate.h"
#include "io_vproxy_msquic_QuicCertificateFile.h"
#include "io_vproxy_msquic_QuicConfiguration.h"
#include "io_vproxy_msquic_QuicConfiguration.impl.h"
#include "io_vproxy_msquic_QuicConnection.h"
#include "io_vproxy_msquic_QuicConnection.impl.h"
#include "io_vproxy_msquic_QuicConnectionEvent.h"
#include "io_vproxy_msquic_QuicConnectionEventConnected.h"
#include "io_vproxy_msquic_QuicConnectionEventConnectionShutdownComplete.h"
#include "io_vproxy_msquic_QuicConnectionEventDatagramReceived.h"
#include "io_vproxy_msquic_QuicConnectionEventDatagramSendStateChanged.h"
#include "io_vproxy_msquic_QuicConnectionEventDatagramStateChanged.h"
#include "io_vproxy_msquic_QuicConnectionEventIdealProcessorChanged.h"
#include "io_vproxy_msquic_QuicConnectionEventLocalAddressChanged.h"
#include "io_vproxy_msquic_QuicConnectionEventPeerAddressChanged.h"
#include "io_vproxy_msquic_QuicConnectionEventPeerCertificateReceived.h"
#include "io_vproxy_msquic_QuicConnectionEventPeerNeedsStreams.h"
#include "io_vproxy_msquic_QuicConnectionEventPeerStreamStarted.h"
#include "io_vproxy_msquic_QuicConnectionEventReliableResetNegotiated.h"
#include "io_vproxy_msquic_QuicConnectionEventResumed.h"
#include "io_vproxy_msquic_QuicConnectionEventResumptionTicketReceived.h"
#include "io_vproxy_msquic_QuicConnectionEventShutdownInitiatedByPeer.h"
#include "io_vproxy_msquic_QuicConnectionEventShutdownInitiatedByTransport.h"
#include "io_vproxy_msquic_QuicConnectionEventStreamsAvailable.h"
#include "io_vproxy_msquic_QuicConnectionEventUnion.h"
#include "io_vproxy_msquic_QuicCredentialConfig.h"
#include "io_vproxy_msquic_QuicListener.h"
#include "io_vproxy_msquic_QuicListener.impl.h"
#include "io_vproxy_msquic_QuicListenerEvent.h"
#include "io_vproxy_msquic_QuicListenerEventNewConnection.h"
#include "io_vproxy_msquic_QuicListenerEventStopComplete.h"
#include "io_vproxy_msquic_QuicListenerEventUnion.h"
#include "io_vproxy_msquic_QuicNewConnectionInfo.h"
#include "io_vproxy_msquic_QuicObjectBase.h"
#include "io_vproxy_msquic_QuicObjectBase.impl.h"
#include "io_vproxy_msquic_QuicRegistration.h"
#include "io_vproxy_msquic_QuicRegistration.impl.h"
#include "io_vproxy_msquic_QuicRegistrationConfig.h"
#include "io_vproxy_msquic_QuicSettings.h"
#include "io_vproxy_msquic_QuicSettingsIsSet.h"
#include "io_vproxy_msquic_QuicStream.h"
#include "io_vproxy_msquic_QuicStream.impl.h"
#include "io_vproxy_msquic_QuicStreamEvent.h"
#include "io_vproxy_msquic_QuicStreamEventIdealSendBufferSize.h"
#include "io_vproxy_msquic_QuicStreamEventPeerReceiveAborted.h"
#include "io_vproxy_msquic_QuicStreamEventPeerSendAborted.h"
#include "io_vproxy_msquic_QuicStreamEventReceive.h"
#include "io_vproxy_msquic_QuicStreamEventSendComplete.h"
#include "io_vproxy_msquic_QuicStreamEventSendShutdownComplete.h"
#include "io_vproxy_msquic_QuicStreamEventShutdownComplete.h"
#include "io_vproxy_msquic_QuicStreamEventStartComplete.h"
#include "io_vproxy_msquic_QuicStreamEventUnion.h"
#include "io_vproxy_msquic_QuicTLSSecret.h"
#include "io_vproxy_msquic_QuicTLSSecretIsSet.h"
#include "jni.h"
#include "pni.h"

JNIEXPORT char * JNICALL JavaCritical_io_vproxy_msquic_MsQuicValues_QuicStatusString(int32_t status) {
    switch (status) {
        case QUIC_STATUS_SUCCESS: return "QUIC_STATUS_SUCCESS";
        case QUIC_STATUS_PENDING: return "QUIC_STATUS_PENDING";
        case QUIC_STATUS_CONTINUE: return "QUIC_STATUS_CONTINUE";
        case QUIC_STATUS_OUT_OF_MEMORY: return "QUIC_STATUS_OUT_OF_MEMORY";
        case QUIC_STATUS_INVALID_PARAMETER: return "QUIC_STATUS_INVALID_PARAMETER";
        case QUIC_STATUS_INVALID_STATE: return "QUIC_STATUS_INVALID_STATE";
        case QUIC_STATUS_NOT_SUPPORTED: return "QUIC_STATUS_NOT_SUPPORTED";
        case QUIC_STATUS_NOT_FOUND: return "QUIC_STATUS_NOT_FOUND";
        case QUIC_STATUS_BUFFER_TOO_SMALL: return "QUIC_STATUS_BUFFER_TOO_SMALL";
        case QUIC_STATUS_HANDSHAKE_FAILURE: return "QUIC_STATUS_HANDSHAKE_FAILURE";
        case QUIC_STATUS_ABORTED: return "QUIC_STATUS_ABORTED";
        case QUIC_STATUS_ADDRESS_IN_USE: return "QUIC_STATUS_ADDRESS_IN_USE";
        case QUIC_STATUS_INVALID_ADDRESS: return "QUIC_STATUS_INVALID_ADDRESS";
        case QUIC_STATUS_CONNECTION_TIMEOUT: return "QUIC_STATUS_CONNECTION_TIMEOUT";
        case QUIC_STATUS_CONNECTION_IDLE: return "QUIC_STATUS_CONNECTION_IDLE";
        case QUIC_STATUS_INTERNAL_ERROR: return "QUIC_STATUS_INTERNAL_ERROR";
        case QUIC_STATUS_CONNECTION_REFUSED: return "QUIC_STATUS_CONNECTION_REFUSED";
        case QUIC_STATUS_PROTOCOL_ERROR: return "QUIC_STATUS_PROTOCOL_ERROR";
        case QUIC_STATUS_VER_NEG_ERROR: return "QUIC_STATUS_VER_NEG_ERROR";
        case QUIC_STATUS_UNREACHABLE: return "QUIC_STATUS_UNREACHABLE";
        case QUIC_STATUS_TLS_ERROR: return "QUIC_STATUS_TLS_ERROR";
        case QUIC_STATUS_USER_CANCELED: return "QUIC_STATUS_USER_CANCELED";
        case QUIC_STATUS_ALPN_NEG_FAILURE: return "QUIC_STATUS_ALPN_NEG_FAILURE";
        case QUIC_STATUS_STREAM_LIMIT_REACHED: return "QUIC_STATUS_STREAM_LIMIT_REACHED";
        case QUIC_STATUS_ALPN_IN_USE: return "QUIC_STATUS_ALPN_IN_USE";
        case QUIC_STATUS_ADDRESS_NOT_AVAILABLE: return "QUIC_STATUS_ADDRESS_NOT_AVAILABLE";
        case QUIC_STATUS_CLOSE_NOTIFY: return "QUIC_STATUS_CLOSE_NOTIFY";
        case QUIC_STATUS_BAD_CERTIFICATE: return "QUIC_STATUS_BAD_CERTIFICATE";
        case QUIC_STATUS_UNSUPPORTED_CERTIFICATE: return "QUIC_STATUS_UNSUPPORTED_CERTIFICATE";
        case QUIC_STATUS_REVOKED_CERTIFICATE: return "QUIC_STATUS_REVOKED_CERTIFICATE";
        case QUIC_STATUS_EXPIRED_CERTIFICATE: return "QUIC_STATUS_EXPIRED_CERTIFICATE";
        case QUIC_STATUS_UNKNOWN_CERTIFICATE: return "QUIC_STATUS_UNKNOWN_CERTIFICATE";
        case QUIC_STATUS_REQUIRED_CERTIFICATE: return "QUIC_STATUS_REQUIRED_CERTIFICATE";
        case QUIC_STATUS_CERT_EXPIRED: return "QUIC_STATUS_CERT_EXPIRED";
        case QUIC_STATUS_CERT_UNTRUSTED_ROOT: return "QUIC_STATUS_CERT_UNTRUSTED_ROOT";
        case QUIC_STATUS_CERT_NO_CERT: return "QUIC_STATUS_CERT_NO_CERT";
        default: return "UNKNOWN";
    }
}
