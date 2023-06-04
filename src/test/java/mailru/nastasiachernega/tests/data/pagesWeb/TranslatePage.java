package mailru.nastasiachernega.tests.data.pagesWeb;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import mailru.nastasiachernega.tests.data.pagesWeb.components.MenuComponents;
import mailru.nastasiachernega.tests.data.pagesWeb.components.PrivacyPolicy;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byTextCaseInsensitive;
import static com.codeborne.selenide.Selenide.*;

public class TranslatePage {

    MenuComponents menuComponents = new MenuComponents();
    PrivacyPolicy privacyPolicy = new PrivacyPolicy();

    private final SelenideElement
            textToTranslateInput = $("#search-input input"),
            languageFromButton = $("#src-selector"),
            languageFromChoice = $$(".languages").first(),
            languageToButton = $("#trg-selector"),
            languageToChoice = $$(".languages").last(),
            searchButton = $("#search-button");

    private final ElementsCollection
            listOfTranslations = $$("#translations-content " +
            ".translation .display-term"),
            listOfExamples = $$("#examples-content .example");

    public TranslatePage addAuthCookieToWebDriver(String path,
                                                  String refreshToken) {
        open(path);
        Cookie cookie = new Cookie("reverso.net.ReversoRefreshToken", refreshToken);
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
        return this;
    }

    public TranslatePage openPage(String path) {
        open(path);
        executeJavaScript("$('aside').hide();");
        return this;
    }

    public TranslatePage setTextForTranslation(String text) {
        textToTranslateInput.setValue(text);
        return this;
    }

    public TranslatePage chooseLanguageFrom(String languageFrom) {
        languageFromButton.click();
        languageFromChoice.$(byTextCaseInsensitive(languageFrom)).click();
        return this;
    }

    public TranslatePage chooseLanguageTo(String languageTo) {
        languageToButton.click();
        languageToChoice.$(byTextCaseInsensitive(languageTo)).click();
        return this;
    }

    public TranslatePage clickOnSearchButton() {
        executeJavaScript("$('aside').hide();");
        searchButton.click();
        return this;
    }

    public TranslatePage checkTranslations(String[] translations) {
        listOfTranslations
                .contains(texts(translations));
        return this;
    }

    public TranslatePage checkExampleText(int exampleNumber,
                                          String example) {
        listOfExamples.get(exampleNumber).$$(".text").first().
                shouldHave(text(example));
        return this;
    }

    public TranslatePage checkTranslatedExampleText(int exampleNumber,
                                                    String translatedExample) {
        listOfExamples.get(exampleNumber).$$(".text").last().
                shouldHave(text(translatedExample));
        return this;
    }

    public TranslatePage addInFavourites(int exampleNumber) {
        executeJavaScript("$('#blocked-rude-results-banner').hide();");
        executeJavaScript("$('#blocked-results-banner').hide();");
        listOfExamples.get(exampleNumber).scrollTo().hover();
        listOfExamples.get(exampleNumber).
                $("button[title='Mark this example as favourite']").click();
        return this;
    }

    public TranslatePage openUserMenu() {
        menuComponents.openUserMenu();
        return this;
    }

    public TranslatePage goToSectionFavourites() {
        menuComponents.goToFavouritesSection();
        return this;
    }

    public TranslatePage goToLoginSection() {
        menuComponents.goToLoginSection();
        return this;
    }

    public TranslatePage checkReversoHeaders(String[] reversoHeaders) {
        menuComponents.checkHeaders(reversoHeaders);
        return this;
    }

    public TranslatePage isThereSectionInMenu(String sectionName) {
        menuComponents.IsThereSectionInMenu(sectionName);
        return this;
    }

    public TranslatePage isThereNotSectionInMenu(String sectionName) {
        menuComponents.IsThereNotSectionInMenu(sectionName);
        return this;
    }

    public TranslatePage agreePrivacyPolicy() {
        privacyPolicy.clickAgreeAndClose();
        return this;
    }
}
