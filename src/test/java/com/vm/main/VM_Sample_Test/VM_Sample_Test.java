package com.vm.main.VM_Sample_Test;

//import com.secura.ap.pages.BillingPage;
//import com.secura.ap.pages.CustomerPage;
//import com.secura.ap.pages.HomePage;
//import com.secura.ap.pages.VendorPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
//import com.secura.ap.pages.LoginPage;
//import com.secura.ap.pages.StartupPage;
//import com.secura.ap.pages.SubmissionPage;
import com.test.automation.common.BaseTest;
import com.test.automation.common.SeHelper;
import com.test.automation.common.Utils.TestPageFactory;
import com.test.automation.common.Utils.TestUtil;
import com.test.automation.common.framework.Util;
import com.test.automation.common.framework.Browser.Browsers;

public class VM_Sample_Test extends BaseTest {

	ExtentReports extent;
	ExtentTest test;
	TestUtil testUtil = new TestUtil();

//	@Test
//	public void AddVehicle_101() {
//		testUtil.ExecuteTest("101");
//	}

	@BeforeSuite(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	public void beforeSuite() throws IOException {
		String homepath = new File(".").getCanonicalPath();
		extent = new ExtentReports(homepath + "\\Automation_Report\\" + "Run_" + Util.getCurrentDate() + "_"
				+ Util.getCurrentTime() + "\\ReportSummary.html");

	}

	@BeforeMethod(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	protected void beforeMethod(Method method, Object[] params) {
		super.beforeMethod(method, params);
		test = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), method.getName());
		test.assignAuthor("VAM QA");
		test.assignCategory(method.getName());
	}
	
	@SuppressWarnings("unchecked")
	@Test(description = "VM Automation Framework", groups = { 
			"QA" }, timeOut = 500000000)
	public void VM_Test_One() {
		testUtil.ExecuteTest("101", getSe());
	}
	
	@SuppressWarnings("unchecked")
	@Test(description = "VM Automation Framework", groups = { "VMTest"
			 }, timeOut = 500000000)
	public void VM_Test_Two() {
		testUtil.ExecuteTest("101", getSe());
	}

	@AfterMethod(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	protected void afterMethod(Method method, ITestResult result, Object[] params) {
		super.afterMethod(method, result, params);
		extent.endTest(test);
	}

	@AfterSuite(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	public void afterSuite() throws IOException {
		extent.flush();
		extent.close();
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
