package com.test.automation.repository;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;

public class IndexPage extends Page {
	
	public static By newSubmission = By.xpath("//*[@ng-click='navigateToMultiQuote()']");

	public static WebElement NewSubmission(SeHelper se) {
		
		se.element().waitForElementIsClickable(newSubmission);
		return se.element().getElement(newSubmission);
	}

	public static By ServicePolicy = By.xpath("//a[@ng-click=\"CustomerAndPolicySearch()\"]");

	public static WebElement ServicePolicy(SeHelper se) {
		se.element().waitForElementIsDisplayed(ServicePolicy);
		return se.element().getElement(ServicePolicy);
	}

	public static By VerifyUser = By.xpath("//*[@class='user hidden-xs']/span");

	public static WebElement VerifyUser(SeHelper se) throws InterruptedException {
		se.element().waitForElementToDisappear(LogInPage.login, 6);
		se.element().waitForElement(VerifyUser);
		return se.element().getElement(VerifyUser);
	}
}
