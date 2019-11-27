package com.test.automation.common.Utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.framework.Browser.Browsers;
import com.test.automation.common.framework.ExtentReporter;

public class TestUtil {

	String TESTDATA_SHEET_PATH = SystemPropertyUtil.getTestDataSheetPath();

	private static ExtentReporter reporter = new ExtentReporter();
	ExcelReader excelReader = new ExcelReader();
	Method method;
	ITestResult result;
	String className;

	public void ExecuteTest(String TestCaseNumber) {

		List<String> sheetCollection = excelReader.sheetCollection;

		LinkedHashMap<String, LinkedHashMap<String, String>> tableData = excelReader.GetTestData(TestCaseNumber,
				TESTDATA_SHEET_PATH);

		for (int i = 0; i < tableData.size(); i++) {
			String sheetName = sheetCollection.get(i);
			LinkedHashMap<String, String> actualData = tableData.get(sheetCollection.get(i));

			actualData.entrySet().forEach(entry -> {
				System.out.println(entry.getKey() + " => " + entry.getValue());

				if (entry.getKey().equalsIgnoreCase("TestCaseNumber") || entry.getKey().equalsIgnoreCase("Flow")) {

				} else {

				}
			});
		}
	}

	public void ExecuteTest(String TestCaseNumber, SeHelper se) {

		initialize(se);
		se.browser().get(SystemPropertyUtil.getBaseStoreUrl());

		// LinkedHashMap<String, LinkedHashMap<String, String>> tableData =
		// excelReader.GetTestData(TestCaseNumber,
		// TESTDATA_SHEET_PATH);

		LinkedHashMap<String, LinkedHashMap<String, String>> tableData = null;

		synchronized (TestCaseNumber) {
			tableData = excelReader.GetTestData(TestCaseNumber, TESTDATA_SHEET_PATH);
		}

		List<String> sheetCollection1 = new ArrayList<String>();
		List<String> actualSheetCollection = new ArrayList<String>();

		tableData.entrySet().forEach(entry -> {
			String sheetName = entry.getKey();

			actualSheetCollection.add(entry.getKey());
			if (sheetName.contains("$")) {
				sheetName = sheetName.split(Pattern.quote("$"))[0];
			}
			sheetCollection1.add(sheetName);
		});

		for (int i = 0; i < tableData.size(); i++) {

			if (se.keepRunning()) {
				LinkedHashMap<String, String> actualData = tableData.get(actualSheetCollection.get(i));
				ExecuteTestProcess(se, sheetCollection1.get(i), actualData);
			} else {
				break;
			}
		}

		endTest(se);
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

	public void initialize(SeHelper se) {
		se.setReporter(reporter);
		se.startSession(Browsers.valueOf(SystemPropertyUtil.getBrowsers()));
		se.driver().manage().timeouts().implicitlyWait(SystemPropertyUtil.getImplicitWaitTime(), TimeUnit.SECONDS);
		Test test = method.getAnnotation(Test.class);
		Browsers myBrowser = se.currentBrowser();
		se.log().trace("Test Method: " + method.getName());
		se.log().trace("Description: " + test.description());
		se.log().trace("Browser: " + myBrowser.toString());
		se.util().sleep(1000);
		reporter.startTest(className, method.getName(), se);
	}

	public void testDetails(ITestResult r, Method m, String className) {
		this.result = r;
		this.method = m;
		this.className = className;
	}

	public void endTest(SeHelper se) {
		se.log().trace("End of " + method.getName() + " Result: " + result.isSuccess() + "\n");
		se.reporter().endResult(result.isSuccess(), se);
		se.reporter().endTest();
		se.log().printLogBuilder();
		se.log().testSeperator();
		se.log().couchDb(result.isSuccess(), String.valueOf(result.isSuccess()));
		se.browser().quit();
	}

	public void closeExtent() {
		reporter.closeExtent();
	}
}