package mailru.nastasiachernega.tests.tests.components;

import static io.restassured.RestAssured.given;

public class FavouritesApiComponent {

    public FavouritesApiComponent sendRequestToClearFavourites(String refreshToken) {
        given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .header("user-agent","")
                .when()
                .delete("https://context.reverso.net/bst-web-user/user/favourites/clear")
                .then()
                .log().status();
        return this;
    }

}
