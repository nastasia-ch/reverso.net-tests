package mailru.nastasiachernega.tests.tests.components;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.ContextTranslationSpec.contextTranslationRequestSpec;
import static mailru.nastasiachernega.tests.data.specs.ContextTranslationSpec.contextTranslationResponseSpec;
import static org.jsoup.Jsoup.parse;

public class ContextTranslationApi {

    private String getResponseApi(String languageFromTo,
                                  String textForTranslation) {
        return given()
                .spec(contextTranslationRequestSpec)
                .pathParam("languagesFromTo", languageFromTo)
                .pathParam("text", textForTranslation)
                .when()
                .get("/{languagesFromTo}/{text}")
                .then()
                .spec(contextTranslationResponseSpec)
                .extract().response().asString();
    };

    private Document getParsedHtmlResponseApi(String languageFromTo,
                                              String textForTranslation) {
        return parse(getResponseApi(languageFromTo, textForTranslation));
    };

    public List<String> getTranslationsApi(String languageFromTo,
                                           String textForTranslation) {
        List<Element> elements =
                getParsedHtmlResponseApi(languageFromTo, textForTranslation).
                        select("#translations-content a .display-term");
        List<String> meanings = new ArrayList<>();
        for (Element element : elements) {
            String meaning = element.text();
            meanings.add(meaning);
        }
        return meanings;
    }
    public String getExampleTextApi(String languageFromTo,
                                        String textForTranslation,
                                        int exampleNumber) {

        return getParsedHtmlResponseApi(languageFromTo, textForTranslation).
                select("section .example").get(exampleNumber).
                select(".text").first().text();

    }

    public String getExampleTranslatedTextApi(String languageFromTo,
                                                  String textForTranslation,
                                                  int exampleNumber) {

        return getParsedHtmlResponseApi(languageFromTo, textForTranslation).
                select("section .example").get(exampleNumber).
                select(".text[lang=ru]").text();

    }




}
