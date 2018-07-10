package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);	
	}

	//Using @FindBy specify a way to locate the webelement
	@FindBy(id="ctl00_MainContent_username")
	public WebElement userName;

	@FindBy(id="ctl00_MainContent_password")
	public WebElement password;

	@FindBy(id="ctl00_MainContent_login_button")
	public WebElement loginButton;

	@FindBy(id="ctl00_MainContent_status")
	public WebElement invalidUserNameErrMsg;


	public void login(String id , String pswd) {
		userName.sendKeys(id);
		userName.sendKeys(pswd);
		loginButton.click();
	}
}
