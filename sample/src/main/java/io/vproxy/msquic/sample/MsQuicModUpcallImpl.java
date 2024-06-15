package io.vproxy.msquic.sample;

import io.vproxy.base.util.Logger;
import io.vproxy.msquic.CXPLAT_THREAD_CONFIG;
import io.vproxy.msquic.MsQuicMod;
import io.vproxy.msquic.MsQuicModUpcall;
import io.vproxy.pni.graal.GraalUtils;

import java.lang.foreign.MemorySegment;
import java.util.concurrent.CountDownLatch;

public class MsQuicModUpcallImpl implements MsQuicModUpcall.Interface {
    private static final MsQuicModUpcallImpl INST = new MsQuicModUpcallImpl();

    private MsQuicModUpcallImpl() {
    }

    public static MsQuicModUpcallImpl get() {
        return INST;
    }

    @Override
    public int dispatch(CXPLAT_THREAD_CONFIG Config, MemorySegment EventQ, MemorySegment Thread, MemorySegment Context) {
        var cb = Config.getCallback();
        var ctx = Config.getContext();

        var latch = new CountDownLatch(1);

        new Thread(() -> {
            GraalUtils.setThread();
            Logger.alert("new msquic thread spawn: " + java.lang.Thread.currentThread());

            MsQuicMod.get().CxPlatGetCurThread(Thread);
            latch.countDown();

            MsQuicMod.get().MsQuicSetIsWorker(true);
            MsQuicMod.get().INVOKE_LPTHREAD_START_ROUTINE(cb, ctx);
        }).start();

        try {
            latch.await();
        } catch (InterruptedException _) {
        }

        return 0;
    }
}
