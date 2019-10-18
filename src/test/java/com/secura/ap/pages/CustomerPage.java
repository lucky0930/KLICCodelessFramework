package com.secura.ap.pages;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.framework.Util;
import com.test.automation.repository.OR_Common;
import com.test.automation.repository.OR_Customer;

public class CustomerPage extends OR_Customer {

	String CA = "Commercial Auto";
	String WC = "Workers Comp";

	public void newCustomer(Map<String, String> row,ExtentTest test) throws IOException {

      // String searchbox = (String) row.get("searchbox");
	//	String partnerNumber = (String) row.get("partnerNumber");
     //  System.out.println("searchbox :>>>>>>   " +partnerNumber);
	    /*String UserName = (String) row.get("UserName");
	    System.out.println("UserName :>>>>>>   " +UserName);
		String password = (String) row.get("password");
		System.out.println("password :>>>>>>   " +password);*/
		Util util = new Util();
		String CustomerAccountName = (String) row.get("CustomerAccountName");
		String ZipCode = (String) row.get("ZipCode");
		String City = (String) row.get("City");
		String StreetAddress = (String) row.get("StreetAddress");
		String LegalEntityType = (String) row.get("LegalEntityType");
		String IndustryType = (String) row.get("IndustryType");
		String SubIndustryType = (String) row.get("SubIndustryType");
		String SSN = (String) row.get("SSN");
		String BusinessPhone = (String) row.get("BusinessPhone");
		String EmailID = (String) row.get("EmailID");
		String SelectProduct = (String) row.get("SelectProduct");
		String AgencyName = (String) row.get("AgencyName");
		String AgentName = (String) row.get("AgentName"); 
		try {
			/*se.verify().verifyEquals("Login Page is displayed", getUsername().isDisplayed(),true,true, test);
			//se.element().enterText(getPartnerNumber(), partnerNumber);
			se.element().enterText(getUsername(), UserName);
			se.element().Click(getUNNext());
			se.element().enterText(getPassword(), password);
			se.element().Click(getLoginbtn());
			Thread.sleep(5000);
			//directClickSub();
			//se.driver().findElement().click();
			//se.element().clickElement(By.cssSelector("a[ng-click='navigateToMultiQuote()']"));
			/*se.element().Click(ClickNewSubmission());
			 Thread.sleep(1000);
			System.out.println("clicked on Login");*/
			
			se.verify().verifyEquals("New Customer Page is displayed", validateCustomerPage().isDisplayed(),true,true, test);
			
			se.element().enterText(GetCustomerAccountName(), CustomerAccountName);
			se.element().enterText(GetZipCode(), ZipCode);			
			se.element().selectvalueByElement(GetCity(), City);
			se.element().enterText(GetStreetAddress(), StreetAddress);			
			se.element().Click(ClickMailingAddress());
			
			se.element().selectvalueByElement(GetLegalEntityType(), LegalEntityType);
			se.element().enterText(GetIndustryType(), IndustryType);
			se.element().enterText(GetSubIndustryType(), SubIndustryType);
			se.element().enterText(GetSSN(), SSN);
			se.element().enterText(GetBusinessPhone(), BusinessPhone);
			se.element().enterText(GetEmailID(), EmailID);
			
			se.element().Click(GetSelectProduct());
			se.element().Click(GetSelectAll());
			se.element().Click(GetSelectComercialAuto());
			
			se.verify().verifyEquals("Customer info. able entered", true,true,true, test);
			se.verify().verifyEquals("Agency info. able entered", true,true,true, test);
			//se.element().selectvalueByElement(GetSelectProduct(), SelectProduct);
			se.element().enterText(GetAgencyName(), AgencyName);
			se.element().enterText(GetAgentName(), AgentName);
			Thread.sleep(3000);
			se.element().Click(ClickCreateSubmission());
			
			
			se.element().Click(ClickDuplicateAccount());
			se.element().Click(ClickSelectAndProceed());
			Thread.sleep(2000);
			se.element().Click(ClickCreateSubmission());
			Thread.sleep(2000);
			
			/*se.element().Click(getlifeann());
			 Thread.sleep(2000);
			 System.out.println("clicked on getlifeann");
			se.element().Click(gethealthc());
			 Thread.sleep(2000);
			 Actions a = new Actions(driver);
			 a.sendKeys(Keys.ARROW_DOWN).build().perform();
			 a.sendKeys(Keys.ENTER).build().perform();
			 Thread.sleep(3000);
			 se.element().enterText(getCompanyName(), Company);
			 Thread.sleep(3000);
			 se.element().Click(gettitle());
			 Thread.sleep(2000);
			 a.sendKeys(Keys.ARROW_DOWN).build().perform();
			 a.sendKeys(Keys.ENTER).build().perform();
			 Thread.sleep(2000);
			 se.element().enterText(getFirstName(), FirstName);
			 Thread.sleep(2000);
			 se.element().enterText(getLastName(), LastName);
			 Thread.sleep(20000);
			/* System.out.println("clicked on gethealthc");
			se.element().Click(getbanking());
			Thread.sleep(5000);
			System.out.println("clicked on getbanking");
			Thread.sleep(5000);
			System.out.println("clicked on getcontactus");
			se.element().Click(getcontactus());*/
			
			
//			test.log(LogStatus.INFO, "Entering text in USERNAME & PASSWORD fields","<b>USERNAME : " + strLgnUsername + "<br> <b>PASSWORD : " + strLgnPassword);
//			test.log(LogStatus.INFO, "Clicking on the button", "Click on LOGIN");
		} catch (Exception e) {
			se.verify().verifyEquals("Issue with login screen", true, false, true, test);
		}
	}
}
