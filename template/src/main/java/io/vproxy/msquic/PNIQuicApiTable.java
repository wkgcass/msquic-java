package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@AlwaysAligned
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
    @Style(Styles.critical)
    abstract void close();

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC h;
            QUIC_STATUS res = api->RegistrationOpen(Config, &h);
            if (returnStatus != NULL)
                *returnStatus = res;
            if (QUIC_SUCCEEDED(res)) {
                return_->SUPER.Api = api;
                return_->SUPER.Handle = h;
                return return_;
            }
            return NULL;
            """
    )
    @Style(Styles.critical)
    abstract PNIQuicRegistration openRegistration(PNIQuicRegistrationConfig Config, @Raw int[] returnStatus);

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            QUIC_STATUS res = api->SetParam(NULL, Param, BufferLength, Buffer);
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
            QUIC_STATUS res = api->GetParam(NULL, Param, BufferLength, Buffer);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Style(Styles.critical)
    abstract int getParam(int Param, @Unsigned @Raw int[] BufferLength, MemorySegment Buffer);
}

@Struct(skip = true)
@AlwaysAligned
@Include("msquic.h")
@Name("QUIC_REGISTRATION_CONFIG")
abstract class PNIQuicRegistrationConfig {
    String AppName;
    int ExecutionProfile; // QUIC_EXECUTION_PROFILE
}

@Struct
@AlwaysAligned
abstract class PNIQuicRegistrationConfigEx extends PNIQuicRegistrationConfig {
    MemorySegment Context;
}

@Struct(skip = true)
@AlwaysAligned
@Include("msquic.h")
@Name("QUIC_EXECUTION_CONFIG")
class PNIQuicExecutionConfig {
    @Name("Flags") int flags;
    @Name("PollingIdleTimeoutUs") @Unsigned int pollingIdleTimeoutUs;
    @Name("ProcessorCount") @Unsigned int processorCount;
    @Name("ProcessorList") @Unsigned @Len(1) short[] processorList;
}
