#include "io_vproxy_msquic_QuicObjectBase.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_setContext(QuicObjectBase * self, void * Context) {
    QUIC_API_TABLE* api = self->Api;
    api->SetContext(self->Handle, Context);
}

JNIEXPORT void * JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_getContext(QuicObjectBase * self) {
    QUIC_API_TABLE* api = self->Api;
    return api->GetContext(self->Handle);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_setCallbackHandler(QuicObjectBase * self, void * Handler, void * Context) {
    QUIC_API_TABLE* api = self->Api;
    api->SetCallbackHandler(self->Handle, Handler, Context);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_setParam(QuicObjectBase * self, int32_t Param, int32_t BufferLength, void * Buffer) {
    QUIC_API_TABLE* api = self->Api;
    QUIC_STATUS res = api->SetParam(self->Handle, Param, BufferLength, Buffer);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_getParam(QuicObjectBase * self, int32_t Param, uint32_t * BufferLength, void * Buffer) {
    QUIC_API_TABLE* api = self->Api;
    QUIC_STATUS res = api->GetParam(self->Handle, Param, BufferLength, Buffer);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.17
// sha256:f992bdb5ecc1d314ab511242caaed1e36f9c43bb0efa82f8cc34493e22b9815b
