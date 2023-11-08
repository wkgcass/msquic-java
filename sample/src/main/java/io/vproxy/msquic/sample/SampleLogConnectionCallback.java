package io.vproxy.msquic.sample;

import io.vproxy.msquic.callback.LogConnectionCallback;

import java.nio.file.Path;

public class SampleLogConnectionCallback extends LogConnectionCallback {
    private final CommandLine cli;

    public SampleLogConnectionCallback(CommandLine cli) {
        this.cli = cli;
    }

    @Override
    protected Path getSSLKeyLogFilePath() {
        return cli.quicTlsSecretLogFilePath;
    }
}
