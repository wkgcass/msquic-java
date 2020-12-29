package msquic.vproxy;

import msquic.Configuration;
import msquic.MsQuicException;
import msquic.Registration;
import vfd.*;
import vproxybase.selector.Handler;
import vproxybase.selector.HandlerContext;
import vproxybase.selector.SelectorEventLoop;
import vproxybase.selector.wrap.VirtualFD;
import vproxybase.util.LogType;
import vproxybase.util.Logger;

import java.io.IOException;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.util.*;

public class SimpleMsQuicServerSocketFD implements ServerSocketFD, VirtualFD {
    private final SelectorEventLoop loop;
    private final MsQuicListenerFD listenerFD;
    private final Map<MsQuicConnectionFD, ConnectionHandler> connFDHandlers = new HashMap<>();

    private IPPort bindingAddress;

    public SimpleMsQuicServerSocketFD(SelectorEventLoop loop,
                                      Registration reg,
                                      Configuration conf,
                                      Collection<String> serverAlpn) throws MsQuicException {
        this.loop = loop;
        this.listenerFD = new MsQuicListenerFD(reg, conf, serverAlpn);
    }

    @Override
    public IPPort getLocalAddress() throws IOException {
        return listenerFD.getLocalAddress();
    }

    @Override
    public SocketFD accept() {
        for (var h : connFDHandlers.values()) {
            if (h.acceptQueues.isEmpty()) continue;
            return h.acceptQueues.pollFirst();
        }
        // nothing returned
        cancelReadable();
        return null;
    }

    @Override
    public void bind(IPPort l4addr) throws IOException {
        listenerFD.bind(l4addr);
        try {
            loop.add(listenerFD, EventSet.read(), null, new ListenerHandler());
        } catch (IOException e) {
            Logger.error(LogType.EVENT_LOOP_ADD_FAIL, "bind() failed when trying to add " + listenerFD + " into loop", e);
            listenerFD.close();
            throw e;
        }
        bindingAddress = l4addr;
    }

    @Override
    public void configureBlocking(boolean b) {
        listenerFD.configureBlocking(b);
    }

    @Override
    public <T> void setOption(SocketOption<T> name, T value) {
        listenerFD.setOption(name, value);
    }

    @Override
    public FD real() {
        return listenerFD;
    }

    @Override
    public boolean isOpen() {
        return listenerFD.isOpen();
    }

    @Override
    public void close() {
        if (!isOpen()) {
            return;
        }
        loop.remove(listenerFD);
        listenerFD.close();

        // release the queues
        while (!connFDHandlers.isEmpty()) {
            var connFDSet = new HashSet<>(connFDHandlers.keySet());
            for (var connFD : connFDSet) {
                closeConn(connFD);
            }
        }
    }

    private void closeConn(MsQuicConnectionFD connFD) {
        loop.remove(connFD);

        var handler = connFDHandlers.get(connFD);
        if (handler == null) {
            Logger.shouldNotHappen("connection handler of conn " + connFD + " not found");
            return;
        }

        handler.closing = true;
        connFD.close();

        // release the stream fd
        MsQuicStreamFD streamFD;
        while ((streamFD = handler.acceptQueues.pollFirst()) != null) {
            streamFD.close();
        }
    }

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

    // it should be invoked in the same thread (the loop)
    // so it can be shared for multiple connection fds
    static final ByteBuffer consumerForConnFD = ByteBuffer.allocate(1);

    private class ListenerHandler implements Handler<MsQuicListenerFD> {
        @Override
        public void accept(HandlerContext<MsQuicListenerFD> ctx) {
            while (true) {
                MsQuicConnectionFD connFD;
                try {
                    connFD = ctx.getChannel().accept();
                } catch (IOException e) {
                    Logger.error(LogType.SERVER_ACCEPT_FAIL, "accepting listener fd " + listenerFD + " failed", e);
                    return;
                }
                if (connFD == null) { // done
                    return;
                }

                consumerForConnFD.limit(1).position(0);
                connFD.read(consumerForConnFD);

                // use connFD as server socket fd now

                try {
                    connFD.bind(bindingAddress);
                } catch (IOException e) {
                    Logger.shouldNotHappen("binding MsQuicConnectionFD failed", e);
                    connFD.close();
                    return;
                }

                var handler = new ConnectionHandler(connFD);
                connFDHandlers.put(connFD, handler);
                try {
                    loop.add(connFD, EventSet.read(), null, handler);
                } catch (IOException e) {
                    Logger.error(LogType.EVENT_LOOP_ADD_FAIL, "bind() failed when trying to add " + connFD + " into loop", e);
                    connFD.close();
                    return;
                }
            }
        }

        @Override
        public void connected(HandlerContext<MsQuicListenerFD> ctx) {
            // will not trigger
        }

        @Override
        public void readable(HandlerContext<MsQuicListenerFD> ctx) {
            // will not trigger
        }

        @Override
        public void writable(HandlerContext<MsQuicListenerFD> ctx) {
            // will not trigger
        }

        @Override
        public void removed(HandlerContext<MsQuicListenerFD> ctx) {
            assert Logger.lowLevelDebug(listenerFD + " removed from loop");
        }
    }

    private class ConnectionHandler implements Handler<MsQuicConnectionFD> {
        private final MsQuicConnectionFD connFD;
        private boolean closing = false;
        private final LinkedList<MsQuicStreamFD> acceptQueues = new LinkedList<>();

        private ConnectionHandler(MsQuicConnectionFD connFD) {
            this.connFD = connFD;
        }

        @Override
        public void accept(HandlerContext<MsQuicConnectionFD> ctx) {
            MsQuicStreamFD streamFD;
            try {
                streamFD = ctx.getChannel().accept();
            } catch (IOException e) {
                assert Logger.lowLevelDebug("accept from connection failed, maybe the peer shutdown the connection: " + e);
                closing = true;

                closeConn(ctx.getChannel());
                return;
            }

            acceptQueues.push(streamFD);
            setReadable();
        }

        @Override
        public void connected(HandlerContext<MsQuicConnectionFD> ctx) {
            // will not trigger
        }

        @Override
        public void readable(HandlerContext<MsQuicConnectionFD> ctx) {
            // will not trigger
        }

        @Override
        public void writable(HandlerContext<MsQuicConnectionFD> ctx) {
            // will not trigger
        }

        @Override
        public void removed(HandlerContext<MsQuicConnectionFD> ctx) {
            if (closing) {
                return;
            }
            Logger.error(LogType.IMPROPER_USE,
                "the connection fd removed from loop " + connFD + ", it will be closed");
            closeConn(connFD);
        }
    }
}
