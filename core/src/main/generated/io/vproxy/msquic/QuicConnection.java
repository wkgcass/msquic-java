package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.array.*;
import java.lang.foreign.*;
import java.lang.invoke.*;
import java.nio.ByteBuffer;

public class QuicConnection extends io.vproxy.msquic.QuicObjectBase implements NativeObject {
    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
        io.vproxy.msquic.QuicObjectBase.LAYOUT

    ).withByteAlignment(8);
    public final MemorySegment MEMORY;

    @Override
    public MemorySegment MEMORY() {
        return MEMORY;
    }

    public QuicConnection(MemorySegment MEMORY) {
        super(MEMORY);
        MEMORY = MEMORY.reinterpret(LAYOUT.byteSize());
        this.MEMORY = MEMORY;
        long OFFSET = 0;
        OFFSET += io.vproxy.msquic.QuicObjectBase.LAYOUT.byteSize();
    }

    public QuicConnection(Allocator ALLOCATOR) {
        this(ALLOCATOR.allocate(LAYOUT));
    }

    private static final MethodHandle closeMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicConnection_close", MemorySegment.class /* self */);

    public void close() {
        try {
            closeMH.invokeExact(MEMORY);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle shutdownMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), void.class, "JavaCritical_io_vproxy_msquic_QuicConnection_shutdown", MemorySegment.class /* self */, int.class /* Flags */, long.class /* ErrorCode */);

    public void shutdown(int Flags, long ErrorCode) {
        try {
            shutdownMH.invokeExact(MEMORY, Flags, ErrorCode);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
    }

    private static final MethodHandle startMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicConnection_start", MemorySegment.class /* self */, io.vproxy.msquic.QuicConfiguration.LAYOUT.getClass() /* Conf */, int.class /* Family */, String.class /* ServerName */, int.class /* ServerPort */);

    public int start(io.vproxy.msquic.QuicConfiguration Conf, int Family, PNIString ServerName, int ServerPort) {
        int RESULT;
        try {
            RESULT = (int) startMH.invokeExact(MEMORY, (MemorySegment) (Conf == null ? MemorySegment.NULL : Conf.MEMORY), Family, (MemorySegment) (ServerName == null ? MemorySegment.NULL : ServerName.MEMORY), ServerPort);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle setConfigurationMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicConnection_setConfiguration", MemorySegment.class /* self */, io.vproxy.msquic.QuicConfiguration.LAYOUT.getClass() /* Conf */);

    public int setConfiguration(io.vproxy.msquic.QuicConfiguration Conf) {
        int RESULT;
        try {
            RESULT = (int) setConfigurationMH.invokeExact(MEMORY, (MemorySegment) (Conf == null ? MemorySegment.NULL : Conf.MEMORY));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle sendResumptionTicketMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicConnection_sendResumptionTicket", MemorySegment.class /* self */, int.class /* Flags */, int.class /* DataLength */, MemorySegment.class /* ResumptionData */);

    public int sendResumptionTicket(int Flags, int DataLength, MemorySegment ResumptionData) {
        int RESULT;
        try {
            RESULT = (int) sendResumptionTicketMH.invokeExact(MEMORY, Flags, DataLength, (MemorySegment) (ResumptionData == null ? MemorySegment.NULL : ResumptionData));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle openStreamMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), io.vproxy.msquic.QuicStream.LAYOUT.getClass(), "JavaCritical_io_vproxy_msquic_QuicConnection_openStream", MemorySegment.class /* self */, int.class /* Flags */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */);

    public io.vproxy.msquic.QuicStream openStream(int Flags, MemorySegment Handler, MemorySegment Context, IntArray returnStatus, Allocator ALLOCATOR) {
        MemorySegment RESULT;
        try {
            RESULT = (MemorySegment) openStreamMH.invokeExact(MEMORY, Flags, (MemorySegment) (Handler == null ? MemorySegment.NULL : Handler), (MemorySegment) (Context == null ? MemorySegment.NULL : Context), (MemorySegment) (returnStatus == null ? MemorySegment.NULL : returnStatus.MEMORY), ALLOCATOR.allocate(io.vproxy.msquic.QuicStream.LAYOUT));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        if (RESULT.address() == 0) return null;
        return RESULT == null ? null : new io.vproxy.msquic.QuicStream(RESULT);
    }

    private static final MethodHandle sendDatagramMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicConnection_sendDatagram", MemorySegment.class /* self */, io.vproxy.msquic.QuicBuffer.LAYOUT.getClass() /* Buffers */, int.class /* BufferCount */, int.class /* Flags */, MemorySegment.class /* ClientSendContext */);

    public int sendDatagram(io.vproxy.msquic.QuicBuffer Buffers, int BufferCount, int Flags, MemorySegment ClientSendContext) {
        int RESULT;
        try {
            RESULT = (int) sendDatagramMH.invokeExact(MEMORY, (MemorySegment) (Buffers == null ? MemorySegment.NULL : Buffers.MEMORY), BufferCount, Flags, (MemorySegment) (ClientSendContext == null ? MemorySegment.NULL : ClientSendContext));
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle resumptionTicketValidationCompleteMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicConnection_resumptionTicketValidationComplete", MemorySegment.class /* self */, boolean.class /* Result */);

    public int resumptionTicketValidationComplete(boolean Result) {
        int RESULT;
        try {
            RESULT = (int) resumptionTicketValidationCompleteMH.invokeExact(MEMORY, Result);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    private static final MethodHandle certificateValidationCompleteMH = PanamaUtils.lookupPNICriticalFunction(new PNILinkOptions(), int.class, "JavaCritical_io_vproxy_msquic_QuicConnection_certificateValidationComplete", MemorySegment.class /* self */, boolean.class /* Result */, int.class /* TlsAlert */);

    public int certificateValidationComplete(boolean Result, int TlsAlert) {
        int RESULT;
        try {
            RESULT = (int) certificateValidationCompleteMH.invokeExact(MEMORY, Result, TlsAlert);
        } catch (Throwable THROWABLE) {
            throw PanamaUtils.convertInvokeExactException(THROWABLE);
        }
        return RESULT;
    }

    @Override
    public void toString(StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
        if (!VISITED.add(new NativeObjectTuple(this))) {
            SB.append("<...>@").append(Long.toString(MEMORY.address(), 16));
            return;
        }
        SB.append("QuicConnection{\n");
        SB.append(" ".repeat(INDENT + 4)).append("SUPER => ");
        {
            INDENT += 4;
            SB.append("QuicObjectBase{\n");
            {
                SB.append(" ".repeat(INDENT + 4)).append("Api => ");
                SB.append(PanamaUtils.memorySegmentToString(getApi()));
            }
            SB.append(",\n");
            {
                SB.append(" ".repeat(INDENT + 4)).append("Handle => ");
                SB.append(PanamaUtils.memorySegmentToString(getHandle()));
            }
            SB.append("\n");
            SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
            INDENT -= 4;
            SB.append("\n");

        }
        SB.append(" ".repeat(INDENT)).append("}@").append(Long.toString(MEMORY.address(), 16));
    }

    public static class Array extends RefArray<QuicConnection> {
        public Array(MemorySegment buf) {
            super(buf, QuicConnection.LAYOUT);
        }

        public Array(Allocator allocator, long len) {
            super(allocator, QuicConnection.LAYOUT, len);
        }

        public Array(PNIBuf buf) {
            super(buf, QuicConnection.LAYOUT);
        }

        @Override
        protected void elementToString(io.vproxy.msquic.QuicConnection ELEM, StringBuilder SB, int INDENT, java.util.Set<NativeObjectTuple> VISITED, boolean CORRUPTED_MEMORY) {
            ELEM.toString(SB, INDENT, VISITED, CORRUPTED_MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnection.Array";
        }

        @Override
        protected QuicConnection construct(MemorySegment seg) {
            return new QuicConnection(seg);
        }

        @Override
        protected MemorySegment getSegment(QuicConnection value) {
            return value.MEMORY;
        }
    }

    public static class Func extends PNIFunc<QuicConnection> {
        private Func(io.vproxy.pni.CallSite<QuicConnection> func) {
            super(func);
        }

        private Func(io.vproxy.pni.CallSite<QuicConnection> func, Options opts) {
            super(func, opts);
        }

        private Func(MemorySegment MEMORY) {
            super(MEMORY);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnection> func) {
            return new Func(func);
        }

        public static Func of(io.vproxy.pni.CallSite<QuicConnection> func, Options opts) {
            return new Func(func, opts);
        }

        public static Func of(MemorySegment MEMORY) {
            return new Func(MEMORY);
        }

        @Override
        protected String toStringTypeName() {
            return "QuicConnection.Func";
        }

        @Override
        protected QuicConnection construct(MemorySegment seg) {
            return new QuicConnection(seg);
        }
    }
}
// metadata.generator-version: pni 21.0.0.18
// sha256:61a05f942ee9996c14da21e9439d2d3e5e1a807ae241a031bfb412e4a7c7b0f5
