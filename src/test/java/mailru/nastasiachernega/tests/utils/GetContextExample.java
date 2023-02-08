package mailru.nastasiachernega.tests.utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static io.restassured.RestAssured.given;
import static org.jsoup.Jsoup.parse;

public class GetContextExample {

    CorrectionUtilsOfHtmlElements util = new CorrectionUtilsOfHtmlElements();

    private Document getHtmlResponse(String languagesFromTo, String text) {
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
        return getHtmlResponse(languagesFromTo,text).
                select("section .example").get(number);
    }

    public String getSrcContext(String languagesFromTo, String text, int number) {
        String srcElement = getExample(languagesFromTo,text,number).
                select(".text").first().toString();
        Element tagSpan = getExample(languagesFromTo,text,number).
                select(".text").first();
        return util.clearElementFromTagSpan(srcElement,tagSpan);

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
        return util.clearElementFromSpanAndATags(trgElement,tagSpan,tagA);
    }

    public String getTrgText(String languagesFromTo, String text, int number) {
        return getExample(languagesFromTo,text,number).
                select(".text[lang=ru]").first().
                select("em").text();
    }

}
