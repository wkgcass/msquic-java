package io.vproxy.msquic.wrap;

import io.vproxy.msquic.QuicRegistration;
import io.vproxy.pni.Allocator;

import java.util.concurrent.atomic.AtomicBoolean;

public class Registration {
    public final Options opts;

    public Registration(Options opts) {
        this.opts = opts;
    }

    public static class OptionsBase extends ApiTable.OptionsBase {
        public final ApiTable apiTable;
        public final QuicRegistration registrationQ;

        public OptionsBase(ApiTable apiTable, QuicRegistration registrationQ) {
            super(apiTable.opts);
            this.apiTable = apiTable;
            this.registrationQ = registrationQ;
        }

        public OptionsBase(OptionsBase opts) {
            super(opts);
            this.apiTable = opts.apiTable;
            this.registrationQ = opts.registrationQ;
        }
    }

    public static class Options extends OptionsBase {
        private final Allocator allocator;

        public Options(ApiTable apiTable, QuicRegistration registrationQ, Allocator allocator) {
            super(apiTable, registrationQ);
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

        opts.registrationQ.close();
        opts.allocator.close();
    }
}
