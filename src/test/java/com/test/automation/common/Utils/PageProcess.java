package com.test.automation.common.Utils;

import java.awt.AWTException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.framework.Util;
import com.test.automation.customs.Assertions;
import com.test.automation.customs.CustomHandler;
import com.test.automation.repository.CommonRepo;

public class PageProcess {

	public static WebElement findElement(SeHelper se, String sheetName, String key, String value,
			String xPathExpression) {
		Class<?> objClass = null;
		WebElement element = null;

		try {

			if (value == null) {
				return null;
			}
			try {

				if (key.contains("FileUpload")) {
					FileUpload(se, key, value, xPathExpression);
					return null;
				}

				if (key.contains("ControlKeys")) {
					ControlKeys(se, value);
					return null;
				}
				
				if (key.contains("OpenPDF")) {
					PDFReader reader = new PDFReader(se, value);
					SystemPropertyUtil.setPDFReader(reader);
					return null;
				}

				if (checkAlert(se, key, value)) {
					return null;
				}

				if (checkPageNav(se, key, value)) {
					return null;
				}

				if (checkWindow(se, key, value)) {
					return null;
				}

				if (checkIframe(se, key, value)) {
					return null;
				}

				if (value.contains("$")) {
					dynamicXpath(se, value);
					return null;
				}
				if (key.equalsIgnoreCase("Screenshot")) {
					FillElement(se, element, key, value);
					return null;
				}

				try {

					// element = (WebElement) callMethod.invoke(obj, se);
					// element = (WebElement) callMethod.invoke(obj);

					element = CommonRepo.ElementObject(se, xPathExpression);

				} catch (NoSuchElementException e) {

					se.log().error(
							e.getClass().getSimpleName() + " encountered for element: " + key + " on " + sheetName, e);
					se.reporter().reportErrorCapture("Element " + key + " on " + sheetName, e, se);
					new Util().sleep(500);
					continueIfException(se, sheetName, key, e);

				} catch (Exception e) {

					se.log().error(
							e.getClass().getSimpleName() + " encountered for element: " + key + " on " + sheetName, e);
					System.out.println("***** Recommend reviewing data entry for this test *****");
					se.reporter().reportErrorCapture("Element " + key + " on " + sheetName, e, se);
					e.printStackTrace();
					return null;
				}

				if (element != null) {
					if (checkOptional(element, value)) {
						return null;
					}
					if (value.contains(">")) {
						Assertions asrt = new Assertions(se);
						asrt.verify(element, value, key);
						return element;
					}

					FillElement(se, element, key, value);
				}
			} catch (SecurityException e) {
				se.log().error(e.getClass().getSimpleName() + " encountered for element: " + key + " on " + sheetName,
						e);
				se.reporter().reportErrorCapture("Element " + key + " on " + sheetName, e, se);
				e.printStackTrace();
			}
		} catch (Exception e) {
			se.log().error(e.getClass().getSimpleName() + " encountered for element: " + key + " on " + sheetName, e);
			se.reporter().reportErrorCapture("Element " + key + " on " + sheetName, e, se);
			e.printStackTrace();
		}
		return element;
	}

	public static WebElement findElement(SeHelper se, String sheetName, String key, String value) {

		Class<?> objClass = null;
		WebElement element = null;

		try {
			objClass = Class.forName("com.test.automation.repository." + sheetName);

		} catch (ClassNotFoundException e) {
			se.log().error(e.getClass().getSimpleName() + " encountered on page: " + sheetName, e);
			se.reporter().reportErrorCapture(sheetName, e, se);
			e.printStackTrace();
		}

		Constructor<?> constructor = null;
		try {
			constructor = objClass.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			se.log().error(e.getClass().getSimpleName() + " encountered on page: " + sheetName, e);
			se.reporter().reportErrorCapture(sheetName, e, se);
			e.printStackTrace();
		}
		try {
			Object obj = constructor.newInstance();

			try {
				if (key.contains("ControlKeys")) {
					ControlKeys(se, value);
					return null;
				}

				if (checkAlert(se, key, value)) {
					return null;
				}

				if (checkPageNav(se, key, value)) {
					return null;
				}

				if (checkWindow(se, key, value)) {
					return null;
				}

				if (checkIframe(se, key, value)) {
					return null;
				}

				if (value.contains("$")) {
					dynamicXpath(se, value);
					return null;
				}
				try {
					Method callMethod = obj.getClass().getMethod(key, SeHelper.class);
					// Method callMethod = obj.getClass().getDeclaredMethod(key);
					callMethod.setAccessible(true);

					element = (WebElement) callMethod.invoke(obj, se);
					// element = (WebElement) callMethod.invoke(obj);

				} catch (NoSuchMethodException e) {
					se.log().error(
							e.getClass().getSimpleName() + " encountered for element: " + key + " on " + sheetName, e);
					System.out.println("***** Recommend reviewing data entry for this test *****");
					se.reporter().reportErrorCapture(sheetName, e, se);
					e.printStackTrace();
				}

				if (element != null) {
					if (checkOptional(element, value)) {
						return null;
					}
					if (value.contains(">")) {
						Assertions asrt = new Assertions(se);
						asrt.verify(element, value, key);
						return element;
					}
					// se.waits().waitForPageLoad();
					// se.waits().waitForElementIsClickable(element);
					FillElement(se, element, key, value);
				}
			} catch (SecurityException e) {
				se.log().error(e.getClass().getSimpleName() + " encountered for element: " + key + " on " + sheetName,
						e);
				se.reporter().reportErrorCapture(sheetName, e, se);
				e.printStackTrace();
			}
		} catch (Exception e) {
			se.log().error(e.getClass().getSimpleName() + " encountered on page: " + sheetName, e);
			se.reporter().reportErrorCapture(sheetName, e, se);
			e.printStackTrace();
		}
		return element;
	}

	private static void FillElement(SeHelper se, WebElement element, String key, String value) {

		if (checkOptional(element, value)) {
			return;
		}

		if ((value.contains("(")) && (value.indexOf(')') == value.length() - 1)) {

			value = new CustomHandler(se).handle(value);
			if (value.equals("sc")) {
				return;
			}
		}

		se.log().logSeStep("Accessing element: \"" + key + "\" Using value: \"" + value + "\"");
		// se.reporter().reportInfo("Accessing Element", "Element: " + key + "<br>Value:
		// " + value);

		switch (element.getTagName()) {
		case "input":
			try {
				if (value.equalsIgnoreCase("Click")) {
					se.element().Click(element);
					// element.click();
				} 
				else if(value.equalsIgnoreCase("jsClick")){
					JavascriptExecutor executor = (JavascriptExecutor)se.driver();
	                executor.executeScript("arguments[0].click();", element);
	
				}
						else {
					element.clear();
					element.sendKeys(value);
				}
			} catch (NoSuchElementException e) {
				se.log().error(e.getClass().getSimpleName() + " encountered when accessing \"" + key + "\"", e);
				System.out.println("***** Recommend reviewing column head data entry *****");
				se.reporter().reportErrorCapture("Element: " + key + " using Value: " + value, e, se);
				e.printStackTrace();
			} catch (org.openqa.selenium.ElementClickInterceptedException e) {
				se.log().logSeStep("Interception " + element.toString() + "Waiting for page load");
				se.waits().waitForPageLoad();
				se.element().Click(element);
			}

			break;
		case "button":
			try {
				if (value.equalsIgnoreCase("Click")) {
					se.element().Click(element);
					// element.click();
				} 
				else if(value.equalsIgnoreCase("jsClick")){
					JavascriptExecutor executor = (JavascriptExecutor)se.driver();
	                executor.executeScript("arguments[0].click();", element);
	
				}
				// element.click();
			} catch (NoSuchElementException e) {
				se.log().error(e.getClass().getSimpleName() + " encountered when accessing \"" + key + "\"", e);
				System.out.println("***** Recommend reviewing column head data entry *****");
				se.reporter().reportErrorCapture("Element: " + key + " using Value: " + value, e, se);
				e.printStackTrace();
			} catch (org.openqa.selenium.ElementClickInterceptedException e) {
				se.log().logSeStep("Interception " + element.toString() + "Waiting for page load");
				se.waits().waitForPageLoad();
				se.element().Click(element);
			}

			break;
		case "select":
			try {
				Select dropDownValue = new Select(element);
				dropDownValue.selectByVisibleText(value);
			} catch (NoSuchElementException e2) {

				try {
					Select dropDownValue1 = new Select(element);
					dropDownValue1.selectByValue(value);
				} catch (NoSuchElementException e1) {

					try {
						Select dropDownValue2 = new Select(element);

						dropDownValue2.selectByIndex(Integer.valueOf(value));
					} catch (NoSuchElementException e) {
						se.log().error(e.getClass().getSimpleName() + " encountered when getting value \"" + value
								+ "\" in element \"" + key + "\" \n", e);
						System.out.println("***** Recommend reviewing column head data entry *****");
						se.reporter().reportErrorCapture("Element: " + key + " using Value: " + value, e, se);
						e.printStackTrace();
					}
				}
			}

			break;
		case "a":
			try {
				if (value.equalsIgnoreCase("Click")) {
					se.element().Click(element);
					// element.click();
				} 
				else if(value.equalsIgnoreCase("jsClick")){
					JavascriptExecutor executor = (JavascriptExecutor)se.driver();
	                executor.executeScript("arguments[0].click();", element);
	
				}
				// element.click();
			} catch (NoSuchElementException e) {
				se.log().error(e.getClass().getSimpleName() + " encountered for element: \"" + key + "\"", e);
				System.out.println("***** Recommend reviewing column head data entry *****");
				se.reporter().reportErrorCapture("Element: " + key + " using Value: " + value, e, se);
				e.printStackTrace();
			} catch (org.openqa.selenium.ElementClickInterceptedException e) {
				se.log().logSeStep("Interception " + element.toString() + "Waiting for page load");
				se.waits().waitForPageLoad();
				se.element().Click(element);
			}

			break;
		case "label":
			try {
				if (value.equalsIgnoreCase("Click")) {
					se.element().Click(element);
					// element.click();
				} 
				else if(value.equalsIgnoreCase("jsClick")){
					JavascriptExecutor executor = (JavascriptExecutor)se.driver();
	                executor.executeScript("arguments[0].click();", element);
	
				}
				// element.click();
			} catch (NoSuchElementException e) {
				se.log().error(e.getClass().getSimpleName() + " encountered for element: \"" + key + "\"", e);
				System.out.println("***** Recommend reviewing column head data entry *****");
				se.reporter().reportErrorCapture("Element: " + key + " using Value: " + value, e, se);
				e.printStackTrace();
			} catch (org.openqa.selenium.ElementClickInterceptedException e) {
				se.log().logSeStep("Interception " + element.toString() + "Waiting for page load");
				se.waits().waitForPageLoad();
				se.element().Click(element);
			}

			break;
		default:
			ActionBasedOnValue(se, element, value);

		}
		se.reporter().reportStepPass("Accessing Element", "Element: " + key + "<br>Value:" + value);
	
	}

	private static void ActionBasedOnValue(SeHelper se, WebElement element, String value) {
		if (value.equalsIgnoreCase("Click")) {
			se.element().Click(element);
			// element.click();
		} 
		else if(value.equalsIgnoreCase("jsClick")){
			JavascriptExecutor executor = (JavascriptExecutor)se.driver();
            executor.executeScript("arguments[0].click();", element);

		} else if (value.contains("Keys")) {
			ControlKeys(se, value);
		} else {
			se.log().debug("No tag and value is identified!");
		}
	}

	private static void ControlKeys(SeHelper se, String key) {
		Actions action = new Actions(se.driver());
		key = key.toUpperCase();

		String[] keys = key.split("[.,]");
		for (String value : keys) {
			value = value.trim();
			if (value.equals("KEYS"))
				continue;
			else if (value.equals("ENTER"))
				value = "RETURN";
			else if (value.contentEquals("CLICK")) {
				try {
					throw new NoSuchMethodException();
				} catch (NoSuchMethodException e) {
					se.log().error(
							"IGNORED: \"Click\" should be given to an element - ControlKeys handles keyboard interactions only",
							e);
					System.out.println("***** Recommend reviewing data entry *****");
				}
				continue;
			}

			if (value.equals("SHIFT") || value.equals("ALT")) {
				action.keyUp(Keys.valueOf(value)).keyDown(Keys.valueOf(value)).perform();
			} else
				action.sendKeys(Keys.valueOf(value)).perform();
		}
		action.sendKeys(Keys.NULL).perform();
	}

	
	private static void FileUpload(SeHelper se, String key, String value, String xPathExpression) {

		try {
			WebElement element = CommonRepo.ElementObject(se, xPathExpression);

			if (element.getTagName().equalsIgnoreCase("input")) {

				// if it is an input, we can just send the filepath directly
				element.sendKeys(value);
				
			} else {

				// if it is not an input, use the Robot class to type into the file explorer
				try {
					
					se.element().Click(element);
					Thread.sleep(1000);
					se.reporter().reportInfo("Uploading File", "File Path:<br>" + value);
					
					Keyboard keyboard = new Keyboard();
					keyboard.type(value.trim());
					Thread.sleep(1000);
					keyboard.enter();

				} catch (AWTException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			se.log().error(e.getClass().getSimpleName() + " encountered during FileUpload.", e);
			se.reporter().reportErrorCapture("Doing FileUpload", e, se);
			e.printStackTrace();
		}
	}

	private static boolean checkAlert(SeHelper se, String key, String value) {
		if (key.equals("Alert")) {
			switch (value) {
			case "dismiss":
				se.browser().dismissPopup();
				return true;

			case "accept":
				se.browser().acceptPopup();
				return true;

			case "text":
				se.browser().sendKeysPopup(value);
				return true;

			default:
				return false;
			}
		}

		else
			return false;
	}

	private static boolean checkPageNav(SeHelper se, String key, String value) {
		switch (key) {
		case "Backward":
			se.browser().navigateBack();
			return true;
		case "Forward":
			se.browser().navigateForward();
			return true;
		case "Refresh":
			se.browser().refresh();
			return true;
		default:
			return false;
		}
	}

	private static boolean checkWindow(SeHelper se, String key, String value) {
		if (key.equals("Window")) {
			se.browser().switchToWindow(Integer.parseInt(value));
			return true;
		}

		return false;
	}

	private static boolean checkIframe(SeHelper se, String key, String value) {
		if (key.equals("Iframe")) {
			se.browser().switchToIFrame(Integer.parseInt(value));
			return true;
		}
		return false;

	}

	private static void dynamicXpath(SeHelper se, String value) {
		// *[contains(text(), '{}')]
		String[] split = value.split("\\$");
		String action = split[0].trim();
		System.out.println(action);
		String xpath = "(//*[contains(text(), '" + split[1].trim() + "')] | //*[@value='" + split[1].trim() + "'])";
		By el = By.xpath(xpath);
		WebElement element = se.element().getElement(el, true);

		try {
			if (action.equalsIgnoreCase("Click")) {
				se.element().Click(element);
				return;
			}
			// else if (action.equalsIgnoreCase("SendKeys")) {
			// element.clear();
			// element.sendKeys(value);
			// return;
			// }
			else {
				se.log().debug("Dynamic XPATH invoked with invalid action: " + action);
				se.reporter().reportStepFail("Dynamic XPATH invoked with invalid action.", "Action: " + action);
				return;
			}
		} catch (NoSuchElementException e) {
			se.log().error(e.getClass().getSimpleName() + " encountered for dynamic XPATH: " + xpath + "\n", e);
			se.reporter().reportErrorCapture("dynamic XPATH - " + xpath, e, se);
		} catch (Exception e) {
			se.log().error(e.getClass().getSimpleName() + " encountered for dynamic XPATH: " + xpath + "\n", e);
			se.reporter().reportErrorCapture("dynamic XPATH - " + xpath, e, se);
		}
	}

	private static boolean checkOptional(WebElement element, String value) {
		if (value.charAt(0) == '[' && value.charAt(value.length() - 1) == ']') {
			if (element.isDisplayed() && element.isEnabled()) {
				return false;
			}

			else {
				return true;
			}
		} else
			return false;
	}

	private static void continueIfException(SeHelper se, String sheetName, String key, Exception e) {

		String continueIfException = SystemPropertyUtil.getContinueIfException().trim();

		if (continueIfException.equalsIgnoreCase("Yes")) {

			return; // if yes keep running

		} else if (continueIfException.equalsIgnoreCase("No")) {

			// if no end test
			se.log().debug(
					"The test ended early because the element: " + key + " could not be found on page " + sheetName);
			se.reporter().reportStepFail("The test ended early because the element: " + key + " could not be found.",
					"Page: " + sheetName);

			se.stopRunning();
		}

		return;
	}
}
