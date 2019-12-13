package com.test.automation.common.framework;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.Utils.TestUtil;

/**
 * Element wrapper class. Contains all things WebElement related. Each method
 * that does something must call se.log().logSeStep
 *
 * @author Sai Gnapika
 */
public class Element {
	private SeHelper se;

	private int defaultTimeOut = 20;
	private int globalSeTimeOut = 20;

	private boolean inFrame = false;

	public Element(SeHelper se) {
		this.se = se;
	}

	

	public By lnk_home = By.partialLinkText("Contract Home");

	public WebElement getHome() {
		se.waits().waitForElement(lnk_home);
		return se.element().getElement(lnk_home);
	}

	/**
	 * Finds and returns the element
	 *
	 * @param locator
	 * @return
	 */
	public WebElement getElement(final By locator) {
			return se.driver().findElement(locator);
	}
	
	public WebElement getElement(final By locator, boolean wait) {
			se.waits().waitForElement(locator);
			return se.driver().findElement(locator);
	}

	public static WebElement getElement(WebDriver driver, By locator) {
			return driver.findElement(locator);
	}

	/**
	 * Finds and returns a list of matching elements
	 *
	 * @param locator
	 * @return
	 */
	public List<WebElement> getElements(final By locator) {
		try {
			return se.driver().findElements(locator);
		} catch (Exception e) {
			String errorName = "Un-handled Exception in getElements:";
			se.log().logSeStep(errorName + e.getMessage());
			se.log().logTcError(errorName, se.browser().takeScreenShot());
			return new ArrayList<WebElement>();
		}
	}

	public boolean enterTextNoTAB(WebElement element, String testdata) {
		// se.log().logSeStep("Enter Text '" + testdata + "' in Element: " +
		// element.toString());
		if (element != null) {
			try {
				element.clear();
				element.sendKeys(testdata);
				return true;
			} catch (InvalidElementStateException e) {
				// se.log().logSeStep("Could not enter text in " + element.toString() + ",
				// element not visible or not enabled");
				// se.verify().reportError("Could not enter text, element not visible or not
				// enabled");
				return false;
			} catch (Exception e) {
				// se.log().logSeStep("Could not enter text in " + element.toString());
				// se.verify().reportError("Could not enter text");
				return false;
			}
		} else
			return false;
	}

	public boolean enterText(WebElement element, String testdata) {
		// se.log().logSeStep("Enter Text '" + testdata + "' in Element: " +
		// element.toString());
		if (element != null && element.isDisplayed() && element.isEnabled()) {
			try {
				if ("input".equals(element.getTagName())) {
					element.sendKeys("");
				} else {
					new Actions(se.driver()).moveToElement(element).perform();
				}
				if (testdata.equals("N/A") || testdata.equals("NA")) {
					enterTAB(element);
				} else {
					element.clear();
					/*
					 * se.util().sleep(1000); try { Thread.sleep(500); } catch (InterruptedException
					 * e) { e.printStackTrace(); }
					 */
					element.sendKeys(testdata);
					enterTAB(element);
				}

				return true;
			} catch (InvalidElementStateException e) {
				// se.log().logSeStep("Could not enter text in " +
				// element.toString() + ", element not visible or not enabled");
				// se.verify().reportError("Could not enter text, element not
				// visible or not enabled");
				return false;
			} catch (Exception e) {
				// se.log().logSeStep("Could not enter text in " +
				// element.toString());
				// se.verify().reportError("Could not enter text");
				return false;
			}
		} else
			return false;

	}

	public boolean enterTextWithDelay(WebElement element, String testdata) {
		// se.log().logSeStep("Enter Text '" + testdata + "' in Element: " +
		// element.toString());
		if (element != null && element.isDisplayed() && element.isEnabled()) {
			try {
				if ("input".equals(element.getTagName())) {
					element.sendKeys("");
				} else {
					new Actions(se.driver()).moveToElement(element).perform();
				}
				if (testdata.equals("N/A") || testdata.equals("NA")) {
					enterTAB(element);
				} else {
					element.clear();
					se.util().sleep(1000);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					element.sendKeys(testdata);
					enterTAB(element);
				}

				return true;
			} catch (InvalidElementStateException e) {
				// se.log().logSeStep("Could not enter text in " +
				// element.toString() + ", element not visible or not enabled");
				// se.verify().reportError("Could not enter text, element not
				// visible or not enabled");
				return false;
			} catch (Exception e) {
				// se.log().logSeStep("Could not enter text in " +
				// element.toString());
				// se.verify().reportError("Could not enter text");
				return false;
			}
		} else
			return false;

	}

	public boolean EnterText(WebElement element, String testdata) {
		// se.log().logSeStep("Enter Text '" + testdata + "' in Element: " +
		// element.toString());
		if (element != null) {
			try {
				if (testdata.equals("NA")) {
					testdata = "";
				}
				element.clear();
				element.sendKeys(testdata);
				return true;
			} catch (InvalidElementStateException e) {
				// se.log().logSeStep("Could not enter text in " + element.toString() + ",
				// element not visible or not enabled");
				// se.verify().reportError("Could not enter text, element not visible or not
				// enabled");
				return false;
			} catch (Exception e) {
				// se.log().logSeStep("Could not enter text in " + element.toString());
				// se.verify().reportError("Could not enter text");
				return false;
			}
		} else
			return false;

	}

	public void enter_textRich(final WebElement element, String testdata) {

		if (testdata.equals("NA")) {
			testdata = "";
		}

		WebDriverWait wait = new WebDriverWait(se.driver(), 30);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {

				if (element.isDisplayed() && element.isEnabled())
					return true;
				else
					return false;
			}
		});

		if ("input".equals(element.getTagName()) || "textarea".equals(element.getTagName())) {
			element.sendKeys("");
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					if (element.equals(driver.switchTo().activeElement()))
						return true;
					else
						return false;
				}
			});
			element.clear();
		} else {
			new Actions(se.driver()).moveToElement(element).perform();
		}
		element.sendKeys(testdata);
	}

	public void checkvalue(WebElement element, String testdata) {
		try {
			if (testdata.equals("NA")) {
				testdata = "N/A";
			}
			if (element.isDisplayed() && !testdata.equals("N/A")) {
				boolean isChecked;
				try {
					if (element.getAttribute("checked") != null
							&& element.getAttribute("checked").equalsIgnoreCase("true")) {
						isChecked = true;
					} else {
						isChecked = false;
					}

				} catch (Exception e) {
					isChecked = false;
				}

				if (isChecked && testdata.equalsIgnoreCase("Yes")) {
					se.log().logSeStep("Skipped: Element is already checked ");
				} else if (!isChecked && testdata.equalsIgnoreCase("Yes")) {
					new Actions(se.driver()).moveToElement(element).perform();
					element.sendKeys(Keys.SPACE);
				} else if (!isChecked && testdata.equalsIgnoreCase("No")) {
					se.log().logSeStep("Skipped: Element is already unchecked ");
				} else if (isChecked && testdata.equalsIgnoreCase("No")) {
					new Actions(se.driver()).moveToElement(element).perform();
					element.sendKeys(Keys.SPACE);
				} else {

				}
			} else {

			}
		} catch (Exception ex) {
			se.log().logSeStep("An unexpected error " + ex.getMessage()
					+ " occurred while executing updateCheckValue of the object ");
			ex.printStackTrace();
		}
	}

	public boolean enterTAB(WebElement element) {
		// se.log().logSeStep("Enter Text '" + testdata + "' in Element: " +
		// element.toString());
		if (element != null) {
			try {
				element.sendKeys(Keys.TAB);
				return true;
			} catch (InvalidElementStateException e) {
				// se.log().logSeStep("Could not enter text in " +
				// element.toString() + ", element not visible or not enabled");
				// se.verify().reportError("Could not enter text, element not
				// visible or not enabled");
				return false;
			} catch (Exception e) {
				// se.log().logSeStep("Could not enter text in " +
				// element.toString());
				// se.verify().reportError("Could not enter text");
				return false;
			}
		} else
			return false;

	}

	/**
	 * Get the text from the element
	 *
	 * @param locator
	 * @return
	 */
	public String getText(final By locator) {
		WebElement element;
		String text = "";
		// se.log().logSeStep("Get Text From Element: " + locator.toString());
		element = searchForElement(locator);
		if (element != null) {
			try {
				text = element.getText();
			} catch (InvalidElementStateException e) {
				se.log().logSeStep(
						"Could not get text from " + locator.toString() + ", elemet not visible or not enabled");
				se.verify().reportError("Could not get text from, element not visible or not enabled");
			} catch (Exception e) {
				se.log().logSeStep("Could not get text from " + locator.toString());
				se.verify().reportError("Could not get text");
			}
			return text;
		} else
			return text;

	}

	public String getSelectedDDValue(final By locator) {
		WebElement element;
		String text = "";
		element = searchForElement(locator);
		if (element != null) {
			try {
				text = element.getText();
			} catch (InvalidElementStateException e) {
				se.log().logSeStep(
						"Could not get text from " + locator.toString() + ", elemet not visible or not enabled");
				se.verify().reportError("Could not get text from, element not visible or not enabled");
			} catch (Exception e) {
				se.log().logSeStep("Could not get text from " + locator.toString());
				se.verify().reportError("Could not get text");
			}
			return text;
		} else
			return text;

	}

	/**
	 * Selects an item from a drop down
	 *
	 * @param locator
	 * @param value
	 * @return
	 */
	public boolean selectElementByValue(final By locator, String value) {
		// se.log().logSeStep(String.format("Select By Value%s In Element: %s",
		// value, locator.toString()));
		WebElement element = searchForElement(locator);
		if (element != null) {
			Select select = new Select(element);
			try {
				select.deselectAll();
			} catch (Exception e) {
				// ignore
			}
			select.selectByValue(value);
			return true;
		} else
			return false;

	}

	/**
	 * @throws IOException Selects an item from a drop down
	 *
	 * @param locator @param selection @return @throws
	 */
	public boolean selectElement(final By locator, String selection) throws IOException {
		// se.log().logSeStep("Select " + selection + " In Element: "
		// + locator.toString());
		boolean flag = true;
		try {
			String browserName = ((RemoteWebDriver) se.driver()).getCapabilities().getBrowserName();
			WebElement element = searchForElement(locator);
			if (selection.equals("N/A") || selection.equals("NA")) {
				selection = "";
			} else {

				if (element != null) {
					Select select = new Select(element);
					try {
						select.deselectAll();

					} catch (Exception e) {
						// ignore
					}
					select.selectByVisibleText(selection);
					flag = true;
				} else {
					flag = false;
				}
			}
		} catch (Exception e) {
			se.verify().verifyEqualsNoScreenshot("Issue with dropdown for" + locator + " ---", true, false);
		}
		return flag;
	}

	public boolean selectElementByIndex(final By locator, int selection) throws IOException {
		// se.log().logSeStep("Select " + selection + " In Element: "
		// + locator.toString());
		boolean flag = true;
		try {
			String browserName = ((RemoteWebDriver) se.driver()).getCapabilities().getBrowserName();
			/*
			 * if(selection.equals("N/A") || selection.equals("NA")){ selection = ""; }
			 */
			WebElement element = searchForElement(locator);
			if (element != null) {
				Select select = new Select(element);
				try {
					select.deselectAll();

				} catch (Exception e) {
					// ignore
				}
				select.selectByIndex(selection);
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			se.verify().verifyEqualsNoScreenshot("Issue with dropdown for" + locator + " ---", true, false);
		}
		return flag;
	}

	public void selectRadioByValue(final By locator, String value) {

		String browserName = ((RemoteWebDriver) se.driver()).getCapabilities().getBrowserName();

		String valueChangeCase = value.toUpperCase();

		List<WebElement> radios = se.driver().findElements(locator);
		for (int i = 0; i < radios.size(); i++) {
			String radiovalue = radios.get(i).getAttribute("value");
			if (radiovalue.trim().contains(valueChangeCase)) {
				radios.get(i).click();
				break;
			}

		}
	}

	public void selectElementDD(final WebElement element, final String testdata) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(se.driver(), 30);

		if ("input".equals(element.getTagName())) {
			element.sendKeys("");
		}
		element.clear();
		element.sendKeys("");
		Thread.sleep(500);

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {

				if (element.getText().length() == 0)
					return true;
				else
					element.clear();
				element.sendKeys("");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				return false;
			}
		});

		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(testdata);

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {

				if (element.getAttribute("value").equals(testdata))
					return true;
				else
					element.clear();
				element.sendKeys("");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				element.sendKeys(Keys.ARROW_DOWN);
				element.sendKeys(testdata);
				return false;
			}
		});

		Thread.sleep(300);
		element.sendKeys(Keys.ENTER);
		Thread.sleep(300);

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {

				String invalid = element.getAttribute("aria-invalid");
				if (invalid == null || "".equals(invalid))
					return true;
				else
					return false;
			}
		});

	}

	/**
	 * Selects an item from a drop down
	 *
	 * @param locator
	 * @param selection
	 * @return
	 */
	public void selectvalue(final By locator, String selection) {

		try {

			WebElement select = se.driver().findElement(locator);
			List<WebElement> options = select.findElements(By.tagName("option"));

			for (WebElement option : options) {

				if (option.getText().trim().equalsIgnoreCase(selection)) {

					option.click();
					break;

				}
			}

		} catch (InvalidElementStateException e) {
			se.log().logSeStep("Could not select on " + locator.toString() + ", element not visible");

		}

	}

	public void selectvalueByElement(final WebElement select, String selection) {

		try {

			// WebElement select = se.driver().findElement(locator);
			List<WebElement> options = select.findElements(By.tagName("option"));

			for (WebElement option : options) {

				if (option.getText().trim().equalsIgnoreCase(selection)) {

					option.click();
					break;

				}
			}

		} catch (InvalidElementStateException e) {
			se.log().logSeStep("Could not select on " + selection + ", element not visible");

		}

	}

	private WebElement searchForElement(final By locator) {
		return searchForElement(locator, globalSeTimeOut);
	}

	/**
	 * Search frames for an Element
	 *
	 * @param locator
	 * @return
	 */
	private WebElement searchForElement(final By locator, final int timeOutInSeconds) {
		if (se.waits().waitForElement(locator, timeOutInSeconds)) {
			return getElement(locator);
		}
		return null;
	}

	/**
	 * Returns to the default browser window or frame
	 */
	public void returnToDefaultContent() {
		se.driver().switchTo().defaultContent();
		inFrame = false;
	}

	/**
	 * Returns the number of matching elements
	 *
	 * @param locator
	 * @return
	 */
	public int getNumberOfElements(By locator) {
		List<WebElement> elements = getElements(locator);
		if (elements == null)
			return 0;
		return elements.size();
	}

	public void getalllinks(String linkname) {

		List<WebElement> alllinks = se.driver().findElements(By.tagName("a"));

		for (int i = 0; i < alllinks.size(); i++) {
			String linkvalue = alllinks.get(i).getText();
			if (linkvalue.trim().contains(linkname)) {
				alllinks.get(i).click();
				break;
			}
		}
	}

	public void selectradiovalue(String value) {

		se.log().logTestStep("Select value as " + value);
		try {
			List<WebElement> radios = se.driver().findElements(By.cssSelector(".radio"));
			for (int i = 0; i < radios.size(); i++) {

				String radiovalue = radios.get(i).getAttribute("value");
				if (radiovalue.trim().contains(value)) {
					radios.get(i).click();
					break;

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void selectradiovalue(final By locator, String value) {

		String browserName = ((RemoteWebDriver) se.driver()).getCapabilities().getBrowserName();

		List<WebElement> radios = se.driver().findElements(locator);
		for (int i = 0; i < radios.size(); i++) {
			String radiovalue = radios.get(i).getAttribute("value");
			if (radiovalue.trim().contains(value)) {
				radios.get(i).click();
				break;
			}

		}

	}

	public void selectradiovalueYN(final By locator, String value) {

		if (value.equals("Yes")) {
			value = "1";
		} else {
			value = "0";
		}

		String browserName = ((RemoteWebDriver) se.driver()).getCapabilities().getBrowserName();
		try {
			List<WebElement> radios = se.driver().findElements(locator);
			for (int i = 0; i < radios.size(); i++) {
				String radiovalue = radios.get(i).getAttribute("value");
				if (radiovalue.trim().contains(value)) {
					radios.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void selectradiovalueUW(final By locator, String value) {

		String valueUppercase = value.toUpperCase();

		String browserName = ((RemoteWebDriver) se.driver()).getCapabilities().getBrowserName();
		try {
			List<WebElement> radios = se.driver().findElements(locator);
			for (int i = 0; i < radios.size(); i++) {
				String radiovalue = radios.get(i).getAttribute("value");
				if (radiovalue.trim().contains(valueUppercase)) {
					radios.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickElement(WebElement Element) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", Element);
	}

	public void clickelement(WebElement Element) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript("arguments[0].click();", Element);
	}

	public void enterTextJSPhone(WebElement Element, String dataValue) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript("document.getElementById('AgencyPhone').value = '" + dataValue + "';");
	}

	public void enterTextJSEffDate(WebElement Element, String dataValue) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript("document.getElementById('EffectiveDate').value = '" + dataValue + "';");
	}

	public void enterTextPriorLossJSEffDate(WebElement Element, String dataValue) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript(
				"document.getElementById('WorkCompLineBusiness_WorkCompLossOrPriorPolicy_EffectiveDt').value = '"
						+ dataValue + "';");
	}

	public void enterTextJSExpDate(WebElement Element, String dataValue) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript("document.getElementById('ExpirationDate').value = '" + dataValue + "';");
	}

	public void enterTextJSZip(WebElement Element, String dataValue) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript("document.getElementById('ApplicantMailingZip_US').value = '" + dataValue + "';");
		Element.sendKeys(Keys.TAB);
	}

	public void enterTextJSDOBStarted(WebElement Element, String dataValue) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript("document.getElementById('DateBusinessStart').value = '" + dataValue + "';");
	}

	public void enterTextJSTaxIDFEIN(WebElement Element, String dataValue) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript("document.getElementById('TaxIdFEIN').value = '" + dataValue + "';");
	}

	public void enterTextJSTaxIDSSN(WebElement Element, String dataValue) {
		JavascriptExecutor executor = (JavascriptExecutor) se.driver();
		executor.executeScript("document.getElementById('TaxIdSSN').value = '" + dataValue + "';");
	}

	public boolean Click(WebElement Element) {
		
		
		
		if (Element != null && Element.isDisplayed() && Element.isEnabled()) {
			try {
				se.waits().waitForElementIsDisplayed(Element);
				se.waits().waitForElementIsClickable(Element);
				
				// se.log().logSeStep("Click Element : " + Element.toString());
				Element.click();
				return true;
			}
			
			catch (InvalidElementStateException e) {
				se.log().logSeStep("Could not click on " + Element.toString() + "Trying again");
				se.util().sleep(5000);
				Element.click();
				return true;
			}
			catch(Throwable ex) {
				se.log().logSeStep("Could not click after trying");
			}
		} else
			se.log().logSeStep("Could not click on " + Element.toString() + ", element id disable and not clickable");
		return false;
	}

	public void doubleClick(WebElement Element) {
		Actions ac1 = new Actions(se.driver());
		ac1.doubleClick(Element).perform();
	}

	/**
	 * Click the element
	 *
	 * @param locator
	 * @return
	 */
	public boolean clickElement(final By locator) {
		return clickElement(locator, null, false, -1, null, -1, -1, null, false);
	}

	public boolean clickElement(final By locator, final String containsText, final boolean exactText,
			final int nthElement, final String frameName, final int x, final int y, final By successLocator,
			final boolean shouldDisappear) {
		// Build logging message from options
		StringBuilder msg = new StringBuilder();
		msg.append("Click");
		if (nthElement != -1)
			msg.append(" (").append(nthElement).append(")");
		msg.append(" element: ").append(locator.toString());
		if (containsText != null && !containsText.isEmpty())
			msg.append(" containing '").append(containsText).append("'");
		if (frameName != null && !frameName.isEmpty())
			msg.append(" in Frame ").append(frameName);
		if (x != -1 || y != -1)
			msg.append(" At (").append(x).append(",").append(y).append(")");
		// se.log().logSeStep(msg.toString());

		try {
			return new WebDriverWait(se.driver(), globalSeTimeOut).ignoring(Throwable.class)
					.withMessage("Unable to click on element").until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver driver) {
							// Switch frame is specified
							if (frameName != null && !frameName.isEmpty())
								switchToFrame(frameName);

							// Find targetElement
							WebElement targetElement = null;
							if (containsText != null && !containsText.isEmpty()) {
								for (WebElement element : getElements(locator)) {
									String elementText = element.getText();
									if (elementText.equals(containsText)
											|| (!exactText && elementText.contains(containsText))) {
										targetElement = element;
										break;
									}
								}
							} else if (nthElement != -1) {
								targetElement = getElements(locator).get(nthElement);
							} else {
								targetElement = getElement(locator);
							}

							// Click on the element
							if (targetElement != null) {
								if (x != -1 || y != -1) {
									new Actions(driver).moveToElement(targetElement).moveByOffset(x, y).click()
											.perform();
								} else {
									targetElement.click();
								}
							}

							// Check for success locator
							boolean success = (targetElement != null && successLocator == null)
									|| (shouldDisappear ? se.waits().waitForElementToDisappear(successLocator, 5)
											: se.waits().waitForElementIsDisplayed(successLocator, 5));

							// Switch frame back to default, if frame was
							// specified
							if (success && frameName != null && !frameName.isEmpty())
								returnToDefaultContent();

							// Did we do it?
							return success;
						}
					});
		} catch (Throwable t) {
			String errorName = "Click Element: ";
			// se.log().logSeStep(errorName + t.getMessage() + ": " +
			// ExceptionUtils.getStackTrace(t));
			// se.log().logTcError(errorName, se.browser().takeScreenShot());
			// Switch frame back to default, if a frame was specified
			if (frameName != null && !frameName.isEmpty())
				returnToDefaultContent();
			return false;
		}
	}

	/**
	 * Switch to a frame by name
	 *
	 * @param iFrameName
	 * @return
	 */
	public boolean switchToFrame(String iFrameName) {
		if (inFrame)
			returnToDefaultContent();
		try {
			se.driver().switchTo().frame(iFrameName);
			inFrame = true;
			return true;
		} catch (Exception e) {
			se.log().logSeStep("Un-handled Exception in swithToFrame: " + e.getMessage());
			return false;
		}
	}

	public boolean switchToFrameByXpath(WebElement Element) {
		if (inFrame)
			returnToDefaultContent();
		try {
			se.driver().switchTo().frame(Element);
			se.log().logSeStep("Switched to an iFrame");
			inFrame = true;
			return true;
		} catch (Exception e) {
			se.log().logSeStep("Un-handled Exception in swithToFrame: " + e.getMessage());
			return false;
		}
	}

	

	public boolean hover(WebElement element) {
		// se.log().logSeStep("Hover Over Element: " + element.toString());

		if (se.browser().getBrowserName().trim().equalsIgnoreCase("Chrome")) {

			String code = "var fireOnThis = arguments[0];" + "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initEvent( 'mouseover', true, true );" + "fireOnThis.dispatchEvent(evObj);";
			((JavascriptExecutor) se.driver()).executeScript(code, element);
		} else {

			Actions actions = new Actions(se.driver());
			Action action = actions.moveToElement(element).build();
			action.perform();
		}

		return true;
	}

	public boolean isElementPresent(WebElement we) {
		boolean flag = false;
		try {
			se.util().sleep(1000);
			flag = we.isDisplayed();
			return flag; // Success!
		} catch (Exception e) {
			return flag;
		}
	}
	
	private void continueIfException(Exception e) {
		String continueIfException = SystemPropertyUtil.getContinueIfException().trim();
		
		if (continueIfException.equalsIgnoreCase("Yes")) {
			return;
		} else if (continueIfException.equalsIgnoreCase("No")) {
			//end test

			String errorName = "NoSuchElementException Exception in getElement:";
			se.log().logSeStep(errorName + e.getMessage());
			se.log().logTcError(errorName, se.browser().takeScreenShot());
			se.log().debug("The test ended early due to an error.");
			se.reporter().reportInfo("The test ended early due to an error.", e.getClass().getSimpleName());
			se.stopRunning();
		}

		return;
	}
}
