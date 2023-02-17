package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

import java.util.LinkedList;

public class DataProvider {

    DataConfig dataConfig = ConfigFactory.create(DataConfig.class,System.getProperties());

    @BeforeAll
    void setUp() {
        System.getProperty("testdata");
    }

    public String getText() {
        return dataConfig.getText();
    }

    public String getLanguageFrom() {
        return dataConfig.getLanguageFrom();
    }

    public String getLanguageTo() {
        return dataConfig.getLanguageTo();
    }

    public LinkedList<String> getTranslations() {
        return dataConfig.getTranslations();
    }

    public LinkedList<String> getExamples() {
        return dataConfig.getExamples();
    }

    public LinkedList<String> getTranslatedExamples() {
        return dataConfig.getTranslatedExamples();
    }

    public LinkedList<String> getContextTranslations() {
        return dataConfig.getContextTranslations();
    }

    public String getLangFromSymbol() {
        return dataConfig.getLangFromSymbol();
    }

    public String getLangToSymbol() {
        return dataConfig.getLangToSymbol();
    }


}
