/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicConnectionEventPeerStreamStarted */
#ifndef _Included_io_vproxy_msquic_QuicConnectionEventPeerStreamStarted
#define _Included_io_vproxy_msquic_QuicConnectionEventPeerStreamStarted
#ifdef __cplusplus
extern "C" {
#endif

struct QuicConnectionEventPeerStreamStarted;
typedef struct QuicConnectionEventPeerStreamStarted QuicConnectionEventPeerStreamStarted;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicConnectionEventPeerStreamStarted, QuicConnectionEventPeerStreamStarted *)
PNIBufExpand(QuicConnectionEventPeerStreamStarted, QuicConnectionEventPeerStreamStarted, 16)

struct QuicConnectionEventPeerStreamStarted {
    void * Stream;
    int32_t Flags;
};

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicConnectionEventPeerStreamStarted
// metadata.generator-version: pni 21.0.0.13
// sha256:45ab03b5c832784b08f12a19699bd600a5cf1737320ecf7eecf9559e8347cd54
