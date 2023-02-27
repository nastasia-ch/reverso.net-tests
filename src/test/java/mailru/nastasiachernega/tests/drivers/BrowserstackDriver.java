package mailru.nastasiachernega.tests.drivers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import lombok.SneakyThrows;
import mailru.nastasiachernega.tests.config.LoginBrowserstackConfig;
import mailru.nastasiachernega.tests.config.MobileConfig;
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

    public static URL getBrowserstackUrl() {
        System.getProperty("environmentMobile");
        try {
            return new URL(mobileConfig.getRemoteURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        System.getProperty("environmentMobile");
        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.merge(capabilities);
        mutableCapabilities.setCapability("browserstack.user", loginBrowserstackConfig.getUsername());
        mutableCapabilities.setCapability("browserstack.key", loginBrowserstackConfig.getPassword());
        mutableCapabilities.setCapability("app", mobileConfig.getAppURL());
        mutableCapabilities.setCapability("device", mobileConfig.getDevice());
        mutableCapabilities.setCapability("os_version", mobileConfig.getOsVersion());
        mutableCapabilities.setCapability("project", "First Java Project");
        mutableCapabilities.setCapability("build", "browserstack-build-1");
        mutableCapabilities.setCapability("name", "first_test");
        return new RemoteWebDriver(getBrowserstackUrl(), mutableCapabilities);
    }
}