package mailru.nastasiachernega.tests.data.apiSteps;

import io.restassured.response.ValidatableResponse;
import static mailru.nastasiachernega.tests.helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.TranslationSpec.contextTranslationRequestSpec;
import static mailru.nastasiachernega.tests.data.specs.TranslationSpec.contextTranslationResponseSpec;

public class TranslationApiSteps {

    public ValidatableResponse translateText(String refreshToken,
                                             String languageFromTo,
                                             String textForTranslation) {
        return given()
                .filter(withCustomTemplates())
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
