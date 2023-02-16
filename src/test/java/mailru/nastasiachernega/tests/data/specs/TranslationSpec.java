package mailru.nastasiachernega.tests.data.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.STATUS;
import static mailru.nastasiachernega.tests.helpers.CustomApiListener.withCustomTemplates;

public class TranslationSpec {

    public static RequestSpecification contextTranslationRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://context.reverso.net")
            .basePath("/translation")
            .header("user-agent","")
            .log().all();

    public static ResponseSpecification contextTranslationResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .expectStatusCode(200)
            .build();

}
