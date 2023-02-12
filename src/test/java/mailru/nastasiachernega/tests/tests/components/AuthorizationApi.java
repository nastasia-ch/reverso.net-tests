package mailru.nastasiachernega.tests.tests.components;

import com.codeborne.selenide.WebDriverRunner;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class AuthorizationApi {

    public String getRefreshToken(String accountURL,
                                  String email,
                                  String password,
                                  String returnURL) {

        String accountAntiforgery = given()
                .log().all()
                .when()
                .get(accountURL)
                .then()
                .extract().cookie("Reverso.Account.Antiforgery");

        String htmlResponse = given()
                .cookie("Reverso.Account.Antiforgery",accountAntiforgery)
                .log().all()
                .when()
                .get(accountURL)
                .then()
                .extract().response().asString();

        Document html = Jsoup.parse(htmlResponse);
        String requestVerificationToken = html.body().getElementsByAttributeValue("name","__RequestVerificationToken").
                attr("value");

        return given()
                .config(RestAssured.config()
                .encoderConfig(EncoderConfig.encoderConfig()
                .encodeContentTypeAs("x-www-form-urlencoded", ContentType.URLENC)))
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .queryParam("returnUrl",
                        returnURL)
                .formParam("Email",email)
                .formParam("Password",password)
                .formParam("__RequestVerificationToken",requestVerificationToken)
                .formParam("RememberMe","false")
                .cookie("Reverso.Account.Antiforgery",accountAntiforgery)
                .cookie("Reverso.Account.TempDataCookie","")
                .log().all()
                .when()
                .post(accountURL)
                .then()
                .log().all()
                .statusCode(302)
                .extract().cookie("reverso.net.ReversoRefreshToken");

    };

    public void authApi(String path,
                          String accountURL,
                          String email,
                          String password,
                          String returnURL) {

        open(path);
        Cookie cookie = new Cookie("reverso.net.ReversoRefreshToken",
                getRefreshToken(accountURL, email, password, returnURL));

        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
    };

}
