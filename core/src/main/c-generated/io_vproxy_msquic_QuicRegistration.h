/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicRegistration */
#ifndef _Included_io_vproxy_msquic_QuicRegistration
#define _Included_io_vproxy_msquic_QuicRegistration
#ifdef __cplusplus
extern "C" {
#endif

struct QuicRegistration;
typedef struct QuicRegistration QuicRegistration;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"
#include "io_vproxy_msquic_MsQuicUpcall.h"
#include "io_vproxy_msquic_QuicConfiguration.h"
#include "io_vproxy_msquic_QuicBuffer.h"
#include "io_vproxy_msquic_QuicSettings.h"
#include "io_vproxy_msquic_QuicListener.h"
#include "io_vproxy_msquic_QuicConnection.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicRegistration, QuicRegistration *)

PNI_PACK(struct, QuicRegistration, {
    void * Api;
    void * Reg;
});

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_close(QuicRegistration * self);
JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_shutdown(QuicRegistration * self, int32_t Flags, int64_t ErrorCode);
JNIEXPORT QuicConfiguration * JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_openConfiguration(QuicRegistration * self, QUIC_BUFFER * AlpnBuffers, uint32_t AlpnBufferCount, QUIC_SETTINGS * Settings, void * Context, int32_t * returnStatus, QuicConfiguration * return_);
JNIEXPORT QuicListener * JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_openListener(QuicRegistration * self, void * Handler, void * Context, int32_t * returnStatus, QuicListener * return_);
JNIEXPORT QuicConnection * JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_openConnection(QuicRegistration * self, void * Handler, void * Context, int32_t * returnStatus, QuicConnection * return_);

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicRegistration
// metadata.generator-version: pni 21.0.0.8
// sha256:b4b5422d7104883809ca48a5d63375b73e821a772b0e0e97fc5748605a00510f