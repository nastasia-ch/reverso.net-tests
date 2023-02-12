package mailru.nastasiachernega.tests.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.components.AuthorizationApi;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jsoup.Jsoup.parse;

public class LoginTests {

    TestData date = new TestData();
    AuthorizationApi authorizationApi = new AuthorizationApi();

    @Test
    void loginTestUI() {
        open("https://www.reverso.net/text-translation");
        Configuration.holdBrowserOpen=true;
        $("[aria-label='Login menu']").click();
        $(".app-popup-menu-view__menu").$(byText("Log in")).click();
        $("#Email").setValue("test.test.user962@gmail.com");
        $("#Password").setValue("QWEasd098");
        $("button[type=submit]").click();

        $("[aria-label='Login menu']").click();
        $(".app-popup-menu-view__menu").shouldHave(text("testtestuser962"));

    }

    @Test
    void loginTestApiUi() {

        open("https://www.reverso.net/assets/images/favicon.ico");

        Cookie cookie = new Cookie("reverso.net.ReversoRefreshToken",
               authorizationApi.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL));

        WebDriverRunner.getWebDriver().manage().addCookie(cookie);

        open("https://www.reverso.net/text-translation");
        Configuration.holdBrowserOpen=true;
        $("[aria-label='Login menu']").click();
        $(".app-popup-menu-view__menu").shouldHave(text("testtestuser962"));

    }

    @Test
    void loginTestAPI() {

        String response = given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken",
                        authorizationApi.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .header("user-agent","")
                .when()
                .get("https://context.reverso.net/translation/")
                .then()
                .log().all()
                .extract().response().asString();

        Document htmlText = parse(response);
        assertThat(htmlText.select(".username").text()).isEqualTo("testtestuser962");

        System.out.println();
    }

}
