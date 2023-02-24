package mailru.nastasiachernega.tests.tests.testsAPI;

import io.qameta.allure.*;
import io.restassured.response.Response;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.TranslationApiSteps;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Тесты API")
@Feature("Перевод текста")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс", url = "https://context.reverso.net/translation/")
public class TranslationTestsApi {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationApiSteps translationApi = new TranslationApiSteps();
    TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка перевода текста")
    @Test
    void apiCheckTranslation() {
        step("Выполняем api-запрос на перевод текста '" + data.text + "' " +
                "и проверяем наличие в html-ответе вариантов его перевода '" + data.translations);

        String refreshToken = authApi.
                getRefreshToken(data.emailValid, data.passwordValid);

        List<String> translations = translationApi
                .apiTranslation(refreshToken, data.languageFromTo, data.text)
                .extract().response()
                .htmlPath().get("**.find{it.@id == 'translations-content'}.**.findAll" +
                        "{it.@class.toString().contains('translation')}.@data-term");

        assertThat(translations).containsSequence(data.translations);
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка контекстного перевода текста")
    @Test
    void apiCheckExampleContent() {
        step("Выполняем api-запрос на перевод текста '" + data.text + "' " +
                "и проверяем наличие в html-ответе примера '" + data.example + "' " +
                "и его перевода '" + data.translatedExample + "'");

        String refreshToken = authApi.
                getRefreshToken(data.emailValid, data.passwordValid);

        Response response = translationApi
                .apiTranslation(refreshToken, data.languageFromTo, data.text)
                .extract().response();

        String example = response.body().htmlPath().
                getString("**.findAll{it.@class == 'example'}["+ data.exampleNumber + "]." +
                        "**.findAll{it.@class == 'text'}[0].text()").trim();

        String translatedExample = response.body().htmlPath().
                getString("**.findAll{it.@class == 'example'}["+ data.exampleNumber + "]." +
                        "**.findAll{it.@class == 'text'}[1].text()").trim();

        assertThat(example).isEqualTo(data.example);
        assertThat(translatedExample).isEqualTo(data.translatedExample);
    }

}
