package com.test.automation.common.framework;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.Period;

import javax.imageio.ImageIO;

import com.test.automation.common.SeHelper;
import com.test.automation.common.framework.OSTools;

@SuppressWarnings("unused")
public class Log {
	private static final Logger logger = LoggerFactory.getLogger(Log.class);
	private SeHelper se;
	private boolean logToCouchDB = true;
	private StringBuilder logBuilder = new StringBuilder();
	private JSONObject tcSteps = new JSONObject();
	private int tcStepsCount = 1;
	private int tcErrCount = 1;
	private String tcVerdict = "null";
	private String tcBrowserName = "";
	private String tcBrowserVersion = "";
	private static String tcBuild = "";
	private String tcStartTime = "";
	private String tcEndTime = "";
	private String tcVerificationsPass = "";
	private String tcVerificationsFail = "";
	private String tcVerificationsError = "";
	private String tcDriverVersion = "";
	private String tcPackageName = "";
	private String tcNameAppend = "";
	private JSONObject attachments = new JSONObject();
	private JSONObject verifications = new JSONObject();

	private static boolean saveScreenShots = true;
	private static boolean screenShotsOn = true;

	private String tcClassName;
	private String tcTestName;
	private String couchDBDocumentUID = "";
	private String couchDBLocation = "192.168.2.102:5984";

	public static enum VERDICT {
		Pass, Fail, Error
	}

	public Log(String couchDBLocation, String UID) {
		this.couchDBDocumentUID = UID;
		this.couchDBLocation = couchDBLocation;
	}

	public String getCouchDBUid() {
		return couchDBDocumentUID;
	}

	public String getCouchDBLocation() {
		return couchDBLocation;
	}

	public String getBrowserName() {
		return tcBrowserName;
	}

	public String getBrowserVersion() {
		return tcBrowserVersion;
	}

	public boolean hasLog() {
		return logBuilder.length() > 0;
	}

	public void setLogToCouchDB(boolean logToCouchDB) {
		this.logToCouchDB = logToCouchDB;
	}

	/**
	 * Concatinates two JSON Objects
	 *
	 * @param j1
	 * @param j2
	 * @return
	 * @throws JSONException
	 */
	public JSONObject concatJSON(JSONObject j1, JSONObject j2) throws JSONException {
		JSONObject result = new JSONObject();

		Iterator<?> keys = j1.keys();

		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (j1.get(key) instanceof JSONObject) {
				result.put(key, j1.get(key));
			}
		}

		keys = j2.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (j2.get(key) instanceof JSONObject) {
				result.put(key, j2.get(key));
			}
		}

		return result;
	}

	/**
	 * Turns off screen shot logging
	 */
	public void setNoScreenShots() {
		Log.screenShotsOn = true;
		Log.saveScreenShots = true;
	}

	/**
	 * set the browser name for the log
	 *
	 * @param browserName
	 */
	public void setTcBrowserName(String browserName) {
		tcBrowserName = browserName;
	}

	/**
	 * set the browser version for the log
	 *
	 * @param browserVersion
	 */
	public void setTcBrowserVersion(String browserVersion) {
		tcBrowserVersion = browserVersion;
	}

	/**
	 * set the build name for
	 *
	 * @param buildName
	 */
	public void setTcBuild(String buildName) {
		tcBuild = buildName;

	}

	/**
	 * set the start time for test
	 */
	public void setTcStartTime() {
		tcStartTime = com.test.automation.common.framework.Util
				.getDateStamp(com.test.automation.common.framework.Util.ISO8601_DATETIMESTAMP);
	}

	/**
	 * set the end time for the test
	 */
	public void setTcEndTime() {
		tcEndTime = com.test.automation.common.framework.Util
				.getDateStamp(com.test.automation.common.framework.Util.ISO8601_DATETIMESTAMP);
	}

	public String getTcStartTime() {
		return tcStartTime.toString();
	}

	public String getTcEndTime() {
		return tcEndTime.toString();
	}

	/**
	 * Set the number of passes
	 *
	 * @param totalPass
	 */
	public void setTcVerificationsPass(String totalPass) {
		tcVerificationsPass = totalPass;
	}

	/**
	 * Set the number of errors
	 *
	 * @param totalError
	 */
	public void setTcVerificationsError(String totalError) {
		tcVerificationsError = totalError;
	}

	public void setTcClassName(String tcClassName) {
		this.tcClassName = tcClassName;
	}

	public void setTcTestName(String tcTestName) {
		this.tcTestName = tcTestName;
	}

	/**
	 * Set the number of fails
	 *
	 * @param totalFail
	 */
	public void setTcVerificationsFail(String totalFail) {
		tcVerificationsFail = totalFail;
	}

	/**
	 * Adds a verification JSON object to the Log
	 *
	 * @param verificationNumber
	 * @param testname
	 * @param screenShotBase64
	 * @param result
	 * @return url to screenshot
	 */
	public String logTcVerification(int verificationNumber, String testname, String screenShotBase64, VERDICT verdict) {

		if (screenShotBase64 != null) {
			String couchAttachmentUrl = "";
			try {
				String vPointName = String.format("%02d", verificationNumber) + "_" + testname.replaceAll("\\W", "_");
				final JSONObject screenShot = new JSONObject();
				screenShot.put("content_type", "image/png");
				screenShot.put("data", screenShotBase64);
				attachments.put(vPointName + ".png", screenShot);
				couchAttachmentUrl = "http://" + couchDBLocation + "/test_results/" + getDocumentID() + "/" + vPointName
						+ ".png";
				screenshot(vPointName, couchAttachmentUrl, verdict);
				verifications.put(vPointName, verdict.name());
			} catch (JSONException e) {
				// TODO: handle exception
			}

			return couchAttachmentUrl;
		} else {
			try {
				String vPointName = String.format("%02d", verificationNumber) + "_" + testname.replaceAll("\\W", "_");
				verifications.put(vPointName, verdict.name());
			} catch (JSONException e) {
				// TODO: handle exception
			}
			return "";
		}

	}

	public String logTcError(String errorname, String screenShotBase64) {
		tcVerdict = "Error";
		trace("<strong class='error-label'>ERROR: </strong><span class='error-body'>" + errorname + "</span>");

		if (screenShotBase64 != null) {
			String couchAttachmentUrl = "";
			try {
				String errName = "ERROR_" + String.format("%02d", tcErrCount++) + "_"
						+ errorname.replaceAll("\\W", "_");
				final JSONObject screenShot = new JSONObject();
				screenShot.put("content_type", "image/png");
				screenShot.put("data", screenShotBase64);
				attachments.put(errName + ".png", screenShot);
				couchAttachmentUrl = "http://" + couchDBLocation + "/test_results/" + getTcName() + couchDBDocumentUID
						+ "/" + errName + ".png";

				String screenshotUrl = "<a class='crop' href=\"" + couchAttachmentUrl + "\" title=\"ScreenShot "
						+ errorname + " - Error\">" + "<div class='mask'><img src='" + couchAttachmentUrl
						+ "'></div><hr>" + "</a>";
				screenshot(errorname, couchAttachmentUrl, VERDICT.Error);
			} catch (JSONException e) {
				// TODO: handle exception
			}
			return couchAttachmentUrl;
		}
		return "";
	}

	@Deprecated
	public void logTcStep(String step) {
		logSeStep(step);
	}

	/**
	 * Add a Selenium level step to the report.
	 *
	 * @param step
	 *            Description of the Selenium Step
	 */
	public void logSeStep(String step) {
		// get current date
		String timeStamp = com.test.automation.common.framework.Util
				.getDateStamp(com.test.automation.common.framework.Util.ISO8601_TIMESTAMP);

		if (step.startsWith("VP")) {
			verification(step);
		} else {
			seStep(step);
		}

		try {
			final JSONObject tcStep = new JSONObject();
			tcStep.put("SE STEP " + step, timeStamp);
			tcSteps.put("Step " + String.format("%03d", tcStepsCount++), tcStep);
		} catch (JSONException e) {
			// TODO: handle exception
		}
	}

	/**
	 * Add a Test level step to the report.
	 *
	 * @param format
	 *            Description of the Test Step (in sprintf format)
	 * @param args
	 *            list of variable substitution parameters
	 */
	public void logTestStep(final String format, String... args) {
		logTestStep(String.format(format, new Object[] { args }));
	}

	/**
	 * Add a Test level step to the report.
	 *
	 * @param step
	 *            Description of the Test Step
	 */
	public void logTestStep(String step) {
		// get current date
		String timeStamp = com.test.automation.common.framework.Util
				.getDateStamp(com.test.automation.common.framework.Util.ISO8601_TIMESTAMP);
		testStepLogger(step);

		try {
			final JSONObject tcStep = new JSONObject();
			tcStep.put("TEST STEP " + step, timeStamp);
			tcSteps.put("Step " + String.format("%03d", tcStepsCount++), tcStep);
		} catch (JSONException e) {
			// TODO: handle exception
		}
	}

	/**
	 * Use logTestStep insted of testStep
	 *
	 * @deprecated use {@link logTestStep(step)} instead.
	 */
	@Deprecated
	public void testStep(String step) {
		logTestStep(step);
	}

	/**
	 * clear the couch db report
	 */
	public void clearCouchDBReport() {
		tcSteps = new JSONObject();
		tcStepsCount = 1;
		attachments = new JSONObject();
		verifications = new JSONObject();
		logBuilder.setLength(0);
	}

	/**
	 * Log a Selenium test to couch db
	 *
	 * @param verdict
	 */
	public void couchDb(boolean verdict, String verdictText) {
		if (logToCouchDB) {
			ITestResult itest = Reporter.getCurrentTestResult();
			String[] testClass = itest.getTestClass().getName().split("\\.");
			String tcLevel = "";
			String tcName = testClass[testClass.length - 1] + tcNameAppend;
			final String testResultsUrl = "http://" + couchDBLocation + "/test_results" + "/" + getTcName()
					+ couchDBDocumentUID;

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			String[] classpath = classLoader.getResource("").toString().split("/");
			tcLevel = (classpath[classpath.length - 1]).replace("Se", "");
			tcLevel += ".";
			for (int i = 0; i < testClass.length - 1; i++) {
				String level = testClass[i];
				tcLevel += level;
				if (i < testClass.length - 2)
					tcLevel += ".";
			}
			tcVerdict = verdictText;
			tcLevel = tcLevel.replace("test-classes.com", "");
			final JSONObject tcResult = new JSONObject();
			try {
				tcResult.put("tcAutonaut", OSTools.getUsername());
				tcResult.put("tcName", tcName);
				tcResult.put("tcLevel", tcLevel);
				tcResult.put("tcUI", tcLevel + "." + tcName);
				tcResult.put("tcType", "Functional");
				tcResult.put("tcFramework", "Selenium");
				tcResult.put("tcBuild", tcBuild);
				tcResult.put("tcBrowserName", tcBrowserName);
				tcResult.put("tcBrowserVersion", tcBrowserVersion);
				tcResult.put("tcStartTime", tcStartTime);
				tcResult.put("tcEndTime", tcEndTime);
				tcResult.put("tcVerdict", tcVerdict);
				tcResult.put("tcSteps", tcSteps);
				tcResult.put("tcLogFile", "file:\\\\blah\\");
				final JSONObject tcVerifications = new JSONObject();
				tcVerifications.put("Pass", tcVerificationsPass);
				tcVerifications.put("Fail", tcVerificationsFail);
				tcVerifications.put("tcVerificationDetail", verifications);
				tcResult.put("tcVerifications", tcVerifications);
				tcResult.put("_attachments", attachments);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			HttpClient client = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
																					// Limit
			HttpResponse response;

			try {
				HttpPut put = new HttpPut(new URI(testResultsUrl));
				ByteArrayEntity se = new ByteArrayEntity(tcResult.toString().toString().getBytes("UTF8"));
				se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));

				put.setEntity(se);

				response = client.execute(put);
				/* Checking response */
				if (response != null) {
					trace("Response: " + response.toString());
					Pattern docIdPatt = Pattern.compile("test_results\\/(.*), ETag:");
					Matcher docId = docIdPatt.matcher(response.toString());
					if (docId.find())
						trace("http://" + couchDBLocation + "/_utils/document.html?test_results/" + docId.group(1));
					// trace("http://" + couchDBLocation +
					// "/_utils/document.html?test_results/" + docId.group(1));
				}
			} catch (HttpHostConnectException e) {
				trace("Cannot Connect to CouchDB. Are you outside the firewall?");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		clearCouchDBReport();
	}

	/**
	 * Log Selenium Log to couchDb This overload, gets everything it needs from
	 * the ITestResult, simplifing the parameters Should be called from an
	 * AfterMethod, passing the ITestResult from the main test method through
	 * reflection example: protected void afterMethod(Method method, ITestResult
	 * result, Object[] params)
	 * 
	 * Normally the AfterMethod would use its own ITestResult, but in this case
	 * we want the logs from the Before and After methods to be included in the
	 * main test log
	 * 
	 * @param iTest
	 */
	public void couchDb(ITestResult iTest) {
		if (logToCouchDB) {
			String[] testClass = iTest.getTestClass().getName().split("\\.");
			String tcLevel = "";
			String tcName = testClass[testClass.length - 1] + tcNameAppend;
			final String testResultsUrl = "http://" + couchDBLocation + "/test_results" + "/" + getTcName(iTest)
					+ couchDBDocumentUID;

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			String[] classpath = classLoader.getResource("").toString().split("/");
			tcLevel = (classpath[classpath.length - 1]).replace("Se", "");
			tcLevel += ".";
			for (int i = 0; i < testClass.length - 1; i++) {
				String level = testClass[i];
				tcLevel += level;
				if (i < testClass.length - 2)
					tcLevel += ".";
			}
			int pass = tcVerificationsPass.length() > 0 ? Integer.parseInt(tcVerificationsPass) : 0;
			int fail = tcVerificationsFail.length() > 0 ? Integer.parseInt(tcVerificationsFail) : 0;

			if (fail > 0)
				tcVerdict = "Fail";
			else if (pass > 0)
				tcVerdict = "Pass";
			else if (pass + fail < 1)
				tcVerdict = "Error";

			tcLevel = tcLevel.replace("test-classes.com", "");
			final JSONObject tcResult = new JSONObject();
			try {
				tcResult.put("tcAutonaut", OSTools.getUsername());
				tcResult.put("tcName", tcName);
				tcResult.put("tcLevel", tcLevel);
				tcResult.put("tcUI", tcLevel + "." + tcName);
				tcResult.put("tcType", "Functional");
				tcResult.put("tcFramework", "Selenium");
				tcResult.put("tcBuild", tcBuild);
				tcResult.put("tcBrowserName", tcBrowserName);
				tcResult.put("tcBrowserVersion", tcBrowserVersion);
				tcResult.put("tcStartTime", tcStartTime);
				tcResult.put("tcEndTime", tcEndTime);
				tcResult.put("tcVerdict", tcVerdict);
				// tcResult.put("tcLog", couchDBReport.toString());
				tcResult.put("tcSteps", tcSteps);
				tcResult.put("tcLogFile", "file:\\\\blah\\");
				// json.put("tcVerificationsPass", tcVerificationsPass);
				// json.put("tcVerificationsFail", tcVerificationsFail);
				final JSONObject tcVerifications = new JSONObject();
				tcVerifications.put("Pass", tcVerificationsPass);
				tcVerifications.put("Fail", tcVerificationsFail);
				tcVerifications.put("tcVerificationDetail", verifications);
				tcResult.put("tcVerifications", tcVerifications);
				tcResult.put("_attachments", attachments);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			HttpClient client = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
																					// Limit
			HttpResponse response;

			try {
				HttpPut put = new HttpPut(new URI(testResultsUrl));
				ByteArrayEntity se = new ByteArrayEntity(tcResult.toString().toString().getBytes("UTF8"));
				se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));

				put.setEntity(se);

				response = client.execute(put);
				/* Checking response */
				if (response != null) {
					Pattern docIdPatt = Pattern.compile("test_results\\/(.*), ETag:");
					Matcher docId = docIdPatt.matcher(response.toString());
					if (docId.find())
						trace("http://" + couchDBLocation + "/_utils/document.html?test_results/" + docId.group(1));
					else
						trace("Response: " + response.toString());
				} else
					trace("Something happened while communicating with CouchDB, the response was null");
			} catch (HttpHostConnectException e) {
				trace("Cannot Connect to CouchDB. Are you outside the firewall?");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		clearCouchDBReport();
	}

	@Deprecated
	private void logInfo(String logString) {
		trace(logString);
	}

	private void verification(String logString) {
		logger.info(logString);
		logBuilder.append(logString).append("\n");
		writeLog("<div class=verification>" + "<strong class='verification-label'>VERIFICATION: </strong>" + logString
				+ "</div>");
	}

	private void testStepLogger(String logString) {
		logger.info(logString);
		logBuilder.append(logString).append("\n");
		writeLog(
				"<div class=test-step>" + "<strong class='teststep-label'>TEST STEP: </strong>" + logString + "</div>");
	}

	private void seStep(String logString) {
		logger.info(logString);
		logBuilder.append(logString).append("\n");
		writeLog("<div class=se-step>" + "<strong class='sestep-label'>SE STEP: </strong>" + logString + "</div>");
	}

	public void trace(String logString) {
		logger.trace(logString);
		logBuilder.append(logString).append("\n");
		writeLog("<div class=trace-step>" + logString + "</div>");
	}

	public void traceToConsole(String logString) {
		logger.trace(logString);
		logBuilder.append(logString).append("\n");
	}

	public void debug(String logString) {
		logger.debug(logString);
		logBuilder.append(logString).append("\n");
	}

	public void screenshot(String scresnshotName, String screenshotHref, VERDICT verdict) {
		String logString;
		switch (verdict) {
		case Pass:
			logString = "<a class='crop' href=\"" + screenshotHref + "\" title='ScreenShot " + scresnshotName
					+ " Pass'>" + "<div class='mask'><img src='" + screenshotHref + "'></div><hr>" + "</a>";
			writeLog("<div class='pass-screenshot'>" + logString + "</div>");
			break;
		case Fail:
			logString = "<a class='crop' href=\"" + screenshotHref + "\" title='ScreenShot " + scresnshotName
					+ " Fail'>" + "<div class='mask'><img src='" + screenshotHref + "'></div><hr>" + "</a>";
			writeLog("<div class='fail-screenshot'>" + logString + "</div>");
			break;
		case Error:
			logString = "<a class='crop' href=\"" + screenshotHref + "\" title='ScreenShot " + scresnshotName
					+ " Fail'>" + "<div class='mask'><img src='" + screenshotHref + "'></div><hr>" + "</a>";
			writeLog("<div class='error-screenshot'>" + logString + "</div>");
			break;
		default:
			break;
		}
	}

	/**
	 * Write to both the testng log and the couch db log
	 *
	 * @param logString
	 */
	private void writeLog(String logString) {
		Reporter.log(logString);
		// System.out.println(logString);

	}

	public void printLogBuilder() {
		System.out.println(logBuilder.toString());
		logBuilder.setLength(0);
	}

	public void appendToTcName(String tcName) {
		tcNameAppend = " - " + tcName;
	}

	public String getTcName() {
		if (this.tcTestName == null) {
			// Get the test name from the current Reporter test result
			return Reporter.getCurrentTestResult().getMethod().getMethodName();
		}
		return this.tcTestName;
	}

	/**
	 * Get the tcName from the ITestResult Object
	 * 
	 * @param itest
	 * @return
	 */
	public String getTcName(ITestResult iTest) {
		if (iTest != null) {
			String[] testClass = iTest.getTestClass().getName().split("\\.");
			return testClass[testClass.length - 1] + "_" + iTest.getMethod().getMethodName() + tcNameAppend;
		} else {
			return "junitClass_junitMethod" + tcNameAppend;
		}
	}

	public String toString() {
		return "Log";
	}

	public String getTcClassName() {
		if (this.tcClassName == null) {
			// Get the class name from the current Reporter test result
			String[] testClass = Reporter.getCurrentTestResult().getTestClass().getName().split("\\.");
			return testClass[testClass.length - 1];
		}
		return this.tcClassName;
	}

	private String getDocumentID() {
		// return getTcClassName() + "_" + getTcName() + couchDBDocumentUID;
		return getTcName() + couchDBDocumentUID;
	}

	public void setTcDriverVersion(String driverVersion) {
		this.tcDriverVersion = driverVersion;
	}

	public String getTcDriverVersion() {
		return this.tcDriverVersion;
	}

	public void setTcPackageName(String tcPackageName) {
		this.tcPackageName = tcPackageName;
	}

	public String getTcPackageName() {
		return tcPackageName;
	}

}