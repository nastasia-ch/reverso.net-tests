package mailru.nastasiachernega.tests.tests.testsAPI;

import mailru.nastasiachernega.tests.data.models.TextTranslationRequestModel;
import mailru.nastasiachernega.tests.data.models.TextTranslationResponseModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.TextTranslationSpec.translateRequestSpec;
import static mailru.nastasiachernega.tests.data.specs.TextTranslationSpec.translateResponseSpec;
import static org.assertj.core.api.Assertions.assertThat;

public class TextTranslationTests {

    @CsvFileSource (resources = "/test_data_translation.csv")
    @ParameterizedTest
    void translateTextTest(String langFrom,
                           String textToTranslate,
                           String langTo,
                           String translatedText) {

        TextTranslationRequestModel requestBody =
                TextTranslationRequestModel.builder()
                .from(langFrom)
                .input(textToTranslate)
                .to(langTo)
                .build();

        TextTranslationResponseModel response = given()
                .spec(translateRequestSpec)
                .body(requestBody)
                .when()
                .post()
                .then()
                .spec(translateResponseSpec)
                .extract().as(TextTranslationResponseModel.class);

        assertThat(response.getTranslation()).
                contains(translatedText);

    }

}
