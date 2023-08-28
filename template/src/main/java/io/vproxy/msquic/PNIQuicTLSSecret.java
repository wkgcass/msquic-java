package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

@Struct(skip = true)
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
@Include("msquic.h")
class PNIQuicTLSSecretIsSet {
    @BitField(
        name = {
            "ClientRandom", "ClientEarlyTrafficSecret", "ClientHandshakeTrafficSecret",
            "ServerHandshakeTrafficSecret", "ClientTrafficSecret0", "ServerTrafficSecret0"
        },
        bit = {
            1, 1, 1,
            1, 1, 1
        }
    )
    @Unsigned byte IsSet;
}
