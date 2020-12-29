package msquic.vproxy;

import msquic.*;
import vfd.EventSet;
import vfd.IPPort;
import vproxybase.selector.Handler;
import vproxybase.selector.HandlerContext;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.util.Callback;
import vproxybase.util.LogType;
import vproxybase.util.Logger;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SimpleMsQuicConnectionManager {
    private static final ConcurrentHashMap<MGRRef, SimpleMsQuicConnectionManager> managers = new ConcurrentHashMap<>();

    public static SimpleMsQuicConnectionManager connect(SelectorEventLoop loop,
                                                        IPPort remote, Registration reg, Configuration conf) {
        var ref = new MGRRef(loop, remote, reg, conf);
        return managers.computeIfAbsent(ref, x -> new SimpleMsQuicConnectionManager(ref));
    }

    private final MGRRef ref;
    private MsQuicConnectionFD connFD;
    private boolean closed;

    private final ConcurrentLinkedQueue<Callback<MsQuicConnectionFD, IOException>> pendingCallbacks = new ConcurrentLinkedQueue<>();

    private SimpleMsQuicConnectionManager(MGRRef ref) {
        assert Logger.lowLevelDebug("creating SimpleMsQuicConnectionManager: " + ref);
        this.ref = ref;
    }

    private boolean started = false;
    private boolean connected = false;
    private final Object connectedLock = new Object();
    private boolean closing = false;

    public void get(Callback<MsQuicConnectionFD, IOException> cb) {
        if (closed) {
            assert Logger.lowLevelDebug("the manager is already closed");
            cb.failed(new IOException("closed"));
            return;
        }

        if (!started) {
            synchronized (this) {
                if (!started) {
                    assert Logger.lowLevelDebug("start the SimpleMsQuicConnectionManager");

                    started = true;
                    MsQuicConnectionFD connFD;
                    try {
                        connFD = new MsQuicConnectionFD(ref.reg, ref.conf);
                    } catch (MsQuicException e) {
                        assert Logger.lowLevelDebug("failed creating connection fd: " + e);
                        cb.failed(e);
                        return;
                    }

                    try {
                        connFD.connect(ref.remote);
                    } catch (IOException e) {
                        assert Logger.lowLevelDebug("failed to connect to " + ref.remote + ": " + e);
                        cb.failed(e);
                        return;
                    }

                    try {
                        ref.loop.add(connFD, EventSet.write(), null, new ConnectionHandler());
                    } catch (IOException e) {
                        Logger.error(LogType.EVENT_LOOP_ADD_FAIL, "adding into event loop failed", e);
                        cb.failed(e);
                        return;
                    }

                    this.connFD = connFD;
                }
            }
        }

        if (!connected) {
            synchronized (connectedLock) {
                if (!connected) {
                    assert Logger.lowLevelDebug("pending callback");
                    pendingCallbacks.add(cb);
                    return;
                }
            }
        }

        assert Logger.lowLevelDebug("already connected, direct run the callback");
        cb.succeeded(connFD);
    }

    public void close() {
        // alert pending events to shutdown
        if (!pendingCallbacks.isEmpty()) {
            IOException err = new IOException("quic connection closed");
            Callback<MsQuicConnectionFD, IOException> cb;
            while ((cb = pendingCallbacks.poll()) != null) {
                cb.failed(err);
            }
        }
        // run the poll process before closed checking
        if (closed) {
            return;
        }
        closed = true;

        // remove from static map
        managers.remove(ref);

        assert Logger.lowLevelDebug("close called");
        if (connFD != null) {
            assert Logger.lowLevelDebug("connFD not null, close it");
            closing = true;
            ref.loop.remove(connFD);
            connFD.close();
            connFD = null;
        } else {
            assert Logger.lowLevelDebug("connFD is null, do nothing");
        }
    }

    private static class MGRRef {
        final SelectorEventLoop loop;
        final IPPort remote;
        final Registration reg;
        final Configuration conf;

        private MGRRef(SelectorEventLoop loop, IPPort remote, Registration reg, Configuration conf) {
            this.loop = loop;
            this.remote = remote;
            this.reg = reg;
            this.conf = conf;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MGRRef mgrRef = (MGRRef) o;
            return Objects.equals(loop, mgrRef.loop) &&
                Objects.equals(remote, mgrRef.remote) &&
                Objects.equals(reg, mgrRef.reg) &&
                Objects.equals(conf, mgrRef.conf);
        }

        @Override
        public int hashCode() {
            return Objects.hash(loop, remote, reg, conf);
        }

        @Override
        public String toString() {
            return "{" +
                "loop=" + loop +
                ", remote=" + remote +
                ", reg=" + reg +
                ", conf=" + conf +
                '}';
        }
    }

    private class ConnectionHandler implements Handler<MsQuicConnectionFD> {
        @Override
        public void accept(HandlerContext<MsQuicConnectionFD> ctx) {
            while (true) {
                MsQuicStreamFD streamFD;
                try {
                    streamFD = connFD.accept();
                } catch (IOException e) {
                    assert Logger.lowLevelDebug("connection " + ctx.getChannel() + " accept() got exception: " + e);
                    close();
                    return;
                }
                if (streamFD == null) { // done
                    assert Logger.lowLevelDebug("accept() returns null");
                    return;
                }

                Logger.warn(LogType.INVALID_EXTERNAL_DATA, "accepted StreamFD from SimpleMsQuicConnectionManager, " +
                    "and is unable to be handled, the stream " + streamFD + " will be closed");
                streamFD.close();
            }
        }

        @Override
        public void connected(HandlerContext<MsQuicConnectionFD> ctx) {
            boolean finishConnect;
            try {
                finishConnect = connFD.finishConnect();
            } catch (IOException e) {
                assert Logger.lowLevelDebug("connection " + ctx.getChannel() + " finishConnect() got exception: " + e);
                close();
                return;
            }
            if (!finishConnect) {
                Logger.shouldNotHappen("finishConnect returns false, the event should not fire");
                close();
                return;
            }

            assert Logger.lowLevelDebug("initiate stream to " + ref.remote + " with " + connFD);
            SimpleMsQuicConnectionManager.this.connFD = ctx.getChannel();
            synchronized (connectedLock) {
                connected = true;
            }

            Callback<MsQuicConnectionFD, IOException> cb;
            while ((cb = pendingCallbacks.poll()) != null) {
                cb.succeeded(connFD);
            }
        }

        @Override
        public void readable(HandlerContext<MsQuicConnectionFD> ctx) {
            // will not fire
        }

        @Override
        public void writable(HandlerContext<MsQuicConnectionFD> ctx) {
            // will not fire
        }

        @Override
        public void removed(HandlerContext<MsQuicConnectionFD> ctx) {
            if (closing) { // expected to be removed
                return;
            }
            Logger.error(LogType.IMPROPER_USE, "the connFD " + connFD + " removed from loop, the manager will be closed");
            close();
        }
    }
}
