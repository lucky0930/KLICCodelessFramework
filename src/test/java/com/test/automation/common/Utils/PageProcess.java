package com.test.automation.common.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;

import com.test.automation.common.SeHelper;
import com.test.automation.common.framework.Assertions;
import com.test.automation.common.framework.CustomHandler;


public class PageProcess {

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
				if(key.equals("ControlKeys")) {
					System.out.println("Starting ControlKeys function");
					ControlKeys(se, value);
					return null;
				}

				Method callMethod = obj.getClass().getMethod(key, SeHelper.class);
				// Method callMethod = obj.getClass().getDeclaredMethod(key);
				callMethod.setAccessible(true);

				element = (WebElement) callMethod.invoke(obj, se);
				// element = (WebElement) callMethod.invoke(obj);
				
				if (value.contains("$")) {
					element = dynamicXpath(se, value);
				}

				if (element != null) {
					if (value.contains(">")) {
						Assertions asrt = new Assertions(se);
						asrt.verify(element, value);
						return element;
					}
					FillElement(se, element, key, value);
				}
			} catch (NoSuchMethodException e) {
				se.log().error("NoSuchMethodException encountered when attempting to get element: "
								+ key + " on " + sheetName, e);
				se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
				e.printStackTrace();
			} catch (SecurityException e) {
				se.log().error("SecurityException encountered when attempting to get element: "
						+ key + " on " + sheetName, e);
				se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			se.log().error("InstantiationException encountered when new instance of page: " + sheetName
					+ " attempted to be created.", e);
			se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			se.log().error("IllegalAccessException encountered when new instance of page: " + sheetName
					+ " attempted to be created.", e);
			se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			se.log().error("IllegalArgumentException encountered when new instance of page: " + sheetName
					+ " attempted to be created.", e);
			se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			se.log().error("InvocationTargetException encountered when new instance of page: " + sheetName
					+ " attempted to be created.", e);
			se.reporter().reportErrorCapture("Error accessing page.", "Page Name: " + sheetName, sheetName, se);
			e.getCause().printStackTrace();
			e.printStackTrace();
		}
		return element;
	}

	private static void FillElement(SeHelper se, WebElement element, String key, String value) {

		
		
		if (value.contains("()")) {
			value = new CustomHandler().handle(value);
		}
		
		se.log().logSeStep("Accessing element: \"" + key + ".\" Using value: \"" + value + ".\"");
		se.reporter().reportInfo("Accessing Element", "Element: " + key + " || Value: " + value);
		
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
					se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + " || Value: " + value, key, se);
					e.printStackTrace();
				}
				break;
			case "button":
				try {
					se.element().Click(element);
					// element.click();
				} catch (NoSuchElementException e) {
					se.log().error("NoSuchElementException encountered when trying to access \"" + key + "\"", e);
					se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + " || Value: " + value, key, se);
					e.printStackTrace();
				}
				break;
			case "select":
				try {
					Select dropDownValue = new Select(element);
					dropDownValue.selectByValue(value);
				} catch (NoSuchElementException e) {
					se.log().error("NoSuchElementException encountered when trying to locate value \"" + value
							+ "\" in element \"" + key + "\" \n", e);
					se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + " || Value: " + value, key, se);
					e.printStackTrace();
				} catch (Exception e) {
					se.log().error("Exception encountered when trying to locate value \"" + value + "\" in element \""
							+ key + "\" \n", e);
					se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + " || Value: " + value, key, se);
					e.printStackTrace();
				}
				break;
			case "a":
				try {
					se.element().Click(element);
					// element.click();
				} catch (NoSuchElementException e) {
					se.log().error("NoSuchElementException encountered when trying to find \"" + key + "\"", e);
					se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + " || Value: " + value, key, se);
					e.printStackTrace();
				}
				break;
			case "label":
				try {
					se.element().Click(element);
					// element.click();
				} catch (NoSuchElementException e) {
					se.log().error("NoSuchElementException encountered when trying to find \"" + key + "\"", e);
					se.reporter().reportErrorCapture("Could Not Access Element", "Element: " + key + " || Value: " + value, key, se);
					e.printStackTrace();
				}
				break;
			default:
				ActionBasedOnValue(se, element, value);

			}
	}

	private static void ActionBasedOnValue(SeHelper se, WebElement element, String value) {
		if (value.contains("Click")) {
			se.element().Click(element);
			// element.click();
		} else if(value.contains("Keys.")){
			value = value.replace("Keys.", "");
			element.sendKeys(Keys.valueOf(value.toUpperCase()));
		} else {
			se.log().debug("No tag and value is identified!");
		}
	}
	
	private static void ControlKeys(SeHelper se, String key) {
		Actions action = new Actions(se.driver());
		if (key.contains(",")) {
			String[] keys = key.split(",");
			for (String value : keys) {
				value = value.toUpperCase();
				if(value.equals("SHIFT") || value.equals("ALT"))
					action.keyDown(value);
				else
					action.sendKeys(value);
			}
			action.sendKeys(Keys.NULL);
		} else {
			action.sendKeys(key.toUpperCase());
		}
	}
	
	private static WebElement dynamicXpath(SeHelper se, String value) {
		//*[contains(text(), '{}')]
		
		String[] split = value.split("\\$");			
		String xpath = "(//*[contains(text(), '" + split[1].trim() + "')] | //*[@value='" + split[1].trim() + "'])";
		By el = By.xpath(xpath);
		return se.element().getElement(el, true);
	}
}
