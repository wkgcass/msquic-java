package io.vproxy.msquic.wrap;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.msquic.*;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;
import io.vproxy.pni.PNIString;
import io.vproxy.pni.array.IntArray;
import io.vproxy.vfd.IPPort;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.*;

public abstract class Connection {
    public final PNIRef<Connection> ref;
    public final Options opts;
    public final QuicConnection connectionQ;

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
        private Function<PNIRef<Connection>, QuicConnection> connectionSupplier;

        public Options(Registration registration, Allocator allocator, Function<PNIRef<Connection>, QuicConnection> connectionSupplier) {
            super(registration);
            this.allocator = allocator;
            this.connectionSupplier = connectionSupplier;
        }

        public Options(Listener listener, Allocator allocator, QuicConnection connectionQ) {
            super(listener, connectionQ);
            this.allocator = allocator;
            this.connectionSupplier = null;
        }
    }

    private volatile boolean closed = false;
    private volatile boolean connIsClosed = false;

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

        closeConnection();
        opts.allocator.close();
        ref.close();
        close0();
    }

    protected void close0() {
    }

    public void closeConnection() {
        if (connIsClosed) {
            return;
        }
        synchronized (this) {
            if (connIsClosed) {
                return;
            }
            connIsClosed = true;
        }
        if (connectionQ != null) {
            connectionQ.close();
        }
    }

    // need to override
    public int callback(@SuppressWarnings("unused") QuicConnectionEvent event) {
        if (requireEventLogging()) {
            logEvent(event);
        }

        return switch (event.getType()) {
            case QUIC_CONNECTION_EVENT_CONNECTED -> {
                try (var allocator = Allocator.ofConfined()) {
                    var addr = new QuicAddr(allocator);
                    var size = new IntArray(allocator, 1);
                    size.set(0, (int) QuicAddr.LAYOUT.byteSize());
                    var ok = opts.apiTable.opts.apiTableQ.getParam(connectionQ.getConn(), QUIC_PARAM_CONN_LOCAL_ADDRESS, size, addr.MEMORY);
                    if (ok == 0) {
                        var str = new PNIString(allocator.allocate(64));
                        addr.toString(str);
                        localAddress = new IPPort(str.toString());
                    } else {
                        Logger.alert("[MsQuicJava][ERROR] failed to retrieve local address from connection " + connectionQ.getConn().address());
                    }
                    ok = opts.apiTable.opts.apiTableQ.getParam(connectionQ.getConn(), QUIC_PARAM_CONN_REMOTE_ADDRESS, size, addr.MEMORY);
                    if (ok == 0) {
                        var str = new PNIString(allocator.allocate(64));
                        addr.toString(str);
                        remoteAddress = new IPPort(str.toString());
                    } else {
                        Logger.alert("[MsQuicJava][ERROR] failed to retrieve remote address from connection " + connectionQ.getConn().address());
                    }
                }
                yield 0;
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE -> {
                close();
                yield 0;
            }
            default -> MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;
        };
    }

    protected boolean requireEventLogging() {
        return Logger.debugOn();
    }

    protected Path getSSLKeyLogFilePath() {
        var v = System.getenv("SSLKEYLOGFILE");
        if (v == null)
            return null;
        if (!new File(v).isFile())
            return null;
        return Path.of(v);
    }

    protected void logEvent(QuicConnectionEvent event) {
        Logger.alert("---------- " + Thread.currentThread() + " ----------");
        switch (event.getType()) {
            case QUIC_CONNECTION_EVENT_CONNECTED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_CONNECTED");
                var data = event.getUnion().getCONNECTED();
                {
                    var negotiatedAlpn = data.getNegotiatedAlpn().reinterpret(data.getNegotiatedAlpnLength());
                    Logger.alert("NegotiatedAlpn");
                    Utils.hexDump(negotiatedAlpn);
                }
                {
                    Logger.alert("SessionResumed: " + data.isSessionResumed());
                }
                var SSLKEYLOGFILE = getSSLKeyLogFilePath();
                if (SSLKEYLOGFILE != null) {
                    if (getQuicTLSSecret() != null) {
                        var s = getQuicTLSSecret();
                        Logger.alert("QuicTLSSecret");
                        Utils.hexDump(s.MEMORY);
                        try {
                            Files.writeString(SSLKEYLOGFILE,
                                MsQuicUtils.convertQuicTlsSecretToSSLKEYLOGFILE(s),
                                StandardOpenOption.APPEND);
                            Logger.alert("SSLKEYLOGFILE: " + SSLKEYLOGFILE);
                        } catch (IOException e) {
                            Logger.error(LogType.FILE_ERROR, "failed to write SSLKEYLOGFILE: " + SSLKEYLOGFILE, e);
                        }
                    } else {
                        Logger.alert("QuicTLSSecret: null");
                    }
                }
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT -> {
                Logger.alert("QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_TRANSPORT");
                var data = event.getUnion().getSHUTDOWN_INITIATED_BY_TRANSPORT();
                {
                    Logger.alert("Status: " + data.getStatus());
                    Logger.alert("ErrorCode: " + data.getErrorCode());
                }
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER -> {
                Logger.alert("QUIC_CONNECTION_EVENT_SHUTDOWN_INITIATED_BY_PEER");
                var data = event.getUnion().getSHUTDOWN_INITIATED_BY_PEER();
                {
                    Logger.alert("ErrorCode: " + data.getErrorCode());
                }
            }
            case QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE -> {
                Logger.alert("QUIC_CONNECTION_EVENT_SHUTDOWN_COMPLETE");
                var data = event.getUnion().getSHUTDOWN_COMPLETE();
                {
                    Logger.alert("HandshakeCompleted: " + data.isHandshakeCompleted());
                    Logger.alert("PeerAcknowledgedShutdown: " + data.isPeerAcknowledgedShutdown());
                    Logger.alert("AppCloseInProgress: " + data.isAppCloseInProgress());
                }
            }
            case QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_LOCAL_ADDRESS_CHANGED");
                var data = event.getUnion().getLOCAL_ADDRESS_CHANGED();
                try (var tmpAlloc = Allocator.ofConfined()) {
                    var addr = data.getAddress();
                    var str = new PNIString(tmpAlloc.allocate(64));
                    addr.toString(str);
                    Logger.alert("local address changed: " + addr);
                }
            }
            case QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_PEER_ADDRESS_CHANGED");
                var data = event.getUnion().getPEER_ADDRESS_CHANGED();
                try (var tmpAlloc = Allocator.ofConfined()) {
                    var addr = data.getAddress();
                    var str = new PNIString(tmpAlloc.allocate(64));
                    addr.toString(str);
                    Logger.alert("peer address changed: " + addr);
                }
            }
            case QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_PEER_STREAM_STARTED");
                var data = event.getUnion().getPEER_STREAM_STARTED();
                {
                    Logger.alert("Flags: " + data.getFlags());
                }
                {
                    Logger.alert("Stream: " + data.getStream());
                }
            }
            case QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE -> {
                Logger.alert("QUIC_CONNECTION_EVENT_STREAMS_AVAILABLE");
                var data = event.getUnion().getSTREAMS_AVAILABLE();
                {
                    Logger.alert("BidirectionalCount: " + data.getBidirectionalCount());
                    Logger.alert("UnidirectionalCount: " + data.getUnidirectionalCount());
                }
            }
            case QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS -> {
                Logger.alert("QUIC_CONNECTION_EVENT_PEER_NEEDS_STREAMS");
                var data = event.getUnion().getPEER_NEEDS_STREAMS();
                {
                    Logger.alert("Bidirectional: " + data.isBidirectional());
                }
            }
            case QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_IDEAL_PROCESSOR_CHANGED");
                var data = event.getUnion().getIDEAL_PROCESSOR_CHANGED();
                {
                    Logger.alert("IdealProcessor: " + data.getIdealProcessor());
                    Logger.alert("PartitionIndex: " + data.getPartitionIndex());
                }
            }
            case QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_DATAGRAM_STATE_CHANGED");
                var data = event.getUnion().getDATAGRAM_STATE_CHANGED();
                {
                    Logger.alert("SendEnabled: " + data.isSendEnabled());
                    Logger.alert("MaxSendLength: " + data.getMaxSendLength());
                }
            }
            case QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_DATAGRAM_RECEIVED");
                var data = event.getUnion().getDATAGRAM_RECEIVED();
                {
                    var buffer = data.getBuffer();
                    var seg = buffer.getBuffer().reinterpret(buffer.getLength());
                    Logger.alert("Buffer");
                    Utils.hexDump(seg);
                }
                {
                    Logger.alert("Flags: " + data.getFlags());
                }
            }
            case QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_DATAGRAM_SEND_STATE_CHANGED");
                var data = event.getUnion().getDATAGRAM_SEND_STATE_CHANGED();
                {
                    Logger.alert("ClientContext: " + data.getClientContext());
                    Logger.alert("State: " + data.getState());
                }
            }
            case QUIC_CONNECTION_EVENT_RESUMED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_RESUMED");
                var data = event.getUnion().getRESUMED();
                {
                    int len = data.getResumptionStateLength() & 0xffff;
                    if (len == 0) {
                        Logger.alert("ResumptionState: null");
                    } else {
                        var state = data.getResumptionState().reinterpret(len);
                        Logger.alert("ResumptionState");
                        Utils.hexDump(state);
                    }
                }
            }
            case QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_RESUMPTION_TICKET_RECEIVED");
                var data = event.getUnion().getRESUMPTION_TICKET_RECEIVED();
                {
                    var seg = data.getResumptionTicket().reinterpret(data.getResumptionTicketLength() & 0xffff);
                    Logger.alert("ResumptionTicket");
                    Utils.hexDump(seg);
                }
            }
            case QUIC_CONNECTION_EVENT_PEER_CERTIFICATE_RECEIVED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_PEER_CERTIFICATE_RECEIVED");
                var data = event.getUnion().getPEER_CERTIFICATE_RECEIVED();
                {
                    Logger.alert("Certificate: " + data.getCertificate());
                    Logger.alert("DeferredErrorFlags: " + data.getDeferredErrorFlags());
                    Logger.alert("DeferredStatus: " + data.getDeferredStatus());
                    Logger.alert("Chain: " + data.getChain());
                }
            }
            case QUIC_CONNECTION_EVENT_RELIABLE_RESET_NEGOTIATED -> {
                Logger.alert("QUIC_CONNECTION_EVENT_RELIABLE_RESET_NEGOTIATED");
                var data = event.getUnion().getRELIABLE_RESET_NEGOTIATED();
                {
                    Logger.alert("IsNegotiated: " + data.isIsNegotiated());
                }
            }
            default -> Logger.alert("UNKNOWN CONNECTION EVENT: " + event.getType());
        }
    }

    public int enableTlsSecretDebug() {
        quicTLSSecret = new QuicTLSSecret(opts.allocator.allocate(QuicTLSSecret.LAYOUT.byteSize()));
        return opts.apiTable.opts.apiTableQ.setParam(connectionQ.getConn(), QUIC_PARAM_CONN_TLS_SECRETS,
            (int) QuicTLSSecret.LAYOUT.byteSize(), quicTLSSecret.MEMORY);
    }

    public QuicTLSSecret getQuicTLSSecret() {
        return quicTLSSecret;
    }

    @Override
    public String toString() {
        return "Connection[local=" + (localAddress == null ? "null" : localAddress.formatToIPPortString())
               + " remote=" + (remoteAddress == null ? "null" : remoteAddress.formatToIPPortString())
               + "]@" + Long.toString(connectionQ.getConn().address(), 16)
               + (isClosed() ? "[closed]" : "[open]");
    }
}
