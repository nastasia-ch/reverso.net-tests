package mailru.nastasiachernega.tests.config.browserstack;

import com.codeborne.selenide.WebDriverProvider;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserStackDriver implements WebDriverProvider {

    static LoginBrowserStackConfig browserStackLoginConfig =
            ConfigFactory.create(LoginBrowserStackConfig.class, System.getProperties());
    static BrowserStackConfig browserStackConfig =
            ConfigFactory.create(BrowserStackConfig.class, System.getProperties());

    public static URL getBrowserstackUrl() {
        try {
            return new URL(browserStackConfig.getBaseURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public WebDriver createDriver(Capabilities capabilities) {

        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.merge(capabilities);

        mutableCapabilities.setCapability("browserstack.user", browserStackLoginConfig.getUsername());
        mutableCapabilities.setCapability("browserstack.key", browserStackLoginConfig.getPassword());

        // Set URL of the application under test
        mutableCapabilities.setCapability("app", browserStackConfig.getApp());

        // Specify device and os_version for testing
        mutableCapabilities.setCapability("device", browserStackConfig.getDevice());
        mutableCapabilities.setCapability("os_version", browserStackConfig.getOsVersion());

        // Set other BrowserStack capabilities
        mutableCapabilities.setCapability("project", "First Java Project");
        mutableCapabilities.setCapability("build", "browserstack-build-1");
        mutableCapabilities.setCapability("name", "first_test");

        // Initialise the remote Webdriver using BrowserStack remote URL
        // and desired capabilities defined above

        return new RemoteWebDriver(getBrowserstackUrl(), mutableCapabilities);
    }
}