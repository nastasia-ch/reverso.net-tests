package mailru.nastasiachernega.tests.data.testData;

import mailru.nastasiachernega.tests.config.LoginConfig;
import mailru.nastasiachernega.tests.utils.GetContextExample;
import org.aeonbits.owner.ConfigFactory;

public class TestDataApi {

    private final GetContextExample util = new GetContextExample();
    LoginConfig config = ConfigFactory.create(LoginConfig.class,System.getProperties());

    public String accountURL = "https://account.reverso.net/Account/Login",
            email = config.getEmail(),
            password = config.getPassword(),
            returnURL = "https://www.reverso.net/text-translation";

    public int exampleNumber = 2;
    public String languageFromTo = "english-russian",
            textForTranslation = "automation engineer",
            srcContext = util.getSrcContext(languageFromTo,textForTranslation,exampleNumber),
            srcLang = "en",
            srcText = util.getSrcText(languageFromTo,textForTranslation,exampleNumber),
            trgContext = util.getTrgContext(languageFromTo,textForTranslation,exampleNumber),
            trgLang = "ru",
            trgText = util.getTrgText(languageFromTo,textForTranslation,exampleNumber);


}
