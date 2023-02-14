package mailru.nastasiachernega.tests.data.apiSteps;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import mailru.nastasiachernega.tests.data.models.AddInFavouritesRequestModel;
import mailru.nastasiachernega.tests.data.models.AddInFavouritesResponseModel;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.FavouritesSpec.favouritesCheckResponseSpec;
import static mailru.nastasiachernega.tests.data.specs.FavouritesSpec.favouritesRequestSpec;

public class FavouritesApiSteps {


    public Response apiAddInFavourites(String refreshToken,
                                       String exampleText,
                                       String langFromSymbol,
                                       String textForTranslation,
                                       String exampleTranslation,
                                       String langToSymbol,
                                       String translatedText) {
        AddInFavouritesRequestModel requestBody = new AddInFavouritesRequestModel();
        requestBody.setSrcContext(exampleText);
        requestBody.setSrcLang(langFromSymbol);
        requestBody.setSrcText(textForTranslation);
        requestBody.setTrgContext(exampleTranslation);
        requestBody.setTrgLang(langToSymbol);
        requestBody.setTrgText(translatedText);

        return given()
                .spec(favouritesRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .body(requestBody)
                .when()
                .post("")
                .then()
                .statusCode(200)
                .spec(favouritesCheckResponseSpec)
                .extract().response();
    }

    public Response apiClearFavourites(String refreshToken) {
        return given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .header("user-agent","")
                .when()
                .delete("https://context.reverso.net/bst-web-user/user/favourites/clear")
                .then()
                .extract().response();
    }

}
