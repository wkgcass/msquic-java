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

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_setParam(QuicApiTable * self, int32_t Param, int32_t BufferLength, void * Buffer) {
    QUIC_API_TABLE* api = self->Api;
    QUIC_STATUS res = api->SetParam(NULL, Param, BufferLength, Buffer);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_getParam(QuicApiTable * self, int32_t Param, uint32_t * BufferLength, void * Buffer) {
    QUIC_API_TABLE* api = self->Api;
    QUIC_STATUS res = api->GetParam(NULL, Param, BufferLength, Buffer);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.17
// sha256:4b5289240e0eac2bdd05843044257f09fc1709a09c79f541e851dc9ef5b8a0d6
