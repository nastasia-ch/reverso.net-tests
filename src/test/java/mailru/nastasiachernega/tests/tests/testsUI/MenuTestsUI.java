package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.pages.TranslationPage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.qameta.allure.Allure.step;

@Epic("Тесты UI")
@Feature("Меню")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс", url = "https://context.reverso.net/translation/")
public class MenuTestsUI extends TestBaseWeb {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationPage translationPage = new TranslationPage();
    TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка в меню заголовков разделов сайта Reverso Context")
    @Test
    void checkReversoHeaders() {

        step("Открываем страницу", ()-> {
            translationPage.
                    openPage(data.translationPath);
        });

        step("Проверяем наличие в меню следующих заголовков разделов " +
                "сайта Reverso Context", ()-> {
            translationPage.checkReversoHeaders(data.reversoHeaders);
        });
    }

    @ValueSource(strings = {
            "History",
            "Favourites",
            "My contributions"
    })
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка разделов меню пользователя с авторизацией")
    @ParameterizedTest
    void checkUserMenuWithAuth(String sectionName) {

        step("Авторизуемся через Api", ()-> {
            translationPage.addAuthCookieToWebDriver(data.translationPath,
                    authApi.getRefreshToken(data.emailValid, data.passwordValid));
        });

        step("Открываем страницу", ()-> {
            translationPage.
                    openPage(data.translationPath);
        });

        step("Проверяем наличие в меню пользователя раздела", ()-> {
            translationPage
                    .openUserMenu()
                    .IsThereSectionInMenu(sectionName);
        });
    }

    @ValueSource(strings = {
            "History",
            "Favourites",
            "My contributions"
    })
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка разделов меню пользователя без авторизациии")
    @ParameterizedTest
    void checkUserMenuWithoutAuth(String sectionName) {

        step("Открываем страницу", ()-> {
            translationPage.
                    openPage(data.translationPath);
        });

        step("Проверяем отсутствие в меню пользователя раздела", ()-> {
            translationPage
                    .openUserMenu()
                    .IsThereNotSectionInMenu(sectionName);
        });

    }

}
