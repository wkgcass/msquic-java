package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@AlwaysAligned
@Include({
    "msquic.h",
    "io_vproxy_msquic_MsQuicUpcall.h",
})
public abstract class PNIQuicRegistration extends PNIQuicObjectBase {
    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->SUPER.Api;
            HQUIC reg = self->SUPER.Handle;
            api->RegistrationClose(reg);
            """
    )
    @Style(Styles.critical)
    abstract void close();

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->SUPER.Api;
            HQUIC reg = self->SUPER.Handle;
                        
            api->RegistrationShutdown(reg, Flags, ErrorCode);
            """
    )
    @Style(Styles.critical)
    abstract void shutdown(int Flags, long ErrorCode);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->SUPER.Api;
            HQUIC reg = self->SUPER.Handle;
                        
            HQUIC configuration;
            QUIC_STATUS res = api->ConfigurationOpen(
                    reg, AlpnBuffers, AlpnBufferCount, Settings, sizeof(QUIC_SETTINGS), Context, &configuration);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->SUPER.Api = api;
                return_->SUPER.Handle = configuration;
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
            QUIC_API_TABLE* api = self->SUPER.Api;
            HQUIC reg = self->SUPER.Handle;
                        
            HQUIC lsn;
            QUIC_STATUS res = api->ListenerOpen(reg,
                    (QUIC_LISTENER_CALLBACK_HANDLER) Handler,
                    Context, &lsn);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->SUPER.Api = api;
                return_->SUPER.Handle = lsn;
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
            QUIC_API_TABLE* api = self->SUPER.Api;
            HQUIC reg = self->SUPER.Handle;
                        
            HQUIC conn;
            QUIC_STATUS res = api->ConnectionOpen(reg,
                    (QUIC_CONNECTION_CALLBACK_HANDLER) Handler,
                    Context, &conn);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->SUPER.Api = api;
                return_->SUPER.Handle = conn;
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
    @Bit({
        @Bit.Field(name = "SendBufferingEnabled", bits = 1, bool = true),
        @Bit.Field(name = "PacingEnabled", bits = 1, bool = true),
        @Bit.Field(name = "MigrationEnabled", bits = 1, bool = true),
        @Bit.Field(name = "DatagramReceiveEnabled", bits = 1, bool = true),
        @Bit.Field(name = "ServerResumptionLevel", bits = 2),
        @Bit.Field(name = "GreaseQuicBitEnabled", bits = 1, bool = true),
        @Bit.Field(name = "EcnEnabled", bits = 1, bool = true),
    })
    @Unsigned byte Field01;
    @Unsigned byte MaxOperationsPerDrain;
    @Unsigned byte MtuDiscoveryMissingProbeCount;
    @Unsigned int DestCidUpdateIdleTimeoutMs;
    @Bit({
        @Bit.Field(name = "HyStartEnabled", bits = 1, bool = true),
        @Bit.Field(name = "EncryptionOffloadAllowed", bits = 1, bool = true),
        @Bit.Field(name = "ReliableResetEnabled", bits = 1, bool = true),
    })
    @Unsigned long Flags;
}

@Struct
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicSettingsIsSet {
    @Bit({
        @Bit.Field(name = "MaxBytesPerKey", bits = 1, bool = true),
        @Bit.Field(name = "HandshakeIdleTimeoutMs", bits = 1, bool = true),
        @Bit.Field(name = "IdleTimeoutMs", bits = 1, bool = true),
        @Bit.Field(name = "MtuDiscoverySearchCompleteTimeoutUs", bits = 1, bool = true),
        @Bit.Field(name = "TlsClientMaxSendBuffer", bits = 1, bool = true),
        @Bit.Field(name = "TlsServerMaxSendBuffer", bits = 1, bool = true),
        @Bit.Field(name = "StreamRecvWindowDefault", bits = 1, bool = true),
        @Bit.Field(name = "StreamRecvBufferDefault", bits = 1, bool = true),
        @Bit.Field(name = "ConnFlowControlWindow", bits = 1, bool = true),
        @Bit.Field(name = "MaxWorkerQueueDelayUs", bits = 1, bool = true),
        @Bit.Field(name = "MaxStatelessOperations", bits = 1, bool = true),
        @Bit.Field(name = "InitialWindowPackets", bits = 1, bool = true),
        @Bit.Field(name = "SendIdleTimeoutMs", bits = 1, bool = true),
        @Bit.Field(name = "InitialRttMs", bits = 1, bool = true),
        @Bit.Field(name = "MaxAckDelayMs", bits = 1, bool = true),
        @Bit.Field(name = "DisconnectTimeoutMs", bits = 1, bool = true),
        @Bit.Field(name = "KeepAliveIntervalMs", bits = 1, bool = true),
        @Bit.Field(name = "CongestionControlAlgorithm", bits = 1, bool = true),
        @Bit.Field(name = "PeerBidiStreamCount", bits = 1, bool = true),
        @Bit.Field(name = "PeerUnidiStreamCount", bits = 1, bool = true),
        @Bit.Field(name = "MaxBindingStatelessOperations", bits = 1, bool = true),
        @Bit.Field(name = "StatelessOperationExpirationMs", bits = 1, bool = true),
        @Bit.Field(name = "MinimumMtu", bits = 1, bool = true),
        @Bit.Field(name = "MaximumMtu", bits = 1, bool = true),
        @Bit.Field(name = "SendBufferingEnabled", bits = 1, bool = true),
        @Bit.Field(name = "PacingEnabled", bits = 1, bool = true),
        @Bit.Field(name = "MigrationEnabled", bits = 1, bool = true),
        @Bit.Field(name = "DatagramReceiveEnabled", bits = 1, bool = true),
        @Bit.Field(name = "ServerResumptionLevel", bits = 1, bool = true),
        @Bit.Field(name = "MaxOperationsPerDrain", bits = 1, bool = true),
        @Bit.Field(name = "MtuDiscoveryMissingProbeCount", bits = 1, bool = true),
        @Bit.Field(name = "DestCidUpdateIdleTimeoutMs", bits = 1, bool = true),
        @Bit.Field(name = "GreaseQuicBitEnabled", bits = 1, bool = true),
        @Bit.Field(name = "EcnEnabled", bits = 1, bool = true),
        @Bit.Field(name = "HyStartEnabled", bits = 1, bool = true),
        @Bit.Field(name = "EncryptionOffloadAllowed", bits = 1, bool = true),
        @Bit.Field(name = "ReliableResetEnabled", bits = 1, bool = true),
    })
    @Unsigned long IsSetFlags;
}
