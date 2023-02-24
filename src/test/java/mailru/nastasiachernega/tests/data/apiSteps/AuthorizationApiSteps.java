package mailru.nastasiachernega.tests.data.apiSteps;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.AuthorizationSpec.*;
import static mailru.nastasiachernega.tests.helpers.CustomApiListener.withCustomTemplates;

public class AuthorizationApiSteps {

    public String getRefreshToken(String email,
                                  String password) {

        String accountAntiforgery = given()
                .spec(openAuthPageRequestSpec)
                .when()
                .get()
                .then()
                .spec(openAuthPageResponseSpec)
                .extract().cookie("Reverso.Account.Antiforgery");

        String htmlResponse = given()
                .spec(openAuthPageRequestSpec)
                .cookie("Reverso.Account.Antiforgery",accountAntiforgery)
                .when()
                .get()
                .then()
                .spec(openAuthPageResponseSpec)
                .extract().response().asString();

        Document html = Jsoup.parse(htmlResponse);
        String requestVerificationToken = html.body().
                getElementsByAttributeValue("name","__RequestVerificationToken").
                attr("value");

        return given()
                .spec(authRequestSpec)
                .formParam("Email",email)
                .formParam("Password",password)
                .formParam("__RequestVerificationToken",requestVerificationToken)
                .formParam("RememberMe","false")
                .cookie("Reverso.Account.Antiforgery",accountAntiforgery)
                .cookie("Reverso.Account.TempDataCookie","")
                .when()
                .post()
                .then()
                .spec(authResponseSpec)
                .extract().cookie("reverso.net.ReversoRefreshToken");
    };

}
