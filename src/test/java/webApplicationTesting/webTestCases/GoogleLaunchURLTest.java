package webApplicationTesting.webTestCases;



import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class GoogleLaunchURLTest extends WebTestBaseClass {

    @Test
    public void LaunchGoogleURL() throws Exception {
        driver.get(GoogleURL);
        driver.findElement(By.name("a")).sendKeys("Hello");
      //  log.info("Google URL is Launched");
        WebTestBaseClass.getScreenShot("Test");


        System.out.println("Launch test environment URL: " + GoogleURL);
        System.out.println("Title of URL is : " + driver.getTitle());
    }


}
