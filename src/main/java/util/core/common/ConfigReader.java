package util.core.common;

import util.reader.xml.ConfigXmlReader;

public class ConfigReader {
	
	
	private static String configXmlPathStr = "config/config.xml";
	private static ConfigXmlReader configXmlReader;
	public static boolean isNonBrowserTest() {
	String nonBrowserSettingsXPathStr = "browsers. browser.value";
	boolean isNonBrowserTest = System.getProperty("nonBrowserTest") == null
	? configXmlReader.getAttributeValue (nonBrowserSettingsXPathStr, "nonBrowserTest")
	.equalsIgnoreCase("true")
	: System.getProperty("nonBrowserTest").equalsIgnoreCase("true");
	return isNonBrowserTest;
	}
public static boolean enablePerfectoConnect() {
	boolean enablePerfectoConnect = false;
	String defaultTargetMediumStr = configXmlReader
	.getAttributeValue("configuration.generalsettings.frameworkSettings.value", "defaultTargetMedium");
	if (defaultTargetMediumStr.equalsIgnoreCase("webMobile")) {
	
	String enablePerfectoConnectStr = configXmlReader
	.getAttributeValue("MobileSettings.Perfecto.mSettings.value", "enablePerfectoConnect");
	enablePerfectoConnect = System.getProperty("enablePerfectoConnect") == null
	? enablePerfectoConnectStr.equalsIgnoreCase("true")
	: System.getProperty ("enablePerfectoConnect").equalsIgnoreCase("true");
	}
	
	return enablePerfectoConnect;
}
	static {
	configXmlReader = new ConfigXmlReader(configXmlPathStr);
	}

}
