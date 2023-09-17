/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicConnectionEventResumptionTicketReceived */
#ifndef _Included_io_vproxy_msquic_QuicConnectionEventResumptionTicketReceived
#define _Included_io_vproxy_msquic_QuicConnectionEventResumptionTicketReceived
#ifdef __cplusplus
extern "C" {
#endif

struct QuicConnectionEventResumptionTicketReceived;
typedef struct QuicConnectionEventResumptionTicketReceived QuicConnectionEventResumptionTicketReceived;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicConnectionEventResumptionTicketReceived, QuicConnectionEventResumptionTicketReceived *)

PNI_PACK(struct, QuicConnectionEventResumptionTicketReceived, {
    uint32_t ResumptionTicketLength; /* padding */ uint64_t :32;
    void * ResumptionTicket;
});

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicConnectionEventResumptionTicketReceived
// metadata.generator-version: pni 21.0.0.8
// sha256:9b1bb9f75d13326f1905e725869a25f36eb4d3751e1666fb3183a8fcf7556527