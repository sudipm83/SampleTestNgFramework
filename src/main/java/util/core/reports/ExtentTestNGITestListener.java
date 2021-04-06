package util.core.reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import util.reader.xml.ConfigXmlReader;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGITestListener implements ITestListener{

	public static ExtentReports extentReports = ExtentManager.getInstance();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal();
	private static final Logger Logger = LogManager.getLogger(ExtentTestNGITestListener.class.getName());
	private static ExtentTest extentTestparent;
	private ExtentTest newExtentTest;
	
	private String configXmlPathStr = "config/config.xml";
	private ConfigXmlReader configXmlReader;
	private String environmentStr;
	private String browserStr;
	private String platformStr;
	private String languageStr;
	private ITestContext testsuite;
	
	private String currentTestSuiteStr;
	private String previousTestSuiteStr;
	
	
	public ExtentTestNGITestListener() {
	this.configXmlReader = new ConfigXmlReader(this.configXmlPathStr);
	this.previousTestSuiteStr = "";
	}
	
	
	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	
}
