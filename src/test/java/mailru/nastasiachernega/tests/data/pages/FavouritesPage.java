package mailru.nastasiachernega.tests.data.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FavouritesPage {

    private SelenideElement
            clearAllFavouritesButton = $("#remove"),
            acceptClearButton = $(".delete-alert").$(byText("Accept"));

    private ElementsCollection
            listOfFavourites = $$(".examples");

    public FavouritesPage openPage(String path) {
        open(path);
        return this;
    };

    public FavouritesPage checkAddingExampleInFavourites(String exampleText,
                                                         String exampleTranslatedText) {
        listOfFavourites.contains(text(exampleText));
        listOfFavourites.contains(text(exampleTranslatedText));
        return this;
    };

    public int getExampleId(String exampleText) {
        return Integer.parseInt($$(".examples .src").
                findBy(text(exampleText)).parent().
                parent().getAttribute("data-id"));
    };

    public FavouritesPage checkMessageOpenPageWithoutAuth() {
        $("#not-logged").
                shouldHave(text("You should be logged in to save and view your favourites."));
        return this;
    };


}
