package mobileApplicationTesting.mobileUtilityClasses;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class IPhoneSimulator_SouceLabDesiredCapability {
    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();

        dc.setCapability("PlatformName", "iOS");     // imp
        dc.setCapability("PlatformVersion", "7.1");        // imp for mac os

        dc.setCapability("appiumVersion", "1.3.4"); // imp
        dc.setCapability("deviceName", "iPhone Simulator"); // imp
        dc.setCapability("browserName", ""); // imp
        dc.setCapability("app", "sauce-storage:myapp.app.zip"); // imp
        dc.setCapability("deviceName", "iPhone Simulator"); // imp

        IOSDriver driver = new IOSDriver(new URL("http://username:Accesskey@ondemand.saucelabs.com:80/wd/hub"), dc);  // appium server url
        // Simulator will be launched and app will be displayed
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
