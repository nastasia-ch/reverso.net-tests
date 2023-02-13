package mailru.nastasiachernega.tests.tests.testsUI;

import mailru.nastasiachernega.tests.config.ProjectProvider;
import mailru.nastasiachernega.tests.data.pages.HistoryPage;
import mailru.nastasiachernega.tests.data.pages.TranslationPage;
import mailru.nastasiachernega.tests.data.pages.FavouritesPage;
import mailru.nastasiachernega.tests.data.pages.LoginPage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.components.AuthorizationApiComponent;
import mailru.nastasiachernega.tests.tests.components.FavouritesApiComponent;
import mailru.nastasiachernega.tests.tests.components.HistoryApiComponent;
import mailru.nastasiachernega.tests.tests.components.TranslationApiComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class testsUI extends ProjectProvider {

    AuthorizationApiComponent authApi = new AuthorizationApiComponent();
    TranslationApiComponent translationApi = new TranslationApiComponent();
    FavouritesApiComponent favouritesApi = new FavouritesApiComponent();
    HistoryApiComponent historyApi = new HistoryApiComponent();

    LoginPage loginPage = new LoginPage();
    TranslationPage contextTranslationPage = new TranslationPage();
    FavouritesPage favouritesPage = new FavouritesPage();
    HistoryPage historyPage = new HistoryPage();

    TestData data = new TestData();

    @DisplayName("Проверка наличия на странице заголовков разделов сайта Reverso Context")
    @Test
    void checkReversoHeaders() {

        step("Открываем страницу", ()-> {
            contextTranslationPage.
                    openPage(data.translationPath);
        });

        step("Проверяем наличие следующих заголовков разделов " +
                "сайта Reverso Context: " + data.reversoHeaders, ()-> {
            contextTranslationPage.checkReversoHeaders(data.reversoHeaders);
        });

    }

    @DisplayName("Проверка авторизации с валидными электронным адресом и паролем")
    @Test
    void loginTestWithValidData() {

        step("Открываем страницу", ()-> {
            contextTranslationPage.
                    openPage(data.translationPath);
        });

        step("Переходим в раздел авторизации", ()-> {
            contextTranslationPage.goToLoginSection();
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
            contextTranslationPage.checkLogin(data.username);
        });

    }

    @DisplayName("Проверка авторизации с невалидными электронным адресом и паролем")
    @Test
    void loginTestWithInvalidData() {

        step("Открываем страницу", ()-> {
            contextTranslationPage.
                    openPage(data.translationPath);
        });

        step("Переходим в раздел авторизации", ()-> {
            contextTranslationPage.goToLoginSection();
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

    @DisplayName("Проверка перевода введенного текста")
    @Test
    void checkTextTranslations() {

        step("Авторизуемся через Api", ()-> {
            authApi.authApi(data.translationPath,
                            data.accountURL,
                            data.emailValid,
                            data.passwordValid,
                            data.returnURL);
        });

        step("Открываем страницу", ()-> {
            contextTranslationPage.
                    openPage(data.translationPath);
        });

        step("Вводим текст для перевода: " + data.textForTranslation, ()-> {
            contextTranslationPage.
                    setTextToTranslate(data.textForTranslation);
        });

        step("Устанавливаем язык введенного текста, " +
                "с которого будем переводить: " + data.languageFrom, ()-> {
            contextTranslationPage.
                    chooseLanguageFromTranslate(data.languageFrom);
        });

        step("Устанавливаем язык, на который " +
                "будем переводить: " + data.languageTo, ()-> {
            contextTranslationPage.
                    chooseLanguageToTranslate(data.languageTo);
        });

        step("Нажимаем на кнопку поиска", ()-> {
            contextTranslationPage.
                    clickOnSearchButton();
        });

        step("Результаты перевода должны содержать " +
                "следующие варианты: " + data.translations.toString(), ()-> {
            contextTranslationPage.checkTranslations(data.translations);
        });

    }

    @DisplayName("Проверка отображение контекстных примеров с введенным текстом и его переводов")
    @Test
    void checkExampleContent() {

        step("Авторизуемся через Api", ()-> {
            authApi.authApi(data.translationPath,
                    data.accountURL,
                    data.emailValid,
                    data.passwordValid,
                    data.returnURL);
        });

        step("Открываем страницу", ()-> {
            contextTranslationPage.
                    openPage(data.translationPath);
        });

        step("Вводим текст для перевода: " + data.textForTranslation, ()-> {
            contextTranslationPage.
                    setTextToTranslate(data.textForTranslation);
        });

        step("Устанавливаем язык введенного текста, " +
                "с которого будем переводить: " + data.languageFrom, ()-> {
            contextTranslationPage.
                    chooseLanguageFromTranslate(data.languageFrom);
        });

        step("Устанавливаем язык, на который " +
                "будем переводить: " + data.languageTo, ()-> {
            contextTranslationPage.
                    chooseLanguageToTranslate(data.languageTo);
        });

        step("Нажимаем на кнопку поиска", ()-> {
            contextTranslationPage.
                    clickOnSearchButton();
        });

        step("Проверямем содержание в " + data.exampleNumber +
                "-м примере введенного текста '" + data.textForTranslation + "'", ()-> {
            contextTranslationPage.
                    checkExampleConsistInputText(data.exampleNumber, data.textForTranslation);
        });

        step("Проверямем содержание в " + data.exampleNumber +
                "-м примере одного из вариантов перевода '" + data.textForTranslation + "'", ()-> {
            contextTranslationPage.
                    checkExampleConsistInputText(data.exampleNumber, data.textForTranslation);
        });

    }

    @DisplayName("Проверка добавления примера в 'Избранное'")
    @Test
    void addInFavourites() throws Exception {

        step("Авторизуемся через Api", ()-> {
            authApi.authApi(data.translationPath,
                    data.accountURL,
                    data.emailValid,
                    data.passwordValid,
                    data.returnURL);
        });

        step("Открываем страницу", ()-> {
            contextTranslationPage.
                    openPage(data.translationPath);
        });

        step("Вводим текст для перевода: " + data.textForTranslation, ()-> {
            contextTranslationPage.
                    setTextToTranslate(data.textForTranslation);
        });

        step("Устанавливаем язык введенного текста, " +
                "с которого будем переводить: " + data.languageFrom, ()-> {
            contextTranslationPage.
                    chooseLanguageFromTranslate(data.languageFrom);
        });

        step("Устанавливаем язык, на который " +
                "будем переводить: " + data.languageTo, ()-> {
            contextTranslationPage.
                    chooseLanguageToTranslate(data.languageTo);
        });

        step("Нажимаем на кнопку поиска", ()-> {
            contextTranslationPage.
                    clickOnSearchButton();
        });

        step("Добавляем " + data.exampleNumber +
                "-й пример в раздел 'Избранное'", ()-> {
            contextTranslationPage.
                    addInFavourites(data.exampleNumber);
        });

        step("Переходим в раздел 'Избранное'", ()-> {
            contextTranslationPage.
                    goToSectionFavourites();
        });

        step("Проверяем, добавлен ли " + data.exampleNumber +
                "-й пример в раздел 'Избранное'", ()-> {
            favouritesPage.checkAddingExampleInFavourites
                            (data.getExampleText(data.exampleNumber),
                            data.getExampleTranslatedText(data.exampleNumber));
        });

        step("Очищаем раздел 'Избранное' после теста через Api", ()-> {
            //favouritesPage.deleteAllFromFavourites();
            favouritesApi.sendRequestToClearFavourites(authApi.
                    getRefreshToken(data.accountURL, data.emailValid,
                    data.passwordValid, data.returnURL));
        });

    };

    @DisplayName("Проверка добавления примера в раздел 'История'")
    @Test
    void addInHistory() {

        step("Отправляем запрос на перевод через Api", ()-> {
            translationApi.sendRequestOnTranslation(authApi.
                    getRefreshToken(data.accountURL, data.emailValid,
                            data.passwordValid, data.returnURL),
                    data.languageFromTo, data.textForTranslation);
        });

        step("Авторизуемся через Api", ()-> {
            authApi.authApi(data.translationPath,
                    data.accountURL,
                    data.emailValid,
                    data.passwordValid,
                    data.returnURL);
        });

        step("Открываем страницу", ()-> {
            historyPage.openPage(data.historyPath);
        });

        step("Проверяем наличие текста перевода в разделе 'История'", ()-> {
            historyPage.checkAddingExampleInHistory(data.textForTranslation);
        });

        step("Очищаем раздел 'История' после теста через Api", ()-> {
            historyApi.sendRequestToClearHistory
                    (authApi.getRefreshToken(data.accountURL, data.emailValid,
                            data.passwordValid, data.returnURL));
        });

    };



}
