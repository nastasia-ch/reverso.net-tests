package mailru.nastasiachernega.tests.data.specs;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class AuthorizationSpec {

    public static RequestSpecification openAuthPageRequestSpec = with()
            .baseUri("https://account.reverso.net/")
            .basePath("/Account/Login")
            .header("user-agent","")
            .log().method()
            .log().uri()
            .log().headers();

    public static RequestSpecification authRequestSpec = with()
            .baseUri("https://account.reverso.net/")
            .basePath("/Account/Login")
            .header("user-agent","")
            .config(RestAssured.config()
            .encoderConfig(EncoderConfig.encoderConfig()
            .encodeContentTypeAs("x-www-form-urlencoded", ContentType.URLENC)))
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .queryParam("returnUrl",
                    "https://context.reverso.net/translation/")
            .log().method()
            .log().uri()
            .log().headers();

    public static ResponseSpecification openAuthPageResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification authResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .expectStatusCode(302)
            .build();

}
