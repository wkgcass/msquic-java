#include <msquic.h>
#include "msquic_internal_NativeValues.h"

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1ADDRESS_1FAMILY_1UNSPEC
  (JNIEnv* env, jobject self) {
    return QUIC_ADDRESS_FAMILY_UNSPEC;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1ADDRESS_1FAMILY_1INET
  (JNIEnv* env, jobject self) {
    return QUIC_ADDRESS_FAMILY_INET;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1ADDRESS_1FAMILY_1INET6
  (JNIEnv* env, jobject self) {
    return QUIC_ADDRESS_FAMILY_INET6;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1CONNECTED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_CONNECTED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1SHUTDOWN_1INITIATED_1BY_1TRANSPORT
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1SHUTDOWN_1INITIATED_1BY_1PEER
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1SHUTDOWN_1COMPLETE
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1LOCAL_1ADDRESS_1CHANGED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1PEER_1ADDRESS_1CHANGED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1PEER_1STREAM_1STARTED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1STREAMS_1AVAILABLE
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1PEER_1NEEDS_1STREAMS
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1IDEAL_1PROCESSOR_1CHANGED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1DATAGRAM_1STATE_1CHANGED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1DATAGRAM_1RECEIVED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1DATAGRAM_1SEND_1STATE_1CHANGED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1RESUMED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_RESUMED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1EVENT_1RESUMPTION_1TICKET_1RECEIVED
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1EXECUTION_1PROFILE_1LOW_1LATENCY
  (JNIEnv* env, jobject self) {
    return QUIC_EXECUTION_PROFILE_LOW_LATENCY;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1EXECUTION_1PROFILE_1TYPE_1MAX_1THROUGHPUT
  (JNIEnv* env, jobject self) {
    return QUIC_EXECUTION_PROFILE_TYPE_MAX_THROUGHPUT;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1EXECUTION_1PROFILE_1TYPE_1SCAVENGER
  (JNIEnv* env, jobject self) {
    return QUIC_EXECUTION_PROFILE_TYPE_SCAVENGER;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1EXECUTION_1PROFILE_1TYPE_1REAL_1TIME
  (JNIEnv* env, jobject self) {
    return QUIC_EXECUTION_PROFILE_TYPE_REAL_TIME;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1LISTENER_1EVENT_1NEW_1CONNECTION
  (JNIEnv* env, jobject self) {
    return QUIC_LISTENER_EVENT_NEW_CONNECTION;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SEND_1FLAG_1NONE
  (JNIEnv* env, jobject self) {
    return QUIC_SEND_FLAG_NONE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SEND_1FLAG_1ALLOW_10_1RTT
  (JNIEnv* env, jobject self) {
    return QUIC_SEND_FLAG_ALLOW_0_RTT;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SEND_1FLAG_1START
  (JNIEnv* env, jobject self) {
    return QUIC_SEND_FLAG_START;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SEND_1FLAG_1FIN
  (JNIEnv* env, jobject self) {
    return QUIC_SEND_FLAG_FIN;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SEND_1FLAG_1DGRAM_1PRIORITY
  (JNIEnv* env, jobject self) {
    return QUIC_SEND_FLAG_DGRAM_PRIORITY;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SEND_1RESUMPTION_1FLAG_1NONE
  (JNIEnv* env, jobject self) {
    return QUIC_SEND_RESUMPTION_FLAG_NONE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SEND_1RESUMPTION_1FLAG_1FINAL
  (JNIEnv* env, jobject self) {
    return QUIC_SEND_RESUMPTION_FLAG_FINAL;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SERVER_1NO_1RESUME
  (JNIEnv* env, jobject self) {
    return QUIC_SERVER_NO_RESUME;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SERVER_1RESUME_1ONLY
  (JNIEnv* env, jobject self) {
    return QUIC_SERVER_RESUME_ONLY;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1SERVER_1RESUME_1AND_1ZERORTT
  (JNIEnv* env, jobject self) {
    return QUIC_SERVER_RESUME_AND_ZERORTT;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1EVENT_1START_1COMPLETE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_EVENT_START_COMPLETE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1EVENT_1RECEIVE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_EVENT_RECEIVE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1EVENT_1SEND_1COMPLETE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_EVENT_SEND_COMPLETE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1EVENT_1PEER_1SEND_1SHUTDOWN
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_EVENT_PEER_SEND_SHUTDOWN;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1EVENT_1PEER_1SEND_1ABORTED
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_EVENT_PEER_SEND_ABORTED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1EVENT_1PEER_1RECEIVE_1ABORTED
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_EVENT_PEER_RECEIVE_ABORTED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1EVENT_1SEND_1SHUTDOWN_1COMPLETE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_EVENT_SEND_SHUTDOWN_COMPLETE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1EVENT_1SHUTDOWN_1COMPLETE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1EVENT_1IDEAL_1SEND_1BUFFER_1SIZE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_EVENT_IDEAL_SEND_BUFFER_SIZE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1SHUTDOWN_1FLAG_1NONE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_SHUTDOWN_FLAG_NONE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1SHUTDOWN_1FLAG_1GRACEFUL
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_SHUTDOWN_FLAG_GRACEFUL;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1SHUTDOWN_1FLAG_1ABORT_1SEND
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_SHUTDOWN_FLAG_ABORT_SEND;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1SHUTDOWN_1FLAG_1ABORT_1RECEIVE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_SHUTDOWN_FLAG_ABORT_RECEIVE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1SHUTDOWN_1FLAG_1ABORT
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_SHUTDOWN_FLAG_ABORT;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1SHUTDOWN_1FLAG_1IMMEDIATE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_SHUTDOWN_FLAG_IMMEDIATE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1ABORTED
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_ABORTED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1ADDRESS_1IN_1USE
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_ADDRESS_IN_USE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1ALPN_1NEG_1FAILURE
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_ALPN_NEG_FAILURE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1BUFFER_1TOO_1SMALL
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_BUFFER_TOO_SMALL;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1CONNECTION_1IDLE
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_CONNECTION_IDLE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1CONNECTION_1REFUSED
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_CONNECTION_REFUSED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1CONNECTION_1TIMEOUT
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_CONNECTION_TIMEOUT;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1CONTINUE
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_CONTINUE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1DNS_1RESOLUTION_1ERROR
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_DNS_RESOLUTION_ERROR;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1EPOLL_1ERROR
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_EPOLL_ERROR;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1HANDSHAKE_1FAILURE
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_HANDSHAKE_FAILURE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1INTERNAL_1ERROR
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_INTERNAL_ERROR;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1INVALID_1PARAMETER
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_INVALID_PARAMETER;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1INVALID_1STATE
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_INVALID_STATE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1NOT_1FOUND
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_NOT_FOUND;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1NOT_1SUPPORTED
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_NOT_SUPPORTED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1OUT_1OF_1MEMORY
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_OUT_OF_MEMORY;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1PENDING
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_PENDING;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1PERMISSION_1DENIED
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_PERMISSION_DENIED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1PROTOCOL_1ERROR
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_PROTOCOL_ERROR;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1SOCKET_1ERROR
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_SOCKET_ERROR;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1SUCCESS
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_SUCCESS;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1TLS_1ERROR
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_TLS_ERROR;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1UNREACHABLE
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_UNREACHABLE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1USER_1CANCELED
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_USER_CANCELED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STATUS_1VER_1NEG_1ERROR
  (JNIEnv* env, jobject self) {
    return QUIC_STATUS_VER_NEG_ERROR;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1OPEN_1FLAG_1NONE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_OPEN_FLAG_NONE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1OPEN_1FLAG_1UNIDIRECTIONAL
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_OPEN_FLAG_UNIDIRECTIONAL;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1OPEN_1FLAG_10_1RTT
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_OPEN_FLAG_0_RTT;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1START_1FLAG_1NONE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_START_FLAG_NONE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1START_1FLAG_1FAIL_1BLOCKED
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_START_FLAG_FAIL_BLOCKED;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1START_1FLAG_1IMMEDIATE
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_START_FLAG_IMMEDIATE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1STREAM_1START_1FLAG_1ASYNC
  (JNIEnv* env, jobject self) {
    return QUIC_STREAM_START_FLAG_ASYNC;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1RECEIVE_1FLAG_1NONE
  (JNIEnv* env, jobject self) {
    return QUIC_RECEIVE_FLAG_NONE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1RECEIVE_1FLAG_10_1RTT
  (JNIEnv* env, jobject self) {
    return QUIC_RECEIVE_FLAG_0_RTT;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1RECEIVE_1FLAG_1FIN
  (JNIEnv* env, jobject self) {
    return QUIC_RECEIVE_FLAG_FIN;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1SHUTDOWN_1FLAG_1NONE
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_SHUTDOWN_FLAG_NONE;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_NativeValues_QUIC_1CONNECTION_1SHUTDOWN_1FLAG_1SILENT
  (JNIEnv* env, jobject self) {
    return QUIC_CONNECTION_SHUTDOWN_FLAG_SILENT;
  }
