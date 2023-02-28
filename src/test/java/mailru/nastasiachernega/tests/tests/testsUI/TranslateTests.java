package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.pages.TranslatePage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.TestBaseWeb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Epic("Тесты UI")
@Feature("Перевод текста")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс 'Reverso Context'",
        url = "https://context.reverso.net/translation/")
public class TranslateTests extends TestBaseWeb {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslatePage translationPage = new TranslatePage();
    TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Перевод текста")
    @Test
    @Tag("UI_tests")
    @Tag("translate_tests")
    void checkTextTranslations() {

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

        step("Проверяем в результатах список " +
                "вариантов перевода", () -> {
            translationPage
                    .checkTranslations(data.translations);
        });
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Контекстный перевод текста")
    @Test
    @Tag("UI_tests")
    @Tag("translate_tests")
    void checkExampleContent() {

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

        step("Проверямем в результатах текст в " +
                data.exampleNumber + "-м примере", () -> {
            translationPage
                    .checkExampleText(data.exampleNumber, data.example);
        });

        step("Проверямем в результатах перевод в " + data.exampleNumber +
                "-м примере", () -> {
            translationPage
                    .checkTranslatedExampleText(data.exampleNumber, data.translatedExample);
        });
    }

}
