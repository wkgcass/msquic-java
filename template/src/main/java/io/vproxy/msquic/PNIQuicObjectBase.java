package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@AlwaysAligned
public abstract class PNIQuicObjectBase {
    MemorySegment Api; // QUIC_API_TABLE
    MemorySegment Handle; // HQUIC

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            api->SetContext(self->Handle, Context);
            """
    )
    @Style(Styles.critical)
    abstract void setContext(MemorySegment Context);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            return api->GetContext(self->Handle);
            """
    )
    @Style(Styles.critical)
    abstract MemorySegment getContext();

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            api->SetCallbackHandler(self->Handle, Handler, Context);
            """
    )
    @Style(Styles.critical)
    abstract void setCallbackHandler(MemorySegment Handler, MemorySegment Context);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            QUIC_STATUS res = api->SetParam(self->Handle, Param, BufferLength, Buffer);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Style(Styles.critical)
    abstract int setParam(int Param, int BufferLength, MemorySegment Buffer);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            QUIC_STATUS res = api->GetParam(self->Handle, Param, BufferLength, Buffer);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Style(Styles.critical)
    abstract int getParam(int Param, @Unsigned @Raw int[] BufferLength, MemorySegment Buffer);
}
