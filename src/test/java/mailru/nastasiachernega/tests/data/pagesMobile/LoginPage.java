package mailru.nastasiachernega.tests.data.pagesMobile;

import com.codeborne.selenide.SelenideElement;
import mailru.nastasiachernega.tests.data.pagesMobile.components.MenuComponents;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

public class LoginPage {

    MenuComponents menuComponents = new MenuComponents();

    private final SelenideElement
            emailInput = $(id("com.softissimo.reverso.context:id/et_email")),
            passwordInput = $(id("com.softissimo.reverso.context:id/et_password")),
            loginButtonWithEmail = $(id("com.softissimo.reverso.context:id/btn_sign_in")),
            userNameInfo = $(id("com.softissimo.reverso.context:id/et_username_reverso")),
            userEmailInfo = $(id("com.softissimo.reverso.context:id/et_email_reverso"));

    public LoginPage clickUserProfile() {
        menuComponents.clickUserProfile();
        return this;
    }

    public LoginPage clickLoginButtonWithEmail() {
        loginButtonWithEmail.click();
        return this;
    }

    public LoginPage setEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public LoginPage setPassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public LoginPage checkLogin(String username) {
        userNameInfo.shouldHave(text(username));
        return this;
    }

}
