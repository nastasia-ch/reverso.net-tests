package mailru.nastasiachernega.tests.data.testData;

import com.github.javafaker.Faker;
import mailru.nastasiachernega.tests.config.DataConfig;
import mailru.nastasiachernega.tests.config.LoginConfig;
import mailru.nastasiachernega.tests.data.apiSteps.AuthorizationApiSteps;
import org.aeonbits.owner.ConfigFactory;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class TestData {

    ThreadLocalRandom random = ThreadLocalRandom.current();
    Faker faker = new Faker(new Locale("en"));

    LoginConfig loginConfig = ConfigFactory.create(LoginConfig.class,System.getProperties());
    DataConfig dataConfig = ConfigFactory.create(DataConfig.class,System.getProperties());

    public final String
            emailValid = loginConfig.getEmail(),
            passwordValid = loginConfig.getPassword(),
            username = loginConfig.getUsername(),
            emailInvalid = faker.internet().emailAddress(),
            passwordInvalid = faker.internet().password(1,8),
            errorLoginInfo = "Your login information was incorrect. Please try again.";

    public final int userID = loginConfig.getUserID();

    public String translationPath = "/translation",
                  historyPath = "/history",
                  favouritesPath = "/favourites";

    public String[] reversoHeaders = {"Translation","Context","Grammar Check","Synonyms","Conjugation"};

    public int exampleNumber = random.nextInt(0,20);

    public String
            textToTranslate = dataConfig.getText(),
            languageFrom = dataConfig.getLanguageFrom(),
            languageTo = dataConfig.getLanguageTo();

    public List<String> translations = dataConfig.getTranslations();

    public String
            example = dataConfig.getExamples().get(exampleNumber),
            translatedExample = dataConfig.getTranslatedExamples().get(exampleNumber),
            contextTranslation = dataConfig.getContextTranslations().get(exampleNumber);

    public String commentText = "Good example";

    // special test data for API testing
    public String
            languageFromTo = languageFrom + "-" + languageTo,
            langFromSymbol = dataConfig.getLangFromSymbol(),
            langToSymbol = dataConfig.getLangToSymbol(),
            exampleWithTags = dataConfig.getExamplesWithTags().get(exampleNumber),
            translatedExampleWithTags = dataConfig.getTranslatedExamplesWithTags().get(exampleNumber);



}
