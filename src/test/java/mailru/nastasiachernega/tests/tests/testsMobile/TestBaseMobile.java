package mailru.nastasiachernega.tests.tests.testsMobile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import mailru.nastasiachernega.tests.config.browserstack.BrowserStackDriver;
import mailru.nastasiachernega.tests.helpers.Attach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;

public class TestBaseMobile {

    @BeforeAll
    static void setUp() {
        System.getProperty("environmentMobile");
        Configuration.browser = BrowserStackDriver.class.getName();
        Configuration.browserSize = null;
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void addAttachments() throws IOException {
        String sessionId = Attach.getSessionId();

        //Attach.screenshotAs("Last screenshot");
        Attach.pageSource();

        closeWebDriver();

        Attach.addVideoBrowserstack(sessionId);
    }

}
