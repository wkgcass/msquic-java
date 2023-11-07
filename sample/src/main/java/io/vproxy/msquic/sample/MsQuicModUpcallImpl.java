package io.vproxy.msquic.sample;

import io.vproxy.base.util.Logger;
import io.vproxy.msquic.CXPLAT_THREAD_CONFIG;
import io.vproxy.msquic.MsQuicMod;
import io.vproxy.msquic.MsQuicModUpcall;
import io.vproxy.pni.graal.GraalUtils;

import java.lang.foreign.MemorySegment;

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

        new Thread(() -> {
            GraalUtils.setThread();
            Logger.alert(STR."new msquic thread spawn: \{java.lang.Thread.currentThread()}");

            MsQuicMod.get().CxPlatGetCurThread(Thread);
            MsQuicMod.get().INVOKE_LPTHREAD_START_ROUTINE(cb, ctx);
        }).start();

        return 0;
    }
}
