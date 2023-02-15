package mailru.nastasiachernega.tests.data.apiSteps;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import org.jsoup.nodes.Document;
import static org.jsoup.Jsoup.parse;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.TranslationSpec.contextTranslationRequestSpec;
import static mailru.nastasiachernega.tests.data.specs.TranslationSpec.contextTranslationResponseSpec;

public class TranslationApiSteps {

    public ValidatableResponse apiTranslation(String refreshToken,
                                              String languageFromTo,
                                              String textForTranslation) {
        return given()
                .spec(contextTranslationRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .pathParam("languagesFromTo", languageFromTo)
                .pathParam("text", textForTranslation)
                .when()
                .get("/{languagesFromTo}/{text}")
                .then()
                .spec(contextTranslationResponseSpec);
    }

}
