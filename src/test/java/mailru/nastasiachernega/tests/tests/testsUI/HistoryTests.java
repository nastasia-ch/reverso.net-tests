package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.pages.HistoryPage;
import mailru.nastasiachernega.tests.data.pages.TranslatePage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.HistoryApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.TranslationApiSteps;
import mailru.nastasiachernega.tests.tests.TestBaseWeb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Epic("Тесты UI")
@Feature("История")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс 'Reverso Context'",
        url = "https://context.reverso.net/history")
public class HistoryTests extends TestBaseWeb {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationApiSteps translationApi = new TranslationApiSteps();
    HistoryApiSteps historyApi = new HistoryApiSteps();

    TranslatePage translationPage = new TranslatePage();
    HistoryPage historyPage = new HistoryPage();

    TestData data = new TestData();

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Добавление в раздел 'История'")
    @Test
    @Tag("UI_tests")
    @Tag("history_tests")
    void addInHistory() {

        step("Отправляем запрос на перевод через Api", () -> {
            translationApi
                    .translateText(authApi.getRefreshToken(data.emailValid, data.passwordValid),
                            data.languageFromTo, data.text);
        });

        step("Авторизуемся через Api", () -> {
            translationPage
                    .addAuthCookieToWebDriver(data.translationPath,
                            authApi.getRefreshToken(data.emailValid, data.passwordValid));
        });

        step("Открываем страницу раздела 'История'", () -> {
            historyPage
                    .openPage(data.historyPath);
        });

        step("Проверяем в отображаемых результатах наличие " +
                "текста перевода: '" + data.text + "'", () -> {
            historyPage
                    .checkAddingTextInHistory(data.text);
        });

        step("Очищаем раздел 'История' после теста через Api: " +
                "удаляем сохраненный запрос", () -> {
            historyApi
                    .deleteFromHistory(authApi.getRefreshToken(data.emailValid, data.passwordValid),
                            historyPage.getHistoryId(data.text));
        });
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка фильтрации в разделе 'История'")
    @Test
    @Tag("UI_tests")
    @Tag("history_tests")
    void checkFilterInHistory() {

        step("Отправляем запрос на перевод через Api", () -> {
            translationApi
                    .translateText(authApi.getRefreshToken(data.emailValid, data.passwordValid),
                            data.languageFromTo, data.text);
        });

        step("Авторизуемся через Api", () -> {
            translationPage
                    .addAuthCookieToWebDriver(data.translationPath,
                            authApi.getRefreshToken(data.emailValid, data.passwordValid));
        });

        step("Открываем страницу раздела 'История'", () -> {
            historyPage
                    .openPage(data.historyPath);
        });

        step("В поле поиска вводим текст поиска: '" + data.text + "'", () -> {
            historyPage
                    .setFilteredText(data.text);
        });

        step("Выбираем язык оригинала: " + data.languageFrom, () -> {
            historyPage
                    .chooseLanguageFrom(data.langFromSymbol);
        });

        step("Выбираем язык перевода: " + data.languageTo, () -> {
            historyPage
                    .chooseLanguageTo(data.langToSymbol);
        });

        step("Нажимаем кнопку применения фильтра", () -> {
            historyPage
                    .clickFilterButton();
        });

        step("Проверяем в первом результате отображенный текст: " +
                "должен быть текст '" + data.text + "'", () -> {
            historyPage
                    .checkAddingTextInHistory(data.text);
        });

        step("Проверяем в первом результате дату сохранения в историю: " +
                "должна быть текущая дата " + data.currentDate, () -> {
            historyPage
                    .checkAddingDateInHistory(data.currentDate);
        });

        step("Очищаем раздел 'История' после теста через Api: " +
                "удаляем сохраненный запрос", () -> {
            historyApi
                    .deleteFromHistory
                            (authApi.getRefreshToken(data.emailValid, data.passwordValid),
                                    historyPage.getHistoryId(data.text));
        });
    }
}
