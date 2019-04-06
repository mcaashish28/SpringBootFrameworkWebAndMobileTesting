package mobileApplicationTesting.mobileUtilityClasses;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class IPhoneSimulator_DesiredCapability {
    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities dc  =new DesiredCapabilities();

        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");     // imp
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");        // imp for mac os
        // for IOS version > 10.2
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST); // imp 'XCUITest'
        dc.setCapability(MobileCapabilityType.APP, "//PathtoAppFolder//MobileApp.app"); // imp

        IOSDriver<IOSElement> driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), dc);  // appium server url
        // Simulator will be launched and app will be displayed
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
