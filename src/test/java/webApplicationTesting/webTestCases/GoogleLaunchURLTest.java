package webApplicationTesting.webTestCases;


import org.testng.annotations.Test;

public class GoogleLaunchURLTest extends WebTestBaseClass {

    @Test
    public void LaunchGoogleURL(){
        driver.get(GoogleURL);
        System.out.println("Launch test environment URL: " + GoogleURL);
        System.out.println("Title of URL is : " + driver.getTitle());
    }


}
