package mailru.nastasiachernega.tests.data.pagesWeb;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import mailru.nastasiachernega.tests.data.pagesWeb.components.PrivacyPolicy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class HistoryPage {

    PrivacyPolicy privacyPolicy = new PrivacyPolicy();

    private final SelenideElement
            filterTextInput = $("#filter-text"),
            filterButton = $("#filter");

    private final ElementsCollection
            listOfHistoryResults = $$(".entry");

    public HistoryPage openPage(String path) {
        open(path);
        return this;
    }

    public HistoryPage setFilteredText(String text) {
        filterTextInput.setValue(text);
        return this;
    }

    public HistoryPage chooseLanguageFrom(String langFromSymbol) {
        privacyPolicy.clickAgreeAndClose();
        $(byText("Source language")).click();
        $(".src.lang").$(".drop-down").
                $("span[data-value='" + langFromSymbol + "']").
                $("span").click();
        return this;
    }

    public HistoryPage chooseLanguageTo(String langToSymbol) {
        $(byText("Target language")).click();
        $(".trg.lang").$(".drop-down").
                $("span[data-value='" + langToSymbol + "']").
                $("span").click();
        return this;
    }

    public HistoryPage clickFilterButton() {
        filterButton.click();
        return this;
    }

    public HistoryPage checkAddingTextInHistory(String text) {
        listOfHistoryResults.get(0).
                $(".src").equals(text(text));
        return this;
    }

    public HistoryPage checkAddingDateInHistory(String date) {
        listOfHistoryResults.get(0).
                $(".date").equals(text(date));
        return this;
    }

    public String getHistoryId(String text) {
        return $$(".entry .src").
                findBy(text(text)).parent().parent().parent().
                parent().getAttribute("data-id");
    }

}
