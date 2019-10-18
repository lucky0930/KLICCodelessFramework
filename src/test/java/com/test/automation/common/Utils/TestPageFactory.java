package com.test.automation.common.Utils;


import org.openqa.selenium.WebDriver;

import com.test.automation.common.Page;
import com.test.automation.common.SeHelper;

import org.openqa.selenium.support.PageFactory;

public class TestPageFactory extends CommonPageFactory
{
  public static <T> T initElements(WebDriver driver, java.lang.Class<T> pageClassToProxy) {
   T obj = PageFactory.initElements(driver, pageClassToProxy);
   SeHelper se = new SeHelper();
   se.setDriver(driver);
   ((Page)obj).setSeHelper(se);
 
   return obj;
  }

  public static <T> T initElements(SeHelper se, java.lang.Class<T> pageClassToProxy) {
	   T obj = PageFactory.initElements(se.driver(), pageClassToProxy);
	   ((Page)obj).setSeHelper(se);
	   return obj;
	  }
  
}