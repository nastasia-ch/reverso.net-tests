package mailru.nastasiachernega.tests.tests.testsMobile;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.TestBaseMobile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.appium.java_client.AppiumBy.id;
import static io.qameta.allure.Allure.step;

import java.util.Arrays;

@Epic("Тесты MOBILE")
@Feature("Перевод текста")
@Owner("Anastasia Chernega")
public class TranslateTests extends TestBaseMobile {

    TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Перевод текста")
    @Test
    @Tag("mobile_tests")
    @Tag("translate_tests")
    void translateTest() {

        step("Нажимаем 'Reverso Translation'", () -> {
            $$(id("com.softissimo.reverso.context:id/design_menu_item_text")).
                    findBy(text("Reverso Translation")).click();
        });

        step("Устанавиливаем язык оригинала: " + data.languageFrom, () -> {
            $(id("com.softissimo.reverso.context:id/button_source_language")).click();
            $$(id("com.softissimo.reverso.context:id/tv_language"))
                    .findBy(text(data.languageFrom)).click();
        });

        step("Устанавиливаем язык перевода: " + data.languageTo, () -> {
            $(id("com.softissimo.reverso.context:id/button_target_language")).click();
            $$(id("com.softissimo.reverso.context:id/tv_language"))
                    .findBy(text(data.languageTo)).click();
        });

        step("В поле поиска вводим текст: '" + data.text + "'", () -> {
            $(id("com.softissimo.reverso.context:id/textInputTranslator"))
                    .sendKeys(data.text);
        });

        step("Запускаем поиск", () -> {
            $(id("com.softissimo.reverso.context:id/translateInputTranslator")).click();
        });

        step("Проверяем перевод текста: " + Arrays.asList(data.translations).get(0) + "'", () -> {
            $(id("com.softissimo.reverso.context:id/responseTextTranslator")).
                    shouldHave(text(Arrays.asList(data.translations).get(0)));
        });
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Контекстный перевод текста")
    @Test
    @Tag("mobile_tests")
    @Tag("translate_tests")
    void contextTranslateTest() {

        step("Нажимаем 'New Search'", () -> {
            $$(id("com.softissimo.reverso.context:id/design_menu_item_text"))
                    .findBy(text("New Search")).click();
        });

        step("В поле поиска вводим текст: '" + data.text + "'", () -> {
            $(id("com.softissimo.reverso.context:id/edit_search")).click();
            $(id("com.softissimo.reverso.context:id/edit_search")).
                    sendKeys(data.text);
        });

        step("Устанавиливаем язык оригинала: " + data.languageFrom, () -> {
            $(id("com.softissimo.reverso.context:id/button_source_language")).click();
            $$(id("com.softissimo.reverso.context:id/tv_language"))
                    .findBy(text(data.languageFrom)).click();
        });

        step("Устанавиливаем язык перевода: " + data.languageTo, () -> {
            $(id("com.softissimo.reverso.context:id/button_target_language")).click();
            $$(id("com.softissimo.reverso.context:id/tv_language"))
                    .findBy(text(data.languageTo)).click();
        });

        step("Проверяем в результатах наличие примера с текстом: '" +
                data.exampleMobile + "'", () -> {
            $$(id("com.softissimo.reverso.context:id/text_source"))
                    .get(data.exampleNumberMobile)
                    .shouldHave(text(data.exampleMobile));
        });

        step("Проверяем в результатах наличие перевода примера с текстом: '" +
                data.translatedExampleMobile + "'", () -> {
            $$(id("com.softissimo.reverso.context:id/text_target"))
                    .get(data.exampleNumberMobile)
                    .shouldHave(text(data.translatedExampleMobile));
        });
    }

}
