package com.test.automation.customs;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.test.automation.common.SeHelper;

public class BrowserCommands {
	
	//Implement dynamic function selector based on String - change below functions to private
	
	BrowserCommands(String key, SeHelper se) {
		try {
			Method callMethod = this.getClass().getMethod(key);
			callMethod.setAccessible(true);
		} catch (NoSuchMethodException e) {
			se.log().error("Method: " + key + " does not exist.", e);
			e.printStackTrace();
		}
	}
	
	//Changing tabs (e.g. ctrl+tab) without opening a new tab or closing the currently 
	//	focused tab is messy in this environment --- not recommended
	
	private void newTab(WebDriver driver, String url) {
		((JavascriptExecutor) driver).executeScript("window.open('"+ url +"','_blank');");
	}
	
	private void closeTab(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.close();");
	}
	
	private void refreshPage(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.location.reload();");
	}
}
