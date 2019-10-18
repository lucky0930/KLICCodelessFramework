package com.test.automation.common.framework;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.SeHelper;
import com.test.automation.common.framework.NoAssertionsException;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;

public class Verification {
	private SeHelper se;

	private StringBuilder result = new StringBuilder();
	int totalPass = 0;
	int totalFail = 0;
	int totalError = 0;
	int verificationCount = 0;
	boolean noscreenshot = true;

	private final String SETTING_SCREEN_SHOT_ON_FAIL_ONLY = "screenShotOnFailOnly";

	public Verification(SeHelper se) {
		this.se = se;
	}

	/**
	 * Clears counts of fails, passes and total verification calls.
	 */
	public void clearVerifications() {
		totalPass = 0;
		totalFail = 0;
		totalError = 0;
		verificationCount = 0;
	}

	/**
	 * Get count of all verifications run
	 * 
	 * @return count of verifications run
	 */
	public int getVerificationCount() {
		return verificationCount;
	}

	/**
	 * Get total passing verifications.
	 * 
	 * @return count of passing verifications
	 */
	public int getTotalPass() {
		return totalPass;
	}

	/**
	 * Get total failing verifications
	 * 
	 * @return count of failing verifications
	 */
	public int getTotalFail() {
		return totalFail;
	}

	/**
	 * Get total failing verifications
	 * 
	 * @return count of failing verifications
	 */
	public int getTotalError() {
		return totalError;
	}

	private boolean logFail(String testname, String message) {
		result.append("\n").append(testname).append(": ").append(message);
		String screenshotUrl = se.log().logTcVerification(verificationCount++, testname, se.browser().takeScreenShot(),
				Log.VERDICT.Fail);
		result.append("\n").append(screenshotUrl);
		totalFail++;
		return false;
	}

	private boolean logFailNoScreenShot(String testname, String message) {
		result.append("\n").append(testname).append(": ").append(message);
		se.log().logTcVerification(verificationCount++, testname, null, Log.VERDICT.Fail);
		totalFail++;
		return false;
	}

	private boolean logPass(String testname) {

		boolean screenShotOnFailOnly = false;
		String screenShotBase64 = null;
		try {
			screenShotOnFailOnly = se.settings().asBoolean(SETTING_SCREEN_SHOT_ON_FAIL_ONLY);
		} catch (Exception e) {
		}

		if (!screenShotOnFailOnly) {
			screenShotBase64 = se.browser().takeScreenShot();
		}

		se.log().logTcVerification(verificationCount++, testname, screenShotBase64, Log.VERDICT.Pass);
		// result.append("\n").append(screenshotUrl);
		totalPass++;
		return true;

	}

	private boolean logPass(String testname, boolean noscreenshot) {

		String screenShotBase64 = null;
		se.log().logTcVerification(verificationCount++, testname, screenShotBase64, Log.VERDICT.Pass);
		totalPass++;
		return true;

	}

	private void logError(String errorName, String screenShotBase64) {
		result.append("\nERROR: ").append(errorName);
		se.log().trace("<strong class='error-label'>ERROR: </strong><span class='error-body'>" + errorName + "</span>");
		se.log().logTcVerification(verificationCount++, errorName, screenShotBase64, Log.VERDICT.Error);
		totalError++;
	}

	/**
	 * Verify that two Strings are equivalent
	 *
	 * Logs pass/fail based on results.
	 *
	 * @param testname
	 *            Name of this test
	 * @param actual
	 *            Actual string found
	 * @param expected
	 *            Expected string
	 * @return True if strings match
	 * @throws IOException 
	 */
	public boolean verifyEquals(String testname, String actual, String expected,ExtentTest test) throws IOException {
		String vpName = getFormattedVPTestName(testname);
		String screenshot = Util.captureScreenshot(testname, se);
		String image = "";
		se.log().logSeStep(
				String.format("%s: Verify Strings Equal Actual: '%s' Expected: '%s'", vpName, actual, expected));
		try {
			Assert.assertEquals(actual, expected, vpName);
			logPass(testname);
			test.log(LogStatus.PASS, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected);
			return true;
		} catch (AssertionError e) {
			image = test.addScreenCapture(screenshot);
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		} catch (Exception e) {
			image = test.addScreenCapture(screenshot);
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		}
	}

	/**
	 * Verify that two Strings are equivalent
	 *
	 * Logs pass/fail based on results.
	 *
	 * @param testname
	 *            Name of this test
	 * @param actual
	 *            Actual string found
	 * @param expected
	 *            Expected string
	 * @return True if strings match
	 * @throws IOException 
	 */
	public boolean verifyEquals(String testname, String actual, String expected, boolean noscreenshot, ExtentTest test) throws IOException {
		String vpName = getFormattedVPTestName(testname);
		String screenshot = Util.captureScreenshot(testname, se);
		String image = "";
		se.log().logSeStep(
				String.format("%s: Verify Strings Equal Actual: '%s' Expected: '%s'", vpName, actual, expected));
		try {
			Assert.assertEquals(actual, expected, vpName);
			logPass(testname, noscreenshot);
			test.log(LogStatus.PASS, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected);
			return true;
		} catch (AssertionError e) {
			image = test.addScreenCapture(screenshot);
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName+ " is <b style='color:red;'>NOT displayed </b>", "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		} catch (Exception e) {
			image = test.addScreenCapture(screenshot);
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName+ " is <b style='color:red;'>NOT displayed </b>", "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		}
	}

	public boolean verifyEquals(String testname, boolean actual, boolean expected, boolean noscreenshot, ExtentTest test) throws IOException {
		String vpName = getFormattedVPTestName(testname);
		String screenshot = Util.captureScreenshot(testname, se);
		String image = "";
		se.log().logSeStep(
				String.format("%s: verify boolean Actual: '%s' Expected: '%s'", vpName, actual, expected));
		try {
			Assert.assertEquals(actual, expected, vpName);
			logPass(testname, noscreenshot);
			test.log(LogStatus.PASS, vpName+" is displayed", "<br>Actual: "+actual+"<br>Expected: "+expected);
			return true;
		} catch (AssertionError e) {
			image = test.addScreenCapture(screenshot);
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName+ " is <b style='color:red;'>NOT displayed </b>", "<br>Actual: "+actual+"<br>Expected: "+expected+"<br>"+image);
			return false;
		} catch (Exception e) {
			image = test.addScreenCapture(screenshot);
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName+ " is <b style='color:red;'>NOT displayed </b>", "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		}
	}

	/**
	 * Verify that two doubles are equivalent within acceptable delta
	 *
	 * Logs pass/fail based on results.
	 *
	 * @param testname
	 *            Name of this test
	 * @param actual
	 *            Actual double found
	 * @param expected
	 *            Expected double
	 * @param delta
	 *            Delta double
	 * @return True if double match
	 * @throws IOException 
	 */
	public boolean verifyEquals(String testname, double actual, double expected, double delta, ExtentTest test) throws IOException {
		String vpName = getFormattedVPTestName(testname);
		String screenshot = Util.captureScreenshot(testname, se);
		String image = test.addScreenCapture(screenshot);
		se.log().logSeStep(
				String.format("%s: Verify Double Equal Actual with in delta: '%f' Expected: '%f' Delta : '%f'", vpName,
						actual, expected, delta));
		try {
			Assert.assertEquals(actual, expected, delta, vpName);
			logPass(testname);
			test.log(LogStatus.PASS, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected);
			return true;
		} catch (AssertionError e) {
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		} catch (Exception e) {
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		}
	}

	/**
	 * Verify that two integers are equivalent
	 *
	 * Logs pass/fail based on results.
	 *
	 * @param testname
	 *            Name of this test
	 * @param actual
	 *            Actual integer found
	 * @param expected
	 *            Expected integer
	 * @return True if integers match
	 * @throws IOException 
	 */
	public boolean verifyEquals(String testname, int actual, int expected, ExtentTest test) throws IOException {
		String vpName = getFormattedVPTestName(testname);
		String screenshot = Util.captureScreenshot(testname, se);
		String image = test.addScreenCapture(screenshot);
		se.log().logSeStep(
				String.format("%s: Verify Integers Equal Actual: '%d' Expected: '%d'", vpName, actual, expected));
		try {
			Assert.assertEquals(actual, expected, vpName);
			logPass(testname);
			test.log(LogStatus.PASS, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected);
			return true;
		} catch (AssertionError e) {
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		} catch (Exception e) {
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		}
	}

	/**
	 * Needs to be called at the end of all tests. If not called, your test will
	 * not report/log correctly.
	 *
	 * Closes the test session results, recording end time, and logging counts
	 * of pass/fail verifications.
	 *
	 * Logs screenshots and test statistics into CouchDB.
	 *
	 * If there are no verifications or there are any failing verifications,
	 * will cause the test to be reported as failed.
	 */
	public void checkForFail() {
		// Close the log
		finalizeLog();

		// Calculate pass/fail
		boolean testPassed = isTestPassed();

		// Write CouchDB record
		se.log().couchDb(testPassed, verdictText());

		// Give browser a chance to close if needed
		closeBrowserIfNeeded();

		// Save our log (for below), then clear result log
		StringBuilder testResults = getCurrentResults();

		// if we have a failure assertion, toss it now
		if (hasFailedVerifications()) {
			result.setLength(0);
			throw new AssertionError(testResults);
		} else if (!hasVerifications()) {
			testResults.append("There were no assertions");
			throw new NoAssertionsException(testResults.toString());
		}
	}

	/**
	 * Returns if the current log shows the test as passing.
	 *
	 * Passing is defined as having at least one passing verification and no
	 * failed verifications.
	 *
	 * Having no verifications is considered not passing, so tests will be
	 * "failed" until they verify something.
	 *
	 * @return true if there are passing verifications and no failures, else
	 *         false
	 */
	public boolean isTestPassed() {
		// return !hasVerifications() || hasFailedVerifications();
		return hasVerifications() && !hasFailedVerifications();
	}

	public boolean hasFailedVerifications() {
		return totalFail > 0;
	}

	public boolean hasErrorVerifications() {
		return totalError > 0;
	}

	public boolean hasVerifications() {
		return totalPass > 0;
	}

	/**
	 * Gives you a read-only copy of the current results output (failed
	 * verification info).
	 *
	 * @return StringBuilder copy of current results object.
	 */

	public StringBuilder getCurrentResults() {
		return new StringBuilder(result);
	}

	/**
	 * Adds final test statistics to the log object and dumps the output of the
	 * log to the console for debugging.
	 */
	public void finalizeLog() {
		se.log().setTcEndTime();
		se.log().setTcVerificationsPass(Integer.toString(totalPass));
		se.log().setTcVerificationsFail(Integer.toString(totalFail));
		se.log().setTcVerificationsError(Integer.toString(totalError));
		Reporter.log("<span class=summary><b>Test Summary</b> Verifications Passed: <font color=\"green\">" + totalPass
				+ "</font> Verifications Failed: <font color=\"red\">" + totalFail
				+ "</font> Verifications ERROR: <font color=\"red\">" + totalError + "</font></span> <br>");
		if (se.log().hasLog()) {
			se.log().printLogBuilder();
		}
	}

	/**
	 * If running on the grid, close the browser. If running locally, only close
	 * the browser if the test passed.
	 */
	public void closeBrowserIfNeeded() {
		String seHelperName = se.toString();
		if (seHelperName.contains("Grid") || isTestPassed()) {
			se.log().logSeStep("Closing browser: " + seHelperName);
			se.browser().quit();
		} else {
			se.log().logSeStep("Leaving browser open: " + seHelperName);
		}
	}

	public String verdictText() {
		if (getTotalFail() > 0)
			return "Fail";
		else if (getTotalPass() > 0)
			return "Pass";
		else if (getVerificationCount() < 1)
			return "Error";
		return "null";
	}

	public void throwAssertionError() {
		if (se.log().hasLog()) {
			se.log().printLogBuilder();
		}
		se.log().couchDb(false, verdictText());
		StringBuilder temp = new StringBuilder(result);
		result.setLength(0);
		se.browser().quit();
		throw new AssertionError(temp);
	}

	public boolean verifyEquals(String testname, boolean actual, boolean expected, ExtentTest test) throws IOException {
		String vpName = getFormattedVPTestName(testname);
		String screenshot = Util.captureScreenshot(testname, se);
		String image = test.addScreenCapture(screenshot);
		se.log().logSeStep(String.format("%s: verify boolean Actual: '%s' Expected: '%s'", vpName, actual, expected));
		try {
			Assert.assertEquals(actual, expected, vpName);
			logPass(testname);
			test.log(LogStatus.PASS, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected);
			return true;
		} catch (AssertionError e) {
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected+"<br>"+image);
			return false;
		} catch (Exception e) {
			logFail(testname, e.getMessage());
			test.log(LogStatus.FAIL, vpName, "<br> Actual: "+actual+"<br> Expected: "+expected);
			return false;
		}
	}
	
	public boolean verifyEqualsNoScreenshot(String testname, boolean actual, boolean expected) throws IOException {
		String vpName = getFormattedVPTestName(testname);
		se.log().logSeStep(String.format("%s: verify boolean Actual: '%s' Expected: '%s'", vpName, actual, expected));
		try {
			Assert.assertEquals(actual, expected, vpName);
			logPass(testname);
			return true;
		} catch (AssertionError e) {
			logFail(testname, e.getMessage());
			return false;
		} catch (Exception e) {
			logFail(testname, e.getMessage());
			return false;
		}
	}

	public boolean verifyTrue(String testname, boolean actual, ExtentTest test) throws IOException {
		return verifyEquals(testname, actual, true,test);
	}

	public boolean verifyFalse(String testname, boolean actual, ExtentTest test) throws IOException {
		return verifyEquals(testname, actual, false,test);
	}

	public boolean verifyContains(String testname, String actual, String expected) {
		String vpName = getFormattedVPTestName(testname);
		se.log().logSeStep(String.format("%s: verify Contains Actual: '%s' Expected: '%s'", vpName, actual, expected));
		try {
			if (!actual.contains(expected))
				Assert.fail(vpName);
			logPass(testname);
			return true;
		} catch (AssertionError e) {
			if (actual == null)
				logFailNoScreenShot(testname, "Actual is null");
			else
				logFail(testname, "Expected String '" + expected + "' not found in '" + actual + "'");
			return false;
		} catch (Exception e) {
			logFail(testname, e.getMessage());
			return false;
		}
	}

	private String getFormattedVPTestName(String testname) {
		return String.format("VP%02d %s", verificationCount, escapeTestName(testname));
	}

	private String escapeTestName(String testname) {
		return testname.replaceAll("\n", " ");
	}

	public String attachScreenshot(String screenshotName) {
		return se.log().logTcVerification(verificationCount++, screenshotName, se.browser().takeScreenShot(),
				Log.VERDICT.Pass);
	}

	public void reportError(String message) {
		logError(message, se.browser().takeScreenShot());
	}

	public void reportError(String message, String screenShotBase64) {
		logError(message, screenShotBase64);
	}

	public void clearReport() {
		result.setLength(0);
	}

	public String toString() {
		return "Verification";
	}
}
