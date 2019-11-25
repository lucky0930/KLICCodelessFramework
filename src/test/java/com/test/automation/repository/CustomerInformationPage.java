package com.test.automation.repository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.common.SeHelper;

public class CustomerInformationPage {
	
	public CustomerInformationPage(SeHelper se) {
		PageFactory.initElements(se.driver(), this);
	}
	
	@FindBy(xpath = "//li[@ng-click=\"setCustomerAccountDetailsTab('submissions')\"]")
	static WebElement Submissions;

	public static WebElement Submissions(SeHelper se) {
		return Submissions;
	}
	
	@FindBy(xpath = "//a[@ng-click=\"CreateNewSubmission()\"]")
	static WebElement AddNewSubmission;

	public static WebElement AddNewSubmission(SeHelper se) {
		return AddNewSubmission;
	}
}
