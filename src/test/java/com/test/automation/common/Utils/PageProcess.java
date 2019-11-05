package com.test.automation.common.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.test.automation.common.SeHelper;

public class PageProcess {

	public static WebElement findElement(SeHelper se, String sheetName, String key, String value) {
		Class<?> objClass = null;
		WebElement element = null;
		try {
			objClass = Class.forName("com.test.automation.repository." + sheetName);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		Constructor<?> constuctor = null;
		try {
			constuctor = objClass.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {

			e.printStackTrace();
		}
		try {
			Object obj = constuctor.newInstance();

			try {
				Method callMethod = obj.getClass().getMethod(key, SeHelper.class);
				// Method callMethod = obj.getClass().getDeclaredMethod(key);
				callMethod.setAccessible(true);

				element = (WebElement) callMethod.invoke(obj, se);
				// element = (WebElement) callMethod.invoke(obj);

				if (element != null) {
					FillElement(element, value);
				}
			} catch (NoSuchMethodException e) {

				e.printStackTrace();
			} catch (SecurityException e) {

				e.printStackTrace();
			}
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.getCause().printStackTrace();
			e.printStackTrace();
		}
		return element;
	}

	private static void FillElement(WebElement element, String value) {

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
			Select dropDownValue = new Select(element);
			dropDownValue.selectByValue(value);
			break;
		case "a":
			element.click();
			break;
		case "label":
			element.click();
			break;
		default:
			System.out.println("No Tag value is identified!");
		}
		
	}
}
