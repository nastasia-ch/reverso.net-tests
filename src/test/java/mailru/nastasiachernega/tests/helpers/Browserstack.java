package mailru.nastasiachernega.tests.helpers;

import mailru.nastasiachernega.tests.config.LoginBrowserstackConfig;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class Browserstack {
    static LoginBrowserstackConfig loginConfig =
            ConfigFactory.create(LoginBrowserstackConfig.class, System.getProperties());

    public static String getVideoUrlBrowserstack(String sessionId) {

        String url = format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);

        return given()
                .log().all()
                .auth().basic(loginConfig.getUsername(), loginConfig.getPassword())
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }
}
