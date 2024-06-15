package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicSettingsIsSet extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("IsSetFlags")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private static final VarHandleW IsSetFlagsVH = VarHandleW.of(
        LAYOUT.varHandle(
            MemoryLayout.PathElement.groupElement("IsSetFlags")
        )
    );

    public long getIsSetFlags() {
        return IsSetFlagsVH.getLong(MEMORY);
    }

    public void setIsSetFlags(long IsSetFlags) {
        IsSetFlagsVH.set(MEMORY, IsSetFlags);
    }

    public boolean isMaxBytesPerKey() {
        var N = getIsSetFlags();
        return ((N >> 0) & 0b1) == 1;
    }

    public void setMaxBytesPerKey(boolean MaxBytesPerKey) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 0);
        var NN = (long) (MaxBytesPerKey ? 1 : 0);
        NN = (long) (NN << 0);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isHandshakeIdleTimeoutMs() {
        var N = getIsSetFlags();
        return ((N >> 1) & 0b1) == 1;
    }

    public void setHandshakeIdleTimeoutMs(boolean HandshakeIdleTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 1);
        var NN = (long) (HandshakeIdleTimeoutMs ? 1 : 0);
        NN = (long) (NN << 1);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isIdleTimeoutMs() {
        var N = getIsSetFlags();
        return ((N >> 2) & 0b1) == 1;
    }

    public void setIdleTimeoutMs(boolean IdleTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 2);
        var NN = (long) (IdleTimeoutMs ? 1 : 0);
        NN = (long) (NN << 2);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMtuDiscoverySearchCompleteTimeoutUs() {
        var N = getIsSetFlags();
        return ((N >> 3) & 0b1) == 1;
    }

    public void setMtuDiscoverySearchCompleteTimeoutUs(boolean MtuDiscoverySearchCompleteTimeoutUs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 3);
        var NN = (long) (MtuDiscoverySearchCompleteTimeoutUs ? 1 : 0);
        NN = (long) (NN << 3);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isTlsClientMaxSendBuffer() {
        var N = getIsSetFlags();
        return ((N >> 4) & 0b1) == 1;
    }

    public void setTlsClientMaxSendBuffer(boolean TlsClientMaxSendBuffer) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 4);
        var NN = (long) (TlsClientMaxSendBuffer ? 1 : 0);
        NN = (long) (NN << 4);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isTlsServerMaxSendBuffer() {
        var N = getIsSetFlags();
        return ((N >> 5) & 0b1) == 1;
    }

    public void setTlsServerMaxSendBuffer(boolean TlsServerMaxSendBuffer) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 5);
        var NN = (long) (TlsServerMaxSendBuffer ? 1 : 0);
        NN = (long) (NN << 5);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isStreamRecvWindowDefault() {
        var N = getIsSetFlags();
        return ((N >> 6) & 0b1) == 1;
    }

    public void setStreamRecvWindowDefault(boolean StreamRecvWindowDefault) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 6);
        var NN = (long) (StreamRecvWindowDefault ? 1 : 0);
        NN = (long) (NN << 6);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isStreamRecvBufferDefault() {
        var N = getIsSetFlags();
        return ((N >> 7) & 0b1) == 1;
    }

    public void setStreamRecvBufferDefault(boolean StreamRecvBufferDefault) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 7);
        var NN = (long) (StreamRecvBufferDefault ? 1 : 0);
        NN = (long) (NN << 7);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isConnFlowControlWindow() {
        var N = getIsSetFlags();
        return ((N >> 8) & 0b1) == 1;
    }

    public void setConnFlowControlWindow(boolean ConnFlowControlWindow) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 8);
        var NN = (long) (ConnFlowControlWindow ? 1 : 0);
        NN = (long) (NN << 8);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMaxWorkerQueueDelayUs() {
        var N = getIsSetFlags();
        return ((N >> 9) & 0b1) == 1;
    }

    public void setMaxWorkerQueueDelayUs(boolean MaxWorkerQueueDelayUs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 9);
        var NN = (long) (MaxWorkerQueueDelayUs ? 1 : 0);
        NN = (long) (NN << 9);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMaxStatelessOperations() {
        var N = getIsSetFlags();
        return ((N >> 10) & 0b1) == 1;
    }

    public void setMaxStatelessOperations(boolean MaxStatelessOperations) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 10);
        var NN = (long) (MaxStatelessOperations ? 1 : 0);
        NN = (long) (NN << 10);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isInitialWindowPackets() {
        var N = getIsSetFlags();
        return ((N >> 11) & 0b1) == 1;
    }

    public void setInitialWindowPackets(boolean InitialWindowPackets) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 11);
        var NN = (long) (InitialWindowPackets ? 1 : 0);
        NN = (long) (NN << 11);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isSendIdleTimeoutMs() {
        var N = getIsSetFlags();
        return ((N >> 12) & 0b1) == 1;
    }

    public void setSendIdleTimeoutMs(boolean SendIdleTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 12);
        var NN = (long) (SendIdleTimeoutMs ? 1 : 0);
        NN = (long) (NN << 12);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isInitialRttMs() {
        var N = getIsSetFlags();
        return ((N >> 13) & 0b1) == 1;
    }

    public void setInitialRttMs(boolean InitialRttMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 13);
        var NN = (long) (InitialRttMs ? 1 : 0);
        NN = (long) (NN << 13);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMaxAckDelayMs() {
        var N = getIsSetFlags();
        return ((N >> 14) & 0b1) == 1;
    }

    public void setMaxAckDelayMs(boolean MaxAckDelayMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 14);
        var NN = (long) (MaxAckDelayMs ? 1 : 0);
        NN = (long) (NN << 14);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isDisconnectTimeoutMs() {
        var N = getIsSetFlags();
        return ((N >> 15) & 0b1) == 1;
    }

    public void setDisconnectTimeoutMs(boolean DisconnectTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 15);
        var NN = (long) (DisconnectTimeoutMs ? 1 : 0);
        NN = (long) (NN << 15);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isKeepAliveIntervalMs() {
        var N = getIsSetFlags();
        return ((N >> 16) & 0b1) == 1;
    }

    public void setKeepAliveIntervalMs(boolean KeepAliveIntervalMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 16);
        var NN = (long) (KeepAliveIntervalMs ? 1 : 0);
        NN = (long) (NN << 16);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isCongestionControlAlgorithm() {
        var N = getIsSetFlags();
        return ((N >> 17) & 0b1) == 1;
    }

    public void setCongestionControlAlgorithm(boolean CongestionControlAlgorithm) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 17);
        var NN = (long) (CongestionControlAlgorithm ? 1 : 0);
        NN = (long) (NN << 17);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isPeerBidiStreamCount() {
        var N = getIsSetFlags();
        return ((N >> 18) & 0b1) == 1;
    }

    public void setPeerBidiStreamCount(boolean PeerBidiStreamCount) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 18);
        var NN = (long) (PeerBidiStreamCount ? 1 : 0);
        NN = (long) (NN << 18);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isPeerUnidiStreamCount() {
        var N = getIsSetFlags();
        return ((N >> 19) & 0b1) == 1;
    }

    public void setPeerUnidiStreamCount(boolean PeerUnidiStreamCount) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 19);
        var NN = (long) (PeerUnidiStreamCount ? 1 : 0);
        NN = (long) (NN << 19);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMaxBindingStatelessOperations() {
        var N = getIsSetFlags();
        return ((N >> 20) & 0b1) == 1;
    }

    public void setMaxBindingStatelessOperations(boolean MaxBindingStatelessOperations) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 20);
        var NN = (long) (MaxBindingStatelessOperations ? 1 : 0);
        NN = (long) (NN << 20);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isStatelessOperationExpirationMs() {
        var N = getIsSetFlags();
        return ((N >> 21) & 0b1) == 1;
    }

    public void setStatelessOperationExpirationMs(boolean StatelessOperationExpirationMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 21);
        var NN = (long) (StatelessOperationExpirationMs ? 1 : 0);
        NN = (long) (NN << 21);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMinimumMtu() {
        var N = getIsSetFlags();
        return ((N >> 22) & 0b1) == 1;
    }

    public void setMinimumMtu(boolean MinimumMtu) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 22);
        var NN = (long) (MinimumMtu ? 1 : 0);
        NN = (long) (NN << 22);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMaximumMtu() {
        var N = getIsSetFlags();
        return ((N >> 23) & 0b1) == 1;
    }

    public void setMaximumMtu(boolean MaximumMtu) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 23);
        var NN = (long) (MaximumMtu ? 1 : 0);
        NN = (long) (NN << 23);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isSendBufferingEnabled() {
        var N = getIsSetFlags();
        return ((N >> 24) & 0b1) == 1;
    }

    public void setSendBufferingEnabled(boolean SendBufferingEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 24);
        var NN = (long) (SendBufferingEnabled ? 1 : 0);
        NN = (long) (NN << 24);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isPacingEnabled() {
        var N = getIsSetFlags();
        return ((N >> 25) & 0b1) == 1;
    }

    public void setPacingEnabled(boolean PacingEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 25);
        var NN = (long) (PacingEnabled ? 1 : 0);
        NN = (long) (NN << 25);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMigrationEnabled() {
        var N = getIsSetFlags();
        return ((N >> 26) & 0b1) == 1;
    }

    public void setMigrationEnabled(boolean MigrationEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 26);
        var NN = (long) (MigrationEnabled ? 1 : 0);
        NN = (long) (NN << 26);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isDatagramReceiveEnabled() {
        var N = getIsSetFlags();
        return ((N >> 27) & 0b1) == 1;
    }

    public void setDatagramReceiveEnabled(boolean DatagramReceiveEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 27);
        var NN = (long) (DatagramReceiveEnabled ? 1 : 0);
        NN = (long) (NN << 27);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isServerResumptionLevel() {
        var N = getIsSetFlags();
        return ((N >> 28) & 0b1) == 1;
    }

    public void setServerResumptionLevel(boolean ServerResumptionLevel) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 28);
        var NN = (long) (ServerResumptionLevel ? 1 : 0);
        NN = (long) (NN << 28);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMaxOperationsPerDrain() {
        var N = getIsSetFlags();
        return ((N >> 29) & 0b1) == 1;
    }

    public void setMaxOperationsPerDrain(boolean MaxOperationsPerDrain) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 29);
        var NN = (long) (MaxOperationsPerDrain ? 1 : 0);
        NN = (long) (NN << 29);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isMtuDiscoveryMissingProbeCount() {
        var N = getIsSetFlags();
        return ((N >> 30) & 0b1) == 1;
    }

    public void setMtuDiscoveryMissingProbeCount(boolean MtuDiscoveryMissingProbeCount) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1 << 30);
        var NN = (long) (MtuDiscoveryMissingProbeCount ? 1 : 0);
        NN = (long) (NN << 30);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isDestCidUpdateIdleTimeoutMs() {
        var N = getIsSetFlags();
        return ((N >> 31) & 0b1) == 1;
    }

    public void setDestCidUpdateIdleTimeoutMs(boolean DestCidUpdateIdleTimeoutMs) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1L << 31);
        var NN = (long) (DestCidUpdateIdleTimeoutMs ? 1 : 0);
        NN = (long) (NN << 31);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isGreaseQuicBitEnabled() {
        var N = getIsSetFlags();
        return ((N >> 32) & 0b1) == 1;
    }

    public void setGreaseQuicBitEnabled(boolean GreaseQuicBitEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1L << 32);
        var NN = (long) (GreaseQuicBitEnabled ? 1 : 0);
        NN = (long) (NN << 32);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isEcnEnabled() {
        var N = getIsSetFlags();
        return ((N >> 33) & 0b1) == 1;
    }

    public void setEcnEnabled(boolean EcnEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1L << 33);
        var NN = (long) (EcnEnabled ? 1 : 0);
        NN = (long) (NN << 33);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isHyStartEnabled() {
        var N = getIsSetFlags();
        return ((N >> 34) & 0b1) == 1;
    }

    public void setHyStartEnabled(boolean HyStartEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1L << 34);
        var NN = (long) (HyStartEnabled ? 1 : 0);
        NN = (long) (NN << 34);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isEncryptionOffloadAllowed() {
        var N = getIsSetFlags();
        return ((N >> 35) & 0b1) == 1;
    }

    public void setEncryptionOffloadAllowed(boolean EncryptionOffloadAllowed) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1L << 35);
        var NN = (long) (EncryptionOffloadAllowed ? 1 : 0);
        NN = (long) (NN << 35);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setIsSetFlags(N);
    }

    public boolean isReliableResetEnabled() {
        var N = getIsSetFlags();
        return ((N >> 36) & 0b1) == 1;
    }

    public void setReliableResetEnabled(boolean ReliableResetEnabled) {
        var N = getIsSetFlags();
        long MASK = (long) (0b1L << 36);
        var NN = (long) (ReliableResetEnabled ? 1 : 0);
        NN = (long) (NN << 36);
        N = (long) ((N & ~MASK) | (NN & MASK));
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
            SB.append(" ".repeat(INDENT + 8)).append("MaxBytesPerKey:1 => ").append(isMaxBytesPerKey());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("HandshakeIdleTimeoutMs:1 => ").append(isHandshakeIdleTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("IdleTimeoutMs:1 => ").append(isIdleTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MtuDiscoverySearchCompleteTimeoutUs:1 => ").append(isMtuDiscoverySearchCompleteTimeoutUs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("TlsClientMaxSendBuffer:1 => ").append(isTlsClientMaxSendBuffer());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("TlsServerMaxSendBuffer:1 => ").append(isTlsServerMaxSendBuffer());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("StreamRecvWindowDefault:1 => ").append(isStreamRecvWindowDefault());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("StreamRecvBufferDefault:1 => ").append(isStreamRecvBufferDefault());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ConnFlowControlWindow:1 => ").append(isConnFlowControlWindow());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxWorkerQueueDelayUs:1 => ").append(isMaxWorkerQueueDelayUs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxStatelessOperations:1 => ").append(isMaxStatelessOperations());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("InitialWindowPackets:1 => ").append(isInitialWindowPackets());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("SendIdleTimeoutMs:1 => ").append(isSendIdleTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("InitialRttMs:1 => ").append(isInitialRttMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxAckDelayMs:1 => ").append(isMaxAckDelayMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("DisconnectTimeoutMs:1 => ").append(isDisconnectTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("KeepAliveIntervalMs:1 => ").append(isKeepAliveIntervalMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("CongestionControlAlgorithm:1 => ").append(isCongestionControlAlgorithm());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("PeerBidiStreamCount:1 => ").append(isPeerBidiStreamCount());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("PeerUnidiStreamCount:1 => ").append(isPeerUnidiStreamCount());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxBindingStatelessOperations:1 => ").append(isMaxBindingStatelessOperations());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("StatelessOperationExpirationMs:1 => ").append(isStatelessOperationExpirationMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MinimumMtu:1 => ").append(isMinimumMtu());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaximumMtu:1 => ").append(isMaximumMtu());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("SendBufferingEnabled:1 => ").append(isSendBufferingEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("PacingEnabled:1 => ").append(isPacingEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MigrationEnabled:1 => ").append(isMigrationEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("DatagramReceiveEnabled:1 => ").append(isDatagramReceiveEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ServerResumptionLevel:1 => ").append(isServerResumptionLevel());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MaxOperationsPerDrain:1 => ").append(isMaxOperationsPerDrain());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MtuDiscoveryMissingProbeCount:1 => ").append(isMtuDiscoveryMissingProbeCount());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("DestCidUpdateIdleTimeoutMs:1 => ").append(isDestCidUpdateIdleTimeoutMs());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("GreaseQuicBitEnabled:1 => ").append(isGreaseQuicBitEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("EcnEnabled:1 => ").append(isEcnEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("HyStartEnabled:1 => ").append(isHyStartEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("EncryptionOffloadAllowed:1 => ").append(isEncryptionOffloadAllowed());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ReliableResetEnabled:1 => ").append(isReliableResetEnabled());
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
// metadata.generator-version: pni 21.0.0.20
// sha256:2b55c2c02a3f931f28faac7ea9a1b94cdfcbf26267ed8e7ef43c0addfcac9eb4
