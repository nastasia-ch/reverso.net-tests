package mailru.nastasiachernega.tests.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddInFavouritesResponseModel {

    int id;
    int userID;
    String srcText;
    String trgText;
    String srcContext;
    String trgContext;
    String srcLang;
    String trgLang;
    String creationDate;

}
