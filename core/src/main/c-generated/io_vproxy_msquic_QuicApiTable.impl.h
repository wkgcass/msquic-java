#include "io_vproxy_msquic_QuicApiTable.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_close(QuicApiTable * self) {
    QUIC_API_TABLE* api = self->Api;
    MsQuicClose(api);
}

JNIEXPORT QuicRegistration * JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_openRegistration(QuicApiTable * self, QUIC_REGISTRATION_CONFIG * Config, int32_t * returnStatus, QuicRegistration * return_) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC h;
    QUIC_STATUS res = api->RegistrationOpen(Config, &h);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->SUPER.Api = api;
        return_->SUPER.Handle = h;
        return return_;
    }
    return NULL;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.17
// sha256:7baf44a713047c32c956b0c568f36530a8ae22521c22928b75f87f2c658a14a0
