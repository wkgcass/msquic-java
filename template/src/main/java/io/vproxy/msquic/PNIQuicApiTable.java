package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@Include("msquic.h")
public abstract class PNIQuicApiTable {
    MemorySegment Api; // QUIC_API_TABLE

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            MsQuicClose(api);
            """
    )
    @Critical
    abstract void close();

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            api->SetContext(Handle, Context);
            """
    )
    @Critical
    abstract void setContext(MemorySegment Handle, MemorySegment Context);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            return api->GetContext(Handle);
            """
    )
    @Critical
    abstract MemorySegment getContext(MemorySegment Handle);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            api->SetCallbackHandler(Handle, Handler, Context);
            """
    )
    @Critical
    abstract void setCallbackHandler(MemorySegment Handle, MemorySegment Handler, MemorySegment Context);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            QUIC_STATUS res = api->SetParam(Handle, Param, BufferLength, Buffer);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int setParam(MemorySegment Handle, int Param, int BufferLength, MemorySegment Buffer);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            QUIC_STATUS res = api->GetParam(Handle, Param, BufferLength, Buffer);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Critical
    abstract int getParam(MemorySegment Handle, int Param, @Unsigned @Raw int[] BufferLength, MemorySegment Buffer);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC h;
            QUIC_STATUS res = api->RegistrationOpen(Config, &h);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->Api = api;
                return_->Reg = h;
                return return_;
            }
            return NULL;
            """
    )
    @Critical
    abstract PNIQuicRegistration openRegistration(PNIQuicRegistrationConfig Config, @Raw int[] returnStatus);
}

@Struct(skip = true)
@Include("msquic.h")
@Name("QUIC_REGISTRATION_CONFIG")
abstract class PNIQuicRegistrationConfig {
    String AppName;
    int ExecutionProfile; // QUIC_EXECUTION_PROFILE
}
