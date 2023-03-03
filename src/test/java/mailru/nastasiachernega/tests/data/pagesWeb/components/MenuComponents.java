package mailru.nastasiachernega.tests.data.pagesWeb.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.exactTextsCaseSensitiveInAnyOrder;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;

public class MenuComponents {

    private SelenideElement userMenuButton = $("#reverso-user-menu");

    private ElementsCollection listOfReversoHeaders =
            $(".reverso-links-wrapper").$$("a.product span");

    public MenuComponents checkHeaders (String[] headers){
        listOfReversoHeaders.
                shouldHave(exactTextsCaseSensitiveInAnyOrder(headers));
        return this;
    };

    public MenuComponents openUserMenu () {
        userMenuButton.click();
        return this;
    };

    public MenuComponents goToLoginSection () {
        userMenuButton.$(byLinkText("Log in")).click();
        return this;
    };

    public MenuComponents goToFavouritesSection () {
        userMenuButton.$(byLinkText("Favourites")).click();
        return this;
    };

    public MenuComponents logOut() {
        userMenuButton.$(byLinkText("Log out")).click();
        return this;
    };

    public MenuComponents IsThereSectionInMenu(String sectionName) {
        userMenuButton.$(".drop-down").shouldHave(text(sectionName));
        return this;
    }

    public MenuComponents IsThereNotSectionInMenu(String sectionName) {
        userMenuButton.$(".drop-down").shouldNotHave(text(sectionName));
        return this;
    }





}
