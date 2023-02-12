package mailru.nastasiachernega.tests.tests.testsAPI;

import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.utils.GetContextTranslationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.ContextTranslationSpec.contextTranslationRequestSpec;
import static mailru.nastasiachernega.tests.data.specs.ContextTranslationSpec.contextTranslationResponseSpec;
import static org.assertj.core.api.Assertions.assertThat;

public class ContextTranslationTests {

    GetContextTranslationUtils util = new GetContextTranslationUtils();
    TestData data = new TestData();

    @DisplayName("Проверка перевода словосочетания в разделе контекстного перевода")
    @Test
    void contextTranslationTest() {

        step("Выполняем запрос на контекстный перевод словосочетания '" + data.textForTranslation + "'");
            String response = given()
                    .spec(contextTranslationRequestSpec)
                    .pathParam("languagesFromTo", data.languageFromTo)
                    .pathParam("text", data.textForTranslation)
                    .when()
                    .get("/{languagesFromTo}/{text}")
                    .then()
                    .spec(contextTranslationResponseSpec)
                    .extract().response().asString();

        step("Извлекаем из html ответа варианты перевода");
            List<String> translations = util.getTranslations(response);

        step("Проверяемем получение вариантов перевода: " + Arrays.asList(data.translations));
        assertThat(translations).containsAll(Arrays.asList(data.translations));
    }

    @DisplayName("Проверка наличия в примерах переводимого словосочетания и его перевода")
    @Test
    void contextExamplesContentTest() {

        step("Выполняем запрос на контекстный перевод словосочетания '" + data.textForTranslation + "'");
        String response = given()
                .spec(contextTranslationRequestSpec)
                .pathParam("languagesFromTo", data.languageFromTo)
                .pathParam("text", data.textForTranslation)
                .when()
                .get("/{languagesFromTo}/{text}")
                .then()
                .spec(contextTranslationResponseSpec)
                .extract().response().asString();

        step("Извлекаем из html ответа " + data.exampleNumber+1 + "й пример перевода");
        String contextText = util.getExampleContextText(response,data.exampleNumber);
        String translatedText = util.getExampleTranslatedContextText(response,data.exampleNumber);

        step("Проверямем содержание в примере словосочетания '" + data.textForTranslation + "'");
        assertThat(contextText).contains(data.textForTranslation);

        step("Проверямем содержание в одного из вариантов перевода: " + Arrays.asList(data.translations));
        assertThat(translatedText).containsAnyOf(data.translations);
    }

}
