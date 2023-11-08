package io.vproxy.msquic.callback;

import io.vproxy.msquic.QuicListenerEvent;
import io.vproxy.msquic.QuicListenerEventNewConnection;
import io.vproxy.msquic.QuicListenerEventStopComplete;
import io.vproxy.msquic.wrap.Listener;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public interface ListenerCallback {
    default boolean remove(Listener listener) {
        return false;
    }

    default int newConnection(Listener listener, QuicListenerEventNewConnection data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int stopComplete(Listener listener, QuicListenerEventStopComplete data) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default int unknown(Listener listener, QuicListenerEvent event) {
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    default void closed(Listener listener) {
    }
}
