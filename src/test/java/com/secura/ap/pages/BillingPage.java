//package com.secura.ap.pages;
//
//import com.relevantcodes.extentreports.ExtentTest;
//import com.test.automation.repository.OR_Common;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class BillingPage extends OR_Common {
//
//    public void makeSinglePayment(Map<String, String> row, ExtentTest test) throws IOException {
//
//        try {
//            se.element().Click(getPoliciesTab());
//            se.element().Click(getpolicyNumber());
//            se.element().Click(getbillingacctTab());
//            se.element().Click(getSinglePaymentBtn());
//            Thread.sleep(3000);
//            se.element().enterText(getTransactionAmount(), row.get("Amount"));
//            se.element().Click(getPaymentModeDD());
//            se.element().Click(getCheckPayment());
//            se.element().enterText(getCheck(), row.get("Check"));
//            se.element().Click(getPage());
//            se.element().Click(getProceedBtn());
//            Thread.sleep(3000);
////			test.log(LogStatus.INFO, "Entering text in USERNAME & PASSWORD fields","<b>USERNAME : " + strLgnUsername + "<br> <b>PASSWORD : " + strLgnPassword);
////			test.log(LogStatus.INFO, "Clicking on the button", "Click on LOGIN");
//        } catch (Exception e) {
//            se.verify().verifyEquals("Issue with login screen", true, false, true, test);
//        }
//    }
//}
