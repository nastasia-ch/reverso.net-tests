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

    public FavouritesPage addCommentForExample (String exampleText,
                                                String commentText) {
        //$$(".examples .src").findBy(text(exampleText)).parent().
        //        parent().$(".details").$(".comment").click();
        $$(".examples .src").findBy(text(exampleText)).parent().
                parent().$(".comment").$("input").
                setValue(commentText).click();
        //$$(".examples .src").findBy(text(exampleText)).parent().
        //        parent().$(".comment").$("input").hover();
        $$(".examples .src").findBy(text(exampleText)).parent().
                parent().$(".comment").$(".icons").
                $("button.icon.check]").hover().click();
        $(".popup.message").$("button[title='Close']").click();
        //$$(".examples .src").findBy(text(exampleText)).parent().
        //        parent().$(".comment").$("input").click();
        //$$(".examples .src").findBy(text(exampleText)).parent().
        //        parent().$(".comment").$(".icons").
        //        $("[title='Accept']").hover().click();
        return this;
    };

    public FavouritesPage checkAddingComment (String exampleText,
                                              String commentText) {
        $$(".examples .src").findBy(text(exampleText)).parent().
                parent().$(".comment").$(".icons").$("[title='Undo changes']").
                shouldHave(attribute("data-default", "Good example"));
        return this;
    };

    public FavouritesPage deleteExample(String exampleText) {
        $$(".examples .src").findBy(text(exampleText)).parent().
                parent().$(".details").$("button.delete").click();
        acceptClearButton.click();
        return this;
    };

    public FavouritesPage checkDeleteExample(String exampleText) {
        $$(".examples .src").findBy(text(exampleText)).parent().
                parent().$(".details").$("button.delete").click();
        acceptClearButton.click();
        return this;
    };


    public FavouritesPage deleteAllFromFavourites() {
        clearAllFavouritesButton.click();
        acceptClearButton.click();
        return this;
    };

}
