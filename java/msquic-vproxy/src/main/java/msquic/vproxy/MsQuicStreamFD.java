package msquic.vproxy;

import msquic.MsQuicException;
import msquic.Stream;
import msquic.StreamEvent;
import msquic.nativevalues.*;
import vfd.FD;
import vfd.IP;
import vfd.IPPort;
import vfd.SocketFD;
import vfd.abs.AbstractBaseFD;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.selector.wrap.VirtualFD;
import vproxybase.util.*;

import java.io.IOException;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class MsQuicStreamFD extends AbstractBaseFD implements SocketFD, VirtualFD {
    private final MsQuicConnectionFD connFD;
    private final boolean isAccepted;
    private Stream stream;

    private final IPPort connLocalOnInit;
    private final IPPort connRemoteOnInit;
    private long streamId;
    private IPPort streamLocalOnInit;
    private IPPort streamRemoteOnInit;

    private SelectorEventLoop loop;
    private boolean closed = false;
    private boolean streamInitialPacketAlreadySent = false;
    private boolean finishConnectDone = false;
    private boolean connected = false;

    public MsQuicStreamFD(MsQuicConnectionFD connFD) throws MsQuicException {
        this.connFD = connFD;
        this.isAccepted = false;

        connLocalOnInit = new IPPort(connFD.conn.getLocalAddress());
        connRemoteOnInit = new IPPort(connFD.conn.getRemoteAddress());

        writeToStreamBuffer = new DirectBuffer(32768);
    }

    MsQuicStreamFD(MsQuicConnectionFD connFD, Stream acceptedStream) throws MsQuicException {
        this.connFD = connFD;
        this.isAccepted = true;
        this.stream = acceptedStream;
        connected = true;

        connLocalOnInit = new IPPort(connFD.conn.getLocalAddress());
        connRemoteOnInit = new IPPort(connFD.conn.getRemoteAddress());

        streamId = acceptedStream.getId();
        streamLocalOnInit = connLocalOnInit;
        streamRemoteOnInit = new IPPort(
            IP.from(ByteArray.allocate(16).int64(8, stream.real).toJavaArray()),
            (int) streamId);

        writeToStreamBuffer = new DirectBuffer(32768);

        acceptedStream.setCallbackHandler(this::streamCallback);
    }

    @Comment("not valid until the stream is started properly")
    public long getStreamId() {
        return streamId;
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
    public void connect(IPPort l4addr) throws IOException { // the address will not be used
        checkOpen();
        if (!connRemoteOnInit.equals(l4addr)) {
            throw new IOException("the input address " + l4addr + " not the same as initial con remote address " + connRemoteOnInit);
        }
        if (isAccepted) throw new IOException("cannot call connect() on an accepted stream");
        if (streamInitialPacketAlreadySent) throw new IOException("stream packet is already sent");
        streamInitialPacketAlreadySent = true;
        stream = connFD.conn.openStream(StreamOpenFlags.NONE, this::streamCallback);
        stream.start(StreamStartFlags.ASYNC);

        streamRemoteOnInit = connRemoteOnInit;
    }

    @Override
    public boolean isConnected() {
        if (isAccepted) {
            return connected;
        } else {
            return connected && finishConnectDone;
        }
    }

    @Override
    public void shutdownOutput() throws IOException {
        checkOpen();
        checkConnected();
        stream.shutdown(StreamShutdownFlags.ABORT_SEND);
    }

    @Override
    public boolean finishConnect() throws IOException {
        if (!streamInitialPacketAlreadySent) throw new IOException("stream " + stream + " is not trying to start");
        if (finishConnectDone) throw new IOException("finish connect already called");
        if (connected) {
            finishConnectDone = true;
        }
        return connected;
    }

    @Override
    public IPPort getLocalAddress() throws IOException {
        checkOpen();
        return streamLocalOnInit;
    }

    @Override
    public IPPort getRemoteAddress() throws IOException {
        checkOpen();
        return streamRemoteOnInit;
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
        return connFD;
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
        try {
            super.close();
        } catch (IOException e) {
            Logger.shouldNotHappen("closing base fd should not fail", e);
        }
        closed = true;
        if (stream != null) {
            stream.close();
        }
        writeToStreamBuffer.clean();
    }

    // ========
    // impl
    // ========
    private boolean readable = false;

    private void setReadable() {
        readable = true;
        if (loop == null) {
            return;
        }
        loop.runOnLoop(() -> loop.selector.registerVirtualReadable(this));
    }

    private void cancelReadable() {
        readable = false;
        if (loop == null) {
            return;
        }
        loop.runOnLoop(() -> loop.selector.removeVirtualReadable(this));
    }

    private boolean writable = false;

    private void setWritable() {
        writable = true;
        if (loop == null) {
            return;
        }
        loop.runOnLoop(() -> loop.selector.registerVirtualWritable(this));
    }

    private void cancelWritable() {
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
        // do nothing
    }

    private final LinkedList<QuicBufferEx> readBuffers = new LinkedList<>();
    private final DirectBuffer writeToStreamBuffer;
    private boolean peerSendShutdown = false;
    private boolean peerAbort = false;

    @Override
    public int read(ByteBuffer dst) throws IOException {
        checkOpen();
        checkConnected();
        if (peerAbort) {
            throw new IOException("Connection reset");
        }

        if (peerSendShutdown && readBuffers.isEmpty()) {
            return -1; // EOF
        }
        if (dst.limit() == dst.position()) {
            return 0; // empty dst
        }
        if (readBuffers.isEmpty()) { // nothing to read
            cancelReadable();
            return 0;
        }
        int oldPos = dst.position();
        while (dst.limit() != dst.position() && !readBuffers.isEmpty()) {
            var buf = readBuffers.peekFirst();
            utilRead(dst, (direct, off, len) -> {
                direct.limit(len).position(0);
                buf.read(direct);
                return direct.position();
            });
            if (buf.used() == 0) {
                readBuffers.pollFirst();
            }
        }
        if (readBuffers.isEmpty()) { // everything fully read
            if (!peerSendShutdown) { // if EOF, do not cancel readable
                cancelReadable();
            }
        }
        int readLen = dst.position() - oldPos;
        stream.receiveComplete(readLen);
        if (readBuffers.isEmpty()) {
            stream.setReceiveEnabled(true);
        }
        return readLen;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        checkOpen();
        checkConnected();
        if (peerAbort) {
            throw new IOException("Connection reset");
        }

        int len = src.limit() - src.position();
        if (len == 0) { // nothing to write
            return 0;
        }
        DirectBuffer.Slice slice = writeToStreamBuffer.allocate(len);
        ByteBuffer b = slice.get();
        if (b == null) { // no space
            cancelWritable();
            return 0;
        }
        int bufLen = b.limit() - b.position();
        int srcLim = src.limit();
        if (bufLen < len) {
            src.limit(src.position() + bufLen);
        }
        b.put(src);
        src.limit(srcLim);

        // check free space
        int freeSpace = writeToStreamBuffer.free();
        if (freeSpace == 0) {
            cancelWritable();
        }

        // send
        int sndLen;
        try {
            sndLen = stream.send(SendFlags.NONE, slice.get());
        } catch (MsQuicException e) {
            Logger.error(LogType.SOCKET_ERROR, "sending data to " + stream + " failed", e);
            throw e;
        }
        assert Logger.lowLevelDebug("sent " + sndLen + " bytes to " + stream);

        return bufLen;
    }

    private void streamCallback(StreamEvent event) throws MsQuicException {
        assert Logger.lowLevelDebug(this + " event: " + event.type);
        switch (event.type) {
            case START_COMPLETE:
                assert Logger.lowLevelDebug("stream " + stream + " start complete");
                streamId = stream.getId();
                streamLocalOnInit = new IPPort(
                    IP.from(ByteArray.allocate(16).int64(8, stream.real).toJavaArray()),
                    (int) streamId);
                connected = true;
                setWritable();
                break;
            case SEND_COMPLETE:
                sendComplete();
                break;
            case RECEIVE:
                receive(event);
                break;
            case PEER_SEND_SHUTDOWN:
                assert Logger.lowLevelDebug("peer send shutdown");
                peerSendShutdown = true;
                setReadable();
                break;
            case PEER_SEND_ABORTED:
            case PEER_RECEIVE_ABORTED:
                assert Logger.lowLevelDebug("peer abort");
                peerAbort = true;
                setReadable();
                setWritable();
                break;
        }
    }

    private void sendComplete() {
        assert Logger.lowLevelDebug("send complete, ready to send more data, setWritable");
        stream.pollWBuf(); // ignore
        if (loop == null) {
            assert Logger.lowLevelDebug("complete when loop not set");
            sendComplete0();
        } else {
            assert Logger.lowLevelDebug("complete on loop");
            loop.runOnLoop(this::sendComplete0);
        }
    }

    private void sendComplete0() {
        writeToStreamBuffer.release();
        setWritable();
    }

    private void receive(StreamEvent event) throws MsQuicException {
        // handle the receive event
        long len = event.RECEIVE.getTotalBufferLength();
        assert Logger.lowLevelDebug("received data " +
            "len=" + len + ", " +
            "absOff=" + event.RECEIVE.absoluteOffset + ", " +
            "buffers=" + event.RECEIVE.buffers + ", " +
            "flags=" + event.RECEIVE.receiveFlags);
        if (len <= 0) {
            if (readBuffers.isEmpty()) {
                return;
            } else {
                throw MsQuicException.PENDING;
            }
        }
        event.RECEIVE.setTotalBufferLength(0); // async, so nothing read in this thread

        boolean received = false;
        for (var buf : event.RECEIVE.buffers) {
            if (buf.length() == 0) {
                continue;
            }
            received = true;
            readBuffers.add(new QuicBufferEx(buf));
        }

        if (loop == null) {
            assert Logger.lowLevelDebug("received message when loop not set");
            receive0(received);
        } else {
            assert Logger.lowLevelDebug("received message on loop");
            final boolean finalReceived = received;
            loop.runOnLoop(() -> receive0(finalReceived));
        }

        throw MsQuicException.PENDING;
    }

    private void receive0(boolean received) {
        if (received) {
            assert Logger.lowLevelDebug("received data");
            setReadable();
        } else {
            assert Logger.lowLevelDebug("RECEIVE event fired but nothing read");
        }
    }

    @Override
    public String toString() {
        return "MsQuicStreamFD(" + stream + ')';
    }
}
