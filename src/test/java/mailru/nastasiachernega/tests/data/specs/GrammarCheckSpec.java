package mailru.nastasiachernega.tests.data.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static org.hamcrest.Matchers.notNullValue;

public class GrammarCheckSpec {

    public static RequestSpecification grammarCheckRequestSpec = with()
            .filter(new AllureRestAssured())
            .baseUri("https://orthographe.reverso.net")
            .basePath("/api/v1/Spelling/")
            .contentType("application/json; charset=utf-8")
            .header("user-agent","")
            .log().all();

    public static ResponseSpecification grammarCheckResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("corrections.group", notNullValue())
            .expectBody("corrections.shortDescription", notNullValue())
            .expectBody("corrections.longDescription", notNullValue())
            .expectBody("corrections.mistakeText", notNullValue())
            .expectBody("corrections.correctionText", notNullValue())
            .expectBody("corrections.suggestions.text", notNullValue())
            .expectBody("corrections.suggestions.definition", notNullValue())
            .build();

}
