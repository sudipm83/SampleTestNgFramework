package util.core;

import com.google.common.base.Predicate;
import com.google.common.io.Files;

import java.awt.Color;
import java.awt. Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import util.reader.xml.ConfigXmlReader;

public class DriverUtilBase
{
	
	
	protected static final Logger logger = LogManager.getLogger(DriverUtilBase.class.getName());
	protected static final int secondsToTry = getSecondsToTry();
	protected static final int implicitSearchTime = 1;
	protected static final int jQueryTimeout = 45;
	protected int clickDelay = 150;
	protected boolean clickscrolling = true;
	protected WebDriver driver;
	protected WebDriverWait wdw;
	protected Actions actions;
	Predicate<Thread> threadAlive = (timeoutThread) -> {
	return !timeoutThread.isAlive();
	};
	
	public DriverUtilBase() {
	}
	
	public DriverUtilBase (WebDriver newDriver) {
	this.driver = newDriver;
	this.actions = new Actions (this.driver);
	}
	
	private static int getSecondsToTry() {
		try {
		ConfigXmlReader configXmlReader = new ConfigXmlReader("config/config.xml");
		String secondsStr = configXmlReader.getAttributeValue("configuration.generalsettings.frameworkSettings.value", "secondsToTry");
		logger.info("DriverUtilBase.setSeconds To Try(): Set timeout to" + secondsStr +" seconds");
		int secondsToTry = Integer.parseInt(secondsStr);
		return secondsToTry;
		} catch (Exception var3) {
		SeleniumException.throwUnexpectedErrorException(var3,
		"Timeout seconds To Try was not configured correctly. Please check your config file");
		return 0;
		}
	}
	
	public WebElement waitForElementTobeAppear(By locator)
	{
		return this.waitForElementToBeVisible(locator, secondsToTry);
	}
	
	public WebElement waitForElementToBeVisible(By locator, int timeout) {
		logger.debug("DriverutilBase.waitForElementToBeVisible(locator, timeout): "+ locator.toString() + ", " + timeout);
			try {
			this.wdw = new WebDriverWait(this.driver, (long) timeout);
			this.wdw.withMessage(String.format("Timed out waiting for the element with property {%s} to be visible.",locator.toString()));
			this.wdw.pollingEvery(1L, TimeUnit.SECONDS);
			
		
			return (WebElement) this.wdw.until(ExpectedConditions.visibilityOfElementLocated(locator));
			} catch (NoSuchElementException var4) {
			SeleniumException.throwNoSuchElementException(locator, "Element does not exist");
			} catch (Exception var5) {
			SeleniumException.throwTimedoutWhileWaitingException(" to be visible", locator.toString());
			}
			return null;
		}
	
		public boolean waitForElementToBeInvisible(By locator) 
		{
			return this.waitForElementToBeInvisible(locator, secondsToTry);
		}
		
		
		public boolean waitForElementToBeInvisible(By locator, int timeout) {
			
		
		logger.debug("DriverUtilBase. waitForElementToBeInvisible(locator, timeout): " + locator.toString()+", "+timeout);
		try {
			this.wdw = new WebDriverWait(this.driver, (long) timeout);
			this.wdw.withMessage(String.format("Timed out waiting for the element with property {%s} to be invisible.",
					locator.toString()));
			this.wdw.pollingEvery(1L, TimeUnit.SECONDS);
			return (Boolean) this.wdw.until(ExpectedConditions. invisibilityOfElementLocated (locator));
			} catch (NoSuchElementException var4) {
			SeleniumException.throwNoSuchElementException(locator, "Element does not exist");
			} catch (Exception vars) {
			SeleniumException.throwTimedoutWhileWaitingException(" to be invisible", locator.toString());
			}
		return false;
		}
		
		

}
