package com.test.automation.common.framework;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;

public class ExtentReporter {

	private ExtentReports extent;
	private ExtentTest test;
	private String reportPath = null;

	public ExtentReporter() {
		reportPath = SystemPropertyUtil.getExtentReportPath() + "Run_" + Util.getCurrentDate() + "_"
				+ Util.getCurrentTime();
		extent = new ExtentReports(reportPath + "\\ReportSummary.html");
	}

	public void startTest(String testClass, String testMethod, SeHelper se) {
		test = extent.startTest((testClass + " :: " + testMethod), testMethod);
		test.assignAuthor("VAM QA");
		test.assignCategory(testMethod);
		test.log(LogStatus.INFO, "Started Execution",
				"URL: " + SystemPropertyUtil.getBaseStoreUrl() + ":: Browser: " + se.browser().getBrowserName());
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

	public void reportFailCapture(String step, String details, String captureName, SeHelper se) {
		try {
			test.log(LogStatus.FAIL, step, details
					+ test.addScreenCapture(Util.captureScreenshot(Util.getCurrentDate() + "_" + captureName, se)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reportError(String step, String details) {
		test.log(LogStatus.ERROR, step, details);
	}

	public void reportErrorCapture(String step, String details, String captureName, SeHelper se) {
		try {
			test.log(LogStatus.ERROR, step, details
					+ test.addScreenCapture(Util.captureScreenshot(Util.getCurrentDate() + "_" + captureName, se)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void endResult(Boolean result, SeHelper se) {
		if (result) {
			try {
				test.log(LogStatus.PASS, "Test Result", "Result: " + result
						+ test.addScreenCapture(Util.captureScreenshot(Util.getCurrentDate() + "_" + "TC_PASS", se)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				test.log(LogStatus.FAIL, "Test Result", "Result: " + result
						+ test.addScreenCapture(Util.captureScreenshot(Util.getCurrentDate() + "_" + "TC_FAIL", se)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void endTest() {
		extent.endTest(test);
		extent.getReportId();
	}

	public void closeExtent() {
		extent.flush();
		extent.close();
		copyfile(reportPath);
	}

	private void copyfile(String reportPath2) {
		File source = new File(reportPath2);
		File dest = null;

		dest = new File(SystemPropertyUtil.getRecentReportPath());

		try {
			FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
