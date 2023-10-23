#include "io_vproxy_msquic_MsQuicModUpcall.h"
#include <stdio.h>
#include <stdlib.h>

#ifdef __cplusplus
extern "C" {
#endif

static int32_t (*_dispatch)(CXPLAT_THREAD_CONFIG *,CXPLAT_EVENTQ *,void *,void *);

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_MsQuicModUpcall_INIT(
    int32_t (*dispatch)(CXPLAT_THREAD_CONFIG *,CXPLAT_EVENTQ *,void *,void *)
) {
    _dispatch = dispatch;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_MsQuicModUpcall_dispatch(CXPLAT_THREAD_CONFIG * Config, CXPLAT_EVENTQ * EventQ, void * Thread, void * Context) {
    if (_dispatch == NULL) {
        printf("JavaCritical_io_vproxy_msquic_MsQuicModUpcall_dispatch function pointer is null");
        fflush(stdout);
        exit(1);
    }
    return _dispatch(Config, EventQ, Thread, Context);
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.16
// sha256:b9205bff7493940fc47d62f2ee8ad58022417490b79d4bb57a4ddc5faf559bff
