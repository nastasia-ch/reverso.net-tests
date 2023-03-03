package mailru.nastasiachernega.tests.data.pagesMobile;

import com.codeborne.selenide.SelenideElement;
import mailru.nastasiachernega.tests.data.pagesMobile.components.LanguageChooseComponents;
import mailru.nastasiachernega.tests.data.pagesMobile.components.MenuComponents;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

public class TranslatePage {

    MenuComponents menuComponents = new MenuComponents();
    LanguageChooseComponents languageChooseComponents = new LanguageChooseComponents();

    private final SelenideElement
            textInput = $(id("com.softissimo.reverso.context:id/textInputTranslator")),
            translateButton = $(id("com.softissimo.reverso.context:id/translateInputTranslator")),
            translateResult = $(id("com.softissimo.reverso.context:id/responseTextTranslator"));

    public TranslatePage clickReversoTranslation() {
        menuComponents.clickReversoTranslation();
        return this;
    }

    public TranslatePage setTextForTranslation(String text) {
        textInput.click();
        textInput.sendKeys(text);
        return this;
    }

    public TranslatePage clickButtonLanguageFrom() {
        languageChooseComponents.clickButtonLanguageFrom();
        return this;
    }

    public TranslatePage chooseLanguageFrom(String languageFrom) {
        languageChooseComponents.chooseLanguage(languageFrom);
        return this;
    }

    public TranslatePage clickButtonLanguageTo() {
        languageChooseComponents.clickButtonLanguageTo();
        return this;
    }

    public TranslatePage chooseLanguageTo(String languageTo) {
        languageChooseComponents.chooseLanguage(languageTo);
        return this;
    }

    public TranslatePage clickTranslateButton() {
        translateButton.click();
        return this;
    }

    public TranslatePage checkTranslation(String[] translations) {
        translateResult.shouldHave(text(Arrays.asList(translations).get(0)));
        return this;
    }

}
