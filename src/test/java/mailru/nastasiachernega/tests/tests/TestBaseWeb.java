package mailru.nastasiachernega.tests.tests;

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

    @BeforeAll
    static void setUp() {
        System.getProperty("environmentWeb");
        WebDriver.configure();
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        if (getBrowserName().equals("chrome")) {
            Attach.browserConsoleLogs();
        }
        if (getRunIn().equals("selenoid")) {
            Attach.addVideoSelenoid();
        }
        closeWebDriver();
    }

    private String getRunIn() {
        return ConfigFactory.create(WebConfig.class, System.getProperties()).getRunIn();
    }

    private String getBrowserName() {
        return ConfigFactory.create(WebConfig.class, System.getProperties()).getBrowserName();
    }

}
