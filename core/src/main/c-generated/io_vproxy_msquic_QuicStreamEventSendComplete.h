/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicStreamEventSendComplete */
#ifndef _Included_io_vproxy_msquic_QuicStreamEventSendComplete
#define _Included_io_vproxy_msquic_QuicStreamEventSendComplete
#ifdef __cplusplus
extern "C" {
#endif

struct QuicStreamEventSendComplete;
typedef struct QuicStreamEventSendComplete QuicStreamEventSendComplete;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicStreamEventSendComplete, QuicStreamEventSendComplete *)

PNI_PACK(struct, QuicStreamEventSendComplete, {
    uint8_t Canceled; /* padding */ uint64_t :56;
    void * ClientContext;
});

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicStreamEventSendComplete
// metadata.generator-version: pni 21.0.0.8
// sha256:af167fc3dec08c374ac9ca800551d3d61a93e67dc43297b113821c9455a5df3d