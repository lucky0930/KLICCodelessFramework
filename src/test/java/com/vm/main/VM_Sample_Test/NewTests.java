package com.vm.main.VM_Sample_Test;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;

public class NewTests {

	@BeforeMethod(alwaysRun = true, groups = { "test" }, timeOut = 1800000000)
	protected void beforeMethod(Method method, Object[] params) {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\VMQApractice\\Desktop\\VAMQAPractice\\VM_Framework_Base\\VM_Framework_Base\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("");
	}
	
	

}
