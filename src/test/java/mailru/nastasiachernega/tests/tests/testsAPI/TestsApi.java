package mailru.nastasiachernega.tests.tests.testsAPI;

import io.qameta.allure.Epic;
import io.restassured.response.Response;
import mailru.nastasiachernega.tests.config.WebDriverProvider;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.FavouritesApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.HistoryApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.TranslationApiSteps;
import mailru.nastasiachernega.tests.data.models.FavouritesResponseModel;
import mailru.nastasiachernega.tests.data.testData.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.is;

@Epic("API tests")
public class TestsApi {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationApiSteps translationApi = new TranslationApiSteps();
    FavouritesApiSteps favouritesApi = new FavouritesApiSteps();
    HistoryApiSteps historyApi = new HistoryApiSteps();

    TestData data = new TestData();

    @DisplayName("Проверка правильности получения контекстного примера и его перевода")
    @Test
    void apiCheckExampleContent() {

        step("Выполняем api запрос на перевод текста '" + data.text + "'");
        String refreshToken = authApi.
                getRefreshToken(data.emailValid, data.passwordValid);

        Response response = translationApi
                .apiTranslation(refreshToken, data.languageFromTo, data.text)
                .extract().response();

        step("Извлекаем из api ответа текст примера " + data.exampleNumber);
        String example = response.body().htmlPath().
                getString("**.findAll{it.@class == 'example'}["+ data.exampleNumber + "]." +
                        "div.find{it.@class == 'src ltr'}.span.find{it.@class == 'text'}.text()").trim();

        step("Извлекаем из api ответа переведенный текст примера " + data.exampleNumber);
        String translatedExample = response.body().htmlPath().
                getString("**.findAll{it.@class == 'example'}["+ data.exampleNumber + "]." +
                        "div.find{it.@class == 'trg ltr'}.span.find{it.@class == 'text'}.text()").trim();

        step("Проверяем корректность текста примера " + data.exampleNumber);
        assertThat(example).isEqualTo(data.example);

        step("Проверяем корректность переведенного текста примера " + data.exampleNumber);
        assertThat(translatedExample).isEqualTo(data.translatedExample);
    }

    @DisplayName("Проверка добавления примера в 'Избранное'")
    @Test
    void apiAddInFavourites() {

        step("Выполняем api запрос на добавление примера в 'Избраннное' " +
                "и проверяем корректность добавления пример");
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
