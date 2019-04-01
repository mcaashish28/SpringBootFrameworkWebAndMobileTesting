package webApplicationTesting.webTestCases;



import org.testng.annotations.Test;

public class GoogleLaunchURLTest extends WebTestBaseClass {

    @Test
    public void LaunchGoogleURL() throws Exception {
        driver.get(GoogleURL);
      //  log.info("Google URL is Launched");



        System.out.println("Launch test environment URL: " + GoogleURL);
        System.out.println("Title of URL is : " + driver.getTitle());
    }


}
