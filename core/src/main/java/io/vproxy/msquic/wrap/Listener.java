package io.vproxy.msquic.wrap;

import io.vproxy.base.util.Logger;
import io.vproxy.msquic.*;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;
import io.vproxy.pni.PNIString;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.QUIC_LISTENER_EVENT_NEW_CONNECTION;
import static io.vproxy.msquic.MsQuicConsts.QUIC_LISTENER_EVENT_STOP_COMPLETE;

public abstract class Listener {
    public final PNIRef<Listener> ref;
    public final Options opts;
    public final QuicListener listenerQ;

    public Listener(Options opts) {
        this.ref = PNIRef.of(this);
        this.opts = opts;
        this.listenerQ = opts.listenerSupplier.apply(ref);
        opts.listenerSupplier = null; // gc
    }

    public static class OptionsBase extends Registration.OptionsBase {
        public final Registration registration;

        public OptionsBase(Registration registration) {
            super(registration.opts);
            this.registration = registration;
        }
    }

    public static class Options extends OptionsBase {
        private final Allocator allocator;
        private Function<PNIRef<Listener>, QuicListener> listenerSupplier;

        public Options(Registration registration, Allocator allocator,
                       Function<PNIRef<Listener>, QuicListener> listenerSupplier) {
            super(registration);
            this.allocator = allocator;
            this.listenerSupplier = listenerSupplier;
        }
    }

    private volatile boolean closed = false;
    private volatile boolean lsnIsClosed = false;

    public boolean isClosed() {
        return closed;
    }

    public void close() {
        if (closed) {
            return;
        }
        synchronized (this) {
            if (closed) {
                return;
            }
            closed = true;
        }

        closeListener();
        opts.allocator.close();
        ref.close();
        close0();
    }

    protected void close0() {
    }

    public void closeListener() {
        if (lsnIsClosed) {
            return;
        }
        synchronized (this) {
            if (lsnIsClosed) {
                return;
            }
            lsnIsClosed = true;
        }
        if (listenerQ != null) {
            listenerQ.close();
        }
    }

    // need to override
    public int callback(QuicListenerEvent event) {
        if (requireEventLogging()) {
            logEvent(event);
        }

        //noinspection SwitchStatementWithTooFewBranches
        return switch (event.getType()) {
            case QUIC_LISTENER_EVENT_STOP_COMPLETE -> {
                close();
                yield 0;
            }
            default -> MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;
        };
    }

    protected boolean requireEventLogging() {
        return Logger.debugOn();
    }

    protected void logEvent(QuicListenerEvent event) {
        Logger.alert("---------- " + Thread.currentThread() + " ----------");
        switch (event.getType()) {
            case QUIC_LISTENER_EVENT_NEW_CONNECTION -> {
                Logger.alert("QUIC_LISTENER_EVENT_NEW_CONNECTION");
                var data = event.getUnion().getNEW_CONNECTION();
                var info = data.getInfo();
                {
                    Logger.alert("QuicVersion: " + info.getQuicVersion());
                }
                try (var allocator = Allocator.ofConfined()) {
                    var addr = new PNIString(allocator.allocate(64));
                    info.getLocalAddress().toString(addr);
                    Logger.alert("LocalAddress: " + addr);
                }
                try (var allocator = Allocator.ofConfined()) {
                    var addr = new PNIString(allocator.allocate(64));
                    info.getRemoteAddress().toString(addr);
                    Logger.alert("RemoteAddress: " + addr);
                }
                {
                    var buffer = info.getCryptoBuffer().reinterpret(info.getCryptoBufferLength());
                    Logger.alert("CryptoBuffer:");
                    Utils.hexDump(buffer);
                }
                {
                    var alpn = info.getClientAlpnList().reinterpret(info.getClientAlpnListLength());
                    Logger.alert("ClientAlpnList:");
                    Utils.hexDump(alpn);
                }
                {
                    var serverName = info.getServerName().MEMORY.reinterpret(info.getServerNameLength());
                    byte[] bytes = new byte[(int) serverName.byteSize()];
                    MemorySegment.ofArray(bytes).copyFrom(serverName);
                    Logger.alert("ServerName: " + new String(bytes));
                }
                {
                    var negotiatedAlpn = info.getNegotiatedAlpn().reinterpret(info.getNegotiatedAlpnLength());
                    Logger.alert("NegotiatedAlpn:");
                    Utils.hexDump(negotiatedAlpn);
                }
                {
                    var conn = data.getConnection();
                    Logger.alert("Connection: " + conn);
                }
            }
            case QUIC_LISTENER_EVENT_STOP_COMPLETE -> {
                Logger.alert("QUIC_LISTENER_EVENT_STOP_COMPLETE");
                {
                    var appCloseInProgress = event.getUnion().getSTOP_COMPLETE().isAppCloseInProgress();
                    Logger.alert("AppCloseInProgress: " + appCloseInProgress);
                }
            }
            default -> Logger.alert("UNKNOWN LISTENER EVENT: " + event.getType());
        }
    }
}
