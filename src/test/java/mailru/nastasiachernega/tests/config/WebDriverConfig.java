package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${environmentWeb}.properties",
        "classpath:chrome-local.properties"
})
public interface WebDriverConfig extends Config {

    @Key("browser.name")
    @DefaultValue("chrome")
    String getBrowserName();

    @Key("browser.version")
    String getBrowserVersion();

    @Key("browser.size")
    String getBrowserSize();

    @Key("browser.remoteURLForTestRun")
    String getRemoteURLForTestRun();

    @Key("browser.baseURL")
    String getBaseURL();

}
