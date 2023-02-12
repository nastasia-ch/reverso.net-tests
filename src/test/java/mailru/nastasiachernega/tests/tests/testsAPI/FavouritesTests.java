package mailru.nastasiachernega.tests.tests.testsAPI;

import mailru.nastasiachernega.tests.data.models.AddInFavouritesRequestModel;
import mailru.nastasiachernega.tests.data.models.AddInFavouritesResponseModel;
import mailru.nastasiachernega.tests.data.models.CommentRequestModel;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.components.AuthorizationApi;
import mailru.nastasiachernega.tests.utils.GetContextExampleUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.FavouritesSpec.favouritesCheckResponseSpec;
import static mailru.nastasiachernega.tests.data.specs.FavouritesSpec.favouritesRequestSpec;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class FavouritesTests {

    TestData date = new TestData();
    AuthorizationApi auth = new AuthorizationApi();
    GetContextExampleUtils utils = new GetContextExampleUtils();

    @DisplayName("Добавление примера в Favourites")
    @Test
    void addInFavourites() {

        step("Добавляем пример в Favourites");
        AddInFavouritesRequestModel requestBody = new AddInFavouritesRequestModel();
        requestBody.setSrcContext(date.srcContext);
        requestBody.setSrcLang(date.srcLang);
        requestBody.setSrcText(date.srcText);
        requestBody.setTrgContext(date.trgContext);
        requestBody.setTrgLang(date.trgLang);
        requestBody.setTrgText(date.trgText);

        AddInFavouritesResponseModel response = given()
                .spec(favouritesRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .body(requestBody)
                .when()
                .post("")
                .then()
                .statusCode(200)
                .spec(favouritesCheckResponseSpec)
                .extract().as(AddInFavouritesResponseModel.class);

        step("Проверяем соответствие данных реквеста данным респонса (userID, текста примера и его перевода)");
        assertThat(response.getUserID()).isEqualTo(utils.getUserID(date.accountURL,date.email,date.password,date.returnURL));
        assertThat(response.getSrcContext()).isEqualTo(date.srcContext);
        assertThat(response.getTrgContext()).isEqualTo(date.trgContext);

    }

    @DisplayName("Добавление комментария к примеру, сохраненному в Favourites")
    @Test
    void addComment() {

        step("Добавляем пример в Favourites и получаем id примера");
        AddInFavouritesRequestModel favouriteRequestBody = new AddInFavouritesRequestModel();
        favouriteRequestBody.setSrcContext(date.srcContext);
        favouriteRequestBody.setSrcLang(date.srcLang);
        favouriteRequestBody.setSrcText(date.srcText);
        favouriteRequestBody.setTrgContext(date.trgContext);
        favouriteRequestBody.setTrgLang(date.trgLang);
        favouriteRequestBody.setTrgText(date.trgText);

        int exampleId = given()
                .spec(favouritesRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .body(favouriteRequestBody)
                .when()
                .post("")
                .then()
                .statusCode(200)
                .spec(favouritesCheckResponseSpec)
                .extract().path("id");

        step("Добавляем в пример с id " + exampleId + " комментарий: " + date.commentText);
        CommentRequestModel commentRequestBody = new CommentRequestModel();
        commentRequestBody.setComment(date.commentText);

        AddInFavouritesResponseModel response = given()
                .spec(favouritesRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .pathParam("exampleId", exampleId)
                .body(commentRequestBody)
                .when()
                .put("/{exampleId}")
                .then()
                .statusCode(200)
                .spec(favouritesCheckResponseSpec)
                .extract().as(AddInFavouritesResponseModel.class);

        step("Проверяем в респонсе user id и добавленный комментарий");
        assertThat(response.getUserID()).isEqualTo(utils.getUserID(date.accountURL,date.email,date.password,date.returnURL));
        assertThat(response.getComment()).isEqualTo(date.commentText);

    }

    @DisplayName("Удаление примера из Favourites")
    @Test
    void deleteFromFavourites() {

        step("Добавляем пример в Favourites и получаем id примера");
        AddInFavouritesRequestModel requestBody = new AddInFavouritesRequestModel();
        requestBody.setSrcContext(date.srcContext);
        requestBody.setSrcLang(date.srcLang);
        requestBody.setSrcText(date.srcText);
        requestBody.setTrgContext(date.trgContext);
        requestBody.setTrgLang(date.trgLang);
        requestBody.setTrgText(date.trgText);

        int exampleId = given()
                .spec(favouritesRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .body(requestBody)
                .when()
                .post("")
                .then()
                .statusCode(200)
                .spec(favouritesCheckResponseSpec)
                .extract().path("id");

        step("Получаем количество сохраненных примеров в Favourites");
        int numOfFavourites = given()
                .spec(favouritesRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .queryParam("length",25)
                .when()
                .get("")
                .then()
                .spec(favouritesCheckResponseSpec)
                .extract().path("numTotalResults");

        step("Удаляем пример из Favourites");
        given()
                .spec(favouritesRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .queryParam("ids",exampleId)
                .when()
                .delete("")
                .then()
                .log().all()
                .statusCode(200)
                .spec(favouritesCheckResponseSpec)
                .body("numUpdatedResults", is(1));

        step("Проверяем количество сохраненных примеров в Favourites: должно уменьшиться на 1");
        given()
                .spec(favouritesRequestSpec)
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .queryParam("length",25)
                .when()
                .get("")
                .then()
                .spec(favouritesCheckResponseSpec)
                .body("numTotalResults",is(numOfFavourites-1));

    }


}
