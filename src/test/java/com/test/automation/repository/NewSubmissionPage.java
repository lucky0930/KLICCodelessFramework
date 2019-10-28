package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;

public class NewSubmissionPage extends Page {

	public static By customerAccountName = By.xpath("//input[@type='text']");

	public static WebElement CustomerAccountName(SeHelper se) {

		return se.element().getElement(customerAccountName);
	}

	public static By zipcode = By.xpath("//input[@id='location']");

	public static WebElement ZipCode(SeHelper se) {

		return se.element().getElement(zipcode);
	}

	public static By city = By.xpath("//*[@id='PrimaryAccountCity']");

	public static WebElement City(SeHelper se) {
		return se.element().getElement(city);
	}

	public static By state = By.xpath("//input[@id='PrimaryAccountState']");

	public static WebElement State(SeHelper se) {

		return se.element().getElement(state);
	}

	public static By streetAddress = By.xpath(" //input[@id='IDphysical_streetaddress']");

	public static WebElement StreetAddress(SeHelper se) {

		return se.element().getElement(streetAddress);

	}

	public static By apt = By.xpath("//input[@id='IDphysical_aptsuit']");

	public static WebElement Apt(SeHelper se) {

		return se.element().getElement(apt);
	}

	public By MailingAddress = By.xpath("//*[@class='vam-maillabel']/label");

	public WebElement MailingAddress() {
		se.element().waitForElement(MailingAddress);
		return se.element().getElement(MailingAddress);
	}

	public By LegalEntityType = By.xpath("//*[@id='Id_legal_entity_type']");

	public WebElement LegalEntityType() {
		se.element().waitForElement(LegalEntityType);
		return se.element().getElement(LegalEntityType);
	}

	public By IndustryType = By.xpath("//*[@id='Id_industry_type']");

	public WebElement IndustryType() {
		se.element().waitForElement(IndustryType);
		return se.element().getElement(IndustryType);
	}

	public By SubIndustryType = By.xpath("//*[@id='Id_subIndustry_type']");

	public WebElement SubIndustryType() {
		se.element().waitForElement(SubIndustryType);
		return se.element().getElement(SubIndustryType);
	}

	public By BusinessPhone = By.xpath("//*[@id='IdbusinessPhone']");

	public WebElement BusinessPhone() {
		se.element().waitForElement(BusinessPhone);
		return se.element().getElement(BusinessPhone);
	}

	public By EmailID = By.xpath("//*[@id='email_id']");

	public WebElement EmailID() {
		se.element().waitForElement(EmailID);
		return se.element().getElement(EmailID);
	}

	public By SelectProduct = By.xpath("//*[@id='id-selected-products']/div/button");

	public WebElement SelectProduct() {
		se.element().waitForElement(SelectProduct);
		return se.element().getElement(SelectProduct);
	}

	public By AgencyName = By.xpath("//*[@id='Id_agency_name']");

	public WebElement AgencyName() {
		se.element().waitForElement(AgencyName);
		return se.element().getElement(AgencyName);
	}

	public By AgentName = By.xpath("//*[@id='Id_agent_name']");

	public WebElement AgentName() {
		se.element().waitForElement(AgentName);
		return se.element().getElement(AgentName);
	}

	public By CreateSubmission = By.xpath("//*[@ng-click='accountproceed()']");

	public WebElement CreateSubmission() {
		se.element().waitForElement(CreateSubmission);
		return se.element().getElement(CreateSubmission);
	}

	public By cancel = By.xpath("//*[@id=\"scrollToDivID\"]/div[1]/form/div[3]/a");

	public WebElement Cancel() {
		se.element().waitForElement(cancel);
		return se.element().getElement(cancel);
	}
}
