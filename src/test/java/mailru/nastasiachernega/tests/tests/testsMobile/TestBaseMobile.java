package mailru.nastasiachernega.tests.tests.testsMobile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import mailru.nastasiachernega.tests.drivers.BrowserstackDriver;
import mailru.nastasiachernega.tests.config.MobileConfig;
import mailru.nastasiachernega.tests.drivers.MobileDriver;
import mailru.nastasiachernega.tests.helpers.Attach;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.*;

public class TestBaseMobile {
    static MobileConfig mobileConfig =
            ConfigFactory.create(MobileConfig.class, System.getProperties());

    @BeforeAll
    static void setUp() {
        System.getProperty("environmentMobile");
        String testTypeMobile = mobileConfig.getTestType();
        switch (testTypeMobile) {
            case "mobile":
                Configuration.browser = BrowserstackDriver.class.getName();
                Configuration.browserSize = null;
            case "emulator":
                Configuration.browser = MobileDriver.class.getName();
                Configuration.browserSize = null;
        }
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void addAttachments() {
        System.getProperty("environmentMobile");
        String sessionId = Attach.getSessionId();
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        closeWebDriver();
        if(mobileConfig.getTestType() == "mobile") {Attach.addVideoBrowserstack(sessionId);}
    }
}
