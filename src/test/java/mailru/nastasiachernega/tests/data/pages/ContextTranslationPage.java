package mailru.nastasiachernega.tests.data.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byTextCaseInsensitive;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ContextTranslationPage {

    private SelenideElement
            textToTranslateInput = $("#search-input input"),
            languageFromButton = $("#src-selector"),
            languageFromChoice = $$(".languages").first(),
            languageToButton = $("#trg-selector"),
            languageToChoice = $$(".languages").last(),
            searchButton = $("#search-button");

    private ElementsCollection
            listOfTranslations = $$("#translations-content .translation .display-term"),
            getListOfExamples = $$("#examples-content .example");

    public ContextTranslationPage openPage(String path) {
        open(path);
        return this;
    };

    public ContextTranslationPage setTextToTranslate(String textToTranslate) {
        textToTranslateInput.setValue(textToTranslate);
        return this;
    };

    public ContextTranslationPage chooseLanguageFromTranslate(String languageFrom) {
        languageFromButton.click();
        languageFromChoice.$(byTextCaseInsensitive(languageFrom)).click();
        return this;
    };

    public ContextTranslationPage chooseLanguageToTranslate(String languageTo) {
        languageToButton.click();
        languageToChoice.$(byTextCaseInsensitive(languageTo)).click();
        return this;
    };

    public ContextTranslationPage clickOnSearchButton() {
        executeJavaScript("$('aside').hide();");
        searchButton.click();
        return this;
    };

    public ContextTranslationPage checkTranslations(String[] translations) {
        listOfTranslations.contains(texts(translations));
        return this;
    };

    public ContextTranslationPage checkExampleConsistInputText(int exampleNumber,
                                                               String textToTranslate) {
        getListOfExamples.get(exampleNumber).$$(".text").first().
                shouldHave(text(textToTranslate));
        return this;
    };

    public ContextTranslationPage checkExampleConsistTranslatedText(int exampleNumber,
                                                                    String[] translations) {
        assertThat(getListOfExamples.get(exampleNumber).$$(".text").last().getText()).
                containsAnyOf(translations);
        return this;
    };


}
