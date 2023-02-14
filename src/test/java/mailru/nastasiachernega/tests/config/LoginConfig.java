package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:login.properties"
})
public interface LoginConfig extends Config {

    @Key("login.email")
    String getEmail();

    @Key("login.password")
    String getPassword();

    @Key("login.username")
    String getUsername();

    @Key("login.userID")
    int getUserID();

}
