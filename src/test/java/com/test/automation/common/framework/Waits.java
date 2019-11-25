package com.test.automation.common.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.automation.common.SeHelper;

public class Waits {
	private SeHelper se;

	private int defaultTimeOut = 20;
	private int globalSeTimeOut = 20;

	private boolean inFrame = false;

	public Waits(SeHelper se) {
		this.se = se;
	
	}

	/**
	 * Change the default timeout for the waits
	 *
	 * @param seconds
	 */
	public void setTimeOut(int seconds) {
		globalSeTimeOut = seconds;
	}

	/**
	 * Reset the timeout back to default
	 */
	public void resetTimeOut() {
		globalSeTimeOut = defaultTimeOut;
	}

	/**
	 * Gets the current setting for the timeout
	 *
	 * @return the current timeout setting
	 */
	public int getTimeOut() {
		return globalSeTimeOut;
	}

	/**
	 * Wait for an element, using the default timeout Element.globalSeTimeOut
	 *
	 * @param locator
	 * @return
	 */
	public boolean waitForElement(final By locator) {
		return waitForElement(locator, globalSeTimeOut);
	}

	/**
	 * Wait for an element to be displayed, using the default timeout
	 * Element.globalSeTimeOut
	 *
	 * @param locator
	 * @return
	 */
	public boolean waitForElementIsDisplayed(final By locator) {
		return waitForElementIsDisplayed(locator, globalSeTimeOut);
	}

	/**
	 * Wait for an element to be displayed, using the default timeout
	 * Element.globalSeTimeOut
	 *
	 * @param element
	 * @return
	 */
	public boolean waitForElementIsDisplayed(WebElement element) {
		return waitForElementIsDisplayed(element, globalSeTimeOut);
	}

	/**
	 * Wait for an element, using specified timeout
	 *
	 * @param locator
	 * @param timeOutInSeconds
	 * @return
	 */
	

	
	public boolean waitForElementIsClickable(WebElement element) {
		
		try {
			new WebDriverWait( se.driver(),globalSeTimeOut).until(ExpectedConditions
					.elementToBeClickable(element));
			return true;
			
		}catch (TimeoutException e) {
			se.log().logSeStep("Timed out waiting for element " + element.toString());
			se.verify().reportError("Timed Out Waiting For Element");
			return false;
		} catch (Exception e) {
			String errorName = "Un-handled Exception in waitForElement: ";
			se.log().logSeStep(errorName + e + ": " + e.getMessage());
			return false;
		}
		
	}
	
	
	public boolean waitForElementVisible(final By locator) {
		
		try {
			new WebDriverWait( se.driver(),globalSeTimeOut).until(ExpectedConditions
				.invisibilityOfElementLocated(locator));
			return true;
			
		}catch (TimeoutException e) {
			se.log().logSeStep("Timed out waiting for element " + locator.toString());
			se.verify().reportError("Timed Out Waiting For Element");
			return false;
		} catch (Exception e) {
			String errorName = "Un-handled Exception in waitForElement: ";
			se.log().logSeStep(errorName + e + ": " + e.getMessage());
			return false;
		}
		
	}
	
	public boolean waitForPageLoad() {
		
		try {
			new WebDriverWait(se.driver(), 10000).until(WebDriver -> ((JavascriptExecutor) WebDriver)
		            .executeScript("return document.readyState").equals("complete"));
			return true;
			
		}catch (TimeoutException e) {
			//se.log().logSeStep("Timed out waiting for element " + element.toString());
			se.verify().reportError("Timed Out Waiting For Element");
			return false;
		} catch (Exception e) {
			String errorName = "Un-handled Exception in waitForElement: ";
			se.log().logSeStep(errorName + e + ": " + e.getMessage());
			return false;
		}
		
	}

	public boolean waitForElement(final By locator, int timeOutInSeconds) {
		try {
			new WebDriverWait(se.driver(), timeOutInSeconds).ignoring(RuntimeException.class)
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(locator);
						}
					});
			return true;
		} catch (TimeoutException e) {
			se.log().logSeStep("Timed out waiting for element " + locator.toString());
			se.verify().reportError("Timed Out Waiting For Element");
			return false;
		} catch (Exception e) {
			String errorName = "Un-handled Exception in waitForElement: ";
			se.log().logSeStep(errorName + e + ": " + e.getMessage());
			return false;
		}

	}

	/**
	 * Wait for an element to be displayed, using specified timeout
	 *
	 * @param locator
	 * @param timeOutInSeconds
	 * @return
	 */
	public boolean waitForElementIsDisplayed(final By locator, int timeOutInSeconds) {
		try {
			new WebDriverWait(se.driver(), timeOutInSeconds).ignoring(RuntimeException.class)
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return d.findElement(locator).isDisplayed();
						}
					});
			return true;
		} catch (TimeoutException e) {
			se.log().logSeStep("Timed out waiting for element " + locator.toString());
			se.verify().reportError("Timed Out Waiting For Element");
			return false;
		} catch (Exception e) {
			String errorName = "Un-handled Exception in waitForElement: ";
			se.log().logSeStep(errorName + e + ": " + e.getMessage());
			return false;
		}
	}

	/**
	 * Wait for an element to be displayed, using specified timeout
	 *
	 * @param element
	 * @param timeOutInSeconds
	 * @return
	 */
	public boolean waitForElementIsDisplayed(final WebElement element, int timeOutInSeconds) {
		try {
			new WebDriverWait(se.driver(), timeOutInSeconds).ignoring(RuntimeException.class)
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return element.isDisplayed();
						}
					});
			return true;
		} catch (TimeoutException e) {
			se.log().logSeStep("Timed out waiting for element " + element.toString());
			se.verify().reportError("Timed Out Waiting For Element");
			return false;
		} catch (Exception e) {
			String errorName = "Un-handled Exception in waitForElement: ";
			se.log().logSeStep(errorName + e + ": " + e.getMessage());
			return false;
		}

	}

	/**
	 * Returns true if element exists
	 *
	 * @param locator
	 * @return
	 */
	public boolean exists(final By locator) {
		// se.log().logSeStep("Checking if Element exists: " +
		// locator.toString());
		//se.driver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			se.driver().findElement(locator);
			return true;
		} catch (Exception e) {
			// swallow
			return false;
		}
	}
	
	
	/**
	 * Wait for an element such as a spinner or modal, to go away.
	 *
	 * @param locator
	 * @return true if element disappears within the timeout limit
	 */
	public boolean waitForElementToDisappear(final By locator, int timeoutSeconds) {
		boolean elementIsFound = true;
		int timeout = 0;
		// se.log().logSeStep("Waiting for element: " + locator.toString() + "
		// to disappear");
		do {
			try {
				WebElement element = se.driver().findElement(locator);
				if (element.isDisplayed() && element.isEnabled()) // &&
																	// !element.getAttribute("style").contains("none")
				{
					se.util().sleep(1000);
					timeout++;
				} else
					elementIsFound = false;
			} catch (Exception e) {
				elementIsFound = false;
			}
		} while (elementIsFound && (timeout < timeoutSeconds));

		if (timeout == timeoutSeconds)
			se.log().logSeStep("Timed out in waitForElementToDisappear");
		return !elementIsFound;
	}
	
	public boolean waitForElementToDisappear(WebElement element, int timeoutSeconds) {
		boolean elementIsFound = true;
		int timeout = 0;
		// se.log().logSeStep("Waiting for element: " + locator.toString() + "
		// to disappear");
		do {
			try {
				if (element.isDisplayed() && element.isEnabled()) // &&
																	// !element.getAttribute("style").contains("none")
				{
					se.util().sleep(1000);
					timeout++;
				} else
					elementIsFound = false;
			} catch (Exception e) {
				elementIsFound = false;
			}
		} while (elementIsFound && (timeout < timeoutSeconds));

		if (timeout == timeoutSeconds)
			se.log().logSeStep("Timed out in waitForElementToDisappear");
		return !elementIsFound;
	}
	
	
	/**
	 * Wait for an element to be displayed, using specified timeout
	 *
	 * @paramelement
	 * @paramtimeOutInSeconds
	 * @return
	 */

	public void waitForElementLoading(WebElement we) {

		try {
			int i = 0;
			while (i < 10) {
				se.util().sleep(1000);
				waitForElementIsDisplayed(we);
				if (se.element().isElementPresent(we))
					break;

				i = i + 1;
			}

		} catch (Exception e) {
			System.out.println(
					"Not able to locate the element on the page OR the element did not load on the page in less than 10 seconds during the test run: "
							+ e.getMessage());
		}
	
}
	}
