package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.hack.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class MsQuicUpcall {
    private static final Arena ARENA = Arena.ofShared();

    public static final MemorySegment listenerCallback;

    private static int listenerCallback(MemorySegment Listener, MemorySegment Context, MemorySegment Event) {
        if (IMPL == null) {
            System.out.println("io.vproxy.msquic.MsQuicUpcall#listenerCallback");
            System.exit(1);
        }
        var RESULT = IMPL.listenerCallback(
            (Listener.address() == 0 ? null : Listener),
            (Context.address() == 0 ? null : Context),
            (Event.address() == 0 ? null : new io.vproxy.msquic.QuicListenerEvent(Event))
        );
        return RESULT;
    }

    public static final MemorySegment connectionCallback;

    private static int connectionCallback(MemorySegment Connection, MemorySegment Context, MemorySegment Event) {
        if (IMPL == null) {
            System.out.println("io.vproxy.msquic.MsQuicUpcall#connectionCallback");
            System.exit(1);
        }
        var RESULT = IMPL.connectionCallback(
            (Connection.address() == 0 ? null : Connection),
            (Context.address() == 0 ? null : Context),
            (Event.address() == 0 ? null : new io.vproxy.msquic.QuicConnectionEvent(Event))
        );
        return RESULT;
    }

    public static final MemorySegment streamCallback;

    private static int streamCallback(MemorySegment Stream, MemorySegment Context, MemorySegment Event) {
        if (IMPL == null) {
            System.out.println("io.vproxy.msquic.MsQuicUpcall#streamCallback");
            System.exit(1);
        }
        var RESULT = IMPL.streamCallback(
            (Stream.address() == 0 ? null : Stream),
            (Context.address() == 0 ? null : Context),
            (Event.address() == 0 ? null : new io.vproxy.msquic.QuicStreamEvent(Event))
        );
        return RESULT;
    }

    static {
        MethodHandle listenerCallbackMH;
        MethodHandle connectionCallbackMH;
        MethodHandle streamCallbackMH;
        try {
            listenerCallbackMH = MethodHandles.lookup().findStatic(io.vproxy.msquic.MsQuicUpcall.class, "listenerCallback", MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class, MemorySegment.class));
            connectionCallbackMH = MethodHandles.lookup().findStatic(io.vproxy.msquic.MsQuicUpcall.class, "connectionCallback", MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class, MemorySegment.class));
            streamCallbackMH = MethodHandles.lookup().findStatic(io.vproxy.msquic.MsQuicUpcall.class, "streamCallback", MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class, MemorySegment.class));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        listenerCallback = PanamaUtils.defineCFunction(new PNILinkOptions(), ARENA, listenerCallbackMH, int.class, MemorySegment.class, MemorySegment.class, MemorySegment.class);
        connectionCallback = PanamaUtils.defineCFunction(new PNILinkOptions(), ARENA, connectionCallbackMH, int.class, MemorySegment.class, MemorySegment.class, MemorySegment.class);
        streamCallback = PanamaUtils.defineCFunction(new PNILinkOptions(), ARENA, streamCallbackMH, int.class, MemorySegment.class, MemorySegment.class, MemorySegment.class);

        var initMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions().setCritical(true), void.class, "JavaCritical_io_vproxy_msquic_MsQuicUpcall_INIT", MemorySegment.class, MemorySegment.class, MemorySegment.class);
        try {
            initMH.invoke(listenerCallback, connectionCallback, streamCallback);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static Interface IMPL = null;

    public static void setImpl(Interface impl) {
        java.util.Objects.requireNonNull(impl);
        IMPL = impl;
    }

    public interface Interface {
        int listenerCallback(MemorySegment Listener, MemorySegment Context, io.vproxy.msquic.QuicListenerEvent Event);

        int connectionCallback(MemorySegment Connection, MemorySegment Context, io.vproxy.msquic.QuicConnectionEvent Event);

        int streamCallback(MemorySegment Stream, MemorySegment Context, io.vproxy.msquic.QuicStreamEvent Event);
    }
}
// metadata.generator-version: pni 21.0.0.20
// sha256:effec302cd397414615ac2539c82d7f12796165c91fa747bac3e1270be151fd6
