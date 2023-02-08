package mailru.nastasiachernega.tests.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byTextCaseInsensitive;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class UITests {

    @Test
    void nameOfBlocksTest() {
        open("https://www.reverso.net/text-translation");
        $("header.app-new-top-bar-desktop").$$("div div")
                .shouldHave(CollectionCondition.exactTextsCaseSensitiveInAnyOrder("Translation",
                        "Context","Grammar Check","Synonyms","Conjugation"));
    }

    @Test
    void translateTextTest() {
        open("https://www.reverso.net/text-translation");
        Configuration.holdBrowserOpen=true;
        $$(".language-select").first().$(".lang-label").click();
        $(".language-select-options").$(byText("Russian")).click();
        $$(".language-select").last().$(".lang-label").click();
        $(".language-select-options").$(byText("Italian")).click();
        $(".translation-input__source textarea")
                .setValue("Selenide - это обёртка вокруг Selenium WebDriver");
        $(".translation-input__result")
                .shouldHave(text("Selenide è un involucro intorno a Selenium WebDriver"));
    }

    @Test
    void switchLanguagesButtonTest() {
        open("https://www.reverso.net/text-translation");
        Configuration.holdBrowserOpen=true;
        $$(".language-select").first().$(".lang-label").click();
        $(".language-select-options").$(byText("Russian")).click();
        $$(".language-select").last().$(".lang-label").click();
        $(".language-select-options").$(byText("Italian")).click();
        $(".translation-input__source textarea")
                .setValue("Selenide - это обёртка вокруг Selenium WebDriver");
        $(".translation-input__result")
                .shouldHave(text("Selenide è un involucro intorno a Selenium WebDriver"));

        $("button[aria-label='Swap languages']").click();
    }

    @Test
    void translateWordDocTest() {
        open("https://documents.reverso.net/Default.aspx");
        //Configuration.holdBrowserOpen=true;
        System.out.println($("#reverso-header-2020").getAttribute("id"));
        //System.out.println($(".translation-language-marked").parent().getAttribute("href"));
        //$("a.link[href='https://documents.reverso.net/Default.aspx?lang=en&utm_source=reversoweb&utm_medium=textlink&utm_campaign=homepage-translatedoc#']").click();

        //actions().moveToElement($("$$(.translation-language-box"),115,1).click().build().perform();
        //$("#Form1").$(".upload-box").$(".translation-language-wrapper")
        //        .$(".translation-language-header").click();
        //$(byText("Translate documents")).click();
        //$("form").$(".translation-language-inner a").click();
        //$("a.link[href='https://documents.reverso.net/Default.aspx?lang=en&utm_source=reversoweb&utm_medium=textlink&utm_campaign=homepage-translatedoc#']").click();
        //$("a").shouldHave(attribute("href","https://documents.reverso.net/Default.aspx?lang=en&utm_source=reversoweb&utm_medium=textlink&utm_campaign=homepage-translatedoc#"));
        //sleep(500000);
        //$("a").shouldBe(visible);
        // $("#fileupload").uploadFromClasspath("Selenide.docx");
        // $(".modal-body").shouldHave(text("Sign up to translate your document"));

        System.out.printf("$(#fileUploadLbl)");

    }
}
