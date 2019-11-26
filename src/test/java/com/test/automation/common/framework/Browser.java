package com.test.automation.common.framework;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.automation.common.SeHelper;

import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class Browser {
	private SeHelper se;

	// Globals
	private String prevWindow = null; // Previous Window Name
	private String prevWindowHandle = null; // Previous Window Handle
	private String currentPopUp = null; // Popup window handle

	/**
	 * Supported Browsers
	 */
	public static enum Browsers {
		HtmlUnit, Chrome, InternetExplorer, SauceChrome, SauceIE, GridChrome, GridInternetExplorer, VMChrome, VMIE,
		edgedriver, geckodrivers
	};

	// Wait Conditions
	private static ExpectedCondition<Boolean> windowTitleContains(final String windowTitle) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return driver.getTitle().toLowerCase().replaceAll("-", "").replaceAll(" ", "")
						.contains(windowTitle.toLowerCase().replaceAll("-", "").replaceAll(" ", ""));
			}
		};
	}

	/**
	 * Description: Default Constructor
	 * 
	 * @param se
	 */
	public Browser(SeHelper se) {
		this.se = se;
	}

	/**
	 * Description: Get current browser type
	 *
	 * @return
	 */
	public Browsers getBrowserType() {
		return se.currentBrowser();
	}

	/**
	 * Description: Close an Alert Pop Up
	 *
	 * @return
	 */
	public boolean closeAlert() {
		// Get a handle to the open alert, prompt or confirmation
		try {
			se.util().sleep(2000);
			Alert alert = se.driver().switchTo().alert();

			// And acknowledge the alert (equivalent to clicking "OK")
			alert.accept();
			se.log().logSeStep("Closing Alert");
			return true;
		} catch (Exception e) {
			// no alert
			// se.log().logSeStep("Cannot Close Alert");
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * Description: Get the window handle by iterating all windows searching for the
	 * title.
	 *
	 * @param title
	 * @return Window handle of window with the specified title.
	 */
	
	
	
	
	private String getHandleByTitle(String title) {
		String currentHandle = se.driver().getWindowHandle();
		for (String handle : se.driver().getWindowHandles()) {
			if (title.equals(se.driver().switchTo().window(handle).getTitle())) {
				se.log().trace("Found new window handle by title: " + handle);
				return handle;
			}
		}
		se.log().trace("Could not find new window handle by title");
		return currentHandle;
	}

	/**
	 * Description: Get the window handle by iterating all windows searching for the
	 * url.
	 *
	 * @param url
	 * @return Window handle of window with the specified url
	 */
	private String getHandleByUrl(String url) {
		String currentHandle = se.driver().getWindowHandle();
		for (String handle : se.driver().getWindowHandles()) {
			if ((se.driver().switchTo().window(handle).getCurrentUrl()).contains(url)) {
				return handle;
			}
		}
		return currentHandle;
	}
	
	
	

	/**
	 * Description: Gets the browser version
	 *
	 * @return
	 */
	public String getBrowserVersion() {
		if (se.currentBrowser() == Browsers.HtmlUnit)
			return "0.0";
		else {
			Capabilities browserCapabilities = ((RemoteWebDriver) se.driver()).getCapabilities();
			return browserCapabilities.getVersion();
		}
	}
	
	
	

	/**
	 * Description: Maximize the browser
	 */

	public void maximizeBrowser() {
		se.driver().manage().window().maximize();
	}

	public void setBrowserSize(int width, int height) {
		Dimension targetSize = new Dimension(width, height);
		se.driver().manage().window().setSize(targetSize);
	}

	public void setBrowserPosition(Point p) {
		se.driver().manage().window().setPosition(p);
	}

	/**
	 * Description: Delete the cookies for the current domain (page must be opened)
	 */
	public void deleteCookies() {
		se.driver().manage().deleteAllCookies();
	}

	/**
	 * Description: Deletes a specific cookie
	 *
	 * @param name Name of cookie to delete
	 */
	public void deleteCookieNamed(String name) {
		se.driver().manage().deleteCookieNamed(name);
	}

	/**
	 * Description: Used for initial get of first page Log.setTcStartTime(); is
	 * called. use get(url) to get pages without overwriting the start time.
	 *
	 * @param url
	 */
	public boolean openUrl(String url) {
		se.log().logSeStep("Open URL: " + url);
		try {
			se.driver().get(url);
			// deleteCookies();
			se.log().setTcStartTime();
			// se.driver().get(url);
		} catch (Exception e) {
			se.log().logTcError(String.format("Unhandled Exception in openUrl %s: %s: %s: %s", url, e, e.getMessage(),
					ExceptionUtils.getFullStackTrace(e)), takeScreenShot());
			return false;
		}
		return se.driver().getTitle() != null;
	}

	/**
	 * Description: Load a new url in the browser. Does not log the test case start
	 * time. Use openUrl() instead, if this is the first url loaded for the test.
	 *
	 * @param url
	 */
	public boolean get(String url) {
		se.log().logSeStep("Get URL: " + url);
		if (url == null) {
			se.log().logSeStep("url is null, nothing to get");
			return false;
		}
		try {
			se.driver().get(url);
		} catch (Exception e) {
			System.out.println("Unhandled Exception in Browser.get " + url);
			System.out.println(e.getMessage());
			return false;
		}
		if (se.driver().getTitle() == null || se.driver().getTitle().equals("Problem loading page")
				|| se.driver().getTitle().contains("Oops! Google Chrome could not connect to")
				|| se.driver().getTitle().equals("Internet Explorer cannot display the webpage")) {
			return false;
		} else
			return true;
	}

	/**
	 * Description: Get the current url of the browser.
	 *
	 * @return The url as a string.
	 */
	public String getCurrentUrl() {

		try {
			return se.driver().getCurrentUrl();
		} catch (Exception e) {
			System.out.println("Unhandled Exception in getCurrentUrl");
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Description: Navigate Back in the browser
	 */
	public void navigateBack() {
		se.driver().navigate().back();
	}

	/**
	 * Description: Refresh current page
	 */
	public void refresh() {
		se.log().logSeStep("Refreshing Page");
		se.driver().navigate().refresh();
	}

	/**
	 * Description: Navigate Forward in the browser
	 */
	public void navigateForward() {
		se.driver().navigate().forward();
	}

	/**
	 * Description: Switch to a window using the title
	 *
	 * @param title of window
	 * @return true of successful
	 */
	public String switchToWindow(String title) {

		try {
			if (prevWindow == null || prevWindow != title) {
				prevWindow = se.driver().getTitle();
				prevWindowHandle = se.driver().getWindowHandle();
				se.log().logSeStep("Saved Prev Window Title:" + prevWindow);
			}
			se.log().logSeStep("Switching To Window Title:" + title);
			return getHandleByTitle(title);
		} catch (Exception e) {
			se.log().logSeStep("Un-handled Exception in switchToWindow:");
			se.log().logSeStep(e.getMessage());
		}

		return null;
	}
	

	public void switchToWindow(int index) {
		try {

			Set<String> windows = se.driver().getWindowHandles();
			Object[] arr = windows.toArray();
			se.driver().switchTo().window(((String)arr[index]));

		}
		catch(Exception e) {

		}
	}



	public void switchToIFrame(int index) {
		try {
		se.driver().switchTo().frame(index);
		}
		catch(Exception e) {

		}
	}
	/**
	 * Description: Return to the previous window, previous window was saved by
	 * swithToWindow()
	 *
	 * @return true of successful
	 */
	public boolean returnToPrevWindow() {
		if (prevWindowHandle == null) {
			se.log().logSeStep("No Prev Window Handle Stored");
			se.driver().switchTo().defaultContent();
			return false;
		} else {
			se.log().logSeStep("Switching to Prev Window Handle " + prevWindowHandle);
			se.driver().switchTo().window(prevWindowHandle);
			prevWindow = null;
			return true;
		}

	}

	/**
	 * Description: Closes current window and returns to the previous window, if
	 * this is the last window the browser will quit
	 *
	 * @return true if able to return to previous window, false of no more windows
	 *         are open
	 */
	public boolean closeCurrentWindow() {
		se.driver().close();
		return returnToPrevWindow();
	}

	/**
	 * Description: Gets the window Title
	 *
	 * @return
	 */
	public String getWindowTitle() {
		return se.driver().getTitle();
	}

	/**
	 * Gets the browser name
	 *
	 * @return
	 */
	public String getBrowserName() {
		Capabilities browserCapabilities = ((RemoteWebDriver) se.driver()).getCapabilities();
		return browserCapabilities.getBrowserName();
	}

	/**
	 * Description: Takes a screenshot and returns as a base64 encoded string.
	 * Doesn't add to report directly.
	 *
	 * @return base64 encoded string of screenshot
	 */
	public String takeScreenShot() {
		if (se.driver() == null)
			return null;
		WebDriver driver = se.driver();
		if (!(driver instanceof TakesScreenshot))
			driver = (new Augmenter()).augment(driver);
		try {
			return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		} catch (Exception e) {
			se.log().trace(e.getMessage());
			se.log().trace(e.getStackTrace().toString());
		}
		se.util().sleep(500);
		return null;
	}

	/**
	 * Description: Close browser and browser session.
	 * 
	 * Should be called after all tests !!
	 */
	public void quit() {
		try {
			if (se.driver() instanceof RemoteWebDriver && ((RemoteWebDriver) se.driver()).getSessionId() != null) {
				se.driver().close();
				se.driver().quit();
			} else {
				se.driver().quit();
			}
		} catch (Exception e) {

		}
	}

	public String toString() {
		return "Browser";
	}

	public void scrollPageDown() {

		JavascriptExecutor jse = (JavascriptExecutor) se.driver();
		jse.executeScript("scroll(0, 500);");

	}
	
	public void dismissPopup() {
		se.driver().switchTo().alert().dismiss();
	}
	
	public void acceptPopup() {
		se.driver().switchTo().alert().accept();
	}
	public void sendKeysPopup(String keys) {
		se.driver().switchTo().alert().sendKeys(keys);
	}
	
	
}