package mailru.nastasiachernega.tests.tests.components;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.specs.TranslationSpec.contextTranslationRequestSpec;
import static mailru.nastasiachernega.tests.specs.TranslationSpec.contextTranslationResponseSpec;

public class TranslationApiComponent {

    public TranslationApiComponent sendRequestOnTranslation(String refreshToken,
                                                            String languageFromTo,
                                                            String textForTranslation) {
        given()
                .spec(contextTranslationRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .pathParam("languagesFromTo", languageFromTo)
                .pathParam("text", textForTranslation)
                .when()
                .get("/{languagesFromTo}/{text}")
                .then()
                .spec(contextTranslationResponseSpec);
        return this;
    }

}
