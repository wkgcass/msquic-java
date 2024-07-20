package io.vproxy.msquic.wrap;

import io.vproxy.msquic.QuicApiTable;
import io.vproxy.pni.Allocator;

import java.util.concurrent.atomic.AtomicBoolean;

public class ApiTable {
    public final Options opts;

    public ApiTable(Options opts) {
        this.opts = opts;
    }

    public static class OptionsBase {
        public final QuicApiTable apiTableQ;

        public OptionsBase(QuicApiTable apiTableQ) {
            this.apiTableQ = apiTableQ;
        }

        public OptionsBase(OptionsBase opts) {
            this.apiTableQ = opts.apiTableQ;
        }
    }

    public static class Options extends OptionsBase {
        private final Allocator allocator;

        public Options(QuicApiTable apiTableQ, Allocator allocator) {
            super(apiTableQ);
            this.allocator = allocator;
        }
    }

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public boolean isClosed() {
        return closed.get();
    }

    public void close() {
        if (opts.allocator == null) {
            return; // silently swallow because it's not allowed to be closed
        }

        if (closed.get()) {
            return;
        }
        if (!closed.compareAndSet(false, true)) {
            return;
        }

        opts.apiTableQ.close();
        opts.allocator.close();
    }
}
