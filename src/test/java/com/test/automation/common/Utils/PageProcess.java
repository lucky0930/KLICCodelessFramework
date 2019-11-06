package com.test.automation.common.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.test.automation.common.SeHelper;

public class PageProcess {

	public static WebElement findElement(SeHelper se, String sheetName, String key, String value) {
		Class<?> objClass = null;
		WebElement element = null;
		try {
			//se.log().logSeStep("Creating repository for page: \"" + sheetName + "\"");
			objClass = Class.forName("com.test.automation.repository." + sheetName);

		} catch (ClassNotFoundException e) {
			se.log().error("ClassNotFoundException encountered when the repository for page: " + sheetName + " was attempted to be created.", e);
			e.printStackTrace();
		}

		Constructor<?> constuctor = null;
		try {
			constuctor = objClass.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			se.log().error("Exception encountered when attempting to get constructor for page repository: " + sheetName, e);
			e.printStackTrace();
		}
		try {
			Object obj = constuctor.newInstance();

			try {
				se.log().logSeStep("Getting element: \"" + key + "\" on " + sheetName + " using value: \"" + value + "\"");
				Method callMethod = obj.getClass().getMethod(key, SeHelper.class);
				// Method callMethod = obj.getClass().getDeclaredMethod(key);
				callMethod.setAccessible(true);

				element = (WebElement) callMethod.invoke(obj, se);
				// element = (WebElement) callMethod.invoke(obj);

				if (element != null) {
					FillElement(se, element, key, value);
				}
			} catch (NoSuchMethodException e) {
				se.log().error("NoSuchMethodException encountered when attempting to get element: " + key + " on " + sheetName, e);
				e.printStackTrace();
			} catch (SecurityException e) {
				se.log().error("SecurityException encountered when attempting to get element: " + key + " on " + sheetName, e);
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			se.log().error("InstantiationException encountered when new instance of page: " + sheetName + " attempted to be created.", e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			se.log().error("IllegalAccessException encountered when new instance of page: " + sheetName + " attempted to be created.", e);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			se.log().error("IllegalArgumentException encountered when new instance of page: " + sheetName + " attempted to be created.", e);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			se.log().error("InvocationTargetException encountered when new instance of page: " + sheetName + " attempted to be created.", e);
			e.getCause().printStackTrace();
			e.printStackTrace();
		}
		return element;
	}

	private static void FillElement(SeHelper se, WebElement element, String key, String value) {

		switch (element.getTagName()) {
		case "input":
			if (value.equalsIgnoreCase("Click")) {
				element.click();
				break;
			} else {
				element.clear();
				element.sendKeys(value);
				break;
			}

		case "button":
			element.click();
			break;
		case "select":
			try {
				Select dropDownValue = new Select(element);
				dropDownValue.selectByValue(value);
			} catch (NoSuchElementException e) {
				se.log().error("NoSuchElementException encountered when trying to locate value \"" + value + "\" in element \"" + key + "\" \n", e);
				e.printStackTrace();
			} catch (Exception e) {
				se.log().error("Exception encountered when trying to locate value \"" + value + "\" in element \"" + key + "\" \n", e);
				e.printStackTrace();
			}
			break;
		case "a":
			element.click();
			break;
		case "label":
			element.click();
			break;
		default:
			//System.out.println("No tag value is identified!");
			se.log().debug("No tag value is identified!");
		}

	}
}
