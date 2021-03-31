package test.flows;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import page.objects.LoginPage;
import test.utils.BaseTest;

public class LoginTest {
	LoginPage loginPage = new LoginPage();
	
	

	@BeforeTest
	public void init()
	{
		BaseTest.initializeWebDriver("Chrome");
		
		
		
	}
	
	@Test
	public void testLogin()
	{
		loginPage.typeUserName("sudip");
		loginPage.typePassword("sudip");
		loginPage.clickLoginButton();
		
	}
	
	@AfterTest
	public void quitDriver()
	{
		BaseTest.closeBrowser();
	}

}
