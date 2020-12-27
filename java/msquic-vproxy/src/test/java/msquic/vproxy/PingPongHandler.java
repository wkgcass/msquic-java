package msquic.vproxy;

import vproxybase.connection.*;
import vproxybase.util.LogType;
import vproxybase.util.Logger;
import vproxybase.util.RingBuffer;
import vproxybase.util.nio.ByteArrayChannel;

import java.io.IOException;

public class PingPongHandler implements ConnectableConnectionHandler {
    private final String identifier;
    private int incr = 0;

    public PingPongHandler(String identifier) {
        this.identifier = identifier;
    }

    private void send(ConnectionHandlerContext ctx) {
        ctx.eventLoop.getSelectorEventLoop().delay(2000, () -> {
            String snd = identifier + "-" + (incr++);
            Logger.alert("snd: `" + snd + '\'');
            ByteArrayChannel chnl = ByteArrayChannel.fromFull(snd.getBytes());
            ctx.connection.getOutBuffer().storeBytesFrom(chnl);
        });
    }

    @Override
    public void connected(ConnectableConnectionHandlerContext ctx) {
        send(ctx);
    }

    @Override
    public void readable(ConnectionHandlerContext ctx) {
        RingBuffer inBuf = ctx.connection.getInBuffer();
        int len = inBuf.used();
        assert Logger.lowLevelDebug("inBuf.used = " + len);
        ByteArrayChannel chnl = ByteArrayChannel.fromEmpty(len);
        inBuf.writeTo(chnl);
        String str = new String(chnl.getArray().toJavaArray());
        Logger.alert("rcv: `" + str + "'");

        send(ctx);
    }

    @Override
    public void writable(ConnectionHandlerContext ctx) {
        // write nothing
    }

    @Override
    public void exception(ConnectionHandlerContext ctx, IOException err) {
        ctx.connection.close();
    }

    @Override
    public void remoteClosed(ConnectionHandlerContext ctx) {
        ctx.connection.close();
    }

    @Override
    public void closed(ConnectionHandlerContext ctx) {
        // ignore
    }

    @Override
    public void removed(ConnectionHandlerContext ctx) {
        Logger.error(LogType.IMPROPER_USE, ctx.connection + " removed from loop");
        ctx.connection.close();
    }
}
