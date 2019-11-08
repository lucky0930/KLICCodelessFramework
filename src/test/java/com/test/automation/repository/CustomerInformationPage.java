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
	public static By VerifyCustomerAccountID = By.xpath("(//span[contains(text(),'Customer Account ID')])[2]//parent::div//following-sibling::div");

	public static WebElement VerifyCustomerAccountID(SeHelper se) throws InterruptedException {
		se.element().waitForElementIsDisplayed(VerifyCustomerAccountID);
		return se.element().getElement(VerifyCustomerAccountID, true);
	}
	public static By VerifyCustomerSince = By.xpath("(//span[contains(text(),'Customer Since')])[2]//parent::div//following-sibling::div");

	public static WebElement VerifyCustomerSince(SeHelper se) throws InterruptedException {
		
		return se.element().getElement(VerifyCustomerSince, true);
	}
	public static By VerifyLegalEntityType = By.xpath("(//span[contains(text(),'Legal Entity Type')])[2]//parent::div//following-sibling::div");

	public static WebElement VerifyLegalEntityType(SeHelper se) throws InterruptedException {
		
		return se.element().getElement(VerifyLegalEntityType, true);
	}
	public static By VerifySSN = By.xpath("(//span[contains(text(),'SSN')])[2]//parent::div//following-sibling::div");

	public static WebElement VerifySSN(SeHelper se) throws InterruptedException {
		
		return se.element().getElement(VerifySSN, true);
	}
	public static By VerifyPhone = By.xpath("(//span[contains(text(),'Phone #')])[2]//parent::div//following-sibling::div");

	public static WebElement VerifyPhone(SeHelper se) throws InterruptedException {
		
		return se.element().getElement(VerifyPhone, true);
	}
	public static By VerifyEmailID = By.xpath("(//span[contains(text(),'Email ID')])[2]//parent::div//following-sibling::div");

	public static WebElement VerifyEmailID(SeHelper se) throws InterruptedException {
		
		return se.element().getElement(VerifyEmailID, true);
	}
	public static By VerifyPhysicalAddress = By.xpath("(//span[contains(text(),'Physical Address')])[2]//parent::div//following-sibling::div");

	public static WebElement VerifyPhysicalAddress(SeHelper se) throws InterruptedException {
		
		return se.element().getElement(VerifyPhysicalAddress, true);
	}
	public static By VerifyMailingAddress = By.xpath("(//span[contains(text(),'Mailing Address')])[2]//parent::div//following-sibling::div");

	public static WebElement VerifyMailingAddress(SeHelper se) throws InterruptedException {
		
		return se.element().getElement(VerifyMailingAddress, true);
	}
}
