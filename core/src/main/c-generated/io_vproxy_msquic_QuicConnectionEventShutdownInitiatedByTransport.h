/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicConnectionEventShutdownInitiatedByTransport */
#ifndef _Included_io_vproxy_msquic_QuicConnectionEventShutdownInitiatedByTransport
#define _Included_io_vproxy_msquic_QuicConnectionEventShutdownInitiatedByTransport
#ifdef __cplusplus
extern "C" {
#endif

struct QuicConnectionEventShutdownInitiatedByTransport;
typedef struct QuicConnectionEventShutdownInitiatedByTransport QuicConnectionEventShutdownInitiatedByTransport;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicConnectionEventShutdownInitiatedByTransport, QuicConnectionEventShutdownInitiatedByTransport *)
PNIBufExpand(QuicConnectionEventShutdownInitiatedByTransport, QuicConnectionEventShutdownInitiatedByTransport, 16)

struct QuicConnectionEventShutdownInitiatedByTransport {
    int32_t Status;
    int64_t ErrorCode;
};

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicConnectionEventShutdownInitiatedByTransport
// metadata.generator-version: pni 21.0.0.13
// sha256:4d75e95100db5b28d42e6e5908effb6442f9ae8e617b016210aaaaf317b3fa3d
