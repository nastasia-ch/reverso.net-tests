package mailru.nastasiachernega.tests.data.apiSteps;

import static io.restassured.RestAssured.given;

public class HistoryApiSteps {

    public HistoryApiSteps apiClearHistory(String refreshToken) {
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
