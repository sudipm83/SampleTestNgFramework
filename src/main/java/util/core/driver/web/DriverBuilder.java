package util.core.driver.web;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import util.core.DriverUtilBase;
import util.reader.xml.ConfigXmlReader;

public final class DriverBuilder {
	
	private Locale locale = new Locale("en", "CA");
	private WebDriver driver;
	private static Logger logger = LogManager.getLogger(DriverBuilder.class.getName());
	private DriverUtilBase driverUtilBase;
	private static final String environmentStr = System.getProperty("environment");
	private static String configXmlPathStr = "config/config.xml";
	private static ConfigXmlReader configXmlReader;
	
	public DriverBuilder(WebDriver driver)
	{
		this.driver = driver;
		this.driverUtilBase = new DriverUtilBase(this.driver);
	}
	
	public void loadURL(String propertyKey)throws Exception
	{
		ClassLoader loader = DriverBuilder.class.getClassLoader();
		
		try {
			ResourceBundle resource = ResourceBundle.getBundle("properties/config", this.locale, loader);
			this.driver.get(resource.getString(propertykey));
		}catch (MissingResourceException var6)
		{
			String defaultEnvironmentStr = getEnvironment();
			String applicationUrlStr = configXmlReader.getAttribetureValueByXpath(
					"//environment[@name='" + defaultEnvironmentStr + "']//url[@key='" + propertyKey + "']/@value");
			this.driver.get(applicationUrlStr);
		}
		this.driverUtilBase.waitForJQueryToCOmplete();
			
	}
	
	public static String getLanguage()
	{
		try {
			String languageStr = configXmlReader.getAttributeValue("configuration.generalSettings.value","defaultLanguage");
			if(languageStr.equalsIgnoreCase("english") || languageStr.equalsIgnoreCase("french"))
			{
				return languageStr;
			}
			logger.error("Please mention the correct language");
		
		}catch (Exception var1)
		{
			logger.error("The language is not supported "+ var1.getMessage());
		}
		return null;
		
	}
	
	public void changeLanguage(String languageStr)
	{
		this.locale = new Locale(languageStr, "CA");
	}
	
	public static String getEnvironment()
	{
		String defaultEnvironmentStr = configXmlReader.getAttributeValue("configuration.generalSettings.value","defaultEnvironmentName");
		try {
			if(null != environmentStr)
			{
				defaultEnvironmentStr = environmentStr;
			}
		}catch (IllegalArgumentException var2)
		{
			logger.error("Please enter proper environment name.");
		}
		return defaultEnvironmentStr;
	}
	
	public void setReport(boolean activate)
	{
		WebDriverFactory.setReport(activate);
	}
	
	public void setIsCucumber(boolean activate)
	{
		WebDriverFactory.setIsCucumber(activate);
	}
	
	public <T> void setConfig(T t, CheckedConsumer<T> c) throws Exception
	{
		c.accept(t);
	}
	
	static
	{
		configXmlReader = new ConfigXmlReader(configXmlPathStr);
	}
}
