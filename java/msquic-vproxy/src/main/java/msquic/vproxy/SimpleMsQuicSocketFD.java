package msquic.vproxy;

import msquic.*;
import vfd.EventSet;
import vfd.FD;
import vfd.IPPort;
import vfd.SocketFD;
import vproxybase.selector.Handler;
import vproxybase.selector.HandlerContext;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.selector.wrap.VirtualFD;
import vproxybase.util.Callback;
import vproxybase.util.LogType;
import vproxybase.util.Logger;

import java.io.IOException;
import java.net.SocketOption;
import java.nio.ByteBuffer;

public class SimpleMsQuicSocketFD implements SocketFD, VirtualFD {
    private MsQuicStreamFD streamFD;
    private final SelectorEventLoop loop;
    private final Registration reg;
    private final Configuration conf;

    private IPPort remote;

    private IOException error;
    private boolean closed = false;
    private boolean proxyEvents = false;

    public SimpleMsQuicSocketFD(SelectorEventLoop loop, Registration reg, Configuration conf) {
        this.loop = loop;
        this.reg = reg;
        this.conf = conf;
    }

    @Override
    public void connect(IPPort l4addr) {
        remote = l4addr;
        SimpleMsQuicConnectionManager.connect(loop, l4addr, reg, conf).get(new Callback<>() {
            @Override
            protected void onSucceeded(MsQuicConnectionFD connFD) {
                MsQuicStreamFD streamFD;
                try {
                    streamFD = new MsQuicStreamFD(connFD) {
                        @Override
                        protected void setReadable() {
                            if (proxyEvents) {
                                SimpleMsQuicSocketFD.this.setReadable();
                            } else {
                                super.setReadable();
                            }
                        }

                        @Override
                        protected void cancelReadable() {
                            if (proxyEvents) {
                                SimpleMsQuicSocketFD.this.cancelReadable();
                            } else {
                                super.cancelReadable();
                            }
                        }

                        @Override
                        protected void setWritable() {
                            if (proxyEvents) {
                                SimpleMsQuicSocketFD.this.setWritable();
                            } else {
                                super.setWritable();
                            }
                        }

                        @Override
                        protected void cancelWritable() {
                            if (proxyEvents) {
                                SimpleMsQuicSocketFD.this.cancelWritable();
                            } else {
                                super.cancelWritable();
                            }
                        }
                    };
                } catch (MsQuicException e) {
                    assert Logger.lowLevelDebug("initiating MsQuicStreamFD failed: " + e);
                    error = e;
                    setReadable();
                    setWritable();
                    return;
                }

                // needs connecting
                try {
                    streamFD.connect(l4addr);
                } catch (IOException e) {
                    assert Logger.lowLevelDebug("connecting " + l4addr + " failed: " + e);
                    error = e;
                    setReadable();
                    setWritable();

                    streamFD.close();
                    return;
                }

                try {
                    loop.add(streamFD, EventSet.write(), null, new StreamHandler());
                } catch (IOException e) {
                    Logger.error(LogType.EVENT_LOOP_ADD_FAIL, "adding fd " + streamFD + " to loop failed", e);
                    error = e;
                    setReadable();
                    setWritable();

                    streamFD.close();
                    return;
                }

                SimpleMsQuicSocketFD.this.streamFD = streamFD;
            }

            @Override
            protected void onFailed(IOException err) {
                error = err;
                setReadable();
                setWritable();
            }
        });
    }

    private void checkOpen() throws IOException {
        if (closed) throw new IOException("closed");
    }

    private void checkStream() throws IOException {
        if (streamFD == null) throw new IOException("stream not ready");
    }

    private void checkError() throws IOException {
        if (error != null) throw error;
    }

    @Override
    public boolean isConnected() {
        return streamFD != null && streamFD.isConnected();
    }

    @Override
    public void shutdownOutput() throws IOException {
        checkOpen();
        checkStream();
        streamFD.shutdownOutput();
    }

    @Override
    public boolean finishConnect() throws IOException {
        checkOpen();
        checkError();
        checkStream();
        assert Logger.lowLevelDebug("finishConnect() called");
        return streamFD.finishConnect();
    }

    @Override
    public IPPort getLocalAddress() throws IOException {
        checkOpen();
        return streamFD == null ? null : streamFD.getLocalAddress();
    }

    @Override
    public IPPort getRemoteAddress() throws IOException {
        checkOpen();
        return remote;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        checkOpen();
        checkError();
        checkStream();
        return streamFD.read(dst);
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        checkOpen();
        checkError();
        checkStream();
        return streamFD.write(src);
    }

    @Override
    public void configureBlocking(boolean b) {
        if (b) throw new UnsupportedOperationException();
    }

    @Override
    public <T> void setOption(SocketOption<T> name, T value) {
        // not supported, but do not raise exception
    }

    @Override
    public FD real() {
        return streamFD;
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
        if (streamFD != null) {
            loop.remove(streamFD);
            streamFD.close();
        }
    }

    private boolean readable = false;

    private void setReadable() {
        assert Logger.lowLevelDebug("set readable");
        readable = true;
        loop.runOnLoop(() -> loop.selector.registerVirtualReadable(this));
    }

    private void cancelReadable() {
        assert Logger.lowLevelDebug("cancel readable");
        readable = false;
        loop.runOnLoop(() -> loop.selector.removeVirtualReadable(this));
    }

    private boolean writable = false;

    private void setWritable() {
        assert Logger.lowLevelDebug("set writable");
        writable = true;
        loop.runOnLoop(() -> loop.selector.registerVirtualWritable(this));
    }

    private void cancelWritable() {
        assert Logger.lowLevelDebug("cancel writable");
        writable = false;
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
        // ignore
    }

    private class StreamHandler implements Handler<MsQuicStreamFD> {
        @Override
        public void accept(HandlerContext<MsQuicStreamFD> ctx) {
            // will not fire
        }

        @Override
        public void connected(HandlerContext<MsQuicStreamFD> ctx) {
            assert Logger.lowLevelDebug("finishConnect() succeeded, remove fd from loop");
            loop.remove(streamFD);
            proxyEvents = true;

            setWritable();
        }

        @Override
        public void readable(HandlerContext<MsQuicStreamFD> ctx) {
            // ignore, will be removed after connected
        }

        @Override
        public void writable(HandlerContext<MsQuicStreamFD> ctx) {
            // ignore, will be removed after connected
        }

        @Override
        public void removed(HandlerContext<MsQuicStreamFD> ctx) {
            assert Logger.lowLevelDebug(streamFD + " removed from loop");
        }
    }
}
