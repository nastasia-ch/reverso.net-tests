package mailru.nastasiachernega.tests.config.browserstack;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:browserstack/{environmentMobile}.properties",
        "classpath:browserstack/google-pixel-3-v9.0-remote.properties"
})
public interface BrowserStackConfig extends Config {

    @Key("browserstack.app")
    @DefaultValue("bs://ac4078b0204a61d67b56d9c428ff2d3ad6d13657")
    String getApp();

    @Key("browserstack.device")
    @DefaultValue("Google Pixel 3")
    String getDevice();

    @Key("browserstack.osVersion")
    @DefaultValue("9.0")
    String getOsVersion();

    @Key("browserstack.baseURL")
    @DefaultValue("http://hub.browserstack.com/wd/hub")
    String getBaseURL();
}
