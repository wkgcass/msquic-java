package msquic;

import msquic.nativevalues.StreamEventType;

public class StreamEvent {
    public final StreamEventType type;

    public StreamEvent(StreamEventType type) {
        this.type = type;
    }
}
