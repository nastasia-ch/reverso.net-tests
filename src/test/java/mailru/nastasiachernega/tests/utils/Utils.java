package mailru.nastasiachernega.tests.utils;

import mailru.nastasiachernega.tests.tests.AuthorizationApi;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.jsoup.Jsoup.parse;

public class Utils {

    public int getUserID(String accountURL, String email, String password, String returnURL) {

        AuthorizationApi auth = new AuthorizationApi();

        String response = given()
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(accountURL,email,password,returnURL))
                .header("user-agent","")
                .when()
                .get("https://context.reverso.net/translation/")
                .then()
                .extract().response().asString();

        String html = parse(response).toString();

        String varName = "user_id = '";
        int userIdIndexBegin = html.indexOf(varName)+varName.length();

        return Integer.valueOf(Arrays.asList(html.substring(userIdIndexBegin).
                split(varName+"|';")).get(0));

    }


}
