#include "io_vproxy_msquic_QuicListener.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicListener_close(QuicListener * self) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC lsn = self->SUPER.Handle;
    api->ListenerClose(lsn);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicListener_start(QuicListener * self, QUIC_BUFFER * AlpnBuffers, int32_t AlpnBufferCount, QUIC_ADDR * Addr) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC lsn = self->SUPER.Handle;

    QUIC_STATUS res = api->ListenerStart(lsn, AlpnBuffers, AlpnBufferCount, Addr);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicListener_stop(QuicListener * self) {
    QUIC_API_TABLE* api = self->SUPER.Api;
    HQUIC lsn = self->SUPER.Handle;
    api->ListenerStop(lsn);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.17
// sha256:94d10b724a35e3a3de4c4cdeb92e56e2674771b10e7f4686795e693d58925d29
