package io.vproxy.msquic;

import io.vproxy.pni.annotation.*;

import java.lang.foreign.MemorySegment;

@Struct
@AlwaysAligned
@Include("msquic.h")
public abstract class PNIQuicConfiguration {
    MemorySegment Api; // QUIC_API_TABLE
    MemorySegment Conf; // HQUIC

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conf = self->Conf;
            api->ConfigurationClose(conf);
            """
    )
    @Style(Styles.critical)
    abstract void close();

    @Impl(
        // language="c"
        c = """
            QUIC_API_TABLE* api = self->Api;
            HQUIC conf = self->Conf;
                        
            QUIC_STATUS res = api->ConfigurationLoadCredential(conf, CredConfig);
            if (QUIC_SUCCEEDED(res)) {
                return 0;
            }
            return res;
            """
    )
    @Style(Styles.critical)
    abstract int loadCredential(PNIQuicCredentialConfig CredConfig);
}

@Struct(skip = true)
@AlwaysAligned
@Include("msquic.h")
@Name("QUIC_CREDENTIAL_CONFIG")
abstract class PNIQuicCredentialConfig {
    int Type; // QUIC_CREDENTIAL_TYPE
    int Flags; // QUIC_CREDENTIAL_FLAGS
    PNIQuicCertificate Certificate;
    String Principle;
    MemorySegment Reserved;
    MemorySegment AsyncHandler;
    int AllowedCipherSuites; // QUIC_ALLOWED_CIPHER_SUITE_FLAGS
    String CaCertificateFile;
}

@Union(embedded = true)
@AlwaysAligned
@Include("msquic.h")
abstract class PNIQuicCertificate {
    @Pointer PNIQuicCertificateFile CertificateFile;
}

@Struct(skip = true)
@AlwaysAligned
@Include("msquic.h")
@Name("QUIC_CERTIFICATE_FILE")
abstract class PNIQuicCertificateFile {
    String PrivateKeyFile;
    String CertificateFile;
}
