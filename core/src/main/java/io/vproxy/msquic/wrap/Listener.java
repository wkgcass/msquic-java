package io.vproxy.msquic.wrap;

import io.vproxy.msquic.*;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIRef;

import java.util.function.Function;

import static io.vproxy.msquic.MsQuicConsts.QUIC_LISTENER_EVENT_STOP_COMPLETE;

public abstract class Listener {
    public final PNIRef<Listener> ref;
    public final QuicApiTable apiTable;
    public final QuicRegistration registration;
    private final Allocator allocator;
    public final QuicListener listener;

    public Listener(QuicApiTable apiTable, QuicRegistration registration,
                    Allocator allocator,
                    Function<PNIRef<Listener>, QuicListener> listenerSupplier) {
        this.ref = PNIRef.of(this);
        this.apiTable = apiTable;
        this.registration = registration;
        this.allocator = allocator;
        this.listener = listenerSupplier.apply(ref);
    }

    private volatile boolean isClosed = false;
    private volatile boolean lsnIsClosed = false;

    public void close() {
        if (isClosed) {
            return;
        }
        synchronized (this) {
            if (isClosed) {
                return;
            }
            isClosed = true;
        }

        closeListener();
        allocator.close();
        ref.close();
        close0();
    }

    protected void close0() {
    }

    public void closeListener() {
        if (lsnIsClosed) {
            return;
        }
        synchronized (this) {
            if (lsnIsClosed) {
                return;
            }
            lsnIsClosed = true;
        }
        if (listener != null) {
            listener.close();
        }
    }

    // need to override
    public int callback(QuicListenerEvent event) {
        //noinspection SwitchStatementWithTooFewBranches
        return switch (event.getType()) {
            case QUIC_LISTENER_EVENT_STOP_COMPLETE -> {
                close();
                yield 0;
            }
            default -> MsQuicConsts.QUIC_STATUS_NOT_SUPPORTED;
        };
    }
}
