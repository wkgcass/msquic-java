package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;
import io.vproxy.pni.graal.*;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.c.function.*;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

public class MsQuicUpcall {
    private static final Arena ARENA = Arena.ofShared();

    public static MemorySegment listenerCallback;
    public static final CEntryPointLiteral<CFunctionPointer> listenerCallbackCEPL = GraalUtils.defineCFunctionByName(new PNILinkOptions(), io.vproxy.msquic.MsQuicUpcall.class, "listenerCallback");

    @CEntryPoint
    public static int listenerCallback(IsolateThread THREAD, VoidPointer ListenerPTR, VoidPointer ContextPTR, VoidPointer EventPTR) {
        if (IMPL == null) {
            System.out.println("io.vproxy.msquic.MsQuicUpcall#listenerCallback");
            System.exit(1);
        }
        var Listener = MemorySegment.ofAddress(ListenerPTR.rawValue());
        var Context = MemorySegment.ofAddress(ContextPTR.rawValue());
        var Event = MemorySegment.ofAddress(EventPTR.rawValue());
        var RESULT = IMPL.listenerCallback(
            (Listener.address() == 0 ? null : Listener),
            (Context.address() == 0 ? null : Context),
            (Event.address() == 0 ? null : new io.vproxy.msquic.QuicListenerEvent(Event))
        );
        return RESULT;
    }

    public static MemorySegment connectionCallback;
    public static final CEntryPointLiteral<CFunctionPointer> connectionCallbackCEPL = GraalUtils.defineCFunctionByName(new PNILinkOptions(), io.vproxy.msquic.MsQuicUpcall.class, "connectionCallback");

    @CEntryPoint
    public static int connectionCallback(IsolateThread THREAD, VoidPointer ConnectionPTR, VoidPointer ContextPTR, VoidPointer EventPTR) {
        if (IMPL == null) {
            System.out.println("io.vproxy.msquic.MsQuicUpcall#connectionCallback");
            System.exit(1);
        }
        var Connection = MemorySegment.ofAddress(ConnectionPTR.rawValue());
        var Context = MemorySegment.ofAddress(ContextPTR.rawValue());
        var Event = MemorySegment.ofAddress(EventPTR.rawValue());
        var RESULT = IMPL.connectionCallback(
            (Connection.address() == 0 ? null : Connection),
            (Context.address() == 0 ? null : Context),
            (Event.address() == 0 ? null : new io.vproxy.msquic.QuicConnectionEvent(Event))
        );
        return RESULT;
    }

    public static MemorySegment streamCallback;
    public static final CEntryPointLiteral<CFunctionPointer> streamCallbackCEPL = GraalUtils.defineCFunctionByName(new PNILinkOptions(), io.vproxy.msquic.MsQuicUpcall.class, "streamCallback");

    @CEntryPoint
    public static int streamCallback(IsolateThread THREAD, VoidPointer StreamPTR, VoidPointer ContextPTR, VoidPointer EventPTR) {
        if (IMPL == null) {
            System.out.println("io.vproxy.msquic.MsQuicUpcall#streamCallback");
            System.exit(1);
        }
        var Stream = MemorySegment.ofAddress(StreamPTR.rawValue());
        var Context = MemorySegment.ofAddress(ContextPTR.rawValue());
        var Event = MemorySegment.ofAddress(EventPTR.rawValue());
        var RESULT = IMPL.streamCallback(
            (Stream.address() == 0 ? null : Stream),
            (Context.address() == 0 ? null : Context),
            (Event.address() == 0 ? null : new io.vproxy.msquic.QuicStreamEvent(Event))
        );
        return RESULT;
    }

    private static void setNativeImpl() {
        listenerCallback = MemorySegment.ofAddress(listenerCallbackCEPL.getFunctionPointer().rawValue());
        connectionCallback = MemorySegment.ofAddress(connectionCallbackCEPL.getFunctionPointer().rawValue());
        streamCallback = MemorySegment.ofAddress(streamCallbackCEPL.getFunctionPointer().rawValue());

        var initMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions().setCritical(true), void.class, "JavaCritical_io_vproxy_msquic_MsQuicUpcall_INIT", MemorySegment.class, MemorySegment.class, MemorySegment.class);
        try {
            initMH.invoke(listenerCallback, connectionCallback, streamCallback);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        listenerCallback = PanamaUtils.lookupFunctionPointer(new PNILookupOptions(), "JavaCritical_io_vproxy_msquic_MsQuicUpcall_listenerCallback").orElseThrow(() -> new NullPointerException("JavaCritical_io_vproxy_msquic_MsQuicUpcall_listenerCallback"));
        connectionCallback = PanamaUtils.lookupFunctionPointer(new PNILookupOptions(), "JavaCritical_io_vproxy_msquic_MsQuicUpcall_connectionCallback").orElseThrow(() -> new NullPointerException("JavaCritical_io_vproxy_msquic_MsQuicUpcall_connectionCallback"));
        streamCallback = PanamaUtils.lookupFunctionPointer(new PNILookupOptions(), "JavaCritical_io_vproxy_msquic_MsQuicUpcall_streamCallback").orElseThrow(() -> new NullPointerException("JavaCritical_io_vproxy_msquic_MsQuicUpcall_streamCallback"));
    }

    private static Interface IMPL = null;

    public static void setImpl(Interface impl) {
        java.util.Objects.requireNonNull(impl);
        IMPL = impl;
        setNativeImpl();
    }

    public interface Interface {
        int listenerCallback(MemorySegment Listener, MemorySegment Context, io.vproxy.msquic.QuicListenerEvent Event);

        int connectionCallback(MemorySegment Connection, MemorySegment Context, io.vproxy.msquic.QuicConnectionEvent Event);

        int streamCallback(MemorySegment Stream, MemorySegment Context, io.vproxy.msquic.QuicStreamEvent Event);
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:b11ec70d7ab70942a4246511256041787d81aa71336bc20f4696a734c66bafe1
