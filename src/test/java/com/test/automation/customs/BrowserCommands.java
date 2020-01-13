package com.test.automation.customs;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BrowserCommands {
	
	//Implement dynamic function selector based on String - change below functions to private
	
	
	

	//Changing tabs (e.g. alt+tab) without opening a new tab or closing the currently focused tab is impossible to automate
	
	public void newTab(WebDriver driver, String url) {
		((JavascriptExecutor) driver).executeScript("window.open('"+ url +"','_blank');");
	}
	
	public void closeTab(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.close();");
	}
	
	public void refreshPage(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.location.reload();");
	}
}
