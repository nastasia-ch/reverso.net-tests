package mailru.nastasiachernega.tests.data.pagesWeb;

import com.codeborne.selenide.ElementsCollection;
import mailru.nastasiachernega.tests.data.pagesWeb.components.PrivacyPolicy;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FavouritesPage {

    PrivacyPolicy privacyPolicy = new PrivacyPolicy();

    private final ElementsCollection
            listOfFavourites = $$(".examples");

    public FavouritesPage openPage(String path) {
        open(path);
        return this;
    }

    public FavouritesPage checkAddingExample(String example) {
        listOfFavourites.contains(text(example));
        return this;
    }

    public FavouritesPage checkAddingTranslatedExample(String translatedExample) {
        listOfFavourites.contains(text(translatedExample));
        return this;
    }

    public int getExampleId(String example) {
        return Integer.parseInt($$(".examples .src").
                findBy(text(example)).parent().
                parent().getAttribute("data-id"));
    }

    public FavouritesPage checkMessageOpenPageWithoutAuth() {
        $("#not-logged").
                shouldHave(text("You should be logged in to save and view your favourites."));
        return this;
    }

    public FavouritesPage agreePrivacyPolicy() {
        privacyPolicy.clickAgreeAndClose();
        return this;
    }

}
