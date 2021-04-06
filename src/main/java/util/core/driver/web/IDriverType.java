package util.core.driver.web;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

public interface IDriverType {
	<T extends MutableCapabilities> T initializeOptions() throws Exception;
	WebDriver initializeDriver(MutableCapabilities var1);
}
