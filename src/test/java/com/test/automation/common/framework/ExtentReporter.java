package com.test.automation.common.framework;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;

public class ExtentReporter {

	private ExtentTest test;

	public ExtentReporter(ExtentTest test) {
		this.test = test;
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

	public void endResult(SeHelper se, Boolean result) {		
		if (result) {
			try {
				test.log(LogStatus.PASS, "Test Passed", ""
						+ test.addScreenCapture(Util.captureScreenshot(Util.getCurrentDate() + "_" + "TC_PASS", se)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				test.log(LogStatus.FAIL, "Test Failed", ""
						+ test.addScreenCapture(Util.captureScreenshot(Util.getCurrentDate() + "_" + "TC_FAIL", se)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ExtentTest getTest() {
		return test;
	}
}
