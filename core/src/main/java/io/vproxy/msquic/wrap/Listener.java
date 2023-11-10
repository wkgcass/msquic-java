package io.vproxy.msquic.wrap;

import io.vproxy.msquic.MsQuicUtils;
import io.vproxy.msquic.QuicListener;
import io.vproxy.msquic.QuicListenerEvent;
import io.vproxy.msquic.callback.ListenerCallback;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;
import io.vproxy.vfd.IPPort;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.*;

public class Listener {
    public final PNIRef<Listener> ref;
    public final Options opts;
    public final QuicListener listenerQ;
    private IPPort bindAddress;

    public Listener(Options opts) {
        this.ref = PNIRef.of(this);
        this.opts = opts;
        this.listenerQ = opts.listenerSupplier.apply(ref);
        opts.listenerSupplier = null; // gc
    }

    public int start(IPPort bindIPPort, String... alpn) {
        return start(bindIPPort, Arrays.asList(alpn));
    }

    public int start(IPPort bindIPPort, List<String> alpn) {
        if (alpn.isEmpty())
            throw new IllegalArgumentException("alpn must be specified");

        canReleaseResources = false; // set first, in case the callback immediately calls close()

        int res;
        try (var tmpAllocator = Allocator.ofConfined()) {
            var bindAddr = MsQuicUtils.convertIPPortToQuicAddr(bindIPPort, tmpAllocator);
            var alpnBuffers = MsQuicUtils.newAlpnBuffers(alpn, tmpAllocator);

            res = listenerQ.start(alpnBuffers, (int) alpnBuffers.length(), bindAddr);
        }
        if (res == 0) {
            this.bindAddress = bindIPPort;
        } else { // res != 0 means starting failed, the callbacks will never be called
            canReleaseResources = true;
        }
        return res;
    }

    public static class OptionsBase extends Registration.OptionsBase {
        public final Registration registration;

        public OptionsBase(Registration registration) {
            super(registration.opts);
            this.registration = registration;
        }
    }

    public static class Options extends OptionsBase {
        private final Allocator allocator;
        public final ListenerCallback callback;
        private Function<PNIRef<Listener>, QuicListener> listenerSupplier;

        public Options(Registration registration, Allocator allocator,
                       ListenerCallback callback,
                       Function<PNIRef<Listener>, QuicListener> listenerSupplier) {
            super(registration);
            this.allocator = allocator;
            this.callback = callback;
            this.listenerSupplier = listenerSupplier;
        }
    }

    private volatile boolean closed = false;
    private volatile boolean lsnCloseIsCalled = false;
    private volatile boolean canReleaseResources = true;

    public boolean isClosed() {
        return closed;
    }

    public void close() {
        if (closed) {
            return;
        }
        final var canReleaseResources = this.canReleaseResources;
        synchronized (this) {
            if (closed) {
                return;
            }
            if (canReleaseResources) {
                closed = true;
            }
        }

        stop();
        if (!canReleaseResources) {
            return;
        }
        opts.allocator.close();
        ref.close();
        opts.callback.closed(this);
    }

    protected void stop() {
        if (lsnCloseIsCalled) {
            return;
        }
        synchronized (this) {
            if (lsnCloseIsCalled) {
                return;
            }
            lsnCloseIsCalled = true;
        }
        if (listenerQ != null) {
            listenerQ.close();
        }
    }

    // need to override
    public int callback(QuicListenerEvent event) {
        return switch (event.getType()) {
            case QUIC_LISTENER_EVENT_STOP_COMPLETE -> {
                var data = event.getUnion().getSTOP_COMPLETE();
                var status = opts.callback.stopComplete(this, data);
                if (status == QUIC_STATUS_NOT_SUPPORTED)
                    status = 0;

                canReleaseResources = true;
                close();
                yield status;
            }
            case QUIC_LISTENER_EVENT_NEW_CONNECTION -> {
                var data = event.getUnion().getNEW_CONNECTION();
                yield opts.callback.newConnection(this, data);
            }
            default -> opts.callback.unknown(this, event);
        };
    }

    @SuppressWarnings("StringTemplateMigration")
    public String toString() {
        return "Listener["
               + "bind=" + bindAddress
               + "]@" + Long.toString(listenerQ.getLsn().address(), 16)
               + (isClosed() ? "[closed]" : "[open]");
    }
}
