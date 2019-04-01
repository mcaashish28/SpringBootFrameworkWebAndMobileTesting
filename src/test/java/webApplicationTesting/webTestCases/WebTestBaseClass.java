package webApplicationTesting.webTestCases;

import Utilities.ReadConfig;

//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class WebTestBaseClass {

    // Log object
   // public static Logger log = Logger.getLogger("WebTestBaseClass");
    public static final Logger log = LoggerFactory.getLogger(WebTestBaseClass.class);

    // create object of ReadConfig class
    ReadConfig readConfig = new ReadConfig();


    public String ManulifeURL = readConfig.getManulifeURLURL();
    public String GoogleURL = readConfig.getGoogleURL();

    // WebDriver
    public static WebDriver driver;


    public static String browser = "chrome";

    // Setup method
    //@Parameters("getbrowser")      // take browser name from testng.xml file
    @BeforeSuite
    // public void setup(String browser)
    public void setup(){


        //  PropertyConfigurator.configure("log4j.properties");

      //  PropertyConfigurator.configure("./src/test/resources/log4j.properties");
       //   log.info("This is first logger Message.");

        if(browser.equals("chrome")){
            System.setProperty("webdriver.chrome.driver",readConfig.getChromeDriverpath());
            driver = new ChromeDriver();
           log.info("Chrome Browser is Launched.");
          //  log.info("Initializing Chrome driver...");
        }else if(browser.equals("ie")){
            System.setProperty("webdriver.ie.driver",readConfig.getIEDriverpath());
            driver = new InternetExplorerDriver();
         //   log.info("IE Browser is Launched.");

        }else if(browser.equals("firefox")){
            System.setProperty("webdriver.gecko.driver",readConfig.getFirefoxDriverpath());
            driver = new FirefoxDriver();
           // log.info("Firefox Browser is Launched.");

        }

       // driver.get(GoogleURL);
       // System.out.println("Launch test environment URL: " + GoogleURL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }


    @AfterSuite
    public void tearDown(){
        // driver.quit();
    }


}
