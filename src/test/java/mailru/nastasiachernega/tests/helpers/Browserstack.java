package mailru.nastasiachernega.tests.helpers;

import mailru.nastasiachernega.tests.config.browserstack.LoginBrowserStackConfig;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static mailru.nastasiachernega.tests.helpers.CustomApiListener.withCustomTemplates;

public class Browserstack {
    static LoginBrowserStackConfig browserStackLoginConfig =
            ConfigFactory.create(LoginBrowserStackConfig.class, System.getProperties());

    public static String getVideoUrlBrowserstack(String sessionId) {

        String url = format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);

        return given()
                .log().all()
                .filter(withCustomTemplates())
                .auth().basic(browserStackLoginConfig.getUsername(), browserStackLoginConfig.getPassword())
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }
}
