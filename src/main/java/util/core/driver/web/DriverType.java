package util.core.driver.web;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

public enum DriverType implements IDriverType{

	HEADLESS_CHROME, CHROME, HEADLESS_FIREFOX, IE, SAFARI, ANDROID_CHROME, IOS_SAFARI;

	
	protected DriverPathLoader loader;
	
	
	
	public <T extends MutableCapabilities> T initializeOptions() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public WebDriver initializeDriver(MutableCapabilities var1) {
		// TODO Auto-generated method stub
		return null;
	}



}
