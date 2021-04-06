package util.core.driver.web;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {

	
	private static DriverType driverType;
	private static DriverType defaultDriver = null;
	private static final String BROWSER = System.getProperty("browser");
	private static final boolean USE_REMOTE_DRIVER = Boolean.getBoolean("remoteDriver");
	private static final Logger logger = LogManager.getLogger(WebDriverFactory.class.getName());
	private static boolean reportBln;
	private static boolean isCucumberbln = false;
	public static boolean getReportParam() {
	return reportBln;
	}
	static void setReport(boolean activateBln) {
	reportBln =  activateBln;
	}
	public static boolean getIsCucumber() {
	return isCucumberbln;
	}
	static void setIsCucumber(boolean activateBln) {
	isCucumberbln = activateBln;
	}
	private static WebDriver instantiateDriver(MutableCapabilities mutableCapabilities) throws MalformedURLException {
		if (USE_REMOTE_DRIVER) {
			URL seleniumGridURL = null;
			
			try {
			seleniumGridURL = new URL(System.getProperty("seleniumGridURL"));
		} catch (Exception var4) {
		   logger.error("Error: "+ var4.getMessage());
		   throw var4;
		}
			
		String desiredBrowserVersionStr = System.getProperty("desiredBrowserVersion");
		String desiredPlatformStr = System.getProperty("desiredPlatform");
		if (desiredPlatformStr != null && !desiredPlatformStr.isEmpty()) {
			mutableCapabilities.setCapability(desiredPlatformStr,Platform.valueOf(desiredPlatformStr.toUpperCase()));
			}
			if (desiredBrowserVersionStr != null && !desiredBrowserVersionStr.isEmpty()) {
			mutableCapabilities.setCapability(desiredBrowserVersionStr, desiredBrowserVersionStr);
			}
				return new RemoteWebDriver(seleniumGridURL, mutableCapabilities);
			} else {
				return driverType.initializeDriver (mutableCapabilities);
			}
	}
	
	private static DriverType verifyBrowserType() {
		Map<String, String> browserConfig getBrowserConfig();
		String browser TestedStr = (String) browserConfig.get("browserTested");
		DriverType type = null;
		I
		try {
		if (!BROWSER.toString().isEmpty()) {
		if (!BROWSER.toString().toUpperCase().contains ("DEVICE")) {
		type DriverType.valueOf(BROWSER.toUpperCase());
		}
		} else {
		type DriverType, valueof(browserTestedStr.toUpperCase());
		}
		} catch (IllegalArgumentException var4) {
		Logger.error("Please enter proper WebDriver name.");
		Logger.error(var4.getMessage());
		}
		return type;
	}
	
	public static WebDriver getDriver() throws Exception {
		MutableCapabilities mutableCapabilities = null;
		driverType = verifyBrowserType();
		mutableCapabilities = driverType.initializeOptions():
		return instantiateDriver(mutableCapabilities);
	}
	
	public static String getBrowser() {
		String browserStr = null; ;
		driverType = verifyBrowserType();
		browserStr = driverType.name();
		return browserStr;
	}
	
	public static WebDriver getDriver(String browser) throws Exception {
		WebDriver driver = null;
		MutableCapabilities mutableCapabilities;
		try {
		DriverType type = DriverType.valueOf(browser.toUpperCase());
		if (type != null) {
		mutableCapabilities = type.initializeOptions();
		driver = type.initializeDriver(mutableCapabilities);
			}
		} catch (Exception var4) {
			System.out.println("An exception occured during the WebDriver instatiation of browser :"+var4);
			System.out.println("Try to instantiate the default driver - "+ defaultDriver);
			mutableCapabilities = defaultDriver.initializeOptions();
			driver = defaultDriver.initializeDriver(mutableCapabilities);
		}
		return driver;
	}
}
