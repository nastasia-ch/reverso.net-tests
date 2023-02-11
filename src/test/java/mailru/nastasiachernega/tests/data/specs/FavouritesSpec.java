package mailru.nastasiachernega.tests.data.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;

public class FavouritesSpec {

    public static RequestSpecification favouritesRequestSpec = with()
            .filter(new AllureRestAssured())
            .baseUri("https://context.reverso.net")
            .basePath("/bst-web-user/user/favourites")
            .contentType("application/json; charset=utf-8")
            .header("user-agent","")
            .log().all();

    public static ResponseSpecification favouritesCheckResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();

}
