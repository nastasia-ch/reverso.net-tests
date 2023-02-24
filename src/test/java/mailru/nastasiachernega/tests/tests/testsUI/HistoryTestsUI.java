package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.config.WebDriverProvider;
import mailru.nastasiachernega.tests.data.pages.HistoryPage;
import mailru.nastasiachernega.tests.data.pages.TranslationPage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.HistoryApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.TranslationApiSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Epic("Тесты UI")
@Feature("История")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс", url = "https://context.reverso.net/history")
public class HistoryTestsUI extends WebDriverProvider {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationApiSteps translationApi = new TranslationApiSteps();
    HistoryApiSteps historyApi = new HistoryApiSteps();

    TranslationPage translationPage = new TranslationPage();
    HistoryPage historyPage = new HistoryPage();

    TestData data = new TestData();

    @Severity(SeverityLevel.CRITICAL)
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

        step("Проверяем в разделе 'История' наличие текста перевода: '" + data.text + "'", ()-> {
            historyPage.checkAddingTextInHistory(data.text);
        });

        step("Очищаем раздел 'История' после теста через Api: удаляем сохраненный запрос", ()-> {
            historyApi.
                    apiDeleteFromHistory(authApi.getRefreshToken(data.emailValid, data.passwordValid),
                                    historyPage.getHistoryId(data.text));
        });
    };

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка фильтрации в разделе 'История'")
    @Test
    void checkFilterInHistory() {

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

        step("В поле поиска вводим текст поиска", ()-> {
            historyPage.setFilteredText(data.text);
        });

        step("Выбираем язык оригинала", ()-> {
            historyPage.chooseSourceLanguage(data.languageFrom);
        });

        step("Выбираем язык перевода", ()-> {
            historyPage.chooseTargetLanguage(data.languageTo);
        });

        step("Нажимаем кнопку применения фильтра", ()-> {
            historyPage.clickFilterButton();
        });

        step("Проверяем в первой строке текст перевода:" +
                " '" + data.text + "'", ()-> {
            historyPage.checkAddingTextInHistory(data.text);
        });

        step("Проверяем в первой строке дату сохранения в историю " +
                "(текущая дата): " + data.currentDate, ()-> {
            historyPage.checkDateOfAddingInHistory(data.currentDate);
        });

        step("Очищаем раздел 'История' после теста через Api", ()-> {
            historyApi.
                    apiDeleteFromHistory(authApi.getRefreshToken(data.emailValid, data.passwordValid),
                            historyPage.getHistoryId(data.text));
        });

    };

}
