package com.test.automation.repository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.common.SeHelper;

public class NewSubmissionPage {
	
	public NewSubmissionPage(SeHelper se) {
		PageFactory.initElements(se.driver(), this);
	}

	@FindBy(xpath = "//*[@id='IdCustomerAccountName']")
	static WebElement customerAccountName;

	public static WebElement CustomerAccountName(SeHelper se) {
		return customerAccountName;
	}
	@FindBy(xpath = "//input[@id='location']")
	static WebElement zipcode;

	public static WebElement ZipCode(SeHelper se) {
		return zipcode;
	}

	@FindBy(xpath = "//*[@id='PrimaryAccountCity']")
	static WebElement city;
	
	public static WebElement City(SeHelper se) {
		return city;
	}

	@FindBy(xpath = "//input[@id='PrimaryAccountState']")
	static WebElement state;

	public static WebElement State(SeHelper se) {
		return state;
	}

	@FindBy(xpath = " //input[@id='IDphysical_streetaddress']")
	static WebElement streetAddress;

	public static WebElement StreetAddress(SeHelper se) {
		return streetAddress;
	}

	@FindBy(xpath = "//input[@id='IDphysical_aptsuit']")
	static WebElement apt;

	public static WebElement Apt(SeHelper se) {
		return apt;
	}

	@FindBy(xpath = "//*[@class='vam-maillabel']/label")
	static WebElement MailingAddress;

	public static WebElement MailingAddress(SeHelper se) {
		return MailingAddress;
	}

	@FindBy(xpath = "//*[@id='Id_legal_entity_type']")
	static WebElement LegalEntityType;

	public static WebElement LegalEntityType(SeHelper se) {
		return LegalEntityType;
	}

	@FindBy(xpath = "//*[@id='Id_industry_type']")
	static WebElement IndustryType;

	public static WebElement IndustryType(SeHelper se) {
		return IndustryType;
	}

	@FindBy(xpath = "//*[@id='Id_subIndustry_type']")
	static WebElement SubIndustryType;

	public static WebElement SubIndustryType(SeHelper se) {
		return SubIndustryType;
	}

	@FindBy(xpath = "//span[contains(text(),'SSN')]//ancestor::label//following-sibling::div[@ng-show='isHideSSN']//input")
	static WebElement SSN;

	public static WebElement SSN(SeHelper se) {
		return SSN;
	}

	@FindBy(xpath = "//*[@ng-blur=\"validatefeinNumber()\"]")
	static WebElement FEIN;

	public static WebElement FEIN(SeHelper se) {
		return FEIN;
	}

	@FindBy(xpath = "//*[@id='IdbusinessPhone']")
	static WebElement BusinessPhone;

	public static WebElement BusinessPhone(SeHelper se) {
		return BusinessPhone;
	}

	@FindBy(xpath = "//*[@ng-blur='validateemail()']")
	static WebElement EmailId;

	public static WebElement EmailId(SeHelper se) {
		return EmailId;
	}

	@FindBy(xpath = "//*[@id='id-selected-products']/div/button")
	static WebElement SelectProduct;

	public static WebElement SelectProduct(SeHelper se) {
		return SelectProduct;
	}

	@FindBy(xpath = "//*[contains(text(),'Commercial Auto')]")
	static WebElement CommercialAuto;

	public static WebElement CommercialAuto(SeHelper se) {
		return CommercialAuto;
	}

	@FindBy(xpath = "//*[@id='Id_agency_name']")
	static WebElement AgencyName;

	public static WebElement AgencyName(SeHelper se) {
		return AgencyName;
	}

	@FindBy(xpath = "//*[@id='Id_agent_name']")
	static WebElement AgentName;

	public static WebElement AgentName(SeHelper se) {
		return AgentName;
	}

	@FindBy(xpath = "//*[@ng-click='accountproceed()']")
	static WebElement CreateSubmission;

	public static WebElement CreateSubmission(SeHelper se) {
		return CreateSubmission;
	}

	public static WebElement CreateSubmission1(SeHelper se) {
		se.waits().waitForElementToDisappear(SelectAndProceed, 6);
		se.waits().waitForElementIsClickable(CreateSubmission);
		// se.element().waitForElementIs(se.element().getElement(CreateSubmission1));
		return CreateSubmission;

	}

	@FindBy(xpath = "//*[@id=\"scrollToDivID\"]/div[1]/form/div[3]/a")
	static WebElement cancel;

	public static WebElement Cancel(SeHelper se) {
		return cancel;
	}

	@FindBy(xpath = "//input[@name=\"Duplicate\"]")
	static WebElement SelectUser;

	public static WebElement SelectUser(SeHelper se) {
		return SelectUser;
	}

	@FindBy(xpath = "//*[@id=\"existingAccountpopup\"]/div/div/div[3]/button")
	static WebElement SelectAndProceed;

	public static WebElement SelectAndProceed(SeHelper se) {
		return SelectAndProceed;
	}

	@FindBy(xpath = "//button[@class=\"btn btn-success pull-right\"]")
	static WebElement SelectAndProceed1;

	public static WebElement SelectAndProceed1(SeHelper se) {
		return SelectAndProceed1;
	}

	public static WebElement VerifyCustomerAccountName1(SeHelper se) {
		return customerAccountName;
	}

	public static WebElement VerifyCity(SeHelper se) {
		return city;
	}

	public static WebElement VerifyState(SeHelper se) {
		return state;
	}

	public static WebElement VerifyMailingAddress(SeHelper se) {
		return MailingAddress;
	}

	public static WebElement VerifyLegalEntityType(SeHelper se) {
		return LegalEntityType;
	}

	public static WebElement VerifySSN1(SeHelper se) {
		return SSN;
	}

	public static WebElement VerifyFEIN(SeHelper se) {
		return FEIN;
	}

	public static WebElement VerifyColourofCS(SeHelper se) {
		return CreateSubmission;
	}
}
