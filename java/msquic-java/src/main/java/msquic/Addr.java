package msquic;

import msquic.nativevalues.AddressFamily;

public class Addr {
    public final AddressFamily family;
    public final boolean loopback;
    public final int port;

    public Addr(AddressFamily family, boolean loopback, int port) {
        this.family = family;
        this.loopback = loopback;
        this.port = port;
    }

    @Override
    public String toString() {
        return "Addr{" +
            "family=" + family +
            ", loopback=" + loopback +
            ", port=" + port +
            '}';
    }
}
