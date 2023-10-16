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

@Function
@Include("msquic.h")
interface PNIMsQuicMod {
    @Impl(
        // language="c"
        c = """
            return MsQuicSetEventLoopThreadDispatcher(dispatcher);
            """
    )
    @Critical
    int MsQuicSetEventLoopThreadDispatcher(MemorySegment dispatcher);

    @Impl(
        // language="c"
        c = """
            return CxPlatGetCurThread((CXPLAT_THREAD*) Thread);
            """
    )
    @Critical
    int CxPlatGetCurThread(MemorySegment Thread);

    @Impl(
        // language="c"
        c = """
            ((LPTHREAD_START_ROUTINE) Callback)(Context);
            """
    )
    @Critical
    void INVOKE_LPTHREAD_START_ROUTINE(MemorySegment Callback, MemorySegment Context);
}

@Upcall
@Include("msquic.h")
interface PNIMsQuicModUpcall {
    int dispatch(PNICXPLAT_THREAD_CONFIG Config, @NativeType("CXPLAT_EVENTQ *") MemorySegment EventQ, MemorySegment Thread, MemorySegment Context);
}
