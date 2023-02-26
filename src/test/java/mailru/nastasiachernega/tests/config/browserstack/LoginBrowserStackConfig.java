package mailru.nastasiachernega.tests.config.browserstack;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:browserstack/login-google-pixel-3-v9.0-remote.properties"
})
public interface LoginBrowserStackConfig extends Config {

    @Key("login.username")
    String getUsername();

    @Key("login.password")
    String getPassword();
}
