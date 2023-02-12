package mailru.nastasiachernega.tests.tests.testsUI;

import com.codeborne.selenide.WebDriverRunner;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.components.AuthorizationApi;
import mailru.nastasiachernega.tests.tests.components.ContextTranslationApi;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class HistoryTests {

    ContextTranslationApi contextTranslationApi = new ContextTranslationApi();
    AuthorizationApi authApi = new AuthorizationApi();
    TestData data = new TestData();

    @Test
    void checkAddingInHistory() {

        step("Отправка запроса на перевод текста '" +
                data.languageFromTo + "' через Api", ()-> {
            contextTranslationApi.getTranslationsApi(data.languageFromTo,
                    data.textForTranslation);
        });

        step("Авторизация через Api", ()-> {
            open("https://context.reverso.net/history");
            Cookie cookie = new Cookie("reverso.net.ReversoRefreshToken",
                    authApi.getRefreshToken(data.accountURL, data.email, data.password, data.returnURL));
            WebDriverRunner.getWebDriver().manage().addCookie(cookie);
        });

        step("Проверка наличия текста перевода'" +
                 data.languageFromTo + "' во вкладке 'История'", ()-> {
            open("https://context.reverso.net/history");
            $(".results").shouldHave(text(data.textForTranslation));
        });

        //удаление из истории

    }



    //$$("#examples-content .example").get(0).

}
