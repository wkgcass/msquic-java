package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct(skip = true)
@AlwaysAligned
@Include("msquic.h")
@Name("QUIC_BUFFER")
public abstract class PNIQuicBuffer {
    @Unsigned int Length;
    MemorySegment Buffer;
}
