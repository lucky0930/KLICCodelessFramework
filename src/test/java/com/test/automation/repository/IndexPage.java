package com.test.automation.repository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.common.SeHelper;

public class IndexPage {
	
	public IndexPage(SeHelper se) {
		PageFactory.initElements(se.driver(), this);
	}
	
	@FindBy(xpath = "//*[@ng-click='navigateToMultiQuote()']")
	static WebElement newSubmission; 

	public static WebElement NewSubmission(SeHelper se) {
		return newSubmission;
	}

	@FindBy(xpath = "//a[@ng-click=\"CustomerAndPolicySearch()\"]")
	static WebElement ServicePolicy;

	public static WebElement ServicePolicy(SeHelper se) {
		return ServicePolicy;
	}

	@FindBy(xpath = "//*[@class='user hidden-xs']/span")
	static WebElement VerifyUser;

	public static WebElement VerifyUser(SeHelper se) throws InterruptedException {
		se.waits().waitForElementToDisappear(LogInPage.LogIn(se), 6);
		return VerifyUser;
	}
}
