package mailru.nastasiachernega.tests.config;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

import com.codeborne.selenide.Configuration;

public class ProjectProvider {

    @BeforeAll
    static void setUp() {

        ProjectConfig config = ConfigFactory.create(ProjectConfig.class,System.getProperties());

        Configuration.baseUrl = config.getBaseUrlUi();

    }



}
