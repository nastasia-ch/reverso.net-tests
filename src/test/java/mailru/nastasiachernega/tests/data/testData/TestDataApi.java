package mailru.nastasiachernega.tests.data.testData;

import mailru.nastasiachernega.tests.config.LoginConfig;
import mailru.nastasiachernega.tests.utils.GetContextExampleUtils;
import org.aeonbits.owner.ConfigFactory;

import java.util.concurrent.ThreadLocalRandom;

public class TestDataApi {

    ThreadLocalRandom random = ThreadLocalRandom.current();

    private final GetContextExampleUtils util = new GetContextExampleUtils();
    LoginConfig config = ConfigFactory.create(LoginConfig.class,System.getProperties());

    public String accountURL = "https://account.reverso.net/Account/Login",
            email = config.getEmail(),
            password = config.getPassword(),
            returnURL = "https://www.reverso.net/text-translation";

    public int exampleNumber = random.nextInt(1,21);
    public String languageFromTo = "english-russian",
            textForTranslation = "quality assurance",
            srcContext = util.getSrcContext(languageFromTo,textForTranslation,exampleNumber),
            srcLang = "en",
            srcText = util.getSrcText(languageFromTo,textForTranslation,exampleNumber),
            trgContext = util.getTrgContext(languageFromTo,textForTranslation,exampleNumber),
            trgLang = "ru",
            trgText = util.getTrgText(languageFromTo,textForTranslation,exampleNumber);

    public String[] translations = {"обеспечение качества", "гарантия качества",
            "контроль качества", "качество"};

    public String commentText = "Good example";


}
