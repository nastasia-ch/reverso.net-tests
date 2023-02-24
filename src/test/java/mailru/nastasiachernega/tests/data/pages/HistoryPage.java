package mailru.nastasiachernega.tests.data.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class HistoryPage {

    private SelenideElement
            filterTextInput = $("#filter-text"),
            filterButton = $("#filter"),
            clearHistoryButton = $("#remove"),
            acceptClearButton = $(".delete-alert").$(byText("Accept"));

    private ElementsCollection
            listOfHistoryResults = $$(".entry");

    public HistoryPage openPage(String path) {
        open(path);
        return this;
    };

    public HistoryPage setFilteredText(String textForTranslate) {
        filterTextInput.setValue(textForTranslate);
        return this;
    }

    public HistoryPage chooseSourceLanguage(String langFromSymbol) {
        $(byText("Source language")).click();
        $(".src.lang").$(".drop-down").
                $("span[data-value='"+langFromSymbol+"']").$("span").click();
        return this;
    }

    public HistoryPage chooseTargetLanguage(String langToSymbol) {
        $(byText("Target language")).click();
        $(".trg.lang").$(".drop-down").
                $("span[data-value='"+langToSymbol+"']").$("span").click();
        return this;
    }

    public HistoryPage clickFilterButton() {
        filterButton.click();
        return this;
    }

    public HistoryPage checkAddingTextInHistory(String text) {
        listOfHistoryResults.get(0).$(".src").equals(text(text));
        return this;
    };

    public HistoryPage checkDateOfAddingInHistory(String date) {
        listOfHistoryResults.get(0).$(".date").equals(text(date));
        return this;
    };

    public String getHistoryId(String text) {
        return $$(".entry .src").
                findBy(text(text)).parent().parent().parent().
                parent().getAttribute("data-id");
    };


}
