package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;
import com.test.automation.common.framework.Element;

public class CommonRepo extends Page {

	public static By username = By.xpath("//*[@id='UserName']");

	public static WebElement UserName(SeHelper se) {

		return se.element().getElement(username);
	}

	public static WebElement ElementObject(SeHelper se, String xPathExpression) 
	{
		long startWait = System.nanoTime();
		
		
		WebElement element = getElement(se, xPathExpression);
		
		long stopWait = System.nanoTime();
		
		long implicitWait = ((stopWait - startWait) / 100000000);
		
		System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + "\n");

		if (element == null) {
			se.waits().waitForElement(By.xpath(xPathExpression));
		}
		
		
		if(!element.isDisplayed()) {
			se.waits().waitForElementIsDisplayed(By.xpath(xPathExpression));
		}
		
		return getElement(se, xPathExpression);
		// return se.element().getElement(By.xpath(xPathExpression));
	}

	private static WebElement getElement(SeHelper se, String xPathExpression) {

		return se.element().getElement(By.xpath(xPathExpression));
	}

}
