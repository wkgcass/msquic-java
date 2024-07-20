#include "io_vproxy_msquic_QuicExtraApiTable.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicExtraApiTable_ThreadCountLimitSet(QUIC_EXTRA_API_TABLE * self, uint32_t limit) {
    self->ThreadCountLimitSet(limit);
}

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
// sha256:092c59044ef8212082642eb50c2b80e117fa9206a2a2adc6973300e6e38df8e3
