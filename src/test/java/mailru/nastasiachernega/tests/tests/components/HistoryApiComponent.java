package mailru.nastasiachernega.tests.tests.components;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.specs.TranslationSpec.contextTranslationRequestSpec;
import static mailru.nastasiachernega.tests.specs.TranslationSpec.contextTranslationResponseSpec;

public class HistoryApiComponent {

    public HistoryApiComponent sendRequestToClearHistory(String refreshToken) {
        given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .header("user-agent","")
                .when()
                .delete("https://context.reverso.net/bst-web-user/user/history/clear")
                .then()
                .log().status();
        return this;
    }

}
