#include "io_vproxy_msquic_QuicAddr.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicAddr_getFamily(QUIC_ADDR * self) {
    return QuicAddrGetFamily(self);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicAddr_setFamily(QUIC_ADDR * self, int32_t family) {
    QuicAddrSetFamily(self, family);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicAddr_getPort(QUIC_ADDR * self) {
    return QuicAddrGetPort(self);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicAddr_setPort(QUIC_ADDR * self, int32_t port) {
    QuicAddrSetPort(self, port);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicAddr_toString(QUIC_ADDR * self, char * str) {
    QuicAddrToString(self, (QUIC_ADDR_STR*) str);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.8
// sha256:914650f1bb9fd4b1a0f51dcf23956fe8c4f1fe16511bcfbcf709503d102ee81a
