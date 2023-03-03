package mailru.nastasiachernega.tests.tests.testsMobile;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.pagesMobile.ContextTranslatePage;
import mailru.nastasiachernega.tests.data.pagesMobile.TranslatePage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.TestBaseMobile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

import java.util.Arrays;

@Epic("Reverso Context")
@Feature("Тесты MOBILE")
@Story("Перевод текста")
@Owner("Anastasia Chernega")
public class TranslateTests extends TestBaseMobile {

    TestData data = new TestData();
    TranslatePage translatePage = new TranslatePage();
    ContextTranslatePage contextTranslatePage = new ContextTranslatePage();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Перевод текста")
    @Test
    @Tag("mobile_tests")
    @Tag("translate_tests")
    void translateTest() {

        step("Тестовые шаги", () -> {
            step("Нажимаем 'Reverso Translation'", () -> {
                translatePage
                        .clickReversoTranslation();
            });

            step("Устанавиливаем язык оригинала: " + data.languageFrom, () -> {
                translatePage
                        .clickButtonLanguageFrom()
                        .chooseLanguageFrom(data.languageFrom);
            });

            step("Устанавиливаем язык перевода: " + data.languageTo, () -> {
                translatePage
                        .clickButtonLanguageTo()
                        .chooseLanguageTo(data.languageTo);
            });

            step("В поле поиска вводим текст: '" + data.text + "'", () -> {
                translatePage
                        .setTextForTranslation(data.text);
            });

            step("Запускаем перевод", () -> {
                translatePage
                        .clickTranslateButton();
            });

            step("Проверяем перевод текста: " + Arrays.asList(data.translations).get(0) + "'", () -> {
                translatePage.checkTranslation(data.translations);
            });
        });
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Контекстный перевод текста")
    @Test
    @Tag("mobile_tests")
    @Tag("translate_tests")
    void contextTranslateTest() {

        step("Тестовые шаги", () -> {
            step("Нажимаем 'New Search'", () -> {
                contextTranslatePage
                        .clickNewSearch();
            });

            step("В поле поиска вводим текст: '" + data.text + "'", () -> {
                contextTranslatePage
                        .setTextForTranslation(data.text);
            });

            step("Устанавиливаем язык оригинала: " + data.languageFrom, () -> {
                contextTranslatePage
                        .clickButtonLanguageFrom()
                        .chooseLanguageFrom(data.languageFrom);
            });

            step("Устанавиливаем язык перевода: " + data.languageTo, () -> {
                contextTranslatePage
                        .clickButtonLanguageTo()
                        .chooseLanguageTo(data.languageTo);
            });

            step("Проверяем в результатах наличие примера с текстом: '" +
                    data.exampleMobile + "'", () -> {
                contextTranslatePage
                        .checkExampleText(data.exampleNumberMobile, data.exampleMobile);
            });

            step("Проверяем в результатах наличие перевода примера с текстом: '" +
                    data.translatedExampleMobile + "'", () -> {
                contextTranslatePage.checkTranslatedExampleText(data.exampleNumberMobile,
                                                                data.translatedExampleMobile);
            });
        });
    }
}
