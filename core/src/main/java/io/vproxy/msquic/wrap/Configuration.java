package io.vproxy.msquic.wrap;

import io.vproxy.msquic.QuicConfiguration;
import io.vproxy.pni.Allocator;

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

    private volatile boolean closed = false;

    public boolean isClosed() {
        return closed;
    }

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

        opts.configurationQ.close();
        opts.allocator.close();
    }
}
