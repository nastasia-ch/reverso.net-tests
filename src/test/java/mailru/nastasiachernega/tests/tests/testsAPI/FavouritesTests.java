package mailru.nastasiachernega.tests.tests.testsAPI;

import io.qameta.allure.*;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.FavouritesApiSteps;
import mailru.nastasiachernega.tests.data.models.FavouritesResponseModel;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.is;

@Epic("Тесты API")
@Feature("Избранное")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс 'Reverso Context'",
        url = "https://context.reverso.net/favourites")
public class FavouritesTests {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    FavouritesApiSteps favouritesApi = new FavouritesApiSteps();
    TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Добавление примера в 'Избранное' с авторизацией")
    @Test
    @Tag("API_tests")
    @Tag("favourites_tests")
    void apiAddInFavouritesWithAuth() {

        String refreshToken = step("Выполняем api-запрос на получение " +
                "авторизационной куки", () ->
                authApi
                        .getRefreshToken(data.emailValid, data.passwordValid));

        FavouritesResponseModel response = step("Выполняем api-запрос на " +
                "добавление примера в 'Избраннное'", () ->
                favouritesApi
                        .addInFavourites(refreshToken,
                                data.exampleWithTags,
                                data.langFromSymbol,
                                data.text,
                                data.translatedExampleWithTags,
                                data.langToSymbol,
                                data.contextTranslation)
                        .extract().response().as(FavouritesResponseModel.class));

        step("Проверяем корректность данных в ответе", () -> {
            assertThat(response.getUserID()).isEqualTo(data.userID);
            assertThat(response.getSrcText()).isEqualTo(data.text);
            assertThat(response.getTrgText()).isEqualTo(data.contextTranslation);
            assertThat(response.getSrcContext()).isEqualTo(data.exampleWithTags);
            assertThat(response.getTrgContext()).isEqualTo(data.translatedExampleWithTags);
        });

        step("Очищаем данные после теста: выполняем api-запрос " +
                "на удаление примера", () ->
                favouritesApi
                        .deleteFromFavourites(refreshToken, response.getId())
                        .body("numUpdatedResults", is(1)));
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("Добавление примера в 'Избранное' с пустыми данными ")
    @Test
    @Tag("API_tests")
    @Tag("favourites_tests")
    void apiAddInFavouritesWithNullData() {

        String refreshToken = step("Выполняем api-запрос на получение " +
                "авторизационной куки", () ->
                authApi
                        .getRefreshToken(data.emailValid, data.passwordValid));

        String errorText = step("Выполняем api-запрос на " +
                "добавление примера в 'Избраннное', проверяем код ответа " +
                "и извлекаем текст ошибки", () ->
                favouritesApi
                        .addInFavourites(refreshToken,
                                "",
                                "",
                                "",
                                "",
                                "",
                                "")
                        .statusCode(400)
                        .extract().response()
                        .htmlPath()
                        .getString("**.find{it.@id == 'error-content'}.p[0].text()"));

        step("Проверяем текст ошибки в ответе", () -> {
            assertThat(errorText).isEqualTo("Your request contains a " +
                    "malformed syntax and cannot be fulfilled. Please, try again.");
        });
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("Добавление примера в 'Избранное' без авторизации")
    @Test
    @Tag("API_tests")
    @Tag("favourites_tests")
    void apiInFavouritesWithoutAuth() {

        String refreshToken = "";
        step("Выполняем api-запрос на добавление примера в " +
                "'Избраннное' без авторизации и проверяем получение в ответе " +
                "информации об ошибке", () -> {
            favouritesApi
                    .addInFavourites(refreshToken,
                            data.exampleWithTags,
                            data.langFromSymbol,
                            data.text,
                            data.translatedExampleWithTags,
                            data.langToSymbol,
                            data.contextTranslation)
                    .statusCode(403)
                    .body("error", is("Forbidden"))
                    .body("message", is("Access Denied"));
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Добавление комментария к примеру")
    @Test
    @Tag("API_tests")
    @Tag("favourites_tests")
    void apiAddCommentInFavourites() {

        String refreshToken = step("Выполняем api-запрос на получение " +
                "авторизационной куки", () ->
                authApi
                        .getRefreshToken(data.emailValid, data.passwordValid));

        int exampleId = step("Выполняем api запрос на добавление примера " +
                "в 'Избранное' и извлекаем id примера", () ->
                favouritesApi
                        .addInFavourites(refreshToken,
                                data.exampleWithTags,
                                data.langFromSymbol,
                                data.text,
                                data.translatedExampleWithTags,
                                data.langToSymbol,
                                data.contextTranslation)
                        .extract().path("id"));

        FavouritesResponseModel response = step("Добавляем к примеру " +
                "комментарий", () ->
                favouritesApi
                        .updateComment(refreshToken, exampleId, data.commentText)
                        .extract().response().as(FavouritesResponseModel.class));

        step("Проверяем корректность добавления комментария", () -> {
            assertThat(response.getUserID()).isEqualTo(data.userID);
            assertThat(response.getId()).isEqualTo(exampleId);
            assertThat(response.getComment()).isEqualTo(data.commentText);
        });

        step("Очищаем данные после теста: выполняем api-запрос " +
                "на очищение комментария", () -> {
            favouritesApi
                    .updateComment(refreshToken, exampleId, "")
                    .body("comment", is(""));
        });

        step("Очищаем данные после теста: выполняем api-запрос " +
                "на удаление приемера", () -> {
            favouritesApi
                    .deleteFromFavourites(refreshToken, exampleId)
                    .body("numUpdatedResults", is(1));
        });

    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Удаление примера из 'Избранное'")
    @Test
    @Tag("API_tests")
    @Tag("favourites_tests")
    void apiDeleteFromFavourites() {

        String refreshToken = step("Выполняем api-запрос на получение " +
                "авторизационной куки", () ->
                authApi
                        .getRefreshToken(data.emailValid, data.passwordValid));

        int exampleId = step("Выполняем api запрос на добавление примера " +
                "в 'Избранное' и извлекаем id примера", () ->
                favouritesApi
                        .addInFavourites(refreshToken,
                                data.exampleWithTags,
                                data.langFromSymbol,
                                data.text,
                                data.translatedExampleWithTags,
                                data.langToSymbol,
                                data.contextTranslation)
                        .extract().path("id"));

        int numOfFavourites = step("Получаем количество сохраненных " +
                "примеров в 'Избраннное'", () ->
                favouritesApi
                        .getListOfFavourites(refreshToken)
                        .extract().path("numTotalResults"));

        step("Удаляем пример из 'Избраннное'", () -> {
            favouritesApi
                    .deleteFromFavourites(refreshToken, exampleId)
                    .body("numUpdatedResults", is(1));
        });

        step("Проверяем количество сохраненных примеров в 'Избраннное': " +
                "должно уменьшиться на 1", () -> {
            favouritesApi
                    .getListOfFavourites(refreshToken)
                    .body("numTotalResults", is(numOfFavourites - 1));
        });
    }
}
