package test.utils;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import util.reader.xml.ConfigXmlReader;

public class BaseTest {
	
	protected static WebDriver driver;
	
	public static String configXmlPathStr = "config/config.xml";
	public static ConfigXmlReader configXmlReader = new ConfigXmlReader(configXmlPathStr);
	public static String defaultEnvironment = configXmlReader.getAttributeValue("configuration.generalSettings.value","defaultEnvironmentName");
	
	public String username = EnvironmentManager.getKeyValue("credentials","username");
	
	
	public BaseTest()
	{
		// Setup property file for config
		
		
	}
	public static void initializeWebDriver(String browsername)
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.touristsdigest.com/?snax_login_popup");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		
	}
	
	public static void closeBrowser()
	{driver.quit();}

}
