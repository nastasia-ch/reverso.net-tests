package mailru.nastasiachernega.tests.data.pagesMobile.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static io.appium.java_client.AppiumBy.id;

public class MenuComponents {

    private final SelenideElement
            reversoTranslationButton = $(id("com.softissimo.reverso.context:id/button_reverso_translation")),
            newSearchButton = $(id("com.softissimo.reverso.context:id/button_new_search")),
            userProfileButton = $(id("com.softissimo.reverso.context:id/txt_full_name"));

    public MenuComponents clickUserProfile() {
        userProfileButton.click();
        return this;
    }

    public MenuComponents clickNewSearch() {
        newSearchButton.click();
        return this;
    }

    public MenuComponents clickReversoTranslation() {
        reversoTranslationButton.click();
        return this;
    }

}
