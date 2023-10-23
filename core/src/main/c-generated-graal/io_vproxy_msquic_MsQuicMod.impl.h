#include "io_vproxy_msquic_MsQuicMod.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicMod_MsQuicSetEventLoopThreadDispatcher(void * dispatcher) {
    return MsQuicSetEventLoopThreadDispatcher(dispatcher);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicMod_CxPlatGetCurThread(void * Thread) {
    return CxPlatGetCurThread((CXPLAT_THREAD*) Thread);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_MsQuicMod_INVOKE_LPTHREAD_START_ROUTINE(void * Callback, void * Context) {
    ((LPTHREAD_START_ROUTINE) Callback)(Context);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.16
// sha256:7d8f5eb7b251ac45cf0ea2f87480599055a436ca9429a89b013b534675d99acf
