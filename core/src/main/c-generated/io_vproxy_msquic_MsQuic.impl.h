#include "io_vproxy_msquic_MsQuic.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT QuicApiTable * JNICALL JavaCritical_io_vproxy_msquic_MsQuic_open(uint32_t Version, int32_t * returnStatus, QuicApiTable * return_) {
    QUIC_API_TABLE* api;
    QUIC_STATUS res = MsQuicOpenVersion(Version, (const void**) &api);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->Api = api;
        return return_;
    }
    return NULL;
}

JNIEXPORT uint8_t JNICALL JavaCritical_io_vproxy_msquic_MsQuic_buildQuicAddr(char * addr, int32_t port, QUIC_ADDR * result) {
    return QuicAddrFromString(addr, port, result);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.8
// sha256:e77f8998ee63289ed9ef6fc1d71f4609ab2e6a13e458022162f19a877b1dc44f
