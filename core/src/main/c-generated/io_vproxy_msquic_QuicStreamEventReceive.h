/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicStreamEventReceive */
#ifndef _Included_io_vproxy_msquic_QuicStreamEventReceive
#define _Included_io_vproxy_msquic_QuicStreamEventReceive
#ifdef __cplusplus
extern "C" {
#endif

struct QuicStreamEventReceive;
typedef struct QuicStreamEventReceive QuicStreamEventReceive;

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

PNIEnvExpand(QuicStreamEventReceive, QuicStreamEventReceive *)

PNI_PACK(struct, QuicStreamEventReceive, {
    uint64_t AbsoluteOffset;
    uint64_t TotalBufferLength;
    QUIC_BUFFER * Buffers;
    uint32_t BufferCount;
    int32_t Flags;
});

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicStreamEventReceive
// metadata.generator-version: pni 21.0.0.8
// sha256:fa2346332332e3cdce55c86b2099ac46d87c19cb3455dcbf8c90cd0d839fc1ab