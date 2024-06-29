package io.vproxy.msquic.callback;

import io.vproxy.msquic.QuicListenerEvent;
import io.vproxy.msquic.QuicListenerEventNewConnection;
import io.vproxy.msquic.QuicListenerEventStopComplete;
import io.vproxy.msquic.wrap.Listener;

import java.util.ArrayList;
import java.util.List;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public class ListenerCallbackList implements ListenerCallback {
    private final List<ListenerCallback> callbacks = new ArrayList<>(4);

    public static ListenerCallbackList withLog(ListenerCallback cb) {
        return withLog(cb, LogListenerCallback.DEFAULT_VALUE_FOR_WITH_DETAIL);
    }

    public static ListenerCallbackList withLog(ListenerCallback cb, boolean withDetail) {
        if (cb instanceof ListenerCallbackList cbls) {
            cbls.callbacks.addFirst(new LogListenerCallback(withDetail));
            return cbls;
        } else {
            return new ListenerCallbackList()
                .add(new LogListenerCallback(withDetail))
                .add(cb);
        }
    }

    public static ListenerCallback withLogIf(boolean checkRes, ListenerCallback cb) {
        return withLogIf(checkRes, cb, LogListenerCallback.DEFAULT_VALUE_FOR_WITH_DETAIL);
    }

    public static ListenerCallback withLogIf(boolean checkRes, ListenerCallback cb, boolean withDetail) {
        if (checkRes) {
            return withLog(cb, withDetail);
        }
        return cb;
    }

    public ListenerCallbackList add(ListenerCallback cb) {
        callbacks.add(cb);
        return this;
    }

    private boolean invalidState(int state) {
        return state != 0 && state != QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int newConnection(Listener listener, QuicListenerEventNewConnection data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.newConnection(listener, data);
            if (cb.remove(listener)) {
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
    public int stopComplete(Listener listener, QuicListenerEventStopComplete data) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.stopComplete(listener, data);
            if (cb.remove(listener)) {
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
    public int unknown(Listener listener, QuicListenerEvent event) {
        int state = QUIC_STATUS_NOT_SUPPORTED;
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            int s = cb.unknown(listener, event);
            if (cb.remove(listener)) {
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
    public void closed(Listener listener) {
        for (var ite = callbacks.iterator(); ite.hasNext(); ) {
            var cb = ite.next();
            cb.closed(listener);
            if (cb.remove(listener)) {
                ite.remove();
            }
        }
    }
}
