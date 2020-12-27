package msquic.vproxy;

import msquic.*;
import msquic.nativevalues.AddressFamily;
import msquic.nativevalues.ListenerEventType;
import msquic.nativevalues.Status;
import vfd.*;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.selector.wrap.VirtualFD;
import vproxybase.util.Logger;

import java.io.IOException;
import java.net.SocketOption;
import java.util.Collection;
import java.util.LinkedList;

public class MsQuicListenerFD implements ServerSocketFD, VirtualFD {
    private SelectorEventLoop loop;
    private final Configuration conf;
    private final Listener listener;
    private final Collection<String> serverAlpn;

    private final LinkedList<MsQuicConnectionFD> acceptQueue = new LinkedList<>();

    private boolean bond = false;
    private boolean closed = false;
    IPPort localAddress;

    public MsQuicListenerFD(Registration reg,
                            Configuration conf,
                            Collection<String> serverAlpn) throws MsQuicException {
        this.conf = conf;
        this.listener = reg.openListener(this::listenerCallback);
        this.serverAlpn = serverAlpn;
    }

    @Override
    public void loopAware(SelectorEventLoop loop) {
        assert Logger.lowLevelDebug(this + " loopAware " + loop);
        this.loop = loop;
    }

    private void checkOpen() throws IOException {
        if (closed) throw new IOException("closed");
    }

    private void checkBond() throws IOException {
        if (!bond) throw new IOException("bind() not called yet");
    }

    @Override
    public IPPort getLocalAddress() throws IOException {
        checkBond();
        return localAddress;
    }

    @Override
    public MsQuicConnectionFD accept() throws IOException {
        assert Logger.lowLevelDebug("accept() called on " + this);
        checkOpen();
        checkBond();
        MsQuicConnectionFD connFD = acceptQueue.pollFirst();
        if (connFD == null) {
            cancelReadable();
        }
        assert Logger.lowLevelDebug("accept() returns " + connFD);
        return connFD;
    }

    @Override
    public void bind(IPPort l4addr) throws IOException {
        checkOpen();
        if (bond) throw new IOException("already bond");
        Addr addr;
        if (l4addr.getAddress() instanceof IPv4) {
            if (l4addr.getAddress().formatToIPString().equals("127.0.0.1")) {
                addr = new Addr(AddressFamily.INET, true, l4addr.getPort());
            } else {
                addr = new Addr(AddressFamily.INET, false, l4addr.getPort());
            }
        } else if (l4addr.getAddress() instanceof IPv6) {
            if (l4addr.getAddress().formatToIPString().equals("::1")) {
                addr = new Addr(AddressFamily.INET6, true, l4addr.getPort());
            } else {
                addr = new Addr(AddressFamily.INET6, false, l4addr.getPort());
            }
        } else {
            throw new IOException("not valid address for MsQuic: " + l4addr);
        }
        listener.start(serverAlpn, addr);
        localAddress = l4addr;
        bond = true;
    }

    @Override
    public void configureBlocking(boolean b) {
        if (b) throw new UnsupportedOperationException();
    }

    @Override
    public <T> void setOption(SocketOption<T> name, T value) {
        // unsupported, but do not raise exceptions
    }

    @Override
    public FD real() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOpen() {
        return !closed;
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        listener.close();
        MsQuicConnectionFD connFD;
        while ((connFD = acceptQueue.pollFirst()) != null) {
            connFD.close();
        }
    }

    // =========
    // impl
    // =========

    private boolean readable = false;

    private void setReadable() {
        readable = true;
        if (loop == null) {
            return;
        }
        loop.selector.registerVirtualReadable(this);
    }

    private void cancelReadable() {
        readable = false;
        if (loop == null) {
            return;
        }
        loop.selector.removeVirtualReadable(this);
    }

    @Override
    public void onRegister() {
        if (readable) {
            setReadable();
        }
    }

    @Override
    public void onRemove() {
        // do nothing
    }

    private void listenerCallback(ListenerEvent event) throws MsQuicException {
        assert Logger.lowLevelDebug(this + " event: " + event.type);
        if (event.type == ListenerEventType.NEW_CONNECTION) {
            Connection conn = event.NEW_CONNECTION.connection;
            conn.setConfiguration(conf);
            new MsQuicConnectionFD(conf, this, conn, event.NEW_CONNECTION.info);
            return;
        }
        throw new MsQuicException(Status.NOT_SUPPORTED);
    }

    void pushAcceptableFD(MsQuicConnectionFD connFD) {
        if (closed) {
            assert Logger.lowLevelDebug("connection should be accepted but the listener is already closed");
            connFD.close();
            return;
        }
        if (loop == null) {
            assert Logger.lowLevelDebug("no loop when conn accepted");
            pushAcceptableFD0(connFD);
        } else {
            assert Logger.lowLevelDebug("run on loop when conn accepted");
            loop.runOnLoop(() -> pushAcceptableFD0(connFD));
        }
    }

    private void pushAcceptableFD0(MsQuicConnectionFD connFD) {
        acceptQueue.add(connFD);
        setReadable();
    }

    @Override
    public String toString() {
        return "MsQuicListenerFD(" + listener + ')';
    }
}
