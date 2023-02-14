package mailru.nastasiachernega.tests.tests.testsUI;

import io.restassured.response.Response;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.FavouritesApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.HistoryApiSteps;
import mailru.nastasiachernega.tests.data.apiSteps.TranslationApiSteps;
import mailru.nastasiachernega.tests.data.models.AddInFavouritesResponseModel;
import mailru.nastasiachernega.tests.data.testData.TestData;
import mailru.nastasiachernega.tests.utils.XlsReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static io.qameta.allure.Allure.step;

public class testsApi {

    AuthorizationApiSteps authApi = new AuthorizationApiSteps();
    TranslationApiSteps translationApi = new TranslationApiSteps();
    FavouritesApiSteps favouritesApi = new FavouritesApiSteps();
    HistoryApiSteps historyApi = new HistoryApiSteps();

    TestData data = new TestData();

    @DisplayName("Проверка правильности получения контекстного примера и его перевода")
    @Test
    void apiCheckExampleContent() throws Exception{

        step("Выполняем api запрос на перевод текста '" + data.textToTranslate + "'");
        Response response =
                translationApi.apiTranslation(authApi.
                                getRefreshToken(data.accountURL,
                                data.emailValid,
                                data.passwordValid,
                                data.returnURL),
                                data.languageFromTo,
                                data.textToTranslate);

        step("Извлекаем из api ответа текст примера " + data.exampleNumber);
        String exampleText = response.body().htmlPath().
                getString("**.findAll{it.@class == 'example'}["+ data.exampleNumber + "]." +
                        "div.find{it.@class == 'src ltr'}.span.find{it.@class == 'text'}.text()").trim();

        step("Извлекаем из api ответа переведенный текст примера " + data.exampleNumber);
        String exampleTranslatedText = response.body().htmlPath().
                getString("**.findAll{it.@class == 'example'}["+ data.exampleNumber + "]." +
                        "div.find{it.@class == 'trg ltr'}.span.find{it.@class == 'text'}.text()").trim();

        step("Проверяем корректность текста примера " + data.exampleNumber);
        assertThat(exampleText).isEqualTo(data.getText());

        step("Проверяем корректность переведенного текста примера " + data.exampleNumber);
        assertThat(exampleTranslatedText).isEqualTo(data.getTranslatedText());

    }

    @DisplayName("Проверка добавления примера в 'Избранное'")
    @Test
    void apiAddInFavourites() throws Exception {

        step("Выполняем api запрос на добавление примера в 'Избраннное' и получаем JSON ответ");
        AddInFavouritesResponseModel response = favouritesApi.apiAddInFavourites(authApi.
                                            getRefreshToken(data.accountURL,
                                            data.emailValid,
                                            data.passwordValid,
                                            data.returnURL),
                                            data.getHTMLText(),
                                            data.langFromSymbol,
                                            data.textToTranslate,
                                            data.getHTMLTranslatedText(),
                                            data.langToSymbol,
                                            data.getTranslation()).
                                            as(AddInFavouritesResponseModel.class);

        step("Проверяем добавление примера в 'Избраннное' пользователя");
        assertThat(response.getUserID()).isEqualTo(data.userID);
        assertThat(response.getSrcText()).isEqualTo(data.textToTranslate);
        assertThat(response.getTrgText()).isEqualTo(data.getTranslation());
        assertThat(response.getSrcContext()).isEqualTo(data.getHTMLText());
        assertThat(response.getTrgContext()).isEqualTo(data.getHTMLTranslatedText());

        step("Выполняем api запрос на очищение раздела 'Избранное' после теста");
        Response response1 = favouritesApi.apiClearFavourites((authApi.
                        getRefreshToken(data.accountURL,
                        data.emailValid,
                        data.passwordValid,
                        data.returnURL)));
    }


}
