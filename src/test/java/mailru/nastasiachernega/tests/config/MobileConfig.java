package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:mobile/{environmentMobile}.properties",
        //"classpath:mobile/google-pixel-4-v11.0-emulator.properties"
        "classpath:mobile/google-pixel-4-v11.0-remote.properties"
})
public interface MobileConfig extends Config {

    @Key("testType")
    String getTestType();

    @Key("platform")
    String getPlatform();

    @Key("appURL")
    String getAppURL();

    @Key("device")
    String getDevice();

    @Key("osVersion")
    String getOsVersion();

    @Key("remoteURL")
    String getRemoteURL();

    @Key("appPackage")
    String getAppPackage();

    @Key("appActivity")
    String getAppActivity();

    @Key("appPathToSave")
    String getAppPathToSave();

}
