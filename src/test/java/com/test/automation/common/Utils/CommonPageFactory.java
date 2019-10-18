package com.test.automation.common.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.common.CommonPage;
import com.test.automation.common.SeHelper;

public class CommonPageFactory
{

  public static <T> T initElements(WebDriver driver, java.lang.Class<T> pageClassToProxy) {
   T obj = PageFactory.initElements(driver, pageClassToProxy);
   SeHelper se = new SeHelper();
   se.setDriver(driver);
   ((CommonPage)obj).setSeHelper(se);
 
   return obj;
  }
  
  public static <T> T initElements(SeHelper se, java.lang.Class<T> pageClassToProxy) {
	   T obj = PageFactory.initElements(se.driver(), pageClassToProxy);
	   ((CommonPage)obj).setSeHelper(se);
	   return obj;
	  }
  
}

