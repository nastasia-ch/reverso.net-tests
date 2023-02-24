package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.config.WebDriverProvider;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.FavouritesApiSteps;
import mailru.nastasiachernega.tests.data.pages.FavouritesPage;
import mailru.nastasiachernega.tests.data.pages.TranslationPage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Epic("Тесты UI")
@Feature("Избранное")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс", url = "https://context.reverso.net/favourites")
public class FavouritesTestsUI extends WebDriverProvider {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    FavouritesApiSteps favouritesApi = new FavouritesApiSteps();

    TranslationPage translationPage = new TranslationPage();
    FavouritesPage favouritesPage = new FavouritesPage();

    TestData data = new TestData();

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка добавления примера в 'Избранное'")
    @Test
    @Tag("add_in_favourites")
    void addInFavourites() {

        step("Авторизуемся через Api", ()-> {
            translationPage.addAuthCookieToWebDriver(data.translationPath,
                    authApi.getRefreshToken(data.emailValid, data.passwordValid));
        });

        step("Открываем страницу", ()-> {
            translationPage.
                    openPage(data.translationPath);
        });

        step("Вводим текст для перевода: " + data.text, ()-> {
            translationPage.
                    setTextToTranslate(data.text);
        });

        step("Устанавливаем язык оригинала: " + data.languageFrom, ()-> {
            translationPage.
                    chooseLanguageFromTranslate(data.languageFrom);
        });

        step("Устанавливаем язык перевода: " + data.languageTo, ()-> {
            translationPage.
                    chooseLanguageToTranslate(data.languageTo);
        });

        step("Нажимаем на кнопку поиска", ()-> {
            translationPage.
                    clickOnSearchButton();
        });

        step("Добавляем " + data.exampleNumber +
                "-й пример в раздел 'Избранное'", ()-> {
            translationPage.
                    addInFavourites(data.exampleNumber);
        });

        step("Переходим в раздел 'Избранное'", ()-> {
            translationPage
                    .openUserMenu()
                    .goToSectionFavourites();
        });

        step("Проверяем, добавлен ли " + data.exampleNumber +
                "-й пример в раздел 'Избранное'", ()-> {
            favouritesPage.checkAddingExampleInFavourites
                            (data.example,
                            data.translatedExample);
        });

        step("Очищаем раздел 'Избранное' после теста через Api", ()-> {
            int exampleId = favouritesPage.getExampleId(data.example);
            favouritesApi.apiDeleteFromFavourites(authApi.getRefreshToken
                            (data.emailValid, data.passwordValid),
                            exampleId);
        });
    };

}
