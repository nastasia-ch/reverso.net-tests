package mailru.nastasiachernega.tests.data.testData;

import com.codeborne.xlstest.XLS;
import com.github.javafaker.Faker;
import mailru.nastasiachernega.tests.config.LoginConfig;
import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class TestData {

    ThreadLocalRandom random = ThreadLocalRandom.current();
    Faker faker = new Faker(new Locale("en"));

    LoginConfig config = ConfigFactory.create(LoginConfig.class,System.getProperties());

    public final String accountURL = "https://account.reverso.net/Account/Login",
            returnURL = "https://www.reverso.net/text-translation",
            emailValid = config.getEmail(),
            username = config.getUsername(),
            passwordValid = config.getPassword(),
            emailInvalid = faker.internet().emailAddress(),
            passwordInvalid = faker.internet().password(1,10),
            errorLoginInfo = "Your login information was incorrect. Please try again.";

    public String translationPath = "/translation",
                  historyPath = "/history";

    public String[] reversoHeaders = {"Translation","Context","Grammar Check","Synonyms","Conjugation"};

    public int exampleNumber = random.nextInt(1,21);

    public String languageFrom = "english",
            languageTo = "russian",
            textForTranslation = "quality assurance";

    public String[] translations = {"обеспечение качества", "гарантия качества",
            "контроль качества", "качество"};

    public String commentText = "Good example";

    public String getExampleText(int exampleNumber) throws IOException {
        ClassLoader cl = TestData.class.getClassLoader();
        try (InputStream is = cl.getResourceAsStream("Examples.xlsx")) {
            XLS xls = new XLS(is);
            return xls.excel.getSheetAt(0).getRow(exampleNumber + 1).getCell(0).
                getStringCellValue();
        }
    }

    public String getExampleTranslatedText(int exampleNumber) throws IOException {
        ClassLoader cl = TestData.class.getClassLoader();
        try (InputStream is = cl.getResourceAsStream("Examples.xlsx")) {
            XLS xls = new XLS(is);
            return xls.excel.getSheetAt(0).getRow(exampleNumber + 1).getCell(1).
                    getStringCellValue();
        }
    }

    public String languageFromTo = languageFrom + "-" + languageTo;

}
