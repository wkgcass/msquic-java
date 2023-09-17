/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicStream */
#ifndef _Included_io_vproxy_msquic_QuicStream
#define _Included_io_vproxy_msquic_QuicStream
#ifdef __cplusplus
extern "C" {
#endif

struct QuicStream;
typedef struct QuicStream QuicStream;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"
#include "io_vproxy_msquic_QuicBuffer.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicStream, QuicStream *)

PNI_PACK(struct, QuicStream, {
    void * Api;
    void * Stream;
});

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicStream_close(QuicStream * self);
JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicStream_start(QuicStream * self, int32_t Flags);
JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicStream_shutdown(QuicStream * self, int32_t Flags, int64_t ErrorCode);
JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicStream_send(QuicStream * self, QUIC_BUFFER * Buffers, int32_t BufferCount, int32_t Flags, void * ClientSendContext);
JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicStream_receiveComplete(QuicStream * self, int64_t BufferLength);
JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicStream_receiveSetEnabled(QuicStream * self, uint8_t IsEnabled);

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicStream
// metadata.generator-version: pni 21.0.0.8
// sha256:1f09d9f70ca9cbbefb38150b4c0146bf1ab0cd3522623f03cc36a26b3497b27e