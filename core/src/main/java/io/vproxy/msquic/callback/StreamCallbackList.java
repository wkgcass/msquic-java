package io.vproxy.msquic.callback;

import io.vproxy.base.util.Logger;
import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Stream;

import java.util.ArrayList;
import java.util.List;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public class StreamCallbackList implements StreamCallback {
    private final List<StreamCallback> callbacks = new ArrayList<>(4);

    public static StreamCallbackList withLog(StreamCallback cb) {
        if (cb instanceof StreamCallbackList cbls) {
            cbls.callbacks.addFirst(new LogStreamCallback());
            return cbls;
        } else {
            return new StreamCallbackList()
                .add(new LogStreamCallback())
                .add(cb);
        }
    }

    public static StreamCallback withLogIfDebugEnabled(StreamCallback cb) {
        if (Logger.debugOn()) {
            return withLog(cb);
        }
        return cb;
    }

    public StreamCallbackList add(StreamCallback cb) {
        callbacks.add(cb);
        return this;
    }

    private boolean invalidState(int state) {
        return state != 0 && state != QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int startComplete(Stream stream, QuicStreamEventStartComplete data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.startComplete(stream, data);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int receive(Stream stream, QuicStreamEventReceive data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.receive(stream, data);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int sendComplete(Stream stream, QuicStreamEventSendComplete data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.sendComplete(stream, data);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int peerSendShutdown(Stream stream) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.peerSendShutdown(stream);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int peerSendAborted(Stream stream, QuicStreamEventPeerSendAborted data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.peerSendAborted(stream, data);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int peerReceiveAborted(Stream stream, QuicStreamEventPeerReceiveAborted data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.peerReceiveAborted(stream, data);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int sendShutdownComplete(Stream stream, QuicStreamEventSendShutdownComplete data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.sendShutdownComplete(stream, data);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int shutdownComplete(Stream stream, QuicStreamEventShutdownComplete data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.shutdownComplete(stream, data);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int idealSendBufferSize(Stream stream, QuicStreamEventIdealSendBufferSize data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.idealSendBufferSize(stream, data);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int peerAccepted(Stream stream) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.peerAccepted(stream);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public int unknown(Stream stream, QuicStreamEvent event) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var cb : callbacks) {
            int s = cb.unknown(stream, event);
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public void closed(Stream stream) {
        for (var cb : callbacks) {
            cb.closed(stream);
        }
    }
}
