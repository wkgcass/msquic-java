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
