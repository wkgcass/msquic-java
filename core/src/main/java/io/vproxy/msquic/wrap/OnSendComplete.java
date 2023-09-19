package io.vproxy.msquic.wrap;

@FunctionalInterface
public interface OnSendComplete {
    void onSendComplete(SendContext ctx, boolean succeeded);
}
