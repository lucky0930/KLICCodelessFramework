package com.secura.ap.pages;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.repository.OR_Common;
import com.test.automation.repository.OR_Home;
import com.test.automation.repository.OR_Submission;

public class SubmissionPage extends OR_Submission {

	public void Submission(Map<String, String> row,ExtentTest test) throws IOException {
		
		String submissionNarrative = (String) row.get("SubmissionNarrative");
		
		try {
			se.verify().verifyEquals("Submission Page is displayed", GetSubmissionPage().isDisplayed(),true,true, test);
			

			//se.element().enterText(getsubmissionNarrative(), submissionNarrative);
			//se.element().Click(ClicksubmissionSave());
			
			//se.element().Click(GetSubmissionPage());
			
			Thread.sleep(3000);
			se.verify().verifyEquals("Editing the Customer Details", true,true,true, test);
			
			se.element().Click(clicsubmissionEdit());
			
//			test.log(LogStatus.INFO, "Entering text in USERNAME & PASSWORD fields","<b>USERNAME : " + strLgnUsername + "<br> <b>PASSWORD : " + strLgnPassword);
//			test.log(LogStatus.INFO, "Clicking on the button", "Click on LOGIN");
		} catch (Exception e) {
			se.verify().verifyEquals("Issue with Submission screen", true, false, true, test);
		}
	}
}
