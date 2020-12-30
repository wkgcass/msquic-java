package msquic.vproxy;

import msquic.MsQuicException;
import msquic.QuicBuffer;
import msquic.Stream;
import msquic.StreamEvent;
import msquic.nativevalues.*;
import vfd.FD;
import vfd.IP;
import vfd.IPPort;
import vfd.SocketFD;
import vproxybase.selector.wrap.AbstractBaseVirtualSocketFD;
import vproxybase.selector.wrap.VirtualFD;
import vproxybase.util.*;
import vproxybase.util.promise.Promise;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class MsQuicStreamFD extends AbstractBaseVirtualSocketFD implements SocketFD, VirtualFD {
    private final MsQuicConnectionFD connFD;
    private Stream stream;
    private volatile boolean msquicStreamIsAlreadyShutDown = false;
    private Callback<Void, Throwable> closingCallback = null;

    private long streamId;

    private final Lock shutdownLock = Lock.create();

    @SuppressWarnings("RedundantThrows")
    public MsQuicStreamFD(MsQuicConnectionFD connFD) throws MsQuicException {
        super(false, null, null);
        this.connFD = connFD;
        writeToStreamBuffer = new DirectBuffer(16384);
    }

    MsQuicStreamFD(MsQuicConnectionFD connFD, Stream acceptedStream) throws MsQuicException {
        super(true,
            new IPPort(connFD.conn.getLocalAddress()),
            new IPPort(
                IP.from(ByteArray.allocate(16).int64(8, acceptedStream.real).toJavaArray()),
                (int) acceptedStream.getId()));

        this.connFD = connFD;
        this.stream = acceptedStream;

        streamId = acceptedStream.getId();

        writeToStreamBuffer = new DirectBuffer(16384);

        acceptedStream.setCallbackHandler(this::streamCallback);
    }

    @Comment("not valid until the stream is started properly")
    public long getStreamId() {
        return streamId;
    }

    public IPPort getConnectionCurrentRemoteAddress() {
        return connFD.getCurrentRemoteAddress();
    }

    public IPPort getConnectionCurrentLocalAddress() {
        return connFD.getCurrentLocalAddress();
    }

    @Override
    public void connect(IPPort l4addr) throws IOException { // the address will not be used
        super.connect(l4addr);
        stream = connFD.conn.openStream(StreamOpenFlags.NONE, this::streamCallback);
        stream.start(StreamStartFlags.ASYNC);
    }

    @Override
    public void shutdownOutput() throws IOException {
        super.shutdownOutput();
        stream.send(SendFlags.FIN, null);
    }

    @Override
    public FD real() {
        return connFD;
    }

    @Override
    public void close() {
        if (stream == null) {
            assert Logger.lowLevelDebug("stream not initiated when the fd is closed");
            super.close();
            return;
        }
        assert Logger.lowLevelDebug("async close the stream to prevent native buffer released while still in use");
        asyncClose(reset -> {
            //noinspection unused
            try (var x = shutdownLock.lock()) {
                if (msquicStreamIsAlreadyShutDown) {
                    assert Logger.lowLevelDebug("the stream is already shutdown, directly release the streamFD");
                    doClose(true);
                    return Promise.resolve(null);
                }
                try {
                    stream.shutdown(reset ? StreamShutdownFlags.ABORT : StreamShutdownFlags.GRACEFUL);
                } catch (MsQuicException e) {
                    assert Logger.lowLevelDebug("shutdown stream when closing got exception: " + e);
                    stream.close(); // directly close to prevent the fd from leaking
                    return Promise.resolve(null);
                }
                var tup = Promise.<Void>todo();
                closingCallback = tup.right;
                return tup.left;
            }
        });
    }

    @Override
    protected void doClose(boolean reset) {
        assert Logger.lowLevelDebug("closing " + this);
        if (stream != null) {
            if (msquicStreamIsAlreadyShutDown) {
                assert Logger.lowLevelDebug("already shutdown, so close stream now");
                stream.close();
            } else {
                assert Logger.lowLevelDebug("try to shutdown the stream first");
                try {
                    stream.shutdown(reset ? StreamShutdownFlags.ABORT : StreamShutdownFlags.GRACEFUL);
                } catch (MsQuicException e) {
                    assert Logger.lowLevelDebug("shutdown stream when closing got exception: " + e);
                    stream.close(); // directly close to prevent the fd from leaking
                }
            }
        }
        releaseBuffers();
    }

    private void releaseBuffers() {
        writeToStreamBuffer.clean();
    }

    private final LinkedList<QuicBuffer> readBuffers = new LinkedList<>();
    private final DirectBuffer writeToStreamBuffer;

    @Override
    protected boolean noDataToRead() {
        return readBuffers.isEmpty();
    }

    @Override
    protected int doRead(ByteBuffer dst) throws IOException {
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
        int readLen = dst.position() - oldPos;
        stream.receiveComplete(readLen);
        if (readBuffers.isEmpty()) {
            stream.setReceiveEnabled(true);
        }
        return readLen;
    }

    @Override
    protected boolean noSpaceToWrite() {
        return writeToStreamBuffer.free() == 0;
    }

    @Override
    protected int doWrite(ByteBuffer src) throws IOException {
        int len = src.limit() - src.position();
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

        // send
        int sndLen;
        try {
            sndLen = stream.send(SendFlags.NONE, slice.get());
        } catch (MsQuicException e) {
            Logger.error(LogType.SOCKET_ERROR, "sending data to " + stream + " failed", e);
            throw e;
        }
        assert Logger.lowLevelDebug("sent " + sndLen + " bytes to " + stream);
        assert sndLen == bufLen;

        return bufLen;
    }

    private void streamCallback(StreamEvent event) throws MsQuicException {
        assert Logger.lowLevelDebug(this + " event: " + event.type);

        if (!isOpen()) {
            assert Logger.lowLevelDebug("the stream is closed, event still firing: " + event.type);
            // ignore events except SHUTDOWN_COMPLETE
            if (event.type != StreamEventType.SHUTDOWN_COMPLETE) {
                return;
            }
        }

        switch (event.type) {
            case START_COMPLETE:
                assert Logger.lowLevelDebug("stream " + stream + " start complete");
                streamId = stream.getId();
                alertConnected(new IPPort(
                    IP.from(ByteArray.allocate(16).int64(8, stream.real).toJavaArray()),
                    (int) streamId));
                break;
            case SEND_COMPLETE:
                sendComplete();
                break;
            case RECEIVE:
                receive(event);
                break;
            case PEER_SEND_SHUTDOWN:
                assert Logger.lowLevelDebug("peer send shutdown");
                setEof();
                break;
            case PEER_SEND_ABORTED:
            case PEER_RECEIVE_ABORTED:
                assert Logger.lowLevelDebug("peer abort");
                raiseError(new IOException("Connection reset"));
                break;
            case SHUTDOWN_COMPLETE:
                //noinspection unused
                try (var x = shutdownLock.lock()) {
                    assert Logger.lowLevelDebug("shutdown complete");
                    msquicStreamIsAlreadyShutDown = true; // then it will be directly close when close() is called
                    if (isOpen()) {
                        assert Logger.lowLevelDebug("the stream is not closed yet, " +
                            "maybe the shutdown is because of the connection closing/shutting-down");
                        raiseError(new IOException("depended connection shutdown/closed"));
                    } else {
                        assert Logger.lowLevelDebug("the stream is already closed, " +
                            "do close the stream");
                        stream.close();
                        if (closingCallback == null) {
                            Logger.shouldNotHappen("the SHUTDOWN_COMPLETE event is lost, " +
                                "stream " + stream + " will never be closed until the connection " + connFD + " closes " +
                                "and garbage collected");
                        } else {
                            assert Logger.lowLevelDebug("callback exists, do final release and invoke the callback");
                            releaseBuffers();
                            closingCallback.succeeded();
                        }
                    }
                }
                break;
            /*
             * about SHUTDOWN_COMPLETE:
             * tracing through the msquic code, I find that if ConnectionClose is called, the related streams are closed automatically
             *
             * here's the trace
             *
             * ConnectionClose
             * MsQuicConnectionClose
             * QuicConnCloseHandle (QUIC_CLOSE_SILENT | QUIC_CLOSE_QUIC_STATUS)
             * QuicConnCloseLocally
             * QuicConnTryClose
             * if (IsFirstCloseForConnection)
             *     QuicStreamSetShutdown
             *     QuicStreamShutdown (
             *         QUIC_STREAM_SHUTDOWN_FLAG_ABORT_SEND |
             *         QUIC_STREAM_SHUTDOWN_FLAG_ABORT_RECEIVE |
             *         QUIC_STREAM_SHUTDOWN_SILENT)
             *     QuicStreamSendShutdown (Stream->Flags.LocalCloseAcked = TRUE) then QuicStreamRecvShutdown (Stream->Flags.RemoteCloseAcked = TRUE)
             *     if (Silent)
             *         QuicStreamTryCompleteShutdown
             *         if (Stream->Flags.LocalCloseAcked && Stream->Flags.RemoteCloseAcked) {
             *             QuicStreamIndicateShutdownComplete
             *             Event.Type = QUIC_STREAM_EVENT_SHUTDOWN_COMPLETE
             */
        }
    }

    private void sendComplete() {
        assert Logger.lowLevelDebug("send complete, ready to send more data, setWritable");
        stream.pollWBuf(); // ignore
        tryToRunOnLoop(() -> {
            writeToStreamBuffer.release();
            setWritable();
        });
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
            readBuffers.add(buf);
        }

        assert Logger.lowLevelDebug("do received message");
        final boolean finalReceived = received;
        tryToRunOnLoop(() -> receive0(finalReceived));

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
    protected String formatToString() {
        return "MsQuicStreamFD(" + stream + ")[" + (isOpen() ? "open" : "closed") + "]";
    }
}
