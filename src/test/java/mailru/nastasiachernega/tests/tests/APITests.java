package mailru.nastasiachernega.tests.tests;

import com.codeborne.selenide.WebDriverRunner;
import mailru.nastasiachernega.tests.data.models.*;
import mailru.nastasiachernega.tests.data.testData.TestDataApi;
import mailru.nastasiachernega.tests.utils.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.jsoup.Jsoup.parse;

public class APITests {

    TestDataApi date = new TestDataApi();
    AuthorizationApi auth = new AuthorizationApi();
    Utils utils = new Utils();

    @Test
    void grammarCheckUnknownWordTest() {

        GrammarCheckRequestModel requestBody =
                GrammarCheckRequestModel.builder()
                .language("eng")
                .text("Selenide is a from wraapper for Selenium WebDriver.")
                .build();

        GrammarCheckResponseModel response = given()
                .log().all()
                .body(requestBody)
                .contentType("application/json; charset=utf-8")
                .header("user-agent","")
                .when()
                .post("https://orthographe.reverso.net/api/v1/Spelling/")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(GrammarCheckResponseModel.class);

        assertThat(response.getCorrections().get(0).getGroup()).
                isEqualTo("Unknown");
        assertThat(response.getCorrections().get(0).getShortDescription()).
                isEqualTo("Possible Spelling Mistake");
        assertThat(response.getCorrections().get(0).getLongDescription()).
                isEqualTo("A proper or common noun is not capitalized");
        assertThat(response.getCorrections().get(0).getMistakeDefinition()).
                isEqualTo("unknown word, no suggestions available");

    }

    @Test
    void grammarCheckAutoCorrectedMistakeTest() {

        GrammarCheckRequestModel requestBody =
                GrammarCheckRequestModel.builder()
                        .language("eng")
                        .text("Selenide is a from wraapper for Selenium WebDriver.")
                        .build();

        GrammarCheckResponseModel response = given()
                .log().all()
                .body(requestBody)
                .contentType("application/json; charset=utf-8")
                .header("user-agent","")
                .when()
                .post("https://orthographe.reverso.net/api/v1/Spelling/")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(GrammarCheckResponseModel.class);

        assertThat(response.getCorrections().get(2).getGroup()).
                isEqualTo("AutoCorrected");
        assertThat(response.getCorrections().get(2).getShortDescription()).
                isEqualTo("Spelling Mistake");
        assertThat(response.getCorrections().get(2).getLongDescription()).
                isEqualTo("A word was not spelled correctly");
        assertThat(response.getCorrections().get(2).getCorrectionText()).
                isEqualTo("wrapper");

    }

    @CsvSource(value = {
            "1, wrapper, a loose dressing gown for women",
            "2, wrappers, a loose dressing gown for women",
            "3, wrapped, covered with or as if with clothes or a wrap or cloak"
    })
    @ParameterizedTest
    void grammarCheckSuggestionsForMistakeAutoCorrectionTest(int numberOfSuggestion,
                                                               String suggestedText,
                                                               String definition) {

        GrammarCheckRequestModel requestBody =
                GrammarCheckRequestModel.builder()
                        .language("eng")
                        .text("Selenide is a from wraapper for Selenium WebDriver.")
                        .build();

        GrammarCheckResponseModel response = given()
                .log().all()
                .body(requestBody)
                .contentType("application/json; charset=utf-8")
                .header("user-agent","")
                .when()
                .post("https://orthographe.reverso.net/api/v1/Spelling/")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(GrammarCheckResponseModel.class);

        assertThat(response.getCorrections().get(2).getSuggestions().get(numberOfSuggestion-1).
                getText()).isEqualTo(suggestedText);
        assertThat(response.getCorrections().get(2).getSuggestions().get(numberOfSuggestion-1).
                getDefinition()).isEqualTo(definition);

    }

    @ValueSource(strings = {
            "обеспечение качества",
            "гарантия качества",
            "контроль качества",
            "качество"
    })
    @ParameterizedTest
    void contextTranslationTest(String translation) {

        String response = given().
                log().all()
                .pathParam("languagesFromTo", "english-russian")
                .pathParam("text", "quality assurance")
                .header("user-agent","")
                .when()
                .get("https://context.reverso.net/translation/{languagesFromTo}/{text}")
                .then()
                .extract().response().asString();

        Document html = parse(response);
        List<Element> elements = html.select("#translations-content a .display-term");
        List<String> meanings = new ArrayList<>();
        for (Element element : elements) {
            String meaning = element.text();
            meanings.add(meaning);
        }
        assertThat(meanings).contains(translation);
    }

    @Test
    void contextExamplesContentTest() {

        String response = given()
                .log().all()
                .pathParam("languagesFromTo", "english-russian")
                .pathParam("text", "quality assurance")
                .header("user-agent","")
                .when()
                .get("https://context.reverso.net/translation/{languagesFromTo}/{text}")
                .then()
                .extract().response().asString();

        Document html = parse(response);
        String contextText = html.select("section .example").get(8).
                select(".text").first().text();
        String translatedContextText = html.select("section .example").get(8).
                select(".text[lang=ru]").text();

        assertThat(contextText).contains("quality assurance");
        assertThat(translatedContextText).containsAnyOf("обеспечение качества", "гарантия качества",
                "контроль качества", "качество");
    }

    @Test
    void addInFavourites() {

        AddInFavouritesRequestModel requestBody = new AddInFavouritesRequestModel();
        requestBody.setSrcContext(date.srcContext);
        requestBody.setSrcLang(date.srcLang);
        requestBody.setSrcText(date.srcText);
        requestBody.setTrgContext(date.trgContext);
        requestBody.setTrgLang(date.trgLang);
        requestBody.setTrgText(date.trgText);

        AddInFavouritesResponseModel response = given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .body(requestBody)
                .contentType("application/json; charset=utf-8")
                .header("user-agent","")
                .when()
                .post("https://context.reverso.net/bst-web-user/user/favourites")
                .then()
                .statusCode(200)
                .log().all()
                .extract().as(AddInFavouritesResponseModel.class);

        assertThat(response.getUserID()).isEqualTo(utils.getUserID(date.accountURL,date.email,date.password,date.returnURL));
        assertThat(response.getSrcContext()).isEqualTo(date.srcContext);
        assertThat(response.getTrgContext()).isEqualTo(date.trgContext);

        System.out.println();

    }

    @Test
    void checkAddingInFavourites() {

        AddInFavouritesRequestModel requestBody = new AddInFavouritesRequestModel();
        requestBody.setSrcContext(date.srcContext);
        requestBody.setSrcLang(date.srcLang);
        requestBody.setSrcText(date.srcText);
        requestBody.setTrgContext(date.trgContext);
        requestBody.setTrgLang(date.trgLang);
        requestBody.setTrgText(date.trgText);

        int dataId = given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .body(requestBody)
                .contentType("application/json; charset=utf-8")
                .header("user-agent","")
                .when()
                .post("https://context.reverso.net/bst-web-user/user/favourites")
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        open("https://context.reverso.net/translation/");

        Cookie cookie = new Cookie("reverso.net.ReversoRefreshToken",
                auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL));

        WebDriverRunner.getWebDriver().manage().addCookie(cookie);

        open("https://context.reverso.net/favourites");
        //Configuration.holdBrowserOpen=true;
        $("#reverso-user-menu").click();
        $(byText("Favourites")).click();

        $("[data-id='"+dataId+"'] .examples").
                shouldHave(text(date.srcContext.replace("<em>","").
                        replace("</em>","")));
        $("[data-id='"+dataId+"']").
                shouldHave(text(date.trgContext.replace("<em>","").
                replace("</em>","")));;

        System.out.println();

    }


    @Test
    void deleteFromFavourites() {

        AddInFavouritesRequestModel requestBody = new AddInFavouritesRequestModel();
        requestBody.setSrcContext(date.srcContext);
        requestBody.setSrcLang(date.srcLang);
        requestBody.setSrcText(date.srcText);
        requestBody.setTrgContext(date.trgContext);
        requestBody.setTrgLang(date.trgLang);
        requestBody.setTrgText(date.trgText);

        AddInFavouritesResponseModel response = given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .body(requestBody)
                .contentType("application/json; charset=utf-8")
                .header("user-agent","")
                .when()
                .post("https://context.reverso.net/bst-web-user/user/favourites")
                .then()
                .statusCode(200)
                .log().all()
                .extract().as(AddInFavouritesResponseModel.class);

        assertThat(response.getUserID()).isEqualTo(utils.getUserID(date.accountURL,date.email,date.password,date.returnURL));
        assertThat(response.getSrcContext()).isEqualTo(date.srcContext);
        assertThat(response.getTrgContext()).isEqualTo(date.trgContext);

        int dataId = response.getId();

        int num = given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .queryParam("length",25)
                .header("user-agent","")
                .when()
                .get("https://context.reverso.net/bst-web-user/user/favourites")
                .then()
                .statusCode(200)
                .extract().body().path("numTotalResults");

        given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .queryParam("ids",dataId)
                .header("user-agent","")
                .when()
                .delete("https://context.reverso.net/bst-web-user/user/favourites")
                .then()
                .log().all()
                .statusCode(200)
                .body("numUpdatedResults", is(1));

        given()
                .log().all()
                .cookie("reverso.net.ReversoRefreshToken",
                        auth.getRefreshToken(date.accountURL,date.email,date.password,date.returnURL))
                .queryParam("length",25)
                .header("user-agent","")
                .when()
                .get("https://context.reverso.net/bst-web-user/user/favourites")
                .then()
                .statusCode(200)
                .body("numTotalResults",is(num-1));

    }


    // тест сохранения в избранное

    // тест сохранения в истории

}
