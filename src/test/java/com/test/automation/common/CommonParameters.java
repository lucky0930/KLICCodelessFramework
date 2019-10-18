package com.test.automation.common;

import java.io.File;
import java.io.IOException;

public class CommonParameters extends Page{
	
	public String homepath;
	public String filepath;
	public void Parameters() throws IOException{
		homepath = new File(".").getCanonicalPath();
		filepath = homepath + "\\resources\\demo_test_data\\PWTestdata.xlsx";
	
	}
	
}
