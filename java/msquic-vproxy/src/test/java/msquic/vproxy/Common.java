package msquic.vproxy;

public class Common {
    private Common() {
    }

    public static final String crtContent = "-----BEGIN CERTIFICATE-----\n" +
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
    public static final String keyContent = "-----BEGIN PRIVATE KEY-----\n" +
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
}
