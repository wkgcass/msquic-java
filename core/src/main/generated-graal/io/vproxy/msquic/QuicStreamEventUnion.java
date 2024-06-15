package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class QuicStreamEventUnion extends AbstractNativeObject implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.unionLayout(
        io.vproxy.msquic.QuicStreamEventStartComplete.LAYOUT.withName("START_COMPLETE"),
        io.vproxy.msquic.QuicStreamEventReceive.LAYOUT.withName("RECEIVE"),
        io.vproxy.msquic.QuicStreamEventSendComplete.LAYOUT.withName("SEND_COMPLETE"),
        io.vproxy.msquic.QuicStreamEventPeerSendAborted.LAYOUT.withName("PEER_SEND_ABORTED"),
        io.vproxy.msquic.QuicStreamEventPeerReceiveAborted.LAYOUT.withName("PEER_RECEIVE_ABORTED"),
        io.vproxy.msquic.QuicStreamEventSendShutdownComplete.LAYOUT.withName("SEND_SHUTDOWN_COMPLETE"),
        io.vproxy.msquic.QuicStreamEventShutdownComplete.LAYOUT.withName("SHUTDOWN_COMPLETE"),
        io.vproxy.msquic.QuicStreamEventIdealSendBufferSize.LAYOUT.withName("IDEAL_SEND_BUFFER_SIZE")
    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    private final io.vproxy.msquic.QuicStreamEventStartComplete START_COMPLETE;

    public io.vproxy.msquic.QuicStreamEventStartComplete getSTART_COMPLETE() {
        return this.START_COMPLETE;
    }

    private final io.vproxy.msquic.QuicStreamEventReceive RECEIVE;

    public io.vproxy.msquic.QuicStreamEventReceive getRECEIVE() {
        return this.RECEIVE;
    }

    private final io.vproxy.msquic.QuicStreamEventSendComplete SEND_COMPLETE;

    public io.vproxy.msquic.QuicStreamEventSendComplete getSEND_COMPLETE() {
        return this.SEND_COMPLETE;
    }

    private final io.vproxy.msquic.QuicStreamEventPeerSendAborted PEER_SEND_ABORTED;

    public io.vproxy.msquic.QuicStreamEventPeerSendAborted getPEER_SEND_ABORTED() {
        return this.PEER_SEND_ABORTED;
    }

    private final io.vproxy.msquic.QuicStreamEventPeerReceiveAborted PEER_RECEIVE_ABORTED;

    public io.vproxy.msquic.QuicStreamEventPeerReceiveAborted getPEER_RECEIVE_ABORTED() {
        return this.PEER_RECEIVE_ABORTED;
    }

    private final io.vproxy.msquic.QuicStreamEventSendShutdownComplete SEND_SHUTDOWN_COMPLETE;

    public io.vproxy.msquic.QuicStreamEventSendShutdownComplete getSEND_SHUTDOWN_COMPLETE() {
        return this.SEND_SHUTDOWN_COMPLETE;
    }

    private final io.vproxy.msquic.QuicStreamEventShutdownComplete SHUTDOWN_COMPLETE;

    public io.vproxy.msquic.QuicStreamEventShutdownComplete getSHUTDOWN_COMPLETE() {
        return this.SHUTDOWN_COMPLETE;
    }

    private final io.vproxy.msquic.QuicStreamEventIdealSendBufferSize IDEAL_SEND_BUFFER_SIZE;

    public io.vproxy.msquic.QuicStreamEventIdealSendBufferSize getIDEAL_SEND_BUFFER_SIZE() {
        return this.IDEAL_SEND_BUFFER_SIZE;
    }

    public QuicStreamEventUnion(MemorySegment MEMORY) {
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        this.START_COMPLETE = new io.vproxy.msquic.QuicStreamEventStartComplete(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventStartComplete.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventStartComplete.LAYOUT.byteSize();
        OFFSET = 0;
        this.RECEIVE = new io.vproxy.msquic.QuicStreamEventReceive(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventReceive.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventReceive.LAYOUT.byteSize();
        OFFSET = 0;
        this.SEND_COMPLETE = new io.vproxy.msquic.QuicStreamEventSendComplete(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventSendComplete.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventSendComplete.LAYOUT.byteSize();
        OFFSET = 0;
        this.PEER_SEND_ABORTED = new io.vproxy.msquic.QuicStreamEventPeerSendAborted(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventPeerSendAborted.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventPeerSendAborted.LAYOUT.byteSize();
        OFFSET = 0;
        this.PEER_RECEIVE_ABORTED = new io.vproxy.msquic.QuicStreamEventPeerReceiveAborted(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventPeerReceiveAborted.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventPeerReceiveAborted.LAYOUT.byteSize();
        OFFSET = 0;
        this.SEND_SHUTDOWN_COMPLETE = new io.vproxy.msquic.QuicStreamEventSendShutdownComplete(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventSendShutdownComplete.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventSendShutdownComplete.LAYOUT.byteSize();
        OFFSET = 0;
        this.SHUTDOWN_COMPLETE = new io.vproxy.msquic.QuicStreamEventShutdownComplete(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventShutdownComplete.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventShutdownComplete.LAYOUT.byteSize();
        OFFSET = 0;
        this.IDEAL_SEND_BUFFER_SIZE = new io.vproxy.msquic.QuicStreamEventIdealSendBufferSize(MEMORY.asSlice(OFFSET, io.vproxy.msquic.QuicStreamEventIdealSendBufferSize.LAYOUT.byteSize()));
        OFFSET += io.vproxy.msquic.QuicStreamEventIdealSendBufferSize.LAYOUT.byteSize();
        OFFSET = 0;
    }

    public QuicStreamEventUnion(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        CORRUPTED_MEMORY = true;
        SB.append("QuicStreamEventUnion(\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("START_COMPLETE => ");
            PanamaUtils.nativeObjectToString(getSTART_COMPLETE(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("RECEIVE => ");
            PanamaUtils.nativeObjectToString(getRECEIVE(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SEND_COMPLETE => ");
            PanamaUtils.nativeObjectToString(getSEND_COMPLETE(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PEER_SEND_ABORTED => ");
            PanamaUtils.nativeObjectToString(getPEER_SEND_ABORTED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("PEER_RECEIVE_ABORTED => ");
            PanamaUtils.nativeObjectToString(getPEER_RECEIVE_ABORTED(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SEND_SHUTDOWN_COMPLETE => ");
            PanamaUtils.nativeObjectToString(getSEND_SHUTDOWN_COMPLETE(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("SHUTDOWN_COMPLETE => ");
            PanamaUtils.nativeObjectToString(getSHUTDOWN_COMPLETE(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append(",\n");
        {
            SB.append(" ".repeat(INDENT + 4)).append("IDEAL_SEND_BUFFER_SIZE => ");
            PanamaUtils.nativeObjectToString(getIDEAL_SEND_BUFFER_SIZE(), SB, INDENT + 4, VISITED, CORRUPTED_MEMORY);
        }
        SB.append("\n");
        SB.append(" ".repeat(INDENT)).append(")@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicStreamEventUnion> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventUnion.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicStreamEventUnion.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicStreamEventUnion.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicStreamEventUnion ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventUnion.Array";
        }

        @Override
        protected QuicStreamEventUnion construct(MemorySegment seg) {
            return new QuicStreamEventUnion(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicStreamEventUnion value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicStreamEventUnion> {
        private Func(io.vproxy.pni.CallSite<QuicStreamEventUnion> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicStreamEventUnion> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventUnion> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventUnion> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicStreamEventUnion.Func";
        }

        @Override
        protected QuicStreamEventUnion construct(MemorySegment seg) {
            return new QuicStreamEventUnion(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:89dbe0da6993513d86acc57f9700c214b45323143fa12dd3d1ec1cc00cae5de7
