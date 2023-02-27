package mailru.nastasiachernega.tests.data.testData;

import com.github.javafaker.Faker;
import mailru.nastasiachernega.tests.config.LoginReversoConfig;
import mailru.nastasiachernega.tests.utils.DateUtil;
import org.aeonbits.owner.ConfigFactory;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class TestData extends DataProvider {

    ThreadLocalRandom random = ThreadLocalRandom.current();
    Faker faker = new Faker(new Locale("en"));
    LoginReversoConfig loginConfig = ConfigFactory.create(LoginReversoConfig.class, System.getProperties());
    DateUtil dateUtil = new DateUtil();

    // login test data
    public final String
            emailValid = loginConfig.getEmail(),
            passwordValid = loginConfig.getPassword(),
            username = loginConfig.getUsername(),
            emailInvalid = faker.internet().emailAddress(),
            passwordInvalid = faker.internet().password(1, 8),
            errorLoginInfo = "Your login information was incorrect. Please try again.";

    public int userID = loginConfig.getUserID();

    // test data for actions with translations
    public String[] reversoHeaders = {"Translation", "Context", "Grammar Check", "Synonyms", "Conjugation"};

    public int exampleNumber = random.nextInt(0, 20);

    public String
            translationPath = "/translation",
            historyPath = "/history",
            favouritesPath = "/favourites",
            text = getText(),
            languageFrom = getLanguageFrom(),
            languageTo = getLanguageTo(),
            commentText = "Good example",
            currentDate = dateUtil.getCurrentDate();

    public String[] translations = getTranslations();

    public String
            example = getExamples().get(exampleNumber).
            replace("<em>", "").replace("</em>", ""),
            translatedExample = getTranslatedExamples().get(exampleNumber).
                    replace("<em>", "").replace("</em>", ""),
            contextTranslation = getContextTranslations().get(exampleNumber);

    // special test data for API testing favourites
    public String
            languageFromTo = languageFrom + "-" + languageTo,
            langFromSymbol = getLangFromSymbol(),
            langToSymbol = getLangToSymbol(),
            exampleWithTags = getExamples().get(exampleNumber),
            translatedExampleWithTags = getTranslatedExamples().get(exampleNumber);

    // special test data for mobile testing
    public int exampleNumberMobile = random.nextInt(0, 1);
    public String
            exampleMobile = getExamples().get(exampleNumberMobile).
            replace("<em>", "").replace("</em>", ""),
            translatedExampleMobile = getTranslatedExamples().get(exampleNumberMobile).
                    replace("<em>", "").replace("</em>", "");
}
