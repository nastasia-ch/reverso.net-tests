package mailru.nastasiachernega.tests.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GrammarCheckRequestModel {

    @Builder.Default
    boolean IsUserPremium = false;
    @Builder.Default
    boolean autoReplace = true;
    @Builder.Default
    String englishDialect = "indifferent";
    @Builder.Default
    boolean getCorrectionDetails = true;
    @Builder.Default
    String interfaceLanguage = "en";
    @Builder.Default
    boolean isHtml = false;
    String language;
    @Builder.Default
    String locale = "";
    @Builder.Default
    String origin = "interactive";
    @Builder.Default
    String originalText = "";
    @Builder.Default
    SpellingFeedbackOptions spellingFeedbackOptions = SpellingFeedbackOptions.builder().build();
    String text;

    @Data
    @Builder
    public static class SpellingFeedbackOptions {
        @Builder.Default
        boolean insertFeedback = true;
        @Builder.Default
        boolean userLoggedOn = false;
    }




}
