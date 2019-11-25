package com.test.automation.repository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.common.SeHelper;

public class LogInPage {
	
	@FindBy(xpath = "//*[@id='Password']")
	static WebElement password;
	
	@FindBy(xpath = "//*[@id='Password']//following::button")
	static WebElement loginButton;
	
	
	public LogInPage(SeHelper se)
	{
		PageFactory.initElements(se.driver(), this);
	}
	
	
	public static void Password(String name)	//encapsulates webelements
	{
		password.sendKeys(name);
	}
	
	public static WebElement Password(SeHelper se)			//original handling (temporary for testing purposes)
	{
		return password;
	}
	
	public static void LogIn()
	{
		loginButton.click();
	}
	
	public static WebElement LogIn(SeHelper se)				//original handling (temporary for testing purposes)
	{
		return loginButton;
	}

}
