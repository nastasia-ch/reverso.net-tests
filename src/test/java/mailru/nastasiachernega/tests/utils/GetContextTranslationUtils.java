package mailru.nastasiachernega.tests.utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import static org.jsoup.Jsoup.parse;

public class GetContextTranslationUtils {

    private Document getParsedHtmlResponse(String response) {
        return parse(response);
    }

    public List<String> getTranslations(String response) {
        List<Element> elements =
                getParsedHtmlResponse(response).
                select("#translations-content a .display-term");
        List<String> meanings = new ArrayList<>();
        for (Element element : elements) {
            String meaning = element.text();
            meanings.add(meaning);
        }
        return meanings;
    }

    public String getExampleContextText(String response, int exampleNumber) {

        return getParsedHtmlResponse(response).select("section .example").get(exampleNumber).
                select(".text").first().text();

    }

    public String getExampleTranslatedContextText(String response, int exampleNumber) {

        return getParsedHtmlResponse(response).select("section .example").get(exampleNumber).
                select(".text[lang=ru]").text();

    }

}
