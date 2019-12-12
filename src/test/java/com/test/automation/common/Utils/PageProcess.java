package com.test.automation.common.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;

import com.test.automation.common.SeHelper;
import com.test.automation.customs.Assertions;
import com.test.automation.customs.CustomHandler;
import com.test.automation.repository.CommonRepo;

public class PageProcess {
	
	public static WebElement findElement(SeHelper se, String sheetName, String key, String value, String xPathExpression) {
		Class<?> objClass = null;
		WebElement element = null;

		
		try {

	           if (value == null | value.contentEquals("")) {
	                return null;
	            }
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
					
					//element = (WebElement) callMethod.invoke(obj, se);
					// element = (WebElement) callMethod.invoke(obj);
					element = CommonRepo.ElementObject(se, xPathExpression);

				} catch (Exception e) {
					se.log().error("NoSuchMethodException encountered when attempting to get element: " + key + " on "
							+ sheetName, e);
					System.out.println("***** Recommend reviewing data entry for this test *****");
					se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
					e.printStackTrace();
				}

				if (element != null) {
					if (checkOptional(element, value)) {
						return null;
					}
					if (value.contains(">")) {
						Assertions asrt = new Assertions(se);
						asrt.verify(element, value);
						return element;
					}
					se.waits().waitForPageLoad();
					se.waits().waitForElementIsClickable(element);
					FillElement(se, element, key, value);
				}
			} catch (SecurityException e) {
				se.log().error(
						"SecurityException encountered when attempting to get element: " + key + " on " + sheetName, e);
				se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
				e.printStackTrace();
			}
		} catch (Exception e) {
			se.log().error(
					e.toString() + " encountered when new instance of page: " + sheetName + " attempted to be created.",
					e);
			se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
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
			se.log().error("ClassNotFoundException encountered when accessing page: " + sheetName, e);
			se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
			e.printStackTrace();
		}

		Constructor<?> constructor = null;
		try {
			constructor = objClass.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			se.log().error("Exception encountered when accessing page: " + sheetName, e);
			se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
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
					se.log().error("NoSuchMethodException encountered when attempting to get element: " + key + " on "
							+ sheetName, e);
					System.out.println("***** Recommend reviewing data entry for this test *****");
					se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
					e.printStackTrace();
				}

				if (element != null) {
					if (checkOptional(element, value)) {
						return null;
					}
					if (value.contains(">")) {
						Assertions asrt = new Assertions(se);
						asrt.verify(element, value);
						return element;
					}
					se.waits().waitForPageLoad();
					se.waits().waitForElementIsClickable(element);
					FillElement(se, element, key, value);
				}
			} catch (SecurityException e) {
				se.log().error(
						"SecurityException encountered when attempting to get element: " + key + " on " + sheetName, e);
				se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
				e.printStackTrace();
			}
		} catch (Exception e) {
			se.log().error(
					e.toString() + " encountered when new instance of page: " + sheetName + " attempted to be created.",
					e);
			se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
			e.printStackTrace();
		}
		return element;
	}

	private static void FillElement(SeHelper se, WebElement element, String key, String value) {

		if (checkOptional(element, value)) {
			return;
		}

		if ((value.contains("(")) && (value.indexOf(')') == value.length() - 1)) {
			value = new CustomHandler().handle(value);
		}

		se.log().logSeStep("Accessing element: \"" + key + "\" Using value: \"" + value + "\"");
		se.reporter().reportInfo("Accessing Element", "Element: " + key + "<br>Value: " + value);

		switch (element.getTagName()) {
		case "input":
			try {
				if (value.equalsIgnoreCase("Click")) {
					se.element().Click(element);
					// element.click();
				} else {
					element.clear();
					element.sendKeys(value);
				}
			} catch (NoSuchElementException e) {
				se.log().error("NoSuchElementException encountered when trying to access \"" + key + "\"", e);
				System.out.println("***** Recommend reviewing column head data entry *****");
				se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + "<br>Value: " + value,
						key, se);
				e.printStackTrace();
			}
			break;
		case "button":
			try {
				se.element().Click(element);
				// element.click();
			} catch (NoSuchElementException e) {
				se.log().error("NoSuchElementException encountered when trying to access \"" + key + "\"", e);
				System.out.println("***** Recommend reviewing column head data entry *****");
				se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + "<br>Value: " + value,
						key, se);
				e.printStackTrace();
			}
			break;
		case "select":
			try {
				Select dropDownValue = new Select(element);
				dropDownValue.selectByVisibleText(value);	
			}
			catch (NoSuchElementException e2) {
				
				try {
					Select dropDownValue1 = new Select(element);
					dropDownValue1.selectByValue(value);
				}
					catch (NoSuchElementException e1) {
						
						
						try {
							Select dropDownValue2 = new Select(element);

							dropDownValue2.selectByIndex(Integer.valueOf(value));
						}
						catch (NoSuchElementException e) {
							se.log().error("NoSuchElementException encountered when trying to locate value \"" + value
									+ "\" in element \"" + key + "\" \n", e);
							System.out.println("***** Recommend reviewing column head data entry *****");
							se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + " || Value: " + value,
									key, se);
							e.printStackTrace();
						}
					 }}
			break;
		case "a":
			try {
				se.element().Click(element);
				// element.click();
			} catch (NoSuchElementException e) {
				se.log().error("NoSuchElementException encountered when trying to find \"" + key + "\"", e);
				System.out.println("***** Recommend reviewing column head data entry *****");
				se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + "<br>Value: " + value,
						key, se);
				e.printStackTrace();
			}
			break;
		case "label":
			try {
				se.element().Click(element);
				// element.click();
			} catch (NoSuchElementException e) {
				se.log().error("NoSuchElementException encountered when trying to find \"" + key + "\"", e);
				System.out.println("***** Recommend reviewing column head data entry *****");
				se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + "<br>Value: " + value,
						key, se);
				e.printStackTrace();
			}
			break;
		default:
			ActionBasedOnValue(se, element, value);

		}
		se.reporter().reportPass("Accessing Element", "Element: " + key + "<br>Value: " + value);
	}

	private static void ActionBasedOnValue(SeHelper se, WebElement element, String value) {
		if (value.contains("Click")) {
			se.element().Click(element);
			// element.click();
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
				}
				catch (NoSuchMethodException e){
					se.log().error("IGNORED: \"Click\" should be given to an element - ControlKeys handles keyboard interactions only", e);
					System.out.println("***** Recommend reviewing data entry *****");
				}
				continue;
			}
			
			if (value.equals("SHIFT") || value.equals("ALT"))
			{
				action.keyUp(Keys.valueOf(value)).keyDown(Keys.valueOf(value)).perform();
			}
			else
				action.sendKeys(Keys.valueOf(value)).perform();
		}
		action.sendKeys(Keys.NULL).perform();
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
		System.out.println("DYNAMIC X PATH: " + xpath);
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
				se.reporter().reportError("Dynamic XPATH invoked with invalid action.", "Action: " + action);
				return;
			}
		} catch (NoSuchElementException e) {
			se.log().error("NoSuchElementException encountered for dynamic XPATH: " + xpath + "\n", e);
			se.reporter().reportError("Error encountered with dynamic XPATH: " + xpath, "Action: " + action);
		} catch (Exception e) {
			se.log().error("Exception encountered with the dynamic XPATH: " + xpath + "\n", e);
			se.reporter().reportError("Error encountered with dynamic XPATH: " + xpath, "Action: " + action);
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


}
