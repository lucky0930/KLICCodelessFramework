package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;
import com.test.automation.common.framework.Element;

public class LogInPage extends Page {

	public static By username = By.xpath("//*[@id='UserName']");

	public static WebElement UserName(SeHelper se) {

		return se.element().getElement(username, true);
	}

	public static By next = By.xpath("//*[@id='UserName']//following::button");

	public static WebElement Next(SeHelper se) {
		se.element().waitForElement(next);
		return se.element().getElement(next);
	}

	public static By password = By.xpath("//*[@id='Password']");

	public static WebElement Password(SeHelper se) {
		se.element().waitForElement(password);
		return se.element().getElement(password);
	}

	public static By login = By.xpath("//*[@id='Password']//following::button");

	public static WebElement LogIn(SeHelper se) {
		se.element().waitForElement(login);
		return se.element().getElement(login);
	}

}
