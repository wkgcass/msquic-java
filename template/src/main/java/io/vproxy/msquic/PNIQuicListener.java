package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@Include("msquic.h")
public abstract class PNIQuicListener {
    MemorySegment Api; // QUIC_API_TABLE
    MemorySegment Lsn; // HQUIC

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC lsn = self->Lsn;
            api->ListenerClose(lsn);
            """
    )
    @Critical
    abstract void close();

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC lsn = self->Lsn;
                        
            QUIC_STATUS res = api->ListenerStart(lsn, AlpnBuffers, AlpnBufferCount, Addr);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int start(@Raw PNIQuicBuffer[] AlpnBuffers, int AlpnBufferCount, PNIQuicAddr Addr);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC lsn = self->Lsn;
            api->ListenerStop(lsn);
            """
    )
    @Critical
    abstract void stop();
}

@Struct(skip = true)
@Include("msquic.h")
@Name("QUIC_LISTENER_EVENT")
abstract class PNIQuicListenerEvent {
    int Type; // QUIC_LISTENER_EVENT_TYPE
    PNIQuicListenerEventUnion Union;
}

@Union(embedded = true)
@Include("msquic.h")
abstract class PNIQuicListenerEventUnion {
    PNIQuicListenerEventNewConnection NEW_CONNECTION;
    PNIQuicListenerEventStopComplete STOP_COMPLETE;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicListenerEventNewConnection {
    @Pointer PNIQuicNewConnectionInfo Info;
    MemorySegment Connection; // HQUIC
}

@Struct(skip = true)
@Include("msquic.h")
@Name("QUIC_NEW_CONNECTION_INFO")
abstract class PNIQuicNewConnectionInfo {
    @Unsigned int QuicVersion;
    @Pointer PNIQuicAddr LocalAddress;
    @Pointer PNIQuicAddr RemoteAddress;
    @Unsigned int CryptoBufferLength;
    @Unsigned short ClientAlpnListLength;
    @Unsigned short ServerNameLength;
    @Unsigned byte NegotiatedAlpnLength;
    MemorySegment CryptoBuffer;
    MemorySegment ClientAlpnList;
    MemorySegment NegotiatedAlpn;
    String ServerName;
}

@Struct
@Include("msquic.h")
abstract class PNIQuicListenerEventStopComplete {
    @BitField(
        name = {"AppCloseInProgress"},
        bit = {1}
    )
    @Unsigned byte Field01;
}
