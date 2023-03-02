package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.FavouritesApiSteps;
import mailru.nastasiachernega.tests.data.pages.FavouritesPage;
import mailru.nastasiachernega.tests.data.pages.TranslatePage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.TestBaseWeb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Features({@Feature("Тесты UI"),@Feature("Избранное")})
//@Epic("Тесты UI")
//@Feature("Избранное")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс 'Reverso Context'",
        url = "https://context.reverso.net/favourites")
public class FavouritesTests extends TestBaseWeb {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    FavouritesApiSteps favouritesApi = new FavouritesApiSteps();

    TranslatePage translationPage = new TranslatePage();
    FavouritesPage favouritesPage = new FavouritesPage();

    TestData data = new TestData();

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Открытие раздела 'Избранное' без авторизации")
    @Test
    @Tag("UI_tests")
    @Tag("favourites_tests")
    void openPageWithoutAuth() {

        step("Открываем страницу 'Избранное' без авторизации", () -> {
            favouritesPage
                    .openPage(data.favouritesPath);
        });

        step("Проверяем на странице наличие сообщения " +
                "о необходимости авторизации для пользования контентом", () -> {
            favouritesPage
                    .checkMessageOpenPageWithoutAuth();
        });
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Добавление примера в 'Избранное'")
    @Test
    @Tag("UI_tests")
    @Tag("favourites_tests")
    void addInFavourites() {

        step("Авторизуемся через Api", () -> {
            translationPage
                    .addAuthCookieToWebDriver(data.translationPath,
                            authApi.getRefreshToken(data.emailValid, data.passwordValid));
        });

        step("Открываем страницу", () -> {
            translationPage
                    .openPage(data.translationPath);
        });

        step("Вводим текст для перевода: " + data.text, () -> {
            translationPage
                    .setTextForTranslation(data.text);
        });

        step("Устанавливаем язык оригинала: " + data.languageFrom, () -> {
            translationPage
                    .chooseLanguageFrom(data.languageFrom);
        });

        step("Устанавливаем язык перевода: " + data.languageTo, () -> {
            translationPage
                    .chooseLanguageTo(data.languageTo);
        });

        step("Нажимаем на кнопку поиска", () -> {
            translationPage
                    .clickOnSearchButton();
        });

        step("Добавляем " + data.exampleNumber +
                "-й пример в раздел 'Избранное'", () -> {
            translationPage
                    .addInFavourites(data.exampleNumber);
        });

        step("Переходим в раздел 'Избранное'", () -> {
            translationPage
                    .openUserMenu()
                    .goToSectionFavourites();
        });

        step("Проверяем, добавлен ли " + data.exampleNumber +
                "-й пример в раздел 'Избранное'", () -> {
            favouritesPage
                    .checkAddingExample(data.example)
                    .checkAddingTranslatedExample(data.translatedExample);
        });

        step("Очищаем раздел 'Избранное' после теста через Api", () -> {
            int exampleId =
                    favouritesPage.getExampleId(data.example);
            favouritesApi
                    .deleteFromFavourites(authApi.getRefreshToken
                            (data.emailValid, data.passwordValid),
                            exampleId);
        });
    }

}
