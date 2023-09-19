package io.vproxy.msquic.wrap;

import io.vproxy.msquic.QuicApiTable;
import io.vproxy.pni.Allocator;

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

    private volatile boolean closed = false;

    public boolean isClosed() {
        return closed;
    }

    public void close() {
        if (opts.allocator == null) {
            return; // silently swallow because it's not allowed to be closed
        }

        if (closed) {
            return;
        }
        synchronized (this) {
            if (closed) {
                return;
            }
            closed = true;
        }

        opts.apiTableQ.close();
        opts.allocator.close();
    }
}
