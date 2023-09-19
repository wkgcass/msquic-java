package io.vproxy.msquic.wrap;

import io.vproxy.pni.Allocator;

public class SendContext {
    public final Allocator allocator;
    public final OnSendComplete onSendComplete;

    public SendContext(
        Allocator allocator,
        OnSendComplete onSendComplete
    ) {
        this.allocator = allocator;
        this.onSendComplete = onSendComplete;
    }

    private static final SendContext EMPTY_INSTANCE = new SendContext(
        Allocator.ofDummy(), (ctx, ok) -> {
    }
    );

    public static SendContext empty() {
        return EMPTY_INSTANCE;
    }
}
