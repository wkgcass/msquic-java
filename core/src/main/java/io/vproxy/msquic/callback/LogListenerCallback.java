package io.vproxy.msquic.callback;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.base.util.bytearray.MemorySegmentByteArray;
import io.vproxy.msquic.MsQuicUtils;
import io.vproxy.msquic.QuicListenerEvent;
import io.vproxy.msquic.QuicListenerEventNewConnection;
import io.vproxy.msquic.QuicListenerEventStopComplete;
import io.vproxy.msquic.wrap.Listener;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public class LogListenerCallback implements ListenerCallback {
    public static final boolean DEFAULT_VALUE_FOR_WITH_DATA = false;

    private final boolean withData;

    public LogListenerCallback() {
        this(DEFAULT_VALUE_FOR_WITH_DATA);
    }

    public LogListenerCallback(boolean withData) {
        this.withData = withData;
    }

    @Override
    public int newConnection(Listener listener, QuicListenerEventNewConnection data) {
        Logger.trace(LogType.ALERT, "QUIC_LISTENER_EVENT_NEW_CONNECTION: " + listener);
        Logger.trace(LogType.ALERT, "" + data);
        var info = data.getInfo();
        {
            Logger.trace(LogType.ALERT, "LocalAddress: " + MsQuicUtils.convertQuicAddrToIPPort(info.getLocalAddress()));
        }
        {
            Logger.trace(LogType.ALERT, "RemoteAddress: " + MsQuicUtils.convertQuicAddrToIPPort(info.getRemoteAddress()));
        }

        if (!withData) {
            return QUIC_STATUS_NOT_SUPPORTED;
        }

        {
            var buffer = info.getCryptoBuffer().reinterpret(info.getCryptoBufferLength());
            Logger.trace(LogType.ALERT, "CryptoBuffer:\n" + new MemorySegmentByteArray(buffer).hexDump());
        }
        {
            var alpn = info.getClientAlpnList().reinterpret(info.getClientAlpnListLength());
            Logger.trace(LogType.ALERT, "ClientAlpnList:\n" + new MemorySegmentByteArray(alpn).hexDump());
        }
        {
            var negotiatedAlpn = info.getNegotiatedAlpn().reinterpret(info.getNegotiatedAlpnLength());
            Logger.trace(LogType.ALERT, "NegotiatedAlpn:\n" + new MemorySegmentByteArray(negotiatedAlpn).hexDump());
        }
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int stopComplete(Listener listener, QuicListenerEventStopComplete data) {
        Logger.trace(LogType.ALERT, "QUIC_LISTENER_EVENT_STOP_COMPLETE: " + listener);
        Logger.trace(LogType.ALERT, "" + data);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int unknown(Listener listener, QuicListenerEvent event) {
        Logger.trace(LogType.ALERT, "QUIC_LISTENER_EVENT: UNKNOWN " + event.getType() + ": " + listener);
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public void closed(Listener listener) {
        Logger.trace(LogType.ALERT, "QUIC_LISTENER: CLOSED: " + listener);
    }
}
