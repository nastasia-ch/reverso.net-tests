package mailru.nastasiachernega.tests.tests.testsUI;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import mailru.nastasiachernega.tests.config.WebDriverConfig;
import mailru.nastasiachernega.tests.helpers.Attach;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBaseWeb {

    @BeforeAll
    static void setUp() {

        System.getProperty("environmentWeb");

        WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());

        Configuration.browser = config.getBrowserName();
        Configuration.browserVersion = config.getBrowserVersion();
        Configuration.browserSize = config.getBrowserSize();
        Configuration.baseUrl = config.getBaseURL();
        String remoteUrl= config.getRemoteURLForTestRun();
        if (remoteUrl != null) {
            Configuration.remote = remoteUrl;
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("allure", new AllureSelenide());

    }


    @AfterEach
    void addAttachments () {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class,System.getProperties());
        if(config.getBrowserName().equals("firefox") == false) {
            Attach.browserConsoleLogs();
        }
        Attach.addVideoSelenoid();
        closeWebDriver();
    }

}
