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

PNI_PACK(struct, QuicConnectionEventShutdownInitiatedByTransport, {
    int32_t Status; /* padding */ uint64_t :32;
    int64_t ErrorCode;
});

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicConnectionEventShutdownInitiatedByTransport
// metadata.generator-version: pni 21.0.0.8
// sha256:754ff6855c1a508834087735bedbf35329eafd745f82fc7870da2753f0b71685