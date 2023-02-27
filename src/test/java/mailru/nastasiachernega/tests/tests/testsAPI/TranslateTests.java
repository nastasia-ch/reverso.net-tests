package mailru.nastasiachernega.tests.tests.testsAPI;

import io.qameta.allure.*;
import io.restassured.response.Response;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.TranslationApiSteps;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Тесты API")
@Feature("Перевод текста")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс",
        url = "https://context.reverso.net/translation/")
public class TranslateTests {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationApiSteps translationApi = new TranslationApiSteps();
    TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Перевод текста")
    @Test
    @Tag("API_tests")
    @Tag("translate_tests")
    void apiCheckTextTranslations() {

        String refreshToken = step("Выполняем api-запрос на получение " +
                "авторизационной куки", () ->
                authApi
                        .getRefreshToken(data.emailValid, data.passwordValid));

        List<String> translations = step("Выполняем api-запрос на перевод текста " +
                "и извлекаем из html-ответа варианты его перевода", () ->
                translationApi
                        .translateText(refreshToken, data.languageFromTo, data.text)
                        .extract().response()
                        .htmlPath().get("**.find{it.@id == 'translations-content'}.**.findAll" +
                                "{it.@class.toString().contains('translation')}.@data-term"));

        step("Проверяем наличие в html-ответе вариантов перевода", () -> {
            assertThat(translations).containsSequence(data.translations);
        });
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Контекстный перевод текста")
    @Test
    @Tag("API_tests")
    @Tag("translate_tests")
    void apiCheckExampleContent() {

        String refreshToken = step("Выполняем api-запрос на получение " +
                "авторизационной куки", () ->
                authApi
                        .getRefreshToken(data.emailValid, data.passwordValid));

        Response response = step("Выполняем api-запрос на перевод текста", () ->
                translationApi
                        .translateText(refreshToken, data.languageFromTo, data.text)
                        .extract().response());

        String example = step("Извлекаем из html-ответа текст примера", () ->
                response.body()
                        .htmlPath()
                        .getString("**.findAll{it.@class == 'example'}" +
                                "[" + data.exampleNumber + "].**" +
                                ".findAll{it.@class == 'text'}[0].text()").trim());

        String translatedExample = step("Извлекаем из html-ответа перевод примера", () ->
                response.body()
                        .htmlPath()
                        .getString("**.findAll{it.@class == 'example'}" +
                                "[" + data.exampleNumber + "].**" +
                                ".findAll{it.@class == 'text'}[1].text()").trim());

        step("Проверяем текст примера", () -> {
            assertThat(example).isEqualTo(data.example);
        });

        step("Проверяем перевод примера", () -> {
            assertThat(translatedExample).isEqualTo(data.translatedExample);
        });
    }
}
