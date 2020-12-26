package msquic;

import msquic.nativevalues.ExecutionProfile;

public class RegistrationConfig {
    public String appName;
    public ExecutionProfile profile;

    public RegistrationConfig setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public RegistrationConfig setProfile(ExecutionProfile profile) {
        this.profile = profile;
        return this;
    }
}
