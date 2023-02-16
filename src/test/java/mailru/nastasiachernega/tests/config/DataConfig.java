package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

import java.util.LinkedList;

@Config.Sources({
        "classpath:datafile.properties"
})
public interface DataConfig extends Config {

    @Config.Key("datafile.text")
    String getText();

    @Config.Key("datafile.languageFrom")
    String getLanguageFrom();

    @Config.Key("datafile.languageTo")
    String getLanguageTo();

    @Config.Separator(" / ")
    @Config.Key("datafile.translations")
    LinkedList<String> getTranslations();

    @Config.Separator(" / ")
    @Config.Key("datafile.examples")
    LinkedList<String> getExamples();

    @Config.Separator(" / ")
    @Config.Key("datafile.translatedExamples")
    LinkedList<String> getTranslatedExamples();

    @Config.Separator(" / ")
    @Config.Key("datafile.contextTranslations")
    LinkedList<String> getContextTranslations();

    @Config.Key("datafile.langFromSymbol")
    String getLangFromSymbol();

    @Config.Key("datafile.langToSymbol")
    String getLangToSymbol();

    @Config.Separator(" / ")
    @Config.Key("datafile.examplesWithTags")
    LinkedList<String> getExamplesWithTags();

    @Config.Separator(" / ")
    @Config.Key("datafile.translatedExamplesWithTags")
    LinkedList<String> getTranslatedExamplesWithTags();


}
