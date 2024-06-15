package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnectionEventUnion extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.unionLayout(
        io.vproxy.msquic.QuicConnectionEventConnected.LAYOUT.withName("CONNECTED"),
        io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByTransport.LAYOUT.withName("SHUTDOWN_INITIATED_BY_TRANSPORT"),
        io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByPeer.LAYOUT.withName("SHUTDOWN_INITIATED_BY_PEER"),
        io.vproxy.msquic.QuicConnectionEventConnectionShutdownComplete.LAYOUT.withName("SHUTDOWN_COMPLETE"),
        io.vproxy.msquic.QuicConnectionEventLocalAddressChanged.LAYOUT.withName("LOCAL_ADDRESS_CHANGED"),
        io.vproxy.msquic.QuicConnectionEventPeerAddressChanged.LAYOUT.withName("PEER_ADDRESS_CHANGED"),
        io.vproxy.msquic.QuicConnectionEventPeerStreamStarted.LAYOUT.withName("PEER_STREAM_STARTED"),
        io.vproxy.msquic.QuicConnectionEventStreamsAvailable.LAYOUT.withName("STREAMS_AVAILABLE"),
        io.vproxy.msquic.QuicConnectionEventPeerNeedsStreams.LAYOUT.withName("PEER_NEEDS_STREAMS"),
        io.vproxy.msquic.QuicConnectionEventIdealProcessorChanged.LAYOUT.withName("IDEAL_PROCESSOR_CHANGED"),
        io.vproxy.msquic.QuicConnectionEventDatagramStateChanged.LAYOUT.withName("DATAGRAM_STATE_CHANGED"),
        io.vproxy.msquic.QuicConnectionEventDatagramReceived.LAYOUT.withName("DATAGRAM_RECEIVED"),
        io.vproxy.msquic.QuicConnectionEventDatagramSendStateChanged.LAYOUT.withName("DATAGRAM_SEND_STATE_CHANGED"),
        io.vproxy.msquic.QuicConnectionEventResumed.LAYOUT.withName("RESUMED"),
        io.vproxy.msquic.QuicConnectionEventResumptionTicketReceived.LAYOUT.withName("RESUMPTION_TICKET_RECEIVED"),
        io.vproxy.msquic.QuicConnectionEventPeerCertificateReceived.LAYOUT.withName("PEER_CERTIFICATE_RECEIVED"),
        io.vproxy.msquic.QuicConnectionEventReliableResetNegotiated.LAYOUT.withName("RELIABLE_RESET_NEGOTIATED")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private final io.vproxy.msquic.QuicConnectionEventConnected CONNECTED;

    public io.vproxy.msquic.QuicConnectionEventConnected getCONNECTED() {
        return this.CONNECTED;
    }

    private final io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByTransport SHUTDOWN_INITIATED_BY_TRANSPORT;

    public io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByTransport getSHUTDOWN_INITIATED_BY_TRANSPORT() {
        return this.SHUTDOWN_INITIATED_BY_TRANSPORT;
    }

    private final io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByPeer SHUTDOWN_INITIATED_BY_PEER;

    public io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByPeer getSHUTDOWN_INITIATED_BY_PEER() {
        return this.SHUTDOWN_INITIATED_BY_PEER;
    }

    private final io.vproxy.msquic.QuicConnectionEventConnectionShutdownComplete SHUTDOWN_COMPLETE;

    public io.vproxy.msquic.QuicConnectionEventConnectionShutdownComplete getSHUTDOWN_COMPLETE() {
        return this.SHUTDOWN_COMPLETE;
    }

    private final io.vproxy.msquic.QuicConnectionEventLocalAddressChanged LOCAL_ADDRESS_CHANGED;

    public io.vproxy.msquic.QuicConnectionEventLocalAddressChanged getLOCAL_ADDRESS_CHANGED() {
        return this.LOCAL_ADDRESS_CHANGED;
    }

    private final io.vproxy.msquic.QuicConnectionEventPeerAddressChanged PEER_ADDRESS_CHANGED;

    public io.vproxy.msquic.QuicConnectionEventPeerAddressChanged getPEER_ADDRESS_CHANGED() {
        return this.PEER_ADDRESS_CHANGED;
    }

    private final io.vproxy.msquic.QuicConnectionEventPeerStreamStarted PEER_STREAM_STARTED;

    public io.vproxy.msquic.QuicConnectionEventPeerStreamStarted getPEER_STREAM_STARTED() {
        return this.PEER_STREAM_STARTED;
    }

    private final io.vproxy.msquic.QuicConnectionEventStreamsAvailable STREAMS_AVAILABLE;

    public io.vproxy.msquic.QuicConnectionEventStreamsAvailable getSTREAMS_AVAILABLE() {
        return this.STREAMS_AVAILABLE;
    }

    private final io.vproxy.msquic.QuicConnectionEventPeerNeedsStreams PEER_NEEDS_STREAMS;

    public io.vproxy.msquic.QuicConnectionEventPeerNeedsStreams getPEER_NEEDS_STREAMS() {
        return this.PEER_NEEDS_STREAMS;
    }

    private final io.vproxy.msquic.QuicConnectionEventIdealProcessorChanged IDEAL_PROCESSOR_CHANGED;

    public io.vproxy.msquic.QuicConnectionEventIdealProcessorChanged getIDEAL_PROCESSOR_CHANGED() {
        return this.IDEAL_PROCESSOR_CHANGED;
    }

    private final io.vproxy.msquic.QuicConnectionEventDatagramStateChanged DATAGRAM_STATE_CHANGED;

    public io.vproxy.msquic.QuicConnectionEventDatagramStateChanged getDATAGRAM_STATE_CHANGED() {
        return this.DATAGRAM_STATE_CHANGED;
    }

    private final io.vproxy.msquic.QuicConnectionEventDatagramReceived DATAGRAM_RECEIVED;

    public io.vproxy.msquic.QuicConnectionEventDatagramReceived getDATAGRAM_RECEIVED() {
        return this.DATAGRAM_RECEIVED;
    }

    private final io.vproxy.msquic.QuicConnectionEventDatagramSendStateChanged DATAGRAM_SEND_STATE_CHANGED;

    public io.vproxy.msquic.QuicConnectionEventDatagramSendStateChanged getDATAGRAM_SEND_STATE_CHANGED() {
        return this.DATAGRAM_SEND_STATE_CHANGED;
    }

    private final io.vproxy.msquic.QuicConnectionEventResumed RESUMED;

    public io.vproxy.msquic.QuicConnectionEventResumed getRESUMED() {
        return this.RESUMED;
    }

    private final io.vproxy.msquic.QuicConnectionEventResumptionTicketReceived RESUMPTION_TICKET_RECEIVED;

    public io.vproxy.msquic.QuicConnectionEventResumptionTicketReceived getRESUMPTION_TICKET_RECEIVED() {
        return this.RESUMPTION_TICKET_RECEIVED;
    }

    private final io.vproxy.msquic.QuicConnectionEventPeerCertificateReceived PEER_CERTIFICATE_RECEIVED;

    public io.vproxy.msquic.QuicConnectionEventPeerCertificateReceived getPEER_CERTIFICATE_RECEIVED() {
        return this.PEER_CERTIFICATE_RECEIVED;
    }

    private final io.vproxy.msquic.QuicConnectionEventReliableResetNegotiated RELIABLE_RESET_NEGOTIATED;

    public io.vproxy.msquic.QuicConnectionEventReliableResetNegotiated getRELIABLE_RESET_NEGOTIATED() {
        return this.RELIABLE_RESET_NEGOTIATED;
    }

    public QuicConnectionEventUnion(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        this.CONNECTED = new io.vproxy.msquic.QuicConnectionEventConnected(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventConnected.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventConnected.LAYOUT.byteSize();
        OFFSET = 0;
        this.SHUTDOWN_INITIATED_BY_TRANSPORT = new io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByTransport(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByTransport.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByTransport.LAYOUT.byteSize();
        OFFSET = 0;
        this.SHUTDOWN_INITIATED_BY_PEER = new io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByPeer(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByPeer.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventShutdownInitiatedByPeer.LAYOUT.byteSize();
        OFFSET = 0;
        this.SHUTDOWN_COMPLETE = new io.vproxy.msquic.QuicConnectionEventConnectionShutdownComplete(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventConnectionShutdownComplete.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventConnectionShutdownComplete.LAYOUT.byteSize();
        OFFSET = 0;
        this.LOCAL_ADDRESS_CHANGED = new io.vproxy.msquic.QuicConnectionEventLocalAddressChanged(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventLocalAddressChanged.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventLocalAddressChanged.LAYOUT.byteSize();
        OFFSET = 0;
        this.PEER_ADDRESS_CHANGED = new io.vproxy.msquic.QuicConnectionEventPeerAddressChanged(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventPeerAddressChanged.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventPeerAddressChanged.LAYOUT.byteSize();
        OFFSET = 0;
        this.PEER_STREAM_STARTED = new io.vproxy.msquic.QuicConnectionEventPeerStreamStarted(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventPeerStreamStarted.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventPeerStreamStarted.LAYOUT.byteSize();
        OFFSET = 0;
        this.STREAMS_AVAILABLE = new io.vproxy.msquic.QuicConnectionEventStreamsAvailable(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventStreamsAvailable.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventStreamsAvailable.LAYOUT.byteSize();
        OFFSET = 0;
        this.PEER_NEEDS_STREAMS = new io.vproxy.msquic.QuicConnectionEventPeerNeedsStreams(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventPeerNeedsStreams.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventPeerNeedsStreams.LAYOUT.byteSize();
        OFFSET = 0;
        this.IDEAL_PROCESSOR_CHANGED = new io.vproxy.msquic.QuicConnectionEventIdealProcessorChanged(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventIdealProcessorChanged.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventIdealProcessorChanged.LAYOUT.byteSize();
        OFFSET = 0;
        this.DATAGRAM_STATE_CHANGED = new io.vproxy.msquic.QuicConnectionEventDatagramStateChanged(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventDatagramStateChanged.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventDatagramStateChanged.LAYOUT.byteSize();
        OFFSET = 0;
        this.DATAGRAM_RECEIVED = new io.vproxy.msquic.QuicConnectionEventDatagramReceived(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventDatagramReceived.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventDatagramReceived.LAYOUT.byteSize();
        OFFSET = 0;
        this.DATAGRAM_SEND_STATE_CHANGED = new io.vproxy.msquic.QuicConnectionEventDatagramSendStateChanged(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventDatagramSendStateChanged.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventDatagramSendStateChanged.LAYOUT.byteSize();
        OFFSET = 0;
        this.RESUMED = new io.vproxy.msquic.QuicConnectionEventResumed(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventResumed.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventResumed.LAYOUT.byteSize();
        OFFSET = 0;
        this.RESUMPTION_TICKET_RECEIVED = new io.vproxy.msquic.QuicConnectionEventResumptionTicketReceived(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventResumptionTicketReceived.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventResumptionTicketReceived.LAYOUT.byteSize();
        OFFSET = 0;
        this.PEER_CERTIFICATE_RECEIVED = new io.vproxy.msquic.QuicConnectionEventPeerCertificateReceived(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventPeerCertificateReceived.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventPeerCertificateReceived.LAYOUT.byteSize();
        OFFSET = 0;
        this.RELIABLE_RESET_NEGOTIATED = new io.vproxy.msquic.QuicConnectionEventReliableResetNegotiated(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicConnectionEventReliableResetNegotiated.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicConnectionEventReliableResetNegotiated.LAYOUT.byteSize();
        OFFSET = 0;
    }

    public QuicConnectionEventUnion(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        CORRUPTED_MEMORY = true;
        SB.append("QuicConnectionEventUnion(\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("CONNECTED => ");
            PanamaUtils.nativeObjectToString(getCONNECTED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SHUTDOWN_INITIATED_BY_TRANSPORT => ");
            PanamaUtils.nativeObjectToString(getSHUTDOWN_INITIATED_BY_TRANSPORT(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SHUTDOWN_INITIATED_BY_PEER => ");
            PanamaUtils.nativeObjectToString(getSHUTDOWN_INITIATED_BY_PEER(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SHUTDOWN_COMPLETE => ");
            PanamaUtils.nativeObjectToString(getSHUTDOWN_COMPLETE(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("LOCAL_ADDRESS_CHANGED => ");
            PanamaUtils.nativeObjectToString(getLOCAL_ADDRESS_CHANGED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PEER_ADDRESS_CHANGED => ");
            PanamaUtils.nativeObjectToString(getPEER_ADDRESS_CHANGED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PEER_STREAM_STARTED => ");
            PanamaUtils.nativeObjectToString(getPEER_STREAM_STARTED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("STREAMS_AVAILABLE => ");
            PanamaUtils.nativeObjectToString(getSTREAMS_AVAILABLE(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PEER_NEEDS_STREAMS => ");
            PanamaUtils.nativeObjectToString(getPEER_NEEDS_STREAMS(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IDEAL_PROCESSOR_CHANGED => ");
            PanamaUtils.nativeObjectToString(getIDEAL_PROCESSOR_CHANGED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("DATAGRAM_STATE_CHANGED => ");
            PanamaUtils.nativeObjectToString(getDATAGRAM_STATE_CHANGED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("DATAGRAM_RECEIVED => ");
            PanamaUtils.nativeObjectToString(getDATAGRAM_RECEIVED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("DATAGRAM_SEND_STATE_CHANGED => ");
            PanamaUtils.nativeObjectToString(getDATAGRAM_SEND_STATE_CHANGED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("RESUMED => ");
            PanamaUtils.nativeObjectToString(getRESUMED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("RESUMPTION_TICKET_RECEIVED => ");
            PanamaUtils.nativeObjectToString(getRESUMPTION_TICKET_RECEIVED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PEER_CERTIFICATE_RECEIVED => ");
            PanamaUtils.nativeObjectToString(getPEER_CERTIFICATE_RECEIVED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("RELIABLE_RESET_NEGOTIATED => ");
            PanamaUtils.nativeObjectToString(getRELIABLE_RESET_NEGOTIATED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append(")@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnectionEventUnion> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnectionEventUnion.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnectionEventUnion.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnectionEventUnion.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnectionEventUnion ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventUnion.Array";
        }

        @Override
        protected QuicConnectionEventUnion construct(MemorySegment seg) {
            return new QuicConnectionEventUnion(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnectionEventUnion value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnectionEventUnion> {
        private Func(io.vproxy.pni.CallSite<QuicConnectionEventUnion> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnectionEventUnion> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventUnion> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnectionEventUnion> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnectionEventUnion.Func";
        }

        @Override
        protected QuicConnectionEventUnion construct(MemorySegment seg) {
            return new QuicConnectionEventUnion(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:e56a403348d2dfe1b18fd305724b61b8abfde4c61184df496e8fe87543ded9c6
