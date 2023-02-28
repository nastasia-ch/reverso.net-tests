package mailru.nastasiachernega.tests.drivers;

import com.codeborne.selenide.WebDriverProvider;
import lombok.SneakyThrows;
import mailru.nastasiachernega.tests.config.LoginBrowserstackConfig;
import mailru.nastasiachernega.tests.config.MobileConfig;
import mailru.nastasiachernega.tests.config.ProjectConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserstackDriver implements WebDriverProvider {

    static LoginBrowserstackConfig loginBrowserstackConfig =
            ConfigFactory.create(LoginBrowserstackConfig.class, System.getProperties());
    static MobileConfig mobileConfig =
            ConfigFactory.create(MobileConfig.class, System.getProperties());
    static ProjectConfig projectConfig =
            ConfigFactory.create(ProjectConfig.class, System.getProperties());

    public static URL getBrowserstackUrl() {
        try {
            return new URL(mobileConfig.getRemoteURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.merge(capabilities);
        mutableCapabilities.setCapability("browserstack.user", loginBrowserstackConfig.getUsername());
        mutableCapabilities.setCapability("browserstack.key", loginBrowserstackConfig.getPassword());
        mutableCapabilities.setCapability("app", projectConfig.getAppURLBrowserstack());
        mutableCapabilities.setCapability("device", mobileConfig.getDevice());
        mutableCapabilities.setCapability("os_version", mobileConfig.getOsVersion());
        mutableCapabilities.setCapability("project", "reverso.net");
        mutableCapabilities.setCapability("build", "browserstack-build");
        mutableCapabilities.setCapability("name", "tests");
        return new RemoteWebDriver(getBrowserstackUrl(), mutableCapabilities);
    }
}