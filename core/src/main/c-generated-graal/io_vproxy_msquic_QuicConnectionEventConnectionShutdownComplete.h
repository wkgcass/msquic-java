/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicConnectionEventConnectionShutdownComplete */
#ifndef _Included_io_vproxy_msquic_QuicConnectionEventConnectionShutdownComplete
#define _Included_io_vproxy_msquic_QuicConnectionEventConnectionShutdownComplete
#ifdef __cplusplus
extern "C" {
#endif

struct QuicConnectionEventConnectionShutdownComplete;
typedef struct QuicConnectionEventConnectionShutdownComplete QuicConnectionEventConnectionShutdownComplete;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicConnectionEventConnectionShutdownComplete, QuicConnectionEventConnectionShutdownComplete *)
PNIBufExpand(QuicConnectionEventConnectionShutdownComplete, QuicConnectionEventConnectionShutdownComplete, 1)

struct QuicConnectionEventConnectionShutdownComplete {
    uint8_t HandshakeCompleted : 1;
    uint8_t PeerAcknowledgedShutdown : 1;
    uint8_t AppCloseInProgress : 1;
    uint8_t : 5;

};

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicConnectionEventConnectionShutdownComplete
// metadata.generator-version: pni 21.0.0.16
// sha256:9d6ee096840f897117207516aedbd52141fc0494e6c16bd7b3cf89279aef3d85