package webApplicationTesting.pageObjectModel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import webApplicationTesting.webTestCases.WebTestBaseClass;

public class LoginPage extends WebTestBaseClass {

    // constructor to initialize object repository with driver object
    public LoginPage()
    {
        PageFactory.initElements(driver, this);
    }

    // Page Factory - OR
    @FindBy(xpath="//input[@id='username']")
    WebElement username;

    public WebElement getUsername() {
        return username;
    }

    public void setUsername(WebElement username) {
        this.username = username;
    }
}
