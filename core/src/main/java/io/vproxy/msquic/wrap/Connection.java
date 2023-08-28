package io.vproxy.msquic.wrap;

import io.vproxy.msquic.*;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;
import io.vproxy.pni.PNIString;
import io.vproxy.pni.array.IntArray;
import io.vproxy.vfd.IPPort;

import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.*;

public abstract class Connection {
    public final PNIRef<Connection> ref;
    public final QuicApiTable apiTable;
    public final QuicRegistration registration;
    private final Allocator allocator;
    public final QuicConnection connection;

    private IPPort remoteAddress;
    private IPPort localAddress;
    private QuicTLSSecret quicTLSSecret;

    public IPPort getRemoteAddress() {
        return remoteAddress;
    }

    public IPPort getLocalAddress() {
        return localAddress;
    }

    public Connection(QuicApiTable apiTable, QuicRegistration registration, Allocator allocator,
                      Function<PNIRef<Connection>, QuicConnection> connectionSupplier) {
        this.ref = PNIRef.of(this);
        this.apiTable = apiTable;
        this.registration = registration;
        this.allocator = allocator;
        this.connection = connectionSupplier.apply(ref);
    }

    private volatile boolean isClosed = false;
    private volatile boolean connIsClosed = false;

    public void close() {
        if (isClosed) {
            return;
        }
        synchronized (this) {
            if (isClosed) {
                return;
            }
            isClosed = true;
        }

        closeConnection();
        allocator.close();
        ref.close();
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
        if (connection != null) {
            connection.close();
        }
    }

    // need to override
    public int callback(@SuppressWarnings("unused") QuicConnectionEvent event) {
        return switch (event.getType()) {
            case QUIC_CONNECTION_EVENT_CONNECTED -> {
                try (var allocator = Allocator.ofConfined()) {
                    var addr = new QuicAddr(allocator.allocate(sizeofQuicAddr));
                    var size = new IntArray(allocator, 1);
                    size.set(0, sizeofQuicAddr);
                    var ok = apiTable.getParam(connection.getConn(), QUIC_PARAM_CONN_LOCAL_ADDRESS, size, addr.MEMORY);
                    if (ok == 0) {
                        var str = new PNIString(allocator.allocate(64));
                        addr.toString(str);
                        localAddress = new IPPort(str.toString());
                    } else {
                        System.out.println("[MsQuicJava][ERROR] failed to retrieve local address from connection " + connection.getConn().address());
                    }
                    ok = apiTable.getParam(connection.getConn(), QUIC_PARAM_CONN_REMOTE_ADDRESS, size, addr.MEMORY);
                    if (ok == 0) {
                        var str = new PNIString(allocator.allocate(64));
                        addr.toString(str);
                        remoteAddress = new IPPort(str.toString());
                    } else {
                        System.out.println("[MsQuicJava][ERROR] failed to retrieve remote address from connection " + connection.getConn().address());
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

    public int enableTlsSecretDebug() {
        quicTLSSecret = new QuicTLSSecret(allocator.allocate(QuicTLSSecret.LAYOUT.byteSize()));
        return apiTable.setParam(connection.getConn(), QUIC_PARAM_CONN_TLS_SECRETS,
            (int) QuicTLSSecret.LAYOUT.byteSize(), quicTLSSecret.MEMORY);
    }

    public QuicTLSSecret getQuicTLSSecret() {
        return quicTLSSecret;
    }

    @Override
    public String toString() {
        return "Connection[local=" + (localAddress == null ? "null" : localAddress.formatToIPPortString())
               + " remote=" + (remoteAddress == null ? "null" : remoteAddress.formatToIPPortString())
               + "]@" + Long.toString(connection.getConn().address(), 16);
    }
}
