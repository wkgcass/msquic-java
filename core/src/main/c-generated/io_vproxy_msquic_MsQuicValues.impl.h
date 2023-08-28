#include "io_vproxy_msquic_MsQuicValues.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicValues_sizeofQuicAddr(void) {
    return sizeof(QUIC_ADDR);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_STATUS_NOT_SUPPORTED(void) {
    return QUIC_STATUS_NOT_SUPPORTED;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_ADDRESS_FAMILY_UNSPEC(void) {
    return QUIC_ADDRESS_FAMILY_UNSPEC;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_ADDRESS_FAMILY_INET(void) {
    return QUIC_ADDRESS_FAMILY_INET;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_ADDRESS_FAMILY_INET6(void) {
    return QUIC_ADDRESS_FAMILY_INET6;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.8
// sha256:7917943bed4fde4b637fc490e628c518e1bc3809b7e239a3ffcf0ac3b1d38fef
