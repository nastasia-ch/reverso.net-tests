package mailru.nastasiachernega.tests.tests.testsAPI;

import io.qameta.allure.*;
import io.restassured.response.Response;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.FavouritesApiSteps;
import mailru.nastasiachernega.tests.data.models.FavouritesResponseModel;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.is;

@Epic("Тесты API")
@Feature("Избранное")
@Owner("Anastasia Chernega")
@Link(value = "Ссылка на тестируемый ресурс", url = "https://context.reverso.net/favourites")
public class FavouritesTestsApi {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    FavouritesApiSteps favouritesApi = new FavouritesApiSteps();
    TestData data = new TestData();

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка добавления примера в 'Избранное'")
    @Test
    void apiAddInFavourites() {

        step("Выполняем api запрос на добавление примера в 'Избраннное' " +
                "и проверяем корректность добавления примера");
        String refreshToken = authApi.
                getRefreshToken(data.emailValid, data.passwordValid);

        FavouritesResponseModel response = favouritesApi.
                apiAddInFavourites(refreshToken,
                                   data.exampleWithTags,
                                   data.langFromSymbol,
                                   data.text,
                                   data.translatedExampleWithTags,
                                   data.langToSymbol,
                                   data.contextTranslation)
                .extract().response().as(FavouritesResponseModel.class);

        assertThat(response.getUserID()).isEqualTo(data.userID);
        assertThat(response.getSrcText()).isEqualTo(data.text);
        assertThat(response.getTrgText()).isEqualTo(data.contextTranslation);
        assertThat(response.getSrcContext()).isEqualTo(data.exampleWithTags);
        assertThat(response.getTrgContext()).isEqualTo(data.translatedExampleWithTags);

        step("Очищаем данные после теста: выполняем api запрос на удаление примера");
        favouritesApi.apiDeleteFromFavourites(refreshToken, response.getId())
                .body("numUpdatedResults", is(1));
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("Проверка добавления примера с невалидными данными в 'Избранное'")
    @Test
    void apiAddInFavouritesWithInvalidData() {

        step("Выполняем api запрос на добавление примера с невалидными данными " +
                "и проверяем получение в ответе сообщения об ошибке");
        String refreshToken = authApi.
                getRefreshToken(data.emailValid, data.passwordValid);

        Response response = favouritesApi.
                apiAddInFavourites(refreshToken,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "")
                .statusCode(400)
                .extract().response();

        String errorText = response.htmlPath()
                .getString("**.find{it.@id == 'error-content'}.p[0].text()");

        assertThat(errorText).isEqualTo("Your request contains a malformed syntax " +
                "and cannot be fulfilled. Please, try again.");
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка добавления примера в 'Избранное' без авторизации")
    @Test
    void apiInFavouritesWithoutAuth() {

        step("Выполняем api запрос на добавление примера в 'Избраннное' " +
                "без авторизации и проверяем получение в ответе информации об ошибке");
        String refreshToken = "";
        favouritesApi.
                apiAddInFavourites(refreshToken,
                        data.exampleWithTags,
                        data.langFromSymbol,
                        data.text,
                        data.translatedExampleWithTags,
                        data.langToSymbol,
                        data.contextTranslation)
                .statusCode(403)
                .body("error", is("Forbidden"))
                .body("message", is("Access Denied"));
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Добавление комментария к примеру, сохраненному в 'Избранное'")
    @Test
    void apiAddCommentInFavourites() {

        step("Выполняем api запрос на добавление примера и получаем id примера");
        String refreshToken = authApi.
                getRefreshToken(data.emailValid, data.passwordValid);

        int exampleId = favouritesApi.
                apiAddInFavourites(refreshToken,
                                   data.exampleWithTags,
                                   data.langFromSymbol,
                                   data.text,
                                   data.translatedExampleWithTags,
                                   data.langToSymbol,
                                   data.contextTranslation)
                .extract().path("id");

        step("Добавляем к примеру с id " + exampleId + " комментарий: '" + data.commentText + "' " +
                "и проверяем, что комментарий добавлен в 'Избраннное' пользователя с " + data.userID + "," +
                        " к примеру с " + exampleId + ", c переданным в запросе текстом");
        FavouritesResponseModel response = favouritesApi.
                apiWorkWithComment(refreshToken, exampleId, data.commentText)
                .extract().response().as(FavouritesResponseModel.class);

        assertThat(response.getUserID()).isEqualTo(data.userID);
        assertThat(response.getId()).isEqualTo(exampleId);
        assertThat(response.getComment()).isEqualTo(data.commentText);

        step("Очищаем данные после теста: выполняем api запрос на очищение комментария");
        favouritesApi.apiWorkWithComment(refreshToken, exampleId, "")
                .body("comment",is(""));

        step("Очищаем данные после теста: выполняем api запрос на удаление приемера");
        favouritesApi.apiDeleteFromFavourites(refreshToken, exampleId)
                .body("numUpdatedResults", is(1));

    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("Удаление примера из 'Избранное'")
    @Test
    void apiDeleteFromFavourites() {

        step("Выполняем api запрос на добавление примера в 'Избраннное' и получаем id примера");
        String refreshToken = authApi.
                getRefreshToken(data.emailValid, data.passwordValid);

        int exampleId = favouritesApi.
                apiAddInFavourites(refreshToken,
                                   data.exampleWithTags,
                                   data.langFromSymbol,
                                   data.text,
                                   data.translatedExampleWithTags,
                                   data.langToSymbol,
                                   data.contextTranslation)
                .extract().path("id");

        step("Получаем количество сохраненных примеров в 'Избраннное'");
        int numOfFavourites = favouritesApi.
                apiGetListOfFavourites(refreshToken).
                extract().path("numTotalResults");

        step("Удаляем пример из 'Избраннное'");
        favouritesApi.apiDeleteFromFavourites(refreshToken, exampleId)
                .body("numUpdatedResults", is(1));

        step("Проверяем количество сохраненных примеров в 'Избраннное': должно уменьшиться на 1");
        favouritesApi.apiGetListOfFavourites(refreshToken).
                body("numTotalResults", is(numOfFavourites-1));
    }

}
