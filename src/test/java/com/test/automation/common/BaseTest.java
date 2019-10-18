package com.test.automation.common;

public class BaseTest extends CommonBaseTest{
				
		public String getEnvironment(String baseUrl) {
			
			int envStartPosition = baseUrl.lastIndexOf("://") + 3;
			int envEndPosition = baseUrl.indexOf("-");
			String env=baseUrl.substring(envStartPosition, envEndPosition);
			return env;
		}

}
