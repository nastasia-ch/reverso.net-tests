package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.config.WebDriverProvider;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.pages.TranslationPage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Epic("Тесты UI")
@Feature("Перевод текста")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс", url = "https://context.reverso.net/translation/")
public class TranslationTestsUI extends WebDriverProvider {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationPage translationPage = new TranslationPage();
    TestData data = new TestData();

    @DisplayName("Проверка перевода текста")
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

        step("Проверяем содержание в результатах перевода " +
                "вариантов перевода", ()-> {
            translationPage.checkTranslations(data.translations);
        });
    }

    @DisplayName("Проверка отображения примеров")
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

        step("Устанавливаем язык оригинала, " + data.languageFrom, ()-> {
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
                "одного из вариантов перевода", ()-> {
            translationPage.
                    checkExampleConsistTranslatedText(data.exampleNumber, data.translations);
        });
    }

}
