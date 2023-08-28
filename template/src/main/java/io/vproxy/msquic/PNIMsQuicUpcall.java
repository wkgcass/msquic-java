package io.vproxy.msquic;

import io.vproxy.pni.annotation.Include;
import io.vproxy.pni.annotation.Upcall;

import java.lang.foreign.MemorySegment;

@Upcall
@Include("msquic.h")
public interface PNIMsQuicUpcall {
    int listenerCallback(MemorySegment Listener, MemorySegment Context, PNIQuicListenerEvent Event);

    int connectionCallback(MemorySegment Connection, MemorySegment Context, PNIQuicConnectionEvent Event);

    int streamCallback(MemorySegment Stream, MemorySegment Context, PNIQuicStreamEvent Event);
}
