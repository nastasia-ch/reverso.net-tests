package mailru.nastasiachernega.tests.tests.testsUI;

import mailru.nastasiachernega.tests.config.ProjectProvider;
import mailru.nastasiachernega.tests.data.pages.ContextTranslationPage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.components.AuthorizationApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class TranslationTests extends ProjectProvider {

    AuthorizationApi authApi = new AuthorizationApi();
    ContextTranslationPage contextTranslationPage = new ContextTranslationPage();
    TestData data = new TestData();

    @DisplayName("Проверка перевода словосочетания в разделе контекстного перевода")
    @Test
    void checkTextTranslations() {

        step("Авторизуемся через Api", ()-> {
            authApi.authApi(data.translationPath,
                            data.accountURL,
                            data.email,
                            data.password,
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
                "следующие варианты: " + data.translations, ()-> {
            contextTranslationPage.checkTranslations(data.translations);
        });

    }

    @DisplayName("Проверка наличия в примерах введенного текста и его перевода")
    @Test
    void checkExampleContent() {

        step("Авторизуемся через Api", ()-> {
            authApi.authApi(data.translationPath,
                    data.accountURL,
                    data.email,
                    data.password,
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


}
