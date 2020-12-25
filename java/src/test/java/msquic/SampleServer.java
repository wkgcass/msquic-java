package msquic;

import msquic.nativevalues.*;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SampleServer {
    private static Registration reg = null;
    private static Configuration conf = null;
    private static Listener lsn = null;

    public static void main(String[] args) throws Exception {
        { // this is optional
            MsQuic.setMemoryAllocator(new DirectByteBufferMemoryAllocator());
        }
        MsQuic msquic = MsQuic.open();
        Path crt = Files.createTempFile("sample-", ".crt");
        Path key = Files.createTempFile("sample-", ".key");
        try {
            reg = msquic.openRegistration(new RegistrationConfig()
                .setAppName("quicsample")
                .setProfile(ExecutionProfile.LOW_LATENCY));
            conf = reg.openConfiguration(Set.of("sample"), new Settings()
                .setIdleTimeoutMs(1000)
                .setServerResumptionLevel(ServerResumptionLevel.RESUME_AND_ZERORTT)
                .setPeerBidiStreamCount(1));
            Files.write(crt, crtContent.getBytes(StandardCharsets.UTF_8));
            Files.write(key, keyContent.getBytes(StandardCharsets.UTF_8));
            conf.loadCredential(crt.toAbsolutePath().toString(), key.toAbsolutePath().toString());
            lsn = reg.openListener(lsnEvent -> {
                if (lsnEvent.type == ListenerEventType.NEW_CONNECTION) {
                    Connection conn = lsnEvent.NEW_CONNECTION.connection;
                    conn.setCallbackHandler(connEvent -> {
                        switch (connEvent.type) {
                            case CONNECTED:
                                System.out.println("[conn][" + conn + "] Connected");
                                conn.sendResumptionTicket(SendResumptionFlags.NONE);
                                break;
                            case SHUTDOWN_INITIATED_BY_TRANSPORT:
                                System.out.println("[conn][" + conn + "] Shut down by transport");
                                break;
                            case SHUTDOWN_INITIATED_BY_PEER:
                                System.out.println("[conn][" + conn + "] Shut down by peer");
                                break;
                            case SHUTDOWN_COMPLETE:
                                System.out.println("[conn][" + conn + "] All done");
                                conn.close();
                                break;
                            case PEER_STREAM_STARTED:
                                Stream stream = connEvent.PEER_STREAM_STARTED.stream;
                                System.out.println("[strm][" + stream + "] Peer started");
                                stream.setCallbackHandler(streamEvent -> {
                                    switch (streamEvent.type) {
                                        case SEND_COMPLETE:
                                            System.out.println("[strm][" + stream + "] Data sent");
                                            U.invokeCleaner(stream.pollWBuf());
                                            break;
                                        case RECEIVE:
                                            System.out.println("[strm][" + stream + "] Data received");
                                            break;
                                        case PEER_SEND_SHUTDOWN:
                                            System.out.println("[strm][" + stream + "] Peer shut down");
                                            System.out.println("[strm][" + stream + "] Sending data...");
                                            ByteBuffer buf = ByteBuffer.allocateDirect(100);
                                            ThreadLocalRandom rnd = ThreadLocalRandom.current();
                                            byte[] bytes = new byte[100];
                                            rnd.nextBytes(bytes);
                                            buf.put(bytes);
                                            try {
                                                stream.send(SendFlags.FIN, buf);
                                            } catch (MsQuicException e) {
                                                System.out.println("StreamSend failed: " + e.status);
                                                U.invokeCleaner(buf);
                                            }
                                            break;
                                        case PEER_SEND_ABORTED:
                                            System.out.println("[strm][" + stream + "] Peer aborted");
                                            break;
                                        case SHUTDOWN_COMPLETE:
                                            System.out.println("[strm][" + stream + "] All done");
                                            break;
                                        default:
                                            break;
                                    }
                                });
                                break;
                            case RESUMED:
                                System.out.println("[conn][" + conn + "] Connection resumed!");
                                break;
                            default:
                                break;
                        }
                    });
                    try {
                        conn.setConfiguration(conf);
                    } catch (MsQuicException e) {
                        conn.close();
                        throw e;
                    }
                    return;
                }
                throw new MsQuicException(Status.NOT_SUPPORTED);
            });
            lsn.start(Set.of("sample"), new Addr(AddressFamily.UNSPEC, false, 4567));

            Scanner sc = new Scanner(System.in);
            System.out.println("Press Enter to exit.");
            sc.nextLine();
        } finally {
            try {
                Files.deleteIfExists(crt);
            } catch (IOException ignore) {
            }
            try {
                Files.deleteIfExists(key);
            } catch (IOException ignore) {
            }
            if (lsn != null) {
                lsn.close();
            }
            if (conf != null) {
                conf.close();
            }
            if (reg != null) {
                reg.close();
            }
            msquic.close();
            { // this is optional
                MsQuic.UNSAFE_release();
            }
        }
    }

    static final Unsafe U;
    private static final String crtContent = "-----BEGIN CERTIFICATE-----\n" +
        "MIIDqzCCApOgAwIBAgIJAIvTzI2C9khpMA0GCSqGSIb3DQEBCwUAMGsxCzAJBgNV\n" +
        "BAYTAkNOMREwDwYDVQQIDAhaaGVqaWFuZzERMA8GA1UEBwwISGFuZ3pob3UxDTAL\n" +
        "BgNVBAoMBEhvbWUxDTALBgNVBAsMBEhvbWUxGDAWBgNVBAMMD3drZ2Nhc3MgQ0Eg\n" +
        "cm9vdDAeFw0xOTEyMjcwNDM4MDhaFw0yMDEyMjYwNDM4MDhaMF4xCzAJBgNVBAYT\n" +
        "AlVTMQswCQYDVQQIDAJOWTELMAkGA1UEBwwCTlkxDzANBgNVBAoMBkdvb2dsZTEP\n" +
        "MA0GA1UECwwGR29vZ2xlMRMwEQYDVQQDDApnb29nbGUuY29tMIIBIjANBgkqhkiG\n" +
        "9w0BAQEFAAOCAQ8AMIIBCgKCAQEAslkiuiaeiU+9C6pCZZOrwNul89W3os2spvV8\n" +
        "ywtng/oDjx+dH/taHjWjXbh+0muqoy+I8VCwovs/HBmXj8Dx7fmVi3MOXQZHmFV6\n" +
        "v0S4EVFD3Q3iuOHc/gCVq9xa/+xzOdhYU6zGQ/7rOeLmjIDty+yjbJiUILnK88bp\n" +
        "LYJBbC0GLtU+H/c58QddRwmmakkMxRRzC+qfyBRFGUcP7za8Cubgl2sVlc+3QS4A\n" +
        "es2QUV3z+tqCfNjujurgyCCDOHm1+4be/ebzD77iWuOQPveFvzVI4u8qnmu7Rgqg\n" +
        "jXhwO2dnTqkf0KOfSEBnwhIGvqY05ifAiK/M8/buQOP/G21ZJwIDAQABo18wXTAJ\n" +
        "BgNVHRMEAjAAMAsGA1UdDwQEAwIF4DBDBgNVHREEPDA6ggwqLmdvb2dsZS5jb22C\n" +
        "Cmdvb2dsZS5jb22CDyouZ29vZ2xlLmNvbS5oa4INZ29vZ2xlLmNvbS5oazANBgkq\n" +
        "hkiG9w0BAQsFAAOCAQEAdNPlqIGcnsn4Ggyia5KsPI2/RDVI0DBWi3IyWE/Wl+Xu\n" +
        "dW6PJzpleyftoYNYCyt7bot5Y8yTFi4C1ClHz54bGTQ5ec6d/lrBIFNQLmrOOa2q\n" +
        "lwc0XtTPfDz42Z2PLwZW19YUahkslrPNJVe5qrrfzd1TO9BqxEBA7nWvUFmksYcn\n" +
        "ZD/6pgAXW2zzG7WHnEOpysXhw41GEN5z7eJPNgEA6lw+9i81p0hUbt6ReL2ywcMc\n" +
        "j5B2jshR+aUAMgXuJYfIz0MLwo22lm3wKQf8LGKNpP84mCS+wrj1Z6FTFay2aM/4\n" +
        "EBwtLx5zUHQCFtqt5BncsxG60sXX9VjHwYzqCHy22g==\n" +
        "-----END CERTIFICATE-----\n";
    private static final String keyContent = "-----BEGIN PRIVATE KEY-----\n" +
        "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCyWSK6Jp6JT70L\n" +
        "qkJlk6vA26Xz1beizaym9XzLC2eD+gOPH50f+1oeNaNduH7Sa6qjL4jxULCi+z8c\n" +
        "GZePwPHt+ZWLcw5dBkeYVXq/RLgRUUPdDeK44dz+AJWr3Fr/7HM52FhTrMZD/us5\n" +
        "4uaMgO3L7KNsmJQgucrzxuktgkFsLQYu1T4f9znxB11HCaZqSQzFFHML6p/IFEUZ\n" +
        "Rw/vNrwK5uCXaxWVz7dBLgB6zZBRXfP62oJ82O6O6uDIIIM4ebX7ht795vMPvuJa\n" +
        "45A+94W/NUji7yqea7tGCqCNeHA7Z2dOqR/Qo59IQGfCEga+pjTmJ8CIr8zz9u5A\n" +
        "4/8bbVknAgMBAAECggEBAIM2QO5ja0/qclMauC6zLjF9Z+K04Z3NY7CR+3YGtenL\n" +
        "DsNFpvvYmLyRCdfx3JxCyg+08TNZAhtmbU/nJDKG6XcDoJov0+lsrU/N07jUffd/\n" +
        "qkX/6UXMJiJZm8QNIoYXF87+9DzbaCKucbDs1mGYmVrmhnVm69QH3ODs/rCUnD1Q\n" +
        "yDK8JmjoH8GhaCBfvBkUvFCBDWUZalOfchWAn1eQMxv4D8OYRXR14bh5Cv/SJAuF\n" +
        "lKsYvhYI5jBLRNOS7cpf8c6c7YsIcpDf0Ing4fGBHJotbGgvuuP9sZeTQm/xeO7x\n" +
        "4hsz52+2ctJaU0ZSuLK6bqZXxUMdYHM5vCBFLgoHmQECgYEA2vpgaR4j70bIycbq\n" +
        "NesbTruCqwFi3pM3J/3Osi/h6Klz6xHY5XGvc3N2k3551B39Hg1D45jZoInWYJlS\n" +
        "i6u+lG86YSVzOrfHui7/rRpkwQhxw9uEi0IF3B86UnEi0+++cpPlpM95HXDRVTRH\n" +
        "jxEcuoFcXYUj94lW5WqVFjrdqxECgYEA0IBAieIwWh3mLN7CTzEPz5gsfQoG4xNB\n" +
        "lJtvQC71n7VSRouv9TPTAvPCiS9QGq292yYjFLlmU/XH+aBiqqQP49a4/pbLqD0F\n" +
        "+S88+ws93rEEz+Ivm4+T7ZC4zjMqXaRVfGlu+48LqBC0vrQoLHeedCVNTN5omj6x\n" +
        "n3HYwHHXELcCgYEAj1deTPEh7LuVLCA9qFXiZkNwYahio/gSHueRqiqV4sspyjLA\n" +
        "nFEy3Iw0jpA4B5Yp3sYoLpAbxW71Gf7DfhJKirfUq0rshv9Oip3BV/rzATkZ32+O\n" +
        "7+mkFFeMwfK1La6+KBqQNLZrPc5f+TpjrU8yUxPi9oT06lDIxRxjw019VUECgYEA\n" +
        "nkg+tm4X9tGr43RYXnYIYrhLTcFG6Su9JWu6USdVICEujI+OzL7C+gLDNBHO5fHv\n" +
        "p2aUSy9UF3kGjuLLBG/4ACcJ2Xvwr49j8X+C87HdDimkVYyIR7f/vOGY6jC9gMk4\n" +
        "fHIt5pr5ZmUIUZ3Cwb8tc06+GBTfo/jkLv7mZXIGqpECgYBKoSOliG6k+9lJjjkA\n" +
        "LgNAaPMM63VSSomibMoup607gbxvvo6DHNGSsvmRt7qWldEzLhBXSkzbYqvO8GJJ\n" +
        "yXjgioRbuEqcjo/bamQHg3D5U3eDdelETapWsp8jq8K+L5I3rLxwZwgFaqT3d3Yh\n" +
        "KszwcglypMXpemRptTLSi7cNPw==\n" +
        "-----END PRIVATE KEY-----\n";

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            U = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static class DirectByteBufferMemoryAllocator implements MemoryAllocator<ByteBuffer> {
        @Override
        public ByteBuffer allocate(int size) {
            return ByteBuffer.allocateDirect(size);
        }

        @Override
        public ByteBuffer getMemory(ByteBuffer mem) {
            return mem;
        }

        @Override
        public void release(ByteBuffer mem) {
            U.invokeCleaner(mem);
        }
    }
}
