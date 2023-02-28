package mailru.nastasiachernega.tests.drivers;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import mailru.nastasiachernega.tests.config.MobileConfig;
import mailru.nastasiachernega.tests.config.ProjectConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;


public class EmulatorDriver implements WebDriverProvider {

    static MobileConfig mobileConfig =
            ConfigFactory.create(MobileConfig.class, System.getProperties());
    static ProjectConfig projectConfig =
            ConfigFactory.create(ProjectConfig.class, System.getProperties());

    public static URL getAppiumServerUrl() {
        try {
            return new URL(mobileConfig.getRemoteURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);
        options.setAutomationName(ANDROID_UIAUTOMATOR2)
                .setPlatformName(mobileConfig.getPlatform())
                .setDeviceName(mobileConfig.getDevice())
                .setPlatformVersion(mobileConfig.getOsVersion())
                .setApp(getAppPath())
                .setAppPackage(projectConfig.getAppPackageName())
                .setAppActivity(projectConfig.getAppActivity());
        return new AndroidDriver(getAppiumServerUrl(), options);
    }

    private String getAppPath() {
        String appUrl = projectConfig.getAppURLFile();
        String appPath = projectConfig.getAppPathToSave();
        File app = new File(appPath);
        if (!app.exists()) {
            try (InputStream in = new URL(appUrl).openStream()) {
                copyInputStreamToFile(in, app);
            } catch (IOException e) {
                throw new AssertionError("Failed to download application", e);
            }
        }
        return app.getAbsolutePath();
    }
}
