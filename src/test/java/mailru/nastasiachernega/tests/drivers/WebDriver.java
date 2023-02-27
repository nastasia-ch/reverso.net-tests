package mailru.nastasiachernega.tests.drivers;

import com.codeborne.selenide.Configuration;
import mailru.nastasiachernega.tests.config.WebConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriver {
    static WebConfig webDriverConfig =
            ConfigFactory.create(WebConfig.class, System.getProperties());

    public static void configure() {
        System.getProperty("environmentWeb");
        Configuration.browser = webDriverConfig.getBrowserName();
        Configuration.browserVersion = webDriverConfig.getBrowserVersion();
        Configuration.browserSize = webDriverConfig.getBrowserSize();
        Configuration.baseUrl = webDriverConfig.getBaseURL();
        String remoteUrl= webDriverConfig.getRemoteURLForTestRun();
        if (remoteUrl != null) {Configuration.remote = remoteUrl;}
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }
}
