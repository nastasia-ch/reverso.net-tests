package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:testdata/login-reverso.properties"
})
public interface LoginReversoConfig extends Config {

    @Key("login.email")
    String getEmail();

    @Key("login.password")
    String getPassword();

    @Key("login.username")
    String getUsername();

    @Key("login.userID")
    int getUserID();

}
