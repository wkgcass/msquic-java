#include <string.h>
#include <errno.h>
#include <msquic.h>
#include <stdio.h>
#include <string.h>
#include <arpa/inet.h>
#include "msquic_internal_Native.h"

#ifndef likely
#define likely(x)      __builtin_expect(!!(x), 1)
#endif
#ifndef unlikely
#define unlikely(x)    __builtin_expect(!!(x), 0)
#endif

JNIEXPORT jint JNICALL Java_msquic_internal_Native_QuicBufferLength
  (JNIEnv* env, jobject self, jlong qbufPtr) {
    QUIC_BUFFER* qbuf = (QUIC_BUFFER*)qbufPtr;
    return qbuf->Length;
  }

JNIEXPORT jint JNICALL Java_msquic_internal_Native_QuicBufferRead
  (JNIEnv* env, jobject self, jlong qbufPtr, jlong srcOff, jobject dstBuf, jint dstOff, jint maxReadLen) {
    QUIC_BUFFER* qbuf = (QUIC_BUFFER*)qbufPtr;
    char* src = qbuf->Buffer;
    int readLen = qbuf->Length - srcOff;
    if (readLen > maxReadLen) {
      readLen = maxReadLen;
    }
    char* dst = (*env)->GetDirectBufferAddress(env, dstBuf);
    memcpy(dst, src, readLen);
    return readLen;
  }

jclass MsQuicException;
jmethodID MsQuicException_init;

jclass InternalListenerCallback;
jmethodID InternalListenerCallback_attachOrGetConnectionWrapper;
jmethodID InternalListenerCallback_callback;

jclass InternalConnectionCallback;
jmethodID InternalConnectionCallback_attachOrGetStreamWrapper;
jmethodID InternalConnectionCallback_callback;

jclass InternalStreamCallback;
jmethodID InternalStreamCallback_callback;

jclass ClsMemoryAllocator;
jmethodID MemoryAllocator_allocate;
jmethodID MemoryAllocator_getMemory;
jmethodID MemoryAllocator_release;

jclass ClsString;

jobject memoryAllocator;

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
  (JNIEnv* env, jobject self, jobject allocator) {
    MsQuicException = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "msquic/MsQuicException"));
    MsQuicException_init = (*env)->GetMethodID(env, MsQuicException, "<init>", "(I)V");

    InternalListenerCallback = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "msquic/internal/InternalListenerCallback"));
    InternalListenerCallback_attachOrGetConnectionWrapper =
      (*env)->GetMethodID(env, InternalListenerCallback, "attachOrGetConnectionWrapper", "(JJ)J"); // (real, wrapper)->wrapper
    InternalListenerCallback_callback = (*env)->GetMethodID(env, InternalListenerCallback, "callback",
      // int type
      // long NEW_CONNECTION_connection
      // String newConnNegotiatedAlpn
      // String newConnServerName
      "(IJLjava/lang/String;Ljava/lang/String;)I");

    InternalConnectionCallback = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "msquic/internal/InternalConnectionCallback"));
    InternalConnectionCallback_attachOrGetStreamWrapper =
      (*env)->GetMethodID(env, InternalConnectionCallback, "attachOrGetStreamWrapper", "(JJ)J"); // (real, wrapper)->wrapper
    InternalConnectionCallback_callback = (*env)->GetMethodID(env, InternalConnectionCallback, "callback",
      // int type
      // long PEER_STREAM_STARTED_stream
      // boolean SHUTDOWN_COMPLETE_appCloseInProgress
      // String LOCAL_ADDRESS_CHANGED_address
      // String PEER_ADDRESS_CHANGED_address
      // boolean CONNECTED_sessionResumed
      // String CONNECTED_negotiatedAlpn
      "(IJZLjava/lang/String;Ljava/lang/String;ZLjava/lang/String;)I");

    InternalStreamCallback = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "msquic/internal/InternalStreamCallback"));
    InternalStreamCallback_callback = (*env)->GetMethodID(env, InternalStreamCallback, "callback",
      // int type
      // long RECEIVE_totalBufferLengthPtr
      // long RECEIVE_absoluteOffset
      // long RECEIVE_totalBufferLength
      // long[] RECEIVE_bufferPtrs
      // int RECEIVE_receiveFlags
      "(IJJJ[JI)I");

    ClsMemoryAllocator = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "msquic/MemoryAllocator"));
    MemoryAllocator_allocate = (*env)->GetMethodID(env, ClsMemoryAllocator, "allocate", "(I)Ljava/lang/Object;");
    MemoryAllocator_getMemory = (*env)->GetMethodID(env, ClsMemoryAllocator, "getMemory", "(Ljava/lang/Object;)Ljava/nio/ByteBuffer;");
    MemoryAllocator_release = (*env)->GetMethodID(env, ClsMemoryAllocator, "release", "(Ljava/lang/Object;)V");

    ClsString = (jclass)(*env)->NewGlobalRef(env, (jobject)(*env)->FindClass(env, "java/lang/String"));

    if (allocator == NULL) {
      memoryAllocator = NULL;
    } else {
      memoryAllocator = (*env)->NewGlobalRef(env, allocator);
    }

    JavaVM* jvm;
    jint err = (*env)->GetJavaVM(env, &jvm);
    if (err != JNI_OK) {
      printf("GetJavaVM failed with %d\n", err);
      fflush(stdout);
      return;
    }
    MsQuicJavaInit(jvm, attachThread, detachThread);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_MsQuicJavaRelease
  (JNIEnv* env, jobject self) {
    (*env)->DeleteGlobalRef(env, (jobject)MsQuicException);
    (*env)->DeleteGlobalRef(env, (jobject)InternalListenerCallback);
    (*env)->DeleteGlobalRef(env, (jobject)InternalConnectionCallback);
    (*env)->DeleteGlobalRef(env, (jobject)InternalStreamCallback);
    (*env)->DeleteGlobalRef(env, (jobject)ClsMemoryAllocator);
    (*env)->DeleteGlobalRef(env, (jobject)ClsString);

    if (memoryAllocator) {
      (*env)->DeleteGlobalRef(env, memoryAllocator);
    }
  }

void throwException(JNIEnv* env, QUIC_STATUS err) {
  jobject exObj = (*env)->NewObject(env, MsQuicException, MsQuicException_init, (jint)err);
  (*env)->Throw(env, exObj);
}

void* allocateMemory(JNIEnv* env, size_t size, jobject* outMemoryObject) {
  if (memoryAllocator != 0) {
    jobject memoryObject = (*env)->CallObjectMethod(env, memoryAllocator, MemoryAllocator_allocate, (jint)size);
    memoryObject = (*env)->NewGlobalRef(env, memoryObject);
    *outMemoryObject = memoryObject;
    return (void*)(*env)->GetDirectBufferAddress(env, memoryObject);
  } else {
    *outMemoryObject = NULL;
    return malloc(size);
  }
}

void releaseMemory(JNIEnv* env, void* ptr, jobject memoryObject) {
  if (memoryObject != NULL) {
    (*env)->CallVoidMethod(env, memoryAllocator, MemoryAllocator_release, memoryObject);
    (*env)->DeleteGlobalRef(env, memoryObject);
  } else {
    free(ptr);
  }
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

JNIEXPORT void JNICALL Java_msquic_internal_Native_RegistrationShutdown
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong regPtr, jint flags, jlong errorCode) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    HQUIC reg = (HQUIC)regPtr;

    msquic->RegistrationShutdown(reg, flags, errorCode);
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

JNIEXPORT void JNICALL Java_msquic_internal_Native_ConfigurationLoadAsClient
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong confPtr, jboolean noCertValidation) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    HQUIC conf = (HQUIC)confPtr;

    QUIC_CREDENTIAL_CONFIG credConfig;
    memset(&credConfig, 0, sizeof(QUIC_CREDENTIAL_CONFIG));
    credConfig.Type = QUIC_CREDENTIAL_TYPE_NONE;
    credConfig.Flags = QUIC_CREDENTIAL_FLAG_CLIENT;
    if (noCertValidation) {
      credConfig.Flags |= QUIC_CREDENTIAL_FLAG_NO_CERTIFICATE_VALIDATION;
    }

    QUIC_STATUS err = msquic->ConfigurationLoadCredential(conf, &credConfig);

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
  jobject memoryObject;
} msquic_listener_t;

typedef struct st_msquic_connection {
  jobject cbRef;
  HQUIC conn;
  jobject memoryObject;
} msquic_connection_t;

typedef struct st_msquic_stream {
  jobject cbRef;
  HQUIC stream;
  jobject memoryObject;
} msquic_stream_t;

jstring addressToString(JNIEnv* env, const QUIC_ADDR* addr) {
  QUIC_ADDRESS_FAMILY family = QuicAddrGetFamily(addr);
  int port = QuicAddrGetPort(addr);
  // see msquic_linux.h
  char addressChars[39 + 1];
  if (family == QUIC_ADDRESS_FAMILY_INET) {
    inet_ntop(AF_INET, &addr->Ipv4.sin_addr, addressChars, sizeof(char) * 40);
  } else {
    inet_ntop(AF_INET6, &addr->Ipv6.sin6_addr, addressChars, sizeof(char) * 40);
  }
  char l4addrChars[1 + 39 + 1 + 1 + 5 + 1];
  if (family == QUIC_ADDRESS_FAMILY_INET) {
    sprintf(l4addrChars, "%s:%d", addressChars, port);
  } else {
    sprintf(l4addrChars, "[%s]:%d", addressChars, port);
  }
  return (*env)->NewStringUTF(env, l4addrChars);
}

jstring buildAlpnString(JNIEnv* env, uint8_t len, const uint8_t* negotiatedAlpn) {
  char foo[256];
  int i = 0;
  for (;i < len; ++i) {
    foo[i] = negotiatedAlpn[i];
  }
  foo[i] = '\0';
  jstring alpn = (*env)->NewStringUTF(env, foo);
  return alpn;
}

msquic_connection_t* wrapConnectionOfListener(JNIEnv* env, jobject cbRef, HQUIC conn) {
  if (conn == NULL) {
    return NULL;
  }

  jobject memoryObject;
  msquic_connection_t* wrapper = allocateMemory(env, sizeof(msquic_connection_t), &memoryObject);

  jlong wrapper2 = (*env)->CallLongMethod(env, cbRef, InternalListenerCallback_attachOrGetConnectionWrapper, (jlong)conn, (jlong)wrapper);
  if (((jlong)wrapper) != wrapper2) {
    // already exists
    releaseMemory(env, wrapper, memoryObject);
    return (msquic_connection_t*)wrapper2;
  }
  // new wrapper
  memset(wrapper, 0, sizeof(msquic_connection_t));
  wrapper->conn = conn;
  wrapper->memoryObject = memoryObject;
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
  jlong newConnectionPtr = 0;
  jstring negotiatedAlpn = NULL;
  jstring serverName = NULL;
  if (type == QUIC_LISTENER_EVENT_NEW_CONNECTION) {
    HQUIC newConnection = event->NEW_CONNECTION.Connection;
    newConnectionPtr = (jlong)wrapConnectionOfListener(env, cbRef, newConnection);
    negotiatedAlpn = buildAlpnString(env,
      event->NEW_CONNECTION.Info->NegotiatedAlpnLength,
      event->NEW_CONNECTION.Info->NegotiatedAlpn
    );
    serverName = (*env)->NewStringUTF(env, event->NEW_CONNECTION.Info->ServerName);
  }

  return (QUIC_STATUS) (*env)->CallIntMethod(env, cbRef, InternalListenerCallback_callback, type,
    newConnectionPtr, negotiatedAlpn, serverName);
}

JNIEXPORT jlong JNICALL Java_msquic_internal_Native_ListenerOpen
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong regPtr, jobject cb) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    HQUIC reg = (HQUIC)regPtr;

    jobject cbRef = (*env)->NewGlobalRef(env, cb);
    jobject memoryObject;
    msquic_listener_t* wrapper = allocateMemory(env, sizeof(msquic_listener_t), &memoryObject);
    wrapper->memoryObject = memoryObject;
    wrapper->cbRef = cbRef;

    HQUIC lsn;
    QUIC_STATUS err = msquic->ListenerOpen(reg, listenerCallback, wrapper, &lsn);
    if (QUIC_FAILED(err)) {
      // release ref
      (*env)->DeleteGlobalRef(env, wrapper->cbRef);
      releaseMemory(env, wrapper, memoryObject);
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
    releaseMemory(env, wrapper, wrapper->memoryObject);
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

msquic_stream_t* wrapStream(JNIEnv* env, jobject cbRef, HQUIC stream) {
  if (stream == NULL) {
    return NULL;
  }

  jobject memoryObject;
  msquic_stream_t* wrapper = allocateMemory(env, sizeof(msquic_stream_t), &memoryObject);

  jlong wrapper2 = (*env)->CallLongMethod(env, cbRef, InternalConnectionCallback_attachOrGetStreamWrapper, (jlong)stream, (jlong)wrapper);
  if (((jlong)wrapper) != wrapper2) {
    // already exists
    releaseMemory(env, wrapper, memoryObject);
    return (msquic_stream_t*)wrapper2;
  }
  // new wrapper
  memset(wrapper, 0, sizeof(msquic_stream_t));
  wrapper->stream = stream;
  wrapper->memoryObject = memoryObject;
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
  jlong PEER_STREAM_STARTED_stream = (jlong)wrapStream(env, cbRef, peerStreamStarted);
  jboolean SHUTDOWN_COMPLETE_appCloseInProgress = 0;
  if (type == QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE) {
    SHUTDOWN_COMPLETE_appCloseInProgress = event->SHUTDOWN_COMPLETE.AppCloseInProgress;
  }
  jstring LOCAL_ADDRESS_CHANGED_address = NULL;
  if (type == QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED) {
    LOCAL_ADDRESS_CHANGED_address = addressToString(env, event->LOCAL_ADDRESS_CHANGED.Address);
  }
  jstring PEER_ADDRESS_CHANGED_address = NULL;
  if (type == QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED) {
    PEER_ADDRESS_CHANGED_address = addressToString(env, event->PEER_ADDRESS_CHANGED.Address);
  }
  jboolean CONNECTED_sessionResumed = 0;
  jstring CONNECTED_negotiatedAlpn = NULL;
  if (type == QUIC_CONNECTION_EVENT_CONNECTED) {
    CONNECTED_sessionResumed = event->CONNECTED.SessionResumed;
    CONNECTED_negotiatedAlpn = buildAlpnString(env, event->CONNECTED.NegotiatedAlpnLength, event->CONNECTED.NegotiatedAlpn);
  }

  return (QUIC_STATUS) (*env)->CallIntMethod(env, cbRef, InternalConnectionCallback_callback, type,
    PEER_STREAM_STARTED_stream,
    SHUTDOWN_COMPLETE_appCloseInProgress,
    LOCAL_ADDRESS_CHANGED_address,
    PEER_ADDRESS_CHANGED_address,
    CONNECTED_sessionResumed,
    CONNECTED_negotiatedAlpn
  );
}

JNIEXPORT jlong JNICALL Java_msquic_internal_Native_ConnectionOpen
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong regPtr, jobject cb) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    HQUIC reg = (HQUIC)regPtr;

    jobject memoryObject;
    msquic_connection_t* wrapper = allocateMemory(env, sizeof(msquic_connection_t), &memoryObject);
    HQUIC conn;
    QUIC_STATUS err = msquic->ConnectionOpen(reg, connectionCallback, wrapper, &conn);
    if (QUIC_FAILED(err)) {
      releaseMemory(env, wrapper, memoryObject);
      throwException(env, err);
      return 0;
    }
    wrapper->conn = conn;
    wrapper->memoryObject = memoryObject;
    wrapper->cbRef = (*env)->NewGlobalRef(env, cb);
    return (jlong)wrapper;
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
    releaseMemory(env, wrapper, wrapper->memoryObject);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionStart
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong connPtr, jlong confPtr, jint family, jstring address, jint port) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_connection_t* wrapper = (msquic_connection_t*)connPtr;
    HQUIC conn = wrapper->conn;
    HQUIC conf = (HQUIC)confPtr;

    const char* addressChars = (*env)->GetStringUTFChars(env, address, NULL);

    QUIC_STATUS err = msquic->ConnectionStart(conn, conf, family, addressChars, port);

    { // release
      (*env)->ReleaseStringUTFChars(env, address, addressChars);
    }

    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return;
    }
    return;
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_ConnectionShutdown
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong connPtr, jint flags, jlong errorCode) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_connection_t* wrapper = (msquic_connection_t*)connPtr;
    HQUIC conn = wrapper->conn;

    msquic->ConnectionShutdown(conn, flags, errorCode);
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

JNIEXPORT jstring JNICALL Java_msquic_internal_Native_GetConnectionLocalAddress
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong connPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_connection_t* wrapper = (msquic_connection_t*)connPtr;
    HQUIC conn = wrapper->conn;

    QUIC_ADDR addr;
    uint32_t size = sizeof(QUIC_ADDR);
    QUIC_STATUS err = msquic->GetParam(conn, QUIC_PARAM_LEVEL_CONNECTION, QUIC_PARAM_CONN_LOCAL_ADDRESS, &size, &addr);
    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return NULL;
    }
    return addressToString(env, &addr);
  }

JNIEXPORT jstring JNICALL Java_msquic_internal_Native_GetConnectionRemoteAddress
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong connPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_connection_t* wrapper = (msquic_connection_t*)connPtr;
    HQUIC conn = wrapper->conn;

    QUIC_ADDR addr;
    uint32_t size = sizeof(QUIC_ADDR);
    QUIC_STATUS err = msquic->GetParam(conn, QUIC_PARAM_LEVEL_CONNECTION, QUIC_PARAM_CONN_REMOTE_ADDRESS, &size, &addr);
    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return NULL;
    }
    return addressToString(env, &addr);
  }

typedef struct st_qbuf_wrapper {
  QUIC_BUFFER qbuf;
  jobject memoryObject;
} msquic_qbuf_t;

QUIC_STATUS streamCallback(HQUIC stream, void* data, QUIC_STREAM_EVENT* event) {
  msquic_stream_t* wrapper = (msquic_stream_t*)data;
  jobject cbRef = wrapper->cbRef;
  jint type = event->Type;

  JNIEnv* env = getJNIEnv();

  // release the buffer for sending
  if (type == QUIC_STREAM_EVENT_SEND_COMPLETE) {
    msquic_qbuf_t* qbufwrapper = event->SEND_COMPLETE.ClientContext;
    releaseMemory(env, qbufwrapper, qbufwrapper->memoryObject);
  }

  jlong RECEIVE_totalBufferLengthPtr = 0;
  jlong RECEIVE_absoluteOffset = 0;
  jlong RECEIVE_totalBufferLength = 0;
  jlongArray RECEIVE_bufferPtrs = NULL;
  jint RECEIVE_receiveFlags = 0;
  if (type == QUIC_STREAM_EVENT_RECEIVE) {
    RECEIVE_totalBufferLengthPtr = (jlong)(&(event->RECEIVE.TotalBufferLength));
    RECEIVE_absoluteOffset = event->RECEIVE.AbsoluteOffset;
    RECEIVE_totalBufferLength = event->RECEIVE.TotalBufferLength;
    int bufcount = event->RECEIVE.BufferCount;
    RECEIVE_bufferPtrs = (*env)->NewLongArray(env, bufcount);
    long* foo = malloc(bufcount * sizeof(long));
    for (int i = 0; i < bufcount; ++i) {
      foo[i] = (jlong)(&event->RECEIVE.Buffers[i]);
    }
    (*env)->SetLongArrayRegion(env, RECEIVE_bufferPtrs, 0, bufcount, foo);
    free(foo);
    RECEIVE_receiveFlags = event->RECEIVE.Flags;
  }

  return (QUIC_STATUS) (*env)->CallIntMethod(env, cbRef, InternalStreamCallback_callback, type,
    RECEIVE_totalBufferLengthPtr,
    RECEIVE_absoluteOffset,
    RECEIVE_totalBufferLength,
    RECEIVE_bufferPtrs,
    RECEIVE_receiveFlags
  );
}

JNIEXPORT jlong JNICALL Java_msquic_internal_Native_StreamOpen
  (JNIEnv* env, jobject self, jobject connCB, jlong msquicPtr, jlong connPtr, jint streamOpenFlags, jobject cb) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_connection_t* conn_wrapper = (msquic_connection_t*)connPtr;
    HQUIC conn = conn_wrapper->conn;

    jobject memoryObject;
    msquic_stream_t* wrapper = allocateMemory(env, sizeof(msquic_stream_t), &memoryObject);
    HQUIC stream;
    QUIC_STATUS err = msquic->StreamOpen(conn, streamOpenFlags, streamCallback, wrapper, &stream);
    if (QUIC_FAILED(err)) {
      releaseMemory(env, wrapper, memoryObject);
      throwException(env, err);
      return 0;
    }
    wrapper->stream = stream;
    wrapper->memoryObject = memoryObject;
    wrapper->cbRef = (*env)->NewGlobalRef(env, cb);

    jlong wrapper2 = (*env)->CallLongMethod(env, connCB, InternalConnectionCallback_attachOrGetStreamWrapper, (jlong)stream, (jlong)wrapper);
    if (unlikely(wrapper2 != (jlong)wrapper)) {
      printf("attaching in StreamOpen but already exists: real: %lu, wrapper: %lu", (long)stream, (long)wrapper);
      fflush(stdout);
    }

    return (jlong)wrapper;
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
    releaseMemory(env, wrapper, wrapper->memoryObject);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamStart
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong streamPtr, jint streamStartFlags) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_stream_t* wrapper = (msquic_stream_t*)streamPtr;
    HQUIC stream = wrapper->stream;

    QUIC_STATUS err = msquic->StreamStart(stream, streamStartFlags);
    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return;
    }
    return;
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

    jobject memoryObject;
    msquic_qbuf_t* qbufwrapper = allocateMemory(env, sizeof(msquic_qbuf_t), &memoryObject);
    QUIC_BUFFER* qbuf = &qbufwrapper->qbuf;
    qbufwrapper->memoryObject = memoryObject;
    qbuf->Buffer = buf + off;
    qbuf->Length = len;

    QUIC_STATUS err = msquic->StreamSend(stream, qbuf, 1, sendFlags, qbufwrapper);
    if (QUIC_FAILED(err)) {
      releaseMemory(env, qbufwrapper, memoryObject);
      throwException(env, err);
      return;
    }
    return;
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamReceiveComplete
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong streamPtr, jlong consumedLen) {
    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_stream_t* wrapper = (msquic_stream_t*)streamPtr;
    HQUIC stream = wrapper->stream;

    msquic->StreamReceiveComplete(stream, consumedLen);
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamReceiveSetEnabled
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong streamPtr, jboolean enabled) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_stream_t* wrapper = (msquic_stream_t*)streamPtr;
    HQUIC stream = wrapper->stream;

    QUIC_STATUS err = msquic->StreamReceiveSetEnabled(stream, enabled);
    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return;
    }
    return;
  }

JNIEXPORT void JNICALL Java_msquic_internal_Native_StreamReceiveSetTotalLength
  (JNIEnv* env, jobject self, jlong totalLengthPtrLong, jlong len) {
    uint64_t* totalLengthPtr = (uint64_t*)totalLengthPtrLong;
    *totalLengthPtr = len;
  }

JNIEXPORT jlong JNICALL Java_msquic_internal_Native_GetStreamId
  (JNIEnv* env, jobject self, jlong msquicPtr, jlong streamPtr) {

    QUIC_API_TABLE* msquic = (QUIC_API_TABLE*)msquicPtr;
    msquic_stream_t* wrapper = (msquic_stream_t*)streamPtr;
    HQUIC stream = wrapper->stream;

    QUIC_UINT62 streamId;
    uint32_t size = sizeof(QUIC_UINT62);
    QUIC_STATUS err = msquic->GetParam(stream, QUIC_PARAM_LEVEL_STREAM, QUIC_PARAM_STREAM_ID, &size, &streamId);
    if (QUIC_FAILED(err)) {
      throwException(env, err);
      return 0;
    }
    return streamId;
  }
