package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.test.automation.common.Page;

public class OR_Common extends Page {


	/*public By searchbox = By.xpath("//input[@name='q']");

	public WebElement getPartnerNumber() {
		se.element().waitForElement(searchbox);
	 	return se.element().getElement(searchbox);
	}*/
	public By propcas = By.xpath("(//a[@class='btn btn-info floatR'])[1]");

	public WebElement getpropertycas() {
		se.element().waitForElement(propcas);
	 	return se.element().getElement(propcas);
	}
	
	public By lifeann = By.xpath("//*[@id='divHome']/div[2]/div[1]/div/div[2]/div[1]/a/img");

	public WebElement getlifeann() {
		se.element().waitForElement(lifeann);
	 	return se.element().getElement(lifeann);
	 	
	}
	public By healthcare = By.xpath("//*[@id='divAccount']/div[2]/div[2]/div[1]/div[3]/div[2]/span/span/span[2]");

	public WebElement gethealthc() {
		se.element().waitForElement(healthcare);
	 	return se.element().getElement(healthcare);
	 	
	}
	
	public By title = By.xpath("//*[@id='divAccount']/div[2]/div[2]/div[1]/div[6]/div[2]/span/span[1]/span[2]/span");

	public WebElement gettitle() {
		se.element().waitForElement(title);
	 	return se.element().getElement(title);
	 	
	}
	public By dropsele = By.xpath("//*[@id='divAccount']/div[2]/div[2]/div[1]/div[3]/div[2]/span/span/span[2]");

	public void getdropsele() {
		se.element().waitForElement(dropsele);
	   se.element().getElement(dropsele).sendKeys(Keys.ARROW_DOWN);;
	   
	}
	
	public By contactus = By.xpath("(//a[@class='button button_large'])[1]");

	public WebElement getcontactus() {
	se.element().waitForElement(contactus);
	return se.element().getElement(contactus);
	}			
	
	public By banking = By.xpath("//span[text()=' Software and Services for... ']//following::span[4]");

	public WebElement getbanking() {
		se.element().waitForElement(banking);
	 	return se.element().getElement(banking);
	}
	
	public By username = By.xpath("//*[@id='UserName']");

	public WebElement getUsername() {
		se.element().waitForElement(username);
		return se.element().getElement(username);
	}

	public By password = By.xpath("//*[@id='Password']");

	public WebElement getPassword() {
		se.element().waitForElement(password);
		return se.element().getElement(password);
	}
	
	public By Next = By.xpath("//*[@id=\"UserName\"]//following::button");
	
	public WebElement getUNNext() {
		se.element().waitForElement(Next);
		return se.element().getElement(Next);
	}

	public By login = By.xpath("//*[@id='Password']//following::button");

	public WebElement getLoginbtn() {
		se.element().waitForElement(login);
		return se.element().getElement(login);
	}
	
	//public By NewSubmission = By.xpath("//*[@ng-click='navigateToMultiQuote()']");
	public By NewSubmission = By.cssSelector("a[ng-click='navigateToMultiQuote()']");
	public WebElement ClickNewSubmission() {
		se.element().waitForElement(NewSubmission);
		return se.element().getElement(NewSubmission);
	}
	
	public By CustomerAccountName = By.xpath("//*[@id='IdCustomerAccountName']");
	public WebElement GetCustomerAccountName() {
		se.element().waitForElement(CustomerAccountName);
		return se.element().getElement(CustomerAccountName);
	}
	
	public By ZipCode = By.xpath("//*[@id='location']");
	public WebElement GetZipCode() {
		se.element().waitForElement(ZipCode);
		return se.element().getElement(ZipCode);
	}
	
	public By City = By.xpath("//*[@id='PrimaryAccountCity']");
	public WebElement GetCity() {
		se.element().waitForElement(City);
		return se.element().getElement(City);
	}
	
	public By StreetAddress = By.xpath("//*[@id='IDphysical_streetaddress']");
	public WebElement GetStreetAddress() {
		se.element().waitForElement(StreetAddress);
		return se.element().getElement(StreetAddress);
	}
	
	public By MailingAddress = By.xpath("//*[@class='vam-maillabel']/label");
	
	public WebElement ClickMailingAddress() {
		se.element().waitForElement(MailingAddress);
		return se.element().getElement(MailingAddress);
	}
	
	public By LegalEntityType = By.xpath("//*[@id='Id_legal_entity_type']");
	public WebElement GetLegalEntityType() {
		se.element().waitForElement(LegalEntityType);
		return se.element().getElement(LegalEntityType);
	}
	
	public By IndustryType = By.xpath("//*[@id='Id_industry_type']");
	public WebElement GetIndustryType() {
		se.element().waitForElement(IndustryType);
		return se.element().getElement(IndustryType);
	}
	
	public By SubIndustryType = By.xpath("//*[@id='Id_subIndustry_type']");
	public WebElement GetSubIndustryType() {
		se.element().waitForElement(SubIndustryType);
		return se.element().getElement(SubIndustryType);
	}
	
	public By SSN = By.xpath("//*[@id='IdSsnNumber']");
	public WebElement GetSSN() {
		se.element().waitForElement(SSN);
		return se.element().getElement(SSN);
	}
	
	public By BusinessPhone = By.xpath("//*[@id='IdbusinessPhone']");
	public WebElement GetBusinessPhone() {
		se.element().waitForElement(BusinessPhone);
		return se.element().getElement(BusinessPhone);
	}
	
	public By EmailID = By.xpath("//*[@id='email_id']");
	public WebElement GetEmailID() {
		se.element().waitForElement(EmailID);
		return se.element().getElement(EmailID);
	}
	
	public By SelectProduct = By.xpath("//*[@id='id-selected-products']/div/button");
	public WebElement GetSelectProduct() {
		se.element().waitForElement(SelectProduct);
		return se.element().getElement(SelectProduct);
	}
	
	public By CommercialAuto = By.xpath("//*[@id=\"mCSB_1_container\"]/li[5]");
	public WebElement GetSelectComercialAuto() {
		se.element().waitForElement(CommercialAuto);
		return se.element().getElement(CommercialAuto);
	}
	
	public By AgencyName = By.xpath("//*[@id='Id_agency_name']");
	public WebElement GetAgencyName() {
		se.element().waitForElement(AgencyName);
		return se.element().getElement(AgencyName);
	}
	
	public By AgentName = By.xpath("//*[@id='Id_agent_name']");
	public WebElement GetAgentName() {
		se.element().waitForElement(AgentName);
		return se.element().getElement(AgentName);
	}
	
	public By CreateSubmission = By.xpath("//*[@ng-click='accountproceed()']");
	public WebElement ClickCreateSubmission() {
		se.element().waitForElement(CreateSubmission);
		return se.element().getElement(CreateSubmission);
	}
	
    public By vendors = By.xpath("//span[text()=\"vendors\"]");

    public WebElement getVendorsTab() {
        se.element().waitForElement(vendors);
        return se.element().getElement(vendors);
    }

	public By newVendor = By.xpath("//span[text()=\">>> new vendor\"]");

	public WebElement getNewVendorBtn() {
		se.element().waitForElement(newVendor);
		return se.element().getElement(newVendor);
	}

	public By vendorType = By.xpath("//input[contains(@id,\"200909\")]");

	public WebElement getVendorTypeDD() {
		se.element().waitForElement(vendorType);
		return se.element().getElement(vendorType);
	}

	public By vendorTypeValue = By.xpath("//li[text()=\"Legal Counsel\"]");

	public WebElement getVendorTypeValue() {
		se.element().waitForElement(vendorTypeValue);
		return se.element().getElement(vendorTypeValue);
	}

	public By companyName = By.xpath("//*[@id='AccountNameControl']/input");

	public WebElement getCompanyName() {
		se.element().waitForElement(companyName);
		return se.element().getElement(companyName);
	}

	public By address = By.xpath("//input[contains(@id,\"29325214\")]");

	public WebElement getAddress() {
		se.element().waitForElement(address);
		return se.element().getElement(address);
	}

	public By zip = By.xpath("//input[contains(@id,\"29327414\")]");

	public WebElement getZIP() {
		se.element().waitForElement(zip);
		return se.element().getElement(zip);
	}

	public By page = By.xpath("//div[text()=\"Welcome\"]");

	public WebElement getPage() {
		se.element().waitForElement(page);
		return se.element().getElement(page);
	}

	public By vendorSearch = By.xpath("//span[text()=\">>>vendor search\"]");

	public WebElement getVendorSearch() {
		se.element().waitForElement(vendorSearch);
		return se.element().getElement(vendorSearch);
	}

	public By serviceType = By.xpath("//label[text()=\"Coverage Counsel\"]/preceding-sibling::span/input");

	public WebElement getServicetype() {
		se.element().waitForElement(serviceType);
		return se.element().getElement(serviceType);
	}

	public By firstName = By.xpath("//*[@id='txtFirstName']");

	public WebElement getFirstName() {
		se.element().waitForElement(firstName);
		return se.element().getElement(firstName);
	}
	public By lastName = By.xpath("//*[@id='txtlastName']");

	public WebElement getLastName() {
		se.element().waitForElement(lastName);
		return se.element().getElement(lastName);
	}
	public By phone = By.xpath("//input[contains(@id,\"28224805p211932\")]");

	public WebElement getPhone() {
		se.element().waitForElement(phone);
		return se.element().getElement(phone);
	}
	public By email = By.xpath("//input[contains(@id,\"28224805p211461\")]");

	public WebElement getEmail() {
		se.element().waitForElement(email);
		return se.element().getElement(email);
	}

	public By approve = By.xpath("//span[text()=\">>>approve\"]");

	public WebElement getApproveBtn() {
		se.element().waitForElement(approve);
		return se.element().getElement(approve);
	}

	public By notes = By.xpath("//textarea[contains(@id,\"31188946\")]");

	public WebElement getNotes() {
		se.element().waitForElement(notes);
		return se.element().getElement(notes);
	}
	public By _approve = By.xpath("//span[text()=\">>> approve\"]");

	public WebElement get_approve() {
		se.element().waitForElement(_approve);
		return se.element().getElement(_approve);
	}
	public By exit = By.xpath("//span[text()=\"exit\"]");

	public WebElement getExitBtn() {
		se.element().waitForElement(exit);
		return se.element().getElement(exit);
	}

	public By policies = By.xpath("//span[text()=\"policies\"]");

	public WebElement getPoliciesTab() {
		se.element().waitForElement(policies);
		return se.element().getElement(policies);
	}

	public By policyNumber = By.xpath("//span[text()=\"GL38588274756\"]");

	public WebElement getpolicyNumber() {
		se.element().waitForElement(policyNumber);
		return se.element().getElement(policyNumber);
	}

	public By billingacct = By.xpath("//span[text()=\"Billing Acct\"]");

	public WebElement getbillingacctTab() {
		se.element().waitForElement(billingacct);
		return se.element().getElement(billingacct);
	}

	public By singlePayment = By.xpath("//span[text()=\"single payment\"]");

	public WebElement getSinglePaymentBtn() {
		se.element().waitForElement(singlePayment);
		return se.element().getElement(singlePayment);
	}

	public By transactionAmount = By.xpath("//input[contains(@id,\"29708946\")]");

	public WebElement getTransactionAmount() {
		se.element().waitForElement(transactionAmount);
		return se.element().getElement(transactionAmount);
	}

	public By paymentModeDD = By.xpath("//input[contains(@id,\"29743646p30744646\")]");

	public WebElement getPaymentModeDD() {
		se.element().waitForElement(paymentModeDD);
		return se.element().getElement(paymentModeDD);
	}

	public By checkPayment = By.xpath("//li[text()=\"Check Payment\"]");

	public WebElement getCheckPayment() {
		se.element().waitForElement(checkPayment);
		return se.element().getElement(checkPayment);
	}

	public By check = By.xpath("//input[contains(@id,\"14222546\")]");

	public WebElement getCheck() {
		se.element().waitForElement(check);
		return se.element().getElement(check);
	}

	public By proceed = By.xpath("//span[text()=\">>> proceed\"]");

	public WebElement getProceedBtn() {
		se.element().waitForElement(proceed);
		return se.element().getElement(proceed);
	}


}
