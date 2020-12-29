package msquic.vproxy;

import msquic.*;
import msquic.nativevalues.AddressFamily;
import msquic.nativevalues.ConnectionShutdownFlags;
import msquic.nativevalues.SendResumptionFlags;
import vfd.*;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.selector.wrap.VirtualFD;
import vproxybase.util.Comment;
import vproxybase.util.Logger;

import java.io.IOException;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class MsQuicConnectionFD implements ServerSocketFD, SocketFD, VirtualFD {
    private final Configuration conf;
    private final MsQuicListenerFD listenerFD;
    private final boolean isAccepted;
    final Connection conn;
    private final ListenerEvent.NEW_CONNECTION_DATA.NewConnectionInfo info;
    private ConnectionEvent.CONNECTED_DATA connectedData;

    private IPPort localOnInit;
    private IPPort remoteOnInit;

    private IPPort currentLocal;
    private IPPort currentRemote;

    private final LinkedList<MsQuicStreamFD> acceptQueue = new LinkedList<>();

    private SelectorEventLoop loop;
    private boolean closed = false;
    private boolean connectionInitialPacketAlreadySent = false;
    private boolean connected = false;
    private boolean connectedEventFired = false;

    MsQuicConnectionFD(Configuration conf, MsQuicListenerFD listenerFD, Connection acceptedConn,
                       ListenerEvent.NEW_CONNECTION_DATA.NewConnectionInfo info) {
        this.conf = conf;
        this.listenerFD = listenerFD;
        this.isAccepted = true;
        this.connected = true;
        this.conn = acceptedConn;
        this.info = info;

        this.localOnInit = listenerFD.localAddress;

        this.conn.setCallbackHandler(this::connectonCallback);
    }

    public MsQuicConnectionFD(Registration reg, Configuration conf) throws MsQuicException {
        this.conf = conf;
        this.listenerFD = null;
        this.isAccepted = false;
        this.conn = reg.openConnection(this::connectonCallback);
        this.info = null;
    }

    @Comment("only valid if it's accepted, " +
        "the result will be null if it's an active connection")
    public ListenerEvent.NEW_CONNECTION_DATA.NewConnectionInfo getAcceptedConnectionInfo() {
        return info;
    }

    @Comment("the result will be null if it's not connected yet")
    public ConnectionEvent.CONNECTED_DATA getConnectedData() {
        return connectedData;
    }

    @Override
    public void loopAware(SelectorEventLoop loop) {
        assert Logger.lowLevelDebug(this + " loopAware " + loop);
        this.loop = loop;
    }

    private void checkOpen() throws IOException {
        if (closed) throw new IOException("closed");
    }

    private void checkConnected() throws IOException {
        if (!connected) throw new IOException("not connected yet");
    }

    @Override
    public IPPort getLocalAddress() throws IOException {
        checkOpen();
        return localOnInit;
    }

    public IPPort getCurrentLocalAddress() {
        return currentLocal;
    }

    @Override
    public IPPort getRemoteAddress() throws IOException {
        checkOpen();
        return remoteOnInit;
    }

    public IPPort getCurrentRemoteAddress() {
        return currentRemote;
    }

    @Override
    public void bind(IPPort l4addr) {
        // not supported but do not raise exception
        assert Logger.lowLevelDebug("you should use MsQuicListenerFD to start a server");
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
        conn.close();
    }

    @Override
    public void connect(IPPort l4addr) throws IOException {
        checkOpen();
        if (isAccepted) throw new IOException("cannot call connect() on an accepted connection");
        if (connectionInitialPacketAlreadySent) throw new IOException("connection packet is already sent");

        AddressFamily family;
        if (l4addr.getAddress() instanceof IPv4) {
            family = AddressFamily.INET;
        } else if (l4addr.getAddress() instanceof IPv6) {
            family = AddressFamily.INET6;
        } else {
            throw new IOException("unsupported address family for msquic: " + l4addr);
        }
        connectionInitialPacketAlreadySent = true;
        conn.start(conf, family, l4addr.getAddress().formatToIPString(), l4addr.getPort());
        this.remoteOnInit = l4addr;
        connectionInitialPacketAlreadySent = true;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void shutdownOutput() throws IOException {
        shutdownOutput(ConnectionShutdownFlags.NONE, 1);
    }

    public void shutdownOutput(int flags, long errorCode) throws IOException {
        checkOpen();
        checkConnected();
        conn.shutdown(flags, errorCode);
    }

    @Override
    public boolean finishConnect() throws IOException {
        if (!connectionInitialPacketAlreadySent)
            throw new IOException("connection " + conn + " is not trying to start");
        if (connected) throw new IOException("already connected: " + this);
        if (error != null) throw error;
        if (connectedEventFired) {
            connected = true;
            cancelWritable();
        }
        return connected;
    }

    @Override
    public int read(ByteBuffer dst) {
        assert Logger.lowLevelDebug("you should start a MsQuicStreamFD to read data, the read() here is a mock for alerting events");
        if (dst.limit() == dst.position()) {
            return 0;
        }
        // write one byte to trigger event
        dst.put((byte) 0);
        if (acceptQueue.isEmpty()) {
            cancelReadable();
        }
        return 1;
    }

    @Override
    public int write(ByteBuffer src) {
        throw new UnsupportedOperationException("start a MsQuicStreamFD to write data. this fd should be used as a ServerSock");
    }

    // =========
    // impl
    // =========
    private IOException error;

    @Override
    public MsQuicStreamFD accept() throws IOException {
        assert Logger.lowLevelDebug("accept() called on " + this);
        checkOpen();
        checkConnected();
        if (error != null) {
            throw error;
        }
        MsQuicStreamFD streamFD = acceptQueue.pollFirst();
        if (streamFD == null) {
            cancelReadable();
        }
        assert Logger.lowLevelDebug("accept() returns " + streamFD);
        return streamFD;
    }

    private boolean readable = false;

    private void setReadable() {
        assert Logger.lowLevelDebug("set readable");
        readable = true;
        if (loop == null) {
            return;
        }
        loop.runOnLoop(() -> loop.selector.registerVirtualReadable(this));
    }

    private void cancelReadable() {
        assert Logger.lowLevelDebug("cancel readable");
        readable = false;
        if (loop == null) {
            return;
        }
        loop.runOnLoop(() -> loop.selector.removeVirtualReadable(this));
    }

    private boolean writable = false;

    private void setWritable() {
        assert Logger.lowLevelDebug("set writable");
        writable = true;
        if (loop == null) {
            return;
        }
        loop.runOnLoop(() -> loop.selector.registerVirtualWritable(this));
    }

    private void cancelWritable() {
        assert Logger.lowLevelDebug("cancel writable");
        writable = false;
        if (loop == null) {
            return;
        }
        loop.runOnLoop(() -> loop.selector.removeVirtualWritable(this));
    }

    @Override
    public void onRegister() {
        if (readable) {
            setReadable();
        }
        if (writable) {
            setWritable();
        }
    }

    @Override
    public void onRemove() {
        if (acceptQueue.isEmpty()) {
            cancelReadable(); // the readable event might be added when this fd is considered as a Connection
        }
    }

    private void connectonCallback(ConnectionEvent event) throws MsQuicException {
        assert Logger.lowLevelDebug(this + " event: " + event.type);
        switch (event.type) {
            case CONNECTED:
                if (isAccepted) {
                    assert Logger.lowLevelDebug("accepted connection connected callback");
                    conn.sendResumptionTicket(SendResumptionFlags.NONE);
                } else {
                    assert Logger.lowLevelDebug("established connection connected callback");
                }
                connectedEventFired = true;
                this.connectedData = event.CONNECTED;
                if (!isAccepted) {
                    this.localOnInit = new IPPort(conn.getLocalAddress());
                }
                if (isAccepted) {
                    this.remoteOnInit = new IPPort(conn.getRemoteAddress());
                }
                currentLocal = localOnInit;
                currentRemote = remoteOnInit;
                setReadable();
                if (!isAccepted) { // the active connection needs writable event to complete the connection process
                    setWritable();
                }

                if (isAccepted) {
                    listenerFD.pushAcceptableFD(this);
                }

                break;
            case SHUTDOWN_INITIATED_BY_PEER:
                error = new IOException("shutdown initiated by peer");
                setReadable();
                break;
            case SHUTDOWN_INITIATED_BY_TRANSPORT:
                error = new IOException("shutdown initiated by transport");
                setReadable();
                break;
            case LOCAL_ADDRESS_CHANGED:
                var currentLocal = new IPPort(event.LOCAL_ADDRESS_CHANGED.address);
                assert Logger.lowLevelDebug("local changed from " + this.currentLocal + " to " + currentLocal);
                this.currentLocal = currentLocal;
                break;
            case PEER_ADDRESS_CHANGED:
                var currentRemote = new IPPort(event.PEER_ADDRESS_CHANGED.address);
                assert Logger.lowLevelDebug("remote changed from " + this.currentRemote + " to " + currentRemote);
                this.currentRemote = currentRemote;
                break;
            case PEER_STREAM_STARTED:
                peerStreamStarted(event.PEER_STREAM_STARTED);
                break;
        }
    }

    private void peerStreamStarted(ConnectionEvent.PEER_STREAM_STARTED_DATA PEER_STREAM_STARTED) throws MsQuicException {
        assert Logger.lowLevelDebug("peer stream started");
        Stream stream = PEER_STREAM_STARTED.stream;
        MsQuicStreamFD streamFD;
        try {
            streamFD = new MsQuicStreamFD(this, stream);
        } catch (MsQuicException e) {
            assert Logger.lowLevelDebug("creating MsQuicStreamFD failed: " + e);
            stream.close();
            throw e;
        }
        if (loop == null) {
            assert Logger.lowLevelDebug("new stream while loop not set");
            recordAcceptableStream0(streamFD);
        } else {
            assert Logger.lowLevelDebug("new stream on loop");
            loop.runOnLoop(() -> recordAcceptableStream0(streamFD));
        }
    }

    private void recordAcceptableStream0(MsQuicStreamFD streamFD) {
        acceptQueue.add(streamFD);
        setReadable();
    }

    @Override
    public String toString() {
        return "MsQuicConnectionFD(" + conn + ')';
    }
}
