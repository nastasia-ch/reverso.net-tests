package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

import java.util.LinkedList;

@Config.Sources({
        "classpath:${testdata}.properties",
        "classpath:data-quality-assurance.properties"
})
public interface DataConfig extends Config {

    @Config.Key("data.text")
    String getText();

    @Config.Key("data.languageFrom")
    String getLanguageFrom();

    @Config.Key("data.languageTo")
    String getLanguageTo();

    @Config.Key("data.langFromSymbol")
    String getLangFromSymbol();

    @Config.Key("data.langToSymbol")
    String getLangToSymbol();

    @Config.Separator(" / ")
    @Config.Key("data.translations")
    LinkedList<String> getTranslations();

    @Config.Separator(" / ")
    @Config.Key("data.examples")
    LinkedList<String> getExamples();

    @Config.Separator(" / ")
    @Config.Key("data.translatedExamples")
    LinkedList<String> getTranslatedExamples();

    @Config.Separator(" / ")
    @Config.Key("data.contextTranslations")
    LinkedList<String> getContextTranslations();

}
