package mailru.nastasiachernega.tests.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrammarCheckResponseModel {

    String language;
    String text;
    List<Correction> corrections;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Correction {

        String group;
        String type;
        String shortDescription;
        String longDescription;
        int startIndex;
        int endIndex;
        String mistakeText;
        String mistakeDefinition;
        String correctionText;
        String correctionDefinition;
        List<Suggestion> suggestions;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Suggestion {

            String text;
            String definition;

        }

    }





}
