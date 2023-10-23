package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicSettingsIsSet extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("IsSetFlags")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandle IsSetFlagsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("IsSetFlags")
    );

    public long getIsSetFlags() {
        return (long) IsSetFlagsVH.get(MEMORY);
    }

    public void setIsSetFlags(long IsSetFlags) {
        IsSetFlagsVH.set(MEMORY, IsSetFlags);
    }

    public long getMaxBytesPerKey() {
        var N = getIsSetFlags();
        return (long) ((N >> 0) & 0b1);
    }

    public void setMaxBytesPerKey(long MaxBytesPerKey) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 0);
        MaxBytesPerKey = (long) (MaxBytesPerKey & 0b1);
        MaxBytesPerKey = (long) (MaxBytesPerKey << 0);
        N = (long) ((N & ~MASK) | (MaxBytesPerKey & MASK));
        setIsSetFlags(N);
    }

    public long getHandshakeIdleTimeoutMs() {
        var N = getIsSetFlags();
        return (long) ((N >> 1) & 0b1);
    }

    public void setHandshakeIdleTimeoutMs(long HandshakeIdleTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 1);
        HandshakeIdleTimeoutMs = (long) (HandshakeIdleTimeoutMs & 0b1);
        HandshakeIdleTimeoutMs = (long) (HandshakeIdleTimeoutMs << 1);
        N = (long) ((N & ~MASK) | (HandshakeIdleTimeoutMs & MASK));
        setIsSetFlags(N);
    }

    public long getIdleTimeoutMs() {
        var N = getIsSetFlags();
        return (long) ((N >> 2) & 0b1);
    }

    public void setIdleTimeoutMs(long IdleTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 2);
        IdleTimeoutMs = (long) (IdleTimeoutMs & 0b1);
        IdleTimeoutMs = (long) (IdleTimeoutMs << 2);
        N = (long) ((N & ~MASK) | (IdleTimeoutMs & MASK));
        setIsSetFlags(N);
    }

    public long getMtuDiscoverySearchCompleteTimeoutUs() {
        var N = getIsSetFlags();
        return (long) ((N >> 3) & 0b1);
    }

    public void setMtuDiscoverySearchCompleteTimeoutUs(long MtuDiscoverySearchCompleteTimeoutUs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 3);
        MtuDiscoverySearchCompleteTimeoutUs = (long) (MtuDiscoverySearchCompleteTimeoutUs & 0b1);
        MtuDiscoverySearchCompleteTimeoutUs = (long) (MtuDiscoverySearchCompleteTimeoutUs << 3);
        N = (long) ((N & ~MASK) | (MtuDiscoverySearchCompleteTimeoutUs & MASK));
        setIsSetFlags(N);
    }

    public long getTlsClientMaxSendBuffer() {
        var N = getIsSetFlags();
        return (long) ((N >> 4) & 0b1);
    }

    public void setTlsClientMaxSendBuffer(long TlsClientMaxSendBuffer) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 4);
        TlsClientMaxSendBuffer = (long) (TlsClientMaxSendBuffer & 0b1);
        TlsClientMaxSendBuffer = (long) (TlsClientMaxSendBuffer << 4);
        N = (long) ((N & ~MASK) | (TlsClientMaxSendBuffer & MASK));
        setIsSetFlags(N);
    }

    public long getTlsServerMaxSendBuffer() {
        var N = getIsSetFlags();
        return (long) ((N >> 5) & 0b1);
    }

    public void setTlsServerMaxSendBuffer(long TlsServerMaxSendBuffer) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 5);
        TlsServerMaxSendBuffer = (long) (TlsServerMaxSendBuffer & 0b1);
        TlsServerMaxSendBuffer = (long) (TlsServerMaxSendBuffer << 5);
        N = (long) ((N & ~MASK) | (TlsServerMaxSendBuffer & MASK));
        setIsSetFlags(N);
    }

    public long getStreamRecvWindowDefault() {
        var N = getIsSetFlags();
        return (long) ((N >> 6) & 0b1);
    }

    public void setStreamRecvWindowDefault(long StreamRecvWindowDefault) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 6);
        StreamRecvWindowDefault = (long) (StreamRecvWindowDefault & 0b1);
        StreamRecvWindowDefault = (long) (StreamRecvWindowDefault << 6);
        N = (long) ((N & ~MASK) | (StreamRecvWindowDefault & MASK));
        setIsSetFlags(N);
    }

    public long getStreamRecvBufferDefault() {
        var N = getIsSetFlags();
        return (long) ((N >> 7) & 0b1);
    }

    public void setStreamRecvBufferDefault(long StreamRecvBufferDefault) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 7);
        StreamRecvBufferDefault = (long) (StreamRecvBufferDefault & 0b1);
        StreamRecvBufferDefault = (long) (StreamRecvBufferDefault << 7);
        N = (long) ((N & ~MASK) | (StreamRecvBufferDefault & MASK));
        setIsSetFlags(N);
    }

    public long getConnFlowControlWindow() {
        var N = getIsSetFlags();
        return (long) ((N >> 8) & 0b1);
    }

    public void setConnFlowControlWindow(long ConnFlowControlWindow) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 8);
        ConnFlowControlWindow = (long) (ConnFlowControlWindow & 0b1);
        ConnFlowControlWindow = (long) (ConnFlowControlWindow << 8);
        N = (long) ((N & ~MASK) | (ConnFlowControlWindow & MASK));
        setIsSetFlags(N);
    }

    public long getMaxWorkerQueueDelayUs() {
        var N = getIsSetFlags();
        return (long) ((N >> 9) & 0b1);
    }

    public void setMaxWorkerQueueDelayUs(long MaxWorkerQueueDelayUs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 9);
        MaxWorkerQueueDelayUs = (long) (MaxWorkerQueueDelayUs & 0b1);
        MaxWorkerQueueDelayUs = (long) (MaxWorkerQueueDelayUs << 9);
        N = (long) ((N & ~MASK) | (MaxWorkerQueueDelayUs & MASK));
        setIsSetFlags(N);
    }

    public long getMaxStatelessOperations() {
        var N = getIsSetFlags();
        return (long) ((N >> 10) & 0b1);
    }

    public void setMaxStatelessOperations(long MaxStatelessOperations) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 10);
        MaxStatelessOperations = (long) (MaxStatelessOperations & 0b1);
        MaxStatelessOperations = (long) (MaxStatelessOperations << 10);
        N = (long) ((N & ~MASK) | (MaxStatelessOperations & MASK));
        setIsSetFlags(N);
    }

    public long getInitialWindowPackets() {
        var N = getIsSetFlags();
        return (long) ((N >> 11) & 0b1);
    }

    public void setInitialWindowPackets(long InitialWindowPackets) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 11);
        InitialWindowPackets = (long) (InitialWindowPackets & 0b1);
        InitialWindowPackets = (long) (InitialWindowPackets << 11);
        N = (long) ((N & ~MASK) | (InitialWindowPackets & MASK));
        setIsSetFlags(N);
    }

    public long getSendIdleTimeoutMs() {
        var N = getIsSetFlags();
        return (long) ((N >> 12) & 0b1);
    }

    public void setSendIdleTimeoutMs(long SendIdleTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 12);
        SendIdleTimeoutMs = (long) (SendIdleTimeoutMs & 0b1);
        SendIdleTimeoutMs = (long) (SendIdleTimeoutMs << 12);
        N = (long) ((N & ~MASK) | (SendIdleTimeoutMs & MASK));
        setIsSetFlags(N);
    }

    public long getInitialRttMs() {
        var N = getIsSetFlags();
        return (long) ((N >> 13) & 0b1);
    }

    public void setInitialRttMs(long InitialRttMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 13);
        InitialRttMs = (long) (InitialRttMs & 0b1);
        InitialRttMs = (long) (InitialRttMs << 13);
        N = (long) ((N & ~MASK) | (InitialRttMs & MASK));
        setIsSetFlags(N);
    }

    public long getMaxAckDelayMs() {
        var N = getIsSetFlags();
        return (long) ((N >> 14) & 0b1);
    }

    public void setMaxAckDelayMs(long MaxAckDelayMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 14);
        MaxAckDelayMs = (long) (MaxAckDelayMs & 0b1);
        MaxAckDelayMs = (long) (MaxAckDelayMs << 14);
        N = (long) ((N & ~MASK) | (MaxAckDelayMs & MASK));
        setIsSetFlags(N);
    }

    public long getDisconnectTimeoutMs() {
        var N = getIsSetFlags();
        return (long) ((N >> 15) & 0b1);
    }

    public void setDisconnectTimeoutMs(long DisconnectTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 15);
        DisconnectTimeoutMs = (long) (DisconnectTimeoutMs & 0b1);
        DisconnectTimeoutMs = (long) (DisconnectTimeoutMs << 15);
        N = (long) ((N & ~MASK) | (DisconnectTimeoutMs & MASK));
        setIsSetFlags(N);
    }

    public long getKeepAliveIntervalMs() {
        var N = getIsSetFlags();
        return (long) ((N >> 16) & 0b1);
    }

    public void setKeepAliveIntervalMs(long KeepAliveIntervalMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 16);
        KeepAliveIntervalMs = (long) (KeepAliveIntervalMs & 0b1);
        KeepAliveIntervalMs = (long) (KeepAliveIntervalMs << 16);
        N = (long) ((N & ~MASK) | (KeepAliveIntervalMs & MASK));
        setIsSetFlags(N);
    }

    public long getCongestionControlAlgorithm() {
        var N = getIsSetFlags();
        return (long) ((N >> 17) & 0b1);
    }

    public void setCongestionControlAlgorithm(long CongestionControlAlgorithm) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 17);
        CongestionControlAlgorithm = (long) (CongestionControlAlgorithm & 0b1);
        CongestionControlAlgorithm = (long) (CongestionControlAlgorithm << 17);
        N = (long) ((N & ~MASK) | (CongestionControlAlgorithm & MASK));
        setIsSetFlags(N);
    }

    public long getPeerBidiStreamCount() {
        var N = getIsSetFlags();
        return (long) ((N >> 18) & 0b1);
    }

    public void setPeerBidiStreamCount(long PeerBidiStreamCount) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 18);
        PeerBidiStreamCount = (long) (PeerBidiStreamCount & 0b1);
        PeerBidiStreamCount = (long) (PeerBidiStreamCount << 18);
        N = (long) ((N & ~MASK) | (PeerBidiStreamCount & MASK));
        setIsSetFlags(N);
    }

    public long getPeerUnidiStreamCount() {
        var N = getIsSetFlags();
        return (long) ((N >> 19) & 0b1);
    }

    public void setPeerUnidiStreamCount(long PeerUnidiStreamCount) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 19);
        PeerUnidiStreamCount = (long) (PeerUnidiStreamCount & 0b1);
        PeerUnidiStreamCount = (long) (PeerUnidiStreamCount << 19);
        N = (long) ((N & ~MASK) | (PeerUnidiStreamCount & MASK));
        setIsSetFlags(N);
    }

    public long getMaxBindingStatelessOperations() {
        var N = getIsSetFlags();
        return (long) ((N >> 20) & 0b1);
    }

    public void setMaxBindingStatelessOperations(long MaxBindingStatelessOperations) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 20);
        MaxBindingStatelessOperations = (long) (MaxBindingStatelessOperations & 0b1);
        MaxBindingStatelessOperations = (long) (MaxBindingStatelessOperations << 20);
        N = (long) ((N & ~MASK) | (MaxBindingStatelessOperations & MASK));
        setIsSetFlags(N);
    }

    public long getStatelessOperationExpirationMs() {
        var N = getIsSetFlags();
        return (long) ((N >> 21) & 0b1);
    }

    public void setStatelessOperationExpirationMs(long StatelessOperationExpirationMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 21);
        StatelessOperationExpirationMs = (long) (StatelessOperationExpirationMs & 0b1);
        StatelessOperationExpirationMs = (long) (StatelessOperationExpirationMs << 21);
        N = (long) ((N & ~MASK) | (StatelessOperationExpirationMs & MASK));
        setIsSetFlags(N);
    }

    public long getMinimumMtu() {
        var N = getIsSetFlags();
        return (long) ((N >> 22) & 0b1);
    }

    public void setMinimumMtu(long MinimumMtu) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 22);
        MinimumMtu = (long) (MinimumMtu & 0b1);
        MinimumMtu = (long) (MinimumMtu << 22);
        N = (long) ((N & ~MASK) | (MinimumMtu & MASK));
        setIsSetFlags(N);
    }

    public long getMaximumMtu() {
        var N = getIsSetFlags();
        return (long) ((N >> 23) & 0b1);
    }

    public void setMaximumMtu(long MaximumMtu) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 23);
        MaximumMtu = (long) (MaximumMtu & 0b1);
        MaximumMtu = (long) (MaximumMtu << 23);
        N = (long) ((N & ~MASK) | (MaximumMtu & MASK));
        setIsSetFlags(N);
    }

    public long getSendBufferingEnabled() {
        var N = getIsSetFlags();
        return (long) ((N >> 24) & 0b1);
    }

    public void setSendBufferingEnabled(long SendBufferingEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 24);
        SendBufferingEnabled = (long) (SendBufferingEnabled & 0b1);
        SendBufferingEnabled = (long) (SendBufferingEnabled << 24);
        N = (long) ((N & ~MASK) | (SendBufferingEnabled & MASK));
        setIsSetFlags(N);
    }

    public long getPacingEnabled() {
        var N = getIsSetFlags();
        return (long) ((N >> 25) & 0b1);
    }

    public void setPacingEnabled(long PacingEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 25);
        PacingEnabled = (long) (PacingEnabled & 0b1);
        PacingEnabled = (long) (PacingEnabled << 25);
        N = (long) ((N & ~MASK) | (PacingEnabled & MASK));
        setIsSetFlags(N);
    }

    public long getMigrationEnabled() {
        var N = getIsSetFlags();
        return (long) ((N >> 26) & 0b1);
    }

    public void setMigrationEnabled(long MigrationEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 26);
        MigrationEnabled = (long) (MigrationEnabled & 0b1);
        MigrationEnabled = (long) (MigrationEnabled << 26);
        N = (long) ((N & ~MASK) | (MigrationEnabled & MASK));
        setIsSetFlags(N);
    }

    public long getDatagramReceiveEnabled() {
        var N = getIsSetFlags();
        return (long) ((N >> 27) & 0b1);
    }

    public void setDatagramReceiveEnabled(long DatagramReceiveEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 27);
        DatagramReceiveEnabled = (long) (DatagramReceiveEnabled & 0b1);
        DatagramReceiveEnabled = (long) (DatagramReceiveEnabled << 27);
        N = (long) ((N & ~MASK) | (DatagramReceiveEnabled & MASK));
        setIsSetFlags(N);
    }

    public long getServerResumptionLevel() {
        var N = getIsSetFlags();
        return (long) ((N >> 28) & 0b1);
    }

    public void setServerResumptionLevel(long ServerResumptionLevel) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 28);
        ServerResumptionLevel = (long) (ServerResumptionLevel & 0b1);
        ServerResumptionLevel = (long) (ServerResumptionLevel << 28);
        N = (long) ((N & ~MASK) | (ServerResumptionLevel & MASK));
        setIsSetFlags(N);
    }

    public long getMaxOperationsPerDrain() {
        var N = getIsSetFlags();
        return (long) ((N >> 29) & 0b1);
    }

    public void setMaxOperationsPerDrain(long MaxOperationsPerDrain) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 29);
        MaxOperationsPerDrain = (long) (MaxOperationsPerDrain & 0b1);
        MaxOperationsPerDrain = (long) (MaxOperationsPerDrain << 29);
        N = (long) ((N & ~MASK) | (MaxOperationsPerDrain & MASK));
        setIsSetFlags(N);
    }

    public long getMtuDiscoveryMissingProbeCount() {
        var N = getIsSetFlags();
        return (long) ((N >> 30) & 0b1);
    }

    public void setMtuDiscoveryMissingProbeCount(long MtuDiscoveryMissingProbeCount) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 30);
        MtuDiscoveryMissingProbeCount = (long) (MtuDiscoveryMissingProbeCount & 0b1);
        MtuDiscoveryMissingProbeCount = (long) (MtuDiscoveryMissingProbeCount << 30);
        N = (long) ((N & ~MASK) | (MtuDiscoveryMissingProbeCount & MASK));
        setIsSetFlags(N);
    }

    public long getDestCidUpdateIdleTimeoutMs() {
        var N = getIsSetFlags();
        return (long) ((N >> 31) & 0b1);
    }

    public void setDestCidUpdateIdleTimeoutMs(long DestCidUpdateIdleTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 31);
        DestCidUpdateIdleTimeoutMs = (long) (DestCidUpdateIdleTimeoutMs & 0b1);
        DestCidUpdateIdleTimeoutMs = (long) (DestCidUpdateIdleTimeoutMs << 31);
        N = (long) ((N & ~MASK) | (DestCidUpdateIdleTimeoutMs & MASK));
        setIsSetFlags(N);
    }

    public long getGreaseQuicBitEnabled() {
        var N = getIsSetFlags();
        return (long) ((N >> 32) & 0b1);
    }

    public void setGreaseQuicBitEnabled(long GreaseQuicBitEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 32);
        GreaseQuicBitEnabled = (long) (GreaseQuicBitEnabled & 0b1);
        GreaseQuicBitEnabled = (long) (GreaseQuicBitEnabled << 32);
        N = (long) ((N & ~MASK) | (GreaseQuicBitEnabled & MASK));
        setIsSetFlags(N);
    }

    public long getEcnEnabled() {
        var N = getIsSetFlags();
        return (long) ((N >> 33) & 0b1);
    }

    public void setEcnEnabled(long EcnEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 33);
        EcnEnabled = (long) (EcnEnabled & 0b1);
        EcnEnabled = (long) (EcnEnabled << 33);
        N = (long) ((N & ~MASK) | (EcnEnabled & MASK));
        setIsSetFlags(N);
    }

    public long getHyStartEnabled() {
        var N = getIsSetFlags();
        return (long) ((N >> 34) & 0b1);
    }

    public void setHyStartEnabled(long HyStartEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 34);
        HyStartEnabled = (long) (HyStartEnabled & 0b1);
        HyStartEnabled = (long) (HyStartEnabled << 34);
        N = (long) ((N & ~MASK) | (HyStartEnabled & MASK));
        setIsSetFlags(N);
    }

    public long getEncryptionOffloadAllowed() {
        var N = getIsSetFlags();
        return (long) ((N >> 35) & 0b1);
    }

    public void setEncryptionOffloadAllowed(long EncryptionOffloadAllowed) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 35);
        EncryptionOffloadAllowed = (long) (EncryptionOffloadAllowed & 0b1);
        EncryptionOffloadAllowed = (long) (EncryptionOffloadAllowed << 35);
        N = (long) ((N & ~MASK) | (EncryptionOffloadAllowed & MASK));
        setIsSetFlags(N);
    }

    public long getReliableResetEnabled() {
        var N = getIsSetFlags();
        return (long) ((N >> 36) & 0b1);
    }

    public void setReliableResetEnabled(long ReliableResetEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 36);
        ReliableResetEnabled = (long) (ReliableResetEnabled & 0b1);
        ReliableResetEnabled = (long) (ReliableResetEnabled << 36);
        N = (long) ((N & ~MASK) | (ReliableResetEnabled & MASK));
        setIsSetFlags(N);
    }

    public QuicSettingsIsSet(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicSettingsIsSet(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicSettingsIsSet{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IsSetFlags => ");
            SB.append(getIsSetFlags());
            SB.append(" {\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxBytesPerKey:1 => ").append(getMaxBytesPerKey());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("HandshakeIdleTimeoutMs:1 => ").append(getHandshakeIdleTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("IdleTimeoutMs:1 => ").append(getIdleTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MtuDiscoverySearchCompleteTimeoutUs:1 => ").append(getMtuDiscoverySearchCompleteTimeoutUs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("TlsClientMaxSendBuffer:1 => ").append(getTlsClientMaxSendBuffer());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("TlsServerMaxSendBuffer:1 => ").append(getTlsServerMaxSendBuffer());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("StreamRecvWindowDefault:1 => ").append(getStreamRecvWindowDefault());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("StreamRecvBufferDefault:1 => ").append(getStreamRecvBufferDefault());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ConnFlowControlWindow:1 => ").append(getConnFlowControlWindow());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxWorkerQueueDelayUs:1 => ").append(getMaxWorkerQueueDelayUs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxStatelessOperations:1 => ").append(getMaxStatelessOperations());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("InitialWindowPackets:1 => ").append(getInitialWindowPackets());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("SendIdleTimeoutMs:1 => ").append(getSendIdleTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("InitialRttMs:1 => ").append(getInitialRttMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxAckDelayMs:1 => ").append(getMaxAckDelayMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("DisconnectTimeoutMs:1 => ").append(getDisconnectTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("KeepAliveIntervalMs:1 => ").append(getKeepAliveIntervalMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("CongestionControlAlgorithm:1 => ").append(getCongestionControlAlgorithm());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("PeerBidiStreamCount:1 => ").append(getPeerBidiStreamCount());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("PeerUnidiStreamCount:1 => ").append(getPeerUnidiStreamCount());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxBindingStatelessOperations:1 => ").append(getMaxBindingStatelessOperations());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("StatelessOperationExpirationMs:1 => ").append(getStatelessOperationExpirationMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MinimumMtu:1 => ").append(getMinimumMtu());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaximumMtu:1 => ").append(getMaximumMtu());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("SendBufferingEnabled:1 => ").append(getSendBufferingEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("PacingEnabled:1 => ").append(getPacingEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MigrationEnabled:1 => ").append(getMigrationEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("DatagramReceiveEnabled:1 => ").append(getDatagramReceiveEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ServerResumptionLevel:1 => ").append(getServerResumptionLevel());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxOperationsPerDrain:1 => ").append(getMaxOperationsPerDrain());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MtuDiscoveryMissingProbeCount:1 => ").append(getMtuDiscoveryMissingProbeCount());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("DestCidUpdateIdleTimeoutMs:1 => ").append(getDestCidUpdateIdleTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("GreaseQuicBitEnabled:1 => ").append(getGreaseQuicBitEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("EcnEnabled:1 => ").append(getEcnEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("HyStartEnabled:1 => ").append(getHyStartEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("EncryptionOffloadAllowed:1 => ").append(getEncryptionOffloadAllowed());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ReliableResetEnabled:1 => ").append(getReliableResetEnabled());
            SB.append("\n");
            SB.append(" ".repeat(INDENT + 4)).append("}");
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicSettingsIsSet> {
        public Array(MemorySegment buf) {
            super(buf, QuicSettingsIsSet.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicSettingsIsSet.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicSettingsIsSet.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicSettingsIsSet ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicSettingsIsSet.Array";
        }

        @Override
        protected QuicSettingsIsSet construct(MemorySegment seg) {
            return new QuicSettingsIsSet(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicSettingsIsSet value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicSettingsIsSet> {
        private Func(io.vproxy.pni.CallSite<QuicSettingsIsSet> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicSettingsIsSet> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicSettingsIsSet> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicSettingsIsSet> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicSettingsIsSet.Func";
        }

        @Override
        protected QuicSettingsIsSet construct(MemorySegment seg) {
            return new QuicSettingsIsSet(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:0c5ea4f2ad4186b0bc349d2abee89648d4ba0c521af57746aa608af72c98a08d
