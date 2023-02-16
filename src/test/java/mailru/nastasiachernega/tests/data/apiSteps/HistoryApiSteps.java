package mailru.nastasiachernega.tests.data.apiSteps;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.FavouritesAndHistorySpec.favouritesAndHistoryResponseSpec;
import static mailru.nastasiachernega.tests.data.specs.FavouritesAndHistorySpec.favouritesAndHistoryRequestSpec;
import static mailru.nastasiachernega.tests.helpers.CustomApiListener.withCustomTemplates;

public class HistoryApiSteps {

    public ValidatableResponse apiDeleteFromHistory (String refreshToken,
                                                     String historyID) {
        return given()
                .filter(withCustomTemplates())
                .spec(favouritesAndHistoryRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .queryParam("ids", historyID)
                .when()
                .delete("/history")
                .then()
                .spec(favouritesAndHistoryResponseSpec);
    }

}
