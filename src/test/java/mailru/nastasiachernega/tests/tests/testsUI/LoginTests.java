package mailru.nastasiachernega.tests.tests.testsUI;

import com.codeborne.selenide.Configuration;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTests {

    @Test
    void loginTest() {

        TestData data = new TestData();

        open("https://www.reverso.net/text-translation");
        Configuration.holdBrowserOpen=true;
        $("[aria-label='Login menu']").click();
        $(".app-popup-menu-view__menu").$(byText("Log in")).click();
        $("#Email").setValue(data.email);
        $("#Password").setValue(data.password);
        $("button[type=submit]").click();

        $("[aria-label='Login menu']").click();
        $(".app-popup-menu-view__menu").shouldHave(text(data.email));

    }

}
