#include "io_vproxy_msquic_QuicStream.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicStream_close(QuicStream * self) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC stream = self->SUPER.Handle;
    api->StreamClose(stream);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicStream_start(QuicStream * self, int32_t Flags) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC stream = self->SUPER.Handle;

    QUIC_STATUS res = api->StreamStart(stream, Flags);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicStream_shutdown(QuicStream * self, int32_t Flags, int64_t ErrorCode) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC stream = self->SUPER.Handle;

    QUIC_STATUS res = api->StreamShutdown(stream, Flags, ErrorCode);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicStream_send(QuicStream * self, QUIC_BUFFER * Buffers, int32_t BufferCount, int32_t Flags, void * ClientSendContext) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC stream = self->SUPER.Handle;

    QUIC_STATUS res = api->StreamSend(stream, Buffers, BufferCount, Flags, ClientSendContext);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicStream_receiveComplete(QuicStream * self, int64_t BufferLength) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC stream = self->SUPER.Handle;

    api->StreamReceiveComplete(stream, BufferLength);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicStream_receiveSetEnabled(QuicStream * self, uint8_t IsEnabled) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC stream = self->SUPER.Handle;

    QUIC_STATUS res = api->StreamReceiveSetEnabled(stream, IsEnabled);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.17
// sha256:1bb3c1de1e6d09144ee89e1f239d2b9d852651b1b6dd277f745f847a71ae3276
