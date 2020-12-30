package msquic.vproxy;

import msquic.*;
import msquic.nativevalues.AddressFamily;
import msquic.nativevalues.ListenerEventType;
import msquic.nativevalues.Status;
import vfd.*;
import vproxybase.selector.wrap.AbstractBaseVirtualServerSocketFD;
import vproxybase.selector.wrap.VirtualFD;
import vproxybase.util.Logger;

import java.io.IOException;
import java.util.Collection;

public class MsQuicListenerFD extends AbstractBaseVirtualServerSocketFD<MsQuicConnectionFD> implements ServerSocketFD, VirtualFD {
    private final Configuration conf;
    private final Listener listener;
    private final Collection<String> serverAlpn;

    IPPort localAddress;

    public MsQuicListenerFD(Registration reg,
                            Configuration conf,
                            Collection<String> serverAlpn) throws MsQuicException {
        this.conf = conf;
        this.listener = reg.openListener(this::listenerCallback);
        this.serverAlpn = serverAlpn;
    }

    @Override
    public void bind(IPPort l4addr) throws IOException {
        super.bind(l4addr);
        localAddress = l4addr;

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
    }

    @Override
    protected void doClose() {
        listener.close();
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
        newAcceptableFD(connFD);
    }

    @Override
    protected String formatToString() {
        return "MsQuicListenerFD(" + listener + ")[" + (isOpen() ? "open" : "closed") + "]";
    }
}
