#include "io_vproxy_msquic_QuicListener.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicListener_close(QuicListener * self) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC lsn = self->Lsn;
    api->ListenerClose(lsn);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicListener_start(QuicListener * self, QUIC_BUFFER * AlpnBuffers, int32_t AlpnBufferCount, QUIC_ADDR * Addr) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC lsn = self->Lsn;

    QUIC_STATUS res = api->ListenerStart(lsn, AlpnBuffers, AlpnBufferCount, Addr);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicListener_stop(QuicListener * self) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC lsn = self->Lsn;
    api->ListenerStop(lsn);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.8
// sha256:95aa2e54acdb441d095a2bfdff6b98d637c8b0dccb2c043357f478a32d66d4d3
