package mobileApplicationTesting.mobileTestScripts;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class LaunchAppTest extends MobileTestBaseClass {
    IOSDriver<IOSElement> mobileDriver;
    @Test
    public void LaunchMobileApp() throws MalformedURLException {
        mobileDriver = MobileTestBaseClass.setupMobileDesiredCapabilities();
        mobileDriver.findElementByAccessibilityId("id").click();
        log.info("Click on This ID Element");

    }


}
