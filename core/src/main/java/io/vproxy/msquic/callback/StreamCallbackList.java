package io.vproxy.msquic.callback;

import io.vproxy.msquic.*;
import io.vproxy.msquic.wrap.Stream;

import java.util.ArrayList;
import java.util.List;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public class StreamCallbackList implements StreamCallback {
    private final List<StreamCallback> callbacks = new ArrayList<>(4);

    public static StreamCallbackList withLog(StreamCallback cb) {
        return withLog(cb, LogStreamCallback.DEFAULT_VALUE_FOR_WITH_DATA);
    }

    public static StreamCallbackList withLog(StreamCallback cb, boolean withLog) {
        if (cb instanceof StreamCallbackList cbls) {
            cbls.callbacks.addFirst(new LogStreamCallback(withLog));
            return cbls;
        } else {
            return new StreamCallbackList()
                .add(new LogStreamCallback())
                .add(cb);
        }
    }

    public static StreamCallback withLogIf(boolean checkRes, StreamCallback cb) {
        return withLogIf(checkRes, cb, LogStreamCallback.DEFAULT_VALUE_FOR_WITH_DATA);
    }

    public static StreamCallback withLogIf(boolean checkRes, StreamCallback cb, boolean withLog) {
        if (checkRes) {
            return withLog(cb, withLog);
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.startComplete(stream, data);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.receive(stream, data);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.sendComplete(stream, data);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.peerSendShutdown(stream);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.peerSendAborted(stream, data);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.peerReceiveAborted(stream, data);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.sendShutdownComplete(stream, data);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.shutdownComplete(stream, data);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.idealSendBufferSize(stream, data);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.peerAccepted(stream);
            if (cb.remove(stream)) {
                ite.remove();
            }
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
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.unknown(stream, event);
            if (cb.remove(stream)) {
                ite.remove();
            }
            if (s != QUIC_STATUS_NOT_SUPPORTED)
                state = s;
            if (invalidState(state))
                return state;
        }
        return state;
    }

    @Override
    public void closed(Stream stream) {
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            cb.closed(stream);
            if (cb.remove(stream)) {
                ite.remove();
            }
        }
    }
}
