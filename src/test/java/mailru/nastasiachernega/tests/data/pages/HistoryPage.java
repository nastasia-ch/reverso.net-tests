package mailru.nastasiachernega.tests.data.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class HistoryPage {

    private SelenideElement
            clearHistoryButton = $("#remove"),
            acceptClearButton = $(".delete-alert").$(byText("Accept"));

    private ElementsCollection
            listOfHistoryResults = $$(".entry");

    public HistoryPage openPage(String path) {
        open(path);
        return this;
    };

    public HistoryPage checkAddingExampleInHistory(String exampleText) {
        listOfHistoryResults.contains(text(exampleText));
        return this;
    };

    public HistoryPage deleteHistory() {
        clearHistoryButton.click();
        acceptClearButton.click();
        return this;
    };


}
