package mailru.nastasiachernega.tests.data.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.exactTextsCaseSensitiveInAnyOrder;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byTextCaseInsensitive;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TranslationPage {

    private SelenideElement
            textToTranslateInput = $("#search-input input"),
            languageFromButton = $("#src-selector"),
            languageFromChoice = $$(".languages").first(),
            languageToButton = $("#trg-selector"),
            languageToChoice = $$(".languages").last(),
            searchButton = $("#search-button"),
            userMenuButton = $("#reverso-user-menu"),
            favouritesButton = $(By.linkText("Favourites")),
            loginButton = $(By.linkText("Log in")),
            usernameInfo = $(".username");

    private ElementsCollection
            listOfTranslations = $$("#translations-content .translation .display-term"),
            listOfExamples = $$("#examples-content .example"),
            listOfReversoHeaders = $(".reverso-links-wrapper").$$("a.product span");

    public TranslationPage openPage(String path) {
        open(path);
        return this;
    };

    public TranslationPage setTextToTranslate(String textForTranslate) {
        textToTranslateInput.setValue(textForTranslate);
        return this;
    };

    public TranslationPage chooseLanguageFromTranslate(String languageFrom) {
        languageFromButton.click();
        languageFromChoice.$(byTextCaseInsensitive(languageFrom)).click();
        return this;
    };

    public TranslationPage chooseLanguageToTranslate(String languageTo) {
        languageToButton.click();
        languageToChoice.$(byTextCaseInsensitive(languageTo)).click();
        return this;
    };

    public TranslationPage clickOnSearchButton() {
        executeJavaScript("$('aside').hide();");
        searchButton.click();
        return this;
    };


    public TranslationPage checkTranslations(List<String> translations) {
        listOfTranslations.contains(texts(translations));
        return this;
    };

    public TranslationPage checkExampleConsistInputText(int exampleNumber,
                                                        String textForTranslate) {
        listOfExamples.get(exampleNumber).$$(".text").first().
                shouldHave(text(textForTranslate));
        return this;
    };

    public TranslationPage checkExampleConsistTranslatedText(int exampleNumber,
                                                             String[] translations) {
        assertThat(listOfExamples.get(exampleNumber).$$(".text").last().getText()).
                containsAnyOf(translations);
        return this;
    };

    public TranslationPage addInFavourites(int exampleNumber) {
        listOfExamples.get(exampleNumber).hover();
        listOfExamples.get(exampleNumber).
                $("button[title='Mark this example as favourite']").click();
        return this;
    };

    public TranslationPage goToSectionFavourites() {
        userMenuButton.click();
        favouritesButton.click();
        return this;
    };

    public TranslationPage goToLoginSection() {
        userMenuButton.click();
        loginButton.click();
        return this;
    };

    public TranslationPage checkLogin(String username) {
        userMenuButton.click();
        usernameInfo.shouldHave(text(username));
        return this;
    };


    public TranslationPage checkReversoHeaders(String[] reversoHeaders) {
        listOfReversoHeaders.
                shouldHave(exactTextsCaseSensitiveInAnyOrder(reversoHeaders));
        return this;
    };

}
