package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;

public class CustomerInformationPage extends Page {
	public static By Submissions = By.xpath("//*[@id=\"submissionsTab\"]");

	public static WebElement Submissions(SeHelper se) {

		return se.element().getElement(Submissions, true);
	}
	public static By AddNewSubmission = By.xpath("//*[@ng-click=\"CreateNewSubmission()\"]");

	public static WebElement AddNewSubmission(SeHelper se) {

		return se.element().getElement(AddNewSubmission, true);
	}
}
