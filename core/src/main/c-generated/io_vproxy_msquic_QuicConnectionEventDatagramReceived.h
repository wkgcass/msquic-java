/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicConnectionEventDatagramReceived */
#ifndef _Included_io_vproxy_msquic_QuicConnectionEventDatagramReceived
#define _Included_io_vproxy_msquic_QuicConnectionEventDatagramReceived
#ifdef __cplusplus
extern "C" {
#endif

struct QuicConnectionEventDatagramReceived;
typedef struct QuicConnectionEventDatagramReceived QuicConnectionEventDatagramReceived;

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

PNIEnvExpand(QuicConnectionEventDatagramReceived, QuicConnectionEventDatagramReceived *)
PNIBufExpand(QuicConnectionEventDatagramReceived, QuicConnectionEventDatagramReceived, 24)

struct QuicConnectionEventDatagramReceived {
    QUIC_BUFFER Buffer;
    int32_t Flags;
};

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicConnectionEventDatagramReceived
// metadata.generator-version: pni 21.0.0.13
// sha256:ecd836e9a9ccd48557219390ea6b515b20d5bed0f064c19f392e9009ef33f102
