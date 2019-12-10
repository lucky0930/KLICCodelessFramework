package com.vm.main.VM_Sample_Test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import com.test.automation.common.BaseTest;
import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.Utils.TestPageFactory;
import com.test.automation.common.Utils.TestUtil;
import com.test.automation.common.framework.Util;
import com.test.automation.common.framework.Browser.Browsers;
import com.test.automation.common.framework.ExtentReporter;

public class VM_Sample_Test extends BaseTest {

	ArrayList<TestUtil> testsArray = new ArrayList<>();

	private String reportPath;
	private ExtentReports report;

	@BeforeSuite(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	public void beforeSuite() throws IOException {

		reportPath = SystemPropertyUtil.getExtentReportPath() + "Run_" + Util.getCurrentDate() + "_"
				+ Util.getCurrentTime();

		report = new ExtentReports(reportPath + "\\ReportSummary.html");
	}

	@BeforeMethod(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	protected void beforeMethod(Method method) {

		// add test cases to list to execute
		testsArray.add(new TestUtil(report, method, "101"));
		testsArray.add(new TestUtil(report, method, "102"));
		testsArray.add(new TestUtil(report, method, "105"));
	}

	@SuppressWarnings("unchecked")
	@Test(description = "VM Automation Framework", timeOut = 500000000)
	public void VM_Test_Method() {

		// start threads
		for (TestUtil test : testsArray) {
			test.start();
		}

		// need to wait for threads to finish before proceeding		
		while (!testsArray.isEmpty()) {
			
			for (TestUtil test : testsArray) {
				
				if (test.isAlive() == false) {
					test.endTest();
					testsArray.remove(test);
					break;
				}
			}
		}
	}

	@AfterMethod(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	protected void afterMethod() {

	}

	@AfterSuite(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	public void afterSuite() {

		// Close ExtentReport
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
