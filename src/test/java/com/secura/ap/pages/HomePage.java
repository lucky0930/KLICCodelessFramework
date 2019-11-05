//package com.secura.ap.pages;
//
//import java.io.IOException;
//import java.util.Map;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.interactions.Actions;
//
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;
//import com.test.automation.repository.OR_Common;
//import com.test.automation.repository.OR_Home;
//
//public class HomePage extends OR_Home {
//
//	String CA = "Commercial Auto";
//	String WC = "Workers Comp";
//
//	public void indexHome(Map<String, String> row,ExtentTest test) throws IOException {
//
//		try {
//			se.verify().verifyEquals("Login is successfull", true,true,true, test);
//			se.verify().verifyEquals("Home Page is displayed", validateHome().isDisplayed(),true,true, test);
//			se.element().Click(ClickNewSubmission());
//
//			se.verify().verifyEquals("New Submission is clicked", true,true,true, test);
////			test.log(LogStatus.INFO, "Entering text in USERNAME & PASSWORD fields","<b>USERNAME : " + strLgnUsername + "<br> <b>PASSWORD : " + strLgnPassword);
////			test.log(LogStatus.INFO, "Clicking on the button", "Click on LOGIN");
//		} catch (Exception e) {
//			se.verify().verifyEquals("Issue with Home screen", true, false, true, test);
//		}
//	}
//}
