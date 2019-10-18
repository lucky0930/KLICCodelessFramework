package com.test.automation.common.framework;

/**
 *
 * Helper class to detect which OS the tests are running under.
 */
public class OSTools {
	private static final String USER_HOME = System.getProperty("user.home");
	private static final String USER_NAME = System.getProperty("user.name");
	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
	
	private OSTools() { /* Static Class not intended to be constructed */ }
	
	public static boolean isWindows() {
		return OS_NAME.indexOf("win") >= 0;
	}
	
	public static String getUsername() {
		return USER_NAME;
	}

	public static String getUserHome() {
		return USER_HOME;
	}

}