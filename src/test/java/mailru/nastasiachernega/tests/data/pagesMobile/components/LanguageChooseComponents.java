package mailru.nastasiachernega.tests.data.pagesMobile.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.appium.java_client.AppiumBy.id;

public class LanguageChooseComponents {

    private final SelenideElement
            languageFromButton = $(id("com.softissimo.reverso.context:id/button_source_language")),
            languageToButton = $(id("com.softissimo.reverso.context:id/button_target_language"));

    private final ElementsCollection
            listOfLanguages = $$(id("com.softissimo.reverso.context:id/tv_language"));

    public LanguageChooseComponents clickButtonLanguageFrom() {
        languageFromButton.click();
        return this;
    }

    public LanguageChooseComponents clickButtonLanguageTo() {
        languageToButton.click();
        return this;
    }

    public LanguageChooseComponents chooseLanguage(String language) {
        listOfLanguages.findBy(text(language)).click();
        return this;
    }


}
