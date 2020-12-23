package msquic.internal;

import msquic.*;
import msquic.nativevalues.ListenerEventType;
import msquic.nativevalues.Status;

import java.util.HashMap;
import java.util.Map;

public class InternalListenerCallback {
    public static ConstructConnection constructConnection;

    private final MsQuic msquic;
    private final ListenerCallback cb;
    private final Map<Long, Connection> connWrapperPtrMap = new HashMap<>(); // wrapper -> Conn
    private final Map<Long, Connection> connRealPtrMap = new HashMap<>(); // real -> Conn

    public InternalListenerCallback(MsQuic msquic, ListenerCallback cb) {
        this.msquic = msquic;
        this.cb = cb;
    }

    @SuppressWarnings("unused")
    @UsedByJNI
    public int callback(int type, long newConnection) { // wrapper_ptr
        try {
            cb.callback(new ListenerEvent(ListenerEventType.valueOf(type), getConnectionByWrapperPtr(newConnection)));
        } catch (MsQuicException e) {
            return e.status.intValue;
        } catch (Throwable t) {
            System.out.println("InternalListenerCallback failed with unexpected Exception");
            t.printStackTrace();
            return Status.INTERNAL_ERROR.intValue;
        }
        return Status.SUCCESS.intValue;
    }

    @SuppressWarnings("unused")
    @UsedByJNI
    public long attachOrGetConnectionWrapper(long real, long wrapper) {
        Connection conn = connRealPtrMap.get(real);
        if (conn == null) {
            conn = buildConnection(real, wrapper);
            connRealPtrMap.put(real, conn);
            connWrapperPtrMap.put(wrapper, conn);
        }
        return conn.wrapper;
    }

    public void removeConnection(Connection conn) {
        connRealPtrMap.remove(conn.real);
        connWrapperPtrMap.remove(conn.wrapper);
    }

    private Connection getConnectionByWrapperPtr(long wrapperPtr) {
        if (wrapperPtr == 0) {
            return null;
        }
        Connection conn = connWrapperPtrMap.get(wrapperPtr);
        if (conn == null) {
            throw new RuntimeException("cannot find Connection by wrapper ptr " + wrapperPtr);
        }
        return conn;
    }

    private Connection buildConnection(long real, long wrapperPtr) {
        if (constructConnection == null) {
            Connection.initClass();
        }
        return constructConnection.construct(msquic, real, wrapperPtr, this);
    }

    public interface ConstructConnection {
        Connection construct(MsQuic msquic, long real, long wrapper, InternalListenerCallback listenerCallback);
    }
}
