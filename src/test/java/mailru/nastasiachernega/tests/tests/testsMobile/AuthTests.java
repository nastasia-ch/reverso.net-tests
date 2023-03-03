package mailru.nastasiachernega.tests.tests.testsMobile;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.pagesMobile.LoginPage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.TestBaseMobile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Epic("Reverso Context")
@Feature("Тесты MOBILE")
@Story("Авторизация")
@Owner("Anastasia Chernega")
public class AuthTests extends TestBaseMobile {

    TestData data = new TestData();
    LoginPage loginPage = new LoginPage();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Авторизация с валидными данными")
    @Test
    @Tag("mobile_tests")
    @Tag("auth_tests")
    void loginTest() {

        step("Тестовые шаги", () -> {
            step("Нажимаем 'Login or register for free'", () -> {
                loginPage
                        .clickUserProfile();
            });

            step("Выбираем вариант авторизации через email", () -> {
                loginPage
                        .clickLoginButtonWithEmail();
            });

            step("Вводим email", () -> {
                loginPage
                        .setEmail(data.emailValid);
            });

            step("Вводим пароль", () -> {
                loginPage
                        .setPassword(data.passwordValid);
            });

            step("Нажимаем кнопку авторизации", () -> {
                loginPage
                        .clickLoginButtonWithEmail();
            });

            step("Проверяем авторизацию", () -> {
                loginPage.checkLogin(data.username);
            });
        });
    }
}
