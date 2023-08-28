package io.vproxy.msquic;

import io.vproxy.pni.PNIRef;

import java.lang.foreign.MemorySegment;

public class MsQuicUpcallImpl implements MsQuicUpcall.Interface {
    private MsQuicUpcallImpl() {
    }

    private static final MsQuicUpcallImpl IMPL = new MsQuicUpcallImpl();

    public static MsQuicUpcallImpl get() {
        return IMPL;
    }

    @Override
    public int listenerCallback(MemorySegment Listener, MemorySegment Context, QuicListenerEvent Event) {
        var lsn = PNIRef.<io.vproxy.msquic.wrap.Listener>getRef(Context);
        return lsn.callback(Event);
    }

    @Override
    public int connectionCallback(MemorySegment Connection, MemorySegment Context, QuicConnectionEvent Event) {
        var conn = PNIRef.<io.vproxy.msquic.wrap.Connection>getRef(Context);
        return conn.callback(Event);
    }

    @Override
    public int streamCallback(MemorySegment Stream, MemorySegment Context, QuicStreamEvent Event) {
        var stream = PNIRef.<io.vproxy.msquic.wrap.Stream>getRef(Context);
        return stream.callback(Event);
    }
}
