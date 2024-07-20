#include "io_vproxy_msquic_MsQuicMod.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT QUIC_EXTRA_API_TABLE * JNICALL JavaCritical_io_vproxy_msquic_MsQuicMod_openExtra(uint32_t Version, int32_t * returnStatus) {
    QUIC_EXTRA_API_TABLE* api;
    QUIC_STATUS res = MsQuicOpenExtra(Version, (const void**) &api);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return api;
    }
    return NULL;
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_MsQuicMod_INVOKE_LPTHREAD_START_ROUTINE(void * Callback, void * Context) {
    ((LPTHREAD_START_ROUTINE) Callback)(Context);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.17
// sha256:2c3f4da2fdf4405f3c0972dbc280e69cd99e1e2aa501370c52e22f560e9574f1
