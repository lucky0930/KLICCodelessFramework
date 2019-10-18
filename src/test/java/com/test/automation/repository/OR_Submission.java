package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.test.automation.common.Page;

public class OR_Submission extends Page {
	
	public By SubmissionPage = By.xpath("//*[@class='row vam-PTB10']");
	public WebElement GetSubmissionPage() {
		se.element().waitForElement(SubmissionPage);
		return se.element().getElement(SubmissionPage);
	}

	public By newQuote = By.xpath("//*[@ng-click='createQuote()']");

	public WebElement createNewQuote() {
		se.element().waitForElement(newQuote);
	 	return se.element().getElement(newQuote);
	}
	
	public By SubmissionNarrative = By.xpath("//*[@ng-model='submissionNarrative']");

	public WebElement getsubmissionNarrative() {
		se.element().waitForElement(SubmissionNarrative);
	 	return se.element().getElement(SubmissionNarrative);
	}
	
	public By SubmissionSave = By.xpath("//*[@ng-click='updateNarrative()']");

	public WebElement ClicksubmissionSave() {
		se.element().waitForElement(SubmissionSave);
	 	return se.element().getElement(SubmissionSave);
	}
	
	public By SubmissionEdit = By.xpath("//*[contains(text(),'Edit')]");

	public WebElement clicsubmissionEdit() {
		se.element().waitForElement(SubmissionEdit);
	 	return se.element().getElement(SubmissionEdit);
	}
	
	
	


}
