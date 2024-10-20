package io.vproxy.msquic.wrap;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.*;
import io.vproxy.msquic.callback.ConnectionCallback;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;
import io.vproxy.pni.PNIString;
import io.vproxy.pni.array.IntArray;
import io.vproxy.vfd.IPPort;
import io.vproxy.vfd.IPv4;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.*;

public class Connection {
    public final PNIRef<Connection> ref;
    public final Options opts;
    public final QuicConnection connectionQ;
    private boolean connected = false;

    private IPPort remoteAddress;
    private IPPort localAddress;
    private QuicTLSSecret quicTLSSecret;

    public IPPort getRemoteAddress() {
        return remoteAddress;
    }

    public IPPort getLocalAddress() {
        return localAddress;
    }

    public Connection(Options opts) {
        this.ref = PNIRef.of(this);
        this.opts = opts;
        if (opts.connectionQ != null) {
            this.connectionQ = opts.connectionQ;
        } else {
            this.connectionQ = opts.connectionSupplier.apply(ref);
        }
        opts.connectionSupplier = null; // gc

        if (opts.connectionQ != null) {
            refCount.incrementAndGet(); // opts.connectionQ != null means it's an accepted connection
        }
    }

    public void setConnectionInfo(QuicListenerEventNewConnection data) {
        remoteAddress = MsQuicUtils.convertQuicAddrToIPPort(data.getInfo().getRemoteAddress());
        localAddress = MsQuicUtils.convertQuicAddrToIPPort(data.getInfo().getLocalAddress());
    }

    public int start(Configuration conf, IPPort target) {
        refCount.incrementAndGet(); // incr first, in case the callback immediately calls close()

        int res;
        try (var allocator = Allocator.ofConfined()) {
            var remoteAddress = MsQuicUtils.convertIPPortToQuicAddr(target, allocator);

            connectionQ.setParam(
                QUIC_PARAM_CONN_REMOTE_ADDRESS,
                (int) remoteAddress.MEMORY.byteSize(), remoteAddress.MEMORY);

            int addressFamily = target.getAddress() instanceof IPv4
                ? QUIC_ADDRESS_FAMILY_INET
                : QUIC_ADDRESS_FAMILY_INET6;
            res = connectionQ.start(conf.opts.configurationQ, addressFamily,
                target.getAddress().getHostName() == null
                    ? null
                    : new PNIString(allocator, target.getAddress().getHostName()),
                target.getPort());
        }
        if (res != 0) { // res != 0 means starting failed, the callbacks will never be called
            refCount.decrementAndGet(); // only decr, no other actions
        }
        return res;
    }

    public static class OptionsBase extends Registration.OptionsBase {
        public final Listener listener;
        protected final QuicConnection connectionQ;

        public OptionsBase(Registration registration) {
            super(registration.opts);
            this.listener = null;
            this.connectionQ = null;
        }

        public OptionsBase(Listener listener, QuicConnection connectionQ) {
            super(listener.opts);
            this.listener = listener;
            this.connectionQ = connectionQ;
        }

        public OptionsBase(OptionsBase opts) {
            super(opts);
            this.listener = opts.listener;
            this.connectionQ = opts.connectionQ;
        }
    }

    public static class Options extends OptionsBase {
        public final Allocator allocator;
        public final ConnectionCallback callback;
        private Function<PNIRef<Connection>, QuicConnection> connectionSupplier;

        public Options(Registration registration, Allocator allocator, ConnectionCallback callback, Function<PNIRef<Connection>, QuicConnection> connectionSupplier) {
            super(registration);
            this.allocator = allocator;
            this.callback = callback;
            this.connectionSupplier = connectionSupplier;
        }

        public Options(Listener listener, Allocator allocator, ConnectionCallback callback, QuicConnection connectionQ) {
            super(listener, connectionQ);
            this.allocator = allocator;
            this.callback = callback;
            this.connectionSupplier = null;
        }
    }

    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final AtomicInteger refCount = new AtomicInteger(1); // will decrease on closing

    public boolean isClosed() {
        return closed.get();
    }

    public boolean isConnected() {
        return connected;
    }

    public void close() {
        if (closed.get()) {
            return;
        }
        if (!closed.compareAndSet(false, true)) {
            return;
        }

        var ref = refCount.decrementAndGet();
        if (ref > 0) {
            doShutdown();
        } else if (ref == 0) {
            releaseResources();
        }
    }

    private void deRef() {
        close();
        if (refCount.decrementAndGet() == 0) {
            releaseResources();
        }
    }

    private void doShutdown() {
        if (connectionQ != null) {
            connectionQ.shutdown(QUIC_CONNECTION_SHUTDOWN_FLAG_NONE, 0);
        }
    }

    private void releaseResources() {
        if (connectionQ != null) {
            connectionQ.close();
        }
        opts.allocator.close();
        ref.close();
        opts.callback.closed(this);
    }

    // need to override
    public int callback(@SuppressWarnings("unused") QuicConnectionEvent event) {
        return switch (event.getType()) {
            case QUIC_CONNECTION_EVENT_CONNECTED -> {
                var data = event.getUnion().getCONNECTED();
                try (var allocator = Allocator.ofConfined()) {
                    var addr = new QuicAddr(allocator);
                    var size = new IntArray(allocator, 1);
                    size.set(0, (int) QuicAddr.LAYOUT.byteSize());
                    var ok = connectionQ.getParam(QUIC_PARAM_CONN_LOCAL_ADDRESS, size, addr.MEMORY);
                    if (ok == 0) {
                        localAddress = MsQuicUtils.convertQuicAddrToIPPort(addr);
                    } else {
                        Logger.error(LogType.CONN_ERROR, "[MsQuicJava] failed to retrieve local address from connection " + connectionQ.getHandle().address());
                    }
                    ok = connectionQ.getParam(QUIC_PARAM_CONN_REMOTE_ADDRESS, size, addr.MEMORY);
                    if (ok == 0) {
                        remoteAddress = MsQuicUtils.convertQuicAddrToIPPort(addr);
                    } else {
                        Logger.error(LogType.CONN_ERROR, "[MsQuicJava] failed to retrieve remote address from connection " + connectionQ.getHandle().address());
                    }
                }
                connected = true;

                int status = opts.callback.connected(this, data);
                if (status == QUIC_STATUS_NOT_SUPPORTED)
                    status = 0;
                yield status;
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT -> {
                var data = event.getUnion().getSHUTDOWN_INITIATED_BY_TRANSPORT();
                yield opts.callback.shutdownInitiatedByTransport(this, data);
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER -> {
                var data = event.getUnion().getSHUTDOWN_INITIATED_BY_PEER();
                yield opts.callback.shutdownInitiatedByPeer(this, data);
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE -> {
                var data = event.getUnion().getSHUTDOWN_COMPLETE();
                var status = opts.callback.shutdownComplete(this, data);
                if (status == QUIC_STATUS_NOT_SUPPORTED)
                    status = 0;
                deRef();
                yield status;
            }
            case QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED -> {
                var data = event.getUnion().getLOCAL_ADDRESS_CHANGED();
                localAddress = MsQuicUtils.convertQuicAddrToIPPort(data.getAddress());

                var status = opts.callback.localAddressChanged(this, data);
                if (status == QUIC_STATUS_NOT_SUPPORTED)
                    status = 0;
                yield status;
            }
            case QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED -> {
                var data = event.getUnion().getPEER_ADDRESS_CHANGED();
                remoteAddress = MsQuicUtils.convertQuicAddrToIPPort(data.getAddress());

                var status = opts.callback.peerAddressChanged(this, data);
                if (status == QUIC_STATUS_NOT_SUPPORTED)
                    status = 0;
                yield status;
            }
            case QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED -> {
                var data = event.getUnion().getPEER_STREAM_STARTED();
                yield opts.callback.peerStreamStarted(this, data);
            }
            case QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE -> {
                var data = event.getUnion().getSTREAMS_AVAILABLE();
                yield opts.callback.streamsAvailable(this, data);
            }
            case QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS -> {
                var data = event.getUnion().getPEER_NEEDS_STREAMS();
                yield opts.callback.peerNeedsStreams(this, data);
            }
            case QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED -> {
                var data = event.getUnion().getIDEAL_PROCESSOR_CHANGED();
                yield opts.callback.idealProcessorChanged(this, data);
            }
            case QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED -> {
                var data = event.getUnion().getDATAGRAM_STATE_CHANGED();
                yield opts.callback.datagramStateChanged(this, data);
            }
            case QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED -> {
                var data = event.getUnion().getDATAGRAM_RECEIVED();
                yield opts.callback.datagramReceived(this, data);
            }
            case QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED -> {
                var data = event.getUnion().getDATAGRAM_SEND_STATE_CHANGED();
                yield opts.callback.datagramSendStateChanged(this, data);
            }
            case QUIC_CONNECTION_EVENT_RESUMED -> {
                var data = event.getUnion().getRESUMED();
                var state = opts.callback.resumed(this, data);
                if (state == QUIC_STATUS_NOT_SUPPORTED)
                    state = 0;
                yield state;
            }
            case QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED -> {
                var data = event.getUnion().getRESUMPTION_TICKET_RECEIVED();
                yield opts.callback.resumptionTicketReceived(this, data);
            }
            case QUIC_CONNECTION_EVENT_PEER_CERTIFICATE_RECEIVED -> {
                var data = event.getUnion().getPEER_CERTIFICATE_RECEIVED();
                yield opts.callback.peerCertificateReceived(this, data);
            }
            case QUIC_CONNECTION_EVENT_RELIABLE_RESET_NEGOTIATED -> {
                var data = event.getUnion().getRELIABLE_RESET_NEGOTIATED();
                yield opts.callback.reliableResetNegotiated(this, data);
            }
            default -> opts.callback.unknown(this, event);
        };
    }

    public int enableTlsSecretDebug() {
        quicTLSSecret = new QuicTLSSecret(opts.allocator.allocate(QuicTLSSecret.LAYOUT.byteSize()));
        return connectionQ.setParam(QUIC_PARAM_CONN_TLS_SECRETS,
            (int) QuicTLSSecret.LAYOUT.byteSize(), quicTLSSecret.MEMORY);
    }

    public QuicTLSSecret getQuicTLSSecret() {
        return quicTLSSecret;
    }

    @SuppressWarnings("StringTemplateMigration")
    @Override
    public String toString() {
        return "Connection[local=" + (localAddress == null ? "null" : localAddress.formatToIPPortString())
               + " remote=" + (remoteAddress == null ? "null" : remoteAddress.formatToIPPortString())
               + "]@" + Long.toString(connectionQ.MEMORY.address(), 16)
               + (isClosed() ? "[closed]" : "[open]");
    }
}
