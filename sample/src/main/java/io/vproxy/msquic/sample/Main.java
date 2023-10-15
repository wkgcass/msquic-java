package io.vproxy.msquic.sample;

import io.vproxy.pni.graal.GraalUtils;

import java.util.Arrays;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || !Set.of("client", "server").contains(args[0])) {
            throw new RuntimeException("first arg should be \"client\" or \"server\": " + Arrays.toString(args));
        }
        var newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);

        System.loadLibrary("msquic");
        System.loadLibrary("msquic-java");
        GraalUtils.init();

        System.out.println("java.library.path = " + System.getProperty("java.library.path"));
        if (args[0].equals("client")) {
            Client.main(newArgs);
        } else {
            Server.main(newArgs);
        }
    }
}
