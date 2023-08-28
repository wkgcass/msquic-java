#include "io_vproxy_msquic_QuicRegistration.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_close(QuicRegistration * self) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC reg = self->Reg;
    api->RegistrationClose(reg);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_shutdown(QuicRegistration * self, int32_t Flags, int64_t ErrorCode) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC reg = self->Reg;

    api->RegistrationShutdown(reg, Flags, ErrorCode);
}

JNIEXPORT QuicConfiguration * JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_openConfiguration(QuicRegistration * self, QUIC_BUFFER * AlpnBuffers, uint32_t AlpnBufferCount, QUIC_SETTINGS * Settings, void * Context, int32_t * returnStatus, QuicConfiguration * return_) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC reg = self->Reg;

    HQUIC configuration;
    QUIC_STATUS res = api->ConfigurationOpen(
            reg, AlpnBuffers, AlpnBufferCount, Settings, sizeof(QUIC_SETTINGS), Context, &configuration);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->Api = api;
        return_->Conf = configuration;
        return return_;
    }
    return NULL;
}

JNIEXPORT QuicListener * JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_openListener(QuicRegistration * self, void * Handler, void * Context, int32_t * returnStatus, QuicListener * return_) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC reg = self->Reg;

    HQUIC lsn;
    QUIC_STATUS res = api->ListenerOpen(reg,
            (QUIC_LISTENER_CALLBACK_HANDLER) Handler,
            Context, &lsn);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->Api = api;
        return_->Lsn = lsn;
        return return_;
    }
    return NULL;
}

JNIEXPORT QuicConnection * JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_openConnection(QuicRegistration * self, void * Handler, void * Context, int32_t * returnStatus, QuicConnection * return_) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC reg = self->Reg;

    HQUIC conn;
    QUIC_STATUS res = api->ConnectionOpen(reg,
            (QUIC_CONNECTION_CALLBACK_HANDLER) Handler,
            Context, &conn);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->Api = api;
        return_->Conn = conn;
        return return_;
    }
    return NULL;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.8
// sha256:6f2bc9810a9612f05a05351113c00868d102593960f46101b0ef1c6ec90c792a
