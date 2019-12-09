package com.test.automation.repository;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;

public class Iframepage extends Page {
	public static By Submit = By.xpath("//*[@id=\"a077aa5e\"]");

	public static WebElement Submit(SeHelper se) {
		
		return se.element().getElement(Submit);
	}
//	public static By 2ndiframe = By.xpath("//*[@id=\"google_ads_iframe_/24132379/guru99.com_728x90_0\"]");
//
//	public static WebElement Submit(SeHelper se) {
//		
//		return se.element().getElement(Submit);
//	}
}
