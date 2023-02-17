package mailru.nastasiachernega.tests.data.testData;

import com.github.javafaker.Faker;
import mailru.nastasiachernega.tests.config.DataProvider;
import mailru.nastasiachernega.tests.config.LoginConfig;
import org.aeonbits.owner.ConfigFactory;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class TestData extends DataProvider {

    ThreadLocalRandom random = ThreadLocalRandom.current();
    Faker faker = new Faker(new Locale("en"));
    LoginConfig loginConfig = ConfigFactory.create(LoginConfig.class,System.getProperties());

    // login test data
    public final String
            emailValid = loginConfig.getEmail(),
            passwordValid = loginConfig.getPassword(),
            username = loginConfig.getUsername(),
            emailInvalid = faker.internet().emailAddress(),
            passwordInvalid = faker.internet().password(1,8),
            errorLoginInfo = "Your login information was incorrect. Please try again.";

    public final int userID = loginConfig.getUserID();

    // test data for actions with translations
    public String[] reversoHeaders = {"Translation","Context","Grammar Check","Synonyms","Conjugation"};

    public int exampleNumber = random.nextInt(0,20);

    public String
            translationPath = "/translation",
            historyPath = "/history",
            favouritesPath = "/favourites",
            text = getText(),
            languageFrom = getLanguageFrom(),
            languageTo = getLanguageTo(),
            commentText = "Good example";

    public List<String> translations = getTranslations();

    public String
            example = getExamples().get(exampleNumber).
                    replace("<em>","").replace("</em>",""),
            translatedExample = getTranslatedExamples().get(exampleNumber).
                    replace("<em>","").replace("</em>",""),
            contextTranslation = getContextTranslations().get(exampleNumber);

    // special test data for API testing favourites
    public String
            languageFromTo = languageFrom + "-" + languageTo,
            langFromSymbol = getLangFromSymbol(),
            langToSymbol = getLangToSymbol(),
            exampleWithTags = getExamples().get(exampleNumber),
            translatedExampleWithTags = getTranslatedExamples().get(exampleNumber);


}
