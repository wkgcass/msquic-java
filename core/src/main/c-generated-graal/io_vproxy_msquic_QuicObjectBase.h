/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicObjectBase */
#ifndef _Included_io_vproxy_msquic_QuicObjectBase
#define _Included_io_vproxy_msquic_QuicObjectBase
#ifdef __cplusplus
extern "C" {
#endif

struct QuicObjectBase;
typedef struct QuicObjectBase QuicObjectBase;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicObjectBase, QuicObjectBase *)
PNIBufExpand(QuicObjectBase, QuicObjectBase, 16)

struct QuicObjectBase {
    void * Api;
    void * Handle;
};

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_setContext(QuicObjectBase * self, void * Context);
JNIEXPORT void * JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_getContext(QuicObjectBase * self);
JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_setCallbackHandler(QuicObjectBase * self, void * Handler, void * Context);
JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_setParam(QuicObjectBase * self, int32_t Param, int32_t BufferLength, void * Buffer);
JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicObjectBase_getParam(QuicObjectBase * self, int32_t Param, uint32_t * BufferLength, void * Buffer);

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicObjectBase
// metadata.generator-version: pni 21.0.0.17
// sha256:4788952c2e303cb0a481b5b30d8960f106fdb4117e713c25de974e12dc3e8a40
