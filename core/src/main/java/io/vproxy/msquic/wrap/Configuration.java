package io.vproxy.msquic.wrap;

import io.vproxy.msquic.QuicApiTable;
import io.vproxy.msquic.QuicConfiguration;
import io.vproxy.msquic.QuicRegistration;
import io.vproxy.pni.Allocator;

public class Configuration {
    public final QuicApiTable apiTable;
    public final QuicRegistration registration;
    public final QuicConfiguration configuration;
    private final Allocator allocator;

    public Configuration(QuicApiTable apiTable, QuicRegistration registration, QuicConfiguration configuration, Allocator allocator) {
        this.apiTable = apiTable;
        this.registration = registration;
        this.configuration = configuration;
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

        configuration.close();
        allocator.close();
    }
}
