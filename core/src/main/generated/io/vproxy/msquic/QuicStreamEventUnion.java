package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicStreamEventUnion {
    public static final MemoryLayout LAYOUT = MemoryLayout.unionLayout(
        io.vproxy.msquic.QuicStreamEventStartComplete.LAYOUT.withName("START_COMPLETE"),
        io.vproxy.msquic.QuicStreamEventReceive.LAYOUT.withName("RECEIVE"),
        io.vproxy.msquic.QuicStreamEventSendComplete.LAYOUT.withName("SEND_COMPLETE"),
        io.vproxy.msquic.QuicStreamEventPeerSendAborted.LAYOUT.withName("PEER_SEND_ABORTED"),
        io.vproxy.msquic.QuicStreamEventPeerReceiveAborted.LAYOUT.withName("PEER_RECEIVE_ABORTED"),
        io.vproxy.msquic.QuicStreamEventSendShutdownComplete.LAYOUT.withName("SEND_SHUTDOWN_COMPLETE"),
        io.vproxy.msquic.QuicStreamEventShutdownComplete.LAYOUT.withName("SHUTDOWN_COMPLETE"),
        io.vproxy.msquic.QuicStreamEventIdealSendBufferSize.LAYOUT.withName("IDEAL_SEND_BUFFER_SIZE")
    );
    public final MemorySegment MEMORY;

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
        this(ALLOCATOR.allocate(LAYOUT.byteSize()));
    }

    public static class Array extends RefArray<QuicStreamEventUnion> {
        public Array(MemorySegment buf) {
            super(buf, QuicStreamEventUnion.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            this(allocator.allocate(QuicStreamEventUnion.LAYOUT.byteSize() * len));
        }

        public Array(PNIBuf buf) {
            this(buf.get());
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

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicStreamEventUnion> func) {
            return new Func(func);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected QuicStreamEventUnion construct(MemorySegment seg) {
            return new QuicStreamEventUnion(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.8
// sha256:73fccd371ce079195446601495261f19d688ab0a7f778e03f27540f1c6da262f
