package mailru.nastasiachernega.tests.data.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TextTranslationRequestModel {

    @Builder.Default
    private String format = "text";
    private String from, input;
    @Builder.Default
    private Option options = Option.builder().build();
    private String to;

    @Data
    @Builder(toBuilder = true)
    public static class Option {

        @Builder.Default
        private boolean contextResults = true;
        @Builder.Default
        private boolean languageDetection = true;
        @Builder.Default
        private String origin = "translation.web";
        @Builder.Default
        private boolean sentenceSplitter = true;
    }


}
