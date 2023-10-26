package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@AlwaysAligned
@Include({
    "msquic.h",
    "io_vproxy_msquic_MsQuicUpcall.h",
})
public abstract class PNIQuicRegistration {
    MemorySegment Api; // QUIC_API_TABLE
    MemorySegment Reg; // HQUIC

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC reg = self->Reg;
            api->RegistrationClose(reg);
            """
    )
    @Style(Styles.critical)
    abstract void close();

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC reg = self->Reg;
                        
            api->RegistrationShutdown(reg, Flags, ErrorCode);
            """
    )
    @Style(Styles.critical)
    abstract void shutdown(int Flags, long ErrorCode);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC reg = self->Reg;
                        
            HQUIC configuration;
            QUIC_STATUS res = api->ConfigurationOpen(
                    reg, AlpnBuffers, AlpnBufferCount, Settings, sizeof(QUIC_SETTINGS), Context, &configuration);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->Api = api;
                return_->Conf = configuration;
                return return_;
            }
            return NULL;
            """
    )
    @Style(Styles.critical)
    abstract PNIQuicConfiguration openConfiguration(@Raw PNIQuicBuffer[] AlpnBuffers,
                                                    @Unsigned int AlpnBufferCount,
                                                    PNIQuicSettings Settings,
                                                    MemorySegment Context,
                                                    @Raw int[] returnStatus);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC reg = self->Reg;
                        
            HQUIC lsn;
            QUIC_STATUS res = api->ListenerOpen(reg,
                    (QUIC_LISTENER_CALLBACK_HANDLER) Handler,
                    Context, &lsn);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->Api = api;
                return_->Lsn = lsn;
                return return_;
            }
            return NULL;
            """
    )
    @Style(Styles.critical)
    abstract PNIQuicListener openListener(MemorySegment Handler, MemorySegment Context, @Raw int[] returnStatus);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC reg = self->Reg;
                        
            HQUIC conn;
            QUIC_STATUS res = api->ConnectionOpen(reg,
                    (QUIC_CONNECTION_CALLBACK_HANDLER) Handler,
                    Context, &conn);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->Api = api;
                return_->Conn = conn;
                return return_;
            }
            return NULL;
            """
    )
    @Style(Styles.critical)
    abstract PNIQuicConnection openConnection(MemorySegment Handler, MemorySegment Context, @Raw int[] returnStatus);
}

@Struct(skip = true)
@AlwaysAligned
@Include("msquic.h")
@Name("QUIC_SETTINGS")
abstract class PNIQuicSettings {
    PNIQuicSettingsIsSet IsSet;

    @Unsigned long MaxBytesPerKey;
    @Unsigned long HandshakeIdleTimeoutMs;
    @Unsigned long IdleTimeoutMs;
    @Unsigned long MtuDiscoverySearchCompleteTimeoutUs;
    @Unsigned int TlsClientMaxSendBuffer;
    @Unsigned int TlsServerMaxSendBuffer;
    @Unsigned int StreamRecvWindowDefault;
    @Unsigned int StreamRecvBufferDefault;
    @Unsigned int ConnFlowControlWindow;
    @Unsigned int MaxWorkerQueueDelayUs;
    @Unsigned int MaxStatelessOperations;
    @Unsigned int InitialWindowPackets;
    @Unsigned int SendIdleTimeoutMs;
    @Unsigned int InitialRttMs;
    @Unsigned int MaxAckDelayMs;
    @Unsigned int DisconnectTimeoutMs;
    @Unsigned int KeepAliveIntervalMs;
    @Unsigned short CongestionControlAlgorithm;
    @Unsigned short PeerBidiStreamCount;
    @Unsigned short PeerUnidiStreamCount;
    @Unsigned short MaxBindingStatelessOperations;
    @Unsigned short StatelessOperationExpirationMs;
    @Unsigned short MinimumMtu;
    @Unsigned short MaximumMtu;
    @BitField(
        name = {
            "SendBufferingEnabled", "PacingEnabled", "MigrationEnabled",
            "DatagramReceiveEnabled", "ServerResumptionLevel", "GreaseQuicBitEnabled",
            "EcnEnabled"
        },
        bit = {1, 1, 1, 1, 2, 1, 1}
    )
    @Unsigned byte Field01;
    @Unsigned byte MaxOperationsPerDrain;
    @Unsigned byte MtuDiscoveryMissingProbeCount;
    @Unsigned int DestCidUpdateIdleTimeoutMs;
    @BitField(
        name = {
            "HyStartEnabled", "EncryptionOffloadAllowed", "ReliableResetEnabled",
        },
        bit = {1, 1, 1}
    )
    @Unsigned long Flags;
}

@Struct
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicSettingsIsSet {
    @BitField(
        name = {
            /* 01 */ "MaxBytesPerKey", "HandshakeIdleTimeoutMs", "IdleTimeoutMs",
            /* 02 */ "MtuDiscoverySearchCompleteTimeoutUs",
            /* 03 */ "TlsClientMaxSendBuffer", "TlsServerMaxSendBuffer",
            /* 04 */ "StreamRecvWindowDefault", "StreamRecvBufferDefault",
            /* 05 */ "ConnFlowControlWindow", "MaxWorkerQueueDelayUs",
            /* 06 */ "MaxStatelessOperations", "InitialWindowPackets",
            /* 07 */ "SendIdleTimeoutMs", "InitialRttMs", "MaxAckDelayMs",
            /* 08 */ "DisconnectTimeoutMs", "KeepAliveIntervalMs", "CongestionControlAlgorithm",
            /* 09 */ "PeerBidiStreamCount", "PeerUnidiStreamCount", "MaxBindingStatelessOperations",
            /* 10 */ "StatelessOperationExpirationMs", "MinimumMtu", "MaximumMtu",
            /* 11 */ "SendBufferingEnabled", "PacingEnabled", "MigrationEnabled",
            /* 12 */ "DatagramReceiveEnabled", "ServerResumptionLevel", "MaxOperationsPerDrain",
            /* 13 */ "MtuDiscoveryMissingProbeCount", "DestCidUpdateIdleTimeoutMs",
            /* 14 */ "GreaseQuicBitEnabled", "EcnEnabled", "HyStartEnabled",
            /* 15 */ "EncryptionOffloadAllowed", "ReliableResetEnabled",
        },
        bit = {
            /* 01 */ 1, 1, 1,
            /* 02 */ 1,
            /* 03 */ 1, 1,
            /* 04 */ 1, 1,
            /* 05 */ 1, 1,
            /* 06 */ 1, 1,
            /* 07 */ 1, 1, 1,
            /* 08 */ 1, 1, 1,
            /* 09 */ 1, 1, 1,
            /* 10 */ 1, 1, 1,
            /* 11 */ 1, 1, 1,
            /* 12 */ 1, 1, 1,
            /* 13 */ 1, 1,
            /* 14 */ 1, 1, 1,
            /* 15 */ 1, 1,
        }
    )
    @Unsigned long IsSetFlags;
}
