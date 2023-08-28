/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicConfiguration */
#ifndef _Included_io_vproxy_msquic_QuicConfiguration
#define _Included_io_vproxy_msquic_QuicConfiguration
#ifdef __cplusplus
extern "C" {
#endif

struct QuicConfiguration;
typedef struct QuicConfiguration QuicConfiguration;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"
#include "io_vproxy_msquic_QuicCredentialConfig.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicConfiguration, QuicConfiguration *)

PNI_PACK(struct, QuicConfiguration, {
    void * Api;
    void * Conf;
});

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicConfiguration_close(QuicConfiguration * self);
JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicConfiguration_loadCredential(QuicConfiguration * self, QUIC_CREDENTIAL_CONFIG * CredConfig);

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicConfiguration
// metadata.generator-version: pni 21.0.0.8
// sha256:09a7bde8c0270a664fc9c45cbb5e01d77815c5d36a0c62a62799eb4262ebfa52
