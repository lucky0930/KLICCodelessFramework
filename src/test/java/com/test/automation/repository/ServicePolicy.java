package com.test.automation.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;

	public class ServicePolicy extends Page {

		public static By CustomerAccountName = By.xpath("//input[@ng-model='CustomerSearchCriteria.AccountName']");

		public static WebElement CustomerAccountName(SeHelper se) {

			return se.element().getElement(CustomerAccountName, true);
		}
		public static By Search = By.xpath("//*[@id=\"div1\"]/div/button");

		public static WebElement Search(SeHelper se) {

			return se.element().getElement(Search, true);
		}
		public static By Actions = By.xpath("(//a[@class='vam-table-tdArrow'])[2]");

		public static WebElement Actions(SeHelper se) {

			return se.element().getElement(Actions, true);
		}
}
