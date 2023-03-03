package mailru.nastasiachernega.tests.data.pagesMobile;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import mailru.nastasiachernega.tests.data.pagesMobile.components.LanguageChooseComponents;
import mailru.nastasiachernega.tests.data.pagesMobile.components.MenuComponents;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.appium.java_client.AppiumBy.id;

public class ContextTranslatePage {

    MenuComponents menuComponents = new MenuComponents();
    LanguageChooseComponents languageChooseComponents = new LanguageChooseComponents();

    private final SelenideElement
            textInput = $(id("com.softissimo.reverso.context:id/edit_search"));

    private final ElementsCollection
            listOfExamples = $$(id("com.softissimo.reverso.context:id/text_source")),
            listOfTranslatedExamples = $$(id("com.softissimo.reverso.context:id/text_target"));

    public ContextTranslatePage clickNewSearch() {
        menuComponents.clickNewSearch();
        return this;
    }

    public ContextTranslatePage setTextForTranslation(String text) {
        textInput.click();
        textInput.sendKeys(text);
        return this;
    }

    public ContextTranslatePage clickButtonLanguageFrom() {
        languageChooseComponents.clickButtonLanguageFrom();
        return this;
    }

    public ContextTranslatePage chooseLanguageFrom(String languageFrom) {
        languageChooseComponents.chooseLanguage(languageFrom);
        return this;
    }

    public ContextTranslatePage clickButtonLanguageTo() {
        languageChooseComponents.clickButtonLanguageTo();
        return this;
    }

    public ContextTranslatePage chooseLanguageTo(String languageTo) {
        languageChooseComponents.chooseLanguage(languageTo);
        return this;
    }

    public ContextTranslatePage checkExampleText(int exampleNumber,
                                                 String example) {
        listOfExamples.get(exampleNumber)
                .shouldHave(text(example));
        return this;
    }

    public ContextTranslatePage checkTranslatedExampleText(int exampleNumber,
                                                           String translatedExample) {
        listOfTranslatedExamples.get(exampleNumber)
                .shouldHave(text(translatedExample));
        return this;
    }

}
