package mailru.nastasiachernega.tests.data.testData;

import com.github.javafaker.Faker;
import mailru.nastasiachernega.tests.config.LoginConfig;
import mailru.nastasiachernega.tests.utils.XlsReader;
import org.aeonbits.owner.ConfigFactory;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class TestData {

    XlsReader xlsReader = new XlsReader();
    ThreadLocalRandom random = ThreadLocalRandom.current();
    Faker faker = new Faker(new Locale("en"));

    LoginConfig config = ConfigFactory.create(LoginConfig.class,System.getProperties());

    public final String accountURL = "https://account.reverso.net/Account/Login",
            returnURL = "https://www.reverso.net/text-translation",
            emailValid = config.getEmail(),
            passwordValid = config.getPassword(),
            username = config.getUsername(),
            emailInvalid = faker.internet().emailAddress(),
            passwordInvalid = faker.internet().password(1,10),
            errorLoginInfo = "Your login information was incorrect. Please try again.";

    public final int userID = config.getUserID();

    public String translationPath = "/translation",
                  historyPath = "/history",
                  favouritesPath = "/favourites";

    public String[] reversoHeaders = {"Translation","Context","Grammar Check","Synonyms","Conjugation"};

    public int exampleNumber = random.nextInt(0,21);

    public String languageFrom = "english",
            languageTo = "russian",
            textToTranslate = "quality assurance";

    public String[] translations = {"обеспечение качества", "гарантия качества",
            "контроль качества", "качество", "обеспечения качества", "обеспечению качества", "гарантии качества", "контроля качества"};

    public String commentText = "Good example";


    public String getText() throws Exception {
        return xlsReader.getCellContent("Examples.xlsx", exampleNumber, 0);
        }

    public String getTranslatedText() throws Exception {
        return xlsReader.getCellContent("Examples.xlsx", exampleNumber, 1);
    }

    public String getHTMLText() throws Exception {
        return xlsReader.getCellContent("Examples.xlsx", exampleNumber, 2);
    }

    public String getHTMLTranslatedText() throws Exception {
        return xlsReader.getCellContent("Examples.xlsx", exampleNumber, 3);
    }

    public String getTranslation() throws Exception {
        return xlsReader.getCellContent("Examples.xlsx", exampleNumber, 4);
    }


    public String languageFromTo = languageFrom + "-" + languageTo,
                  langFromSymbol = "en",
                  langToSymbol = "ru";

}
