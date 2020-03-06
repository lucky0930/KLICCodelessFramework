package com.test.automation.common.framework;

import java.io.IOException;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.SeHelper;

public class ExtentReporter {

	private ExtentTest test;
	private boolean testResult = true;

	public ExtentReporter(ExtentTest test) {
		
		this.test = test;
	}

	public void reportInfo(String step, String details) {
		
		test.log(LogStatus.INFO, step, details);
	}
	
	public void reportStepPass(String step, String details) {
		
		test.log(LogStatus.PASS, step, details);
	}
	
	public void reportStepFail(String step, String details) {
		
		test.log(LogStatus.FAIL, step, details);
		testResult = false;
	}

	public void reportVerifyPass(String assertion, String actual, String expected) {
		
		test.log(LogStatus.PASS, assertion + " passed.", "Actual: " + actual + "<br>Expected: " + expected);
	}
	
	 public void reportVerifyPass(String Key, String assertion, String actual, String expected) {
	        if (assertion.equalsIgnoreCase("AreEqual")) {
	            test.log(LogStatus.PASS, "Validation for the " + Key + "is getting success", "Actual: " + actual + "<br>Expected: " + expected);
	        } else {
	            test.log(LogStatus.PASS, assertion + " passed.", "Actual: " + actual + "<br>Expected: " + expected);
	        }
	    }

	public void reportVerifyFail(String assertion, String actual, String expected) {
		
		test.log(LogStatus.FAIL, assertion + " failed.", "Actual: " + actual + "<br>Expected: " + expected);
		testResult = false;
	}

	public void reportFailCapture(String assertion, String actual, String expected, SeHelper se) {
		
		try {
			test.log(LogStatus.FAIL, assertion + " failed.", "Actual: " + actual + "<br>Expected: " + expected
					+ test.addScreenCapture(Util.captureScreenshot(assertion + "_failure", se)));
			testResult = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void screenCapture( SeHelper se) {
		try {
			test.log(LogStatus.INFO, "" + test.addScreenCapture(Util.captureScreenshot("USER_SCREENCAP" + Util.getCurrentDate(), se)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reportError(String step, Exception e) {
		
		test.log(LogStatus.ERROR, "Exception encountered for: " + step, e.getClass().getSimpleName());
		testResult = false;
	}
	
	public void reportErrorCapture(String step, Exception e, SeHelper se) {
		
		try {
			test.log(LogStatus.ERROR, "Exception encountered for: " + step, e.getClass().getSimpleName()
					+ test.addScreenCapture(Util.captureScreenshot(step + "_" + e.getClass().getSimpleName(), se)));
			testResult = false;
		} catch (IOException e1) {
			e.printStackTrace();
		}
	}

	public void reportResult(SeHelper se) {		
		
		if (testResult) {
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
	
	public Boolean getResult() {

		return testResult;
	}
}
