package io.vproxy.msquic.wrap;

import io.vproxy.msquic.QuicApiTable;
import io.vproxy.msquic.QuicRegistration;
import io.vproxy.pni.Allocator;

public class Registration {
    public final QuicApiTable apiTable;
    public final QuicRegistration registration;
    private final Allocator allocator;

    public Registration(QuicApiTable apiTable, QuicRegistration registration, Allocator allocator) {
        this.apiTable = apiTable;
        this.registration = registration;
        this.allocator = allocator;
    }

    private volatile boolean closed = false;

    public void close() {
        if (closed) {
            return;
        }
        synchronized (this) {
            if (closed) {
                return;
            }
            closed = true;
        }

        registration.close();
        allocator.close();
    }
}
