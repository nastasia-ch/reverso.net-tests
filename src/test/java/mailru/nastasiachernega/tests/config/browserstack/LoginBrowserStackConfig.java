package mailru.nastasiachernega.tests.config.browserstack;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:browserstack/login-browserstack.properties"
})
public interface LoginBrowserStackConfig extends Config {

    @Key("login.username")
    String getUsername();

    @Key("login.password")
    String getPassword();
}
