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

@Downcall
@Include("msquic.h")
interface PNIMsQuicMod {
    @Style(Styles.critical)
    @Name("MsQuicSetThreadCountLimit")
    void MsQuicSetThreadCountLimit(@Unsigned int limit);

    @Style(Styles.critical)
    @Name("MsQuicSetEventLoopThreadDispatcher")
    @NativeReturnType("QUIC_STATUS")
    int MsQuicSetEventLoopThreadDispatcher(@NativeType("QUIC_EVENT_LOOP_THREAD_DISPATCH_FN") MemorySegment dispatcher);

    @Style(Styles.critical)
    @Name("CxPlatGetCurThread")
    @NativeReturnType("QUIC_STATUS")
    int CxPlatGetCurThread(@NativeType("CXPLAT_THREAD*") MemorySegment Thread);

    @Impl(
        // language="c"
        c = """
            ((LPTHREAD_START_ROUTINE) Callback)(Context);
            """
    )
    @Style(Styles.critical)
    void INVOKE_LPTHREAD_START_ROUTINE(MemorySegment Callback, MemorySegment Context);

    @Style(Styles.critical)
    @Name("MsQuicSetIsWorker")
    void MsQuicSetIsWorker(boolean isWorker);
}

@Upcall
@Include("msquic.h")
interface PNIMsQuicModUpcall {
    int dispatch(PNICXPLAT_THREAD_CONFIG Config, @NativeType("CXPLAT_EVENTQ *") MemorySegment EventQ, MemorySegment Thread, MemorySegment Context);
}
