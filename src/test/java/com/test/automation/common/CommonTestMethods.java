package com.test.automation.common;


import java.util.Calendar;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.test.automation.common.SeHelper;


public class CommonTestMethods {
	public static HashMap<String, Integer> columnmap;
	public static HashMap<String, Integer> rowmap;
	public static HashMap<?, ?> t_columnmap;
	public static HashMap<?, ?> t_rowmap;
	String strParentWindow, strChildWindow;
	public static Calendar cal = Calendar.getInstance();
	public int ulCnt;

	private SeHelper se;

	public CommonTestMethods(SeHelper se) {
		this.se = se;
	}

	@Deprecated
	public WebDriver getWebDriver() {
		return se.driver();
	}

	/**
	 * Description : This method change the dropdown Value
	 * 
	 * @param dropDownElement
	 * @param value
	 */
	public void changeDropdownValue(WebElement dropDownElement, String value) {
		String javaScript = "('dropDownElement').val('value').change()";
		JavascriptExecutor jsdropdown = (JavascriptExecutor) se.driver();
		jsdropdown.executeScript(javaScript);

	}

	/**
	 * Description : This method is used to get the numbers from a given string
	 * 
	 * @param strText
	 * @return
	 */
	public static String getNumberFromString(String strText) {
		String str = strText;
		StringBuilder myNumbers = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				myNumbers.append(str.charAt(i));
				// se.log().logTestStep(str.charAt(i) + " is a digit.");
			} else {
				// se.log().logTestStep(str.charAt(i) + " not a digit.");
			}
		}
		return myNumbers.toString();
	}

	

}