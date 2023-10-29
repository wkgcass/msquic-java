package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

@Struct(skip = true)
@AlwaysAligned
@Include("msquic.h")
@Name("QUIC_TLS_SECRETS")
public class PNIQuicTLSSecret {
    @Unsigned byte SecretLength;
    PNIQuicTLSSecretIsSet IsSet;
    @Unsigned @Len(32) byte[] ClientRandom;
    @Unsigned @Len(64) byte[] ClientEarlyTrafficSecret;
    @Unsigned @Len(64) byte[] ClientHandshakeTrafficSecret;
    @Unsigned @Len(64) byte[] ServerHandshakeTrafficSecret;
    @Unsigned @Len(64) byte[] ClientTrafficSecret0;
    @Unsigned @Len(64) byte[] ServerTrafficSecret0;
}

@Struct
@AlwaysAligned
@Include("msquic.h")
class PNIQuicTLSSecretIsSet {
    @Bit({
        @Bit.Field(name = "ClientRandom", bits = 1, bool = true),
        @Bit.Field(name = "ClientEarlyTrafficSecret", bits = 1, bool = true),
        @Bit.Field(name = "ClientHandshakeTrafficSecret", bits = 1, bool = true),
        @Bit.Field(name = "ServerHandshakeTrafficSecret", bits = 1, bool = true),
        @Bit.Field(name = "ClientTrafficSecret0", bits = 1, bool = true),
        @Bit.Field(name = "ServerTrafficSecret0", bits = 1, bool = true),
    })
    @Unsigned byte IsSet;
}
