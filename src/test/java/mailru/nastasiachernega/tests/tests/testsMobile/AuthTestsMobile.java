package mailru.nastasiachernega.tests.tests.testsMobile;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;
import static io.qameta.allure.Allure.step;

@Epic("Тесты MOBILE")
@Feature("Авторизация")
@Owner("Anastasia Chernega")
public class AuthTestsMobile extends TestBaseMobile {

    TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Авторизация с валидными данными")
    @Test
    @Tag("mobile_tests")
    void loginTest() {
        step("Нажимаем 'Login or register for free'", () -> {
            $(id("com.softissimo.reverso.context:id/txt_full_name")).click();
        });
        step("Выбираем вариант авторизации через email", () -> {
            $(id("com.softissimo.reverso.context:id/btn_sign_in")).click();
        });
        step("Вводим email", () -> {
            $(id("com.softissimo.reverso.context:id/et_email"))
                    .sendKeys(data.emailValid);
        });
        step("Вводим пароль", () -> {
            $(id("com.softissimo.reverso.context:id/et_password"))
                    .sendKeys(data.passwordValid);
        });
        step("Нажимаем кнопку авторизации", () -> {
            $(id("com.softissimo.reverso.context:id/btn_sign_in")).click();
        });
        step("Проверяем авторизацию", () -> {
            $(id("com.softissimo.reverso.context:id/et_username_reverso"))
                    .shouldHave(text(data.username));
            $(id("com.softissimo.reverso.context:id/et_email_reverso"))
                    .shouldHave(text(data.emailValid));
        });
    }
}
