package msquic.vproxy;

import msquic.*;
import msquic.nativevalues.AddressFamily;
import msquic.nativevalues.ConnectionEventType;
import msquic.nativevalues.ConnectionShutdownFlags;
import msquic.nativevalues.SendResumptionFlags;
import vfd.*;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.selector.wrap.AbstractBaseVirtualServerSocketFD;
import vproxybase.selector.wrap.AbstractBaseVirtualSocketFD;
import vproxybase.selector.wrap.VirtualFD;
import vproxybase.util.Comment;
import vproxybase.util.LogType;
import vproxybase.util.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MsQuicConnectionFD extends AbstractBaseVirtualServerSocketFD<MsQuicStreamFD> implements ServerSocketFD, SocketFD, VirtualFD {
    private final MsQuicConnectionVirtualSocketFD asSocket;

    private final Configuration conf;
    private final MsQuicListenerFD listenerFD;
    private final boolean isAccepted;
    final Connection conn;
    private final ListenerEvent.NEW_CONNECTION_DATA.NewConnectionInfo info;
    private ConnectionEvent.CONNECTED_DATA connectedData;

    private IPPort currentLocal;
    private IPPort currentRemote;

    MsQuicConnectionFD(Configuration conf, MsQuicListenerFD listenerFD, Connection acceptedConn,
                       ListenerEvent.NEW_CONNECTION_DATA.NewConnectionInfo info) {
        super();

        this.conf = conf;
        this.listenerFD = listenerFD;
        this.isAccepted = true;
        this.conn = acceptedConn;
        this.info = info;

        this.conn.setCallbackHandler(this::connectonCallback);

        asSocket = new MsQuicConnectionVirtualSocketFD(true, listenerFD.localAddress, null);
    }

    public MsQuicConnectionFD(Registration reg, Configuration conf) throws MsQuicException {
        super();

        this.conf = conf;
        this.listenerFD = null;
        this.isAccepted = false;
        this.conn = reg.openConnection(this::connectonCallback);
        this.info = null;

        asSocket = new MsQuicConnectionVirtualSocketFD(false, null, null);
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
    public boolean loopAware(SelectorEventLoop loop) {
        if (!super.loopAware(loop)) {
            return false;
        }
        return asSocket.loopAware(loop);
    }

    public IPPort getCurrentLocalAddress() {
        return currentLocal;
    }

    @Override
    public IPPort getRemoteAddress() throws IOException {
        checkOpen();
        return asSocket.getRemoteAddress();
    }

    public IPPort getCurrentRemoteAddress() {
        return currentRemote;
    }

    @Override
    public void close() {
        try {
            super.close();
        } catch (Throwable t) {
            Logger.shouldNotHappen("closing MsQuicConnectionFD asServerSocket failed", t);
        }
        try {
            asSocket.close();
        } catch (Throwable t) {
            Logger.shouldNotHappen("closing MsQuicConnectionFD asSocket failed", t);
        }
    }

    @Override
    protected void doClose() {
        conn.shutdown(ConnectionShutdownFlags.NONE, 0);
    }

    @Override
    public void connect(IPPort l4addr) throws IOException {
        asSocket.connect(l4addr);
    }

    @Override
    public boolean isConnected() {
        return asSocket.isConnected();
    }

    @Override
    public void shutdownOutput() {
        throw new UnsupportedOperationException();
    }

    public void shutdown(int flags, long errorCode) throws IOException {
        checkOpen();
        conn.shutdown(flags, errorCode);
    }

    @Override
    public boolean finishConnect() throws IOException {
        return asSocket.finishConnect();
    }

    @Override
    public int read(ByteBuffer dst) {
        assert Logger.lowLevelDebug("you should start a MsQuicStreamFD to read data, the read() here is a mock for alerting events");
        try {
            return asSocket.read(dst);
        } catch (IOException e) {
            Logger.error(LogType.IMPROPER_USE, "reading from MsQuicConnectionFD failed, " +
                "this fd only supports reading 1 byte for triggering events", e);
            return 0;
        }
    }

    @Override
    public int write(ByteBuffer src) {
        throw new UnsupportedOperationException("start a MsQuicStreamFD to write data. this fd should be used as a ServerSock");
    }

    @Override
    public void onRegister() {
        super.onRegister();
        if (asSocket.isOpen()) {
            asSocket.onRegister();
        }
    }

    private void connectonCallback(ConnectionEvent event) throws MsQuicException {
        assert Logger.lowLevelDebug(this + " event: " + event.type);

        if (!isOpen()) {
            assert Logger.lowLevelDebug("the connection is closed, event still firing: " + event.type);
            // ignore events except SHUTDOWN_COMPLETE
            if (event.type != ConnectionEventType.SHUTDOWN_COMPLETE) {
                return;
            }
        }

        switch (event.type) {
            case CONNECTED:
                if (isAccepted) {
                    assert Logger.lowLevelDebug("accepted connection connected callback");
                    conn.sendResumptionTicket(SendResumptionFlags.NONE);
                } else {
                    assert Logger.lowLevelDebug("established connection connected callback");
                }
                this.connectedData = event.CONNECTED;
                currentLocal = new IPPort(conn.getLocalAddress());
                currentRemote = new IPPort(conn.getRemoteAddress());

                if (isAccepted) {
                    asSocket.setRemote(currentRemote);
                    listenerFD.pushAcceptableFD(this);
                    setReadable(); // let user read one byte from the fd to finish the SocketFD part
                } else {
                    asSocket.alertConnected(new IPPort(conn.getLocalAddress()));
                }

                break;
            case SHUTDOWN_INITIATED_BY_PEER:
                var error = new IOException("shutdown initiated by peer");
                raiseErrorOneTime(error);
                asSocket.raiseError(error);
                break;
            case SHUTDOWN_INITIATED_BY_TRANSPORT:
                error = new IOException("shutdown initiated by transport");
                raiseErrorOneTime(error);
                asSocket.raiseError(error);
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
            case SHUTDOWN_COMPLETE:
                assert Logger.lowLevelDebug("shutdown complete");
                if (isOpen()) {
                    assert Logger.lowLevelDebug("the connection is not closed yet, " +
                        "it's shutdown unexpectedly");
                    raiseErrorOneTime(new IOException("shutdown unexpectedly"));
                } else {
                    assert Logger.lowLevelDebug("the connectionFD is already closed, " +
                        "do close the msquic connection");
                    conn.close();
                }
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
        newAcceptableFD(streamFD);
    }

    @Override
    protected String formatToString() {
        return "MsQuicConnectionFD(" + conn + ")[" + (isOpen() ? "open" : "closed") + "]";
    }

    private class MsQuicConnectionVirtualSocketFD extends AbstractBaseVirtualSocketFD {
        public MsQuicConnectionVirtualSocketFD(boolean isAccepted, IPPort local, IPPort remote) {
            super(MsQuicConnectionFD.this, isAccepted, local, remote);
        }

        @Override
        public void checkConnected() throws IOException {
            super.checkConnected();
        }

        private IPPort remote;

        @Override
        public void setRemote(IPPort remote) {
            super.setRemote(remote);
            this.remote = remote;
        }

        @Override
        public IPPort getRemoteAddress() {
            return remote;
        }

        @Override
        public void alertConnected(IPPort local) {
            super.alertConnected(local);
        }

        @Override
        public void connect(IPPort l4addr) throws IOException {
            super.connect(l4addr);

            AddressFamily family;
            if (l4addr.getAddress() instanceof IPv4) {
                family = AddressFamily.INET;
            } else if (l4addr.getAddress() instanceof IPv6) {
                family = AddressFamily.INET6;
            } else {
                throw new IOException("unsupported address family for msquic: " + l4addr);
            }
            conn.start(conf, family, l4addr.getAddress().formatToIPString(), l4addr.getPort());
        }

        @Override
        public boolean finishConnect() throws IOException {
            boolean ret = super.finishConnect();
            if (ret) {
                // this fd will not be considered as SocketFD anymore
                asSocket.close();
            }
            return ret;
        }

        @Override
        public void raiseError(IOException err) {
            if (isOpen()) {
                super.raiseError(err);
            }
        }

        @Override
        protected boolean noDataToRead() {
            return false; // will not be called
        }

        @Override
        public int read(ByteBuffer dst) throws IOException {
            super.read(dst);

            if (dst.limit() == dst.position()) {
                return 0;
            }
            // write one byte to trigger event
            dst.put((byte) 0);
            if (acceptQueueIsEmpty()) {
                cancelReadable();
            }
            // this fd will not be considered as SocketFD anymore
            asSocket.close();
            return 1;
        }

        @Override
        protected int doRead(ByteBuffer dst) {
            return 0; // will not be called
        }

        @Override
        protected boolean noSpaceToWrite() {
            return false; // will not be called
        }

        @Override
        protected int doWrite(ByteBuffer src) {
            return 0; // will not be called
        }

        @Override
        protected void doClose(boolean reset) {
            cancelWritable(); // writable should not fire anymore
        }

        @Override
        protected String formatToString() {
            return "MsQuicConnectionVirtualSocketFD(" + conn + ")[" + (isOpen() ? "open" : "closed") + "]"; // only for debug purpose
        }
    }
}
