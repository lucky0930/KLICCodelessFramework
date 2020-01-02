package com.test.automation.common;

import java.net.Socket;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import com.test.automation.common.Utils.ExcelReader;

public class SystemPropertyUtil {

	private final static String windowWidthKey = "window.width";
	private final static String windowHeightKey = "window.height";
	private final static String testDataDirectorykey = "test.data.directory";
	private final static String httpCredentialsKey = "http.credentials";
	private final static String browsersKey = "browsers";
	private final static String baseUrlKey = "base.url";
	private final static String localeUrlKey = "locale.url";
	// private final static String baseUrlDefault =
	// "http://138.91.124.246:1050/pages/login.html";
	private static PullFromConfig config = new PullFromConfig();

	private static String baseUrlDefault = config.getConfigProp("BaseURL");
	private final static String localeUrlDefault = "";

	// Default values
	private final static int windowWidthDefault = 1920;
	private final static int windowHeightDefault = 1080;
	private final static String testDataDirectoryDefault = "target/test-classes";
	private final static String httpCredentialsDefault = "true";
	// private final static String browsersDefault = "Chrome";
	private static String browsersDefault = config.getConfigProp("Browser");

	// file paths
	private final static String testDataPath = config.getConfigProp("TestDataPath");
	private final static String testRunnerPath = config.getConfigProp("TestRunnerPath");
	private final static String extentReportPath = config.getConfigProp("ExtentReportPath");
	private final static String recentReportPath = config.getConfigProp("RecentReportPath");
	private final static String logFilePath = config.getConfigProp("LogFilePath");
	private final static String screenshotPath = config.getConfigProp("ScreenshotPath");
	private final static String chromeDriverPath = config.getConfigProp("ChromeDriverPath");
	private final static String geckoDriverPath = config.getConfigProp("GeckoDriverPath");
	private final static String edgeDriverPath = config.getConfigProp("EdgeDriverPath");
	private final static String ieDriverPath = config.getConfigProp("ieDriverPath");
	
	// waits
	private final static String implicitWaitTime = config.getConfigProp("ImplicitWaitTime");
	private final static String explicitWaitTime = config.getConfigProp("ExplicitWaitTime");

	// extra options
	private static String runInParallel = config.getConfigProp("RunInParallel");
	private static String numberOfBrowsers = config.getConfigProp("NumberOfBrowsers");
	private static String continueIfException = config.getConfigProp("ContinueIfException");

	private final static int windowWidth = System.getProperties().containsKey(windowWidthKey)
			? Integer.parseInt(System.getProperty(windowWidthKey))
			: windowWidthDefault;
	private final static int windowHeight = System.getProperties().containsKey(windowHeightKey)
			? Integer.parseInt(System.getProperty(windowHeightKey))
			: windowHeightDefault;
	private final static String browsers = System.getProperties().containsKey(browsersKey)
			? System.getProperty(browsersKey)
			: browsersDefault; // new enums

	private final static String testDataDirectory = System.getProperties().containsKey(testDataDirectorykey)
			? System.getProperty(testDataDirectorykey)
			: testDataDirectoryDefault;
	private final static boolean httpCredentials = Boolean.parseBoolean(
			System.getProperties().containsKey(httpCredentialsKey) ? System.getProperty(httpCredentialsKey)
					: httpCredentialsDefault);

	private final static String baseUrl = System.getProperties().containsKey(baseUrlKey)
			? System.getProperty(baseUrlKey)
			: baseUrlDefault;
	// private final static String baseUrl = "google.co.in";
	private final static String localeUrl = System.getProperties().containsKey(localeUrlKey)
			? System.getProperty(localeUrlKey)
			: localeUrlDefault;

	public static String getBaseUrl() {
		return baseUrlDefault;
	}

	public static String getLocale() {
		return localeUrl;
	}

	public static int getWindowWidth() {
		return windowWidth;
	}

	public static int getWindowHeight() {
		return windowHeight;
	}

	public static String getTestDataDirectory() {
		return testDataDirectory;
	}

	public static boolean getHttpCredentials() {
		return httpCredentials;
	}

	public static String getBrowsers() {
		// translate old style to new SeHelper enums
		return browsers;
	}
	
	public static void updateBrowser(String newBrowser) {
		if (newBrowser.trim().isEmpty())
			return;
		else
			browsersDefault = newBrowser;
	}
	
	public static void updateBaseUrl(String newURL) {
		if (newURL.trim().isEmpty())
			return;
		else
			baseUrlDefault = newURL;
	}
	

	public static String getRootPath() throws IOException {
		return new File(".").getCanonicalPath();
	}

	public static String getContinueIfException() {
		return continueIfException.trim();
	}
	
	public static String runInParallel() {
		return runInParallel.trim();
	}
	
	public static void updateParallel(String option) {
		if (option.trim().isEmpty())
			return;
		else if (option.equalsIgnoreCase("Yes") || option.equalsIgnoreCase("No"))
			runInParallel = option;
		else
			System.out.println("IGNORED: Parallel execution only accepts \"Yes\" or \"No\" inputs.");
	}
	
	public static int getImplicitWaitTime() {
		return Integer.parseInt(implicitWaitTime);
	}

	public static int getExplicitWaitTime() {
		return Integer.parseInt(explicitWaitTime);
	}
	
	public static int getNumberOfBrowsers() {
		return Integer.parseInt(numberOfBrowsers);
	}
	
	public static void updateNumberOfBrowsers(String newNum) {
		if (newNum.trim().isEmpty())
			return;
		else
			numberOfBrowsers = newNum;
	}

	public static String getTestDataSheetPath() {
		try {
			return getRootPath() + testDataPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getTestRunnerPath() {
		try {
			return getRootPath() + testRunnerPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getLogFilePath() {
		try {
			return getRootPath() + logFilePath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getExtentReportPath() {
		try {
			return getRootPath() + extentReportPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getRecentReportPath() {
		try {
			return getRootPath() + recentReportPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getScreenshotPath() {
		try {
			return getRootPath() + screenshotPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getChromeDriverPath() {
		try {
			return getRootPath() + chromeDriverPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getGeckoDriverPath() {
		try {
			return getRootPath() + geckoDriverPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getEdgeDriverPath() {
		try {
			return getRootPath() + edgeDriverPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getIEDriverPath() {
		try {
			return getRootPath() + ieDriverPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

