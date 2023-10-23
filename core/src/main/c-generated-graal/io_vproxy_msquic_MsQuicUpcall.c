#include "io_vproxy_msquic_MsQuicUpcall.h"
#include <stdio.h>
#include <stdlib.h>

#ifdef __cplusplus
extern "C" {
#endif

static int32_t (*_listenerCallback)(void *,void *,void *,QUIC_LISTENER_EVENT *);
static int32_t (*_connectionCallback)(void *,void *,void *,QUIC_CONNECTION_EVENT *);
static int32_t (*_streamCallback)(void *,void *,void *,QUIC_STREAM_EVENT *);

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_MsQuicUpcall_INIT(
    int32_t (*listenerCallback)(void *,void *,void *,QUIC_LISTENER_EVENT *),
    int32_t (*connectionCallback)(void *,void *,void *,QUIC_CONNECTION_EVENT *),
    int32_t (*streamCallback)(void *,void *,void *,QUIC_STREAM_EVENT *)
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
    return _listenerCallback(GetPNIGraalThread(), Listener, Context, Event);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicUpcall_connectionCallback(void * Connection, void * Context, QUIC_CONNECTION_EVENT * Event) {
    if (_connectionCallback == NULL) {
        printf("JavaCritical_io_vproxy_msquic_MsQuicUpcall_connectionCallback function pointer is null");
        fflush(stdout);
        exit(1);
    }
    return _connectionCallback(GetPNIGraalThread(), Connection, Context, Event);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicUpcall_streamCallback(void * Stream, void * Context, QUIC_STREAM_EVENT * Event) {
    if (_streamCallback == NULL) {
        printf("JavaCritical_io_vproxy_msquic_MsQuicUpcall_streamCallback function pointer is null");
        fflush(stdout);
        exit(1);
    }
    return _streamCallback(GetPNIGraalThread(), Stream, Context, Event);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.16
// sha256:fe7953f5296d855fcf47ccf344ace170eeca291abe025f22aa61cd65f113f4c1
