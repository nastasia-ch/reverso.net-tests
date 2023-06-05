package mailru.nastasiachernega.tests.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import mailru.nastasiachernega.tests.drivers.BrowserstackDriver;
import mailru.nastasiachernega.tests.config.MobileConfig;
import mailru.nastasiachernega.tests.drivers.EmulatorDriver;
import mailru.nastasiachernega.tests.helpers.Attach;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.*;

public class TestBaseMobile {

    @BeforeAll
    static void setUp() {
        System.getProperty("environmentMobile");
        selectDriver();
        Configuration.browserSize = null;
    }

    static void selectDriver() {
        if (getRunIn().equals("browserstack")) {
            Configuration.browser = BrowserstackDriver.class.getName();
        } else if (getRunIn().equals("emulator")) {
            Configuration.browser = EmulatorDriver.class.getName();
        }
    }

    static String getRunIn() {
        return ConfigFactory.create(MobileConfig.class, System.getProperties()).getRunIn();
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void tearDown() {
        String sessionId = Attach.getSessionId();
        //Attach.screenshotAs("Last screenshot");
        //Attach.pageSource();
        closeWebDriver();
        if (getRunIn().equals("browserstack")) {
            Attach.addVideoBrowserstack(sessionId);
        }
    }
}
