package com.test.automation.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PullFromConfig {
	private Properties prop = new Properties();
	InputStream inputStream;
 
	public PullFromConfig() {
 
		try {
			//String canon = new File(".").getCanonicalPath();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			System.out.println(prop.getProperty("BaseURL"));
			System.out.println(prop.getProperty("Browser"));
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
			inputStream.close();
			} catch (IOException e) {
				System.out.println("inputStream failed to close");
			}
		}
	}
	
	public Properties getConfigProp() {
		return this.prop;
	}

}
