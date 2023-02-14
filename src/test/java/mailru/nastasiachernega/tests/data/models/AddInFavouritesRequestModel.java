package mailru.nastasiachernega.tests.data.models;

import lombok.Data;

@Data
public class AddInFavouritesRequestModel {

    private String srcContext;
    private String srcLang;
    private String srcText;
    private String trgContext;
    private String trgLang;
    private String trgText;

}
