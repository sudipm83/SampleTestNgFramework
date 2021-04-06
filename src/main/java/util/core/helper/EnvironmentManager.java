package util.core.helper;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class EnvironmentManager {
	
	private static final Logger logger = LogManager.getLogger(EnvironmentManager.class.getName());
	private static final String CONFIG_PATH = "config/config.xml";
	private static final String NODE_HEADER = "//environment[@name='";
	private static final String NODE_FOOTER = "']/@value";
	
	public static String getLanguage()
	{
		String languageStr = System.getProperty("language");
		
		try {
			if(languageStr == null)
			{
				languageStr = DriverBuilder.getLanguage();
			}
			if("english".equalsIgnoreCase(languageStr) || "french".equalsIgnoreCase(languageStr))
			{
				return languageStr;
			}
			
			logger.error("Please mention correct language.");
		}catch (Exception var2)
		{
			logger.error(var2.getMessage());
		}
		return null;
		
	}
	
	public static void setupUrl(String language, String urlKey)
	{
		try {
			DriverBuilder builder = new DriverBuilder(BDDDriverThreadManager.getDriverStatic());
			builder.setConfig(language, builder::changeLanguage);
		}
	}
	
}
