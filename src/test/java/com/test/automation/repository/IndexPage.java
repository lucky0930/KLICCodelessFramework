package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;

public class IndexPage extends Page {
	public static By newSubmission = By.xpath("//*[@ng-click='navigateToMultiQuote()']");

	public static WebElement NewSubmission(SeHelper se) {
		se.element().waitForElement(newSubmission);
		return se.element().getElement(newSubmission);
	}

	public static By ServicePolicy = By.xpath("//a[@ng-click=\"CustomerAndPolicySearch()\"]");

	public static WebElement ServicePolicy(SeHelper se) {
		se.element().waitForElement(ServicePolicy);
		return se.element().getElement(ServicePolicy);
	}

	public static By VerifyUser = By.xpath("//*[@class='user hidden-xs']/span");

	public static WebElement VerifyUser(SeHelper se) {
		se.element().waitForElement(VerifyUser);
		return se.element().getElement(VerifyUser);
	}
}
