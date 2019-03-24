package mobileApplicationTesting.mobileUtilityClasses;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class StartAndStopAppiumServer {

    private AppiumDriverLocalService service;
    private AppiumServiceBuilder builder;

    String node_js_path = "C:\\Program Files\\nodejs\\node.exe";
    String appium_js_path = "C:\\Program Files\\Appium\\node_modules\\appium\\bin\\appium.js";

    @BeforeClass
    public void appiumInitialization(){
        builder = new AppiumServiceBuilder();
        builder.usingDriverExecutable(new File(node_js_path));
        builder.withAppiumJS(new File(appium_js_path));
        builder.withIPAddress("127.0.0.1");
        builder.usingAnyFreePort();
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "info");

        service = AppiumDriverLocalService.buildService(builder);
    }

    @Test
    public void startAppiumServer(){
        service.start();
        System.out.println("Appium Server is Started. ");
    }

    @AfterClass
    public void stopAppiumServer(){
        service.stop();
        System.out.println("Appium Server is Stopped. ");
    }


}