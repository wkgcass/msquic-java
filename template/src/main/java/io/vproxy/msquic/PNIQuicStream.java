package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@Include("msquic.h")
public abstract class PNIQuicStream {
    MemorySegment Api; // QUIC_API_TABLE
    MemorySegment Stream; // HQUIC

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC stream = self->Stream;
            api->StreamClose(stream);
            """
    )
    @Critical
    abstract void close();

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC stream = self->Stream;
                        
            QUIC_STATUS res = api->StreamStart(stream, Flags);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int start(int Flags);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC stream = self->Stream;
                        
            QUIC_STATUS res = api->StreamShutdown(stream, Flags, ErrorCode);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int shutdown(int Flags, long ErrorCode);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC stream = self->Stream;
                        
            QUIC_STATUS res = api->StreamSend(stream, Buffers, BufferCount, Flags, ClientSendContext);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int send(PNIQuicBuffer Buffers, int BufferCount, int Flags, MemorySegment ClientSendContext);

    @Impl(
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC stream = self->Stream;
                        
            api->StreamReceiveComplete(stream, BufferLength);
            """
    )
    @Critical
    abstract void receiveComplete(long BufferLength);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC stream = self->Stream;
                        
            QUIC_STATUS res = api->StreamReceiveSetEnabled(stream, IsEnabled);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int receiveSetEnabled(boolean IsEnabled);
}

@Struct(skip = true)
@Include("msquic.h")
@Name("QUIC_STREAM_EVENT")
abstract class PNIQuicStreamEvent {
    int Type; // QUIC_STREAM_EVENT_TYPE
    PNIQuicStreamEventUnion Union;
}

@Union(embedded = true)
@Include("msquic.h")
abstract class PNIQuicStreamEventUnion {
    PNIQuicStreamEventStartComplete START_COMPLETE;
    PNIQuicStreamEventReceive RECEIVE;
    PNIQuicStreamEventSendComplete SEND_COMPLETE;
    PNIQuicStreamEventPeerSendAborted PEER_SEND_ABORTED;
    PNIQuicStreamEventPeerReceiveAborted PEER_RECEIVE_ABORTED;
    PNIQuicStreamEventSendShutdownComplete SEND_SHUTDOWN_COMPLETE;
    PNIQuicStreamEventShutdownComplete SHUTDOWN_COMPLETE;
    PNIQuicStreamEventIdealSendBufferSize IDEAL_SEND_BUFFER_SIZE;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicStreamEventStartComplete {
    int Status; // QUIC_STATUS
    long ID;
    @BitField(
        name = {"PeerAccepted"},
        bit = {1}
    )
    @Unsigned byte Field01;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicStreamEventReceive {
    @Unsigned long AbsoluteOffset;
    @Unsigned long TotalBufferLength;
    @Pointer PNIQuicBuffer Buffers;
    @Unsigned int BufferCount;
    int Flags; // QUIC_RECEIVE_FLAGS
}

@Struct
@Include("msquic.h")
abstract class PNIQuicStreamEventSendComplete {
    boolean Canceled;
    MemorySegment ClientContext;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicStreamEventPeerSendAborted {
    long ErrorCode;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicStreamEventPeerReceiveAborted {
    long ErrorCode;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicStreamEventSendShutdownComplete {
    boolean Graceful;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicStreamEventShutdownComplete {
    boolean ConnectionShutdown;
    @BitField(
        name = {"AppCloseInProgress", "ConnectionShutdownByApp", "ConnectionClosedRemotely"},
        bit = {1, 1, 1}
    )
    @Unsigned byte Field01;
    long ConnectionErrorCode;
    int ConnectionCloseStatus; // QUIC_STATUS
}

@Struct
@Include("msquic.h")
abstract class PNIQuicStreamEventIdealSendBufferSize {
    @Unsigned long ByteCount;
}
