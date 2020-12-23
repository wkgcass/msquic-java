package msquic;

import msquic.nativevalues.ServerResumptionLevel;

public class Settings {
    public Integer idleTimeoutMs;
    public ServerResumptionLevel serverResumptionLevel;
    public Integer peerBidiStreamCount;

    public Settings setIdleTimeoutMs(Integer idleTimeoutMs) {
        this.idleTimeoutMs = idleTimeoutMs;
        return this;
    }

    public Settings setServerResumptionLevel(ServerResumptionLevel serverResumptionLevel) {
        this.serverResumptionLevel = serverResumptionLevel;
        return this;
    }

    public Settings setPeerBidiStreamCount(Integer peerBidiStreamCount) {
        this.peerBidiStreamCount = peerBidiStreamCount;
        return this;
    }
}
