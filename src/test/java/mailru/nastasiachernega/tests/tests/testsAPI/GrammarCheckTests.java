package mailru.nastasiachernega.tests.tests.testsAPI;

import mailru.nastasiachernega.tests.data.models.GrammarCheckRequestModel;
import mailru.nastasiachernega.tests.data.models.GrammarCheckResponseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static mailru.nastasiachernega.tests.data.specs.GrammarCheckSpec.grammarCheckRequestSpec;
import static mailru.nastasiachernega.tests.data.specs.GrammarCheckSpec.grammarCheckResponseSpec;
import static org.assertj.core.api.Assertions.assertThat;

public class GrammarCheckTests {

    @Test
    void mistakeAutoCorrectTest() {

        GrammarCheckRequestModel requestBody =
                GrammarCheckRequestModel.builder()
                        .language("eng")
                        .text("Software testing is the process of evoluating and verifying that a software product or application does what it's supposed to do.")
                        .build();

        GrammarCheckResponseModel response = given()
                .spec(grammarCheckRequestSpec)
                .body(requestBody)
                .when()
                .post()
                .then()
                .spec(grammarCheckResponseSpec)
                .extract().as(GrammarCheckResponseModel.class);

        assertThat(response.getCorrections().get(0).getGroup()).
                isEqualTo("AutoCorrected");
        assertThat(response.getCorrections().get(0).getShortDescription()).
                isEqualTo("Spelling Mistake");
        assertThat(response.getCorrections().get(0).getLongDescription()).
                isEqualTo("A word was not spelled correctly");
        assertThat(response.getCorrections().get(0).getMistakeText()).
                isEqualTo("evoluating");
        assertThat(response.getCorrections().get(0).getCorrectionText()).
                isEqualTo("evaluating");
    }

    @CsvSource(value = {
            "1, evaluating, 'evaluate or estimate the nature, quality, ability, extent, or significance of'",
            "2, evolution, 'a process in which something passes by degrees to a different stage (especially a more advanced or mature stage)'",
            "3, evaluation, 'act of ascertaining or fixing the value or worth of'"
    })
    @ParameterizedTest
    void suggestionsForMistakeAutoCorrectTest(int numberOfSuggestion,
                                                  String suggestedText,
                                                  String definition) {

        GrammarCheckRequestModel requestBody =
                GrammarCheckRequestModel.builder()
                        .language("eng")
                        .text("Software testing is the process of evoluating and verifying that a software product or application does what it's supposed to do.")
                        .build();

        GrammarCheckResponseModel response = given()
                .spec(grammarCheckRequestSpec)
                .body(requestBody)
                .when()
                .post()
                .then()
                .spec(grammarCheckResponseSpec)
                .extract().as(GrammarCheckResponseModel.class);

        assertThat(response.getCorrections().get(0).getSuggestions().get(numberOfSuggestion - 1).
                getText()).isEqualTo(suggestedText);
        assertThat(response.getCorrections().get(0).getSuggestions().get(numberOfSuggestion - 1).
                getDefinition()).isEqualTo(definition);
    }
}
