package mobilePages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MobileBase {

    public static AndroidDriver<MobileElement> androidDriver = null;

    public void configure() throws MalformedURLException {

        AppiumDriverLocalService service = null;
        String nodepath = "/usr/local/bin/node";
        String appiumpath = "/usr/local/bin/appium";

        String ipAddress = System.getenv("ip"); //the ip address on which appium is gonna run localhost/some vm
        int portNumber = Integer.parseInt(System.getenv("port")); // default port for appium is 4723

        String deviceID = System.getenv("deviceID"); // give the emulator/real device name like Nexus 6
        String device = System.getenv("device"); //do `adb devices` on cmd prompt and get the value

        //this starts the appium as a service ~ Non GUI mode
        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File(nodepath)).withAppiumJS(new File(appiumpath)).withIPAddress(ipAddress)
                .usingPort(portNumber).withArgument(GeneralServerFlag.ROBOT_ADDRESS, deviceID)
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, "8080")
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE));
        service.start();

        URL serverAddress = new URL("http://" + ipAddress + ":" + portNumber + "/wd/hub");

        //set the capabilities of your phone and app
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium-version", "1.8.1");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("noSign", true);
        capabilities.setCapability(MobileCapabilityType.UDID, device);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        capabilities.setCapability("appPackage", System.getenv("facebook_appPackage"));
        capabilities.setCapability("appActivity", System.getenv("facebook_appActivity"));

        String appPath = System.getProperty("user.dir") + System.getenv("facebook_app");
        File app = new File(appPath);
        capabilities.setCapability("app", app.getAbsolutePath());

        androidDriver = new AndroidDriver<MobileElement>(serverAddress, capabilities);
        androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        androidDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
    }

    public boolean checkElementIsDisplayed(MobileElement me) {
        WebDriverWait wait = new WebDriverWait(androidDriver, 60);
        wait.until(ExpectedConditions.visibilityOf(me));
        return me.isDisplayed();
    }
}

