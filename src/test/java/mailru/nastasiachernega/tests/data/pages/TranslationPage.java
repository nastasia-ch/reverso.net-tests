package mailru.nastasiachernega.tests.data.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import mailru.nastasiachernega.tests.data.pages.components.MenuComponents;
import org.assertj.core.condition.AnyOf;
import org.openqa.selenium.Cookie;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.exactTextsCaseSensitiveInAnyOrder;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byTextCaseInsensitive;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TranslationPage {

    MenuComponents menuComponents = new MenuComponents();

    private SelenideElement
            textToTranslateInput = $("#search-input input"),
            languageFromButton = $("#src-selector"),
            languageFromChoice = $$(".languages").first(),
            languageToButton = $("#trg-selector"),
            languageToChoice = $$(".languages").last(),
            searchButton = $("#search-button");

    private ElementsCollection
            listOfTranslations = $$("#translations-content " +
            ".translation .display-term"),
            listOfExamples = $$("#examples-content .example");

    public TranslationPage addAuthCookieToWebDriver(String path,
                                   String refreshToken) {
        open(path);
        Cookie cookie = new Cookie("reverso.net.ReversoRefreshToken", refreshToken);
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
        return this;
    };

    public TranslationPage openPage(String path) {
        open(path);
        executeJavaScript("$('aside').hide();");
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

    public TranslationPage checkTranslations(String[] translations) {
        listOfTranslations.contains(texts(translations));
        return this;
    };

    public TranslationPage checkExampleContainsInputText(int exampleNumber,
                                                         String textForTranslate) {
        listOfExamples.get(exampleNumber).$$(".text").first().
                shouldHave(text(textForTranslate));
        return this;
    };

    public TranslationPage checkExampleContainsTranslation(int exampleNumber,
                                                           String[] translations) {
        assertThat(listOfExamples.get(exampleNumber).$$(".text").
                last().getText()).containsAnyOf(Arrays.asList(translations).
                        toArray(new CharSequence[Arrays.asList(translations).size()]));
        return this;
    };

    public TranslationPage addInFavourites (int exampleNumber){
        executeJavaScript("$('#blocked-rude-results-banner').hide();");
        listOfExamples.get(exampleNumber).hover();
        executeJavaScript("$('#blocked-rude-results-banner').hide();");
        listOfExamples.get(exampleNumber).
                $("button[title='Mark this example as favourite']").click();
        return this;
    };

    public TranslationPage openUserMenu () {
        menuComponents.openUserMenu();
        return this;
    };

    public TranslationPage goToSectionFavourites () {
        menuComponents.goToFavouritesSection();
        return this;
    };

    public TranslationPage goToLoginSection () {
        menuComponents.goToLoginSection();
        return this;
    };

    public TranslationPage checkReversoHeaders (String[] reversoHeaders){
        menuComponents.checkHeaders(reversoHeaders);
        return this;
    };

    public TranslationPage IsThereSectionInMenu(String sectionName) {
        menuComponents.IsThereSectionInMenu(sectionName);
        return this;
    }

    public TranslationPage IsThereNotSectionInMenu(String sectionName) {
        menuComponents.IsThereNotSectionInMenu(sectionName);
        return this;
    }
}
