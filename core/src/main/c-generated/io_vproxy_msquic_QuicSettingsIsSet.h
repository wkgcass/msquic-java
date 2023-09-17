/* DO NOT EDIT THIS FILE - it is machine generated */
/* Header for class io_vproxy_msquic_QuicSettingsIsSet */
#ifndef _Included_io_vproxy_msquic_QuicSettingsIsSet
#define _Included_io_vproxy_msquic_QuicSettingsIsSet
#ifdef __cplusplus
extern "C" {
#endif

struct QuicSettingsIsSet;
typedef struct QuicSettingsIsSet QuicSettingsIsSet;

#ifdef __cplusplus
}
#endif

#include <jni.h>
#include <pni.h>
#include "msquic.h"

#ifdef __cplusplus
extern "C" {
#endif

PNIEnvExpand(QuicSettingsIsSet, QuicSettingsIsSet *)

PNI_PACK(struct, QuicSettingsIsSet, {
    uint64_t MaxBytesPerKey : 1;
    uint64_t HandshakeIdleTimeoutMs : 1;
    uint64_t IdleTimeoutMs : 1;
    uint64_t MtuDiscoverySearchCompleteTimeoutUs : 1;
    uint64_t TlsClientMaxSendBuffer : 1;
    uint64_t TlsServerMaxSendBuffer : 1;
    uint64_t StreamRecvWindowDefault : 1;
    uint64_t StreamRecvBufferDefault : 1;
    uint64_t ConnFlowControlWindow : 1;
    uint64_t MaxWorkerQueueDelayUs : 1;
    uint64_t MaxStatelessOperations : 1;
    uint64_t InitialWindowPackets : 1;
    uint64_t SendIdleTimeoutMs : 1;
    uint64_t InitialRttMs : 1;
    uint64_t MaxAckDelayMs : 1;
    uint64_t DisconnectTimeoutMs : 1;
    uint64_t KeepAliveIntervalMs : 1;
    uint64_t CongestionControlAlgorithm : 1;
    uint64_t PeerBidiStreamCount : 1;
    uint64_t PeerUnidiStreamCount : 1;
    uint64_t MaxBindingStatelessOperations : 1;
    uint64_t StatelessOperationExpirationMs : 1;
    uint64_t MinimumMtu : 1;
    uint64_t MaximumMtu : 1;
    uint64_t SendBufferingEnabled : 1;
    uint64_t PacingEnabled : 1;
    uint64_t MigrationEnabled : 1;
    uint64_t DatagramReceiveEnabled : 1;
    uint64_t ServerResumptionLevel : 1;
    uint64_t MaxOperationsPerDrain : 1;
    uint64_t MtuDiscoveryMissingProbeCount : 1;
    uint64_t DestCidUpdateIdleTimeoutMs : 1;
    uint64_t GreaseQuicBitEnabled : 1;
    uint64_t EcnEnabled : 1;
    uint64_t HyStartEnabled : 1;
    uint64_t EncryptionOffloadAllowed : 1;
    uint64_t ReliableResetEnabled : 1;
    uint64_t : 27;

});

#ifdef __cplusplus
}
#endif
#endif // _Included_io_vproxy_msquic_QuicSettingsIsSet
// metadata.generator-version: pni 21.0.0.8
// sha256:619a35dab60a2133ec16c2b781340efc2a5cf1c195a3b35c390cf9b7c516810f