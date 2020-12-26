package msquic.internal;

import msquic.*;
import msquic.nativevalues.ConnectionEventType;
import msquic.nativevalues.Status;

import java.util.HashMap;
import java.util.Map;

public class InternalConnectionCallback {
    public static StreamConstructor streamConstructor;

    private final MsQuic msquic;
    private final ConnectionCallback cb;
    private final Map<Long, Stream> streamWrapperPtrMap = new HashMap<>(); // wrapper -> Stream
    private final Map<Long, Stream> streamRealPtrMap = new HashMap<>(); // real -> Stream

    public InternalConnectionCallback(MsQuic msquic, ConnectionCallback cb) {
        this.msquic = msquic;
        this.cb = cb;
    }

    @SuppressWarnings("unused")
    @UsedByJNI
    public int callback(int type,
                        //
                        long PEER_STREAM_STARTED_stream,
                        //
                        boolean SHUTDOWN_COMPLETE_appCloseInProgress,
                        //
                        String LOCAL_ADDRESS_CHANGED_address,
                        //
                        String PEER_ADDRESS_CHANGED_address,
                        //
                        boolean CONNECTED_sessionResumed,
                        String CONNECTED_negotiatedAlpn
                        //
    ) {
        try {
            cb.callback(new ConnectionEvent(ConnectionEventType.valueOf(type),
                getStreamByWrapperPtr(PEER_STREAM_STARTED_stream),
                SHUTDOWN_COMPLETE_appCloseInProgress,
                LOCAL_ADDRESS_CHANGED_address,
                PEER_ADDRESS_CHANGED_address,
                CONNECTED_sessionResumed,
                CONNECTED_negotiatedAlpn
            ));
        } catch (MsQuicException e) {
            return e.status.intValue;
        } catch (Throwable t) {
            System.out.println("InternalConnectionCallback failed with unexpected Exception");
            t.printStackTrace();
            return Status.INTERNAL_ERROR.intValue;
        }
        return Status.SUCCESS.intValue;
    }

    @SuppressWarnings("unused")
    @UsedByJNI
    public long attachOrGetStreamWrapper(long real, long wrapper) {
        Stream stream = streamRealPtrMap.get(real);
        if (stream == null) {
            stream = buildStream(real, wrapper);
            streamRealPtrMap.put(real, stream);
            streamWrapperPtrMap.put(wrapper, stream);
        }
        return stream.wrapper;
    }

    public void removeStream(Stream s) {
        streamRealPtrMap.remove(s.real);
        streamWrapperPtrMap.remove(s.wrapper);
    }

    public Stream getStreamByWrapperPtr(long wrapperPtr) {
        if (wrapperPtr == 0) {
            return null;
        }
        Stream stream = streamWrapperPtrMap.get(wrapperPtr);
        if (stream == null) {
            throw new RuntimeException("cannot find Stream by wrapper ptr " + wrapperPtr);
        }
        return stream;
    }

    private Stream buildStream(long real, long wrapper) {
        if (streamConstructor == null) {
            Stream.initClass();
        }
        return streamConstructor.construct(msquic, real, wrapper, this);
    }

    public interface StreamConstructor {
        Stream construct(MsQuic msquic, long real, long wrapper, InternalConnectionCallback connectionCallback);
    }
}
