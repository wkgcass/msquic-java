package io.vproxy.msquic;

import io.vproxy.pni.annotation.Include;
import io.vproxy.pni.annotation.Name;
import io.vproxy.pni.annotation.Struct;
import io.vproxy.pni.annotation.Unsigned;

import java.lang.foreign.MemorySegment;

@Struct(skip = true)
@Include("msquic.h")
@Name("QUIC_BUFFER")
public abstract class PNIQuicBuffer {
    @Unsigned int Length;
    MemorySegment Buffer;
}
