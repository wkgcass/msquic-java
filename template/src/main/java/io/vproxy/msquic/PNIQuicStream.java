package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@AlwaysAligned
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
    @Style(Styles.critical)
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
    @Style(Styles.critical)
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
    @Style(Styles.critical)
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
    @Style(Styles.critical)
    abstract int send(PNIQuicBuffer Buffers, int BufferCount, int Flags, MemorySegment ClientSendContext);

    @Impl(
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC stream = self->Stream;
                        
            api->StreamReceiveComplete(stream, BufferLength);
            """
    )
    @Style(Styles.critical)
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
    @Style(Styles.critical)
    abstract int receiveSetEnabled(boolean IsEnabled);
}

@Struct(skip = true)
@AlwaysAligned
@Include("msquic.h")
@Name("QUIC_STREAM_EVENT")
abstract class PNIQuicStreamEvent {
    int Type; // QUIC_STREAM_EVENT_TYPE
    PNIQuicStreamEventUnion Union;
}

@Union(embedded = true)
@AlwaysAligned
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
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicStreamEventStartComplete {
    int Status; // QUIC_STATUS
    long ID;
    @Bit({
        @Bit.Field(name = "PeerAccepted", bits = 1, bool = true),
    })
    @Unsigned byte Field01;
}

@Struct
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicStreamEventReceive {
    @Unsigned long AbsoluteOffset;
    @Unsigned long TotalBufferLength;
    @Pointer PNIQuicBuffer Buffers;
    @Unsigned int BufferCount;
    int Flags; // QUIC_RECEIVE_FLAGS
}

@Struct
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicStreamEventSendComplete {
    boolean Canceled;
    MemorySegment ClientContext;
}

@Struct
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicStreamEventPeerSendAborted {
    long ErrorCode;
}

@Struct
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicStreamEventPeerReceiveAborted {
    long ErrorCode;
}

@Struct
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicStreamEventSendShutdownComplete {
    boolean Graceful;
}

@Struct
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicStreamEventShutdownComplete {
    boolean ConnectionShutdown;
    @Bit({
        @Bit.Field(name = "AppCloseInProgress", bits = 1, bool = true),
        @Bit.Field(name = "ConnectionShutdownByApp", bits = 1, bool = true),
        @Bit.Field(name = "ConnectionClosedRemotely", bits = 1, bool = true),
    })
    @Unsigned byte Field01;
    long ConnectionErrorCode;
    int ConnectionCloseStatus; // QUIC_STATUS
}

@Struct
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicStreamEventIdealSendBufferSize {
    @Unsigned long ByteCount;
}
