package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.Epic;
import mailru.nastasiachernega.tests.config.WebDriverProvider;
import mailru.nastasiachernega.tests.data.pages.HistoryPage;
import mailru.nastasiachernega.tests.data.pages.TranslationPage;
import mailru.nastasiachernega.tests.data.pages.FavouritesPage;
import mailru.nastasiachernega.tests.data.pages.LoginPage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.FavouritesApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.HistoryApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.TranslationApiSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Epic("UI tests")
public class TestsUI extends WebDriverProvider {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationApiSteps translationApi = new TranslationApiSteps();
    FavouritesApiSteps favouritesApi = new FavouritesApiSteps();
    HistoryApiSteps historyApi = new HistoryApiSteps();

    LoginPage loginPage = new LoginPage();
    TranslationPage translationPage = new TranslationPage();
    FavouritesPage favouritesPage = new FavouritesPage();
    HistoryPage historyPage = new HistoryPage();

    TestData data = new TestData();

    @DisplayName("Проверка наличия на странице заголовков разделов сайта Reverso Context")
    @Test
    void checkReversoHeaders() {

        step("Открываем страницу", ()-> {
            translationPage.
                    openPage(data.translationPath);
        });

        step("Проверяем наличие следующих заголовков разделов " +
                "сайта Reverso Context: " + data.reversoHeaders, ()-> {
            translationPage.checkReversoHeaders(data.reversoHeaders);
        });

    }

    @DisplayName("Проверка авторизации с невалидными электронным адресом и паролем")
    @Test
    void loginTestWithInvalidData() {

        step("Открываем страницу", ()-> {
            translationPage.
                    openPage(data.translationPath);
        });

        step("Переходим в раздел авторизации", ()-> {
            translationPage.goToLoginSection();
        });

        step("Вводим невалидный электронный адрес", ()-> {
            loginPage.setEmail(data.emailInvalid);
        });

        step("Вводим невалидный пароль", ()-> {
            loginPage.setPassword(data.passwordInvalid);
        });

        step("Нажимаем кнопку авторизации", ()-> {
            loginPage.clickLoginButton();
        });

        step("Проверяем отображение информации об ошибке: '" +
                data.errorLoginInfo + "'", ()-> {
            loginPage.checkLoginError(data.errorLoginInfo);
        });

    }

    @DisplayName("Проверка авторизации с валидными электронным адресом и паролем")
    @Test
    void loginTestWithValidData() {

        step("Открываем страницу", ()-> {
            translationPage.
                    openPage(data.translationPath);
        });

        step("Переходим в раздел авторизации", ()-> {
            translationPage.goToLoginSection();
        });

        step("Вводим электронный адрес зарегистрированного пользователя", ()-> {
            loginPage.setEmail(data.emailValid);
        });

        step("Вводим валидный пароль", ()-> {
            loginPage.setPassword(data.passwordValid);
        });

        step("Нажимаем кнопку авторизации", ()-> {
            loginPage.clickLoginButton();
        });

        step("Проверяем авторизацию", ()-> {
            translationPage.checkLogin(data.username);
        });

    }

    @DisplayName("Проверка перевода введенного текста")
    @Test
    void checkTextTranslations() {

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

        step("Устанавливаем язык введенного текста, " +
                "с которого будем переводить: " + data.languageFrom, ()-> {
            translationPage.
                    chooseLanguageFromTranslate(data.languageFrom);
        });

        step("Устанавливаем язык, на который " +
                "будем переводить: " + data.languageTo, ()-> {
            translationPage.
                    chooseLanguageToTranslate(data.languageTo);
        });

        step("Нажимаем на кнопку поиска", ()-> {
            translationPage.
                    clickOnSearchButton();
        });

        step("Результаты перевода должны содержать " +
                "следующие варианты: " + data.translations.toString(), ()-> {
            translationPage.checkTranslations(data.translations);
        });

    }

    @DisplayName("Проверка корректного отображения примеров: " +
            "содержание в них введенного текста и его перевода")
    @Test
    void checkExampleContent() {

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

        step("Устанавливаем язык текста, " + data.languageFrom, ()-> {
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

        step("Проверямем в " + data.exampleNumber + "-м примере содержание " +
                "текста '" + data.text + "'", ()-> {
            translationPage.
                    checkExampleConsistInputText(data.exampleNumber, data.text);
        });

        step("Проверямем в " + data.exampleNumber + "-м примере содержание " +
                "одного из вариантов перевода '" + data.translations + "'", ()-> {
            translationPage.
                    checkExampleConsistTranslatedText(data.exampleNumber, data.translations);
        });

    }

    @Tag("Add_in_favourites")
    @DisplayName("Проверка добавления примера в 'Избранное'")
    @Test
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

        step("Устанавливаем язык введенного текста, " +
                "с которого будем переводить: " + data.languageFrom, ()-> {
            translationPage.
                    chooseLanguageFromTranslate(data.languageFrom);
        });

        step("Устанавливаем язык, на который " +
                "будем переводить: " + data.languageTo, ()-> {
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
            translationPage.
                    goToSectionFavourites();
        });

        step("Проверяем, добавлен ли " + data.exampleNumber +
                "-й пример в раздел 'Избранное'", ()-> {
            favouritesPage.checkAddingExampleInFavourites
                            (data.example,
                            data.translatedExample);
        });

        step("Очищаем раздел 'Избранное' после теста через Api", ()-> {
            favouritesApi.
                    apiDeleteFromFavourites(authApi.getRefreshToken(data.emailValid, data.passwordValid),
                            favouritesPage.getExampleId(data.example));
        });

    };


    @DisplayName("Проверка добавления примера в раздел 'История'")
    @Test
    void addInHistory() {

        step("Отправляем запрос на перевод через Api", ()-> {
            translationApi.
                    apiTranslation(authApi.getRefreshToken(data.emailValid, data.passwordValid),
                            data.languageFromTo, data.text);
        });

        step("Авторизуемся через Api", ()-> {
            translationPage.addAuthCookieToWebDriver(data.translationPath,
                    authApi.getRefreshToken(data.emailValid, data.passwordValid));
        });

        step("Открываем страницу", ()-> {
            historyPage.openPage(data.historyPath);
        });

        step("Проверяем наличие текста перевода в разделе 'История'", ()-> {
            historyPage.checkAddingExampleInHistory(data.text);
        });

        step("Очищаем раздел 'История' после теста через Api", ()-> {
            historyApi.
                    apiDeleteFromHistory(authApi.getRefreshToken(data.emailValid, data.passwordValid),
                                    historyPage.getHistoryId(data.text));
        });

    };

}
