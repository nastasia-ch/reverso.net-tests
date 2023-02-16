package mailru.nastasiachernega.tests.tests;

import io.qameta.allure.Epic;
import io.restassured.response.Response;
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
public class testsApi {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationApiSteps translationApi = new TranslationApiSteps();
    FavouritesApiSteps favouritesApi = new FavouritesApiSteps();
    HistoryApiSteps historyApi = new HistoryApiSteps();

    TestData data = new TestData();

    @DisplayName("Проверка правильности получения контекстного примера и его перевода")
    @Test
    void apiCheckExampleContent() {

        step("Выполняем api запрос на перевод текста '" + data.textToTranslate + "'");
        Response response = translationApi.
                                apiTranslation(authApi.
                                getRefreshToken(data.accountURL,
                                data.emailValid,
                                data.passwordValid,
                                data.returnURL),
                                data.languageFromTo,
                                data.textToTranslate)
                .extract().response();

        step("Извлекаем из api ответа текст примера " + data.exampleNumber);
        String exampleText = response.body().htmlPath().
                getString("**.findAll{it.@class == 'example'}["+ data.exampleNumber + "]." +
                        "div.find{it.@class == 'src ltr'}.span.find{it.@class == 'text'}.text()").trim();

        step("Извлекаем из api ответа переведенный текст примера " + data.exampleNumber);
        String exampleTranslatedText = response.body().htmlPath().
                getString("**.findAll{it.@class == 'example'}["+ data.exampleNumber + "]." +
                        "div.find{it.@class == 'trg ltr'}.span.find{it.@class == 'text'}.text()").trim();

        step("Проверяем корректность текста примера " + data.exampleNumber);
        assertThat(exampleText).isEqualTo(data.example);

        step("Проверяем корректность переведенного текста примера " + data.exampleNumber);
        assertThat(exampleTranslatedText).isEqualTo(data.translatedExample);

    }

    @DisplayName("Проверка добавления примера в 'Избранное'")
    @Test
    void apiAddInFavourites() {

        step("Выполняем api запрос на добавление примера в 'Избраннное'");
        FavouritesResponseModel response = favouritesApi.
                                            apiAddInFavourites(authApi.
                                            getRefreshToken(data.accountURL,
                                            data.emailValid,
                                            data.passwordValid,
                                            data.returnURL),
                                            data.exampleWithTags,
                                            data.langFromSymbol,
                                            data.textToTranslate,
                                            data.translatedExampleWithTags,
                                            data.langToSymbol,
                                            data.contextTranslation)
                .extract().response().as(FavouritesResponseModel.class);

        step("Проверяем добавление примере в 'Избраннное' пользователя с нужным id");
        assertThat(response.getUserID()).isEqualTo(data.userID);

        step("Проверяем корректность добавления примера");
        assertThat(response.getSrcText()).isEqualTo(data.textToTranslate);
        assertThat(response.getTrgText()).isEqualTo(data.contextTranslation);
        assertThat(response.getSrcContext()).isEqualTo(data.exampleWithTags);
        assertThat(response.getTrgContext()).isEqualTo(data.translatedExampleWithTags);

        step("Очищаем данные после теста: выполняем api запрос на удаление примера");
        favouritesApi.apiDeleteFromFavourites(authApi.
                                getRefreshToken(data.accountURL,
                                        data.emailValid,
                                        data.passwordValid,
                                        data.returnURL),
                                        response.getId())
                .body("numUpdatedResults", is(1));
    }

    @DisplayName("Добавление комментария к примеру, сохраненному в 'Избранное'")
    @Test
    void apiAddCommentInFavourites() {

        step("Выполняем api запрос на добавление примера в 'Избраннное' и получаем id примера");
        int exampleId = favouritesApi.apiAddInFavourites(authApi.
                                        getRefreshToken(data.accountURL,
                                        data.emailValid,
                                        data.passwordValid,
                                        data.returnURL),
                                        data.exampleWithTags,
                                        data.langFromSymbol,
                                        data.textToTranslate,
                                        data.translatedExampleWithTags,
                                        data.langToSymbol,
                                        data.contextTranslation)
                                        .extract().path("id");

        step("Добавляем в пример с id " + exampleId + " комментарий: " + data.commentText);
        FavouritesResponseModel response = favouritesApi.apiWorkWithComment(authApi.
                                        getRefreshToken(data.accountURL,
                                        data.emailValid,
                                        data.passwordValid,
                                        data.returnURL),
                                        exampleId,
                                        data.commentText)
                .extract().response().as(FavouritesResponseModel.class);

        step("Проверяем добавление комментария в 'Избраннное' пользователя с нужным id");
        assertThat(response.getUserID()).isEqualTo(data.userID);

        step("К нужному примеру");
        assertThat(response.getId()).isEqualTo(exampleId);

        step("С переданным в запросе текстом");
        assertThat(response.getComment()).isEqualTo(data.commentText);

        step("Очищаем данные после теста: выполняем api запрос на очищение комментария и удаление приемера");
        favouritesApi.apiWorkWithComment(authApi.
                        getRefreshToken(data.accountURL,
                        data.emailValid,
                        data.passwordValid,
                        data.returnURL),
                        exampleId, "")
                .body("comment",is(""));

        favouritesApi.apiDeleteFromFavourites(authApi.
                        getRefreshToken(data.accountURL,
                        data.emailValid,
                        data.passwordValid,
                        data.returnURL),
                        exampleId)
                .body("numUpdatedResults", is(1));

    }

    @DisplayName("Удаление примера из 'Избранное'")
    @Test
    void apiDeleteFromFavourites() {

        step("Выполняем api запрос на добавление примера в 'Избраннное' и получаем id примера");
        int exampleId = favouritesApi.apiAddInFavourites(authApi.
                         getRefreshToken(data.accountURL,
                          data.emailValid,
                          data.passwordValid,
                          data.returnURL),
                          data.exampleWithTags,
                          data.langFromSymbol,
                          data.textToTranslate,
                          data.translatedExampleWithTags,
                          data.langToSymbol,
                          data.contextTranslation)
                          .extract().path("id");

        step("Получаем количество сохраненных примеров в 'Избраннное'");
        int numOfFavourites = favouritesApi.apiGetListOfFavourites(authApi.
                        getRefreshToken(data.accountURL,
                        data.emailValid,
                        data.passwordValid,
                        data.returnURL)).
                        extract().path("numTotalResults");

        step("Удаляем пример из 'Избраннное'");
        favouritesApi.apiDeleteFromFavourites(authApi.
                        getRefreshToken(data.accountURL,
                        data.emailValid,
                        data.passwordValid,
                        data.returnURL),
                        exampleId)
                .body("numUpdatedResults", is(1));

        step("Проверяем количество сохраненных примеров в 'Избраннное': должно уменьшиться на 1");
        favouritesApi.apiGetListOfFavourites(authApi.
                         getRefreshToken(data.accountURL,
                         data.emailValid,
                         data.passwordValid,
                         data.returnURL)).
                         body("numTotalResults", is(numOfFavourites-1));


    }





}
