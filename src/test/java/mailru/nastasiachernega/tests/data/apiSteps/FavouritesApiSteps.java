package mailru.nastasiachernega.tests.data.apiSteps;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import mailru.nastasiachernega.tests.data.models.FavouritesRequestModel;
import mailru.nastasiachernega.tests.data.models.CommentRequestModel;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.FavouritesAndHistorySpec.favouritesAndHistoryResponseSpec;
import static mailru.nastasiachernega.tests.data.specs.FavouritesAndHistorySpec.favouritesAndHistoryRequestSpec;
import static mailru.nastasiachernega.tests.helpers.CustomApiListener.withCustomTemplates;

public class FavouritesApiSteps {


    public ValidatableResponse apiAddInFavourites(String refreshToken,
                                       String exampleText,
                                       String langFromSymbol,
                                       String textForTranslation,
                                       String exampleTranslation,
                                       String langToSymbol,
                                       String translatedText) {

        FavouritesRequestModel requestBody = new FavouritesRequestModel();
        requestBody.setSrcContext(exampleText);
        requestBody.setSrcLang(langFromSymbol);
        requestBody.setSrcText(textForTranslation);
        requestBody.setTrgContext(exampleTranslation);
        requestBody.setTrgLang(langToSymbol);
        requestBody.setTrgText(translatedText);

        return given()
                .filter(withCustomTemplates())
                .spec(favouritesAndHistoryRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .body(requestBody)
                .when()
                .post("/favourites")
                .then()
                .spec(favouritesAndHistoryResponseSpec);
    }


    public ValidatableResponse apiWorkWithComment(String refreshToken,
                                                  int exampleId,
                                                  String commentText) {

        CommentRequestModel commentRequestBody = new CommentRequestModel();
        commentRequestBody.setComment(commentText);

        return given()
                .filter(withCustomTemplates())
                .spec(favouritesAndHistoryRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .pathParam("exampleId", exampleId)
                .body(commentRequestBody)
                .when()
                .put("/favourites/{exampleId}")
                .then()
                .spec(favouritesAndHistoryResponseSpec);
    }

    public ValidatableResponse apiGetListOfFavourites(String refreshToken) {

        return given()
                .filter(withCustomTemplates())
                .spec(favouritesAndHistoryRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .queryParam("start", 0)
                .queryParam("length",50)
                .queryParam("order", 10)
                .queryParam("includeSyn", "yes")
                .when()
                .get("/favourites")
                .then()
                .spec(favouritesAndHistoryResponseSpec);
    }

    public ValidatableResponse apiDeleteFromFavourites (String refreshToken,
                                                        int exampleId) {
        return given()
                .filter(withCustomTemplates())
                .spec(favouritesAndHistoryRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken", refreshToken)
                .queryParam("ids",exampleId)
                .when()
                .delete("/favourites")
                .then()
                .spec(favouritesAndHistoryResponseSpec);
    }

}
