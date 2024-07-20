package io.vproxy.msquic.wrap;

import io.vproxy.msquic.QuicConfiguration;
import io.vproxy.pni.Allocator;

import java.util.concurrent.atomic.AtomicBoolean;

public class Configuration {
    public final Options opts;

    public Configuration(Options opts) {
        this.opts = opts;
    }

    public static class OptionsBase extends Registration.OptionsBase {
        public final Registration registration;
        public final QuicConfiguration configurationQ;

        public OptionsBase(Registration registration, QuicConfiguration configurationQ) {
            super(registration.opts);
            this.registration = registration;
            this.configurationQ = configurationQ;
        }
    }

    public static class Options extends OptionsBase {
        private final Allocator allocator;

        public Options(Registration registration, QuicConfiguration configurationQ, Allocator allocator) {
            super(registration, configurationQ);
            this.allocator = allocator;
        }
    }

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public boolean isClosed() {
        return closed.get();
    }

    public void close() {
        if (closed.get()) {
            return;
        }
        if (!closed.compareAndSet(false, true)) {
            return;
        }

        opts.configurationQ.close();
        opts.allocator.close();
    }
}
