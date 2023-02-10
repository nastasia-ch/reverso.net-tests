package mailru.nastasiachernega.tests.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextTranslationResponseModel {

    private String from, to;
    private List<String> input;
    private List<String> translation;

}