package mailru.nastasiachernega.tests.tests.testsUI;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import mailru.nastasiachernega.tests.drivers.WebDriver;
import mailru.nastasiachernega.tests.config.WebConfig;
import mailru.nastasiachernega.tests.helpers.Attach;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBaseWeb {
    WebConfig webConfig =
            ConfigFactory.create(WebConfig.class, System.getProperties());

    @BeforeAll
    static void setUp() {
        System.getProperty("environmentWeb");
        WebDriver.configure();
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void tearDown() {
        System.getProperty("environmentWeb");
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        if(webConfig.getBrowserName() != "firefox") {Attach.browserConsoleLogs();}
        if (webConfig.getTestType() == "web_selenoid") {Attach.addVideoSelenoid();}
        closeWebDriver();
    }
}
