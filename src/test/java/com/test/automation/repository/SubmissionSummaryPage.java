package com.test.automation.repository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.common.SeHelper;

public class SubmissionSummaryPage {
	
	public SubmissionSummaryPage(SeHelper se) {
		PageFactory.initElements(se.driver(), this);
	}

	@FindBy(xpath = "//*[@class='btn btn-blue']")
	static WebElement Back;

	public static WebElement Back(SeHelper se) {
		return Back;
	}
}
