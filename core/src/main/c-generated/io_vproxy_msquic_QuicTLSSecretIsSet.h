/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicTLSSecretIsSet */
#ifndef _Included_io_vproxy_msquic_QuicTLSSecretIsSet
#define _Included_io_vproxy_msquic_QuicTLSSecretIsSet
#ifdef __cplusplus
extern "C" {
#endif

struct QuicTLSSecretIsSet;
typedef struct QuicTLSSecretIsSet QuicTLSSecretIsSet;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicTLSSecretIsSet, QuicTLSSecretIsSet *)

PNI_PACK(struct, QuicTLSSecretIsSet, {
    uint8_t ClientRandom : 1;
    uint8_t ClientEarlyTrafficSecret : 1;
    uint8_t ClientHandshakeTrafficSecret : 1;
    uint8_t ServerHandshakeTrafficSecret : 1;
    uint8_t ClientTrafficSecret0 : 1;
    uint8_t ServerTrafficSecret0 : 1;
    uint8_t : 2;

});

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicTLSSecretIsSet
// metadata.generator-version: pni 21.0.0.8
// sha256:d6a29a82ac4034062621327eff601559789894509eb59257376a8c3a3a8c1601
