package mailru.nastasiachernega.tests.drivers;

import com.codeborne.selenide.Configuration;
import mailru.nastasiachernega.tests.config.ProjectConfig;
import mailru.nastasiachernega.tests.config.WebConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriver {
    static WebConfig webDriverConfig =
            ConfigFactory.create(WebConfig.class, System.getProperties());
    static ProjectConfig projectConfig =
            ConfigFactory.create(ProjectConfig.class, System.getProperties());

    public static void configure() {
        Configuration.browser = webDriverConfig.getBrowserName();
        Configuration.browserVersion = webDriverConfig.getBrowserVersion();
        Configuration.browserSize = webDriverConfig.getBrowserSize();
        Configuration.baseUrl = projectConfig.getBaseURL();
        String remoteUrl = webDriverConfig.getRemoteURLForTestRun();
        if (remoteUrl != null) {
            Configuration.remote = remoteUrl;
        }
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }
}
