package msquic.vproxy;

import vfd.IPPort;
import vproxybase.connection.*;
import vproxybase.util.Callback;
import vproxybase.util.LogType;
import vproxybase.util.Logger;

import java.io.IOException;

public class MsQuicConnTransHandler implements ConnectableConnectionHandler {
    private final Callback<ServerSock, IOException> cb;
    private boolean willBeRemoved = false;

    public MsQuicConnTransHandler(Callback<ServerSock, IOException> cb) {
        this.cb = cb;
    }

    @Override
    public void connected(ConnectableConnectionHandlerContext ctx) {
        assert Logger.lowLevelDebug("connected: " + ctx.connection);
        done(ctx);
    }

    @Override
    public void readable(ConnectionHandlerContext ctx) {
        assert Logger.lowLevelDebug("readable: " + ctx.connection);
        done(ctx);
    }

    private void done(ConnectionHandlerContext ctx) {
        willBeRemoved = true;
        ctx.eventLoop.removeConnection(ctx.connection);
        if (!(ctx.connection.channel instanceof MsQuicConnectionFD)) {
            ctx.connection.close();
            cb.failed(new IOException("this handler can only handle MsQuicConnectionFD, but got " + ctx.connection.channel));
            return;
        }
        MsQuicConnectionFD fd = (MsQuicConnectionFD) ctx.connection.channel;
        IPPort local;
        try {
            local = fd.getLocalAddress();
        } catch (IOException e) {
            ctx.connection.close();
            cb.failed(new IOException("getting local addr from " + fd + " failed", e));
            return;
        }
        ServerSock serverSock;
        try {
            serverSock = ServerSock.wrap(fd, local, new ServerSock.BindOptions());
        } catch (IOException e) {
            ctx.connection.close();
            cb.failed(new IOException("wrapping " + fd + " into ServerSock failed", e));
            return;
        }
        cb.succeeded(serverSock);
    }

    @Override
    public void writable(ConnectionHandlerContext ctx) {
    }

    @Override
    public void exception(ConnectionHandlerContext ctx, IOException err) {
        Logger.error(LogType.CONN_ERROR, ctx.connection + " got error", err);
        if (!cb.isCalled()) {
            cb.failed(err);
        }
        ctx.connection.close();
    }

    @Override
    public void remoteClosed(ConnectionHandlerContext ctx) {
        // will not fire
    }

    @Override
    public void closed(ConnectionHandlerContext ctx) {
        Logger.error(LogType.IMPROPER_USE, ctx.connection + " is closed");
        if (!cb.isCalled()) {
            cb.failed(new IOException("closed"));
        }
    }

    @Override
    public void removed(ConnectionHandlerContext ctx) {
        if (willBeRemoved) {
            return;
        }
        Logger.error(LogType.IMPROPER_USE, ctx.connection + " removed from loop, will be closed");
        ctx.connection.close();
        if (!cb.isCalled()) {
            cb.failed(new IOException("removed from loop"));
        }
    }
}
