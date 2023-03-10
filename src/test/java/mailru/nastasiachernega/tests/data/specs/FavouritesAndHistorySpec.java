package mailru.nastasiachernega.tests.data.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static mailru.nastasiachernega.tests.helpers.CustomApiListener.withCustomTemplates;

public class FavouritesAndHistorySpec {

    public static RequestSpecification favouritesAndHistoryRequestSpec = with()
            .baseUri("https://context.reverso.net")
            .basePath("/bst-web-user/user")
            .contentType("application/json; charset=utf-8")
            .header("user-agent","")
            .log().method()
            .log().uri()
            .log().body();

    public static ResponseSpecification favouritesAndHistoryResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();
}
