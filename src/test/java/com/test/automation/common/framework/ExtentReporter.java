package com.test.automation.common.framework;

import java.io.File;
import java.io.IOException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

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
	
	public void startTest(String testClass, String testMethod) {
		test = extent.startTest((testClass + " :: " + testMethod), testMethod);
		test.assignAuthor("VAM QA");
		test.assignCategory(testMethod);
		test.log(LogStatus.INFO, "Report Start");
	}
	
	public void reportStep(String step) {
		test.log(LogStatus.INFO, step);
	}
	
	public void reportError(String step) {
		test.log(LogStatus.ERROR, step);
	}
	
	public void endTest() {
		extent.endTest(test);
	}
	
	public void closeExtent() {
		extent.flush();
		extent.close();
	}
}
