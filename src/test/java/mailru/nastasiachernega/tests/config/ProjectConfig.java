package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:project.properties"
})
public interface ProjectConfig extends Config {

    @Key("project.baseUrlUi")
    String getBaseUrlUi();

}
