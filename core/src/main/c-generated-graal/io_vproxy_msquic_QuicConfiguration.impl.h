#include "io_vproxy_msquic_QuicConfiguration.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicConfiguration_close(QuicConfiguration * self) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conf = self->Conf;
    api->ConfigurationClose(conf);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicConfiguration_loadCredential(QuicConfiguration * self, QUIC_CREDENTIAL_CONFIG * CredConfig) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conf = self->Conf;

    QUIC_STATUS res = api->ConfigurationLoadCredential(conf, CredConfig);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.16
// sha256:13000c77a3e6a3ef4576b67aa5b18db5085c8a273df5ee8f73f4c8001c1ba2d9
