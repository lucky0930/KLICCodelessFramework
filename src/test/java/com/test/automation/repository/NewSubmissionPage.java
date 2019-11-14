package com.test.automation.repository;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;

public class NewSubmissionPage extends Page {

	public static By customerAccountName = By.xpath("//*[@id='IdCustomerAccountName']");

	public static WebElement CustomerAccountName(SeHelper se) throws InterruptedException {
		se.waits().waitForElementIsDisplayed(customerAccountName);
		return se.element().getElement(customerAccountName, true);
	}

	public static By zipcode = By.xpath("//input[@id='location']");

	public static WebElement ZipCode(SeHelper se) {

		return se.element().getElement(zipcode, true);
	}

	public static By city = By.xpath("//*[@id='PrimaryAccountCity']");

	public static WebElement City(SeHelper se) {
		return se.element().getElement(city, true);
	}

	public static By state = By.xpath("//input[@id='PrimaryAccountState']");

	public static WebElement State(SeHelper se) {

		return se.element().getElement(state, true);
	}

	public static By streetAddress = By.xpath(" //input[@id='IDphysical_streetaddress']");

	public static WebElement StreetAddress(SeHelper se) {

		return se.element().getElement(streetAddress, true);

	}

	public static By apt = By.xpath("//input[@id='IDphysical_aptsuit']");

	public static WebElement Apt(SeHelper se) {

		return se.element().getElement(apt, true);
	}

	public static By MailingAddress = By.xpath("//*[@class='vam-maillabel']/label");

	public static WebElement MailingAddress(SeHelper se) {

		return se.element().getElement(MailingAddress, true);
	}

	public static By LegalEntityType = By.xpath("//*[@id='Id_legal_entity_type']");

	public static WebElement LegalEntityType(SeHelper se) {
		se.waits().waitForElement(LegalEntityType);
		return se.element().getElement(LegalEntityType, true);
	}

	public static By IndustryType = By.xpath("//*[@id='Id_industry_type']");

	public static WebElement IndustryType(SeHelper se) {
		se.waits().waitForElement(IndustryType);
		return se.element().getElement(IndustryType, true);
	}

	public static By SubIndustryType = By.xpath("//*[@id='Id_subIndustry_type']");

	public static WebElement SubIndustryType(SeHelper se) {
		se.waits().waitForElement(SubIndustryType);
		return se.element().getElement(SubIndustryType, true);
	}

	public static By SSN = By.xpath(
			"//span[contains(text(),'SSN')]//ancestor::label//following-sibling::div[@ng-show='isHideSSN']//input");

	public static WebElement SSN(SeHelper se) {
		se.waits().waitForElement(SSN);
		return se.element().getElement(SSN, true);
	}

	public static By FEIN = By.xpath("//*[@ng-blur=\"validatefeinNumber()\"]");

	public static WebElement FEIN(SeHelper se) {
		se.waits().waitForElement(FEIN);
		return se.element().getElement(FEIN, true);
	}

	public static By BusinessPhone = By.xpath("//*[@id='IdbusinessPhone']");

	public static WebElement BusinessPhone(SeHelper se) {

		se.waits().waitForElement(BusinessPhone);
		return se.element().getElement(BusinessPhone, true);
	}

	public static By EmailId = By.xpath("//*[@ng-blur='validateemail()']");

	public static WebElement EmailId(SeHelper se) {
		se.waits().waitForElement(EmailId);
		return se.element().getElement(EmailId);
	}

	public static By SelectProduct = By.xpath("//*[@id='id-selected-products']/div/button");

	public static WebElement SelectProduct(SeHelper se) {
		se.waits().waitForElement(SelectProduct);
		return se.element().getElement(SelectProduct, true);
	}

	public static By commercialAuto = By.xpath("//*[contains(text(),'Commercial Auto')]");

	public static WebElement CommercialAuto(SeHelper se) {

		return se.element().getElement(commercialAuto, true);
	}

	public static By AgencyName = By.xpath("//*[@id='Id_agency_name']");

	public static WebElement AgencyName(SeHelper se) {
		se.waits().waitForElement(AgencyName);
		return se.element().getElement(AgencyName, true);
	}

	public static By AgentName = By.xpath("//*[@id='Id_agent_name']");

	public static WebElement AgentName(SeHelper se) {
		se.waits().waitForElement(AgentName);
		return se.element().getElement(AgentName, true);
	}

	public static By CreateSubmission = By.xpath("//*[@ng-click='accountproceed()']");

	public static WebElement CreateSubmission(SeHelper se) {
		se.waits().waitForElement(CreateSubmission);
		return se.element().getElement(CreateSubmission, true);
	}

	public static By CreateSubmission1 = By.xpath("//*[@ng-click='accountproceed()']");

	public static WebElement CreateSubmission1(SeHelper se) {
		se.waits().waitForElementToDisappear(SelectAndProceed, 6);
		se.waits().waitForElementIsClickable(se.element().getElement(CreateSubmission1));
		// se.element().waitForElementIs(se.element().getElement(CreateSubmission1));
		return se.element().getElement(CreateSubmission1);

	}

	public static By cancel = By.xpath("//*[@id=\"scrollToDivID\"]/div[1]/form/div[3]/a");

	public static WebElement Cancel(SeHelper se) {
		se.waits().waitForElement(cancel);
		return se.element().getElement(cancel);
	}

	public static By SelectUser = By.xpath("//input[@name=\"Duplicate\"]");

	public static WebElement SelectUser(SeHelper se) {
		se.waits().waitForElementIsDisplayed(SelectUser);
		return se.element().getElement(SelectUser, true);
	}

	public static By SelectAndProceed = By.xpath("//*[@id=\"existingAccountpopup\"]/div/div/div[3]/button");

	public static WebElement SelectAndProceed(SeHelper se) {
		se.waits().waitForElement(SelectAndProceed);
		return se.element().getElement(SelectAndProceed, true);
	}

	public static By SelectAndProceed1 = By.xpath("//button[@class=\"btn btn-success pull-right\"]");

	public static WebElement SelectAndProceed1(SeHelper se) {
		se.waits().waitForElement(SelectAndProceed1);
		return se.element().getElement(SelectAndProceed1, true);
	}

	public static By VerifyCustomerAccountName1 = By.xpath("//*[@id='IdCustomerAccountName']");

	public static WebElement VerifyCustomerAccountName1(SeHelper se) {

		return se.element().getElement(VerifyCustomerAccountName1, true);
	}

	public static By Verifycity = By.xpath("//*[@id='PrimaryAccountCity']");

	public static WebElement VerifyCity(SeHelper se) {
		return se.element().getElement(Verifycity, true);
	}

	public static By Verifystate = By.xpath("//input[@id='PrimaryAccountState']");

	public static WebElement VerifyState(SeHelper se) {

		return se.element().getElement(Verifystate, true);
	}

	public static By VerifyMailingAddress = By.xpath("//*[@class='vam-maillabel']/label");

	public static WebElement VerifyMailingAddress(SeHelper se) {

		return se.element().getElement(VerifyMailingAddress, true);
	}

	public static By VerifyLegalEntityType = By.xpath("//*[@id='Id_legal_entity_type']");

	public static WebElement VerifyLegalEntityType(SeHelper se) {
		return se.element().getElement(VerifyLegalEntityType, true);
	}

	public static By VerifySSN1 = By.xpath("//span[contains(text(),'SSN')]");

	public static WebElement VerifySSN1(SeHelper se) {
		return se.element().getElement(VerifySSN1, true);
	}

	public static By VerifyFEIN = By.xpath("//span[contains(text(),'FEIN')]");

	public static WebElement VerifyFEIN(SeHelper se) {
		return se.element().getElement(VerifyFEIN, true);
	}

	public static By VerifyColourofCS = By.xpath("//*[@ng-click='accountproceed()']");

	public static WebElement VerifyColourofCS(SeHelper se) {
		return se.element().getElement(VerifyColourofCS, true);
	}
}
