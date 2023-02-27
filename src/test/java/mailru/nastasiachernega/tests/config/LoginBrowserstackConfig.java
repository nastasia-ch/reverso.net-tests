package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:mobile/login-browserstack.properties"
})
public interface LoginBrowserstackConfig extends Config {

    @Key("login.username")
    String getUsername();

    @Key("login.password")
    String getPassword();
}
