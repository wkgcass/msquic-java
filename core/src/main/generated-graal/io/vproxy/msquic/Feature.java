package io.vproxy.msquic;

import io.vproxy.pni.*;
import io.vproxy.pni.graal.*;
import io.vproxy.r.org.graalvm.nativeimage.*;
import java.lang.foreign.*;
import java.nio.ByteBuffer;
import org.graalvm.nativeimage.*;
import org.graalvm.nativeimage.hosted.*;

public class Feature implements org.graalvm.nativeimage.hosted.Feature {
    @Override
    public void duringSetup(DuringSetupAccess access) {
        /* PNIFunc & PNIRef & GraalThread */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class), Linker.Option.isTrivial());
        RuntimeClassInitialization.initializeAtBuildTime(GraalPNIFunc.class);
        RuntimeClassInitialization.initializeAtBuildTime(GraalPNIRef.class);
        /* ImageInfo */
        RuntimeClassInitialization.initializeAtRunTime(ImageInfoDelegate.class);
        for (var m : ImageInfo.class.getMethods()) {
            RuntimeReflection.register(m);
        }

        /* JavaCritical_io_vproxy_msquic_MsQuic_open */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(MemoryLayout.class /* io.vproxy.msquic.QuicApiTable.LAYOUT.getClass() */, int.class /* Version */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */));

        /* JavaCritical_io_vproxy_msquic_MsQuic_buildQuicAddr */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(boolean.class, String.class /* addr */, int.class /* port */, MemoryLayout.class /* io.vproxy.msquic.QuicAddr.LAYOUT.getClass() */ /* result */));

        /* graal upcall for io.vproxy.msquic.MsQuicUpcall */
        RuntimeClassInitialization.initializeAtBuildTime(io.vproxy.msquic.MsQuicUpcall.class);
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class, MemorySegment.class, MemorySegment.class), Linker.Option.isTrivial());

        /* JavaCritical_io_vproxy_msquic_MsQuicValues_QuicStatusString */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(String.class, int.class /* status */));

        /* JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_STATUS_NOT_SUPPORTED */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class));

        /* JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_STATUS_PENDING */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class));

        /* JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_ADDRESS_FAMILY_UNSPEC */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class));

        /* JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_ADDRESS_FAMILY_INET */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class));

        /* JavaCritical_io_vproxy_msquic_MsQuicValues_QUIC_ADDRESS_FAMILY_INET6 */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class));

/* JavaCritical_io_vproxy_msquic_QuicAddr___getLayoutByteSize */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(long.class), Linker.Option.isTrivial());

        /* JavaCritical_io_vproxy_msquic_QuicAddr_getFamily */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */));

        /* JavaCritical_io_vproxy_msquic_QuicAddr_setFamily */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */, int.class /* family */));

        /* JavaCritical_io_vproxy_msquic_QuicAddr_getPort */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */));

        /* JavaCritical_io_vproxy_msquic_QuicAddr_setPort */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */, int.class /* port */));

        /* JavaCritical_io_vproxy_msquic_QuicAddr_toString */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */, String.class /* str */));

        /* JavaCritical_io_vproxy_msquic_QuicApiTable_close */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */));

        /* JavaCritical_io_vproxy_msquic_QuicApiTable_setContext */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */, MemorySegment.class /* Handle */, MemorySegment.class /* Context */));

        /* JavaCritical_io_vproxy_msquic_QuicApiTable_getContext */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(MemorySegment.class, MemorySegment.class /* self */, MemorySegment.class /* Handle */));

        /* JavaCritical_io_vproxy_msquic_QuicApiTable_setCallbackHandler */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */, MemorySegment.class /* Handle */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */));

        /* JavaCritical_io_vproxy_msquic_QuicApiTable_setParam */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, MemorySegment.class /* Handle */, int.class /* Param */, int.class /* BufferLength */, MemorySegment.class /* Buffer */));

        /* JavaCritical_io_vproxy_msquic_QuicApiTable_getParam */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, MemorySegment.class /* Handle */, int.class /* Param */, MemorySegment.class /* BufferLength */, MemorySegment.class /* Buffer */));

        /* JavaCritical_io_vproxy_msquic_QuicApiTable_openRegistration */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(MemoryLayout.class /* io.vproxy.msquic.QuicRegistration.LAYOUT.getClass() */, MemorySegment.class /* self */, MemoryLayout.class /* io.vproxy.msquic.QuicRegistrationConfig.LAYOUT.getClass() */ /* Config */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */));

        /* JavaCritical_io_vproxy_msquic_QuicConfiguration_close */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */));

        /* JavaCritical_io_vproxy_msquic_QuicConfiguration_loadCredential */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, MemoryLayout.class /* io.vproxy.msquic.QuicCredentialConfig.LAYOUT.getClass() */ /* CredConfig */));

        /* JavaCritical_io_vproxy_msquic_QuicConnection_close */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */));

        /* JavaCritical_io_vproxy_msquic_QuicConnection_shutdown */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */, int.class /* Flags */, long.class /* ErrorCode */));

        /* JavaCritical_io_vproxy_msquic_QuicConnection_start */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, MemoryLayout.class /* io.vproxy.msquic.QuicConfiguration.LAYOUT.getClass() */ /* Conf */, int.class /* Family */, String.class /* ServerName */, int.class /* ServerPort */));

        /* JavaCritical_io_vproxy_msquic_QuicConnection_setConfiguration */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, MemoryLayout.class /* io.vproxy.msquic.QuicConfiguration.LAYOUT.getClass() */ /* Conf */));

        /* JavaCritical_io_vproxy_msquic_QuicConnection_sendResumptionTicket */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, int.class /* Flags */, int.class /* DataLength */, MemorySegment.class /* ResumptionData */));

        /* JavaCritical_io_vproxy_msquic_QuicConnection_openStream */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(MemoryLayout.class /* io.vproxy.msquic.QuicStream.LAYOUT.getClass() */, MemorySegment.class /* self */, int.class /* Flags */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */));

        /* JavaCritical_io_vproxy_msquic_QuicConnection_sendDatagram */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, MemoryLayout.class /* io.vproxy.msquic.QuicBuffer.LAYOUT.getClass() */ /* Buffers */, int.class /* BufferCount */, int.class /* Flags */, MemorySegment.class /* ClientSendContext */));

        /* JavaCritical_io_vproxy_msquic_QuicConnection_resumptionTicketValidationComplete */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, boolean.class /* Result */));

        /* JavaCritical_io_vproxy_msquic_QuicConnection_certificateValidationComplete */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, boolean.class /* Result */, int.class /* TlsAlert */));

        /* JavaCritical_io_vproxy_msquic_QuicListener_close */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */));

        /* JavaCritical_io_vproxy_msquic_QuicListener_start */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, MemorySegment.class /* AlpnBuffers */, int.class /* AlpnBufferCount */, MemoryLayout.class /* io.vproxy.msquic.QuicAddr.LAYOUT.getClass() */ /* Addr */));

        /* JavaCritical_io_vproxy_msquic_QuicListener_stop */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */));

        /* JavaCritical_io_vproxy_msquic_QuicRegistration_close */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */));

        /* JavaCritical_io_vproxy_msquic_QuicRegistration_shutdown */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */, int.class /* Flags */, long.class /* ErrorCode */));

        /* JavaCritical_io_vproxy_msquic_QuicRegistration_openConfiguration */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(MemoryLayout.class /* io.vproxy.msquic.QuicConfiguration.LAYOUT.getClass() */, MemorySegment.class /* self */, MemorySegment.class /* AlpnBuffers */, int.class /* AlpnBufferCount */, MemoryLayout.class /* io.vproxy.msquic.QuicSettings.LAYOUT.getClass() */ /* Settings */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */));

        /* JavaCritical_io_vproxy_msquic_QuicRegistration_openListener */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(MemoryLayout.class /* io.vproxy.msquic.QuicListener.LAYOUT.getClass() */, MemorySegment.class /* self */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */));

        /* JavaCritical_io_vproxy_msquic_QuicRegistration_openConnection */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(MemoryLayout.class /* io.vproxy.msquic.QuicConnection.LAYOUT.getClass() */, MemorySegment.class /* self */, MemorySegment.class /* Handler */, MemorySegment.class /* Context */, MemorySegment.class /* returnStatus */, MemorySegment.class /* return */));

        /* JavaCritical_io_vproxy_msquic_QuicStream_close */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */));

        /* JavaCritical_io_vproxy_msquic_QuicStream_start */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, int.class /* Flags */));

        /* JavaCritical_io_vproxy_msquic_QuicStream_shutdown */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, int.class /* Flags */, long.class /* ErrorCode */));

        /* JavaCritical_io_vproxy_msquic_QuicStream_send */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, MemoryLayout.class /* io.vproxy.msquic.QuicBuffer.LAYOUT.getClass() */ /* Buffers */, int.class /* BufferCount */, int.class /* Flags */, MemorySegment.class /* ClientSendContext */));

        /* JavaCritical_io_vproxy_msquic_QuicStream_receiveComplete */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(void.class, MemorySegment.class /* self */, long.class /* BufferLength */));

        /* JavaCritical_io_vproxy_msquic_QuicStream_receiveSetEnabled */
        RuntimeForeignAccess.registerForDowncall(PanamaUtils.buildCriticalFunctionDescriptor(int.class, MemorySegment.class /* self */, boolean.class /* IsEnabled */));
    }
}
// metadata.generator-version: pni 21.0.0.16
// sha256:69da987f4a47fe0b98b8a7ffae6319eb644e3cb7323c96ad8c03e18179310900
