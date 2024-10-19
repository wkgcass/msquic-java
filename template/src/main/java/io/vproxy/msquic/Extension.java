package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct(skip = true)
@AlwaysAligned
@Name("CXPLAT_THREAD_CONFIG")
@Include("msquic.h")
class PNICXPLAT_THREAD_CONFIG {
    @Unsigned short Flags;
    @Unsigned short IdealProcessor;
    String Name;
    /**
     * posix:<br/>
     * <pre>
     * typedef void* (* LPTHREAD_START_ROUTINE)(void *);
     * </pre><br/>
     * windows:<br/>
     * <pre>
     * typedef DWORD (__stdcall *LPTHREAD_START_ROUTINE) ([in] LPVOID lpThreadParameter);
     * </pre>
     */
    MemorySegment Callback;
    MemorySegment Context;
}

@Struct(skip = true)
@PointerOnly
@Include("msquic.h")
@Name("QUIC_EXTRA_API_TABLE")
abstract class PNIQuicExtraApiTable {
    @Style(Styles.critical)
    @NativeReturnType("QUIC_STATUS")
    @Impl(
        c = """
            return self->EventLoopThreadDispatcherSet(dispatcher);
            """
    )
    abstract int EventLoopThreadDispatcherSet(@NativeType("QUIC_EVENT_LOOP_THREAD_DISPATCH_FN") MemorySegment dispatcher);

    @Style(Styles.critical)
    @NativeReturnType("QUIC_STATUS")
    @Impl(
        c = """
            return self->ThreadGetCur(Thread);
            """
    )
    abstract int ThreadGetCur(@NativeType("CXPLAT_THREAD*") MemorySegment Thread);

    @Style(Styles.critical)
    @Impl(
        c = """
            self->ThreadSetIsWorker(isWorker);
            """
    )
    abstract void ThreadSetIsWorker(boolean isWorker);
}

@Downcall
@Include("msquic.h")
interface PNIMsQuicMod {
    @Impl(
        // language="c"
        c = """
            QUIC_EXTRA_API_TABLE* api;
            QUIC_STATUS res = MsQuicOpenExtra(Version, (const void**) &api);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return api;
            }
            return NULL;
            """
    )
    @Style(Styles.critical)
    @NoAlloc
    PNIQuicExtraApiTable openExtra(@Unsigned int Version, @Raw int[] returnStatus);

    @Impl(
        // language="c"
        c = """
            ((LPTHREAD_START_ROUTINE) Callback)(Context);
            """
    )
    @Style(Styles.critical)
    void INVOKE_LPTHREAD_START_ROUTINE(MemorySegment Callback, MemorySegment Context);
}

@Upcall
@Include("msquic.h")
interface PNIMsQuicModUpcall {
    int dispatch(PNICXPLAT_THREAD_CONFIG Config, @NativeType("CXPLAT_EVENTQ *") MemorySegment EventQ, MemorySegment Thread, MemorySegment Context);
}
