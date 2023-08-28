package io.vproxy.msquic.wrap;

import io.vproxy.msquic.QuicApiTable;
import io.vproxy.pni.Allocator;

public class ApiTable {
    public final QuicApiTable apiTable;
    private final Allocator allocator;

    public ApiTable(QuicApiTable apiTable, Allocator allocator) {
        this.apiTable = apiTable;
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

        apiTable.close();
        allocator.close();
    }
}
