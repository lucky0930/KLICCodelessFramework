package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.SeHelper;

public class CommonRepo {

	public static By username = By.xpath("//*[@id='UserName']");

	public static WebElement UserName(SeHelper se) {

		return se.element().getElement(username);
	}

	public static WebElement ElementObject(SeHelper se, String expression) {
		long startWait = System.nanoTime();

		
		String typeOfExpression = getTypeOfExpression(expression);
//		
//		
//		if(typeOfExpression != null)
//		{
//			typeOfExpression = expression.substring(expression.indexOf('=') + 1);
//		}
		

		if (expression.startsWith("/")) {

			WebElement element = getXPathElement(se, expression);

			long stopWait = System.nanoTime();

			long implicitWait = ((stopWait - startWait) / 1000000);

			System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

			if (element == null) {
				se.waits().waitForElement(By.xpath(expression));
			}

			if (!element.isDisplayed()) {
				se.waits().waitForElementIsDisplayed(By.xpath(expression));
			}

			return getXPathElement(se, expression);
		}
		else if(typeOfExpression == null)
		{
			WebElement element = getCSSElement(se, expression);

			long stopWait = System.nanoTime();

			long implicitWait = ((stopWait - startWait) / 1000000);

			System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

			if (element == null) {
				se.waits().waitForElement(By.cssSelector(expression));
			}

			if (!element.isDisplayed()) {
				se.waits().waitForElementIsDisplayed(By.cssSelector(expression));
			}

			return getCSSElement(se, expression);
		}
		else
		{
			
			String newExpression = expression.substring(expression.indexOf('=') + 1);
			
			return getElementBasedOnExpression(se, newExpression, typeOfExpression);
		}
		
		// return se.element().getElement(By.xpath(xPathExpression));
	}

	private static WebElement getElementBasedOnExpression(SeHelper se, String newExpression, String typeOfExpression) {
		switch(typeOfExpression)
		{
			case "css":
				WebElement element = getCSSElement(se, newExpression);

				long stopWait = System.nanoTime();

				//long implicitWait = ((stopWait - startWait) / 1000000);

				//System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

				if (element == null) {
					se.waits().waitForElement(By.cssSelector(newExpression));
				}

				if (!element.isDisplayed()) {
					se.waits().waitForElementIsDisplayed(By.cssSelector(newExpression));
				}

				return getCSSElement(se, newExpression);
		}
		return null;
		
	}

	private static String getTypeOfExpression(String expression) {
		String[] selectors = { "Xpath", "CSS", "id", "name", "Linktext", "PartialLinktext", "TagName", "Classname" };

		for (int i = 0; i <= selectors.length; i++) {
			if (expression.startsWith(selectors[i]))
				return selectors[i];
		}
		return null;
	}

	private static WebElement getXPathElement(SeHelper se, String xPathExpression) {

		return se.element().getElement(By.xpath(xPathExpression));
	}

	private static WebElement getCSSElement(SeHelper se, String cssExpression) {

		return se.element().getElement(By.cssSelector(cssExpression));
	}

}
