package com.test.automation.common;

import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.automation.common.SeHelper;

public abstract class CommonPage {

	public WebDriver driver;
	public FluentWait<WebDriver> wait;
	public SeHelper se;

	public void init(Map<String, Object> params) {
		// Override if there is any initialization on subclass. 
	}

	public WebDriver getDriver () {
		return this.driver;
	}

	public FluentWait<WebDriver> getWait() {
		return this.wait;
	}

	@Deprecated
	public void setWebDriver(WebDriver driver){
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
		this.se = new SeHelper();
		se.setDriver(driver);	
	}
	
	public void setSeHelper(SeHelper se){
		this.se = se;
		this.se = new SeHelper();
		this.driver = se.driver();
		se.setDriver(driver);	
		this.wait = new WebDriverWait(this.driver, 30).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
	}
	
}
