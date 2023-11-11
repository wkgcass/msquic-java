#include "io_vproxy_msquic_QuicConfiguration.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicConfiguration_close(QuicConfiguration * self) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC conf = self->SUPER.Handle;
    api->ConfigurationClose(conf);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicConfiguration_loadCredential(QuicConfiguration * self, QUIC_CREDENTIAL_CONFIG * CredConfig) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC conf = self->SUPER.Handle;

    QUIC_STATUS res = api->ConfigurationLoadCredential(conf, CredConfig);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.17
// sha256:fd7812d7c57fa62015ebff45db86f435e95e6bccf08ac8665c015d182b2055df
