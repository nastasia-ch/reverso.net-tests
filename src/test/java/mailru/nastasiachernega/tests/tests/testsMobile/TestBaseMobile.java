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
//        Configuration.browser = BrowserstackDriver.class.getName();
//        Configuration.browserSize = null;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        configure();
    }

    public static void configure() {
        System.getProperty("environmentMobile");
        String testType = mobileConfig.getTestType();
        if(testType=="browserstack"){
            Configuration.browser = BrowserstackDriver.class.getName();
        }
        if(testType=="emulator"){
            Configuration.browser = MobileDriver.class.getName();
        }
        Configuration.browserSize = null;
    }


    @BeforeEach
    void addListener() {
        open();
    }

    @AfterEach
    void tearDown() {
        System.getProperty("environmentMobile");
        String sessionId = Attach.getSessionId();
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        closeWebDriver();
//        if(mobileConfig.getTestType() == "browserstack") {Attach.addVideoBrowserstack(sessionId);}
        //Attach.addVideoBrowserstack(sessionId);
    }
}
