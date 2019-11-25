package com.test.automation.repository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.common.SeHelper;

public class HomePage {
	
	@FindBy(name = "UserName")
	static WebElement userName;
	
	@FindBy(xpath = "//*[@id='UserName']//following::button")
	static WebElement nextButton;	
	
	
	public HomePage(SeHelper se)
	{
		PageFactory.initElements(se.driver(), this);
	}
	
	
	/*public static void UserName(String name)	//encapsulates webelements
	{
		userName.sendKeys(name);
	}
	*/
	
	public static WebElement UserName(SeHelper se)			//original handling (temporary for testing purposes)
	{
		return userName;
	}
	
	/*
	public static void Next()
	{
		nextButton.click();
	}
// */
	public static WebElement Next(SeHelper se)				//original handling (temporary for testing purposes)
	{
		return nextButton;
	}
}
