package mobileApplicationTesting.mobileTestScripts;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import webApplicationTesting.webTestCases.WebTestBaseClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MobileTestBaseClass {

    public static IOSDriver<IOSElement> mobileDriver;
    public static final Logger log = LoggerFactory.getLogger(WebTestBaseClass.class);

    @BeforeSuite
    public static IOSDriver<IOSElement> setupMobileDesiredCapabilities() throws MalformedURLException {

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        dc.setCapability(MobileCapabilityType.APP, "// Path of mobile app//name.app");

        IOSDriver<IOSElement> mobileDriver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
        mobileDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return mobileDriver;
    }


    @AfterSuite
    public void tearDownMobileDriver(){
        mobileDriver.quit();
        mobileDriver = null;
    }

}
