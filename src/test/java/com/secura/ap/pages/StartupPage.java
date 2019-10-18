package com.secura.ap.pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.common.Page;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.framework.Browser.Browsers;

public class StartupPage extends Page {
	
	LoginPage login;
	
/**
 * Description - Accessing URL	
 */
public void APStartUp(ExtentTest test, Browsers myBrowser){	
	
		se.log().logTestStep("Access Agency Port Application");	
			test.log(LogStatus.INFO, "Access Agency Port Application", "Browser : "+myBrowser+"<br> URL : "+SystemPropertyUtil.getBaseStoreUrl());
			se.browser().get(SystemPropertyUtil.getBaseStoreUrl());
		
	}

}
