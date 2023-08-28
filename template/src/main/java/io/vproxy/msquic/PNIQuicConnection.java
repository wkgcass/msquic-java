package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@Include("msquic.h")
public abstract class PNIQuicConnection {
    MemorySegment Api; // QUIC_API_TABLE
    MemorySegment Conn; // HQUIC

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conn = self->Conn;
            api->ConnectionClose(conn);
            """
    )
    @Critical
    abstract void close();

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conn = self->Conn;
                        
            api->ConnectionShutdown(conn, Flags, ErrorCode);
            """
    )
    @Critical
    abstract void shutdown(int Flags, long ErrorCode);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conn = self->Conn;
            HQUIC conf = Conf->Conf;
                        
            QUIC_STATUS res = api->ConnectionStart(conn, conf, Family, ServerName, ServerPort);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int start(PNIQuicConfiguration Conf, int Family, String ServerName, int ServerPort);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conn = self->Conn;
            HQUIC conf = Conf->Conf;
                        
            QUIC_STATUS res = api->ConnectionSetConfiguration(conn, conf);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int setConfiguration(PNIQuicConfiguration Conf);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conn = self->Conn;
                        
            QUIC_STATUS res = api->ConnectionSendResumptionTicket(conn, Flags, DataLength, ResumptionData);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int sendResumptionTicket(int Flags, int DataLength, @Raw byte[] ResumptionData);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conn = self->Conn;
                        
            HQUIC stream;
            QUIC_STATUS res = api->StreamOpen(conn, Flags,
                    (QUIC_STREAM_CALLBACK_HANDLER) Handler,
                    Context, &stream);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->Api = api;
                return_->Stream = stream;
                return return_;
            }
            return NULL;
            """
    )
    @Critical
    abstract PNIQuicStream openStream(int Flags, MemorySegment Handler, MemorySegment Context, @Raw int[] returnStatus);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conn = self->Conn;
                        
            QUIC_STATUS res = api->DatagramSend(conn, Buffers, BufferCount, Flags, ClientSendContext);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int sendDatagram(PNIQuicBuffer Buffers, int BufferCount, int Flags, MemorySegment ClientSendContext);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conn = self->Conn;
                        
            QUIC_STATUS res = api->ConnectionResumptionTicketValidationComplete(conn, Result);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int resumptionTicketValidationComplete(boolean Result);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conn = self->Conn;
                        
            QUIC_STATUS res = api->ConnectionCertificateValidationComplete(conn, Result, TlsAlert);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int certificateValidationComplete(boolean Result, int TlsAlert);
}

@Struct(skip = true)
@Include("msquic.h")
@Name("QUIC_CONNECTION_EVENT")
abstract class PNIQuicConnectionEvent {
    int Type; // QUIC_CONNECTION_EVENT_TYPE
    PNIQuicConnectionEventUnion Union;
}

@Union(embedded = true)
@Include("msquic.h")
abstract class PNIQuicConnectionEventUnion {
    PNIQuicConnectionEventConnected CONNECTED;
    PNIQuicConnectionEventShutdownInitiatedByTransport SHUTDOWN_INITIATED_BY_TRANSPORT;
    PNIQuicConnectionEventShutdownInitiatedByPeer SHUTDOWN_INITIATED_BY_PEER;
    PNIQuicConnectionEventConnectionShutdownComplete SHUTDOWN_COMPLETE;
    PNIQuicConnectionEventLocalAddressChanged LOCAL_ADDRESS_CHANGED;
    PNIQuicConnectionEventPeerAddressChanged PEER_ADDRESS_CHANGED;
    PNIQuicConnectionEventPeerStreamStarted PEER_STREAM_STARTED;
    PNIQuicConnectionEventStreamsAvailable STREAMS_AVAILABLE;
    PNIQuicConnectionEventPeerNeedsStreams PEER_NEEDS_STREAMS;
    PNIQuicConnectionEventIdealProcessorChanged IDEAL_PROCESSOR_CHANGED;
    PNIQuicConnectionEventDatagramStateChanged DATAGRAM_STATE_CHANGED;
    PNIQuicConnectionEventDatagramReceived DATAGRAM_RECEIVED;
    PNIQuicConnectionEventDatagramSendStateChanged DATAGRAM_SEND_STATE_CHANGED;
    PNIQuicConnectionEventResumed RESUMED;
    PNIQuicConnectionEventResumptionTicketReceived RESUMPTION_TICKET_RECEIVED;
    PNIQuicConnectionEventPeerCertificateReceived PEER_CERTIFICATE_RECEIVED;
    PNIQuicConnectionEventReliableResetNegotiated RELIABLE_RESET_NEGOTIATED;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventConnected {
    boolean SessionResumed;
    @Unsigned byte NegotiatedAlpnLength;
    MemorySegment NegotiatedAlpn;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventShutdownInitiatedByTransport {
    int Status; // QUIC_STATUS
    long ErrorCode;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventShutdownInitiatedByPeer {
    long ErrorCode;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventConnectionShutdownComplete {
    @BitField(
        name = {"HandshakeCompleted", "PeerAcknowledgedShutdown", "AppCloseInProgress"},
        bit = {1, 1, 1}
    )
    @Unsigned byte Field01;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventLocalAddressChanged {
    @Pointer PNIQuicAddr Address;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventPeerAddressChanged {
    @Pointer PNIQuicAddr Address;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventPeerStreamStarted {
    MemorySegment Stream; // HQUIC
    int Flags; // QUIC_STREAM_OPEN_FLAGS
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventStreamsAvailable {
    @Unsigned short BidirectionalCount;
    @Unsigned short UnidirectionalCount;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventPeerNeedsStreams {
    boolean Bidirectional;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventIdealProcessorChanged {
    @Unsigned short IdealProcessor;
    @Unsigned short PartitionIndex;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventDatagramStateChanged {
    boolean SendEnabled;
    @Unsigned short MaxSendLength;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventDatagramReceived {
    PNIQuicBuffer Buffer;
    int Flags; // QUIC_RECEIVE_FLAGS
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventDatagramSendStateChanged {
    MemorySegment ClientContext;
    int State; // QUIC_DATAGRAM_SEND_STATE
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventResumed {
    @Unsigned short ResumptionStateLength;
    MemorySegment ResumptionState;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventResumptionTicketReceived {
    @Unsigned int ResumptionTicketLength;
    MemorySegment ResumptionTicket;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventPeerCertificateReceived {
    MemorySegment Certificate;
    @Unsigned int DeferredErrorFlags;
    int DeferredStatus; // QUIC_STATUS
    MemorySegment Chain; // QUIC_CERTIFICATE_CHAIN
}

@Struct
@Include("msquic.h")
abstract class PNIQuicConnectionEventReliableResetNegotiated {
    boolean IsNegotiated;
}
