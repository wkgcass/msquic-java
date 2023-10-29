package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicSettings extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        io.vproxy.msquic.QuicSettingsIsSet.LAYOUT.withName("IsSet"),
        ValueLayout.JAVA_LONG.withName("MaxBytesPerKey"),
        ValueLayout.JAVA_LONG.withName("HandshakeIdleTimeoutMs"),
        ValueLayout.JAVA_LONG.withName("IdleTimeoutMs"),
        ValueLayout.JAVA_LONG.withName("MtuDiscoverySearchCompleteTimeoutUs"),
        ValueLayout.JAVA_INT.withName("TlsClientMaxSendBuffer"),
        ValueLayout.JAVA_INT.withName("TlsServerMaxSendBuffer"),
        ValueLayout.JAVA_INT.withName("StreamRecvWindowDefault"),
        ValueLayout.JAVA_INT.withName("StreamRecvBufferDefault"),
        ValueLayout.JAVA_INT.withName("ConnFlowControlWindow"),
        ValueLayout.JAVA_INT.withName("MaxWorkerQueueDelayUs"),
        ValueLayout.JAVA_INT.withName("MaxStatelessOperations"),
        ValueLayout.JAVA_INT.withName("InitialWindowPackets"),
        ValueLayout.JAVA_INT.withName("SendIdleTimeoutMs"),
        ValueLayout.JAVA_INT.withName("InitialRttMs"),
        ValueLayout.JAVA_INT.withName("MaxAckDelayMs"),
        ValueLayout.JAVA_INT.withName("DisconnectTimeoutMs"),
        ValueLayout.JAVA_INT.withName("KeepAliveIntervalMs"),
        ValueLayout.JAVA_SHORT.withName("CongestionControlAlgorithm"),
        ValueLayout.JAVA_SHORT.withName("PeerBidiStreamCount"),
        ValueLayout.JAVA_SHORT.withName("PeerUnidiStreamCount"),
        ValueLayout.JAVA_SHORT.withName("MaxBindingStatelessOperations"),
        ValueLayout.JAVA_SHORT.withName("StatelessOperationExpirationMs"),
        ValueLayout.JAVA_SHORT.withName("MinimumMtu"),
        ValueLayout.JAVA_SHORT.withName("MaximumMtu"),
        ValueLayout.JAVA_BYTE.withName("Field01"),
        ValueLayout.JAVA_BYTE.withName("MaxOperationsPerDrain"),
        ValueLayout.JAVA_BYTE.withName("MtuDiscoveryMissingProbeCount"),
        MemoryLayout.sequenceLayout(3L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_INT.withName("DestCidUpdateIdleTimeoutMs"),
        MemoryLayout.sequenceLayout(4L, ValueLayout.JAVA_BYTE) /* padding */,
        ValueLayout.JAVA_LONG.withName("Flags")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private final io.vproxy.msquic.QuicSettingsIsSet IsSet;

    public io.vproxy.msquic.QuicSettingsIsSet getIsSet() {
        return this.IsSet;
    }

    private static final VarHandle MaxBytesPerKeyVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MaxBytesPerKey")
    );

    public long getMaxBytesPerKey() {
        return (long) MaxBytesPerKeyVH.get(MEMORY);
    }

    public void setMaxBytesPerKey(long MaxBytesPerKey) {
        MaxBytesPerKeyVH.set(MEMORY, MaxBytesPerKey);
    }

    private static final VarHandle HandshakeIdleTimeoutMsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("HandshakeIdleTimeoutMs")
    );

    public long getHandshakeIdleTimeoutMs() {
        return (long) HandshakeIdleTimeoutMsVH.get(MEMORY);
    }

    public void setHandshakeIdleTimeoutMs(long HandshakeIdleTimeoutMs) {
        HandshakeIdleTimeoutMsVH.set(MEMORY, HandshakeIdleTimeoutMs);
    }

    private static final VarHandle IdleTimeoutMsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("IdleTimeoutMs")
    );

    public long getIdleTimeoutMs() {
        return (long) IdleTimeoutMsVH.get(MEMORY);
    }

    public void setIdleTimeoutMs(long IdleTimeoutMs) {
        IdleTimeoutMsVH.set(MEMORY, IdleTimeoutMs);
    }

    private static final VarHandle MtuDiscoverySearchCompleteTimeoutUsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MtuDiscoverySearchCompleteTimeoutUs")
    );

    public long getMtuDiscoverySearchCompleteTimeoutUs() {
        return (long) MtuDiscoverySearchCompleteTimeoutUsVH.get(MEMORY);
    }

    public void setMtuDiscoverySearchCompleteTimeoutUs(long MtuDiscoverySearchCompleteTimeoutUs) {
        MtuDiscoverySearchCompleteTimeoutUsVH.set(MEMORY, MtuDiscoverySearchCompleteTimeoutUs);
    }

    private static final VarHandle TlsClientMaxSendBufferVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("TlsClientMaxSendBuffer")
    );

    public int getTlsClientMaxSendBuffer() {
        return (int) TlsClientMaxSendBufferVH.get(MEMORY);
    }

    public void setTlsClientMaxSendBuffer(int TlsClientMaxSendBuffer) {
        TlsClientMaxSendBufferVH.set(MEMORY, TlsClientMaxSendBuffer);
    }

    private static final VarHandle TlsServerMaxSendBufferVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("TlsServerMaxSendBuffer")
    );

    public int getTlsServerMaxSendBuffer() {
        return (int) TlsServerMaxSendBufferVH.get(MEMORY);
    }

    public void setTlsServerMaxSendBuffer(int TlsServerMaxSendBuffer) {
        TlsServerMaxSendBufferVH.set(MEMORY, TlsServerMaxSendBuffer);
    }

    private static final VarHandle StreamRecvWindowDefaultVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("StreamRecvWindowDefault")
    );

    public int getStreamRecvWindowDefault() {
        return (int) StreamRecvWindowDefaultVH.get(MEMORY);
    }

    public void setStreamRecvWindowDefault(int StreamRecvWindowDefault) {
        StreamRecvWindowDefaultVH.set(MEMORY, StreamRecvWindowDefault);
    }

    private static final VarHandle StreamRecvBufferDefaultVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("StreamRecvBufferDefault")
    );

    public int getStreamRecvBufferDefault() {
        return (int) StreamRecvBufferDefaultVH.get(MEMORY);
    }

    public void setStreamRecvBufferDefault(int StreamRecvBufferDefault) {
        StreamRecvBufferDefaultVH.set(MEMORY, StreamRecvBufferDefault);
    }

    private static final VarHandle ConnFlowControlWindowVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("ConnFlowControlWindow")
    );

    public int getConnFlowControlWindow() {
        return (int) ConnFlowControlWindowVH.get(MEMORY);
    }

    public void setConnFlowControlWindow(int ConnFlowControlWindow) {
        ConnFlowControlWindowVH.set(MEMORY, ConnFlowControlWindow);
    }

    private static final VarHandle MaxWorkerQueueDelayUsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MaxWorkerQueueDelayUs")
    );

    public int getMaxWorkerQueueDelayUs() {
        return (int) MaxWorkerQueueDelayUsVH.get(MEMORY);
    }

    public void setMaxWorkerQueueDelayUs(int MaxWorkerQueueDelayUs) {
        MaxWorkerQueueDelayUsVH.set(MEMORY, MaxWorkerQueueDelayUs);
    }

    private static final VarHandle MaxStatelessOperationsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MaxStatelessOperations")
    );

    public int getMaxStatelessOperations() {
        return (int) MaxStatelessOperationsVH.get(MEMORY);
    }

    public void setMaxStatelessOperations(int MaxStatelessOperations) {
        MaxStatelessOperationsVH.set(MEMORY, MaxStatelessOperations);
    }

    private static final VarHandle InitialWindowPacketsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("InitialWindowPackets")
    );

    public int getInitialWindowPackets() {
        return (int) InitialWindowPacketsVH.get(MEMORY);
    }

    public void setInitialWindowPackets(int InitialWindowPackets) {
        InitialWindowPacketsVH.set(MEMORY, InitialWindowPackets);
    }

    private static final VarHandle SendIdleTimeoutMsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("SendIdleTimeoutMs")
    );

    public int getSendIdleTimeoutMs() {
        return (int) SendIdleTimeoutMsVH.get(MEMORY);
    }

    public void setSendIdleTimeoutMs(int SendIdleTimeoutMs) {
        SendIdleTimeoutMsVH.set(MEMORY, SendIdleTimeoutMs);
    }

    private static final VarHandle InitialRttMsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("InitialRttMs")
    );

    public int getInitialRttMs() {
        return (int) InitialRttMsVH.get(MEMORY);
    }

    public void setInitialRttMs(int InitialRttMs) {
        InitialRttMsVH.set(MEMORY, InitialRttMs);
    }

    private static final VarHandle MaxAckDelayMsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MaxAckDelayMs")
    );

    public int getMaxAckDelayMs() {
        return (int) MaxAckDelayMsVH.get(MEMORY);
    }

    public void setMaxAckDelayMs(int MaxAckDelayMs) {
        MaxAckDelayMsVH.set(MEMORY, MaxAckDelayMs);
    }

    private static final VarHandle DisconnectTimeoutMsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("DisconnectTimeoutMs")
    );

    public int getDisconnectTimeoutMs() {
        return (int) DisconnectTimeoutMsVH.get(MEMORY);
    }

    public void setDisconnectTimeoutMs(int DisconnectTimeoutMs) {
        DisconnectTimeoutMsVH.set(MEMORY, DisconnectTimeoutMs);
    }

    private static final VarHandle KeepAliveIntervalMsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("KeepAliveIntervalMs")
    );

    public int getKeepAliveIntervalMs() {
        return (int) KeepAliveIntervalMsVH.get(MEMORY);
    }

    public void setKeepAliveIntervalMs(int KeepAliveIntervalMs) {
        KeepAliveIntervalMsVH.set(MEMORY, KeepAliveIntervalMs);
    }

    private static final VarHandle CongestionControlAlgorithmVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("CongestionControlAlgorithm")
    );

    public short getCongestionControlAlgorithm() {
        return (short) CongestionControlAlgorithmVH.get(MEMORY);
    }

    public void setCongestionControlAlgorithm(short CongestionControlAlgorithm) {
        CongestionControlAlgorithmVH.set(MEMORY, CongestionControlAlgorithm);
    }

    private static final VarHandle PeerBidiStreamCountVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("PeerBidiStreamCount")
    );

    public short getPeerBidiStreamCount() {
        return (short) PeerBidiStreamCountVH.get(MEMORY);
    }

    public void setPeerBidiStreamCount(short PeerBidiStreamCount) {
        PeerBidiStreamCountVH.set(MEMORY, PeerBidiStreamCount);
    }

    private static final VarHandle PeerUnidiStreamCountVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("PeerUnidiStreamCount")
    );

    public short getPeerUnidiStreamCount() {
        return (short) PeerUnidiStreamCountVH.get(MEMORY);
    }

    public void setPeerUnidiStreamCount(short PeerUnidiStreamCount) {
        PeerUnidiStreamCountVH.set(MEMORY, PeerUnidiStreamCount);
    }

    private static final VarHandle MaxBindingStatelessOperationsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MaxBindingStatelessOperations")
    );

    public short getMaxBindingStatelessOperations() {
        return (short) MaxBindingStatelessOperationsVH.get(MEMORY);
    }

    public void setMaxBindingStatelessOperations(short MaxBindingStatelessOperations) {
        MaxBindingStatelessOperationsVH.set(MEMORY, MaxBindingStatelessOperations);
    }

    private static final VarHandle StatelessOperationExpirationMsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("StatelessOperationExpirationMs")
    );

    public short getStatelessOperationExpirationMs() {
        return (short) StatelessOperationExpirationMsVH.get(MEMORY);
    }

    public void setStatelessOperationExpirationMs(short StatelessOperationExpirationMs) {
        StatelessOperationExpirationMsVH.set(MEMORY, StatelessOperationExpirationMs);
    }

    private static final VarHandle MinimumMtuVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MinimumMtu")
    );

    public short getMinimumMtu() {
        return (short) MinimumMtuVH.get(MEMORY);
    }

    public void setMinimumMtu(short MinimumMtu) {
        MinimumMtuVH.set(MEMORY, MinimumMtu);
    }

    private static final VarHandle MaximumMtuVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MaximumMtu")
    );

    public short getMaximumMtu() {
        return (short) MaximumMtuVH.get(MEMORY);
    }

    public void setMaximumMtu(short MaximumMtu) {
        MaximumMtuVH.set(MEMORY, MaximumMtu);
    }

    private static final VarHandle Field01VH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Field01")
    );

    public byte getField01() {
        return (byte) Field01VH.get(MEMORY);
    }

    public void setField01(byte Field01) {
        Field01VH.set(MEMORY, Field01);
    }

    public boolean isSendBufferingEnabled() {
        var N = getField01();
        return ((N >> 0) & 0b1) == 1;
    }

    public void setSendBufferingEnabled(boolean SendBufferingEnabled) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 0);
        var NN = (byte) (SendBufferingEnabled ? 1 : 0);
        NN = (byte) (NN << 0);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public boolean isPacingEnabled() {
        var N = getField01();
        return ((N >> 1) & 0b1) == 1;
    }

    public void setPacingEnabled(boolean PacingEnabled) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 1);
        var NN = (byte) (PacingEnabled ? 1 : 0);
        NN = (byte) (NN << 1);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public boolean isMigrationEnabled() {
        var N = getField01();
        return ((N >> 2) & 0b1) == 1;
    }

    public void setMigrationEnabled(boolean MigrationEnabled) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 2);
        var NN = (byte) (MigrationEnabled ? 1 : 0);
        NN = (byte) (NN << 2);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public boolean isDatagramReceiveEnabled() {
        var N = getField01();
        return ((N >> 3) & 0b1) == 1;
    }

    public void setDatagramReceiveEnabled(boolean DatagramReceiveEnabled) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 3);
        var NN = (byte) (DatagramReceiveEnabled ? 1 : 0);
        NN = (byte) (NN << 3);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public byte getServerResumptionLevel() {
        var N = getField01();
        return (byte) ((N >> 4) & 0b11);
    }

    public void setServerResumptionLevel(byte ServerResumptionLevel) {
        var N = getField01();
        byte MASK = (byte) (0b11 << 4);
        ServerResumptionLevel = (byte) (ServerResumptionLevel & 0b11);
        ServerResumptionLevel = (byte) (ServerResumptionLevel << 4);
        N = (byte) ((N & ~MASK) | (ServerResumptionLevel & MASK));
        setField01(N);
    }

    public boolean isGreaseQuicBitEnabled() {
        var N = getField01();
        return ((N >> 6) & 0b1) == 1;
    }

    public void setGreaseQuicBitEnabled(boolean GreaseQuicBitEnabled) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 6);
        var NN = (byte) (GreaseQuicBitEnabled ? 1 : 0);
        NN = (byte) (NN << 6);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    public boolean isEcnEnabled() {
        var N = getField01();
        return ((N >> 7) & 0b1) == 1;
    }

    public void setEcnEnabled(boolean EcnEnabled) {
        var N = getField01();
        byte MASK = (byte) (0b1 << 7);
        var NN = (byte) (EcnEnabled ? 1 : 0);
        NN = (byte) (NN << 7);
        N = (byte) ((N & ~MASK) | (NN & MASK));
        setField01(N);
    }

    private static final VarHandle MaxOperationsPerDrainVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MaxOperationsPerDrain")
    );

    public byte getMaxOperationsPerDrain() {
        return (byte) MaxOperationsPerDrainVH.get(MEMORY);
    }

    public void setMaxOperationsPerDrain(byte MaxOperationsPerDrain) {
        MaxOperationsPerDrainVH.set(MEMORY, MaxOperationsPerDrain);
    }

    private static final VarHandle MtuDiscoveryMissingProbeCountVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("MtuDiscoveryMissingProbeCount")
    );

    public byte getMtuDiscoveryMissingProbeCount() {
        return (byte) MtuDiscoveryMissingProbeCountVH.get(MEMORY);
    }

    public void setMtuDiscoveryMissingProbeCount(byte MtuDiscoveryMissingProbeCount) {
        MtuDiscoveryMissingProbeCountVH.set(MEMORY, MtuDiscoveryMissingProbeCount);
    }

    private static final VarHandle DestCidUpdateIdleTimeoutMsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("DestCidUpdateIdleTimeoutMs")
    );

    public int getDestCidUpdateIdleTimeoutMs() {
        return (int) DestCidUpdateIdleTimeoutMsVH.get(MEMORY);
    }

    public void setDestCidUpdateIdleTimeoutMs(int DestCidUpdateIdleTimeoutMs) {
        DestCidUpdateIdleTimeoutMsVH.set(MEMORY, DestCidUpdateIdleTimeoutMs);
    }

    private static final VarHandle FlagsVH = LAYOUT.varHandle(
        MemoryLayout.PathElement.groupElement("Flags")
    );

    public long getFlags() {
        return (long) FlagsVH.get(MEMORY);
    }

    public void setFlags(long Flags) {
        FlagsVH.set(MEMORY, Flags);
    }

    public boolean isHyStartEnabled() {
        var N = getFlags();
        return ((N >> 0) & 0b1) == 1;
    }

    public void setHyStartEnabled(boolean HyStartEnabled) {
        var N = getFlags();
        long MASK = (long) (0b1 << 0);
        var NN = (long) (HyStartEnabled ? 1 : 0);
        NN = (long) (NN << 0);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setFlags(N);
    }

    public boolean isEncryptionOffloadAllowed() {
        var N = getFlags();
        return ((N >> 1) & 0b1) == 1;
    }

    public void setEncryptionOffloadAllowed(boolean EncryptionOffloadAllowed) {
        var N = getFlags();
        long MASK = (long) (0b1 << 1);
        var NN = (long) (EncryptionOffloadAllowed ? 1 : 0);
        NN = (long) (NN << 1);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setFlags(N);
    }

    public boolean isReliableResetEnabled() {
        var N = getFlags();
        return ((N >> 2) & 0b1) == 1;
    }

    public void setReliableResetEnabled(boolean ReliableResetEnabled) {
        var N = getFlags();
        long MASK = (long) (0b1 << 2);
        var NN = (long) (ReliableResetEnabled ? 1 : 0);
        NN = (long) (NN << 2);
        N = (long) ((N & ~MASK) | (NN & MASK));
        setFlags(N);
    }

    public QuicSettings(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        this.IsSet = new io.vproxy.msquic.QuicSettingsIsSet(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicSettingsIsSet.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicSettingsIsSet.LAYOUT.byteSize();
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_SHORT_UNALIGNED.byteSize();
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
        OFFSET += ValueLayout.JAVA_BYTE.byteSize();
        OFFSET += 3; /* padding */
        OFFSET += ValueLayout.JAVA_INT_UNALIGNED.byteSize();
        OFFSET += 4; /* padding */
        OFFSET += ValueLayout.JAVA_LONG_UNALIGNED.byteSize();
    }

    public QuicSettings(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicSettings{\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IsSet => ");
            PanamaUtils.nativeObjectToString(getIsSet(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MaxBytesPerKey => ");
            SB.append(getMaxBytesPerKey());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("HandshakeIdleTimeoutMs => ");
            SB.append(getHandshakeIdleTimeoutMs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IdleTimeoutMs => ");
            SB.append(getIdleTimeoutMs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MtuDiscoverySearchCompleteTimeoutUs => ");
            SB.append(getMtuDiscoverySearchCompleteTimeoutUs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("TlsClientMaxSendBuffer => ");
            SB.append(getTlsClientMaxSendBuffer());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("TlsServerMaxSendBuffer => ");
            SB.append(getTlsServerMaxSendBuffer());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("StreamRecvWindowDefault => ");
            SB.append(getStreamRecvWindowDefault());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("StreamRecvBufferDefault => ");
            SB.append(getStreamRecvBufferDefault());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("ConnFlowControlWindow => ");
            SB.append(getConnFlowControlWindow());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MaxWorkerQueueDelayUs => ");
            SB.append(getMaxWorkerQueueDelayUs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MaxStatelessOperations => ");
            SB.append(getMaxStatelessOperations());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("InitialWindowPackets => ");
            SB.append(getInitialWindowPackets());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SendIdleTimeoutMs => ");
            SB.append(getSendIdleTimeoutMs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("InitialRttMs => ");
            SB.append(getInitialRttMs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MaxAckDelayMs => ");
            SB.append(getMaxAckDelayMs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("DisconnectTimeoutMs => ");
            SB.append(getDisconnectTimeoutMs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("KeepAliveIntervalMs => ");
            SB.append(getKeepAliveIntervalMs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("CongestionControlAlgorithm => ");
            SB.append(getCongestionControlAlgorithm());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PeerBidiStreamCount => ");
            SB.append(getPeerBidiStreamCount());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PeerUnidiStreamCount => ");
            SB.append(getPeerUnidiStreamCount());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MaxBindingStatelessOperations => ");
            SB.append(getMaxBindingStatelessOperations());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("StatelessOperationExpirationMs => ");
            SB.append(getStatelessOperationExpirationMs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MinimumMtu => ");
            SB.append(getMinimumMtu());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MaximumMtu => ");
            SB.append(getMaximumMtu());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Field01 => ");
            SB.append(getField01());
            SB.append(" {\n");
            SB.append(" ".repeat(INDENT + 8)).append("SendBufferingEnabled:1 => ").append(isSendBufferingEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("PacingEnabled:1 => ").append(isPacingEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("MigrationEnabled:1 => ").append(isMigrationEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("DatagramReceiveEnabled:1 => ").append(isDatagramReceiveEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("ServerResumptionLevel:2 => ").append(getServerResumptionLevel());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("GreaseQuicBitEnabled:1 => ").append(isGreaseQuicBitEnabled());
            SB.append(",\n");
            SB.append(" ".repeat(INDENT + 8)).append("EcnEnabled:1 => ").append(isEcnEnabled());
            SB.append("\n");
            SB.append(" ".repeat(INDENT + 4)).append("}");
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MaxOperationsPerDrain => ");
            SB.append(getMaxOperationsPerDrain());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("MtuDiscoveryMissingProbeCount => ");
            SB.append(getMtuDiscoveryMissingProbeCount());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("DestCidUpdateIdleTimeoutMs => ");
            SB.append(getDestCidUpdateIdleTimeoutMs());
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("Flags => ");
            SB.append(getFlags());
            SB.append(" {\n");
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

    public static class Array extends RefArray<QuicSettings> {
        public Array(MemorySegment buf) {
            super(buf, QuicSettings.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicSettings.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicSettings.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicSettings ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicSettings.Array";
        }

        @Override
        protected QuicSettings construct(MemorySegment seg) {
            return new QuicSettings(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicSettings value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicSettings> {
        private Func(io.vproxy.pni.CallSite<QuicSettings> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicSettings> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicSettings> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicSettings> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicSettings.Func";
        }

        @Override
        protected QuicSettings construct(MemorySegment seg) {
            return new QuicSettings(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.18
// sha256:c2f12988ccf3bb34d0f616da999dff83114b411cb6dbd1249e42ad15419d9bc0
