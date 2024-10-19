#include "io_vproxy_msquic_QuicExtraApiTable.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT QUIC_STATUS JNICALL JavaCritical_io_vproxy_msquic_QuicExtraApiTable_EventLoopThreadDispatcherSet(QUIC_EXTRA_API_TABLE * self, QUIC_EVENT_LOOP_THREAD_DISPATCH_FN dispatcher) {
    return self->EventLoopThreadDispatcherSet(dispatcher);
}

JNIEXPORT QUIC_STATUS JNICALL JavaCritical_io_vproxy_msquic_QuicExtraApiTable_ThreadGetCur(QUIC_EXTRA_API_TABLE * self, CXPLAT_THREAD* Thread) {
    return self->ThreadGetCur(Thread);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicExtraApiTable_ThreadSetIsWorker(QUIC_EXTRA_API_TABLE * self, uint8_t isWorker) {
    self->ThreadSetIsWorker(isWorker);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.17
// sha256:d234e804719dc69f86ee2ebd020430c0a1265deb05b967230c00489a15ad7f56
