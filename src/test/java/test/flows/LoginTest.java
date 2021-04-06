package test.flows;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import page.objects.LoginPage;
import test.utils.BaseTest;
import util.exception.NoMatchingNameException;
import util.reader.excel.ExcelUtil;

public class LoginTest {
	LoginPage loginPage = new LoginPage();
	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/testDataSheet.xlsx");
	
	@DataProvider(name = "readDataFromExcel")
	public Object[][] getExceldataFromSheet(Method method) throws Exception
	{
		Object[][] testObjArray = excelUtil.getAllMatchingTestCases("sheetname", method.getName());
		return (testObjArray);
	}

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
