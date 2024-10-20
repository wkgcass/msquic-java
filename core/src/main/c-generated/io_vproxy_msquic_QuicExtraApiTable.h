/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicExtraApiTable */
#ifndef _Included_io_vproxy_msquic_QuicExtraApiTable
#define _Included_io_vproxy_msquic_QuicExtraApiTable
#ifdef __cplusplus
extern "C" {
#endif

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QUIC_EXTRA_API_TABLE, QUIC_EXTRA_API_TABLE *)
PNIBufExpand(QUIC_EXTRA_API_TABLE, QUIC_EXTRA_API_TABLE, (0 /* !!invalid!! */))

JNIEXPORT QUIC_STATUS JNICALL JavaCritical_io_vproxy_msquic_QuicExtraApiTable_EventLoopThreadDispatcherSet(QUIC_EXTRA_API_TABLE * self, QUIC_EVENT_LOOP_THREAD_DISPATCH_FN dispatcher);
JNIEXPORT QUIC_STATUS JNICALL JavaCritical_io_vproxy_msquic_QuicExtraApiTable_ThreadGetCur(QUIC_EXTRA_API_TABLE * self, CXPLAT_THREAD* Thread);
JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicExtraApiTable_ThreadSetIsWorker(QUIC_EXTRA_API_TABLE * self, uint8_t isWorker);

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicExtraApiTable
// metadata.generator-version: pni 21.0.0.17
// sha256:88b552741e9d60f48c1477bd5a61d35a59d99354636c31436166531d94cddeb6
