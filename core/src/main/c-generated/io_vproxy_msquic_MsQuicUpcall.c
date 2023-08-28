#include "io_vproxy_msquic_MsQuicUpcall.h"
#include <stdio.h>
#include <stdlib.h>

#ifdef __cplusplus
extern "C" {
#endif

static int32_t (*_listenerCallback)(void *,void *,QUIC_LISTENER_EVENT *);
static int32_t (*_connectionCallback)(void *,void *,QUIC_CONNECTION_EVENT *);
static int32_t (*_streamCallback)(void *,void *,QUIC_STREAM_EVENT *);

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_MsQuicUpcall_INIT(
    int32_t (*listenerCallback)(void *,void *,QUIC_LISTENER_EVENT *),
    int32_t (*connectionCallback)(void *,void *,QUIC_CONNECTION_EVENT *),
    int32_t (*streamCallback)(void *,void *,QUIC_STREAM_EVENT *)
) {
    _listenerCallback = listenerCallback;
    _connectionCallback = connectionCallback;
    _streamCallback = streamCallback;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicUpcall_listenerCallback(void * Listener, void * Context, QUIC_LISTENER_EVENT * Event) {
    if (_listenerCallback == NULL) {
        printf("JavaCritical_io_vproxy_msquic_MsQuicUpcall_listenerCallback function pointer is null");
        fflush(stdout);
        exit(1);
    }
    return _listenerCallback(Listener, Context, Event);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicUpcall_connectionCallback(void * Connection, void * Context, QUIC_CONNECTION_EVENT * Event) {
    if (_connectionCallback == NULL) {
        printf("JavaCritical_io_vproxy_msquic_MsQuicUpcall_connectionCallback function pointer is null");
        fflush(stdout);
        exit(1);
    }
    return _connectionCallback(Connection, Context, Event);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicUpcall_streamCallback(void * Stream, void * Context, QUIC_STREAM_EVENT * Event) {
    if (_streamCallback == NULL) {
        printf("JavaCritical_io_vproxy_msquic_MsQuicUpcall_streamCallback function pointer is null");
        fflush(stdout);
        exit(1);
    }
    return _streamCallback(Stream, Context, Event);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.8
// sha256:2075b62ca414e429b4a3d7f0fcaecfa8d673ffc7403057f00a924e9259a298b1
