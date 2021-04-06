package util.core.driver.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import util.reader.xml.ConfigXmlReader;

public abstract class BDDDriverThreadManager {
	
		
	private static Logger logger = LogManager.getLogger(BDDDriverThreadManager.class.getName());
	private static final List<WebDriver> threadPool = Collections.synchronizedList(new ArrayList());
	
	private static ThreadLocal<WebDriver> threadDriver = ThreadLocal.withInitial(() ->{
		try {
			return get();
		}catch (Exception var1) {
			logger.info("Unable to get the driver due to "+ var1.getMessage());
			throw new RuntimeException(var1);
		}
				
	});
	private static WebDriver get() throws Exception {
	WebDriver driverThread = WebDriverFactory.getDriver();
	threadPool.add(driverThread);
	return driverThread;
	}
	
	protected WebDriver getDriver() {
	return (WebDriver) threadDriver.get();
	}
	protected void newDriver() throws Exception {
	threadDriver.set(get());
	}
	public static WebDriver getDriverStatic() {
	return (WebDriver) threadDriver.get();
	}
	public void createDriver() throws Exception {
	this.newDriver();
	}
	public void clearSettings() {
	String configxmlPathstr = "config/config.xml";
	ConfigXmlReader configXmlReader = new ConfigXmlReader(configxmlPathstr);
	String closeBrowserstr = configXmlReader.getAttributeValue("browsers.browser.value", "closeBrowser");
	if(closeBrowserstr.equalsIgnoreCase("true"))
	{
		this.getDriver().quit();
		WebDriverFactory.setIsCucumber(false);
	}
	}

}
