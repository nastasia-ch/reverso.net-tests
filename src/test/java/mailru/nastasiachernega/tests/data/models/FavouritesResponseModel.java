package mailru.nastasiachernega.tests.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FavouritesResponseModel {

    int id;
    int userID;
    String srcText;
    String trgText;
    String srcContext;
    String trgContext;
    String srcLang;
    String trgLang;
    String creationDate;
    String comment;

}
