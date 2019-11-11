package com.test.automation.common.framework;

import java.io.File;
import java.io.IOException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;

public class ExtentReporter {

	private ExtentReports extent;
	private ExtentTest test;
	
	public ExtentReporter() {
		try {
			String homepath = new File(".").getCanonicalPath();
			extent = new ExtentReports(homepath + "\\Automation_Report\\" + "Run_" + Util.getCurrentDate() + "_"
					+ Util.getCurrentTime() + "\\ReportSummary.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startTest(String testClass, String testMethod, SeHelper se) {
		test = extent.startTest((testClass + " :: " + testMethod), testMethod);
		test.assignAuthor("VAM QA");
		test.assignCategory(testMethod);
		test.log(LogStatus.INFO, "Started Execution", "URL: " + SystemPropertyUtil.getBaseStoreUrl() + "Browser: " + se.browser().getBrowserName());
	}
	
	public void reportInfo(String step, String details) {
		test.log(LogStatus.INFO, step, details);
	}
	
	public void reportPass(String step, String details) {
		test.log(LogStatus.PASS, step, details);
	}
	
	public void reportFail(String step, String details) {
		test.log(LogStatus.FAIL, step, details);
	}
	
	public void reportError(String step, String details) {
		test.log(LogStatus.ERROR, step, details);
	}
	
	public void reportErrorCapture(String step, String details, String captureName, SeHelper se) {
		try {
			test.log(LogStatus.ERROR, step + test.addScreenCapture(Util.captureScreenshot(Util.getCurrentDate() + "_" + captureName, se)), details);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endTest() {
		extent.endTest(test);
	}
	
	public void closeExtent() {
		extent.flush();
		extent.close();
	}
}
