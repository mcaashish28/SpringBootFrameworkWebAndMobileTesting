package mobileApplicationTesting.mobileUtilityClasses;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ScrollDownInIOSApp {

    IOSDriver driver;

    // Generic Code to sroll down in IOS App. Just copy paste at desired location this piece of code
    public void genericScrollDownCodeForIOSApp() throws MalformedURLException {

        DesiredCapabilities dc  =new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
        // for IOS version > 10.2
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST); // imp
        dc.setCapability(MobileCapabilityType.APP, "//PathtoAppFolder//MobileApp.app");
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), dc);  // appium server url

        // Generic Code to sroll down in IOS App. Just copy paste at desired location this piece of code - start

        TouchAction action = new TouchAction(driver);
        PointOption p1= new PointOption();
        Dimension dimensions = driver.manage().window().getSize();
        Double screenHeightStart = dimensions.getHeight() * 0.5;
        int h1 = screenHeightStart.intValue();
        Double screenHeightEnd = dimensions.getHeight() * 0.2;
        int h2 = screenHeightEnd.intValue();
        action.press(p1.point(0, h1)).moveTo(p1.point(0,-h2)).release().perform();

        /* --
        Dimension size = driver.manage().window().getSize();
        int x = size.getWidth() / 2;
        int starty = (int) (size.getHeight() * 0.60);
        int endy = (int) (size.getHeight() * 0.10);
        driver.swipe(x, starty, x, endy, 2000);
        // click on visible object
        driver.findElementByAccessibilityId("NameofObject").click();
        */

        // Generic Code to sroll down in IOS App. Just copy paste at desired location this piece of code - end
    }

}
