#include "io_vproxy_msquic_QuicRegistration.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_close(QuicRegistration * self) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC reg = self->SUPER.Handle;
    api->RegistrationClose(reg);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_shutdown(QuicRegistration * self, int32_t Flags, int64_t ErrorCode) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC reg = self->SUPER.Handle;

    api->RegistrationShutdown(reg, Flags, ErrorCode);
}

JNIEXPORT QuicConfiguration * JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_openConfiguration(QuicRegistration * self, QUIC_BUFFER * AlpnBuffers, uint32_t AlpnBufferCount, QUIC_SETTINGS * Settings, void * Context, int32_t * returnStatus, QuicConfiguration * return_) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC reg = self->SUPER.Handle;

    HQUIC configuration;
    QUIC_STATUS res = api->ConfigurationOpen(
            reg, AlpnBuffers, AlpnBufferCount, Settings, sizeof(QUIC_SETTINGS), Context, &configuration);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->SUPER.Api = api;
        return_->SUPER.Handle = configuration;
        return return_;
    }
    return NULL;
}

JNIEXPORT QuicListener * JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_openListener(QuicRegistration * self, void * Handler, void * Context, int32_t * returnStatus, QuicListener * return_) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC reg = self->SUPER.Handle;

    HQUIC lsn;
    QUIC_STATUS res = api->ListenerOpen(reg,
            (QUIC_LISTENER_CALLBACK_HANDLER) Handler,
            Context, &lsn);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->SUPER.Api = api;
        return_->SUPER.Handle = lsn;
        return return_;
    }
    return NULL;
}

JNIEXPORT QuicConnection * JNICALL JavaCritical_io_vproxy_msquic_QuicRegistration_openConnection(QuicRegistration * self, void * Handler, void * Context, int32_t * returnStatus, QuicConnection * return_) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC reg = self->SUPER.Handle;

    HQUIC conn;
    QUIC_STATUS res = api->ConnectionOpen(reg,
            (QUIC_CONNECTION_CALLBACK_HANDLER) Handler,
            Context, &conn);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->SUPER.Api = api;
        return_->SUPER.Handle = conn;
        return return_;
    }
    return NULL;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.17
// sha256:955246e6a1cf4b0f5a56c189d15c5905705787047daeeee005eda32257f6965f
