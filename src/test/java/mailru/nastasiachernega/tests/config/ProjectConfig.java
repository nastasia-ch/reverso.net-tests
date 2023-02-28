package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:project.properties"
})
public interface ProjectConfig extends Config {

    @Key("project.baseURL")
    String getBaseURL();

    @Key("project.appURLBrowserstack")
    String getAppURLBrowserstack();

    @Key("project.appURLFile")
    String getAppURLFile();

    @Key("project.appPackageName")
    String getAppPackageName();

    @Key("project.appActivity")
    String getAppActivity();

    @Key("project.appPathToSave")
    String getAppPathToSave();

}
