package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.pages.TranslatePage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.TestBaseWeb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.qameta.allure.Allure.step;

@Epic("Тесты UI")
@Feature("Навигация по меню")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс 'Reverso Context'",
        url = "https://context.reverso.net/translation/")
public class MenuTests extends TestBaseWeb {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslatePage translationPage = new TranslatePage();
    TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Наличие в меню заголовков разделов сайта Reverso Context")
    @Test
    @Tag("UI_tests")
    @Tag("menu_tests")
    void checkReversoHeaders() {

        step("Открываем страницу", () -> {
            translationPage
                    .openPage(data.translationPath);
        });

        step("Проверяем наличие в меню следующих заголовков разделов " +
                "сайта Reverso Context", () -> {
            translationPage
                    .checkReversoHeaders(data.reversoHeaders);
        });
    }

    @ValueSource(strings = {
            "History",
            "Favourites",
            "My contributions"
    })
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Наличие раздела в меню пользователя с авторизацией")
    @ParameterizedTest
    @Tag("UI_tests")
    @Tag("menu_tests")
    void checkUserMenuWithAuth(String sectionName) {

        step("Авторизуемся через Api", () -> {
            translationPage
                    .addAuthCookieToWebDriver(data.translationPath,
                            authApi.getRefreshToken(data.emailValid, data.passwordValid));
        });

        step("Открываем страницу", () -> {
            translationPage
                    .openPage(data.translationPath);
        });

        step("Проверяем наличие раздела в меню пользователя", () -> {
            translationPage
                    .openUserMenu()
                    .isThereSectionInMenu(sectionName);
        });
    }

    @ValueSource(strings = {
            "History",
            "Favourites",
            "My contributions"
    })
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отсутствие раздела в меню пользователя без авторизациии")
    @ParameterizedTest
    @Tag("UI_tests")
    @Tag("menu_tests")
    void checkUserMenuWithoutAuth(String sectionName) {

        step("Открываем страницу", () -> {
            translationPage
                    .openPage(data.translationPath);
        });

        step("Проверяем отсутствие раздела в меню пользователя", () -> {
            translationPage
                    .openUserMenu()
                    .isThereNotSectionInMenu(sectionName);
        });

    }

}
