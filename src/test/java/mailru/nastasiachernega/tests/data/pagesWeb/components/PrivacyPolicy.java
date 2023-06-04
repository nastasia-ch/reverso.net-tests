package mailru.nastasiachernega.tests.data.pagesWeb.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PrivacyPolicy {

    private SelenideElement agreeAndCloseButton =
            $("div .didomi-popup-view").
            $(byText("Agree and close"));

    public PrivacyPolicy clickAgreeAndClose() {
        agreeAndCloseButton.click();
        return this;
    }

}
