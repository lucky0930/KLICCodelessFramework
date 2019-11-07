package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;

public class CustomerInformationPage extends Page {
	public static By Submissions = By.xpath("//li[@ng-click=\"setCustomerAccountDetailsTab('submissions')\"]");

	public static WebElement Submissions(SeHelper se) {

		return se.element().getElement(Submissions, true);
	}
	public static By AddNewSubmission = By.xpath("//a[@ng-click=\"CreateNewSubmission()\"]");

	public static WebElement AddNewSubmission(SeHelper se) {

		return se.element().getElement(AddNewSubmission, true);
	}
}
