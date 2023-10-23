#include "io_vproxy_msquic_QuicConnection.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicConnection_close(QuicConnection * self) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conn = self->Conn;
    api->ConnectionClose(conn);
}

JNIEXPORT void JNICALL JavaCritical_io_vproxy_msquic_QuicConnection_shutdown(QuicConnection * self, int32_t Flags, int64_t ErrorCode) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conn = self->Conn;

    api->ConnectionShutdown(conn, Flags, ErrorCode);
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicConnection_start(QuicConnection * self, QuicConfiguration * Conf, int32_t Family, char * ServerName, int32_t ServerPort) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conn = self->Conn;
    HQUIC conf = Conf->Conf;

    QUIC_STATUS res = api->ConnectionStart(conn, conf, Family, ServerName, ServerPort);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicConnection_setConfiguration(QuicConnection * self, QuicConfiguration * Conf) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conn = self->Conn;
    HQUIC conf = Conf->Conf;

    QUIC_STATUS res = api->ConnectionSetConfiguration(conn, conf);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicConnection_sendResumptionTicket(QuicConnection * self, int32_t Flags, int32_t DataLength, void * ResumptionData) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conn = self->Conn;

    QUIC_STATUS res = api->ConnectionSendResumptionTicket(conn, Flags, DataLength, ResumptionData);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT QuicStream * JNICALL JavaCritical_io_vproxy_msquic_QuicConnection_openStream(QuicConnection * self, int32_t Flags, void * Handler, void * Context, int32_t * returnStatus, QuicStream * return_) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conn = self->Conn;

    HQUIC stream;
    QUIC_STATUS res = api->StreamOpen(conn, Flags,
            (QUIC_STREAM_CALLBACK_HANDLER) Handler,
            Context, &stream);
    if (returnStatus != NULL)
        *returnStatus = res;
    if (QUIC_SUCCEEDED(res)) {
        return_->Api = api;
        return_->Stream = stream;
        return return_;
    }
    return NULL;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicConnection_sendDatagram(QuicConnection * self, QUIC_BUFFER * Buffers, int32_t BufferCount, int32_t Flags, void * ClientSendContext) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conn = self->Conn;

    QUIC_STATUS res = api->DatagramSend(conn, Buffers, BufferCount, Flags, ClientSendContext);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicConnection_resumptionTicketValidationComplete(QuicConnection * self, uint8_t Result) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conn = self->Conn;

    QUIC_STATUS res = api->ConnectionResumptionTicketValidationComplete(conn, Result);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

JNIEXPORT int32_t JNICALL JavaCritical_io_vproxy_msquic_QuicConnection_certificateValidationComplete(QuicConnection * self, uint8_t Result, int32_t TlsAlert) {
    QUIC_API_TABLE* api = self->Api;
    HQUIC conn = self->Conn;

    QUIC_STATUS res = api->ConnectionCertificateValidationComplete(conn, Result, TlsAlert);
    if (QUIC_SUCCEEDED(res)) {
        return 0;
    }
    return res;
}

#ifdef __cplusplus
}
#endif
// metadata.generator-version: pni 21.0.0.16
// sha256:bb1253a73ec5c8b62ff6e4ce3ae30600417938b23198692c32d1d1a1fca5c3e2
