/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicConnectionEventPeerAddressChanged */
#ifndef _Included_io_vproxy_msquic_QuicConnectionEventPeerAddressChanged
#define _Included_io_vproxy_msquic_QuicConnectionEventPeerAddressChanged
#ifdef __cplusplus
extern "C" {
#endif

struct QuicConnectionEventPeerAddressChanged;
typedef struct QuicConnectionEventPeerAddressChanged QuicConnectionEventPeerAddressChanged;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"
#include "io_vproxy_msquic_QuicAddr.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicConnectionEventPeerAddressChanged, QuicConnectionEventPeerAddressChanged *)

PNI_PACK(struct, QuicConnectionEventPeerAddressChanged, {
    QUIC_ADDR * Address;
});

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicConnectionEventPeerAddressChanged
// metadata.generator-version: pni 21.0.0.8
// sha256:301259a9fbe4d517f53da16ea13a50a085551e84df35c379dc3e476d733c36aa
