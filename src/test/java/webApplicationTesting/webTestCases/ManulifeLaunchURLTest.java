package webApplicationTesting.webTestCases;


import org.testng.annotations.Test;

public class ManulifeLaunchURLTest extends WebTestBaseClass {

    @Test
    public void LaunchManulifeURL() throws Exception {
        driver.get(ManulifeURL);

        System.out.println("Launch test environment URL: " + ManulifeURL);
        System.out.println("Title of URL is : " + driver.getTitle());
    }
}
