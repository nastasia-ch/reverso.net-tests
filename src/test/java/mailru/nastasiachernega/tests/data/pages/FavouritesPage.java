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

    public String getExampleId(String exampleText) {
        executeJavaScript("$('aside').hide();");
        return ($$(".examples .src").
                findBy(text(exampleText)).parent().
                parent().getAttribute("data-id")).toString();
    };


}
