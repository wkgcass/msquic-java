#include "io_vproxy_msquic_QuicApiTable.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_close(QuicApiTable * self) {
    QUIC_API_TABLE* api = self->Api;
    MsQuicClose(api);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_setContext(QuicApiTable * self, void * Handle, void * Context) {
    QUIC_API_TABLE* api = self->Api;
    api->SetContext(Handle, Context);
}

JNIEXPORT void * JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_getContext(QuicApiTable * self, void * Handle) {
    QUIC_API_TABLE* api = self->Api;
    return api->GetContext(Handle);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_setCallbackHandler(QuicApiTable * self, void * Handle, void * Handler, void * Context) {
    QUIC_API_TABLE* api = self->Api;
    api->SetCallbackHandler(Handle, Handler, Context);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_setParam(QuicApiTable * self, void * Handle, int32_t Param, int32_t BufferLength, void * Buffer) {
    QUIC_API_TABLE* api = self->Api;
    QUIC_STATUS res = api->SetParam(Handle, Param, BufferLength, Buffer);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_getParam(QuicApiTable * self, void * Handle, int32_t Param, uint32_t * BufferLength, void * Buffer) {
    QUIC_API_TABLE* api = self->Api;
    QUIC_STATUS res = api->GetParam(Handle, Param, BufferLength, Buffer);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT QuicRegistration * JNICALL JavaCritical_io_vproxy_msquic_QuicApiTable_openRegistration(QuicApiTable * self, QUIC_REGISTRATION_CONFIG * Config, int32_t * returnStatus, QuicRegistration * return_) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC h;
    QUIC_STATUS res = api->RegistrationOpen(Config, &h);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->Api = api;
        return_->Reg = h;
        return return_;
    }
    return NULL;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.8
// sha256:6a3dd49d0483722674ed1156229f178bc39358cd1d563bbe50bc0cea49cc869f
