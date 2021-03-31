package page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import test.utils.BaseTest;

public class LoginPage extends BaseTest {
	
	
	
	By username = By.id("user_login");
	By password = By.id("user_pass");
	By loginButton = By.id("wp-submit");
	
	
	public void typeUserName(String usernameText)
	{
		driver.findElement(username).sendKeys(usernameText);
		
	}
	
	public void typePassword(String passwordText)
	{
		driver.findElement(password).sendKeys(passwordText);
		
	}
	
	public void clickLoginButton()
	{
		driver.findElement(loginButton).click();
	}

}
