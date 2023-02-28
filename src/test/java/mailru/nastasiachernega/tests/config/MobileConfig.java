package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:mobile/${environmentMobile}.properties",
        "classpath:mobile/google-pixel-4-v11.0-emulator.properties"
})
public interface MobileConfig extends Config {

    @Key("mobile.runIn")
    String getRunIn();

    @Key("mobile.platform")
    String getPlatform();

    @Key("mobile.device")
    String getDevice();

    @Key("mobile.osVersion")
    String getOsVersion();

    @Key("mobile.remoteURL")
    String getRemoteURL();

}
