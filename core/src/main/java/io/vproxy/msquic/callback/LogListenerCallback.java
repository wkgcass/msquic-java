package io.vproxy.msquic.callback;

import io.vproxy.base.util.ByteArray;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.MsQuicUtils;
import io.vproxy.msquic.QuicListenerEvent;
import io.vproxy.msquic.QuicListenerEventNewConnection;
import io.vproxy.msquic.QuicListenerEventStopComplete;
import io.vproxy.msquic.wrap.Listener;

import java.lang.foreign.ValueLayout;

import static io.vproxy.msquic.MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;

public class LogListenerCallback implements ListenerCallback {
    @Override
    public int newConnection(Listener listener, QuicListenerEventNewConnection data) {
        Logger.alert(STR."QUIC_LISTENER_EVENT_NEW_CONNECTION: \{listener}");
        Logger.alert(STR."\{data}");
        var info = data.getInfo();
        {
            Logger.alert(STR."LocalAddress: \{MsQuicUtils.convertQuicAddrToIPPort(info.getLocalAddress())}");
        }
        {
            Logger.alert(STR."RemoteAddress: \{MsQuicUtils.convertQuicAddrToIPPort(info.getRemoteAddress())}");
        }
        {
            var buffer = info.getCryptoBuffer().reinterpret(info.getCryptoBufferLength());
            var bytes = buffer.toArray(ValueLayout.JAVA_BYTE);
            Logger.alert(STR."CryptoBuffer:\n\{ByteArray.from(bytes).hexDump()}");
        }
        {
            var alpn = info.getClientAlpnList().reinterpret(info.getClientAlpnListLength());
            var bytes = alpn.toArray(ValueLayout.JAVA_BYTE);
            Logger.alert(STR."ClientAlpnList:\n\{ByteArray.from(bytes).hexDump()}");
        }
        {
            var negotiatedAlpn = info.getNegotiatedAlpn().reinterpret(info.getNegotiatedAlpnLength());
            var bytes = negotiatedAlpn.toArray(ValueLayout.JAVA_BYTE);
            Logger.alert(STR."NegotiatedAlpn:\n\{ByteArray.from(bytes).hexDump()}");
        }
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int stopComplete(Listener listener, QuicListenerEventStopComplete data) {
        Logger.alert(STR."QUIC_LISTENER_EVENT_STOP_COMPLETE: \{listener}");
        Logger.alert(STR."\{data}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public int unknown(Listener listener, QuicListenerEvent event) {
        Logger.alert(STR."QUIC_LISTENER_EVENT: UNKNOWN \{event.getType()}: \{listener}");
        return QUIC_STATUS_NOT_SUPPORTED;
    }

    @Override
    public void closed(Listener listener) {
        Logger.alert(STR."QUIC_LISTENER: CLOSED: \{listener}");
    }
}
