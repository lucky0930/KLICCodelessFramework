package com.vm.main.VM_Sample_Test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.Utils.ExcelReader;
import com.test.automation.common.Utils.TestUtil;
import com.test.automation.common.framework.Util;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

public class VM_Sample_Test {

	List<TestUtil> testsArray = new ArrayList<TestUtil>();
	List<String> lstOfTestCasesToExecute = new ArrayList<String>();

	private String reportPath;
	private ExtentReports report;
	private int numberOfBrowsers;
	private int numberOfTests;
	static ExcelReader excelReader = new ExcelReader(true);
	ATUTestRecorder recorder;
	
	@BeforeSuite(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	public void beforeSuite() throws IOException {
		reportPath = SystemPropertyUtil.getExtentReportPath() + "Run_" + Util.getCurrentDate() + "_"
				+ Util.getCurrentTime();

		report = new ExtentReports(reportPath + "\\ReportSummary.html");

		lstOfTestCasesToExecute = GetTestRunnerCases();
		numberOfBrowsers = SystemPropertyUtil.getNumberOfBrowsers();
		numberOfTests = lstOfTestCasesToExecute.size();
	}

	private List<String> GetTestRunnerCases() {

		TestUtil testUtil = new TestUtil();
		return testUtil.ExecuteTestRunner();

	}

	@BeforeMethod(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	protected synchronized void beforeMethod(Method method) throws MalformedURLException {
		DateFormat dateFormat = new SimpleDateFormat ("yy-mm-dd--HH-mm-ss");
		Date date = new Date();
		SeHelper se = new SeHelper();

		if (SystemPropertyUtil.recordScreen() && !SystemPropertyUtil.runHeadless())
		{
			File VLogs = new File(System.getProperty("user.dir") + "\\VideoLogs");
			if (!VLogs.exists()) {
				VLogs.mkdir();
			}
			
			Util.deleteVideoLogs(System.getProperty("user.dir") + "\\VideoLogs");
			try {
				recorder = new ATUTestRecorder(System.getProperty("user.dir"), "\\VideoLogs\\" + method.getName() + "-" + dateFormat.format(date),false);
			}
			catch (ATUTestRecorderException e)
			{
				System.out.println(e.toString());
				se.log().error("Error creating video file", e);
			}
			try {
				recorder.start();
			}
			catch (Exception e) {
				se.log().error("Error starting video recording", e);
			}
		}
		// add test cases to testsArray to execute
		for (String testCaseNumber : lstOfTestCasesToExecute) {
			TestUtil testUtil = new TestUtil(report, method, testCaseNumber);
			testsArray.add(testUtil);
		}
	}

	@SuppressWarnings("unchecked")
	@Test(description = "VM Automation Framework", timeOut = 500000000)
	public void VM_Test() {

		if (SystemPropertyUtil.runInParallel().equalsIgnoreCase("Yes")) { // running tests with parallel execution
			
			int count = 0;
			
			while (count < numberOfTests) {
				
				List<TestUtil> testsParallel = new ArrayList<TestUtil>();
				
				// only execute the specified number of tests at a time
				for (int i = 0; i < numberOfBrowsers; i++) {
					
					testsParallel.add(testsArray.get(count));
					count++;
					
					if (count == numberOfTests) {
						break;
					}
				}

				// start threads
				for (TestUtil test : testsParallel) {
					test.start();
				}

				// need to wait for threads to finish before proceeding
				while (!testsParallel.isEmpty()) {

					for (TestUtil test : testsParallel) {

						if (test.isAlive() == false) {
							test.endTest();
							testsParallel.remove(test);
							break;
						}
					}
				}
			}
			
		} else { // running the tests without parallel execution

			for (TestUtil test : testsArray) {

				test.ExecuteTest();
				test.endTest();
			}
		}
	}

	@AfterMethod(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	protected void afterMethod() {
		try {
			recorder.stop();
		}
		catch (NullPointerException e) {
			if (!SystemPropertyUtil.recordScreen() || SystemPropertyUtil.runHeadless()) {}
			else {
				SeHelper se = new SeHelper();
				se.log().error("Screen recording failed to initialize", e);
			}
		}
		catch (Exception e) {
			SeHelper se = new SeHelper();
			se.log().error("Unable to stop screen recording", e);
		}
	}

	@AfterSuite(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	public void afterSuite() {

		// Close extent tests
		for (TestUtil test : testsArray) {
			
			// If the test did not get to run, report it
			if (test.getExtent().getRunStatus() == LogStatus.UNKNOWN) {
				
				test.getExtent().log(LogStatus.SKIP, "This test was skipped due to an error in a previous test.");
			}
			
			test.endExtentTest();
		}

		// Close extent report
		report.flush();
		report.close();

		// Copy report file
		File source = new File(reportPath);
		File dest = new File(SystemPropertyUtil.getRecentReportPath());

		try {
			FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	@SuppressWarnings("unchecked")
//	@Test(description = "VM Automation Framework", dataProvider = "browserXlsByCol", groups = { "VMTest",
//			"QA" }, timeOut = 500000000)
//	@TestDataXLS(fileName = "\\resources\\test_data\\VM_TestData_Sample.xlsx", sheetVersion = "new", sheetName = "ACME_Data_Sample1")

	/*
	 * public void VM_Test_One(Browsers myBrowser, SeHelper se, Map<String, Object>
	 * params) throws IOException {
	 * 
	 * StartupPage StartupPage = TestPageFactory.initElements(se,
	 * StartupPage.class); LoginPage LoginPage = TestPageFactory.initElements(se,
	 * LoginPage.class); CustomerPage customerPage =
	 * TestPageFactory.initElements(se, CustomerPage.class); HomePage homePage =
	 * TestPageFactory.initElements(se, HomePage.class); SubmissionPage
	 * submissionPage = TestPageFactory.initElements(se, SubmissionPage.class); //
	 * VendorPage vendorPage = TestPageFactory.initElements(se, VendorPage.class);
	 * // BillingPage billingPage = TestPageFactory.initElements(se, //
	 * BillingPage.class);
	 * 
	 * int iteration = 0;
	 * 
	 * for (String name : params.keySet()) { List<Map<String, String>> table = new
	 * ArrayList<Map<String, String>>(); table = (List<Map<String, String>>)
	 * params.get(name); while (iteration < table.size()) { try { Map<String,
	 * String> row = table.get(iteration);
	 * 
	 * String strExecuteScript = (String) row.get("ExecuteScenario"); String loginUN
	 * = (String) row.get("UserName"); String CustomerAccountName = (String)
	 * row.get("CustomerAccountName"); String submissionNarrative = (String)
	 * row.get("SubmissionNarrative");
	 * 
	 * if (strExecuteScript.equalsIgnoreCase("Yes")) {
	 * 
	 * if (!loginUN.equalsIgnoreCase("NA")) {
	 * se.log().logTestStep("Connecting to URL"); test.log(LogStatus.INFO,
	 * "Started Execution", "Connecting to URL"); StartupPage.APStartUp(test,
	 * myBrowser);
	 * 
	 * se.log().logTestStep("Loggin in to APApp"); test.log(LogStatus.INFO,
	 * "Executing", "<b style='color:blue;'>Page : </b>Login" + "<br>Login as : ");
	 * LoginPage.APLogin(row, test); homePage.indexHome(row, test); }
	 * 
	 * if (!CustomerAccountName.equalsIgnoreCase("NA")) {
	 * customerPage.newCustomer(row, test); } if
	 * (!submissionNarrative.equalsIgnoreCase("NA")) {
	 * submissionPage.Submission(row, test); }
	 * 
	 * // vendorPage.createVendor(row, test); // billingPage.makeSinglePayment(row,
	 * test); } iteration++; } catch (Exception e) {
	 * se.verify().verifyEquals("VM_Test_One failed", true, false, true, test);
	 * e.printStackTrace(); iteration++; } } } } // testTearDown(se);
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Test(description = "VM Automation Framework", dataProvider =
	 * "browserXlsByCol", groups = { "VMTest", "QA" }, timeOut = 500000000)
	 * 
	 * @TestDataXLS(fileName = "\\resources\\test_data\\VM_TestData_Sample.xlsx",
	 * sheetVersion = "new", sheetName = "ACME_Data_Sample1")
	 * 
	 * public void VM_Test_Two(Browsers myBrowser, SeHelper se, Map<String, Object>
	 * params) throws IOException {
	 * 
	 * StartupPage StartupPage = TestPageFactory.initElements(se,
	 * StartupPage.class); LoginPage LoginPage = TestPageFactory.initElements(se,
	 * LoginPage.class); CustomerPage customerPage =
	 * TestPageFactory.initElements(se, CustomerPage.class); HomePage homePage =
	 * TestPageFactory.initElements(se, HomePage.class); SubmissionPage
	 * submissionPage = TestPageFactory.initElements(se, SubmissionPage.class); //
	 * VendorPage vendorPage = TestPageFactory.initElements(se, VendorPage.class);
	 * // BillingPage billingPage = TestPageFactory.initElements(se, //
	 * BillingPage.class);
	 * 
	 * int iteration = 0;
	 * 
	 * for (String name : params.keySet()) { List<Map<String, String>> table = new
	 * ArrayList<Map<String, String>>(); table = (List<Map<String, String>>)
	 * params.get(name); while (iteration < table.size()) { try { Map<String,
	 * String> row = table.get(iteration);
	 * 
	 * String strExecuteScript = (String) row.get("ExecuteScenario"); String loginUN
	 * = (String) row.get("UserName"); String CustomerAccountName = (String)
	 * row.get("CustomerAccountName"); String submissionNarrative = (String)
	 * row.get("SubmissionNarrative");
	 * 
	 * if (strExecuteScript.equalsIgnoreCase("Yes")) {
	 * 
	 * if (!loginUN.equalsIgnoreCase("NA")) {
	 * se.log().logTestStep("Connecting to URL"); test.log(LogStatus.INFO,
	 * "Started Execution", "Connecting to URL"); StartupPage.APStartUp(test,
	 * myBrowser);
	 * 
	 * se.log().logTestStep("Loggin in to APApp"); test.log(LogStatus.INFO,
	 * "Executing", "<b style='color:blue;'>Page : </b>Login" + "<br>Login as : ");
	 * LoginPage.APLogin(row, test); homePage.indexHome(row, test); }
	 * 
	 * if (!CustomerAccountName.equalsIgnoreCase("NA")) {
	 * customerPage.newCustomer(row, test); } if
	 * (!submissionNarrative.equalsIgnoreCase("NA")) {
	 * submissionPage.Submission(row, test); }
	 * 
	 * // vendorPage.createVendor(row, test); // billingPage.makeSinglePayment(row,
	 * test); } iteration++; } catch (Exception e) {
	 * se.verify().verifyEquals("VM_Test_One failed", true, false, true, test);
	 * e.printStackTrace(); iteration++; } } }
	 * 
	 * }
	 */

}
