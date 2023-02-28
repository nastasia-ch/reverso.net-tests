package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:web/${environmentWeb}.properties",
        "classpath:web/chrome-local.properties"
})
public interface WebConfig extends Config {

    @Key("browser.runIn")
    String getRunIn();

    @Key("browser.name")
    @DefaultValue("chrome")
    String getBrowserName();

    @Key("browser.version")
    String getBrowserVersion();

    @Key("browser.size")
    String getBrowserSize();

    @Key("browser.remoteURLForTestRun")
    String getRemoteURLForTestRun();

}
