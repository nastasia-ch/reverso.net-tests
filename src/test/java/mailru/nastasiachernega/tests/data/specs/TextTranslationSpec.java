package mailru.nastasiachernega.tests.data.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static org.hamcrest.Matchers.notNullValue;

public class TextTranslationSpec {

    public static RequestSpecification translateRequestSpec = with()
            .filter(new AllureRestAssured())
            .baseUri("https://api.reverso.net")
            .basePath("/translate/v1/translation")
            .contentType("application/json; charset=utf-8")
            .header("user-agent","")
            .log().all();

    public static ResponseSpecification translateResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("from", notNullValue())
            .expectBody("to", notNullValue())
            .expectBody("input", notNullValue())
            .expectBody("contextResults", notNullValue())
            .build();

}
