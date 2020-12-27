/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class msquic_internal_Native */

#ifndef _Included_msquic_internal_Native
#define _Included_msquic_internal_Native
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     msquic_internal_Native
 * Method:    QuicBufferLength
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_msquic_internal_Native_QuicBufferLength
  (JNIEnv *, jobject, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    QuicBufferRead
 * Signature: (JILjava/nio/ByteBuffer;II)I
 */
JNIEXPORT jint JNICALL Java_msquic_internal_Native_QuicBufferRead
  (JNIEnv *, jobject, jlong, jint, jobject, jint, jint);

/*
 * Class:     msquic_internal_Native
 * Method:    MsQuicJavaInit
 * Signature: (Lmsquic/MemoryAllocator;)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_MsQuicJavaInit
  (JNIEnv *, jobject, jobject);

/*
 * Class:     msquic_internal_Native
 * Method:    MsQuicJavaRelease
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_MsQuicJavaRelease
  (JNIEnv *, jobject);

/*
 * Class:     msquic_internal_Native
 * Method:    MsQuicOpen
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_msquic_internal_Native_MsQuicOpen
  (JNIEnv *, jobject);

/*
 * Class:     msquic_internal_Native
 * Method:    MsQuicClose
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_MsQuicClose
  (JNIEnv *, jobject, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    RegistrationOpen
 * Signature: (JLjava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_msquic_internal_Native_RegistrationOpen
  (JNIEnv *, jobject, jlong, jstring, jint);

/*
 * Class:     msquic_internal_Native
 * Method:    RegistrationClose
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_RegistrationClose
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    RegistrationShutdown
 * Signature: (JJIJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_RegistrationShutdown
  (JNIEnv *, jobject, jlong, jlong, jint, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    ConfigurationOpen
 * Signature: (JJ[Ljava/lang/String;IZIZIZ)J
 */
JNIEXPORT jlong JNICALL Java_msquic_internal_Native_ConfigurationOpen
  (JNIEnv *, jobject, jlong, jlong, jobjectArray, jint, jboolean, jint, jboolean, jint, jboolean);

/*
 * Class:     msquic_internal_Native
 * Method:    ConfigurationClose
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ConfigurationClose
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    ConfigurationLoadCredential
 * Signature: (JJLjava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ConfigurationLoadCredential
  (JNIEnv *, jobject, jlong, jlong, jstring, jstring);

/*
 * Class:     msquic_internal_Native
 * Method:    ConfigurationLoadAsClient
 * Signature: (JJZ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ConfigurationLoadAsClient
  (JNIEnv *, jobject, jlong, jlong, jboolean);

/*
 * Class:     msquic_internal_Native
 * Method:    ListenerOpen
 * Signature: (JJLmsquic/internal/InternalListenerCallback;)J
 */
JNIEXPORT jlong JNICALL Java_msquic_internal_Native_ListenerOpen
  (JNIEnv *, jobject, jlong, jlong, jobject);

/*
 * Class:     msquic_internal_Native
 * Method:    ListenerClose
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ListenerClose
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    ListenerStart
 * Signature: (JJ[Ljava/lang/String;IZI)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ListenerStart
  (JNIEnv *, jobject, jlong, jlong, jobjectArray, jint, jboolean, jint);

/*
 * Class:     msquic_internal_Native
 * Method:    ConnectionOpen
 * Signature: (JJLmsquic/internal/InternalConnectionCallback;)J
 */
JNIEXPORT jlong JNICALL Java_msquic_internal_Native_ConnectionOpen
  (JNIEnv *, jobject, jlong, jlong, jobject);

/*
 * Class:     msquic_internal_Native
 * Method:    ConnectionClose
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionClose
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    ConnectionStart
 * Signature: (JJJILjava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionStart
  (JNIEnv *, jobject, jlong, jlong, jlong, jint, jstring, jint);

/*
 * Class:     msquic_internal_Native
 * Method:    ConnectionShutdown
 * Signature: (JJIJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionShutdown
  (JNIEnv *, jobject, jlong, jlong, jint, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    ConnectionSetCallbackHandler
 * Signature: (JJLmsquic/internal/InternalConnectionCallback;)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionSetCallbackHandler
  (JNIEnv *, jobject, jlong, jlong, jobject);

/*
 * Class:     msquic_internal_Native
 * Method:    ConnectionSetConfiguration
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionSetConfiguration
  (JNIEnv *, jobject, jlong, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    ConnectionSendResumptionTicket
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionSendResumptionTicket
  (JNIEnv *, jobject, jlong, jlong, jint);

/*
 * Class:     msquic_internal_Native
 * Method:    GetConnectionLocalAddress
 * Signature: (JJ)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_msquic_internal_Native_GetConnectionLocalAddress
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    GetConnectionRemoteAddress
 * Signature: (JJ)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_msquic_internal_Native_GetConnectionRemoteAddress
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    StreamOpen
 * Signature: (Lmsquic/internal/InternalConnectionCallback;JJILmsquic/internal/InternalStreamCallback;)J
 */
JNIEXPORT jlong JNICALL Java_msquic_internal_Native_StreamOpen
  (JNIEnv *, jobject, jobject, jlong, jlong, jint, jobject);

/*
 * Class:     msquic_internal_Native
 * Method:    StreamClose
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamClose
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    StreamStart
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamStart
  (JNIEnv *, jobject, jlong, jlong, jint);

/*
 * Class:     msquic_internal_Native
 * Method:    StreamSetCallbackHandler
 * Signature: (JJLmsquic/internal/InternalStreamCallback;)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamSetCallbackHandler
  (JNIEnv *, jobject, jlong, jlong, jobject);

/*
 * Class:     msquic_internal_Native
 * Method:    StreamShutdown
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamShutdown
  (JNIEnv *, jobject, jlong, jlong, jint);

/*
 * Class:     msquic_internal_Native
 * Method:    StreamSend
 * Signature: (JJILjava/nio/ByteBuffer;II)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamSend
  (JNIEnv *, jobject, jlong, jlong, jint, jobject, jint, jint);

/*
 * Class:     msquic_internal_Native
 * Method:    StreamReceiveComplete
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamReceiveComplete
  (JNIEnv *, jobject, jlong, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    StreamReceiveSetEnabled
 * Signature: (JJZ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamReceiveSetEnabled
  (JNIEnv *, jobject, jlong, jlong, jboolean);

/*
 * Class:     msquic_internal_Native
 * Method:    StreamReceiveSetTotalLength
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamReceiveSetTotalLength
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     msquic_internal_Native
 * Method:    GetStreamId
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_msquic_internal_Native_GetStreamId
  (JNIEnv *, jobject, jlong, jlong);

#ifdef __cplusplus
}
#endif
#endif