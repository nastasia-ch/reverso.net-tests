package mailru.nastasiachernega.tests.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextTranslationResponseModel {

    private String from, to;
    private List<String> input;
    private ContextResult contextResults;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContextResult {
        private List<Result> results;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Result {
            private String translation, transliteration;
        }

    }

}
