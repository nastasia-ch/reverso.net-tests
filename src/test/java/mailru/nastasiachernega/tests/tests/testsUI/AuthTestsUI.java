package mailru.nastasiachernega.tests.tests.testsUI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.config.WebDriverProvider;
import mailru.nastasiachernega.tests.data.pages.LoginPage;
import mailru.nastasiachernega.tests.data.pages.TranslationPage;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;

@Epic("Тесты UI")
@Feature("Авторизация")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс", url = "https://context.reverso.net/translation/")
public class AuthTestsUI extends WebDriverProvider {

    LoginPage loginPage = new LoginPage();
    TranslationPage translationPage = new TranslationPage();
    static TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка авторизации с валидными электронным адресом и паролем")
    @Test
    void loginTestWithValidData() {

        step("Открываем страницу", ()-> {
            translationPage.
                    openPage(data.translationPath);
        });

        step("Переходим в раздел авторизации", ()-> {
            translationPage
                    .openUserMenu()
                    .goToLoginSection();
        });

        step("Вводим электронный адрес зарегистрированного пользователя", ()-> {
            loginPage.setEmail(data.emailValid);
        });

        step("Вводим валидный пароль", ()-> {
            loginPage.setPassword(data.passwordValid);
        });

        step("Нажимаем кнопку авторизации", ()-> {
            loginPage.clickLoginButton();
        });

        step("Проверяем авторизацию", ()-> {

            translationPage
                    .openUserMenu()
                    .IsThereSectionInMenu(data.username);
        });
    }

    static Stream<Arguments> loginTestWithInvalidData(){
        return Stream.of(
                Arguments.of(data.emailValid,data.passwordInvalid),
                Arguments.of(data.emailInvalid,data.passwordInvalid)
        );
    }
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка авторизации с валидными электронным адресом и невалидным паролем")
    @MethodSource()
    @ParameterizedTest
    void loginTestWithInvalidData(String email, String password) {

        step("Открываем страницу", ()-> {
            translationPage.
                    openPage(data.translationPath);
        });

        step("Переходим в раздел авторизации", ()-> {
            translationPage
                    .openUserMenu()
                    .goToLoginSection();
        });

        step("Вводим валидный электронный адрес", ()-> {
            loginPage.setEmail(email);
        });

        step("Вводим невалидный пароль", ()-> {
            loginPage.setPassword(password);
        });

        step("Нажимаем кнопку авторизации", ()-> {
            loginPage.clickLoginButton();
        });

        step("Проверяем отображение информации об ошибке: '" +
                data.errorLoginInfo + "'", ()-> {
            loginPage.checkLoginError(data.errorLoginInfo);
        });
    }

}
