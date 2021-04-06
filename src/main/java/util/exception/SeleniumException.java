package util.exception;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import util.core.DriverUtilBase;
import util.core.helper.NegativeTestScenarioHelper;
import util.core.reports.ExtentManager;

public class SeleniumException {
	private static final Logger logger = LogManager.getLogger(DriverUtilBase.class.getName());
	private static String tabbing = "";
	private static NegativeTestScenarioHelper negativeScenario = NegativeTestScenarioHelper.getInstance();

	private static String getClassName(int stackLevel) {
		return (new Throwable()).getStackTrace() [stackLevel].getClassName();
	}

	private static String getFilePath(int stackLevel) {
		return (new Throwable()).getStackTrace() [stackLevel].getFileName();
	}

	private static int getLineNumber(int stackLevel) {
		return (new Throwable()).getStackTrace()[stackLevel].getLineNumber();
	}

	private static String getTestName() {
		return (new Throwable()).getCause().getMessage().toString();
	}

	private static void printStackDetails(String errorDetails) {
		int stacklevel = 0;
		boolean loop = true;
		while (true) {
			String className;
			while (loop) {
				className = getClassName(stacklevel);
				if (!className.endsWith("Tests") && !className.endsWith("Test")) {
					++stacklevel;
				} else {
					loop = false;
				}
			}

			className = tabbing + "Test: " + getClassName(stacklevel) + "\n(" + getFilePath(stacklevel) + ":"
					+ Integer.toString(getLineNumber(stacklevel)) + ")\nMethod: " + getClassName(stacklevel - 1)
					+ "\n (" + getFilePath(stacklevel - 1) + ":" + Integer.toString(getLineNumber(stacklevel - 1))
					+ ")\n" + errorDetails + "\n";
			logger.error(className);
			return;
		}
	}

	public static void throwNoSuchElementException(By locator, String customMessage) {
		String message = "Error: " + customMessage + ": " + locator.toString();
		printStackDetails(tabbing + message);
		ExtentManager.reportlog(LogStatus.FAIL, message, false);
		if (negativeScenario.getNegativeScenario()) {
			Assert.assertTrue(true, message);
		} else {
			Assert.fail(message);
		}
	}

	public static void throwNoSuchElementException(WebElement element, String customMessage) {
				String message = "Error: " + customMessage + ": " + element.toString();
				
				printStackDetails(tabbing + message);
				ExtentManager.reportlog(LogStatus.FAIL, message, false);
				if (negativeScenario.getNegativeScenario()) {
				Assert.assertTrue (true, message);
				} else {
				Assert.fail(message);
			
				}

	public static void throwTimedoutwhileWaitingException(String text, String locator) {
				String message = "Error: Timed out waiting for "+locator+text+"";
				printStackDetails(tabbing + message);
				ExtentManager.reportlog(LogStatus.FAIL, message, false);
				
				if (negativeScenario.getNegativeScenario()) {
					Assert.assertTrue(true, message);
					} else {
					Assert.fail (message);
					}
				}

	public static void throwUnexpectedErrorException(Exception e, String customMessage) {
					String message = "Error: Unexpected exception: "+ customMessage + ": " + e.getMessage() +"";
					printStackDetails(tabbing + message);
					ExtentManager.reportlog(LogStatus.FAIL, message, false);
					if (negativeScenario.getNegativeScenario()) {
					Assert. assertTrue(true, message);
					} else
					{
					Assert.fail(message);
					}
	}

	public static void throwMismatchException(By locator, String expectedText, String actualText) {
					String message = "Error: Text Mismatch at: " + locator.toString() + "\n" + tabbing + tabbing + "Expected: \""
					+ expectedText + "\"\n" + tabbing + tabbing + "Actual: \"" + actualText + "\"";
					printStackDetails(tabbing + message);
					ExtentManager.reportlog(LogStatus.FAIL, message, false);
					if (negativeScenario.getNegativeScenario()) {
					Assert.assertTrue(true, message);
					} else {
					Assert.fail(message);
					
					}
	}

	public static void throwCannotcompleteActionException(By locator, String error, Exception e) {
					String message = "Error: "+ error + " : " + locator.toString() + "Exception: " + e.getMessage();
					
					printStackDetails(tabbing + message);
					ExtentManager.reportlog(LogStatus.FAIL, message, false);
					if (negativeScenario.getNegativeScenario()) {
					
						Assert.assertTrue(true, message);
					} else {
						Assert.fail (message);
					}
	}

	public static void throwCannotCompleteActionException(String error, Exception e) {
					String message = "Error: "+ error + " : EXCEPTION: " + e.getMessage();
					printStackDetails(tabbing + message);
					ExtentManager.reportlog(LogStatus.FAIL, message, false);
					if (negativeScenario.getNegativeScenario()) {
					Assert. assertTrue(true, message);
					} else {
					Assert.fail(message);
					}
	}

	public static void throwIndexOutOfBoundsException(By locator, int index, Exception e) {
					String message = "Error: Index out of bounds. Attempted to access element: "+ locator.toString() +
					" at index: " + index +" " + e.getMessage();
					printStackDetails(tabbing + message);
					ExtentManager.reportlog(LogStatus.FAIL, message, false);
					if (negativeScenario.getNegativeScenario()) {
					Assert.assertTrue(true, message);
					} else {
					Assert.fail(message);
					}
	}

	public static void throwIndexOutOfBoundsException(int index, Exception e) {
					String message = "Error: Index out of bounds. Attempted to access element at index: "+ index+" " + e.getMessage();
					printStackDetails(tabbing + message);
					ExtentManager.reportlog(LogStatus.FAIL, message, false);
					if (negativeScenario.getNegativeScenario()) {
						Assert.assertTrue(true, message);
					}else{
						Assert.fail(message);
					}
	}
	

	public static void throwInvalidLanguageException(String language, Exception e) {
						String message = "Error: Invalid language entered: "+ language +" " + e.getMessage();
						printStackDetails(tabbing + message);
						ExtentManager.reportlog(LogStatus.FAIL, message, false);
						if (negativeScenario.getNegativeScenario()) {
							Assert.assertTrue (true, message);
						} else {
							Assert.fail(message);
						}
	}
}