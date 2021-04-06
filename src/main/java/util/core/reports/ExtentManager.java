package util.core.reports;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.maven.surefire.shared.utils.io.FileUtils;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import util.core.common.ConfigReader;
import util.core.driver.web.BDDDriverThreadManager;
import util.core.driver.web.DriverThreadManager;
import util.core.driver.web.WebDriverFactory;
import util.reader.xml.ConfigXmlReader;

public class ExtentManager {
	private static ExtentReports extent;
	private static Logger log = LogManager.getLogger(ExtentManager.class.getName());
	private static Path reportNameStr;
	private static String reportPathStr;
	private static String configXmlPathStr = "config/config.xml";
	private static ConfigXmlReader configXmlReader;

	private static ExtentReports createInstance(String fileName) {
		reportNameStr = Paths.get("test-output", "ExtentReport",
				"Report_" + (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date()));

		try {
			Files.createDirectories(reportNameStr);
		} catch (IOException var2) {
			log.fatal("Parent directory does not exist: " + var2.getMessage());
		} catch (Exception var3) {
			log.error(var3.getMessage());
		}

		reportPathStr = reportNameStr.toString() + "/" + fileName;
		if (!WebDriverFactory.getIsCucumber()) {
			extent = new ExtentReports(reportPathStr, true, DisplayOrder.OLDEST_FIRST);
		}

		return extent;
	}

	public static String takeScreenshot() throws IOException {
		File dest = null;
		if (WebDriverFactory.getReportParam()) {
			WebDriver driver = DriverThreadManager.getDriverStatic();

			File scr = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			String imageNameStr = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date()) + ".png";
			dest = new File(reportNameStr + "\\" + imageNameStr);
			FileUtils.copyFile(scr, dest);
		}
		return dest.getAbsolutePath();
	}

public static String takeScreenShot(Boolean longScreenshot) {
		File file = null;
		try {
			if (WebDriverFactory.getReportParam()) {
			WebDriver driver = DriverThreadManager.getDriverStatic();
			Screenshot screenshot = longScreenshot
			? (new AShot()).shootingStrategy(ShootingStrategies.viewportPasting(1000))
			.takeScreenshot(driver)
			: (new AShot()).shootingStrategy(ShootingStrategies.simple()).takeScreenshot(driver);
			String imageNameStr = (new SimpleDateFormat("yyyymMdd_HHimmss")).format(new Date()) + ".png";
			file = new File(reportNameStr + "\\" + imageNameStr);
			ImageIO.write(screenshot.getImage(), "png", file);
			}
			} catch (IOException var5) {
			log.error(var5.getMessage());
		}
		return file.getAbsolutePath();
}

private static void reportlog(LogStatus status, String messageStr, int screenshotType) {
	try {
	String utf8EncodedMessageString = getUtf8EncodedString(messageStr);
	String frameworkSettingsXPathStr = "configuration.generalsettings.frameworkSettings.value";
	String defaultTargetMediumStr = configXmlReader.getAttributeValue(frameworkSettingsXPathStr,"defaultTargetMedium");
	String browserStr = null;
	if (!System.getProperty("browser").isEmpty()) {
	browserStr = System.getProperty("browser").toUpperCase();
	} else if (defaultTargetMediumStr.contains("web")) {
	browserStr = WebDriverFactory.getBrowser();
	} else {
	browserStr = MobileDriverFactory.getBrowser();
	}
	if (!ConfigReader.isNonBrowserTest()) {
	if (!WebDriverFactory.getIsCucumber()) {
		
		String base64ScreenshotStr = null;
		if (screenshotType != 0) {
		Object driver;
		if (browserStr.contains("DEVICE")) {
		driver = MobileDriverManager.getDriverStatic();
		} else {
		driver = DriverThreadManager.getDriverStatic();
		}
		Screenshot screenshot;
		if (screenshotType == 2) {
		screenshot = (new AShot())
		.shootingStrategy(browserStr.equalsIgnoreCase("IE")
		? ShootingStrategiesForIE.viewportPasting(1000)
		: ShootingStrategies.viewportPasting(1000))
		.takeScreenshot((WebDriver) driver);
		} else {
		screenshot = (new AShot()).shootingStrategy(ShootingStrategies.simple())
		.takeScreenshot((WebDriver) driver);
		}
		
		
		BufferedImage image = screenshot.getImage();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", outputStream);
		base64ScreenshotStr = "data:image/png;base64,"
		+ Base64.getEncoder().encodeToString(outputStream.toByteArray());
		((ExtentTest) ExtentTestNGITestListener.test.get()).log(status, utf8EncodedMessageString,
		((ExtentTest) ExtentTestNGITestListener. test.get())
		.addBase64ScreenShot(base64ScreenshotStr));
		} else {
		((ExtentTest) ExtentTestNGITestListener.test.get()).log(status, utf8EncodedMessageString, "");
		}
		} else {
		WebDriver var13 = BDDDriverThreadManager.getDriverstatic();
		}
		} else {
		((ExtentTest) ExtentTestNGITestListener.test.get()).log(status, utf8EncodedMessageString, "");
		}
	}catch(Exception var12) {
		log.error(var12.getMessage());
	}

	
}

	public static void reportlog(LogStatus status, String messageStr, boolean screenshotBln) {
		int screenshotType = screenshotBln ? 1 : 0;
		reportlog(status, getUtf8EncodedString(messageStr), screenshotType);
	}

	public static void reportlog(LogStatus status, String messageStr, boolean screenshotBln, boolean longScreenshot) {
		int screenshotType = screenshotBln ? (longScreenshot ? 2 : 1) : 0;
		reportlog(status, messageStr, screenshotType);
	}

	public static void reportlog(ExtentTest test, LogStatus status, String messageStr) {
		test.log(status, messageStr);
	}

	static ExtentReports getInstance() {
		if (extent == null) {
			extent = createInstance("ExtentReportResults.html");
		}
		return extent;
	}

	public static String getUtf8EncodedString(String rawString) {
		return new String(rawString.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

	}

	static {
		configXmlReader = new ConfigXmlReader(configXmlPathStr);
	}

}
