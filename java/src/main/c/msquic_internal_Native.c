#include <string.h>
#include <errno.h>
#include <msquic.h>
#include <stdio.h>
#include "msquic_internal_Native.h"

jclass MsQuicException;
jmethodID MsQuicException_init;

jclass InternalListenerCallback;
jmethodID InternalListenerCallback_attachOrGetConnectionWrapper;
jmethodID InternalListenerCallback_callback;
jobject Ref_InternalListenerCallback;

jclass InternalConnectionCallback;
jmethodID InternalConnectionCallback_attachOrGetStreamWrapper;
jmethodID InternalConnectionCallback_callback;
jobject Ref_InternalConnectionCallback;

jclass InternalStreamCallback;
jmethodID InternalStreamCallback_callback;

void* attachThread(void* ptr) {
  JavaVM* jvm = (JavaVM*)ptr;
  JNIEnv* env;
  jint err = (*jvm)->AttachCurrentThread(jvm, (void**)&env, NULL);
  if (err != JNI_OK) {
    printf("AttachCurrentThread failed with %d\n", err);
    fflush(stdout);
    return NULL;
  }
  return (void*)env;
}

void detachThread(void* ptr) {
  JavaVM* jvm = (JavaVM*)ptr;
  jint err = (*jvm)->DetachCurrentThread(jvm);
  if (err != JNI_OK) {
    printf("DetachCurrentThread failed with %d\n", err);
    fflush(stdout);
  }
}

JNIEXPORT void JNICALL Java_msquic_internal_Native_MsQuicJavaInit
  (JNIEnv* env, jobject self) {
    MsQuicException = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "msquic/MsQuicException"));
    MsQuicException_init = (*env)->GetMethodID(env, MsQuicException, "<init>", "(I)V");

    InternalListenerCallback = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "msquic/internal/InternalListenerCallback"));
    InternalListenerCallback_attachOrGetConnectionWrapper =
      (*env)->GetMethodID(env, InternalListenerCallback, "attachOrGetConnectionWrapper", "(JJ)J"); // (real, wrapper)->wrapper
    InternalListenerCallback_callback = (*env)->GetMethodID(env, InternalListenerCallback, "callback", "(IJ)I");

    InternalConnectionCallback = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "msquic/internal/InternalConnectionCallback"));
    InternalConnectionCallback_attachOrGetStreamWrapper =
      (*env)->GetMethodID(env, InternalConnectionCallback, "attachOrGetStreamWrapper", "(JJ)J"); // (real, wrapper)->wrapper
    InternalConnectionCallback_callback = (*env)->GetMethodID(env, InternalConnectionCallback, "callback", "(IJ)I");

    InternalStreamCallback = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "msquic/internal/InternalStreamCallback"));
    InternalStreamCallback_callback = (*env)->GetMethodID(env, InternalStreamCallback, "callback", "(I)I");

    JavaVM* jvm;
    jint err = (*env)->GetJavaVM(env, &jvm);
    if (err != JNI_OK) {
      printf("GetJavaVM failed with %d\n", err);
      fflush(stdout);
      return;
    }
    MsQuicJavaInit(jvm, attachThread, detachThread);
  }

void throwException(JNIEnv* env, QUIC_STATUS err) {
  jobject exObj = (*env)->NewObject(env, MsQuicException, MsQuicException_init, (jint)err);
  (*env)->Throw(env, exObj);
}

JNIEXPORT jlong JNICALL Java_msquic_internal_Native_MsQuicOpen
  (JNIEnv* env, jobject self) {

    QUIC_API_TABLE* msquic;
    QUIC_STATUS err = MsQuicOpen((const QUIC_API_TABLE **)&msquic);
    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return 0;
    }
    return (jlong)msquic;
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_MsQuicClose
  (JNIEnv* env, jobject self, jlong msquicPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;

    MsQuicClose(msquic);
  }

JNIEXPORT jlong JNICALL Java_msquic_internal_Native_RegistrationOpen
  (JNIEnv* env, jobject self, jlong msquicPtr, jstring appName, jint profile) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;

    const char* appNameChars = (*env)->GetStringUTFChars(env, appName, NULL);

    const QUIC_REGISTRATION_CONFIG regConfig = { appNameChars, profile };
    HQUIC reg;
    QUIC_STATUS err = msquic->RegistrationOpen(&regConfig, &reg);

    // release
    {
      (*env)->ReleaseStringUTFChars(env, appName, appNameChars);
    }

    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return 0;
    }
    return (jlong)reg;
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_RegistrationClose
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong regPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    HQUIC reg = (HQUIC)regPtr;

    msquic->RegistrationClose(reg);
  }

JNIEXPORT jlong JNICALL Java_msquic_internal_Native_ConfigurationOpen
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong regPtr, jobjectArray alpn,
  jint idleTimeoutMs, jboolean isSetIdleTimeoutMs,
  jint serverResumeptionLevel, jboolean isSetServerResumeptionLevel,
  jint peerBidiStreamCount, jboolean isSetPeerBidiStreamCount) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    HQUIC reg = (HQUIC)regPtr;

    jsize alpnLen = (*env)->GetArrayLength(env, alpn);
    QUIC_BUFFER* alpnBuffers = malloc(sizeof(QUIC_BUFFER) * alpnLen);
    for (jsize i = 0; i < alpnLen; ++i) {
      jobject str = (*env)->GetObjectArrayElement(env, alpn, i);
      const char* strChars = (*env)->GetStringUTFChars(env, str, NULL);
      int strLen = strlen(strChars);
      alpnBuffers[i].Length = strLen;
      alpnBuffers[i].Buffer = (char*)strChars;
    }

    QUIC_SETTINGS settings;
    memset(&settings, 0, sizeof(settings));
    if (isSetIdleTimeoutMs) {
      settings.IdleTimeoutMs = idleTimeoutMs;
      settings.IsSet.IdleTimeoutMs = TRUE;
    }
    if (isSetServerResumeptionLevel) {
      settings.ServerResumptionLevel = serverResumeptionLevel;
      settings.IsSet.ServerResumptionLevel = TRUE;
    }
    if (isSetPeerBidiStreamCount) {
      settings.PeerBidiStreamCount = peerBidiStreamCount;
      settings.IsSet.PeerBidiStreamCount = TRUE;
    }

    HQUIC conf;
    QUIC_STATUS err = msquic->ConfigurationOpen(reg, alpnBuffers, alpnLen, &settings, sizeof(settings), NULL, &conf);

    // release
    {
      for (jsize i = 0; i < alpnLen; ++i) {
        char* strChars = alpnBuffers[i].Buffer;
        jobject str = (*env)->GetObjectArrayElement(env, alpn, i);
        (*env)->ReleaseStringUTFChars(env, str, strChars);
      }
      free(alpnBuffers);
    }

    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return 0;
    }
    return (jlong)conf;
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_ConfigurationClose
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong confPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    HQUIC conf = (HQUIC)confPtr;

    msquic->ConfigurationClose(conf);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_ConfigurationLoadCredential
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong confPtr, jstring certFilePath, jstring keyFilePath) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    HQUIC conf = (HQUIC)confPtr;

    const char* certFilePathChars = (*env)->GetStringUTFChars(env, certFilePath, NULL);
    const char* keyFilePathChars = (*env)->GetStringUTFChars(env, keyFilePath, NULL);

    QUIC_CERTIFICATE_FILE certFile;
    memset(&certFile, 0, sizeof(QUIC_CERTIFICATE_FILE));
    certFile.CertificateFile = certFilePathChars;
    certFile.PrivateKeyFile = keyFilePathChars;
    QUIC_CREDENTIAL_CONFIG credConfig;
    memset(&credConfig, 0, sizeof(QUIC_CREDENTIAL_CONFIG));
    credConfig.Type = QUIC_CREDENTIAL_TYPE_CERTIFICATE_FILE;
    credConfig.CertificateFile = &certFile;

    QUIC_STATUS err = msquic->ConfigurationLoadCredential(conf, &credConfig);

    // release
    {
      (*env)->ReleaseStringUTFChars(env, certFilePath, certFilePathChars);
      (*env)->ReleaseStringUTFChars(env, keyFilePath, keyFilePathChars);
    }

    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return;
    }
    return;
  }

JNIEnv* getJNIEnv() {
  void* ptr;
  QUIC_STATUS err = MsQuicGetJNIEnv(&ptr);
  if (QUIC_FAILED(err)) {
    printf("getJNIEnv failed\n");
    fflush(stdout);
    return NULL;
  }
  return (JNIEnv*)ptr;
}

typedef struct st_msquic_listener {
  jobject cbRef;
  HQUIC lsn;
} msquic_listener_t;

typedef struct st_msquic_connection {
  jobject cbRef;
  HQUIC conn;
} msquic_connection_t;

typedef struct st_msquic_stream {
  jobject cbRef;
  HQUIC stream;
} msquic_stream_t;

msquic_connection_t* wrapConnectionOfListener(JNIEnv* env, jobject cbRef, HQUIC conn) {
  if (conn == NULL) {
    return NULL;
  }

  msquic_connection_t* wrapper = malloc(sizeof(msquic_connection_t));

  jlong wrapper2 = (*env)->CallLongMethod(env, cbRef, InternalListenerCallback_attachOrGetConnectionWrapper, (jlong)conn, (jlong)wrapper);
  if (((jlong)wrapper) != wrapper2) {
    // already exists
    free(wrapper);
    return (msquic_connection_t*)wrapper2;
  }
  // new wrapper
  memset(wrapper, 0, sizeof(msquic_connection_t));
  wrapper->conn = conn;
  return wrapper;
}

QUIC_STATUS listenerCallback(HQUIC lsn, void* data, QUIC_LISTENER_EVENT* event) {
  JNIEnv* env = getJNIEnv();
  if (env == NULL) {
    return QUIC_STATUS_PENDING;
  }
  msquic_listener_t* wrapper = (msquic_listener_t*)data;
  jobject cbRef = wrapper->cbRef;
  jint type = event->Type;
  HQUIC newConnection = NULL;
  if (type == QUIC_LISTENER_EVENT_NEW_CONNECTION) {
    newConnection = event->NEW_CONNECTION.Connection;
  }
  jlong newConnectionPtr = (jlong)wrapConnectionOfListener(env, cbRef, newConnection);

  return (QUIC_STATUS) (*env)->CallIntMethod(env, cbRef, InternalListenerCallback_callback, type, newConnectionPtr);
}

JNIEXPORT jlong JNICALL Java_msquic_internal_Native_ListenerOpen
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong regPtr, jobject cb) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    HQUIC reg = (HQUIC)regPtr;

    jobject cbRef = (*env)->NewGlobalRef(env, cb);
    msquic_listener_t* wrapper = malloc(sizeof(msquic_listener_t));
    wrapper->cbRef = cbRef;

    HQUIC lsn;
    QUIC_STATUS err = msquic->ListenerOpen(reg, listenerCallback, wrapper, &lsn);
    if (QUIC_FAILED(err)) {
      // release ref
      (*env)->DeleteGlobalRef(env, wrapper->cbRef);
      free(wrapper);
      throwException(env, err);
      return 0;
    }
    wrapper->lsn = lsn;

    return (jlong)wrapper;
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_ListenerClose
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong lsnPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_listener_t* wrapper = (msquic_listener_t*)lsnPtr;
    HQUIC lsn = wrapper->lsn;

    msquic->ListenerClose(lsn);
    if (wrapper->cbRef != NULL) {
      (*env)->DeleteGlobalRef(env, wrapper->cbRef);
    }
    free(wrapper);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_ListenerStart
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong lsnPtr, jobjectArray alpn, jint family, jboolean loopback, jint port) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_listener_t* wrapper = (msquic_listener_t*)lsnPtr;
    HQUIC lsn = wrapper->lsn;

    jsize alpnLen = (*env)->GetArrayLength(env, alpn);
    QUIC_BUFFER* alpnBuffers = malloc(sizeof(QUIC_BUFFER) * alpnLen);
    for (jsize i = 0; i < alpnLen; ++i) {
      jobject str = (*env)->GetObjectArrayElement(env, alpn, i);
      const char* strChars = (*env)->GetStringUTFChars(env, str, NULL);
      int strLen = strlen(strChars);
      alpnBuffers[i].Length = strLen;
      alpnBuffers[i].Buffer = (char*)strChars;
    }

    QUIC_ADDR addr;
    QuicAddrSetFamily(&addr, family);
    QuicAddrSetPort(&addr, port);
    if (loopback) {
      QuicAddrSetToLoopback(&addr);
    }

    QUIC_STATUS err = msquic->ListenerStart(lsn, alpnBuffers, alpnLen, &addr);

    // release
    {
      for (jsize i = 0; i < alpnLen; ++i) {
        char* strChars = alpnBuffers[i].Buffer;
        jobject str = (*env)->GetObjectArrayElement(env, alpn, i);
        (*env)->ReleaseStringUTFChars(env, str, strChars);
      }
      free(alpnBuffers);
    }

    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return;
    }
    return;
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionClose
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong connPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_connection_t* wrapper = (msquic_connection_t*)connPtr;
    HQUIC conn = wrapper->conn;

    msquic->ConnectionClose(conn);
    if (wrapper->cbRef != NULL) {
      (*env)->DeleteGlobalRef(env, wrapper->cbRef);
    }
    free(wrapper);
  }

msquic_stream_t* wrapStream(JNIEnv* env, jobject cbRef, HQUIC stream) {
  if (stream == NULL) {
    return NULL;
  }

  msquic_stream_t* wrapper = malloc(sizeof(msquic_stream_t));

  jlong wrapper2 = (*env)->CallLongMethod(env, cbRef, InternalConnectionCallback_attachOrGetStreamWrapper, (jlong)stream, (jlong)wrapper);
  if (((jlong)wrapper) != wrapper2) {
    // already exists
    free(wrapper);
    return (msquic_stream_t*)wrapper2;
  }
  // new wrapper
  memset(wrapper, 0, sizeof(msquic_stream_t));
  wrapper->stream = stream;
  return wrapper;
}

QUIC_STATUS connectionCallback(HQUIC conn, void* data, QUIC_CONNECTION_EVENT* event) {
  JNIEnv* env = getJNIEnv();
  if (env == NULL) {
    return QUIC_STATUS_PENDING;
  }
  msquic_connection_t* wrapper = (msquic_connection_t*)data;
  jobject cbRef = (jobject)wrapper->cbRef;
  jint type = event->Type;
  HQUIC peerStreamStarted = NULL;
  if (type == QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED) {
    peerStreamStarted = event->PEER_STREAM_STARTED.Stream;
  }
  jlong peerStreamStartedPtr = (jlong)wrapStream(env, cbRef, peerStreamStarted);

  return (QUIC_STATUS) (*env)->CallIntMethod(env, cbRef, InternalConnectionCallback_callback, type, peerStreamStartedPtr);
}

JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionSetCallbackHandler
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong connPtr, jobject cb) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_connection_t* wrapper = (msquic_connection_t*)connPtr;
    HQUIC conn = wrapper->conn;

    jobject cbRef = (*env)->NewGlobalRef(env, cb);

    wrapper->cbRef = cbRef;

    msquic->SetCallbackHandler(conn, connectionCallback, wrapper);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionSetConfiguration
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong connPtr, jlong confPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_connection_t* wrapper = (msquic_connection_t*)connPtr;
    HQUIC conn = wrapper->conn;
    HQUIC conf = (HQUIC)confPtr;

    msquic->ConnectionSetConfiguration(conn, conf);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionSendResumptionTicket
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong connPtr, jint flags) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_connection_t* wrapper = (msquic_connection_t*)connPtr;
    HQUIC conn = wrapper->conn;

    msquic->ConnectionSendResumptionTicket(conn, flags, 0, NULL);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamClose
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong streamPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_stream_t* wrapper = (msquic_stream_t*)streamPtr;
    HQUIC stream = wrapper->stream;

    msquic->StreamClose(stream);
    if (wrapper->cbRef != NULL) {
      (*env)->DeleteGlobalRef(env, wrapper->cbRef);
    }
    free(wrapper);
  }

QUIC_STATUS streamCallback(HQUIC stream, void* data, QUIC_STREAM_EVENT* event) {
  msquic_stream_t* wrapper = (msquic_stream_t*)data;
  jobject cbRef = wrapper->cbRef;
  jint type = event->Type;

  // release the buffer for sending
  if (type == QUIC_STREAM_EVENT_SEND_COMPLETE) {
    free(event->SEND_COMPLETE.ClientContext);
  }

  JNIEnv* env = getJNIEnv();
  return (QUIC_STATUS) (*env)->CallIntMethod(env, cbRef, InternalStreamCallback_callback, type);
}

JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamSetCallbackHandler
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong streamPtr, jobject cb) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_stream_t* wrapper = (msquic_stream_t*)streamPtr;
    HQUIC stream = wrapper->stream;

    jobject cbRef = (*env)->NewGlobalRef(env, cb);
    wrapper->cbRef = cbRef;

    msquic->SetCallbackHandler(stream, streamCallback, wrapper);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamShutdown
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong streamPtr, jint flags) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_stream_t* wrapper = (msquic_stream_t*)streamPtr;
    HQUIC stream = wrapper->stream;

    msquic->StreamShutdown(stream, flags, 0);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamSend
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong streamPtr, jint sendFlags, jobject directByteBuffer, jint off, jint len) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_stream_t* wrapper = (msquic_stream_t*)streamPtr;
    HQUIC stream = wrapper->stream;
    char* buf = (*env)->GetDirectBufferAddress(env, directByteBuffer);

    QUIC_BUFFER* qbuf = malloc(sizeof(QUIC_BUFFER));
    qbuf->Buffer = buf + off;
    qbuf->Length = len;

    QUIC_STATUS err = msquic->StreamSend(stream, qbuf, 1, sendFlags, qbuf);
    if (QUIC_FAILED(err)) {
      free(qbuf);
      throwException(env, err);
      return;
    }
    return;
  }
