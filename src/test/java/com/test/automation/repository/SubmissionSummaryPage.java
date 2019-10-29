package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;
public class SubmissionSummaryPage extends Page{

	public static By Back = By.xpath("//*[@ng-click=\"navgateToSource()\"]");

	public static WebElement Back(SeHelper se) {

		return se.element().getElement(Back);
	}
}

