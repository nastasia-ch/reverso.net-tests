package mailru.nastasiachernega.tests.data.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement
            emailInput = $("#Email"),
            passwordInput = $("#Password"),
            loginButton = $("button[type=submit]"),
            loginErrorOutput = $(".validation-summary-errors");

    public LoginPage setEmail(String email) {
        emailInput.setValue(email);
        return this;
    };

    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    };

    public LoginPage clickLoginButton() {
        loginButton.click();
        return this;
    };

    public LoginPage checkLoginError(String errorLoginInfo) {
        loginErrorOutput.shouldHave(text(errorLoginInfo));
        return this;
    };

}
