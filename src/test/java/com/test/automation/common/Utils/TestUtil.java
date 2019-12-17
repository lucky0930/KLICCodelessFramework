
package com.test.automation.common.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.framework.Browser.Browsers;
import com.test.automation.common.framework.ExtentReporter;
import com.test.automation.common.framework.Util;

public class TestUtil extends Thread {

	String TESTDATA_SHEET_PATH = SystemPropertyUtil.getTestDataSheetPath();
	String TEST_RUNNER_PATH = SystemPropertyUtil.getTestRunnerPath();

	ExcelReader excelReader = new ExcelReader();
	SeHelper se;
	private ExtentReports report;
	Method method;
	String TestCaseNumber;
	ExtentTest test;

	public TestUtil(ExtentReports report, Method method, String TestCaseNumber) {
		this.report = report;
		this.method = method;
		this.TestCaseNumber = TestCaseNumber;
		this.se = new SeHelper();

		this.test = report.startTest("Test Case Number: " + TestCaseNumber);
		test.assignAuthor("VAM QA");
		test.assignCategory(method.getName());

		ExtentReporter reporter = new ExtentReporter(test);
		se.setReporter(reporter);
	}

	/*
	 * public void ExecuteTest(String TestCaseNumber) {
	 * 
	 * List<String> sheetCollection = excelReader.sheetCollection;
	 * 
	 * LinkedHashMap<String, LinkedHashMap<String, String>> tableData =
	 * excelReader.GetTestData(TestCaseNumber, TESTDATA_SHEET_PATH);
	 * 
	 * for (int i = 0; i < tableData.size(); i++) { String sheetName =
	 * sheetCollection.get(i); LinkedHashMap<String, String> actualData =
	 * tableData.get(sheetCollection.get(i));
	 * 
	 * actualData.entrySet().forEach(entry -> { System.out.println(entry.getKey() +
	 * " => " + entry.getValue());
	 * 
	 * if (entry.getKey().equalsIgnoreCase("TestCaseNumber") ||
	 * entry.getKey().equalsIgnoreCase("Flow")) {
	 * 
	 * } else {
	 * 
	 * } }); } }
	 */

	public TestUtil() {
		// TODO Auto-generated constructor stub
	}

	public void run() {

		ExecuteTest();
	}

	public void ExecuteTest() {

		initialize();

		se.browser().get(SystemPropertyUtil.getBaseStoreUrl());

		LinkedHashMap<String, LinkedHashMap<String, String>> mydata = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		LinkedHashMap<String, LinkedHashMap<String, String>> xpathData = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		mydata = excelReader.GetTestData(TestCaseNumber, TESTDATA_SHEET_PATH);

//		LinkedHashMap<String, LinkedHashMap<String, String>> tableData = defaultData;
//		
//		defaultData.clear();
		xpathData = excelReader.GetTestData("Xpath", TESTDATA_SHEET_PATH);

		synchronized ("Xpath") {
			// xpathData = excelReader.GetTestData("Xpath", TESTDATA_SHEET_PATH);
		}

		synchronized (TestCaseNumber) {
			// tableData = excelReader.GetTestData(TestCaseNumber, TESTDATA_SHEET_PATH);
		}

		List<String> sheetCollection1 = new ArrayList<String>();
		List<String> actualSheetCollection = new ArrayList<String>();

		mydata.entrySet().forEach(entry -> {
			String sheetName = entry.getKey();

			actualSheetCollection.add(entry.getKey());
			if (sheetName.contains("$")) {
				sheetName = sheetName.split(Pattern.quote("$"))[0];
			}
			sheetCollection1.add(sheetName);
		});

		for (int i = 0; i < mydata.size(); i++) {

			if (se.keepRunning()) {
				LinkedHashMap<String, String> actualData = mydata.get(actualSheetCollection.get(i));
				LinkedHashMap<String, String> actualxPathData = xpathData.get(actualSheetCollection.get(i));
				// ExecuteTestProcess(se, sheetCollection1.get(i), actualData);
				ExecuteTestProcess(se, sheetCollection1.get(i), actualData, actualxPathData);
			} else {
				break;
			}
		}
	}

	private void ExecuteTestProcess(SeHelper se, String sheetName, LinkedHashMap<String, String> actualData,
			LinkedHashMap<String, String> actualxPathData) {

		se.log().logSeStep("Opening page: " + sheetName);
		se.reporter().reportInfo("Opening Page", "Page Name: " + sheetName);
		
		se.waits().waitForPageLoad();
		try {
			actualData.entrySet().forEach(entry -> {

				if (!se.keepRunning()) {
					return;
				}

				System.out.println(entry.getKey() + " => " + entry.getValue());

				if (entry.getKey().equalsIgnoreCase("TestCaseNumber") || entry.getKey().equalsIgnoreCase("Flow")) {

				} else {
					if (entry.getValue() != null)
						System.out.println(actualxPathData.get(entry.getKey()));
					PageProcess.findElement(se, sheetName, entry.getKey(), entry.getValue(),
							actualxPathData.get(entry.getKey()));
				}
			});
			se.waits().waitForPageLoad();
			
			
		} catch (NullPointerException e) {

			se.log().error(e.getClass().getSimpleName() + " encountered on page: " + sheetName, e);
			se.reporter().reportErrorCapture(sheetName, e, se);
		}
	}

	private void ExecuteTestProcess(SeHelper se, String sheetName, LinkedHashMap<String, String> actualData) {

		se.log().logSeStep("Accessing page: " + sheetName);
		se.reporter().reportInfo("Accessing Page", "Page Name: " + sheetName);

		actualData.entrySet().forEach(entry -> {

			if (!se.keepRunning()) {
				return;
			}

			System.out.println(entry.getKey() + " => " + entry.getValue());

			if (entry.getKey().equalsIgnoreCase("TestCaseNumber") || entry.getKey().equalsIgnoreCase("Flow")) {

			} else {
				if (entry.getValue() != null)
					PageProcess.findElement(se, sheetName, entry.getKey(), entry.getValue());
			}
		});
	}

	public void initialize() {

		se.startSession(Browsers.valueOf(SystemPropertyUtil.getBrowsers()));
		se.driver().manage().timeouts().implicitlyWait(SystemPropertyUtil.getImplicitWaitTime(), TimeUnit.SECONDS);
		Browsers myBrowser = se.currentBrowser();
		se.log().trace("Test Method: " + method.getName());
		se.log().trace("Test Case Number: " + TestCaseNumber);
		se.log().trace("Browser: " + myBrowser.toString());
		se.util().sleep(1000);
		test.log(LogStatus.INFO, "Started Execution",
				"URL: " + SystemPropertyUtil.getBaseStoreUrl() + "<br>Browser: " + se.browser().getBrowserName());
	}

	public void endTest() {

		se.log().trace("End of Test Case #" + TestCaseNumber + " Result: " + se.reporter().getResult() + "\n");
		se.reporter().reportResult(se);
		// report.endTest(se.reporter().getTest());
		// report.getReportId();
		se.log().printLogBuilder();
		se.log().testSeperator();
		se.log().couchDb(se.reporter().getResult(), String.valueOf(se.reporter().getResult()));
		se.browser().quit();
	}

	public void endExtentTest() {
		report.endTest(se.reporter().getTest());
		report.getReportId();
	}

	public List<String> ExecuteTestRunner() {
		List<String> lstOfTC = excelReader.GetTestRunnerData(TEST_RUNNER_PATH);
		return lstOfTC;
	}

	public ExtentTest getExtent() {
		return test;
	}
}