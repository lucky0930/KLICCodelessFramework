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
		

		if (expression.startsWith("/") || expression.startsWith("(/")) {

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
			System.out.print(newExpression);
			
			return getElementBasedOnExpression(se, newExpression, typeOfExpression);
		}
		
		// return se.element().getElement(By.xpath(xPathExpression));
	}

	private static WebElement getElementBasedOnExpression(SeHelper se, String newExpression, String typeOfExpression) {
		
		long stopWait;
		WebElement element;
		switch(typeOfExpression)
		{
			case "CSS":
				
				 element = getCSSElement(se, newExpression);

				 stopWait = System.nanoTime();

				//long implicitWait = ((stopWait - startWait) / 1000000);

				//System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

				if (element == null) {
					se.waits().waitForElement(By.cssSelector(newExpression));
				}

				if (!element.isDisplayed()) {
					se.waits().waitForElementIsDisplayed(By.cssSelector(newExpression));
				}

				return getCSSElement(se, newExpression);
				
				
			case "Xpath":
				 element = getXPathElement(se, newExpression);

				 stopWait = System.nanoTime();

				//long implicitWait = ((stopWait - startWait) / 1000000);

				//System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

				if (element == null) {
					se.waits().waitForElement(By.xpath(newExpression));
				}

				if (!element.isDisplayed()) {
					se.waits().waitForElementIsDisplayed(By.xpath(newExpression));
				}

				return getXPathElement(se, newExpression);
				
				
			case "id":	
				
				
				 element = getIdElement(se, newExpression);

				 stopWait = System.nanoTime();

				//long implicitWait = ((stopWait - startWait) / 1000000);

				//System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

				if (element == null) {
					se.waits().waitForElement(By.id(newExpression));
				}

				if (!element.isDisplayed()) {
					se.waits().waitForElementIsDisplayed(By.id(newExpression));
				}

				return getIdElement(se, newExpression);
			case "name":	
				 element = getNameElement(se, newExpression);

				 stopWait = System.nanoTime();

				//long implicitWait = ((stopWait - startWait) / 1000000);

				//System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

				if (element == null) {
					se.waits().waitForElement(By.name(newExpression));
				}

				if (!element.isDisplayed()) {
					se.waits().waitForElementIsDisplayed(By.name(newExpression));
				}

				return getNameElement(se, newExpression);
			case "Linktext":	
				 element = getLinkTextElement(se, newExpression);

				 stopWait = System.nanoTime();

				//long implicitWait = ((stopWait - startWait) / 1000000);

				//System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

				if (element == null) {
					se.waits().waitForElement(By.linkText(newExpression));
				}

				if (!element.isDisplayed()) {
					se.waits().waitForElementIsDisplayed(By.linkText(newExpression));
				}

				return getLinkTextElement(se, newExpression);
			case "PartialLinktext":	
				 element = getPartialLinkTextElement(se, newExpression);

				 stopWait = System.nanoTime();

				//long implicitWait = ((stopWait - startWait) / 1000000);

				//System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

				if (element == null) {
					se.waits().waitForElement(By.partialLinkText(newExpression));
				}

				if (!element.isDisplayed()) {
					se.waits().waitForElementIsDisplayed(By.partialLinkText(newExpression));
				}

				return getPartialLinkTextElement(se, newExpression);
				
				
				
			case "TagName":	
				 element = getTagNameElement(se, newExpression);

				 stopWait = System.nanoTime();

				//long implicitWait = ((stopWait - startWait) / 1000000);

				//System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

				if (element == null) {
					se.waits().waitForElement(By.tagName(newExpression));
				}

				if (!element.isDisplayed()) {
					se.waits().waitForElementIsDisplayed(By.tagName(newExpression));
				}

				return getTagNameElement(se, newExpression);
				
			case "Classname":	
				 element = getClassnameElement(se, newExpression);

				 stopWait = System.nanoTime();

				//long implicitWait = ((stopWait - startWait) / 1000000);

				//System.out.print("\n IMPLICIT WAIT TIME " + implicitWait + " MS \n");

				if (element == null) {
					se.waits().waitForElement(By.className(newExpression));
				}

				if (!element.isDisplayed()) {
					se.waits().waitForElementIsDisplayed(By.className(newExpression));
				}

				return getClassnameElement(se, newExpression);
		
				
				
				
		}
		return null;
		
	}

	private static String getTypeOfExpression(String expression) {
		String[] selectors = { "Xpath", "CSS", "id", "name", "Linktext", "PartialLinktext", "TagName", "Classname" };

		for (int i = 0; i < selectors.length; i++) {
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
	
	private static WebElement getIdElement(SeHelper se, String idExpression) {
		return se.element().getElement(By.id(idExpression));
		
	}
	private static WebElement getNameElement(SeHelper se, String nameExpression) {
		return se.element().getElement(By.name(nameExpression));
		
	}
	private static WebElement getLinkTextElement(SeHelper se, String linkTextExpression) {
		return se.element().getElement(By.linkText(linkTextExpression));
		
	}
	private static WebElement getTagNameElement(SeHelper se, String tagNameExpression) {
		return se.element().getElement(By.tagName(tagNameExpression));
		
	}
	private static WebElement getPartialLinkTextElement(SeHelper se, String partialLinkTextExpression) {
		return se.element().getElement(By.partialLinkText(partialLinkTextExpression));
		
	}
	private static WebElement getClassnameElement(SeHelper se, String classNameExpression) {
		return se.element().getElement(By.className(classNameExpression));
		
	}
	

}
