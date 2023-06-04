package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.pagesWeb.LoginPage;
import mailru.nastasiachernega.tests.data.pagesWeb.TranslatePage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.tests.TestBaseWeb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;

@Epic("Reverso Context")
@Feature("Тесты UI")
@Story("Авторизация")
@Owner("Anastasia Chernega")
@Link(value = "Тестируемый ресурс 'Reverso Context'",
        url = "https://account.reverso.net/Account/Login")
public class AuthTests extends TestBaseWeb {

    LoginPage loginPage = new LoginPage();
    TranslatePage translationPage = new TranslatePage();
    static TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Авторизация с валидными данными")
    @Test
    @Tag("UI_tests")
    @Tag("auth_tests")
    void loginTestWithValidData() {

        step("Тестовые шаги", () -> {
            step("Открываем страницу", () -> {
                translationPage
                        .openPage(data.translationPath);
            });

            step("Переходим в раздел авторизации", () -> {
                translationPage
                        .openUserMenu()
                        .goToLoginSection();
            });

            step("Вводим валидный электронный адрес", () -> {
                loginPage
                        .setEmail(data.emailValid);
            });

            step("Вводим валидный пароль", () -> {
                loginPage
                        .setPassword(data.passwordValid);
            });

            step("Нажимаем кнопку авторизации", () -> {
                loginPage
                        .clickLoginButton();
            });

            step("Проверяем авторизацию", () -> {

                translationPage
                        .openUserMenu()
                        .isThereSectionInMenu(data.username);
            });
        });
    }

    static Stream<Arguments> loginTestWithInvalidData() {
        return Stream.of(
                Arguments.of("валидный", data.emailValid,
                        "невалидный", data.passwordInvalid),
                Arguments.of("невалидный", data.emailInvalid,
                        "невалидный", data.passwordInvalid)
        );
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Авторизация с невалидными данными")
    @MethodSource()
    @ParameterizedTest
    @Tag("UI_tests")
    @Tag("auth_tests")
    void loginTestWithInvalidData(String emailType, String email,
                                  String passwordType, String password) {

        step("Тестовые шаги", () -> {
            step("Открываем страницу", () -> {
                translationPage
                        .openPage(data.translationPath);
            });

            step("Переходим в раздел авторизации", () -> {
                translationPage
                        .openUserMenu()
                        .goToLoginSection();
            });

            step("Вводим " + emailType + " электронный адрес", () -> {
                loginPage
                        .setEmail(email);
            });

            step("Вводим " + passwordType + " пароль", () -> {
                loginPage
                        .setPassword(password);
            });

            step("Нажимаем кнопку авторизации", () -> {
                loginPage
                        .clickLoginButton();
            });

            step("Проверяем отображение информации об ошибке", () -> {
                loginPage
                        .checkLoginError(data.errorLoginInfo);
            });
        });
    }

}
