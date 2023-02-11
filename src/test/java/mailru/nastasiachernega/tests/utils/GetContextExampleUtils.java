package mailru.nastasiachernega.tests.utils;

import mailru.nastasiachernega.tests.tests.AuthorizationApi;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.jsoup.Jsoup.parse;

public class GetContextExampleUtils {

    CorrectionUtilsOfHtmlElements correctionUtil = new CorrectionUtilsOfHtmlElements();
    AuthorizationApi auth = new AuthorizationApi();

    public Document getParsedHtmlResponse(String languagesFromTo, String text) {
        String response = given()
                .log().all()
                .pathParam("languagesFromTo", languagesFromTo)
                .pathParam("text", text)
                .header("user-agent","")
                .when()
                .get("https://context.reverso.net/translation/{languagesFromTo}/{text}")
                .then()
                .extract().response().asString();
        return parse(response);
    }

    private Element getExample(String languagesFromTo, String text, int number) {
        return getParsedHtmlResponse(languagesFromTo,text).
                select("section .example").get(number);
    }

    public String getSrcContext(String languagesFromTo, String text, int number) {
        String srcElement = getExample(languagesFromTo,text,number).
                select(".text").first().toString();
        Element tagSpan = getExample(languagesFromTo,text,number).
                select(".text").first();
        return correctionUtil.clearElementFromTagSpan(srcElement,tagSpan);

    }

    public String getSrcText(String languagesFromTo, String text, int number) {
        return getExample(languagesFromTo,text,number).
                select(".text").first().
                select("em").text();
    }

    public String getTrgContext(String languagesFromTo, String text, int number) {
        String trgElement = getExample(languagesFromTo,text,number).
                select(".text").last().toString();
        Element tagSpan = getExample(languagesFromTo,text,number).
                select(".text").last();
        Elements tagA = getExample(languagesFromTo,text,number).
                select(".text a");
        return correctionUtil.clearElementFromSpanAndATags(trgElement,tagSpan,tagA);
    }

    public String getTrgText(String languagesFromTo, String text, int number) {
        return getExample(languagesFromTo,text,number).
                select(".text[lang=ru]").first().
                select("em").text();
    }

    public int getUserID(String accountURL, String email, String password, String returnURL) {

        String response = given()
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(accountURL, email, password, returnURL))
                .header("user-agent", "")
                .when()
                .get("https://context.reverso.net/translation/")
                .then()
                .extract().response().asString();

        String html = parse(response).toString();

        String varName = "user_id = '";
        int userIdIndexBegin = html.indexOf(varName) + varName.length();

        return Integer.valueOf(Arrays.asList(html.substring(userIdIndexBegin).
                split(varName + "|';")).get(0));
    }

}
