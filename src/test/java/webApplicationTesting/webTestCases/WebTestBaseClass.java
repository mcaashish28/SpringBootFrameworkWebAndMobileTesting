package webApplicationTesting.webTestCases;

import Utilities.ReadConfig;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class WebTestBaseClass {



    // create object of ReadConfig class
    ReadConfig readConfig = new ReadConfig();

    public String ManulifeURL = readConfig.getManulifeURLURL();
    public String GoogleURL = readConfig.getGoogleURL();

    // WebDriver
    public static WebDriver driver;

    // Logger object
    // public static Logger logger;
    public static String browser = "chrome";

    // Setup method
    //@Parameters("getbrowser")      // take browser name from testng.xml file
    @BeforeSuite
    // public void setup(String browser)
    public void setup(){
        //  log = LogManager.getLogger(BaseClass.class.getName());

      //  PropertyConfigurator.configure("log4j.properties");

        if(browser.equals("chrome")){
            System.setProperty("webdriver.chrome.driver",readConfig.getChromeDriverpath());
            driver = new ChromeDriver();
        }else if(browser.equals("ie")){
            System.setProperty("webdriver.ie.driver",readConfig.getIEDriverpath());
            driver = new InternetExplorerDriver();
        }else if(browser.equals("firefox")){
            System.setProperty("webdriver.gecko.driver",readConfig.getFirefoxDriverpath());
            driver = new FirefoxDriver();
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
